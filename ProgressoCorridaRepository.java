package com.academicrace.api.repository;

import com.academicrace.api.model.ProgressoCorrida;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ProgressoCorridaRepository extends JpaRepository<ProgressoCorrida, Long> {
    List<ProgressoCorrida> findByUsuarioId(Long usuarioId);
    List<ProgressoCorrida> findByGrupoId(Long grupoId);
}