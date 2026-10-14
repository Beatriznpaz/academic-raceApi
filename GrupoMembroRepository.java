package com.academicrace.api.repository;

import com.academicrace.api.model.GrupoMembro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface GrupoMembroRepository extends JpaRepository<GrupoMembro, Long> {
    List<GrupoMembro> findByGrupoId(Long grupoId);
    List<GrupoMembro> findByUsuarioId(Long usuarioId);
}