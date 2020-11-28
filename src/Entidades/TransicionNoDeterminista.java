/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

public class TransicionNoDeterminista {

    private String estadoInicial;
    private char caracter;
    private String[] estadosFinales;

    public TransicionNoDeterminista() {
        this.estadoInicial = null;
        this.caracter = '0';
        this.estadosFinales = null;
    }

    public TransicionNoDeterminista(String estadoInicial, char caracter, String[] estadosFinales) {
        this.estadoInicial = estadoInicial;
        this.caracter = caracter;
        this.estadosFinales = estadosFinales;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public char getCaracter() {
        return caracter;
    }

    public void setCaracter(char caracter) {
        this.caracter = caracter;
    }

    public String[] getEstadosFinales() {
        return estadosFinales;
    }

    public void setEstadosFinales(String[] estadosFinales) {
        this.estadosFinales = estadosFinales;
    }
}
