package com.academicrace.api.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "registro_estudo")
public class RegistroEstudo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, length = 100)
    private String titulo;

    @Column(columnDefinition = "TEXT")
    private String descricao;

    @ManyToOne
    @JoinColumn(name = "metodo_id")
    private MetodoEstudo metodo;

    @Column(name = "duracao_minutos", nullable = false)
    private Integer duracaoMinutos;

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

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public MetodoEstudo getMetodo() {
        return metodo;
    }

    public void setMetodo(MetodoEstudo metodo) {
        this.metodo = metodo;
    }

    public Integer getDuracaoMinutos() {
        return duracaoMinutos;
    }

    public void setDuracaoMinutos(Integer duracaoMinutos) {
        this.duracaoMinutos = duracaoMinutos;
    }

    public LocalDateTime getData() {
        return data;
    }

    public void setData(LocalDateTime data) {
        this.data = data;
    }
}