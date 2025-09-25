package service.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import db.mock.UserDataMock;

public class UserRepositoryMock {
    private List<String> allowedFields = List.of("id", "name", "email");

    // Retorna todos
    public List<UserDataMock> get() {
        return new ArrayList<>(Arrays.asList(UserDataMock.USERS));
    }

    // Retorna apenas um campo de todos os usuários
    public List<String> get(String field) {
        if (!allowedFields.contains(field)) {
            throw new IllegalArgumentException("Campo não permitido: " + field);
        }

        return Arrays.stream(UserDataMock.USERS)
                .map(u -> {
                    if ("id".equals(field)) {
                        return String.valueOf(u.getUser().getId());
                    } else if ("name".equals(field)) {
                        return u.getUser().getName();
                    } else if ("email".equals(field)) {
                        return u.getUser().getEmail();
                    }
                    return null;
                })
                .collect(Collectors.toList());
    }

    // Consulta por campo e valor numérico (exemplo: id = 5)
    public List<UserDataMock> getBy(String field, int value) {
        if (!allowedFields.contains(field)) {
            throw new IllegalArgumentException("Campo não permitido: " + field);
        }

        return Arrays.stream(UserDataMock.USERS)
                .filter(u -> "id".equals(field) && u.getUser().getId() == value)
                .collect(Collectors.toList());
    }

    // Consulta por id direto
    public UserDataMock getBy(int id) {
        return Arrays.stream(UserDataMock.USERS)
                .filter(u -> u.getUser().getId() == id)
                .findFirst()
                .orElse(null);
    }

    // Mock de "criação"
    public void create(String name, String email) {
        System.out.println("Simulando criação do usuário: " + name + " <" + email + ">");
    }

    // Mock de "update"
    public void update(int id, String name, String email) {
        System.out.println("Simulando update do usuário ID=" + id + " para name=" + name + ", email=" + email);
    }

    // Mock de "delete"
    public void delete(int id) {
        System.out.println("Simulando deleção do usuário ID=" + id);
    }
}