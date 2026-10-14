package com.academicrace.api.service;

import com.academicrace.api.model.ProgressoLeitura;
import com.academicrace.api.repository.ProgressoLeituraRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProgressoLeituraService {

    @Autowired
    private ProgressoLeituraRepository progressoLeituraRepository;

    public List<ProgressoLeitura> listarPorUsuario(Long usuarioId) {
        return progressoLeituraRepository.findByUsuarioId(usuarioId);
    }

    public List<ProgressoLeitura> listarPorLivro(Long livroId) {
        return progressoLeituraRepository.findByLivroId(livroId);
    }

    public ProgressoLeitura salvar(ProgressoLeitura progresso) {
        return progressoLeituraRepository.save(progresso);
    }

    public void deletar(Long id) {
        progressoLeituraRepository.deleteById(id);
    }
}