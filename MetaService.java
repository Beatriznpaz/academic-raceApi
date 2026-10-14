package com.academicrace.api.service;

import com.academicrace.api.model.Meta;
import com.academicrace.api.repository.MetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MetaService {

    @Autowired
    private MetaRepository metaRepository;

    public Optional<Meta> buscarPorUsuario(Long usuarioId) {
        return metaRepository.findByUsuarioId(usuarioId);
    }

    public Meta salvar(Meta meta) {
        return metaRepository.save(meta);
    }

    public void deletar(Long id) {
        metaRepository.deleteById(id);
    }
}