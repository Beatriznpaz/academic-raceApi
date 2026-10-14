package com.academicrace.api.repository;

import com.academicrace.api.model.Grupo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;
import java.util.Optional;

public interface GrupoRepository extends JpaRepository<Grupo, Long> {
    List<Grupo> findByTipo(Grupo.TipoGrupo tipo);
    Optional<Grupo> findByCodigoConvite(String codigoConvite);
}