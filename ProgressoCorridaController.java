package com.academicrace.api.controller;

import com.academicrace.api.model.ProgressoCorrida;
import com.academicrace.api.service.ProgressoCorridaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/progresso-corrida")
public class ProgressoCorridaController {

    @Autowired
    private ProgressoCorridaService progressoCorridaService;

    @GetMapping("/usuario/{usuarioId}")
    public List<ProgressoCorrida> listarPorUsuario(@PathVariable Long usuarioId) {
        return progressoCorridaService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/grupo/{grupoId}")
    public List<ProgressoCorrida> listarPorGrupo(@PathVariable Long grupoId) {
        return progressoCorridaService.listarPorGrupo(grupoId);
    }

    @PostMapping
    public ProgressoCorrida salvar(@RequestBody ProgressoCorrida progresso) {
        return progressoCorridaService.salvar(progresso);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        progressoCorridaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}