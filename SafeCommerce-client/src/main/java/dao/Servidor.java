 package dao;

import java.time.LocalDateTime;

public class Servidor {

    private Integer idServidor;
    private String enderecoMac;
    private String modelo;
    private String so;
    private Integer fkEmpresa;
    private LocalDateTime ultimoRegistro;

    private String ipServidor;

    public Servidor(Integer idServidor, String enderecoMac, String modelo, String so, Integer fkEmpresa, LocalDateTime ultimoRegistro, String ipServidor) {
        this.idServidor = idServidor;
        this.enderecoMac = enderecoMac;
        this.modelo = modelo;
        this.so = so;
        this.fkEmpresa = fkEmpresa;
        this.ultimoRegistro = ultimoRegistro;
        this.ipServidor = ipServidor;
    }
    public Servidor(){

    }

    public Integer getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(Integer idServidor) {
        this.idServidor = idServidor;
    }

    public String getEnderecoMac() {
        return enderecoMac;
    }

    public void setEnderecoMac(String enderecoMac) {
        this.enderecoMac = enderecoMac;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getSo() {
        return so;
    }

    public void setSo(String so) {
        this.so = so;
    }

    public Integer getFkEmpresa() {
        return fkEmpresa;
    }

    public void setFkEmpresa(Integer fkEmpresa) {
        this.fkEmpresa = fkEmpresa;
    }

    public LocalDateTime getUltimoRegistro() {
        return ultimoRegistro;
    }

    public void setUltimoRegistro(LocalDateTime ultimoRegistro) {
        this.ultimoRegistro = ultimoRegistro;
    }

    public String getIpServidor() {
        return ipServidor;
    }
    public void setIpServidor(String ipServidor) {
        this.ipServidor = ipServidor;
    }
}
