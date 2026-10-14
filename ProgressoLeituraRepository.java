package com.academicrace.api.repository;

import com.academicrace.api.model.ProgressoLeitura;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgressoLeituraRepository extends JpaRepository<ProgressoLeitura, Long> {
    List<ProgressoLeitura> findByUsuarioId(Long usuarioId);
    List<ProgressoLeitura> findByLivroId(Long livroId);
}