package com.academicrace.api.service;

import com.academicrace.api.model.Grupo;
import com.academicrace.api.model.Mensagem;
import com.academicrace.api.model.Usuario;
import com.academicrace.api.repository.GrupoMembroRepository;
import com.academicrace.api.repository.GrupoRepository;
import com.academicrace.api.repository.MensagemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class MensagemService {

    @Autowired
    private MensagemRepository mensagemRepository;

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoMembroRepository grupoMembroRepository;

    public List<Mensagem> listarPorGrupo(Usuario usuario, Long grupoId) {
        buscarGrupoComoMembro(usuario, grupoId);
        return mensagemRepository.findByGrupoIdOrderByDataEnvioAsc(grupoId);
    }

    public Mensagem enviar(Usuario usuario, Long grupoId, String conteudo) {
        if (conteudo == null || conteudo.isBlank()) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "A mensagem não pode ficar vazia");
        }
        Grupo grupo = buscarGrupoComoMembro(usuario, grupoId);
        Mensagem mensagem = new Mensagem();
        mensagem.setGrupo(grupo);
        mensagem.setUsuario(usuario);
        mensagem.setConteudo(conteudo.trim());
        return mensagemRepository.save(mensagem);
    }

    public void deletar(Usuario usuario, Long id) {
        Mensagem mensagem = mensagemRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Mensagem não encontrada"));
        if (!mensagem.getUsuario().getId().equals(usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você só pode apagar suas próprias mensagens");
        }
        mensagemRepository.deleteById(id);
    }

    private Grupo buscarGrupoComoMembro(Usuario usuario, Long grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado"));
        boolean membro = grupoMembroRepository.findByGrupoId(grupoId).stream()
                .anyMatch(m -> m.getUsuario().getId().equals(usuario.getId()));
        if (!membro) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não participa deste grupo");
        }
        return grupo;
    }
}
