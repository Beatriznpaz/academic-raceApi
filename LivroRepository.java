package com.academicrace.api.repository;

import com.academicrace.api.model.Livro;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface LivroRepository extends JpaRepository<Livro, Long> {
    List<Livro> findByAutor(String autor);
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
}