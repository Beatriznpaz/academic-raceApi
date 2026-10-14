package com.academicrace.api.controller;

import com.academicrace.api.model.Usuario;
import com.academicrace.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public record PerfilResponse(Long id, String nome, String email, String foto) {
        static PerfilResponse de(Usuario usuario) {
            return new PerfilResponse(usuario.getId(), usuario.getNome(), usuario.getEmail(), usuario.getFoto());
        }
    }

    public record AtualizarPerfilRequest(String nome, String senha, String foto) {
    }

    @GetMapping("/me")
    public PerfilResponse me(Authentication authentication) {
        return PerfilResponse.de(usuarioService.buscarAtual(authentication));
    }

    @PutMapping("/me")
    public PerfilResponse atualizarMe(Authentication authentication, @RequestBody AtualizarPerfilRequest request) {
        Usuario usuario = usuarioService.buscarAtual(authentication);
        usuario.setNome(request.nome());
        if (request.senha() != null && !request.senha().isBlank()) {
            usuario.setSenha(passwordEncoder.encode(request.senha()));
        }
        if (request.foto() != null) {
            usuario.setFoto(request.foto());
        }
        return PerfilResponse.de(usuarioService.salvar(usuario));
    }

    @GetMapping
    public List<Usuario> listarTodos() {
        return usuarioService.listarTodos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> buscarPorId(@PathVariable Long id) {
        return usuarioService.buscarPorId(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Usuario salvar(@RequestBody Usuario usuario) {
        return usuarioService.salvar(usuario);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        usuarioService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}