package Entidades;

public class TransitionAFPN {

    private String q0;
    private char character;
    private char pilaIn;                //Caracter de pila como argumento (Busqueda)
    private String[] finalStates;
    private char pilaOut;               //Caracter de pila Resultante en la Pila del AFPN  (Insercion)

    public TransitionAFPN() {
        this.q0 = null;     //Estado Inicial
        this.character = '0';   //Caracter con el que cambia
        this.finalStates = null;    //Estados Finales []
    }

    public TransitionAFPN(String q0, char character, char pilaIn, String[] finalStates, char pilaOut) {
        this.q0 = q0;
        this.character = character;
        this.pilaIn = pilaIn;
        this.pilaOut = pilaOut;
        this.finalStates = finalStates;
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

    public String[] getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(String[] finalStates) {
        this.finalStates = finalStates;
    }

    public String getQ0() {
        return q0;
    }

    public void setQ0(String q0) {
        this.q0 = q0;
    }

    public char getPilaIn() {
        return pilaIn;
    }

    public void setPilaIn(char pilaIn) {
        this.pilaIn = pilaIn;
    }

    public char getPilaOut() {
        return pilaOut;
    }

    public void setPilaOut(char pilaOut) {
        this.pilaOut = pilaOut;
    }

}
