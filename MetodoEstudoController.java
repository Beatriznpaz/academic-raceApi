package com.academicrace.api.controller;

import com.academicrace.api.model.MetodoEstudo;
import com.academicrace.api.service.MetodoEstudoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/metodos-estudo")
public class MetodoEstudoController {

    @Autowired
    private MetodoEstudoService metodoEstudoService;

    @GetMapping
    public List<MetodoEstudo> listarTodos() {
        return metodoEstudoService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<MetodoEstudo> buscarPorId(@PathVariable Long id) {
        return metodoEstudoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public MetodoEstudo salvar(@RequestBody MetodoEstudo metodo) {
        return metodoEstudoService.salvar(metodo);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        metodoEstudoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}