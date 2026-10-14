package com.academicrace.api.controller;

import com.academicrace.api.model.Mensagem;
import com.academicrace.api.model.Usuario;
import com.academicrace.api.service.MensagemService;
import com.academicrace.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/mensagens")
public class MensagemController {

    @Autowired
    private MensagemService mensagemService;

    @Autowired
    private UsuarioService usuarioService;

    public record MensagemResponse(Long id, Long grupoId, Long usuarioId, String usuarioNome, String usuarioFoto,
                                    String conteudo, LocalDateTime dataEnvio, boolean ehVoce) {
        static MensagemResponse de(Mensagem m, Usuario atual) {
            return new MensagemResponse(m.getId(), m.getGrupo().getId(), m.getUsuario().getId(),
                    m.getUsuario().getNome(), m.getUsuario().getFoto(), m.getConteudo(), m.getDataEnvio(),
                    m.getUsuario().getId().equals(atual.getId()));
        }
    }

    public record NovaMensagemRequest(String conteudo) {
    }

    @GetMapping("/grupo/{grupoId}")
    public List<MensagemResponse> listarPorGrupo(Authentication authentication, @PathVariable Long grupoId) {
        Usuario atual = usuarioService.buscarAtual(authentication);
        return mensagemService.listarPorGrupo(atual, grupoId).stream()
                .map(m -> MensagemResponse.de(m, atual))
                .toList();
    }

    @PostMapping("/grupo/{grupoId}")
    public MensagemResponse enviar(Authentication authentication, @PathVariable Long grupoId,
                                    @RequestBody NovaMensagemRequest request) {
        Usuario atual = usuarioService.buscarAtual(authentication);
        Mensagem mensagem = mensagemService.enviar(atual, grupoId, request.conteudo());
        return MensagemResponse.de(mensagem, atual);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(Authentication authentication, @PathVariable Long id) {
        mensagemService.deletar(usuarioService.buscarAtual(authentication), id);
        return ResponseEntity.noContent().build();
    }
}
