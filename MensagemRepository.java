package com.academicrace.api.repository;

import com.academicrace.api.model.Mensagem;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface MensagemRepository extends JpaRepository<Mensagem, Long> {
    List<Mensagem> findByGrupoIdOrderByDataEnvioAsc(Long grupoId);
}