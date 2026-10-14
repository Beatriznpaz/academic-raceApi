package com.academicrace.api.service;

import com.academicrace.api.model.EstanteLivro;
import com.academicrace.api.model.EstanteLivro.StatusLeitura;
import com.academicrace.api.repository.EstanteLivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EstanteLivroService {

    @Autowired
    private EstanteLivroRepository estanteLivroRepository;

    public List<EstanteLivro> listarPorUsuario(Long usuarioId) {
        return estanteLivroRepository.findByUsuarioId(usuarioId);
    }

    public List<EstanteLivro> listarPorUsuarioEStatus(Long usuarioId, StatusLeitura status) {
        return estanteLivroRepository.findByUsuarioIdAndStatus(usuarioId, status);
    }

    public EstanteLivro salvar(EstanteLivro estante) {
        return estanteLivroRepository.save(estante);
    }

    public void deletar(Long id) {
        estanteLivroRepository.deleteById(id);
    }
}