package com.academicrace.api.service;

import com.academicrace.api.model.GrupoMembro;
import com.academicrace.api.repository.GrupoMembroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GrupoMembroService {

    @Autowired
    private GrupoMembroRepository grupoMembroRepository;

    public List<GrupoMembro> listarPorGrupo(Long grupoId) {
        return grupoMembroRepository.findByGrupoId(grupoId);
    }

    public List<GrupoMembro> listarPorUsuario(Long usuarioId) {
        return grupoMembroRepository.findByUsuarioId(usuarioId);
    }

    public GrupoMembro salvar(GrupoMembro membro) {
        return grupoMembroRepository.save(membro);
    }

    public void deletar(Long id) {
        grupoMembroRepository.deleteById(id);
    }
}