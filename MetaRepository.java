package com.academicrace.api.repository;

import com.academicrace.api.model.Meta;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface MetaRepository extends JpaRepository<Meta, Long> {
    Optional<Meta> findByUsuarioId(Long usuarioId);
}