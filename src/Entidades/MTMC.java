package Entidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MTMC extends AF{
    private ArrayList<String> Q;
    private String q0;
    private ArrayList<String> F;
    private ArrayList<String> Sigma;
    private ArrayList<String> Gamma;
    private ArrayList<String[]> Delta;
    private String[] cintas;
    private String estadoActual;
    private int numeroDeCintas;
    
    static Path currentRelativePath = Paths.get("");
    static String wPath = currentRelativePath.toAbsolutePath().toString() + File.separator + "Data" + File.separator + "MTMC" + File.separator + "writeFolder";
    static Path writePath = Paths.get(wPath);    
    static String rPath = currentRelativePath.toAbsolutePath().toString() + File.separator + "Data" + File.separator + "MTMC" + File.separator + "readFolder";
    static Path readPath = Paths.get(rPath);  

    private MTMC(ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<String> Sigma, ArrayList<String> Gamma, ArrayList<String[]> Delta) {
        super(Q, q0, F);
        this.Sigma = Sigma;
        this.Gamma = Gamma;
        this.Delta = Delta;
    }
    
    public MTMC(String fileName) throws IOException{
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
    
    public void setAtributesByFile(String fileName) throws FileNotFoundException, IOException{
        boolean reader = false;
        String name = fileName + ".mttm";
        String aux = "";
        String line = "";
        try {
            BufferedReader rd = new BufferedReader(new FileReader(new File(rPath + File.separator + fileName)));            
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
    
    public boolean procesarCadena(String cadena){
        cintas[0] = cadena;
        estadoActual = q0;
        boolean aprobacionCadena = false;
        String[] arrayCadena = new String[cadena.length()];
        for(int i = 0; i<cadena.length(); i++){
            arrayCadena[i] = String.valueOf(cadena.charAt(i));
        }
        boolean aprobacionSigma = true;
        for (String arrayCadena1 : arrayCadena) {
            if (!Sigma.contains(arrayCadena1)) {
                aprobacionSigma = false;
                break;
            }
        }
        if(!aprobacionSigma) return false;
        int k = numeroDeCintas;
        int[] posicionesEnCintas = new int[k];
        String[] transicionActual = new String[(3*k)+2];
        boolean aprobacionTransicion = true;
        String charToString = "";
        while(!F.contains(estadoActual)){                   //Mientras el estado actual no haga parte del conjunto de estados de aceptación
            for(String[] transicion : Delta){               //Revisa todas las posibles transiciones para verificar la correcta (si existe)
                if(transicion[0].equals(estadoActual)){     //Encuentra las posibles transiciones del estado actual
                    for(int i = 1; i <= k; i++){            //Compara todos los valores de las cintas con los valores de la transicion
                        charToString = String.valueOf(cintas[i-1].charAt(posicionesEnCintas[i]));
                        if(!transicion[i].equals(charToString)){
                            aprobacionTransicion = false;
                            break;
                        }
                    }
                }
                if(aprobacionTransicion){
                    transicionActual = transicion;
                    break;
                }
            }
            if(!aprobacionTransicion) return false;
            String cadenaAux = cadena;
            for(int n = 1; n <= k; n++){
                cadenaAux = cadenaAux.substring(0, posicionesEnCintas[n]) + transicionActual[k+(2*n)] + cadenaAux.substring(n);
                if(transicionActual[k+(2*n)+1].equals("<")){
                    if(posicionesEnCintas[n] == 0){
                        cadenaAux = "!" + cadenaAux;
                    }
                    else{
                        posicionesEnCintas[n] = posicionesEnCintas[n] - 1;
                    }
                }
                else if(transicionActual[k+(2*n)+1].equals("-")){
                    
                }
                else if(transicionActual[k+(2*n)+1].equals(">")){
                    if(posicionesEnCintas[n] == cintas[n].length()){
                        cadenaAux = cadenaAux + "!";
                    }
                    else{
                        posicionesEnCintas[n] = posicionesEnCintas[n]+1;
                    }
                }
                cintas[n] = cadenaAux;
            }
            estadoActual = transicionActual[k+1];
        }
        if(F.contains(estadoActual)){
            aprobacionCadena = true;
        }
    return aprobacionCadena;
    }
    
    public boolean procesarCadenaConDetalles(String cadena){
        cintas[0] = cadena;
        estadoActual = q0;
        String cintasImpresion = "";
        for(int i = 1; i <= cintas.length; i++){
            cintasImpresion = cintasImpresion + ",*!";
        }
        String impresion;
        impresion = "(" + q0 + ",*" + cadena + cintasImpresion + ")->";
        boolean aprobacionCadena = false;
        String[] arrayCadena = new String[cadena.length()];
        for(int i = 0; i<cadena.length(); i++){
            arrayCadena[i] = String.valueOf(cadena.charAt(i));
        }
        boolean aprobacionSigma = true;
        for (String arrayCadena1 : arrayCadena) {
            if (!Sigma.contains(arrayCadena1)) {
                aprobacionSigma = false;
                break;
            }
        }
        if(!aprobacionSigma) return false;
        int k = numeroDeCintas;
        int[] posicionesEnCintas = new int[k];
        String[] transicionActual = new String[(3*k)+2];
        boolean aprobacionTransicion = true;
        String charToString = "";
        while(!F.contains(estadoActual)){                   //Mientras el estado actual no haga parte del conjunto de estados de aceptación
            for(String[] transicion : Delta){               //Revisa todas las posibles transiciones para verificar la correcta (si existe)
                if(transicion[0].equals(estadoActual)){     //Encuentra las posibles transiciones del estado actual
                    for(int i = 1; i <= k; i++){            //Compara todos los valores de las cintas con los valores de la transicion
                        charToString = String.valueOf(cintas[i-1].charAt(posicionesEnCintas[i]));
                        if(!transicion[i].equals(charToString)){
                            aprobacionTransicion = false;
                            break;
                        }
                    }
                }
                if(aprobacionTransicion){
                    transicionActual = transicion;
                    break;
                }
            }
            if(!aprobacionTransicion) return false;
            String cadenaAux = cintas[0];
            for(int n = 1; n <= k; n++){
                cadenaAux = cadenaAux.substring(0, posicionesEnCintas[n]) + transicionActual[k+(2*n)] + cadenaAux.substring(n);
                if(transicionActual[k+(2*n)+1].equals("<")){
                    if(posicionesEnCintas[n] == 0){
                        cadenaAux = "!" + cadenaAux;
                    }
                    else{
                        posicionesEnCintas[n] = posicionesEnCintas[n] - 1;
                    }
                }
                else if(transicionActual[k+(2*n)+1].equals("-")){
                    
                }
                else if(transicionActual[k+(2*n)+1].equals(">")){
                    if(posicionesEnCintas[n] == cintas[n].length()){
                        cadenaAux = cadenaAux + "!";
                    }
                    else{
                        posicionesEnCintas[n] = posicionesEnCintas[n]+1;
                    }
                }
                cintas[n] = cadenaAux;
            }
            estadoActual = transicionActual[k+1];
            impresion = impresion + "(" + estadoActual;
            for(int n = 0; n < cintas.length; n++){
                cintasImpresion = cintas[n].substring(0, posicionesEnCintas[n]) + "*" + cintas[n].substring(n-1);
                impresion = impresion + "," + cintasImpresion;
            }
            impresion = impresion + ")->";
        }
        if(F.contains(estadoActual)){
            aprobacionCadena = true;
            impresion = impresion + "aceptación";
        }
        else{
            aprobacionCadena = false;
            impresion = impresion + "rechazo";
        }
        System.out.println(impresion);
    return aprobacionCadena;
    }
    
    public String procesarCadenaConDetallesString(String cadena){
        cintas[0] = cadena;
        estadoActual = q0;
        String cintasImpresion = "";
        for(int i = 1; i <= cintas.length; i++){
            cintasImpresion = cintasImpresion + ",*!";
        }
        String impresion;
        impresion = "(" + q0 + ",*" + cadena + cintasImpresion + ")->";
        boolean aprobacionCadena = false;
        String[] arrayCadena = new String[cadena.length()];
        for(int i = 0; i<cadena.length(); i++){
            arrayCadena[i] = String.valueOf(cadena.charAt(i));
        }
        boolean aprobacionSigma = true;
        for (String arrayCadena1 : arrayCadena) {
            if (!Sigma.contains(arrayCadena1)) {
                aprobacionSigma = false;
                break;
            }
        }
        if(!aprobacionSigma) return impresion + "rechazo";
        int k = numeroDeCintas;
        int[] posicionesEnCintas = new int[k];
        String[] transicionActual = new String[(3*k)+2];
        boolean aprobacionTransicion = true;
        String charToString = "";
        while(!F.contains(estadoActual)){                   //Mientras el estado actual no haga parte del conjunto de estados de aceptación
            for(String[] transicion : Delta){               //Revisa todas las posibles transiciones para verificar la correcta (si existe)
                if(transicion[0].equals(estadoActual)){     //Encuentra las posibles transiciones del estado actual
                    for(int i = 1; i <= k; i++){            //Compara todos los valores de las cintas con los valores de la transicion
                        charToString = String.valueOf(cintas[i-1].charAt(posicionesEnCintas[i]));
                        if(!transicion[i].equals(charToString)){
                            aprobacionTransicion = false;
                            break;
                        }
                    }
                }
                if(aprobacionTransicion){
                    transicionActual = transicion;
                    break;
                }
            }
            if(!aprobacionTransicion) return impresion + "rechazo";
            String cadenaAux = cintas[0];
            for(int n = 1; n <= k; n++){
                cadenaAux = cadenaAux.substring(0, posicionesEnCintas[n]) + transicionActual[k+(2*n)] + cadenaAux.substring(n);
                if(transicionActual[k+(2*n)+1].equals("<")){
                    if(posicionesEnCintas[n] == 0){
                        cadenaAux = "!" + cadenaAux;
                    }
                    else{
                        posicionesEnCintas[n] = posicionesEnCintas[n] - 1;
                    }
                }
                else if(transicionActual[k+(2*n)+1].equals("-")){
                    
                }
                else if(transicionActual[k+(2*n)+1].equals(">")){
                    if(posicionesEnCintas[n] == cintas[n].length()){
                        cadenaAux = cadenaAux + "!";
                    }
                    else{
                        posicionesEnCintas[n] = posicionesEnCintas[n]+1;
                    }
                }
                cintas[n] = cadenaAux;
            }
            estadoActual = transicionActual[k+1];
            impresion = impresion + "(" + estadoActual;
            for(int n = 0; n < cintas.length; n++){
                cintasImpresion = cintas[n].substring(0, posicionesEnCintas[n]) + "*" + cintas[n].substring(n-1);
                impresion = impresion + "," + cintasImpresion;
            }
            impresion = impresion + ")->";
        }
        if(F.contains(estadoActual)){
            aprobacionCadena = true;
            impresion = impresion + "aceptación";
        }
        else{
            aprobacionCadena = false;
            impresion = impresion + "rechazo";
        }        
    return impresion;
    }
    
    private void procesarListaCadenas(ArrayList<String> listaCadenas, String nombreArchivo, boolean imprimirPantalla){
        for(String cadena : listaCadenas){
            
        }
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
    
    public ArrayList<String[]> getDelta() {
        return Delta;
    }

    public void setDelta(ArrayList<String[]> Delta) {
        this.Delta = Delta;
    }
    
    public String[] getCintas() {
        return cintas;
    }

    public void setCintas(String[] cintas) {
        this.cintas = cintas;
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
            String[] transition = Delta.get(i);
            mt += transition[0] + ":" + transition[1] + "?" + transition[2] + ":" + transition[3] + ":" + transition[4] +"\n";
        }
        return mt;
    }

}

