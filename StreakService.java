package com.academicrace.api.service;

import com.academicrace.api.model.Streak;
import com.academicrace.api.model.Usuario;
import com.academicrace.api.repository.StreakRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;

@Service
public class StreakService {

    @Autowired
    private StreakRepository streakRepository;

    public Optional<Streak> buscarPorUsuario(Long usuarioId) {
        return streakRepository.findByUsuarioId(usuarioId);
    }

    // Igual buscarPorUsuario, mas primeiro verifica se a sequência foi quebrada
    // (a pessoa pulou um dia sem registrar) e, se sim, zera e já persiste —
    // sem isso o número ficava "congelado" no banco até o próximo registro.
    public Optional<Streak> obterAtualizado(Long usuarioId) {
        Optional<Streak> streakOpt = streakRepository.findByUsuarioId(usuarioId);
        if (streakOpt.isEmpty()) {
            return streakOpt;
        }
        Streak streak = streakOpt.get();
        LocalDate hoje = LocalDate.now();
        LocalDate ultimaData = streak.getUltimaData();
        boolean sequenciaQuebrada = ultimaData != null
                && ultimaData.isBefore(hoje.minusDays(1))
                && streak.getDiasConsecutivos() != 0;
        if (sequenciaQuebrada) {
            streak.setDiasConsecutivos(0);
            streak = streakRepository.save(streak);
        }
        return Optional.of(streak);
    }

    public Streak registrarEstudoHoje(Usuario usuario) {
        LocalDate hoje = LocalDate.now();
        Streak streak = streakRepository.findByUsuarioId(usuario.getId()).orElseGet(() -> {
            Streak novo = new Streak();
            novo.setUsuario(usuario);
            novo.setDiasConsecutivos(0);
            return novo;
        });

        LocalDate ultimaData = streak.getUltimaData();
        if (ultimaData != null && ultimaData.equals(hoje)) {
            // já contou hoje, não faz nada
        } else if (ultimaData != null && ultimaData.equals(hoje.minusDays(1))) {
            streak.setDiasConsecutivos(streak.getDiasConsecutivos() + 1);
            streak.setUltimaData(hoje);
        } else {
            streak.setDiasConsecutivos(1);
            streak.setUltimaData(hoje);
        }

        return streakRepository.save(streak);
    }

    public Streak salvar(Streak streak) {
        return streakRepository.save(streak);
    }

    public void deletar(Long id) {
        streakRepository.deleteById(id);
    }
}