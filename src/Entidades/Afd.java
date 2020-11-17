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
    
    private  ArrayList<String> alfabeto;
    private  ArrayList<String> states = new ArrayList<String>();
    private static String initialState;
    private static ArrayList<String> acceptanceStates;
    private static ArrayList<TransitionAFD> transitions;

    //public Afd(String[] alfabeto, String[] states, String initialState, String[] acceptanceStates, Transition[] transitions) {
//        this.alfabeto = alfabeto;
//        this.states = states;
//        this.initialState = initialState;
//        this.acceptanceStates = acceptanceStates;
//        this.transitions = transitions;
//    }
    
    public Afd(String nombreArchivo) {
        setAtributesGivenAFile(nombreArchivo);
    }
    
    public boolean personalContain(String s1, String s2){
        for(int i = 0; i < s2.length(); i++){
            if(!s1.contains(Character.toString(s2.charAt(i)))){
                return false;
            }
        }
        return true;
    }
    
    public static String getNormalString(String s){
        String realString = "";
        for(int i = 0; i < s.length(); i++){
            if(s.charAt(i) != ' '){
                realString += s.charAt(i);
            }
        }
        return realString;
    }
    
    public boolean isEmpty(){
      return true;  
    }
    
    public boolean setAtributesGivenAFile(String nombreArchivo) {
        boolean state = false;
        String curSection = "WTF";
        String fileName = nombreArchivo + ".dfa";
        String curLine = "";
        System.out.println(fileName);
        try {                              
    BufferedReader myReader=new BufferedReader(new FileReader(fileName));
            //Scanner myReader = new Scanner(new FileReader(fileName));
            while ((curLine = myReader.readLine()) != null) {                                                       
                if(personalContain(curLine,"#alphabet")){                    
                    System.out.println("We are in the alphabet");                                           
                }else if(personalContain(curLine,"#states")){
                    System.out.println("We are in the states");                    
                    curSection = curLine;
                    System.out.println(curSection);
                }else if(personalContain(curLine,"#initial")){
                    System.out.println("We are in the initial");
                    curSection = curLine;
                    System.out.println(curSection);
                }else if(personalContain(curLine,"#accepting")){
                    System.out.println("We are in the accepting");
                    curSection = curLine;
                    System.out.println(curSection);
                }else if(personalContain(curLine,"#transitions")){
                    System.out.println("We are in the transitions");
                    curSection = curLine;
                    System.out.println(curSection);
                }else if(curSection.contains(" #alphabet") && curLine.length()!=1){
                    
                }else if(curSection.contains(" # s t a t e s ") && curLine.length()!=1){
                    System.out.println(getNormalString(curLine).length() + curLine) ;
                    states.add(curLine);
                }else if(curSection.contains(" #initial") && curLine.length()!=1){
                    initialState = curLine;
                }else if(curSection.contains(" #accepting") && curLine.length()!=1){
                    acceptanceStates.add(curLine);
                }else if(curSection.contains(" #transitions") && curLine.length()!=1){
                    
                }
                System.out.println(curLine + " "+curLine.length());
                
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
        for (int j = 0; j < acceptanceStates.size(); j++) {
            if (currentState.equals(acceptanceStates.get(j))) {
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
            for (int k = 0; k < acceptanceStates.size(); k++) {
                if (currentState.equals(acceptanceStates.get(j))) {
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
        
    
    public ArrayList<String> getAlfabeto() {
        return alfabeto;
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
    
    public static void procesarListaCadenas(String cadena){

    }        

    @Override
    public String toString() {
        return "Afd{" + "alfabeto=" + alfabeto + ", states=" + states + ", initialState=" + initialState + ", acceptanceStates=" + acceptanceStates + ", transitions=" + transitions + '}';
    }      

    public static void main(String[] args){
        Afd afd = new Afd("afd");
//        Scanner myReader = new Scanner(System.in);
//        String curLine = "";
//        String curSection = "";
//        while (myReader.hasNextLine()) {
//            curLine = myReader.nextLine();
//            if (curLine.equals("#alphabet")) {
//                System.out.println("We are in the alphabet");
//            } else if (curLine.equals("#states")) {
//                System.out.println("We are in the states");
//                curSection = curLine;
//                System.out.println(curSection);
//            } else if (curLine.equals("#initial")) {
//                System.out.println("We are in the initial");
//                curSection = curLine;
//                System.out.println(curSection);
//            } else if (curLine.equals("#accepting")) {
//                System.out.println("We are in the accepting");
//                curSection = curLine;
//                System.out.println(curSection);
//            } else if (curLine.equals("#transitions")) {
//                System.out.println("We are in the transitions");
//                curSection = curLine;
//                System.out.println(curSection);
//            } else if (curSection.equals(" #alphabet") && curLine.length() != 0) {
//
//            } else if (curSection.equals(" # s t a t e s ") && curLine.length() != 0) {
//                states.add(getNormalString(curLine));
//            } else if (curSection.equals(" #initial") && curLine.length() != 0) {
//                initialState = curLine;
//            } else if (curSection.equals(" #accepting") && curLine.length() != 0) {
//                acceptanceStates.add(curLine);
//            } else if (curSection.equals(" #transitions") && !curLine.equals("")) {
//                Transition transition = new Transition(curLine.split(";")[1].split(">"));
//                transitions.add(transition);
//                System.out.println("This is what we got" + transition.getInitialState() + transition.getSymbol() + transition.getNextState());
//            }
//            System.out.println(curLine);
//            System.out.println(curSection);            
//        }                
    }
}
