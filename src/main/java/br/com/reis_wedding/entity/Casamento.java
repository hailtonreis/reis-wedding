package br.com.reis_wedding.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "casamentos")
public class Casamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome_noivo", nullable = false, length = 100)
    private String nomeNoivo;

    @Column(name = "nome_noiva", nullable = false, length = 100)
    private String nomeNoiva;

    @Column(name = "data_evento", nullable = false)
    private LocalDate dataEvento;
    
    @Column(name = "data_limite_rsvp")
    private LocalDate dataLimiteRsvp;

    @Column(nullable = false)
    private Boolean ativo = true;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNomeNoivo() {
        return nomeNoivo;
    }

    public void setNomeNoivo(String nomeNoivo) {
        this.nomeNoivo = nomeNoivo;
    }

    public String getNomeNoiva() {
        return nomeNoiva;
    }

    public void setNomeNoiva(String nomeNoiva) {
        this.nomeNoiva = nomeNoiva;
    }

    public LocalDate getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(LocalDate dataEvento) {
        this.dataEvento = dataEvento;
    }
    
    public LocalDate getDataLimiteRsvp() {
        return dataLimiteRsvp;
    }

    public void setDataLimiteRsvp(LocalDate dataLimiteRsvp) {
        this.dataLimiteRsvp = dataLimiteRsvp;
    }

    public Boolean getAtivo() {
        return ativo;
    }

    public void setAtivo(Boolean ativo) {
        this.ativo = ativo;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
}