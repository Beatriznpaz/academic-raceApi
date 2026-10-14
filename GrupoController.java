package com.academicrace.api.controller;

import com.academicrace.api.model.Grupo;
import com.academicrace.api.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos")
public class GrupoController {

    @Autowired
    private GrupoService grupoService;

    @GetMapping
    public List<Grupo> listarTodos() {
        return grupoService.listarTodos();
    }

    @GetMapping("/tipo/{tipo}")
    public List<Grupo> listarPorTipo(@PathVariable Grupo.TipoGrupo tipo) {
        return grupoService.listarPorTipo(tipo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Grupo> buscarPorId(@PathVariable Long id) {
        return grupoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Grupo salvar(@RequestBody Grupo grupo) {
        return grupoService.salvar(grupo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        grupoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}