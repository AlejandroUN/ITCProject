package Entidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Stack;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AFPD extends AFP{

    private ArrayList<String[]> Delta;
    private Stack<String> stack;
    private String actualState;

    public AFPD(ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<String> Sigma, ArrayList<String> Gamma, ArrayList<String[]> Delta) {
        super(Q, q0, F, Sigma, Gamma);
        this.Delta = Delta;
    }

    public AFPD(String fileName){
        super();
        try {
            setAtributesByFile(fileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(AFPD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void addToAlphabetFromARange(String range, ArrayList<String> alphabet){
        if(((int)range.charAt(0) >= 48) && ((int)range.charAt(0) <= 57)){
            for(int i = Character.getNumericValue(range.charAt(0)); i < Character.getNumericValue(range.charAt(2)) ; i++){                
                alphabet.add(Integer.toString(i));
            }
        }else if(((int)range.charAt(0) >= 65) && ((int)range.charAt(0) <= 90)){
            for(int j = (int)range.charAt(0); j < (int)range.charAt(2)+1 ; j++){                
                String symbol = Character.toString((char)j);                
                alphabet.add(symbol);
            }
        }else if(((int)range.charAt(0) >= 97) && ((int)range.charAt(0) <= 122)){
            for(int j = (int)range.charAt(0); j < (int)range.charAt(2)+1 ; j++){                
                String symbol = Character.toString((char)j);                
                alphabet.add(symbol);
            }
        }
    }
    
    public boolean contain(String s1, String s2){
        for(int i = 0; i < s2.length(); i++){
            if(!s1.contains(Character.toString(s2.charAt(i)))){
                return false;
            }
        }
        return true;
    }    
    
    public void setAtributesByFile(String fileName) throws FileNotFoundException{
        boolean reader = false;
        String name = fileName + ".dfa";
        String aux = "";
        String line = "";
        try {
            BufferedReader rd = new BufferedReader(new FileReader(new File(name)));
            while(rd.readLine() != null){
                line = rd.readLine();
                if(contain(line, "#states")){
                    aux = "#states";
                } else if(contain(aux, "#states") && (line.length() != 0)) {
                    Q.add(line);
                } else if(contain(line, "#initial")){
                    aux = "#initial";
                } else if(contain(aux, "#initial") && (line.length() != 0)){
                    q0 = line;
                } else if(contain(line, "#accepting")){
                    aux = "#accepting";
                } else if(contain(aux, "#accepting") && (line.length() != 0)) {
                    F.add(line);
                }else if(contain(line, "#transitions")){
                    aux = "#transitions";
                } else if(contain(line, "#transitions") && (line.length() != 0)){
                    String initialState = line.split(":")[0];
                    String symbol = line.split(":")[1].split(">")[0];
                    String nextState = line.split(":")[1].split(">")[1];
                    String[] transition = null;                
                    Delta.add(transition);  
                } else if(contain(line, "#alphabetC")){
                    aux = "#alphabetC";
                } else if(contain(line, "#alphabetC")){
                    if(line.length() != 1){
                        addToAlphabetFromARange(line, Sigma);
                    } else {
                        Sigma.add(line);
                    }
                } else if(contain(line, "#alphabetP")){
                    aux = "#alphabetP";
                } else if(contain(line, "#alphabetP")){
                    if(line.length() != 1){
                        addToAlphabetFromARange(line, Gamma);
                    } else {
                        Gamma.add(line);
                    }
                }
            }
        } catch (FileNotFoundException e){
            System.out.println("An error has ocurred");
        } catch (IOException ex){
            Logger.getLogger(AFPD.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private boolean modificarPila(Stack<String> stack, String operation, String parameter){
        if(operation.equals("$") && parameter.equals("$")){
            return true;
        }
        else if(operation.equals("$") && !parameter.equals("$")){
            if(stack.isEmpty()){
                return false;
            }
            else if(!stack.peek().equals(parameter)){
                return false;
            }
            else{
                stack.pop();
            }
        }
        else if(!operation.equals("$") && parameter.equals("$")){
            stack.push(operation);
        }
        else if(!operation.equals("$") && !parameter.equals("$")){
            if(stack.isEmpty()){
                return false;
            }
            else if(!stack.peek().equals(parameter)){
                return false;
            }
            else{
                stack.pop();
                stack.push(operation);
            }
        }
        this.stack = stack;
        return true;
    }

    private boolean procesarCadena(String cadena){
        boolean temp = true;
        char[] aux = cadena.toCharArray();
        for(int i = 0; i < aux.length; i++){
            if(!Sigma.contains(String.valueOf(cadena.charAt(i)))) temp = false;
        }
        String parameter = "";
        String operation = "";
        String newState = "";
        int i = 0;
        String[] transition = null;
        for(String[] j : Delta){
            if(j[0].equals(q0) && j[1].equals(aux[0])){
                transition = j;
                break;
            }
        }
        while(temp == true && i < aux.length){
            parameter = transition[2];
            operation = transition[4];
            if(modificarPila(stack, operation, parameter)){
                actualState = transition[3];
            }
            else{
                temp = false;
            }
            i++;
            for(String[] j : Delta){
                if(j[0].equals(actualState) && j[1].equals(aux[i])){
                    transition = j;
                    break;
                }
            }
        }
        if(!stack.isEmpty() || !F.contains(actualState)){
            temp = false;
        }
        return temp;
    }

    private boolean procesarCadenaConDetalles(String str){
        String impresion = "(" + q0 + "," + str + "," + printStack() + "->";
        boolean temp = true;
        char[] aux = str.toCharArray();
        for(int i = 0; i < aux.length; i++){
            if(!Sigma.contains(String.valueOf(str.charAt(i)))) temp = false;
        }
        String parameter = "";
        String operation = "";
        String newState = "";
        int i = 0;
        String[] transition = null;
        for(String[] j : Delta){
            if(j[0].equals(q0) && j[1].equals(aux[0])){
                transition = j;
                break;
            }
        }
        while(temp == true && i < aux.length){
            impresion = impresion + "(" + q0 + "," + str.substring(i) + "," + printStack() + "->";
            parameter = transition[2];
            operation = transition[4];
            if(modificarPila(stack, operation, parameter)){
                actualState = transition[3];
            }
            else{
                temp = false;
            }
            i++;
            for(String[] j : Delta){
                if(j[0].equals(actualState) && j[1].equals(aux[i])){
                    transition = j;
                    break;
                }
            }

        }
        if(!stack.isEmpty() || !F.contains(actualState)){
            temp = false;
        }
        if(temp){
            impresion = impresion + "accepted";
        } 
        else{
            impresion = impresion + "rejected";
        }
        System.out.println(impresion);
        return temp;
    }
    
    private String printStack(){
        String impresionPila = "";
        if(stack.isEmpty()) return "$";
        String item = stack.pop();
        impresionPila = item;
        printStack();
        stack.push(item);
        return impresionPila;
    }
    
    @Override
    public String toString() {
        String afpd = "#alphabet\n";
        for(int i = 0; i < Sigma.size(); i++){
            afpd += Sigma.get(i) + "\n";
        }
        afpd += "#states\n";
        for(int i = 0; i < Q.size(); i++){
            afpd += Q.get(i) + "\n";
        }
        afpd += "#initial\n";
        afpd += q0 + "\n";
        afpd += "#accepting\n";
        for(int i = 0; i < F.size(); i++){
            afpd += F.get(i) + "\n";
        }
        afpd += "#stack\n";
        for(int i = 0; i < stack.size(); i++){
            afpd += stack.get(i) + "\n";
        }
        afpd += "#transitions\n";
        for(int i = 0; i < Delta.size(); i++){
            String[] transition = Delta.get(i);
        }
        return afpd;
    }
    
    public ArrayList<String[]> getDelta() {
        return Delta;
    }

    public void setDelta(ArrayList<String[]> Delta) {
        this.Delta = Delta;
    }

    public Stack<String> getStack() {
        return stack;
    }

    public void setStack(Stack<String> stack) {
        this.stack = stack;
    }

    public String getActualState() {
        return actualState;
    }

    public void setActualState(String actualState) {
        this.actualState = actualState;
    }
        
}