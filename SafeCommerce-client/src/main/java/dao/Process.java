package dao;

import java.time.LocalDateTime;

public class Process {
    Integer fkServidor;
    Integer pid;
    String nome;
    Double usoCpu;
    String situacaoCpu;
    Double usoRam;
    String situacaoRam;

    public Process(Integer fkServidor, Integer pid, String nome, Double usoCpu, String situacaoCpu, Double usoRam, String situacaoRam) {
        this.fkServidor = fkServidor;
        this.pid = pid;
        this.nome = nome;
        this.usoCpu = usoCpu;
        this.situacaoCpu = situacaoCpu;
        this.usoRam = usoRam;
        this.situacaoRam = situacaoRam;
    }

    public Integer getFkServidor() {
        return fkServidor;
    }

    public void setFkServidor(Integer fkServidor) {
        this.fkServidor = fkServidor;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getUsoCpu() {
        return usoCpu;
    }

    public void setUsoCpu(Double usoCpu) {
        this.usoCpu = usoCpu;
    }

    public String getSituacaoCpu() {
        return situacaoCpu;
    }

    public void setSituacaoCpu(String situacaoCpu) {
        this.situacaoCpu = situacaoCpu;
    }

    public Double getUsoRam() {
        return usoRam;
    }

    public void setUsoRam(Double usoRam) {
        this.usoRam = usoRam;
    }

    public String getSituacaoRam() {
        return situacaoRam;
    }

    public void setSituacaoRam(String situacaoRam) {
        this.situacaoRam = situacaoRam;
    }
}
