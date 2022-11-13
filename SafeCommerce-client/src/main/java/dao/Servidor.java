 package dao;
public class Servidor {
    
    private String idServidor;
    private String enderecoMac;
    private String modelo;
    private String so;

    public Servidor(String idServidor, String enderecoMac, String modelo, String so) {
        this.idServidor = idServidor;
        this.enderecoMac = enderecoMac;
        this.modelo = modelo;
        this.so = so;
    }
    public Servidor(){
        
    }

    public String getIdServidor() {
        return idServidor;
    }

    public void setIdServidor(String idServidor) {
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
}
