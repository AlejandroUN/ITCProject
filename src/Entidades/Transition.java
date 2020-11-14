/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

/**
 *
 * @author aleja
 */
public class Transition {
    
    String initialState;
    String symbol;
    String nextState;

    public String getInitialState() {
        return initialState;
    }

    public String getSymbol() {
        return symbol;
    }

    public String getNextState() {
        return nextState;
    }

    public Transition(String initialState, String symbol, String nextState) {
        this.initialState = initialState;
        this.symbol = symbol;
        this.nextState = nextState;
    }
}
