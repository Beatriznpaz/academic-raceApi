package com.academicrace.api.repository;

import com.academicrace.api.model.RegistroEstudo;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface RegistroEstudoRepository extends JpaRepository<RegistroEstudo, Long> {
    List<RegistroEstudo> findByUsuarioId(Long usuarioId);
}