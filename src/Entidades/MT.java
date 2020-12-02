package Entidades;

import java.util.ArrayList;

/**
 * Super class for turing machines
 */
public class MT {
    
    public ArrayList<String> Q; // Conjunto de estados
    public String q0; // Estado inicial
    public ArrayList<String> F; // Conjunto de estados de aceptación
    public ArrayList<String> Sigma; // Alfabeto de cinta
    public ArrayList<String> Gamma; // Alfabeto de pila
    
    public MT(){}
    
    public ArrayList<String> getQ(){
        return Q;
    }
    
    public void setQ(ArrayList<String> Q){
        this.Q = Q;
    }
    
    public String getq0(){
        return q0;
    }
    
    public void setq0(String q0){
        this.q0 = q0;
    }
    
    public ArrayList<String> getF(){
        return F;
    }
    
    public void setF(ArrayList<String> F){
        this.F = F;
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
