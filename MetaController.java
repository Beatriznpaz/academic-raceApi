package com.academicrace.api.controller;

import com.academicrace.api.model.Meta;
import com.academicrace.api.service.MetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/metas")
public class MetaController {

    @Autowired
    private MetaService metaService;

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Meta> buscarPorUsuario(@PathVariable Long usuarioId) {
        return metaService.buscarPorUsuario(usuarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Meta salvar(@RequestBody Meta meta) {
        return metaService.salvar(meta);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        metaService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}