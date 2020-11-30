package Entidades;

public class TransitionAFP {

    private String q0;
    private char character;
    private String symbol;
    private String nextState;
    private String[] finalStates;

    public TransitionAFP() {
        this.q0 = null;
        this.character = '0';
        this.finalStates = null;
    }
    
    public TransitionAFP(String q0, char character, String[] finalState) {
        this.q0 = q0;
        this.character = character;
        this.finalStates = finalStates;
    }
    
    public TransitionAFP(String q0, String symbol, String nextState) {
        this.q0 = q0;
        this.symbol = symbol;
        this.nextState = nextState;
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
