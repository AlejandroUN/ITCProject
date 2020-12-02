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

public class AFD extends AF {
    
    private  ArrayList<String> Sigma = new ArrayList<String>();
    private static ArrayList<TransitionAFD> Delta = new ArrayList<TransitionAFD>();    

    public AFD(ArrayList<String> Sigma, ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<TransitionAFD> Delta) {
        super(Q, q0, F);
        this.Sigma = Sigma;
        this.Delta = Delta;
    }
    
    public AFD(String nombreArchivo) {
        super();
        setAtributesGivenAFile(nombreArchivo);
    }
    
    public void addToAlphabetFromARange(String range){
        if(((int)range.charAt(0) >= 48) && ((int)range.charAt(0) <= 57)){
            for(int i = Character.getNumericValue(range.charAt(0)); i < Character.getNumericValue(range.charAt(2)) ; i++){                
                Sigma.add(Integer.toString(i));
            }
        }else if(((int)range.charAt(0) >= 65) && ((int)range.charAt(0) <= 90)){
            for(int j = (int)range.charAt(0); j < (int)range.charAt(2)+1 ; j++){                
                String symbol = Character.toString((char)j);                
                Sigma.add(symbol);
            }
        }else if(((int)range.charAt(0) >= 97) && ((int)range.charAt(0) <= 122)){
            for(int k = Character.getNumericValue((int)range.charAt(0)); k < Character.getNumericValue((int)range.charAt(2)) +1; k++){                
                String symbol = Character.toString((char)k);                
                Sigma.add(symbol);
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
                        Sigma.add(curLine);
                    }
                }else if(personalContain(curSection,"#states") && curLine.length()!=0){                      
                    Q.add(curLine);
                }else if(personalContain(curSection,"#initial") && curLine.length()!=0){                      
                    q0 = curLine;
                }else if(personalContain(curSection,"#accepting") && curLine.length()!=0){                      
                    F.add(curLine);
                }else if(personalContain(curSection,"#transitions") && curLine.length()!=0){                                      
                String initialState = curLine.split(":")[0];
                String symbol = curLine.split(":")[1].split(">")[0];
                String nextState = curLine.split(":")[1].split(">")[1];
                TransitionAFD transition = new TransitionAFD(initialState, symbol, nextState);                
                Delta.add(transition);                
                }                                                
            }            
            myReader.close();
        } catch (FileNotFoundException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(AFD.class.getName()).log(Level.SEVERE, null, ex);
        }
        return state;
    }
    
    
    public String getNextState(String  currentState, String symbol){
        for(int i = 0; i < Delta.size(); i++){
            if(Delta.get(i).getInitialState().equalsIgnoreCase(currentState) && Delta.get(i).getSymbol().equalsIgnoreCase(symbol)){
                return Delta.get(i).getNextState();
            }
        }
        return "";
    }    
    
    public boolean procesarCadena(String cadena) {
        String currentState = q0;        
        for (int i = 0; i < cadena.length(); i++) {
            currentState = getNextState(currentState, String.valueOf(cadena.charAt(i)));
        }
        for (int j = 0; j < F.size(); j++) {
            if (currentState.equals(F.get(j))) {
                return true;
            }
        }
        return false;
    }

    public boolean procesarCadenaConDetalles(String cadena) {
        boolean state = false;
        String currentState = q0;
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
        for (int j = 0; j < F.size(); j++) {
            if (currentState.equals(F.get(j))) {
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
            String currentState = q0;
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
            for (int k = 0; k < F.size(); k++) {
                if (currentState.equals(F.get(k))) {
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
        return Sigma;
    }
    
    public void setSigma(ArrayList<String> Sigma){
        this.Sigma = Sigma;
    }

    public ArrayList<TransitionAFD> getDelta() {
        return Delta;
    }
    
    public void setDelta(ArrayList<TransitionAFD> Delta){
        this.Delta = Delta;
    }

    @Override
    public String toString() {
        String afd = "#alphabet\n";
        for(int i = 0; i < Sigma.size(); i++){
            afd += Sigma.get(i) + "\n";
        }
        afd += "#states\n";
        for(int i = 0; i < Q.size(); i++){
            afd += Q.get(i) + "\n";
        }
        afd += "#initial\n";
        afd += q0 + "\n";
        afd += "#accepting\n";
        for(int i = 0; i < F.size(); i++){
            afd += F.get(i) + "\n";
        }
        afd += "#transitions\n";
        for(int i = 0; i < Delta.size(); i++){
            TransitionAFD transition = Delta.get(i);
            afd += transition.getInitialState() + ":" + transition.getSymbol() + ">" + transition.getNextState() + "\n";
        }
        return afd;
    }          

}
