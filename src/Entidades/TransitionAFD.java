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
public class TransitionAFD{
    
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

    public TransitionAFD(String transition[]) {
        this.initialState = transition[0];
        this.symbol = transition[1];
        this.nextState = transition[2];
    }
}
