package com.academicrace.api.model;

import jakarta.persistence.*;

@Entity
@Table(name = "estante_livro")
public class EstanteLivro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "livro_id", nullable = false)
    private Livro livro;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusLeitura status;

    public enum StatusLeitura {
        TBR, CR, DNF
    }

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

    public Livro getLivro() {
        return livro;
    }

    public void setLivro(Livro livro) {
        this.livro = livro;
    }

    public StatusLeitura getStatus() {
        return status;
    }

    public void setStatus(StatusLeitura status) {
        this.status = status;
    }
}