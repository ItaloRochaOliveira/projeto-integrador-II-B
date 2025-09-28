package service.repository;

import java.util.ArrayList;
import java.util.Arrays;
import java.sql.Date;
import java.util.List;
import java.util.stream.Collectors;

import db.mock.UserDataMock;

public class UserRepositoryMock {
    private List<String> allowedFields = List.of("id", "name", "email");
    
    private static final List<UserDataMock> USERS_LIST = new ArrayList<>(Arrays.asList(UserDataMock.USERS));

    
    public List<UserDataMock> get() {
        return new ArrayList<>(USERS_LIST);
    }

    
    public List<String> get(String field) {
        if (!allowedFields.contains(field)) {
            throw new IllegalArgumentException("Campo não permitido: " + field);
        }

        return USERS_LIST.stream()
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

    
    public List<UserDataMock> getBy(String field, int value) {
        if (!allowedFields.contains(field)) {
            throw new IllegalArgumentException("Campo não permitido: " + field);
        }

        return USERS_LIST.stream()
                .filter(u -> "id".equals(field) && u.getUser().getId() == value)
                .collect(Collectors.toList());
    }

    
    public UserDataMock getBy(int id) {
        for (UserDataMock u : USERS_LIST) {
            if (u.getUser().getId() == id) return u;
        }
        return null;
    }

    
    public void create(String name, String email) {
        System.out.println("Simulando criação do usuário: " + name + " <" + email + ">");
    }

    
    public UserDataMock create(String name, String email, String passwordHash, String role) {
        int id = getNextId();
        Date now = new Date(System.currentTimeMillis());
        UserDataMock novo = new UserDataMock(id, name, email, null, passwordHash, now, now, null, role);
        USERS_LIST.add(novo);
        return novo;
    }

    
    public void update(int id, String name, String email) {
        System.out.println("Simulando update do usuário ID=" + id + " para name=" + name + ", email=" + email);
        UserDataMock u = getBy(id);
        if (u != null) {
            
        }
    }

    
    public void delete(int id) {
        System.out.println("Simulando deleção do usuário ID=" + id);
        USERS_LIST.removeIf(u -> u.getUser().getId() == id);
    }

    public int getNextId() {
        int max = 0;
        for (UserDataMock u : USERS_LIST) {
            if (u.getUser().getId() > max) max = u.getUser().getId();
        }
        return max + 1;
    }
}