package Entidades;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Super class for turing machines
 */
public class MT extends AF{
    
    public ArrayList<String> Sigma = new ArrayList<String>(); // Alfabeto de cinta
    public ArrayList<String> Gamma = new ArrayList<String>(); // Alfabeto de pila  
    public ArrayList<TransitionMT> Delta = new ArrayList<TransitionMT>(); 
    public String tape = "";    //cinta   

    static Path currentRelativePath = Paths.get("");
    static String wPath = currentRelativePath.toAbsolutePath().toString() + File.separator + "Data"  + File.separator + "MT" + File.separator + "writeFolder";
    static Path writePath = Paths.get(wPath);    
    static String rPath = currentRelativePath.toAbsolutePath().toString() + File.separator + "Data"  + File.separator + "MT" + File.separator + "readFolder";
    static Path readPath = Paths.get(rPath);    

    public MT(ArrayList<String> Sigma, ArrayList<String> Gamma, ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<TransitionMT> Delta) {
        super(Q, q0, F);
        this.Sigma = Sigma;
        this.Gamma = Gamma;
        this.Delta = Delta;
        Sigma.add("!");
        Gamma.add("!");
    }

    public MT(String nombreArchivo) {
        setAtributesGivenAFile(nombreArchivo);
        Sigma.add("!");
        Gamma.add("!");
    }
    
    public MT(){
        super();
    }
    
    public void addToAlphabetFromARange(String range){        
        if(((int)range.charAt(0) >= 48) && ((int)range.charAt(0) <= 57)){
            for(int i = Character.getNumericValue(range.charAt(0)); i < Character.getNumericValue(range.charAt(2)) ; i++){                 
                Sigma.add(Integer.toString(i) + "1");
            }
        }else if(((int)range.charAt(0) >= 65) && ((int)range.charAt(0) <= 90)){
            for(int j = (int)range.charAt(0); j < (int)range.charAt(2)+1 ; j++){                
                String symbol = Character.toString((char)j);                  
                Sigma.add(symbol);
            }
        }else if(((int)range.charAt(0) >= 97) && ((int)range.charAt(0) <= 122)){
            for(int j = (int)range.charAt(0); j < (int)range.charAt(2)+1 ; j++){                
                String symbol = Character.toString((char)j);                 
                Sigma.add(symbol);
            }
        }
    }
    
    public void addToTapeAlphabetFromARange(String range){
        if(((int)range.charAt(0) >= 48) && ((int)range.charAt(0) <= 57)){
            for(int i = Character.getNumericValue(range.charAt(0)); i < Character.getNumericValue(range.charAt(2)) ; i++){                
                Gamma.add(Integer.toString(i));
            }
        }else if(((int)range.charAt(0) >= 65) && ((int)range.charAt(0) <= 90)){
            for(int j = (int)range.charAt(0); j < (int)range.charAt(2)+1 ; j++){                
                String symbol = Character.toString((char)j);                
                Gamma.add(symbol);
            }
        }else if(((int)range.charAt(0) >= 97) && ((int)range.charAt(0) <= 122)){
            for(int j = (int)range.charAt(0); j < (int)range.charAt(2)+1 ; j++){  
                String symbol = Character.toString((char)j);                
                Gamma.add(symbol);
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
    
    public boolean setAtributesGivenAFile(String nombreArchivo) {
        boolean state = false;
        String curSection = "WTF";
        String fileName = nombreArchivo + ".tm";
        String curLine = "";                    
        try {                 
            BufferedReader myReader=new BufferedReader(new FileReader(new File(rPath + File.separator +fileName)));            
            while ((curLine = myReader.readLine()) != null) {                 
                if(personalContain(curLine,"#inputAlphabet")){               
                    curSection = "#inputAlphabet";                    
                }else if(personalContain(curLine,"#tapeAlphabet")){               
                    curSection = "#tapeAlphabet";                    
                }else if(personalContain(curLine,"#states")){                   
                    curSection = "#states";                    
                }else if(personalContain(curLine,"#initial")){
                    curSection = "#initial";                    
                }else if(personalContain(curLine,"#accepting")){
                    curSection = "#accepting";                    
                }else if(personalContain(curLine,"#transitions")){
                    curSection = "#transitions";                    
                }else if(personalContain(curSection,"#inputAlphabet") && curLine.length()!=0){                    
                    if(curLine.length()!=1){
                        addToAlphabetFromARange(curLine);
                    }else{                        
                        Sigma.add(curLine);
                    }
                }else if(personalContain(curSection,"#tapeAlphabet") && curLine.length()!=0){                    
                    if(curLine.length()!=1){
                        addToTapeAlphabetFromARange(curLine);
                    }else{                        
                        Gamma.add(curLine);
                    }
                }else if(personalContain(curSection,"#states") && curLine.length()!=0){                      
                    Q.add(curLine);
                }else if(personalContain(curSection,"#initial") && curLine.length()!=0){                      
                    q0 = curLine;
                }else if(personalContain(curSection,"#accepting") && curLine.length()!=0){                      
                    F.add(curLine);
                }else if(personalContain(curSection,"#transitions") && curLine.length()!=0){                                      
                    String[] transitionSections = curLine.split(":", 4);                     
                    String initialState = transitionSections[0];                    
                    String symbol = transitionSections[1].split("\\?")[0];                    
                    String nextState = transitionSections[1].split("\\?")[1];                    
                    String nextSymbol = transitionSections[2];   
                    String displacement = transitionSections[3];   
                    TransitionMT transition = new TransitionMT(initialState, symbol, nextState, nextSymbol, displacement);                    
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
    
    public String[] getNextStepGivenATransition(String  currentState, String symbol){
        String[] nextStep = {"", "", ""};
        for(int i = 0; i < Delta.size(); i++){
            if(Delta.get(i).getInitialState().equals(currentState) && Delta.get(i).getSymbol().equals(symbol)){
                nextStep[0] = Delta.get(i).getNextState();
                nextStep[1] = Delta.get(i).getNextSymbol();
                nextStep[2] = Delta.get(i).getDisplacement();
                return nextStep;
            }
        }
        return nextStep;
    } 
    
    public String replaceChar(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }
    
    public boolean isAnAcceptanceState(String state){
        for (int j = 0; j < F.size(); j++) {
            if (state.equals(F.get(j))) {
                return true;
            }
        }
        return false;
    }
    
    public boolean transitionExists( String state, String symbol){
      for (int i = 0; i < Delta.size(); i++) {
            if (personalContain(Delta.get(i).getInitialState(), state) && personalContain(Delta.get(i).getSymbol(), symbol)) {
                return true;
            }
        } 
      return false;
    }
    
    public boolean procesarCadena(String cadena) {
        String currentState = q0;   
        String nextState = "";
        String desplacement = "";
        int currentStringIndex = 0;
        while (((currentStringIndex) <= cadena.length()) && !isAnAcceptanceState(currentState)) {
            char[] aux = cadena.toCharArray();
            for (int i = 0; i < aux.length; i++) {
                if (!Sigma.contains(String.valueOf(cadena.charAt(i)))) {
                    return false;
                }
            }
            if (currentStringIndex < 0) {
                if (!transitionExists(currentState, "!")) {
                    return false;
                }
                nextState = getNextStepGivenATransition(currentState, "!")[0];
                desplacement = getNextStepGivenATransition(currentState, "!")[2];
                cadena = "!" + cadena;
            } else if (!((currentStringIndex) <= cadena.length() - 1)) {
                if (!transitionExists(currentState, "!")) {
                    return false;
                }
                nextState = getNextStepGivenATransition(currentState, "!")[0];
                desplacement = getNextStepGivenATransition(currentState, "!")[2];
                cadena = cadena + "!";
            } else {
                if (!transitionExists(currentState, String.valueOf(cadena.charAt(currentStringIndex)))) {
                    return false;
                }
                nextState = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[0];
                desplacement = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2];
                cadena = replaceChar(cadena, getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[1].charAt(0), currentStringIndex);
            }
            if (personalContain(desplacement, ">")) {
                currentStringIndex++;
            } else if (getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2] == "<") {
                currentStringIndex--;
            }
            currentState = nextState;   
        }
        return isAnAcceptanceState(currentState);        
    }
    
    public boolean procesarCadenaConDetalles(String cadena) {
        boolean sigmaContainsStringCharacters = true;
        String currentState = q0;   
        String nextState = "";
        String desplacement = "";
        String process = "(" + currentState + ")" + cadena + "->";
        int currentStringIndex = 0;
        while (((currentStringIndex) <= cadena.length()) && !isAnAcceptanceState(currentState)
                && sigmaContainsStringCharacters) {
            char[] aux = cadena.toCharArray();
            for (int i = 0; i < aux.length; i++) {
                if (!Sigma.contains(String.valueOf(cadena.charAt(i)))) {
                    sigmaContainsStringCharacters = false;
                    break;
                }
            }
            if (currentStringIndex < 0) {
                if (!transitionExists(currentState, "!")) {
                    break;
                }
                nextState = getNextStepGivenATransition(currentState, "!")[0];
                desplacement = getNextStepGivenATransition(currentState, "!")[2];
                cadena = "!" + cadena;
            } else if (!((currentStringIndex) <= cadena.length() - 1)) {
                if (!transitionExists(currentState, "!")) {
                    break;
                }
                nextState = getNextStepGivenATransition(currentState, "!")[0];
                desplacement = getNextStepGivenATransition(currentState, "!")[2];
                cadena = cadena + "!";
            } else {
                if (!transitionExists(currentState, String.valueOf(cadena.charAt(currentStringIndex)))) {
                    break;
                }
                nextState = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[0];
                desplacement = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2];
                cadena = replaceChar(cadena, getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[1].charAt(0), currentStringIndex);
            }
            if (personalContain(desplacement, ">")) {
                currentStringIndex++;
            } else if (getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2] == "<") {
                currentStringIndex--;
            }
            currentState = nextState;
            if (currentStringIndex < 0) {
                process += "!(" + currentState + ")" + cadena + "->";
            } else if (currentStringIndex > cadena.length() - 1) {
                process += cadena + "(" + currentState + ")!" + "->";
            } else {
                process += cadena.substring(0, currentStringIndex) + "(" + currentState + ")" + cadena.substring(currentStringIndex) + "->";
            }
        }
        process = process.substring(0, process.length()-2);
        System.out.println(process);
        return isAnAcceptanceState(currentState);  
    } 
    
    public String procesarCadenaConDetallesString(String cadena) {
        boolean sigmaContainsStringCharacters = true;
        String currentState = q0;   
        String nextState = "";
        String desplacement = "";
        String process = "(" + currentState + ")" + cadena + "->";
        int currentStringIndex = 0;
        while (((currentStringIndex) <= cadena.length()) && !isAnAcceptanceState(currentState)
                && sigmaContainsStringCharacters) {
            char[] aux = cadena.toCharArray();
            for (int i = 0; i < aux.length; i++) {
                if (!Sigma.contains(String.valueOf(cadena.charAt(i)))) {
                    sigmaContainsStringCharacters = false;
                    break;
                }
            }
            if (currentStringIndex < 0) {
                if (!transitionExists(currentState, "!")) {
                    break;
                }
                nextState = getNextStepGivenATransition(currentState, "!")[0];
                desplacement = getNextStepGivenATransition(currentState, "!")[2];
                cadena = "!" + cadena;
            } else if (!((currentStringIndex) <= cadena.length() - 1)) {
                if (!transitionExists(currentState, "!")) {
                    break;
                }
                nextState = getNextStepGivenATransition(currentState, "!")[0];
                desplacement = getNextStepGivenATransition(currentState, "!")[2];
                cadena = cadena + "!";
            } else {
                if (!transitionExists(currentState, String.valueOf(cadena.charAt(currentStringIndex)))) {
                    break;
                }
                nextState = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[0];
                desplacement = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2];
                cadena = replaceChar(cadena, getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[1].charAt(0), currentStringIndex);
            }
            if (personalContain(desplacement, ">")) {
                currentStringIndex++;
            } else if (getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2] == "<") {
                currentStringIndex--;
            }
            currentState = nextState;
            if (currentStringIndex < 0) {
                process += "!(" + currentState + ")" + cadena + "->";
            } else if (currentStringIndex > cadena.length() - 1) {
                process += cadena + "(" + currentState + ")!" + "->";
            } else {
                process += cadena.substring(0, currentStringIndex) + "(" + currentState + ")" + cadena.substring(currentStringIndex) + "->";
            }
        }
        process = process.substring(0, process.length()-2);        
        return process;  
    } 
    
    public String procesarFuncion(String cadena) {
        boolean sigmaContainsStringCharacters = true;
        String currentState = q0;   
        String nextState = "";
        String desplacement = "";
        String process = "(" + currentState + ")" + cadena + "->";
        int currentStringIndex = 0;
        while (((currentStringIndex) <= cadena.length()) && !isAnAcceptanceState(currentState)
                && sigmaContainsStringCharacters) {
            char[] aux = cadena.toCharArray();
            for (int i = 0; i < aux.length; i++) {
                if (!Sigma.contains(String.valueOf(cadena.charAt(i)))) {
                    sigmaContainsStringCharacters = false;
                    break;
                }
            }
            if (currentStringIndex < 0) {
                if (!transitionExists(currentState, "!")) {
                    break;
                }
                nextState = getNextStepGivenATransition(currentState, "!")[0];
                desplacement = getNextStepGivenATransition(currentState, "!")[2];
                cadena = "!" + cadena;
            } else if (!((currentStringIndex) <= cadena.length() - 1)) {
                if (!transitionExists(currentState, "!")) {
                    break;
                }
                nextState = getNextStepGivenATransition(currentState, "!")[0];
                desplacement = getNextStepGivenATransition(currentState, "!")[2];
                cadena = cadena + "!";
            } else {
                if (!transitionExists(currentState, String.valueOf(cadena.charAt(currentStringIndex)))) {
                    break;
                }
                nextState = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[0];
                desplacement = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2];
                cadena = replaceChar(cadena, getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[1].charAt(0), currentStringIndex);
            }
            if (personalContain(desplacement, ">")) {
                currentStringIndex++;
            } else if (getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2] == "<") {
                currentStringIndex--;
            }
            currentState = nextState;
            if (currentStringIndex < 0) {
                process += "!(" + currentState + ")" + cadena + "->";
            } else if (currentStringIndex > cadena.length() - 1) {
                process += cadena + "(" + currentState + ")!" + "->";
            } else {
                process += cadena.substring(0, currentStringIndex) + "(" + currentState + ")" + cadena.substring(currentStringIndex) + "->";
            }
        }
        process = process.substring(0, process.length()-2);        
        return cadena;  
    } 
    
    public void procesarListaCadenas(String[] listaCadenas, String nombreArchivo, boolean imprimirPantalla) {
        String process = "";
        for(int j = 0; j < listaCadenas.length; j++){
            boolean sigmaContainsStringCharacters = true;
            String currentState = q0;
            String cadena = listaCadenas[j];
            String currentString = cadena;
            String nextState = "";
            String desplacement = "";
            process += "Cadena: " + cadena + "\t";
            process += "(" + currentState + ")" + cadena + "->";
            int currentStringIndex = 0;
            for (int i = 0; i < listaCadenas[0].length(); i++) {                
                    if (!Sigma.contains(String.valueOf(cadena.charAt(i)))) {                        
                        sigmaContainsStringCharacters = false;
                        break;
                    }
                }
            while (((currentStringIndex) <= cadena.length()) && !isAnAcceptanceState(currentState) 
                    && sigmaContainsStringCharacters) {
                char[] aux = cadena.toCharArray();
                for (int i = 0; i < aux.length; i++) {
                    if (!Sigma.contains(String.valueOf(cadena.charAt(i)))) {
                        sigmaContainsStringCharacters = false;
                        break;
                    }
                }
                if(currentStringIndex < 0){
                    if(!transitionExists(currentState, "!")){
                        break;
                    }
                    nextState = getNextStepGivenATransition(currentState, "!")[0];
                    desplacement = getNextStepGivenATransition(currentState, "!")[2];
                    cadena = "!" + cadena;
                }else if(!((currentStringIndex) <= cadena.length()-1)){
                    if(!transitionExists(currentState,"!")){
                        break;
                    }
                    nextState = getNextStepGivenATransition(currentState, "!")[0];
                    desplacement = getNextStepGivenATransition(currentState, "!")[2];
                    cadena = cadena + "!";
                }else{
                    if(!transitionExists(currentState, String.valueOf(cadena.charAt(currentStringIndex)))){
                        break;
                    }
                    nextState = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[0];
                    desplacement = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2];
                    cadena = replaceChar(cadena, getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[1].charAt(0), currentStringIndex);
                }                 
                if (personalContain(desplacement, ">")) {
                    currentStringIndex++;
                } else if (getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2] == "<") {
                    currentStringIndex--;
                }
                currentState = nextState;
                if(currentStringIndex < 0){
                    process += "!(" + currentState + ")" + cadena+ "->";
                }else if(currentStringIndex > cadena.length() - 1){
                    process += cadena + "(" + currentState + ")!" + "->";
                }else{
                    process += cadena.substring(0, currentStringIndex) + "(" + currentState + ")" + cadena.substring(currentStringIndex) + "->";
                }                  
            }
            process = process.substring(0, process.length() - 2);
            for (int k = 0; k < F.size(); k++) {
                if(sigmaContainsStringCharacters){
                    if (currentState.equals(F.get(k))) {
                        process += "\taccepted\t yes";
                        break;
                    } else {
                        process += "\trejected\t no";
                        break;
                    }
                }else{   
                    process += "\trejected\t no";
                    break;
                }                
            }
            process += "\n";
        }        
        try {                            
            if (nombreArchivo.contains(" ")) {
                FileWriter myWriter = new FileWriter(wPath + File.separator + "default.tm");
                myWriter.write(process);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } else {
                FileWriter myWriter = new FileWriter(wPath + File.separator + nombreArchivo + ".tm");
                BufferedWriter bfwriter = new BufferedWriter(myWriter);
                bfwriter.write(process);
                bfwriter.close();
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
    
    public String procesarListaCadenasString(String[] listaCadenas, String nombreArchivo) {
        String process = "";
        for(int j = 0; j < listaCadenas.length; j++){
            boolean sigmaContainsStringCharacters = true;
            String currentState = q0;
            String cadena = listaCadenas[j];
            String currentString = cadena;
            String nextState = "";
            String desplacement = "";
            process += "Cadena: " + cadena + "\t";
            process += "(" + currentState + ")" + cadena + "->";
            int currentStringIndex = 0;
            for (int i = 0; i < listaCadenas[0].length(); i++) {                
                    if (!Sigma.contains(String.valueOf(cadena.charAt(i)))) {                        
                        sigmaContainsStringCharacters = false;
                        break;
                    }
                }
            while (((currentStringIndex) <= cadena.length()) && !isAnAcceptanceState(currentState) 
                    && sigmaContainsStringCharacters) {
                char[] aux = cadena.toCharArray();
                for (int i = 0; i < aux.length; i++) {
                    if (!Sigma.contains(String.valueOf(cadena.charAt(i)))) {
                        sigmaContainsStringCharacters = false;
                        break;
                    }
                }
                if(currentStringIndex < 0){
                    if(!transitionExists(currentState, "!")){
                        break;
                    }
                    nextState = getNextStepGivenATransition(currentState, "!")[0];
                    desplacement = getNextStepGivenATransition(currentState, "!")[2];
                    cadena = "!" + cadena;
                }else if(!((currentStringIndex) <= cadena.length()-1)){
                    if(!transitionExists(currentState,"!")){
                        break;
                    }
                    nextState = getNextStepGivenATransition(currentState, "!")[0];
                    desplacement = getNextStepGivenATransition(currentState, "!")[2];
                    cadena = cadena + "!";
                }else{
                    if(!transitionExists(currentState, String.valueOf(cadena.charAt(currentStringIndex)))){
                        break;
                    }
                    nextState = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[0];
                    desplacement = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2];
                    cadena = replaceChar(cadena, getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[1].charAt(0), currentStringIndex);
                }                 
                if (personalContain(desplacement, ">")) {
                    currentStringIndex++;
                } else if (getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2] == "<") {
                    currentStringIndex--;
                }
                currentState = nextState;
                if(currentStringIndex < 0){
                    process += "!(" + currentState + ")" + cadena+ "->";
                }else if(currentStringIndex > cadena.length() - 1){
                    process += cadena + "(" + currentState + ")!" + "->";
                }else{
                    process += cadena.substring(0, currentStringIndex) + "(" + currentState + ")" + cadena.substring(currentStringIndex) + "->";
                }                  
            }
            process = process.substring(0, process.length() - 2);
            for (int k = 0; k < F.size(); k++) {
                if(sigmaContainsStringCharacters){
                    if (currentState.equals(F.get(k))) {
                        process += "\taccepted\t yes";
                        break;
                    } else {
                        process += "\trejected\t no";
                        break;
                    }
                }else{   
                    process += "\trejected\t no";
                    break;
                }                
            }
            process += "\n";
        }        
        try {                            
            if (nombreArchivo.contains(" ")) {
                FileWriter myWriter = new FileWriter(wPath + File.separator + "default.tm");
                myWriter.write(process);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } else {
                FileWriter myWriter = new FileWriter(wPath + File.separator + nombreArchivo + ".tm");
                BufferedWriter bfwriter = new BufferedWriter(myWriter);
                bfwriter.write(process);
                bfwriter.close();
                System.out.println("Successfully wrote to the file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }     
        return process;               
    }
    
    public ArrayList<String> getSigma() {
        return Sigma;
    }

    public void setSigma(ArrayList<String> Sigma) {
        this.Sigma = Sigma; 
    }

    public ArrayList<String> getGamma() {
        return Gamma;
    }

    public void setGamma(ArrayList<String> Gamma) {
        this.Gamma = Gamma;
    }

    public ArrayList<TransitionMT> getDelta() {
        return Delta;
    }

    public void setDelta(ArrayList<TransitionMT> Delta) {
        this.Delta = Delta;
    }

    public String getTape() {
        return tape;
    }

    public void setTape(String tape) {
        this.tape = tape;
    }

    public ArrayList<String> getQ() {
        return Q;
    }

    public void setQ(ArrayList<String> Q) {
        this.Q = Q;
    }

    public String getQ0() {
        return q0;
    }

    public void setQ0(String q0) {
        this.q0 = q0;
    }

    public ArrayList<String> getF() {
        return F;
    }

    public void setF(ArrayList<String> F) {
        this.F = F;
    }        

    @Override
    public String toString() {
        String mt = "#inputAlphabet\n";
        for(int i = 0; i < Sigma.size(); i++){
            mt += Sigma.get(i) + "\n";
        }
        mt += "#tapeAlphabet\n";
        for(int i = 0; i < Gamma.size(); i++){
            mt += Gamma.get(i) + "\n";
        }
        mt += "#states\n";
        for(int i = 0; i < Q.size(); i++){
            mt += Q.get(i) + "\n";
        }
        mt += "#initial\n";
        mt += q0 + "\n";
        mt += "#accepting\n";
        for(int i = 0; i < F.size(); i++){
            mt += F.get(i) + "\n";
        }
        mt += "#transitions\n";
        for(int i = 0; i < Delta.size(); i++){
            TransitionMT transition = Delta.get(i);
            mt += transition.getInitialState() + ":" + transition.getSymbol() + "?" + transition.getNextState()  
                    + ":" + transition.getNextSymbol() + ":" + transition.getDisplacement() +"\n";
        }
        return mt;
    }
    
    
    
//    public static void main(String[] args){
//        MT mt = new MT("MaquinaDeTuring");
//        String[] cadenas = {"abb","abbb","abba"};
//        System.out.println(mt.procesarCadena(cadenas[0]));
//        System.out.println(mt.procesarCadena(cadenas[1]));
//        System.out.println(mt.procesarFuncion(cadenas[0]));
//        System.out.println(mt.procesarFuncion(cadenas[1]));
//        System.out.println(mt.procesarCadenaConDetalles(cadenas[0]));
//        System.out.println(mt.procesarCadenaConDetalles(cadenas[1]));
//        System.out.println(mt.procesarCadenaConDetalles(cadenas[2]));
//        mt.procesarListaCadenas(cadenas,"PruebaCadenasParaMaquinaTuring",true);
//
//    }
}

