/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.edamatec.cadastrocliente.model;

/**
 *
 * @author Victoria-Pc
 */
public class Cliente {
    private int id;
    private String nome;
    private String cpf;
    private String telefone;
    private String email;
    
    public Cliente(){
        
    }
    
    public Cliente(int id, String nome, String cpf, String telefone, String email){
        this.id = id;
        this.cpf = cpf;
        this.email = email;
        this.nome = nome;
        this.telefone = telefone;
    }
    
    public int getId() { 
        return id; 
    }
    
    public void setId(int id) { 
        this.id = id;
    }
    
    public String getNome() { 
        return nome;
    }
    
    public void setNome(String nome) {
        this.nome = nome; 
    }
    
    public String getCpf() {
        return cpf; 
    }
    
    public void setCpf(String cpf) {
        this.cpf = cpf; 
    }
    
    public String getTelefone() {
        return telefone; 
    }
    
    public void setTelefone(String telefone) { 
        this.telefone = telefone;
    }
    
    public String getEmail() {
        return email;
    }
    
    public void setEmail(String email) {
        this.email = email; 
    }
}
