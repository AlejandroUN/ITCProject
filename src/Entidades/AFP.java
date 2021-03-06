package Entidades;

import java.util.ArrayList;

public class AFP extends AF{ // Super class af with stack
    
    public ArrayList<String> Sigma = new ArrayList<>(); // Alfabeto de cinta
    public ArrayList<String> Gamma = new ArrayList<>(); // Alfabeto de pila
    
    public AFP(ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<String> Sigma, ArrayList<String> Gamma){
        super(Q, q0, F);
        this.Sigma = Sigma;
        this.Gamma = Gamma;
    }
    
    public AFP(){
        super();
    }
    
    
    public void setSigma(ArrayList<String> Sigma){
        this.Sigma = Sigma;
    }
    public ArrayList<String> getSigma(){
        return Sigma;
    }
    
    public void setGamma(ArrayList<String> Gamma){
        this.Gamma = Gamma;
    }
    
    public ArrayList<String> getGamma(){
        return Gamma;
    }

}
