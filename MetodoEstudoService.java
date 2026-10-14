package com.academicrace.api.service;

import com.academicrace.api.model.MetodoEstudo;
import com.academicrace.api.repository.MetodoEstudoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MetodoEstudoService {

    @Autowired
    private MetodoEstudoRepository metodoEstudoRepository;

    public List<MetodoEstudo> listarTodos() {
        return metodoEstudoRepository.findAll();
    }

    public Optional<MetodoEstudo> buscarPorId(Long id) {
        return metodoEstudoRepository.findById(id);
    }

    public MetodoEstudo salvar(MetodoEstudo metodo) {
        return metodoEstudoRepository.save(metodo);
    }

    public void deletar(Long id) {
        metodoEstudoRepository.deleteById(id);
    }
}