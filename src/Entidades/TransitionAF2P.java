/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author Camilo Vargas
 */
public class TransitionAF2P {

    private String q0;
    private char character;
    private char pilaInA;                //Caracter de pila como argumento (Busqueda)
    private char pilaInB;
    private String finalStates;
    private char pilaOutA;               //Caracter de pila Resultante en la Pila del AFPN  (Insercion)
    private char pilaOutB;

    public TransitionAF2P(String q0, char character, char pilaInA, char pilaInB, String finalStates, char pilaOutA, char pilaOutB) {
        this.q0 = q0;
        this.character = character;
        this.pilaInA = pilaInA;
        this.pilaInB = pilaInB;
        this.finalStates = finalStates;
        this.pilaOutA = pilaOutA;
        this.pilaOutB = pilaOutB;
    }

    public String getq0() {
        return q0;
    }

    public void setq0(String q0) {
        this.q0 = q0;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public String getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(String finalStates) {
        this.finalStates = finalStates;
    }

    public String getQ0() {
        return q0;
    }

    public void setQ0(String q0) {
        this.q0 = q0;
    }

    public char getPilaInA() {
        return pilaInA;
    }

    public void setPilaInA(char pilaInA) {
        this.pilaInA = pilaInA;
    }

    public char getPilaOutA() {
        return pilaOutA;
    }

    public void setPilaOutA(char pilaOutA) {
        this.pilaOutA = pilaOutA;
    }

    public char getPilaInB() {
        return pilaInB;
    }

    public void setPilaInB(char pilaInB) {
        this.pilaInB = pilaInB;
    }

    public char getPilaOutB() {
        return pilaOutB;
    }

    public void setPilaOutB(char pilaOutB) {
        this.pilaOutB = pilaOutB;
    }

}
