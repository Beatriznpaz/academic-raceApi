package com.academicrace.api.service;

import com.academicrace.api.model.ProgressoCorrida;
import com.academicrace.api.repository.ProgressoCorridaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressoCorridaService {

    @Autowired
    private ProgressoCorridaRepository progressoCorridaRepository;

    public List<ProgressoCorrida> listarPorUsuario(Long usuarioId) {
        return progressoCorridaRepository.findByUsuarioId(usuarioId);
    }

    public List<ProgressoCorrida> listarPorGrupo(Long grupoId) {
        return progressoCorridaRepository.findByGrupoId(grupoId);
    }

    public ProgressoCorrida salvar(ProgressoCorrida progresso) {
        return progressoCorridaRepository.save(progresso);
    }

    public void deletar(Long id) {
        progressoCorridaRepository.deleteById(id);
    }
}