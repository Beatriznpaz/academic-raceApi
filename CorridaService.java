package com.academicrace.api.service;

import com.academicrace.api.model.Grupo;
import com.academicrace.api.model.GrupoMembro;
import com.academicrace.api.model.ProgressoCorrida;
import com.academicrace.api.model.Usuario;
import com.academicrace.api.repository.GrupoMembroRepository;
import com.academicrace.api.repository.GrupoRepository;
import com.academicrace.api.repository.ProgressoCorridaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.security.SecureRandom;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class CorridaService {

    private static final String ALFABETO = "ABCDEFGHJKLMNPQRSTUVWXYZ23456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private GrupoMembroRepository grupoMembroRepository;

    @Autowired
    private ProgressoCorridaRepository progressoCorridaRepository;

    // ---------- Criar / entrar / listar ----------

    public Grupo criarGrupo(Usuario criador, Grupo grupo) {
        grupo.setCodigoConvite(gerarCodigoUnico());
        Grupo salvo = grupoRepository.save(grupo);
        adicionarMembro(salvo, criador);
        return salvo;
    }

    public Grupo entrarPorCodigo(Usuario usuario, String codigo) {
        Grupo grupo = grupoRepository.findByCodigoConvite(codigo == null ? null : codigo.trim().toUpperCase())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Código de convite inválido"));
        if (!ehMembro(grupo.getId(), usuario.getId())) {
            adicionarMembro(grupo, usuario);
        }
        return grupo;
    }

    public List<Grupo> listarGruposDoUsuario(Usuario usuario) {
        return grupoMembroRepository.findByUsuarioId(usuario.getId()).stream()
                .map(GrupoMembro::getGrupo)
                .collect(Collectors.toList());
    }

    // ---------- Progresso ----------

    public ProgressoCorrida adicionarProgresso(Usuario usuario, Long grupoId, Integer tempoMinutos, String observacao) {
        Grupo grupo = buscarGrupoComoMembro(usuario, grupoId);
        ProgressoCorrida progresso = new ProgressoCorrida();
        progresso.setUsuario(usuario);
        progresso.setGrupo(grupo);
        progresso.setTempoEstudado(tempoMinutos);
        progresso.setObservacao(observacao);
        progresso.setData(LocalDateTime.now());
        return progressoCorridaRepository.save(progresso);
    }

    // ---------- Ranking ----------

    public RankingGrupo montarRanking(Usuario atual, Long grupoId) {
        Grupo grupo = buscarGrupoComoMembro(atual, grupoId);
        List<Usuario> membros = grupoMembroRepository.findByGrupoId(grupoId).stream()
                .map(GrupoMembro::getUsuario)
                .collect(Collectors.toList());
        List<ProgressoCorrida> progressos = progressoCorridaRepository.findByGrupoId(grupoId);
        LocalDate hoje = LocalDate.now();

        List<MembroCalculado> calculados = new ArrayList<>();
        for (Usuario membro : membros) {
            List<ProgressoCorrida> doMembro = progressos.stream()
                    .filter(p -> p.getUsuario().getId().equals(membro.getId()))
                    .collect(Collectors.toList());
            int tempoHoje = doMembro.stream()
                    .filter(p -> p.getData() != null && p.getData().toLocalDate().equals(hoje))
                    .mapToInt(p -> p.getTempoEstudado() == null ? 0 : p.getTempoEstudado())
                    .sum();
            int diasConsecutivos = calcularDiasConsecutivos(doMembro, hoje);
            calculados.add(new MembroCalculado(membro.getNome(), membro.getId(), tempoHoje, diasConsecutivos));
        }

        MembroCalculado lider = calculados.stream()
                .max(Comparator.comparingInt((MembroCalculado m) -> m.diasConsecutivos)
                        .thenComparingInt(m -> m.tempoHoje)
                        .thenComparing(m -> m.nome, Comparator.reverseOrder()))
                .orElse(null);

        MembroCalculado voce = calculados.stream()
                .filter(m -> m.usuarioId.equals(atual.getId()))
                .findFirst()
                .orElse(new MembroCalculado(atual.getNome(), atual.getId(), 0, 0));

        calculados.sort(Comparator.comparingInt((MembroCalculado m) -> m.tempoHoje).reversed()
                .thenComparing(m -> m.nome));
        List<LinhaRanking> rankingTempo = new ArrayList<>();
        for (int i = 0; i < calculados.size(); i++) {
            MembroCalculado m = calculados.get(i);
            rankingTempo.add(new LinhaRanking(i + 1, m.nome, m.tempoHoje, m.usuarioId.equals(atual.getId())));
        }

        int metaDiaria = grupo.getMetaDiaria() == null ? 0 : grupo.getMetaDiaria();
        int faltam = Math.max(0, metaDiaria - voce.tempoHoje);
        SuaMeta suaMeta = new SuaMeta(metaDiaria, voce.tempoHoje, faltam);

        return new RankingGrupo(
                grupo.getId(),
                grupo.getNome(),
                lider == null ? null : new Participante(lider.nome, lider.diasConsecutivos),
                new Participante(voce.nome, voce.diasConsecutivos),
                rankingTempo,
                suaMeta
        );
    }

    private int calcularDiasConsecutivos(List<ProgressoCorrida> progressosDoMembro, LocalDate hoje) {
        Set<LocalDate> dias = progressosDoMembro.stream()
                .filter(p -> p.getData() != null)
                .map(p -> p.getData().toLocalDate())
                .collect(Collectors.toSet());
        if (dias.isEmpty()) {
            return 0;
        }
        LocalDate cursor;
        if (dias.contains(hoje)) {
            cursor = hoje;
        } else if (dias.contains(hoje.minusDays(1))) {
            cursor = hoje.minusDays(1);
        } else {
            return 0;
        }
        int total = 0;
        while (dias.contains(cursor)) {
            total++;
            cursor = cursor.minusDays(1);
        }
        return total;
    }

    // ---------- Helpers ----------

    private void adicionarMembro(Grupo grupo, Usuario usuario) {
        GrupoMembro membro = new GrupoMembro();
        membro.setGrupo(grupo);
        membro.setUsuario(usuario);
        grupoMembroRepository.save(membro);
    }

    private boolean ehMembro(Long grupoId, Long usuarioId) {
        return grupoMembroRepository.findByGrupoId(grupoId).stream()
                .anyMatch(m -> m.getUsuario().getId().equals(usuarioId));
    }

    private Grupo buscarGrupoComoMembro(Usuario usuario, Long grupoId) {
        Grupo grupo = grupoRepository.findById(grupoId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Grupo não encontrado"));
        if (!ehMembro(grupoId, usuario.getId())) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Você não participa deste grupo");
        }
        return grupo;
    }

    private String gerarCodigoUnico() {
        for (int tentativa = 0; tentativa < 20; tentativa++) {
            StringBuilder sb = new StringBuilder(6);
            for (int i = 0; i < 6; i++) {
                sb.append(ALFABETO.charAt(RANDOM.nextInt(ALFABETO.length())));
            }
            String codigo = sb.toString();
            if (grupoRepository.findByCodigoConvite(codigo).isEmpty()) {
                return codigo;
            }
        }
        throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, "Não foi possível gerar um código de convite");
    }

    private static class MembroCalculado {
        final String nome;
        final Long usuarioId;
        final int tempoHoje;
        final int diasConsecutivos;

        MembroCalculado(String nome, Long usuarioId, int tempoHoje, int diasConsecutivos) {
            this.nome = nome;
            this.usuarioId = usuarioId;
            this.tempoHoje = tempoHoje;
            this.diasConsecutivos = diasConsecutivos;
        }
    }

    // ---------- DTOs de resposta ----------

    public record Participante(String nome, int diasConsecutivos) {
    }

    public record LinhaRanking(int posicao, String nome, int tempoMinutos, boolean ehVoce) {
    }

    public record SuaMeta(int metaDiariaMinutos, int progressoMinutos, int faltamMinutos) {
    }

    public record RankingGrupo(Long grupoId, String grupoNome, Participante lider, Participante voce,
                               List<LinhaRanking> rankingTempo, SuaMeta suaMeta) {
    }
}
