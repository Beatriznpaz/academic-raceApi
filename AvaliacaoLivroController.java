package com.academicrace.api.controller;

import com.academicrace.api.model.AvaliacaoLivro;
import com.academicrace.api.service.AvaliacaoLivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/avaliacoes")
public class AvaliacaoLivroController {

    @Autowired
    private AvaliacaoLivroService avaliacaoLivroService;

    @GetMapping("/livro/{livroId}")
    public List<AvaliacaoLivro> listarPorLivro(@PathVariable Long livroId) {
        return avaliacaoLivroService.listarPorLivro(livroId);
    }

    @GetMapping("/usuario/{usuarioId}/livro/{livroId}")
    public ResponseEntity<AvaliacaoLivro> buscarPorUsuarioELivro(@PathVariable Long usuarioId, @PathVariable Long livroId) {
        return avaliacaoLivroService.buscarPorUsuarioELivro(usuarioId, livroId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public AvaliacaoLivro salvar(@RequestBody AvaliacaoLivro avaliacao) {
        return avaliacaoLivroService.salvar(avaliacao);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        avaliacaoLivroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}