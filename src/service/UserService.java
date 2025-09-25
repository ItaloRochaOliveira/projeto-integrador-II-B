package service;

import models.Exceptions.BadRequestException;
import models.Exceptions.NotFoundException;
import service.repository.UserRepositoryMock;
import db.enitites.User;
import db.mock.UserDataMock;

import java.util.List;

public class UserService{
    UserRepositoryMock userRepository;

    public UserService(UserRepositoryMock userRepository){
        this.userRepository = userRepository;
    }

    public UserDataMock get(int id) throws NotFoundException {
        UserDataMock result = userRepository.getBy(id);
        if (result == null) {
            throw new NotFoundException("Usuário não encontrado para o id: " + id);
        }
        return result;
    }

    public List<UserDataMock> getAll() {
        return userRepository.get();
    }

    public void post(String name, String email) throws BadRequestException {
        if (name == null || name.isBlank() || email == null || email.isBlank()) {
            throw new BadRequestException("name e email são obrigatórios");
        }
        userRepository.create(name, email);
    }

    public void put(int id, String name, String email) throws BadRequestException, NotFoundException {
        if (id <= 0) {
            throw new BadRequestException("ID inválido");
        }
        if (name == null || name.isBlank() || email == null || email.isBlank()) {
            throw new BadRequestException("Nome e email são obrigatórios");
        }
        UserDataMock existing = userRepository.getBy(id);
        if (existing == null) {
            throw new NotFoundException("Usuário não encontrado para o id: " + id);
        }
        userRepository.update(id, name, email);
    }

    public void delete(int id) throws BadRequestException, NotFoundException {
        if (id <= 0) {
            throw new BadRequestException("ID inválido");
        }
        UserDataMock existing = userRepository.getBy(id);
        if (existing == null) {
            throw new NotFoundException("Usuário não encontrado para o id: " + id);
        }
        userRepository.delete(id);
    }
}