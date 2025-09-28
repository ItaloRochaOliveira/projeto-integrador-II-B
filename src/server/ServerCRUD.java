package server;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


import models.APIConection.Handler;
import models.APIConection.Request;
import models.APIConection.Response;

public class ServerCRUD {
    private Map<String, Handler> rotasGet = new HashMap<>();
    private Map<String, Handler> rotasPost = new HashMap<>();
    private Map<String, Handler> rotasPut = new HashMap<>();
    private Map<String, Handler> rotasDelete = new HashMap<>();

    public void start() {
        try (ServerSocket serverSocket = new ServerSocket(8080)) {
            System.out.println("Servidor rodando na porta 8080...");
            while (true) {
                Socket socket = serverSocket.accept();
                tratarRequisicao(socket);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void get(String rota, Handler handler) {
        rotasGet.put(rota, handler);
    }

    public void post(String rota, Handler handler) {
        rotasPost.put(rota, handler);
    }

    public void put(String rota, Handler handler) {
        rotasPut.put(rota, handler);
    }

    public void delete(String rota, Handler handler) {
        rotasDelete.put(rota, handler);
    }

    private void tratarRequisicao(Socket socket) {
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()))) {
            String requestLine = in.readLine();
            if (requestLine == null || requestLine.isEmpty()) return;

            String[] parts = requestLine.split(" ");
            String metodo = parts[0];
            String caminho = parts[1];

            String body = "";
            String linha;
            int contentLength = 0;
            Map<String, String> headers = new HashMap<>();

            while ((linha = in.readLine()) != null && !linha.isEmpty()) {
                int idx = linha.indexOf(":");
                if (idx > 0) {
                    String key = linha.substring(0, idx).trim();
                    String value = linha.substring(idx + 1).trim();
                    headers.put(key, value);
                    if (key.equalsIgnoreCase("content-length")) {
                        try {
                            contentLength = Integer.parseInt(value);
                        } catch (NumberFormatException e) {
                            contentLength = 0;
                        }
                    }
                }
            }

            if (contentLength > 0) {
                char[] buffer = new char[contentLength];
                in.read(buffer, 0, contentLength);
                body = new String(buffer);
            }

            if (metodo.equals("GET")) {
                List<String> rotasOrdenadas = new ArrayList<>(rotasGet.keySet());
                rotasOrdenadas.sort((a, b) -> Integer.compare(b.length(), a.length()));
                for (String rota : rotasOrdenadas) {
                    if (caminho.equals(rota) || caminho.startsWith(rota + "/")) {
                        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
                            Request req = new Request();
                            req.path = caminho;
                            req.method = metodo;
                            req.headers = headers;
                            Response res = new Response(out);
                            Handler handler = rotasGet.get(rota);
                            handler.handle(req, res);
                        }
                        return;
                    }
                }
                try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
                    String resposta = "{\"erro\": \"Rota não encontrada\"}";
                    out.write("HTTP/1.1 404 Not Found\r\n");
                    out.write("Content-Type: application/json; charset=UTF-8\r\n");
                    out.write("Content-Length: " + resposta.getBytes(StandardCharsets.UTF_8).length + "\r\n");
                    out.write("\r\n");
                    out.write(resposta);
                    out.flush();
                }
                return;
            }

            if (metodo.equals("POST")) {
                for (String rota : rotasPost.keySet()) {
                    if (caminho.equals(rota) || caminho.matches(rota + "/\\d+")) {
                        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
                            Request req = new Request();
                            req.path = caminho;
                            req.method = metodo;
                            req.body = body;
                            req.headers = headers;
                            Response res = new Response(out);
                            Handler handler = rotasPost.get(rota);
                            handler.handle(req, res);
                        }
                        return;
                    }
                }
                try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
                    String resposta = "{\"erro\": \"Rota não encontrada\"}";
                    out.write("HTTP/1.1 404 Not Found\r\n");
                    out.write("Content-Type: application/json; charset=UTF-8\r\n");
                    out.write("Content-Length: " + resposta.getBytes(StandardCharsets.UTF_8).length + "\r\n");
                    out.write("\r\n");
                    out.write(resposta);
                    out.flush();
                }
                return;
            }

            if (metodo.equals("PUT")) {
                for (String rota : rotasPut.keySet()) {
                    if (caminho.equals(rota) || caminho.matches(rota + "/\\d+")) {
                        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
                            Request req = new Request();
                            req.path = caminho;
                            req.method = metodo;
                            req.body = body;
                            req.headers = headers;
                            Response res = new Response(out);
                            Handler handler = rotasPut.get(rota);
                            handler.handle(req, res);
                        }
                        return;
                    }
                }
                try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
                    String resposta = "{\"erro\": \"Rota não encontrada\"}";
                    out.write("HTTP/1.1 404 Not Found\r\n");
                    out.write("Content-Type: application/json; charset=UTF-8\r\n");
                    out.write("Content-Length: " + resposta.getBytes(StandardCharsets.UTF_8).length + "\r\n");
                    out.write("\r\n");
                    out.write(resposta);
                    out.flush();
                }
                return;
            }

            if (metodo.equals("DELETE")) {
                for (String rota : rotasDelete.keySet()) {
                    if (caminho.equals(rota) || caminho.matches(rota + "/\\d+")) {
                        try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
                            Request req = new Request();
                            req.path = caminho;
                            req.method = metodo;
                            req.headers = headers;
                            Response res = new Response(out);
                            Handler handler = rotasDelete.get(rota);
                            handler.handle(req, res);
                        }
                        return;
                    }
                }
                try (BufferedWriter out = new BufferedWriter(new OutputStreamWriter(socket.getOutputStream(), StandardCharsets.UTF_8))) {
                    String resposta = "{\"erro\": \"Rota não encontrada\"}";
                    out.write("HTTP/1.1 404 Not Found\r\n");
                    out.write("Content-Type: application/json; charset=UTF-8\r\n");
                    out.write("Content-Length: " + resposta.getBytes(StandardCharsets.UTF_8).length + "\r\n");
                    out.write("\r\n");
                    out.write(resposta);
                    out.flush();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

  
}
