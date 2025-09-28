package service.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import db.mock.PontoColetaDataMock;

public class PontoColetaRepositoryMock {
    private List<String> allowedFields = List.of("id", "nome", "endereco", "cidade", "estado", "tiposResiduos");
    private static final List<PontoColetaDataMock> PONTOS_LIST = new ArrayList<>(Arrays.asList(PontoColetaDataMock.PONTOS_COLETA));

    
    public List<PontoColetaDataMock> get() {
        return new ArrayList<>(PONTOS_LIST);
    }

    
    public List<String> get(String field) {
        if (!allowedFields.contains(field)) {
            throw new IllegalArgumentException("Campo não permitido: " + field);
        }

        return PONTOS_LIST.stream()
                .map(p -> {
                    if ("id".equals(field)) {
                        return String.valueOf(p.getPontoColeta().getId());
                    } else if ("nome".equals(field)) {
                        return p.getPontoColeta().getNome();
                    } else if ("endereco".equals(field)) {
                        return p.getPontoColeta().getEndereco();
                    } else if ("cidade".equals(field)) {
                        return p.getPontoColeta().getCidade();
                    } else if ("estado".equals(field)) {
                        return p.getPontoColeta().getEstado();
                    } else if ("tiposResiduos".equals(field)) {
                        return String.join(",", p.getPontoColeta().getTiposResiduos());
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    
    public List<PontoColetaDataMock> getBy(String field, int value) {
        if (!allowedFields.contains(field)) {
            throw new IllegalArgumentException("Campo não permitido: " + field);
        }

        return PONTOS_LIST.stream()
                .filter(p -> "id".equals(field) && p.getPontoColeta().getId() == value)
                .collect(Collectors.toList());
    }

    
    public PontoColetaDataMock getBy(int id) {
        for (PontoColetaDataMock p : PONTOS_LIST) {
            if (p.getPontoColeta().getId() == id) return p;
        }
        return null;
    }

    
    public List<PontoColetaDataMock> getByTipoResiduo(String tipoResiduo) {
        return PONTOS_LIST.stream()
                .filter(p -> p.getPontoColeta().getTiposResiduos().contains(tipoResiduo.toLowerCase()))
                .filter(p -> p.getPontoColeta().isAtivo())
                .collect(Collectors.toList());
    }

    
    public List<String> getTiposResiduosDisponiveis() {
        return new ArrayList<>(PontoColetaDataMock.TIPOS_RESIDUOS_DISPONIVEIS);
    }

    
    public void create(String nome, String endereco, String cidade, String estado, String horarioFuncionamento, 
                      List<String> tiposResiduos, double latitude, double longitude) {
        int id = getNextId();
        long now = System.currentTimeMillis();
        PontoColetaDataMock novo = new PontoColetaDataMock(id, nome, endereco, cidade, estado, horarioFuncionamento,
                tiposResiduos, latitude, longitude, new Date(now), new Date(now), null, true);
        PONTOS_LIST.add(novo);
    }

    
    public void update(int id, String nome, String endereco, String cidade, String estado, String horarioFuncionamento,
                      List<String> tiposResiduos, double latitude, double longitude) {
        for (int i = 0; i < PONTOS_LIST.size(); i++) {
            if (PONTOS_LIST.get(i).getPontoColeta().getId() == id) {
                long now = System.currentTimeMillis();
                PontoColetaDataMock atualizado = new PontoColetaDataMock(id, nome, endereco, cidade, estado, horarioFuncionamento,
                        tiposResiduos, latitude, longitude, new Date(now), new Date(now), null, true);
                PONTOS_LIST.set(i, atualizado);
                return;
            }
        }
    }

    
    public void delete(int id) {
        PONTOS_LIST.removeIf(p -> p.getPontoColeta().getId() == id);
    }

    
    public void softDelete(int id) {
        // Sem setter para ativo; manter como operação no-op no mock
    }

    private int getNextId() {
        int max = 0;
        for (PontoColetaDataMock p : PONTOS_LIST) {
            if (p.getPontoColeta().getId() > max) max = p.getPontoColeta().getId();
        }
        return max + 1;
    }
}
