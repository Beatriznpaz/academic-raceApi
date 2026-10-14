package com.academicrace.api.controller;

import com.academicrace.api.model.Grupo;
import com.academicrace.api.service.CorridaService;
import com.academicrace.api.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/grupos")
public class CorridaController {

    @Autowired
    private CorridaService corridaService;

    @Autowired
    private UsuarioService usuarioService;

    public record NovoGrupoRequest(String nome, String descricao, Grupo.TipoGrupo tipo, Integer metaDiaria,
                                   LocalDate dataInicio, LocalDate dataFim) {
    }

    public record EntrarRequest(String codigo) {
    }

    public record NovoProgressoRequest(Integer tempoMinutos, String observacao) {
    }

    public record GrupoResponse(Long id, String nome, String descricao, String tipo, Integer metaDiaria,
                                LocalDate dataInicio, LocalDate dataFim, String codigoConvite) {
        static GrupoResponse de(Grupo g) {
            return new GrupoResponse(g.getId(), g.getNome(), g.getDescricao(),
                    g.getTipo() == null ? null : g.getTipo().name(), g.getMetaDiaria(),
                    g.getDataInicio(), g.getDataFim(), g.getCodigoConvite());
        }
    }

    @GetMapping("/me")
    public List<GrupoResponse> meusGrupos(Authentication authentication) {
        return corridaService.listarGruposDoUsuario(usuarioService.buscarAtual(authentication)).stream()
                .map(GrupoResponse::de)
                .toList();
    }

    @PostMapping("/me")
    public GrupoResponse criar(Authentication authentication, @RequestBody NovoGrupoRequest request) {
        Grupo grupo = new Grupo();
        grupo.setNome(request.nome());
        grupo.setDescricao(request.descricao());
        grupo.setTipo(request.tipo() == null ? Grupo.TipoGrupo.ESTUDO : request.tipo());
        grupo.setMetaDiaria(request.metaDiaria());
        grupo.setDataInicio(request.dataInicio());
        grupo.setDataFim(request.dataFim());
        return GrupoResponse.de(corridaService.criarGrupo(usuarioService.buscarAtual(authentication), grupo));
    }

    @PostMapping("/entrar")
    public GrupoResponse entrar(Authentication authentication, @RequestBody EntrarRequest request) {
        return GrupoResponse.de(
                corridaService.entrarPorCodigo(usuarioService.buscarAtual(authentication), request.codigo()));
    }

    @GetMapping("/{id}/ranking")
    public CorridaService.RankingGrupo ranking(Authentication authentication, @PathVariable Long id) {
        return corridaService.montarRanking(usuarioService.buscarAtual(authentication), id);
    }

    @PostMapping("/{id}/progresso")
    public void adicionarProgresso(Authentication authentication, @PathVariable Long id,
                                   @RequestBody NovoProgressoRequest request) {
        corridaService.adicionarProgresso(usuarioService.buscarAtual(authentication), id,
                request.tempoMinutos(), request.observacao());
    }
}
