package br.com.reis_wedding.dto;

import java.time.LocalDateTime;

public class AcompanhanteResponseDTO {

    private Long id;
    private Long conviteId;
    private String nome;
    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getConviteId() {
        return conviteId;
    }

    public void setConviteId(Long conviteId) {
        this.conviteId = conviteId;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}