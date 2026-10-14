package com.academicrace.api.controller;

import com.academicrace.api.model.ProgressoLeitura;
import com.academicrace.api.service.ProgressoLeituraService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progresso-leitura")
public class ProgressoLeituraController {

    @Autowired
    private ProgressoLeituraService progressoLeituraService;

    @GetMapping("/usuario/{usuarioId}")
    public List<ProgressoLeitura> listarPorUsuario(@PathVariable Long usuarioId) {
        return progressoLeituraService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/livro/{livroId}")
    public List<ProgressoLeitura> listarPorLivro(@PathVariable Long livroId) {
        return progressoLeituraService.listarPorLivro(livroId);
    }

    @PostMapping
    public ProgressoLeitura salvar(@RequestBody ProgressoLeitura progresso) {
        return progressoLeituraService.salvar(progresso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        progressoLeituraService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}