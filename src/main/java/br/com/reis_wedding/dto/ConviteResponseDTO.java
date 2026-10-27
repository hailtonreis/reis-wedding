package br.com.reis_wedding.dto;

import java.time.LocalDateTime;

import br.com.reis_wedding.enums.StatusConvite;

public class ConviteResponseDTO {

    private Long id;
    private Long casamentoId;
    private String nomeConvidado;
    private String telefone;
    private StatusConvite status;
    private Integer limiteAcompanhantes;
    private LocalDateTime dataConfirmacao;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String token;
    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getCasamentoId() {
        return casamentoId;
    }

    public void setCasamentoId(Long casamentoId) {
        this.casamentoId = casamentoId;
    }

    public String getNomeConvidado() {
        return nomeConvidado;
    }

    public void setNomeConvidado(String nomeConvidado) {
        this.nomeConvidado = nomeConvidado;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public StatusConvite getStatus() {
        return status;
    }

    public void setStatus(StatusConvite status) {
        this.status = status;
    }

    public Integer getLimiteAcompanhantes() {
        return limiteAcompanhantes;
    }

    public void setLimiteAcompanhantes(Integer limiteAcompanhantes) {
        this.limiteAcompanhantes = limiteAcompanhantes;
    }

    public LocalDateTime getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(LocalDateTime dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}