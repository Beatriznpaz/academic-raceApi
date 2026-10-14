package com.academicrace.api.model;

import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "streak")
public class Streak {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "dias_consecutivos")
    private Integer diasConsecutivos = 0;

    @Column(name = "ultima_data")
    private LocalDate ultimaData;

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

    public Integer getDiasConsecutivos() {
        return diasConsecutivos;
    }

    public void setDiasConsecutivos(Integer diasConsecutivos) {
        this.diasConsecutivos = diasConsecutivos;
    }

    public LocalDate getUltimaData() {
        return ultimaData;
    }

    public void setUltimaData(LocalDate ultimaData) {
        this.ultimaData = ultimaData;
    }
}