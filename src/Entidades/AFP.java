package Entidades;

import java.util.ArrayList;

public class AFP extends AF{
    
    public ArrayList<String> Sigma; // Alfabeto de cinta
    public ArrayList<String> Gamma; // Alfabeto de pila
    
    public AFP(ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<String> Sigma, ArrayList<String> Gamma){
        super(Q, q0, F);
        this.Sigma = Sigma;
        this.Gamma = Gamma;
    }
    
    public AFP(){
        super();
    }
    
    public ArrayList<String> getSigma(){
        return Sigma;
    }
    
    public void setSigma(ArrayList<String> Sigma){
        this.Sigma = Sigma;
    }
    
    public ArrayList<String> getGamma(){
        return Gamma;
    }
    
    public void setGamma(ArrayList<String> Gamma){
        this.Gamma = Gamma;
    }
}
