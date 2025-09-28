package service;

import models.Exceptions.BadRequestException;
import models.Exceptions.NotFoundException;
import service.repository.PontoColetaRepositoryMock;
import db.mock.PontoColetaDataMock;

import java.util.List;
import java.util.Arrays;

public class PontoColetaService {
    PontoColetaRepositoryMock pontoColetaRepository;

    public PontoColetaService(PontoColetaRepositoryMock pontoColetaRepository) {
        this.pontoColetaRepository = pontoColetaRepository;
    }

    
    public PontoColetaDataMock get(int id) throws NotFoundException {
        PontoColetaDataMock result = pontoColetaRepository.getBy(id);
        if (result == null) {
            throw new NotFoundException("Ponto de coleta não encontrado para o id: " + id);
        }
        return result;
    }

    
    public List<PontoColetaDataMock> getAll() {
        return pontoColetaRepository.get();
    }

    
    public List<PontoColetaDataMock> getByTipoResiduo(String tipoResiduo) throws BadRequestException {
        if (tipoResiduo == null || tipoResiduo.isBlank()) {
            throw new BadRequestException("Tipo de resíduo é obrigatório");
        }
        
        
        List<String> tiposValidos = pontoColetaRepository.getTiposResiduosDisponiveis();
        if (!tiposValidos.contains(tipoResiduo.toLowerCase())) {
            throw new BadRequestException("Tipo de resíduo inválido. Tipos válidos: " + 
                                        String.join(", ", tiposValidos));
        }
        
        return pontoColetaRepository.getByTipoResiduo(tipoResiduo);
    }

    
    public List<String> getTiposResiduosDisponiveis() {
        return pontoColetaRepository.getTiposResiduosDisponiveis();
    }

    
    public void post(String nome, String endereco, String cidade, String estado, String horarioFuncionamento, 
                    List<String> tiposResiduos, double latitude, double longitude) throws BadRequestException {
        
        if (nome == null || nome.isBlank()) {
            throw new BadRequestException("Nome é obrigatório");
        }
        if (endereco == null || endereco.isBlank()) {
            throw new BadRequestException("Endereço é obrigatório");
        }
        if (cidade == null || cidade.isBlank()) {
            throw new BadRequestException("Cidade é obrigatória");
        }
        if (estado == null || estado.isBlank() || estado.length() != 2) {
            throw new BadRequestException("Estado (UF) é obrigatório e deve ter 2 letras");
        }
        if (tiposResiduos == null || tiposResiduos.isEmpty()) {
            throw new BadRequestException("Tipos de resíduos são obrigatórios");
        }

        
        List<String> tiposValidos = pontoColetaRepository.getTiposResiduosDisponiveis();
        for (String tipo : tiposResiduos) {
            if (!tiposValidos.contains(tipo.toLowerCase())) {
                throw new BadRequestException("Tipo de resíduo inválido: " + tipo + 
                                            ". Tipos válidos: " + String.join(", ", tiposValidos));
            }
        }

        
        if (latitude < -90 || latitude > 90) {
            throw new BadRequestException("Latitude deve estar entre -90 e 90");
        }
        if (longitude < -180 || longitude > 180) {
            throw new BadRequestException("Longitude deve estar entre -180 e 180");
        }

        pontoColetaRepository.create(nome, endereco, cidade, estado.toUpperCase(), horarioFuncionamento, tiposResiduos, latitude, longitude);
    }

    
    public void put(int id, String nome, String endereco, String cidade, String estado, String horarioFuncionamento,
                   List<String> tiposResiduos, double latitude, double longitude) 
                   throws BadRequestException, NotFoundException {
        if (id <= 0) {
            throw new BadRequestException("ID inválido");
        }

        
        PontoColetaDataMock existing = pontoColetaRepository.getBy(id);
        if (existing == null) {
            throw new NotFoundException("Ponto de coleta não encontrado para o id: " + id);
        }

        
        if (nome == null || nome.isBlank()) {
            throw new BadRequestException("Nome é obrigatório");
        }
        if (endereco == null || endereco.isBlank()) {
            throw new BadRequestException("Endereço é obrigatório");
        }
        if (tiposResiduos == null || tiposResiduos.isEmpty()) {
            throw new BadRequestException("Tipos de resíduos são obrigatórios");
        }

        
        List<String> tiposValidos = pontoColetaRepository.getTiposResiduosDisponiveis();
        for (String tipo : tiposResiduos) {
            if (!tiposValidos.contains(tipo.toLowerCase())) {
                throw new BadRequestException("Tipo de resíduo inválido: " + tipo + 
                                            ". Tipos válidos: " + String.join(", ", tiposValidos));
            }
        }

        
        if (latitude < -90 || latitude > 90) {
            throw new BadRequestException("Latitude deve estar entre -90 e 90");
        }
        if (longitude < -180 || longitude > 180) {
            throw new BadRequestException("Longitude deve estar entre -180 e 180");
        }

        pontoColetaRepository.update(id, nome, endereco, cidade, estado.toUpperCase(), horarioFuncionamento, tiposResiduos, latitude, longitude);
    }

    
    public void delete(int id) throws BadRequestException, NotFoundException {
        if (id <= 0) {
            throw new BadRequestException("ID inválido");
        }
        
        PontoColetaDataMock existing = pontoColetaRepository.getBy(id);
        if (existing == null) {
            throw new NotFoundException("Ponto de coleta não encontrado para o id: " + id);
        }
        
        pontoColetaRepository.delete(id);
    }

    
    public void softDelete(int id) throws BadRequestException, NotFoundException {
        if (id <= 0) {
            throw new BadRequestException("ID inválido");
        }
        
        PontoColetaDataMock existing = pontoColetaRepository.getBy(id);
        if (existing == null) {
            throw new NotFoundException("Ponto de coleta não encontrado para o id: " + id);
        }
        
        pontoColetaRepository.softDelete(id);
    }
}
