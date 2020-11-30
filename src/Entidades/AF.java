package Entidades;

import java.util.ArrayList;

public class AF { // Super class AF
    
    public ArrayList<String> Q; // Conjunto de estados
    public String q0; // Estado inicial
    public ArrayList<String> F; // Conjunto de estados de aceptación
   
    public AF(ArrayList<String> Q, String q0, ArrayList<String> F){
        this.Q = Q;
        this.q0 = q0;
        this.F = F;
    }
    
    public AF(){}
    
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
}
