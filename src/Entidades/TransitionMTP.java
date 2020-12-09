package Entidades;

import java.util.ArrayList;

public class TransitionMTP {

    private String initialState;
    private ArrayList<String> tracks;
    private String nextState;
    private String displacement;
    private ArrayList<String> nextTrack;

    public TransitionMTP(String initialState, ArrayList<String> symbol, String nextState, ArrayList<String> nextTrack, String displacement) {
        this.initialState = initialState;
        this.tracks = symbol;
        this.nextState = nextState;
        this.displacement = displacement;
        this.nextTrack = nextTrack;
    }

    public String getInitialState() {
        return initialState;
    }

    public void setInitialState(String initialState) {
        this.initialState = initialState;
    }

    public ArrayList<String> getSymbol() {
        return tracks;
    }

    public void setSymbol(ArrayList<String> symbol) {
        this.tracks = symbol;
    }

    public String getNextState() {
        return nextState;
    }

    public void setNextState(String nextState) {
        this.nextState = nextState;
    }

    public String getDisplacement() {
        return displacement;
    }

    public void setDisplacement(String displacement) {
        this.displacement = displacement;
    }

    public ArrayList<String> getNextTrack() {
        return nextTrack;
    }

    public void setNextTrack(ArrayList<String> nextTrack) {
        this.nextTrack = nextTrack;
    }

}
