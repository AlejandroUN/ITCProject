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
public class TransitionMT {
    
    String initialState;
    String symbol;
    String nextState;
    String nextSymbol;
    String displacement;

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setSymbol(String symbol) {
        this.symbol = symbol;
    }

    public String getNextState() {
        return nextState;
    }

    public void setNextState(String nextState) {
        this.nextState = nextState;
    }

    public String getNextSymbol() {
        return nextSymbol;
    }

    public void setNextSymbol(String nextSymbol) {
        this.nextSymbol = nextSymbol;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public TransitionMT(String initialState, String symbol, String nextState, String nextSymbol, String displacement) {
        this.initialState = initialState;
        this.symbol = symbol;
        this.nextState = nextState;
        this.nextSymbol = nextSymbol;
        this.displacement = displacement;
    }
}
