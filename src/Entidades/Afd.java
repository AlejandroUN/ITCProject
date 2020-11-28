/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aleja
 */
public class Afd {
    
    private  ArrayList<String> setSigma = new ArrayList<String>();
    private  ArrayList<String> states = new ArrayList<String>();
    private static String initialState = "";
    private static ArrayList<String> acceptanceStates = new ArrayList<String>();
    private static ArrayList<TransitionAFD> transitions = new ArrayList<TransitionAFD>();    

    public Afd(ArrayList<String> setSigma, ArrayList<String> states, String initialState, ArrayList<String> acceptanceStates, ArrayList<TransitionAFD> transitions) {
        this.setSigma = setSigma;
        this.states = states;
        this.initialState = initialState;
        this.acceptanceStates = acceptanceStates;
        this.transitions = transitions;
    }
    
    public Afd(String nombreArchivo) {
        setAtributesGivenAFile(nombreArchivo);
    }
    
    public void addToAlphabetFromARange(String range){
        if(((int)range.charAt(0) >= 48) && ((int)range.charAt(0) <= 57)){
            for(int i = Character.getNumericValue(range.charAt(0)); i < Character.getNumericValue(range.charAt(2)) ; i++){                
                setSigma.add(Integer.toString(i));
            }
        }else if(((int)range.charAt(0) >= 65) && ((int)range.charAt(0) <= 90)){
            for(int j = (int)range.charAt(0); j < (int)range.charAt(2)+1 ; j++){                
                String symbol = Character.toString((char)j);                
                setSigma.add(symbol);
            }
        }else if(((int)range.charAt(0) >= 97) && ((int)range.charAt(0) <= 122)){
            for(int k = Character.getNumericValue((int)range.charAt(0)); k < Character.getNumericValue((int)range.charAt(2)) +1; k++){                
                String symbol = Character.toString((char)k);                
                setSigma.add(symbol);
            }
        }
    }
    
    public boolean personalContain(String s1, String s2){
        for(int i = 0; i < s2.length(); i++){
            if(!s1.contains(Character.toString(s2.charAt(i)))){
                return false;
            }
        }
        return true;
    }    
    
    public boolean isEmpty(){
      return true;  
    }
    
    public boolean setAtributesGivenAFile(String nombreArchivo) {
        boolean state = false;
        String curSection = "WTF";
        String fileName = nombreArchivo + ".dfa";
        String curLine = "";                    
        try {                              
            BufferedReader myReader=new BufferedReader(new FileReader(new File(fileName)));            
            while ((curLine = myReader.readLine()) != null) {     
                if(personalContain(curLine,"#alphabet")){               
                    curSection = "#alphabet";                    
                }else if(personalContain(curLine,"#states")){                   
                    curSection = "#states";                    
                }else if(personalContain(curLine,"#initial")){
                    curSection = "#initial";                    
                }else if(personalContain(curLine,"#accepting")){
                    curSection = "#accepting";                    
                }else if(personalContain(curLine,"#transitions")){
                    curSection = "#transitions";                    
                }else if(personalContain(curSection,"#alphabet") && curLine.length()!=0){                    
                    if(curLine.length()!=1){
                        addToAlphabetFromARange(curLine);
                    }else{                        
                        setSigma.add(curLine);
                    }
                }else if(personalContain(curSection,"#states") && curLine.length()!=0){                      
                    states.add(curLine);
                }else if(personalContain(curSection,"#initial") && curLine.length()!=0){                      
                    initialState = curLine;
                }else if(personalContain(curSection,"#accepting") && curLine.length()!=0){                      
                    acceptanceStates.add(curLine);
                }else if(personalContain(curSection,"#transitions") && curLine.length()!=0){                                      
                String initialState = curLine.split(":")[0];
                String symbol = curLine.split(":")[1].split(">")[0];
                String nextState = curLine.split(":")[1].split(">")[1];
                TransitionAFD transition = new TransitionAFD(initialState, symbol, nextState);                
                transitions.add(transition);                
                }                                                
            }            
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(Afd.class.getName()).log(Level.SEVERE, null, ex);
        }
        return state;
    }
    
    
    public String getNextState(String  currentState, String symbol){
        for(int i = 0; i < transitions.size(); i++){
            if(transitions.get(i).getInitialState().equalsIgnoreCase(currentState) && transitions.get(i).getSymbol().equalsIgnoreCase(symbol)){
                return transitions.get(i).getNextState();
            }
        }
        return "";
    }    
    
    public boolean procesarCadena(String cadena) {
        String currentState = initialState;        
        for (int i = 0; i < cadena.length(); i++) {
            currentState = getNextState(currentState, String.valueOf(cadena.charAt(i)));
        }
        for (int j = 0; j < acceptanceStates.size(); j++) {
            if (currentState.equals(acceptanceStates.get(j))) {
                return true;
            }
        }
        return false;
    }

    public boolean procesarCadenaConDetalles(String cadena) {
        boolean state = false;
        String currentState = initialState;
        String currentString = cadena;
        String process = "(" + currentState + "," + cadena + ")";                
        for (int i = 0; i < cadena.length(); i++) {            
            process += "->(";
            currentState = getNextState(currentState, String.valueOf(currentString.charAt(0)));
            if(currentString.length() != 1){
                currentString = currentString.substring(1);
            }else{
                currentString = "";
            }            
            if(!currentString.equals("")){                
                process += currentState + "," + currentString + ")";
            }else{
                process += currentState + ",$)";
            }             
        }
        process += ">>";
        for (int j = 0; j < acceptanceStates.size(); j++) {
            if (currentState.equals(acceptanceStates.get(j))) {
                process += "accepted";
                state = true;
            }else{
                process += "rejected";
                state = false;
            }
        }
        System.out.println(process);
        return state;
    }   
    
    public void procesarListaCadenas(String[] listaCadenas, String nombreArchivo, boolean imprimirPantalla) {
        String process = "";
        for(int j = 0; j < listaCadenas.length; j++){
            String currentState = initialState;
            String cadena = listaCadenas[j];
            String currentString = cadena;
            process += "Cadena: " + cadena + "\t";
            process += "(" + currentState + "," + cadena + ")";
            for (int i = 0; i < cadena.length(); i++) {
                process += "->(";
                currentState = getNextState(currentState, String.valueOf(currentString.charAt(0)));
                if (currentString.length() != 1) {
                    currentString = currentString.substring(1);
                } else {
                    currentString = "";
                }
                if (!currentString.equals("")) {
                    process += currentState + "," + currentString + ")";
                } else {
                    process += currentState + ",$)";
                }
            }
            process += ">>";
            for (int k = 0; k < acceptanceStates.size(); k++) {
                if (currentState.equals(acceptanceStates.get(k))) {
                    process += "accepted\t yes";                    
                } else {
                    process += "rejected\t no";                    
                }
            }
            process += "\n";
        }        
        try {
            if(nombreArchivo.contains(" ")){
                FileWriter myWriter = new FileWriter("default.dfa");
                myWriter.write(process);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            }else{
                FileWriter myWriter = new FileWriter(nombreArchivo + ".dfa");
                myWriter.write(process);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        if(imprimirPantalla){
            System.out.println(process);
        }        
    }   
        
    
    public ArrayList<String> getSigma() {
        return setSigma;
    }

    public ArrayList<String> getStates() {
        return states;
    }

    public String getInitialState() {
        return initialState;
    }

    public ArrayList<String> getAcceptanceStates() {
        return acceptanceStates;
    }

    public ArrayList<TransitionAFD> getTransitions() {
        return transitions;
    }            

    @Override
    public String toString() {
        String afd = "#alphabet\n";
        for(int i = 0; i < setSigma.size(); i++){
            afd += setSigma.get(i) + "\n";
        }
        afd += "#states\n";
        for(int i = 0; i < states.size(); i++){
            afd += states.get(i) + "\n";
        }
        afd += "#initial\n";
        afd += initialState + "\n";
        afd += "#accepting\n";
        for(int i = 0; i < acceptanceStates.size(); i++){
            afd += acceptanceStates.get(i) + "\n";
        }
        afd += "#transitions\n";
        for(int i = 0; i < transitions.size(); i++){
            TransitionAFD transition = transitions.get(i);
            afd += transition.getInitialState() + ":" + transition.getSymbol() + ">" + transition.getNextState() + "\n";
        }
        return afd;
    }          

    public static void main(String[] args){      
        Afd afd = new Afd("afdd");
        String[] cadenas = {"Cd","ABC","ABCd"};
        System.out.println(afd.procesarCadena("Cd"));
        System.out.println(afd.procesarCadena("ABC"));
        System.out.println(afd.procesarCadenaConDetalles("ABC"));
        afd.procesarListaCadenas(cadenas,"EsteEsElOriginalNombreDelArchivo",true);
        System.out.println(afd.toString());
        
    }
}
