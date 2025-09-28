package db.enitites;

import java.sql.Date;
import java.util.List;

public class PontoColeta {
    private int id;
    private String nome;
    private String endereco;
    private String cidade;
    private String estado; 
    private String horarioFuncionamento;
    private List<String> tiposResiduos; 
    private double latitude;
    private double longitude;
    private Date created_at;
    private Date updated_at;
    private Date deleted_at;
    private boolean ativo;

    public PontoColeta(int id, String nome, String endereco, String cidade, String estado, String horarioFuncionamento, 
                      List<String> tiposResiduos, double latitude, double longitude,
                      Date created_at, Date updated_at, Date deleted_at, boolean ativo) {
        this.id = id;
        this.nome = nome;
        this.endereco = endereco;
        this.cidade = cidade;
        this.estado = estado;
        this.horarioFuncionamento = horarioFuncionamento;
        this.tiposResiduos = tiposResiduos;
        this.latitude = latitude;
        this.longitude = longitude;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.ativo = ativo;
    }

    
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHorarioFuncionamento() {
        return horarioFuncionamento;
    }

    public void setHorarioFuncionamento(String horarioFuncionamento) {
        this.horarioFuncionamento = horarioFuncionamento;
    }

    public List<String> getTiposResiduos() {
        return tiposResiduos;
    }

    public void setTiposResiduos(List<String> tiposResiduos) {
        this.tiposResiduos = tiposResiduos;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }

    public Date getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(Date deleted_at) {
        this.deleted_at = deleted_at;
    }

    public boolean isAtivo() {
        return ativo;
    }

    public void setAtivo(boolean ativo) {
        this.ativo = ativo;
    }
}
