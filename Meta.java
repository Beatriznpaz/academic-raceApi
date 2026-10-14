package com.academicrace.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "meta")
public class Meta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(name = "minutos_por_dia", nullable = false)
    private Integer minutosPorDia;

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

    public Integer getMinutosPorDia() {
        return minutosPorDia;
    }

    public void setMinutosPorDia(Integer minutosPorDia) {
        this.minutosPorDia = minutosPorDia;
    }
}