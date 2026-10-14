package com.academicrace.api.service;

import com.academicrace.api.model.AvaliacaoLivro;
import com.academicrace.api.repository.AvaliacaoLivroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AvaliacaoLivroService {

    @Autowired
    private AvaliacaoLivroRepository avaliacaoLivroRepository;

    public List<AvaliacaoLivro> listarPorLivro(Long livroId) {
        return avaliacaoLivroRepository.findByLivroId(livroId);
    }

    public Optional<AvaliacaoLivro> buscarPorUsuarioELivro(Long usuarioId, Long livroId) {
        return avaliacaoLivroRepository.findByUsuarioIdAndLivroId(usuarioId, livroId);
    }

    public AvaliacaoLivro salvar(AvaliacaoLivro avaliacao) {
        return avaliacaoLivroRepository.save(avaliacao);
    }

    public void deletar(Long id) {
        avaliacaoLivroRepository.deleteById(id);
    }
}