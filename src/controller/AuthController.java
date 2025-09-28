package controller;

import models.APIConection.Handler;
import models.APIConection.Request;
import models.APIConection.Response;
import service.AuthService;
import common.JWTUtil;

public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    
    public Handler login() {
        return (Request req, Response res) -> {
            try {
                String body = req.body;
                String email = extractJsonString(body, "email");
                String password = extractJsonString(body, "senha");
                String token = authService.login(email, password);
                res.send(200, "{\"token\":\"" + token + "\"}");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(401, "{\"erro\": \"" + e.getMessage() + "\"}");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    }

    
    public Handler signup() {
        return (Request req, Response res) -> {
            try {
                String body = req.body;
                String name = extractJsonString(body, "nome");
                String email = extractJsonString(body, "email");
                String password = extractJsonString(body, "senha");
                String role = extractJsonString(body, "role");
                String token = authService.signup(name, email, password, role);
                res.send(201, "{\"token\":\"" + token + "\"}");
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
                String auth = req.headers.get("Authorization");
                if (auth == null) auth = req.headers.get("authorization");
                String token = auth.substring(7).trim();
                JWTUtil.UserInfo info = JWTUtil.extractUserInfo(token);
                if (info == null) throw new Exception("Token inválido");
                int userId = info.userId;
                String body = req.body;
                String name = extractJsonString(body, "nome");
                String email = extractJsonString(body, "email");
                authService.updateUser(userId, name, email);
                res.send(200, "{\"message\": \"Usuário atualizado com sucesso\"}");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"" + e.getMessage() + "\" }");
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
                String auth = req.headers.get("Authorization");
                if (auth == null) auth = req.headers.get("authorization");
                String token = auth.substring(7).trim();
                JWTUtil.UserInfo info = JWTUtil.extractUserInfo(token);
                if (info == null) throw new Exception("Token inválido");
                int userId = info.userId;
                authService.deleteUser(userId);
                res.send(200, "{\"message\": \"Usuário removido com sucesso\"}");
            } catch (Exception e) {
                e.printStackTrace();
                try {
                    res.send(400, "{\"erro\": \"" + e.getMessage() + "\" }");
                } catch (java.io.IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        };
    }

    private String extractJsonString(String json, String key) {
        if (json == null) return null;
        String[] parts = json.split("\"" + key + "\"\\s*:\\s*\"");
        if (parts.length < 2) return null;
        return parts[1].split("\"")[0];
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
}
