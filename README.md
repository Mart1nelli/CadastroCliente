# Cadastro de Clientes - Java Swing + MySQL

## 📋 Descrição

Este projeto é uma aplicação desktop desenvolvida em **Java Swing** para gerenciar um cadastro simples de clientes. Permite realizar operações CRUD (Criar, Ler, Atualizar e Deletar) com uma interface gráfica amigável. O backend utiliza **MySQL** para armazenamento dos dados.

---

## ✅ Funcionalidades

- Cadastro de novos clientes com nome, CPF, telefone e email.
- Listagem dos clientes cadastrados em uma tabela (`JTable`).
- Edição dos dados dos clientes existentes.
- Exclusão de clientes com confirmação antes da ação.
- Busca por nome ou CPF.
- Confirmação antes de editar e excluir para evitar erros.
- Campos de formulário com máscara para CPF e telefone.
- Botão para limpar os campos do formulário.

---

## 🛠️ Tecnologias Utilizadas

- Java SE 8 ou superior  
- Swing para interface gráfica  
- MySQL como banco de dados  
- JDBC para conexão com banco de dados  
- NetBeans IDE (opcional)  

---

## 📂 Estrutura do Projeto

- `model` — Contém a classe `Cliente` que representa o modelo de dados.
- `dao` — Classe `ClienteDAO` que realiza operações de banco de dados.
- `view` — Tela principal da aplicação (`TelaDeCadastroCliente`) com a interface gráfica.

---

## 📌 Requisitos

- Java JDK 8+ instalado  
- MySQL instalado e rodando  
- IDE como NetBeans, IntelliJ ou Eclipse (opcional)

---

## 🗃️ Configuração do Banco de Dados

1. Crie o banco de dados no MySQL:

        sql
        CREATE DATABASE cadastro_clientes;
        USE cadastro_clientes;
        
        CREATE TABLE clientes (
            id INT AUTO_INCREMENT PRIMARY KEY,
            nome VARCHAR(100) NOT NULL,
            cpf VARCHAR(14) NOT NULL,
            telefone VARCHAR(15),
            email VARCHAR(100)
        );

---
 3. No arquivo ClienteDAO.java, configure os dados da sua conexão:

            private final String URL = "jdbc:mysql://localhost:3306/cadastro_clientes";
            private final String USER = "seu_usuario";
            private final String PASSWORD = "sua_senha";

---

▶️ Como Rodar
Clone ou baixe o projeto para sua máquina.

Abra o projeto na sua IDE (recomendado: NetBeans)

Adicione o driver JDBC do MySQL ao classpath (ex: mysql-connector-java-X.X.X.jar)

Edite ClienteDAO.java com seu usuário e senha do MySQL.

Rode a classe TelaDeCadastroCliente.java para iniciar a aplicação.

---

🧭 Como Usar
Cadastrar novo cliente:
Preencha os campos e clique em Salvar.

Editar cliente:
Selecione um cliente na tabela, clique em Editar, altere os dados e clique em Salvar.

Excluir cliente:
Selecione o cliente na tabela e clique em Excluir, confirme a ação.

Buscar cliente:
Digite o nome ou CPF no campo e clique em Buscar.

Limpar formulário:
Clique no botão Limpar para limpar os campos e cadastrar um novo cliente.

---

🧠 Explicação Técnica
O formulário usa JTextField e JFormattedTextField com máscara para CPF e telefone.

A tabela é um JTable com DefaultTableModel, populada a partir do banco de dados.

A classe ClienteDAO faz a ponte com o MySQL usando JDBC.

A busca pode ser feita por nome ou CPF. Se ambos estiverem vazios, retorna todos os registros.

O botão salvar detecta se deve inserir ou atualizar com base no ID armazenado.

