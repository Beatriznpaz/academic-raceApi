package com.academicrace.api.repository;

import com.academicrace.api.model.AvaliacaoLivro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface AvaliacaoLivroRepository extends JpaRepository<AvaliacaoLivro, Long> {
    List<AvaliacaoLivro> findByLivroId(Long livroId);
    Optional<AvaliacaoLivro> findByUsuarioIdAndLivroId(Long usuarioId, Long livroId);
}