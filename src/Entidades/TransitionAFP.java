package Entidades;

public class TransitionAFP {

    private String q0;
    private char character;
    private String[] finalStates;

    public TransitionAFP() {
        this.q0 = null;     //Estado Inicial
        this.character = '0';   //Caracter con el que cambia
        this.finalStates = null;    //Estados Finales []
    }
    
    public TransitionAFP(String q0, char character, String[] finalStates) {
        this.q0 = q0;
        this.character = character;
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
}
