package controller;

import java.util.List;
import java.util.Arrays;

import models.APIConection.Handler;
import models.APIConection.Request;
import models.APIConection.Response;
import service.PontoColetaService;
import db.mock.PontoColetaDataMock;
import common.JWTUtil;

public class PontoColetaController {
    PontoColetaService pontoColetaService;

    public PontoColetaController(PontoColetaService pontoColetaService) {
        this.pontoColetaService = pontoColetaService;
    }

    
    public Handler get() {
        return (Request req, Response res) -> {
            try {
                if (!isAuthorized(req, res)) return;
                String[] partes = req.path.split("/");
                if (partes.length == 3) { 
                    if (partes[2].isEmpty()) throw new Exception("ID do ponto de coleta não informado");
                    int id = Integer.parseInt(partes[2]);
                    res.send(200, toJSON(java.util.Collections.singletonList(pontoColetaService.get(id))));
                } else {
                    res.send(200, toJSON(pontoColetaService.getAll()));
                }
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"" + e.getMessage() + "\"}");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    }

    
    public Handler getByTipoResiduo() {
        System.out.println("getByTipoResiduo");
        return (Request req, Response res) -> {
            try {
                if (!isAuthorized(req, res)) return;
                String[] partes = req.path.split("/");
                if (partes.length < 4) throw new Exception("Tipo de resíduo não informado");
                String tipoResiduo = partes[3]; 
                List<PontoColetaDataMock> pontos = pontoColetaService.getByTipoResiduo(tipoResiduo);
                res.send(200, toJSON(pontos));
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"" + e.getMessage() + "\"}");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    }

    
    public Handler getTiposResiduos() {
        return (Request req, Response res) -> {
            try {
                if (!isAuthorized(req, res)) return;
                List<String> tipos = pontoColetaService.getTiposResiduosDisponiveis();
                res.send(200, toJSONStringList(tipos));
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"" + e.getMessage() + "\"}");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    }

    
    public Handler post() {
        return (Request req, Response res) -> {
            try {
                if (!isAuthorized(req, res)) return;
                String body = req.body;
                String nome = extractJsonString(body, "nome");
                String endereco = extractJsonString(body, "endereco");
                String cidade = extractJsonString(body, "cidade");
                String estado = extractJsonString(body, "estado");
                String horarioFuncionamento = extractJsonString(body, "horarioFuncionamento");
                String tiposResiduosStr = extractJsonString(body, "tiposResiduos");
                double latitude = extractJsonDouble(body, "latitude");
                double longitude = extractJsonDouble(body, "longitude");

                
                List<String> tiposResiduos = Arrays.asList(tiposResiduosStr.split(","));
                
                pontoColetaService.post(nome, endereco, cidade, estado, horarioFuncionamento, tiposResiduos, latitude, longitude);
                res.send(201, "{\"message\": \"Ponto de coleta criado com sucesso\"}");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"" + e.getMessage() + "\"}");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    }

    
    public Handler put() {
        return (Request req, Response res) -> {
            try {
                if (!isAuthorized(req, res)) return;
                String[] partes = req.path.split("/");
                if (partes.length < 3) throw new Exception("Caminho inválido");
                int pontoId = Integer.parseInt(partes[2]);
                
                String body = req.body;
                String nome = extractJsonString(body, "nome");
                String endereco = extractJsonString(body, "endereco");
                String cidade = extractJsonString(body, "cidade");
                String estado = extractJsonString(body, "estado");
                String horarioFuncionamento = extractJsonString(body, "horarioFuncionamento");
                String tiposResiduosStr = extractJsonString(body, "tiposResiduos");
                double latitude = extractJsonDouble(body, "latitude");
                double longitude = extractJsonDouble(body, "longitude");

                
                List<String> tiposResiduos = Arrays.asList(tiposResiduosStr.split(","));
                
                pontoColetaService.put(pontoId, nome, endereco, cidade, estado, horarioFuncionamento, tiposResiduos, latitude, longitude);
                res.send(200, "{\"message\": \"Ponto de coleta atualizado com sucesso\"}");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"" + e.getMessage() + "\"}");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    }

    
    public Handler delete() {
        return (Request req, Response res) -> {
            try {
                if (!isAuthorized(req, res)) return;
                String[] partes = req.path.split("/");
                if (partes.length < 3) throw new Exception("Caminho inválido");
                int pontoId = Integer.parseInt(partes[2]);
                pontoColetaService.delete(pontoId);
                res.send(200, "{\"message\": \"Ponto de coleta removido com sucesso\"}");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"" + e.getMessage() + "\"}");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    }

    
    private boolean isAuthorized(Request req, Response res) {
        try {
            String auth = null;
            if (req.headers != null) {
                auth = req.headers.get("Authorization");
                if (auth == null) auth = req.headers.get("authorization");
            }
            if (auth == null || !auth.startsWith("Bearer ")) {
                res.send(401, "{\"erro\": \"Token não informado\"}");
                return false;
            }
            String token = auth.substring(7).trim();
            if (!JWTUtil.validateToken(token)) {
                res.send(401, "{\"erro\": \"Token inválido ou expirado\"}");
                return false;
            }
            return true;
        } catch (Exception e) {
            try {
                res.send(401, "{\"erro\": \"Falha na autenticação\"}");
            } catch (java.io.IOException ioException) {
                ioException.printStackTrace();
            }
            return false;
        }
    }

    
    public static String toJSON(List<?> lista) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < lista.size(); i++) {
            Object item = lista.get(i);
            if (item == null) {
                sb.append("null");
            } else if (item instanceof PontoColetaDataMock) {
                PontoColetaDataMock ponto = (PontoColetaDataMock) item;
                sb.append(String.format(
                    "{\"id\":%d,\"nome\":\"%s\",\"endereco\":\"%s\",\"cidade\":\"%s\",\"estado\":\"%s\",\"horarioFuncionamento\":\"%s\",\"tiposResiduos\":[%s],\"latitude\":%.6f,\"longitude\":%.6f,\"ativo\":%b}",
                    ponto.getPontoColeta().getId(),
                    ponto.getPontoColeta().getNome(),
                    ponto.getPontoColeta().getEndereco(),
                    ponto.getPontoColeta().getCidade(),
                    ponto.getPontoColeta().getEstado(),
                    ponto.getPontoColeta().getHorarioFuncionamento(),
                    ponto.getPontoColeta().getTiposResiduos().stream()
                        .map(tipo -> "\"" + tipo + "\"")
                        .reduce((a, b) -> a + "," + b)
                        .orElse(""),
                    ponto.getPontoColeta().getLatitude(),
                    ponto.getPontoColeta().getLongitude(),
                    ponto.getPontoColeta().isAtivo()
                ));
            } else {
                sb.append('\"').append(item.toString().replace("\"", "\"")).append('\"');
            }
            if (i < lista.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    
    public static String toJSONStringList(List<String> lista) {
        StringBuilder sb = new StringBuilder("[");
        for (int i = 0; i < lista.size(); i++) {
            sb.append('\"').append(lista.get(i)).append('\"');
            if (i < lista.size() - 1) sb.append(",");
        }
        sb.append("]");
        return sb.toString();
    }

    
    private String extractJsonString(String json, String key) {
        String[] parts = json.split("\"" + key + "\"\\s*:\\s*\"");
        if (parts.length < 2) return null;
        return parts[1].split("\"")[0];
    }

    
    private double extractJsonDouble(String json, String key) {
        String[] parts = json.split("\"" + key + "\"\\s*:\\s*");
        if (parts.length < 2) return 0.0;
        String valueStr = parts[1].split("[,}]")[0].trim();
        return Double.parseDouble(valueStr);
    }
}
