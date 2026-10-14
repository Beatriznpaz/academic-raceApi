package com.academicrace.api.service;

import com.academicrace.api.model.Grupo;
import com.academicrace.api.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    public List<Grupo> listarTodos() {
        return grupoRepository.findAll();
    }

    public List<Grupo> listarPorTipo(Grupo.TipoGrupo tipo) {
        return grupoRepository.findByTipo(tipo);
    }

    public Optional<Grupo> buscarPorId(Long id) {
        return grupoRepository.findById(id);
    }

    public Grupo salvar(Grupo grupo) {
        return grupoRepository.save(grupo);
    }

    public void deletar(Long id) {
        grupoRepository.deleteById(id);
    }
}