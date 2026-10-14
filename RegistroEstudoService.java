package com.academicrace.api.service;

import com.academicrace.api.model.RegistroEstudo;
import com.academicrace.api.repository.RegistroEstudoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RegistroEstudoService {

    @Autowired
    private RegistroEstudoRepository registroEstudoRepository;

    public List<RegistroEstudo> listarPorUsuario(Long usuarioId) {
        return registroEstudoRepository.findByUsuarioId(usuarioId);
    }

    public Optional<RegistroEstudo> buscarPorId(Long id) {
        return registroEstudoRepository.findById(id);
    }

    public RegistroEstudo salvar(RegistroEstudo registro) {
        return registroEstudoRepository.save(registro);
    }

    public void deletar(Long id) {
        registroEstudoRepository.deleteById(id);
    }
}