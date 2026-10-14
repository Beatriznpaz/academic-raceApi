package com.academicrace.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "progresso_corrida")
public class ProgressoCorrida {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "grupo_id", nullable = false)
    private Grupo grupo;

    @Column(name = "tempo_estudado")
    private Integer tempoEstudado;

    @Column(columnDefinition = "TEXT")
    private String observacao;

    private LocalDateTime data;

    // Getters e Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Grupo getGrupo() {
        return grupo;
    }

    public void setGrupo(Grupo grupo) {
        this.grupo = grupo;
    }

    public Integer getTempoEstudado() {
        return tempoEstudado;
    }

    public void setTempoEstudado(Integer tempoEstudado) {
        this.tempoEstudado = tempoEstudado;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}