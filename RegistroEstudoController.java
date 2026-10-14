package com.academicrace.api.controller;

import com.academicrace.api.model.MetodoEstudo;
import com.academicrace.api.model.RegistroEstudo;
import com.academicrace.api.model.Usuario;
import com.academicrace.api.service.MetodoEstudoService;
import com.academicrace.api.service.RegistroEstudoService;
import com.academicrace.api.service.StreakService;
import com.academicrace.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/registros-estudo")
public class RegistroEstudoController {

    @Autowired
    private RegistroEstudoService registroEstudoService;

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MetodoEstudoService metodoEstudoService;

    @Autowired
    private StreakService streakService;

    public record NovoRegistroRequest(String titulo, String descricao, Long metodoId, Integer duracaoMinutos) {
    }

    @GetMapping("/me")
    public List<RegistroEstudo> listarMe(Authentication authentication) {
        Long usuarioId = usuarioService.buscarAtual(authentication).getId();
        return registroEstudoService.listarPorUsuario(usuarioId);
    }

    @PostMapping("/me")
    public RegistroEstudo salvarMe(Authentication authentication, @RequestBody NovoRegistroRequest request) {
        Usuario usuario = usuarioService.buscarAtual(authentication);

        RegistroEstudo registro = new RegistroEstudo();
        registro.setUsuario(usuario);
        registro.setTitulo(request.titulo());
        registro.setDescricao(request.descricao());
        registro.setDuracaoMinutos(request.duracaoMinutos());
        registro.setData(LocalDateTime.now());
        if (request.metodoId() != null) {
            MetodoEstudo metodo = metodoEstudoService.buscarPorId(request.metodoId()).orElse(null);
            registro.setMetodo(metodo);
        }

        RegistroEstudo salvo = registroEstudoService.salvar(registro);
        streakService.registrarEstudoHoje(usuario);
        return salvo;
    }

    @GetMapping("/usuario/{usuarioId}")
    public List<RegistroEstudo> listarPorUsuario(@PathVariable Long usuarioId) {
        return registroEstudoService.listarPorUsuario(usuarioId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<RegistroEstudo> buscarPorId(@PathVariable Long id) {
        return registroEstudoService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public RegistroEstudo salvar(@RequestBody RegistroEstudo registro) {
        return registroEstudoService.salvar(registro);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        registroEstudoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}