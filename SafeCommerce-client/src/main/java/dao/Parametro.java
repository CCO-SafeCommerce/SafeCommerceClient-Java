/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author I
 */
public class Parametro {
    private Integer fkMetrica;

    @Override
    public String toString() {
        return "Parametro{" + ", fkMetrica=" + fkMetrica + '}';
    }

    public Integer getFkMetrica() {
        return fkMetrica;
    }

    public void setFkMetrica(Integer fkMetrica) {
        this.fkMetrica = fkMetrica;
    }
    
}
