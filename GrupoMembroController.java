package com.academicrace.api.controller;

import com.academicrace.api.model.GrupoMembro;
import com.academicrace.api.service.GrupoMembroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupo-membros")
public class GrupoMembroController {

    @Autowired
    private GrupoMembroService grupoMembroService;

    @GetMapping("/grupo/{grupoId}")
    public List<GrupoMembro> listarPorGrupo(@PathVariable Long grupoId) {
        return grupoMembroService.listarPorGrupo(grupoId);
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<GrupoMembro> listarPorUsuario(@PathVariable Long usuarioId) {
        return grupoMembroService.listarPorUsuario(usuarioId);
    }

    @PostMapping
    public GrupoMembro salvar(@RequestBody GrupoMembro membro) {
        return grupoMembroService.salvar(membro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        grupoMembroService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}