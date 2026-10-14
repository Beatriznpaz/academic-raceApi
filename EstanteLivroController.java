package com.academicrace.api.controller;

import com.academicrace.api.model.EstanteLivro;
import com.academicrace.api.model.EstanteLivro.StatusLeitura;
import com.academicrace.api.service.EstanteLivroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/estante")
public class EstanteLivroController {

    @Autowired
    private EstanteLivroService estanteLivroService;

    @GetMapping("/usuario/{usuarioId}")
    public List<EstanteLivro> listarPorUsuario(@PathVariable Long usuarioId) {
        return estanteLivroService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/usuario/{usuarioId}/status/{status}")
    public List<EstanteLivro> listarPorUsuarioEStatus(@PathVariable Long usuarioId, @PathVariable StatusLeitura status) {
        return estanteLivroService.listarPorUsuarioEStatus(usuarioId, status);
    }

    @PostMapping
    public EstanteLivro salvar(@RequestBody EstanteLivro estante) {
        return estanteLivroService.salvar(estante);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        estanteLivroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}