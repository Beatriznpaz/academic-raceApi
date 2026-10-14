package com.academicrace.api.repository;

import com.academicrace.api.model.EstanteLivro;
import com.academicrace.api.model.EstanteLivro.StatusLeitura;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface EstanteLivroRepository extends JpaRepository<EstanteLivro, Long> {
    List<EstanteLivro> findByUsuarioId(Long usuarioId);
    List<EstanteLivro> findByUsuarioIdAndStatus(Long usuarioId, StatusLeitura status);
}