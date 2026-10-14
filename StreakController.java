package com.academicrace.api.controller;

import com.academicrace.api.model.Streak;
import com.academicrace.api.service.StreakService;
import com.academicrace.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/streaks")
public class StreakController {

    @Autowired
    private StreakService streakService;

    @Autowired
    private UsuarioService usuarioService;

    public record StreakResponse(Integer diasConsecutivos, LocalDate ultimaData) {
    }

    @GetMapping("/me")
    public StreakResponse me(Authentication authentication) {
        Long usuarioId = usuarioService.buscarAtual(authentication).getId();
        return streakService.obterAtualizado(usuarioId)
                .map(s -> new StreakResponse(s.getDiasConsecutivos(), s.getUltimaData()))
                .orElse(new StreakResponse(0, null));
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<Streak> buscarPorUsuario(@PathVariable Long usuarioId) {
        return streakService.buscarPorUsuario(usuarioId)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Streak salvar(@RequestBody Streak streak) {
        return streakService.salvar(streak);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        streakService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}