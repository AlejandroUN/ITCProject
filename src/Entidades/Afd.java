/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 *
 * @author aleja
 */
public class Afd {
    
    String[] alfabeto;
    String[] states;
    String initialState;
    String[] acceptanceStates;
    Transition[] transitions;

    public Afd(String[] alfabeto, String[] states, String initialState, String[] acceptanceStates, Transition[] transitions) {
        this.alfabeto = alfabeto;
        this.states = states;
        this.initialState = initialState;
        this.acceptanceStates = acceptanceStates;
        this.transitions = transitions;
    }
    
    public String getNextState(String  currentState, String symbol){
        for(int i = 0; i < transitions.length; i++){
            if(transitions[i].getInitialState().equalsIgnoreCase(currentState) && transitions[i].getSymbol().equalsIgnoreCase(symbol)){
                return transitions[i].getNextState();
            }
        }
        return "";
    }    
    
    public boolean procesarCadena(String cadena) {
        String currentState = initialState;        
        for (int i = 0; i < cadena.length(); i++) {
            currentState = getNextState(currentState, String.valueOf(cadena.charAt(i)));
        }
        for (int j = 0; j < acceptanceStates.length; j++) {
            if (currentState.equals(acceptanceStates[j])) {
                return true;
            }
        }
        return false;
    }

    public String procesarCadenaConDetalles(String cadena) {
        String currentState = initialState;
        String currentString = cadena;
        String process = "(" + currentState + "," + cadena + ")";                
        for (int i = 0; i < cadena.length(); i++) {            
            process += "->(";
            currentState = getNextState(currentState, String.valueOf(currentString.charAt(0)));
            currentString = currentString.substring(i+1);
            if(!currentString.equals("")){                
                process += currentState + "," + currentString + ")";
            }else{
                process += currentState + ",$)";
            }            
            
        }
        process += ">>";
        for (int j = 0; j < acceptanceStates.length; j++) {
            if (currentState.equals(acceptanceStates[j])) {
                process += "accepted";
            }else{
                process += "rejected";
            }
        }
        return process;
    }   
    
    public String procesarListaCadenas(String[] listaCadenas, String nombreArchivo, boolean imprimirPantalla) {
        String process = "";
        for(int j = 0; j < listaCadenas.length; j++){
            String currentState = initialState;
            String cadena = listaCadenas[j];
            String currentString = cadena;
            process += cadena + "\n";
            process += "(" + currentState + "," + cadena + ")";
            for (int i = 0; i < cadena.length(); i++) {
                process += "->(";
                currentState = getNextState(currentState, String.valueOf(currentString.charAt(0)));
                currentString = currentString.substring(i + 1);
                if (!currentString.equals("")) {
                    process += currentState + "," + currentString + ")";
                } else {
                    process += currentState + ",$)";
                }
            }
            process += ">>";
            for (int k = 0; k < acceptanceStates.length; k++) {
                if (currentState.equals(acceptanceStates[j])) {
                    process += "accepted";
                } else {
                    process += "rejected";
                }
            }  
            process += "End of the process \n";
        }        
        try {
            FileWriter myWriter = new FileWriter(nombreArchivo + ".dfa");
            myWriter.write(process);            
            myWriter.close();
            System.out.println("Successfully wrote to the file.");
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        return process;
    }   
        
    
    public String[] getAlfabeto() {
        return alfabeto;
    }

    public String[] getStates() {
        return states;
    }

    public String getInitialState() {
        return initialState;
    }

    public String[] getAcceptanceStates() {
        return acceptanceStates;
    }

    public Transition[] getTransitions() {
        return transitions;
    }    
    
    public static void procesarListaCadenas(String cadena){

    }
    
    

    @Override
    public String toString() {
        return "Afd{" + "alfabeto=" + alfabeto + ", states=" + states + ", initialState=" + initialState + ", acceptanceStates=" + acceptanceStates + ", transitions=" + transitions + '}';
    }
    
    static class Demo implements java.io.Serializable 
{ 
    public String a; 
    public String b; 
  
    // Default constructor 
    public Demo(String a, String b) 
    { 
        this.a = a; 
        this.b = b; 
    } 
  
} 



    public static void main(String[] args) throws IOException{
        try (BufferedReader bf = new BufferedReader(new FileReader("afd.dfa"))) {
            String curLine;    
            String holeThing = "";
            while((curLine = bf.readLine())!= null)
                holeThing += curLine + "\n";                
                bf.close();
            System.out.println(holeThing);    
            System.out.println(holeThing.charAt(0)); 
        }
    }
}
