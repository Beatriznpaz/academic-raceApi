package com.academicrace.api.controller;

import com.academicrace.api.model.Usuario;
import com.academicrace.api.security.JwtUtil;
import com.academicrace.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/cadastro")
    public ResponseEntity<?> cadastrar(@RequestBody Usuario usuario) {
        if (usuarioService.buscarPorEmail(usuario.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("Email já cadastrado!");
        }
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        Usuario salvo = usuarioService.salvar(usuario);
        return ResponseEntity.ok(salvo);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
        String email = body.get("email");
        String senha = body.get("senha");

        Optional<Usuario> usuario = usuarioService.buscarPorEmail(email);

        if (usuario.isEmpty() || !passwordEncoder.matches(senha, usuario.get().getSenha())) {
            return ResponseEntity.status(401).body("Email ou senha inválidos!");
        }

        String token = jwtUtil.gerarToken(email);
        return ResponseEntity.ok(Map.of("token", token));
    }
}