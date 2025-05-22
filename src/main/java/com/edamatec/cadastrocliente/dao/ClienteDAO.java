package com.edamatec.cadastrocliente.dao;

import com.edamatec.cadastrocliente.model.Cliente;
import com.edamatec.cadastrocliente.util.ConnectionFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ClienteDAO {

    private static final Logger LOGGER = Logger.getLogger(ClienteDAO.class.getName());

    public void inserir(Cliente cliente) {
        try {
            validarCliente(cliente);

            String sql = "INSERT INTO clientes (nome, cpf, telefone, email) VALUES (?, ?, ?, ?)";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getCpf());
                stmt.setString(3, cliente.getTelefone());
                stmt.setString(4, cliente.getEmail());
                stmt.executeUpdate();
            }

        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, () -> "Validação falhou ao inserir cliente: " + e.getMessage());
            throw e;  // opcional: repassa a exceção para a camada superior tratar (UI)
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao inserir cliente", e);
        }
    }

    public List<Cliente> listarTodos() {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Cliente c = popularCliente(rs);
                lista.add(c);
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao listar clientes", e);
        }
        return lista;
    }

    public List<Cliente> buscarPorNome(String nome) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE nome LIKE ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + nome + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente c = popularCliente(rs);
                    lista.add(c);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar cliente por nome", e);
        }
        return lista;
    }

    public List<Cliente> buscarPorCpf(String cpf) {
        List<Cliente> lista = new ArrayList<>();
        String sql = "SELECT * FROM clientes WHERE REPLACE(REPLACE(REPLACE(cpf, '.', ''), '-', ''), ' ', '') LIKE ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, "%" + cpf + "%");

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Cliente c = popularCliente(rs);
                    lista.add(c);
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar cliente por CPF", e);
        }
        return lista;
    }

    public boolean cpfExiste(String cpf) {
        String sql = "SELECT COUNT(*) FROM clientes WHERE cpf = ?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, cpf);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1) > 0;
                }
            }

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao verificar existência de CPF", e);
        }
        return false;
    }

    public void atualizar(Cliente cliente) {
        try {
            validarCliente(cliente);

            String sql = "UPDATE clientes SET nome=?, cpf=?, telefone=?, email=? WHERE id=?";

            try (Connection conn = ConnectionFactory.getConnection();
                 PreparedStatement stmt = conn.prepareStatement(sql)) {

                stmt.setString(1, cliente.getNome());
                stmt.setString(2, cliente.getCpf());
                stmt.setString(3, cliente.getTelefone());
                stmt.setString(4, cliente.getEmail());
                stmt.setInt(5, cliente.getId());
                stmt.executeUpdate();
            }

        } catch (IllegalArgumentException e) {
            LOGGER.log(Level.WARNING, () -> "Validação falhou ao atualizar cliente: " + e.getMessage());
            throw e;
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao atualizar cliente", e);
        }
    }

    public void deletar(int id) {
        String sql = "DELETE FROM clientes WHERE id=?";

        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE, "Erro ao deletar cliente", e);
        }
    }

    // --- Métodos auxiliares ---

    private Cliente popularCliente(ResultSet rs) throws SQLException {
        Cliente c = new Cliente();
        c.setId(rs.getInt("id"));
        c.setNome(rs.getString("nome"));
        c.setCpf(rs.getString("cpf"));
        c.setTelefone(rs.getString("telefone"));
        c.setEmail(rs.getString("email"));
        return c;
    }

    private void validarCliente(Cliente cliente) {
        if (cliente == null) {
            throw new IllegalArgumentException("Cliente não pode ser nulo.");
        }
        if (cliente.getNome() == null || cliente.getNome().trim().isEmpty()) {
            throw new IllegalArgumentException("Nome é obrigatório.");
        }
        if (!isCpfValido(cliente.getCpf())) {
            throw new IllegalArgumentException("CPF inválido.");
        }
        if (cliente.getEmail() != null && !cliente.getEmail().trim().isEmpty() && !isEmailValido(cliente.getEmail())) {
            throw new IllegalArgumentException("Email inválido.");
        }
        if (cliente.getTelefone() != null && !cliente.getTelefone().trim().isEmpty() && !isTelefoneValido(cliente.getTelefone())) {
            throw new IllegalArgumentException("Telefone inválido.");
        }
    }

    private boolean isCpfValido(String cpf) {
        if (cpf == null) return false;
        String cpfNum = cpf.replaceAll("[^0-9]", "");
        return cpfNum.length() == 11;
    }

    private boolean isEmailValido(String email) {
        return email.matches("^[\\w\\.-]+@[\\w\\.-]+\\.\\w{2,}$");
    }

    private boolean isTelefoneValido(String telefone) {
        return telefone.matches("[0-9\\-\\s\\(\\)]+");
    }
}
