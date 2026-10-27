package br.com.reis_wedding.dto;

public class AcompanhanteRequestDTO {

    private String nome;

    public AcompanhanteRequestDTO() {
    }

    public AcompanhanteRequestDTO(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}