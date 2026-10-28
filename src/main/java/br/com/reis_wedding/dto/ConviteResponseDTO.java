package br.com.reis_wedding.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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

    // Dados adicionais para o RSVP
    private List<String> acompanhantes = new ArrayList<>();
    private LocalDate dataLimiteRsvp;
    private Boolean prazoRsvpEncerrado;

    // =========================================================
    // ID
    // =========================================================

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    // =========================================================
    // CASAMENTO
    // =========================================================

    public Long getCasamentoId() {
        return casamentoId;
    }

    public void setCasamentoId(Long casamentoId) {
        this.casamentoId = casamentoId;
    }

    // =========================================================
    // CONVIDADO
    // =========================================================

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

    // =========================================================
    // STATUS
    // =========================================================

    public StatusConvite getStatus() {
        return status;
    }

    public void setStatus(StatusConvite status) {
        this.status = status;
    }

    // =========================================================
    // LIMITE DE ACOMPANHANTES
    // =========================================================

    public Integer getLimiteAcompanhantes() {
        return limiteAcompanhantes;
    }

    public void setLimiteAcompanhantes(Integer limiteAcompanhantes) {
        this.limiteAcompanhantes = limiteAcompanhantes;
    }

    // =========================================================
    // DATA DE CONFIRMAÇÃO
    // =========================================================

    public LocalDateTime getDataConfirmacao() {
        return dataConfirmacao;
    }

    public void setDataConfirmacao(LocalDateTime dataConfirmacao) {
        this.dataConfirmacao = dataConfirmacao;
    }

    // =========================================================
    // DATA DE CRIAÇÃO
    // =========================================================

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    // =========================================================
    // DATA DE ATUALIZAÇÃO
    // =========================================================

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // =========================================================
    // TOKEN
    // =========================================================

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    // =========================================================
    // ACOMPANHANTES
    // =========================================================

    public List<String> getAcompanhantes() {
        return acompanhantes;
    }

    public void setAcompanhantes(List<String> acompanhantes) {
        this.acompanhantes = acompanhantes != null
                ? acompanhantes
                : new ArrayList<>();
    }

    // =========================================================
    // DATA-LIMITE DO RSVP
    // =========================================================

    public LocalDate getDataLimiteRsvp() {
        return dataLimiteRsvp;
    }

    public void setDataLimiteRsvp(LocalDate dataLimiteRsvp) {
        this.dataLimiteRsvp = dataLimiteRsvp;
    }

    // =========================================================
    // PRAZO DO RSVP ENCERRADO
    // =========================================================

    public Boolean getPrazoRsvpEncerrado() {
        return prazoRsvpEncerrado;
    }

    public void setPrazoRsvpEncerrado(Boolean prazoRsvpEncerrado) {
        this.prazoRsvpEncerrado = prazoRsvpEncerrado;
    }

    // FIM DO ARQUIVO
}