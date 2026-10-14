package com.academicrace.api.repository;

import com.academicrace.api.model.Streak;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StreakRepository extends JpaRepository<Streak, Long> {
    Optional<Streak> findByUsuarioId(Long usuarioId);
}