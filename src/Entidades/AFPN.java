package Entidades;

import java.io.*;
import java.net.URLEncoder;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.logging.*;

public class AFPN extends AFP {

    private ArrayList<TransitionAFPN> Delta = new ArrayList<>();
    private Stack<String> stack = new Stack<>();
    private ArrayList<String> recorridos = new ArrayList<>();

    static Path currentRelativePath = Paths.get("");
    static String wPath = currentRelativePath.toAbsolutePath().toString() + File.separator + "Data" + File.separator + "AFPN" + File.separator + "writeFolder";
    static Path writePath = Paths.get(wPath);    
    static String rPath = currentRelativePath.toAbsolutePath().toString() + File.separator + "Data" + File.separator + "AFPN" + File.separator + "readFolder";
    static Path readPath = Paths.get(rPath); 
    
    //          (Estados,   estado Inicial, estados Aceptacion, Alfabeto, Alfabeto Pila, Transiciones)
    public AFPN(ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<String> Sigma, ArrayList<String> Gamma, ArrayList<TransitionAFPN> Delta) {
        super(Q, q0, F, Sigma, Gamma);
        this.Delta = Delta;
        this.stack = new Stack<>();
    }

    public AFPN(String nombreArchivo) {
        super();
        setAtributesGivenAFile(nombreArchivo);
    }

    public boolean verificarAlfabeto(String cadena) {
        for (int i = 0; i < cadena.length(); i++) {
            if (!this.Sigma.contains(String.valueOf(cadena.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public TransitionAFPN getNextState(String estadoActual, char caracter) {
        int index = new Random().nextInt(this.Delta.size());
        TransitionAFPN transicion = this.Delta.get(index);
        if (transicion.getq0().equals(estadoActual) && (transicion.getCharacter() == caracter || transicion.getCharacter() == '$')) {
            if (transicion.getPilaOut() != '$' && transicion.getPilaIn() != '$' && !this.stack.isEmpty()) {  // A|B
                if (getTopPila().equals(String.valueOf(transicion.getPilaOut()))) {
                    this.stack.pop();
                } else {
                    return null;
                }
            } else if (transicion.getPilaOut() != '$' && transicion.getPilaIn() == '$' && this.stack.isEmpty()) {
                return null;
            }
            if (transicion.getPilaOut() != '$' && transicion.getPilaIn() == '$' && !this.stack.isEmpty()) {  // A|$
                if (getTopPila().equals(String.valueOf(transicion.getPilaOut()))) {
                    this.stack.pop();
                } else {
                    return null;
                }
            } else if (transicion.getPilaOut() != '$' && transicion.getPilaIn() == '$' && this.stack.isEmpty()) {
                return null;
            }
            if (transicion.getPilaOut() == '$' && transicion.getPilaIn() != '$') {                          // $|B
            }
            return transicion;
        }
        return null;
    }

    public String getTopPila() {
        try {
            return this.stack.peek();
        } catch (EmptyStackException e) {
            return "$";
        }
    }

    public String verPila() {
        String pila = "";
        if (this.stack.isEmpty()) {
            return "$";
        }
        for (int i = 0; i < this.stack.size(); i++) {
            pila += this.stack.get(this.stack.size() - i - 1);
        }
        return pila;
    }

    public void addToAlphabetFromARange(String range) {
        if (((int) range.charAt(0) >= 48) && ((int) range.charAt(0) <= 57)) {
            for (int i = Character.getNumericValue(range.charAt(0)); i < Character.getNumericValue(range.charAt(2)); i++) {
                Sigma.add(Integer.toString(i));
            }
        } else if (((int) range.charAt(0) >= 65) && ((int) range.charAt(0) <= 90)) {
            for (int j = (int) range.charAt(0); j < (int) range.charAt(2) + 1; j++) {
                Sigma.add(String.valueOf((char) j));
            }
        } else if (((int) range.charAt(0) >= 97) && ((int) range.charAt(0) <= 122)) {
            for (int j = (int) range.charAt(0); j < (int) range.charAt(2) + 1; j++) {
                Sigma.add(String.valueOf((char) j));
            }
        }
    }

    public boolean personalContain(String s1, String s2) {
        for (int i = 0; i < s2.length(); i++) {
            if (!s1.contains(Character.toString(s2.charAt(i)))) {
                return false;
            }
        }
        return true;
    }

    public boolean setAtributesGivenAFile(String nombreArchivo) {
        boolean state = false;
        String curSection = "WTF";
        String fileName = nombreArchivo + ".pda";
        String curLine = "";
        try {
            BufferedReader myReader=new BufferedReader(new FileReader(new File(rPath + File.separator + fileName)));     
            while ((curLine = myReader.readLine()) != null) {
                if (personalContain(curLine, "#alphabet")) {
                    curSection = "#alphabet";
                } else if (personalContain(curLine, "#states")) {
                    curSection = "#states";
                } else if (personalContain(curLine, "#initial")) {
                    curSection = "#initial";
                } else if (personalContain(curLine, "#accepting")) {
                    curSection = "#accepting";
                } else if (personalContain(curLine, "#transitions")) {
                    curSection = "#transitions";
                } else if (personalContain(curSection, "#alphabet") && curLine.length() != 0) {
                    if (curLine.length() != 1) {
                        addToAlphabetFromARange(curLine);
                    } else {
                        Sigma.add(curLine);
                    }
                } else if (personalContain(curSection, "#states") && curLine.length() != 0) {
                    Q.add(curLine);
                } else if (personalContain(curSection, "#initial") && curLine.length() != 0) {
                    q0 = curLine;
                } else if (personalContain(curSection, "#accepting") && curLine.length() != 0) {
                    F.add(curLine);
                } else if (personalContain(curSection, "#transitions") && curLine.length() != 0) {
                    String initialState = curLine.split(":")[0];
                    String symbol = curLine.split(":")[1].split(">")[0];
                    char pilaIn = curLine.split(":")[2].charAt(0);
                    for (int i = 0; i < curLine.split(">")[1].split(";").length; i++) {
                        char PilaOut = curLine.split(">")[1].split(";")[i].charAt(3);
                        String nexState = curLine.split(">")[1].split(";")[i].split(":")[0];
                        this.Delta.add(new TransitionAFPN(initialState, symbol.charAt(0), pilaIn, nexState, PilaOut));
                    }
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

    public boolean recorridoAFPN(String cadena, String state) {
        String estadoActual = state;
        String cadenaAlterna = cadena;
        recorridos.add("(" + estadoActual + "," + cadena + "," + verPila() + ")");
        try {
            TransitionAFPN aux = getNextState(estadoActual, cadenaAlterna.charAt(0));
            estadoActual = aux.getFinalStates();
            if (aux.getPilaIn() != '$') {
                this.stack.push(String.valueOf(aux.getPilaIn()));
            }
        } catch (NullPointerException nu) {
            recorridos.add("false");
            return false;
        }

        if (cadenaAlterna.length() > 1) {
            cadenaAlterna = cadenaAlterna.substring(1);         //Eliminamos el primer caracter
            return recorridoAFPN(cadenaAlterna, estadoActual);
        }
        if (cadenaAlterna.length() == 1) {   //No ha mas cadena pa recorrer y estado Aceptacion
            recorridos.add("(" + estadoActual + ",$," + verPila() + ")");
            recorridos.add(String.valueOf(F.contains(estadoActual) && this.stack.isEmpty()));
            return F.contains(estadoActual) && this.stack.isEmpty();
        } else {
            recorridos.add("Upps Error");
            return false;
        }
    }

    public boolean procesarCadena(String cadena) {
        ArrayList<ArrayList<String>> lista = new ArrayList<>();
        for (int i = 0; i < 50000 * cadena.length(); i++) {       //Funcion Probabilística
            recorridoAFPN(cadena, q0);
            if (!lista.contains(recorridos)) {
                lista.add(recorridos);
            }
            this.recorridos = new ArrayList<>();
            this.stack = new Stack<>();
        }
        return lista.stream().anyMatch((lista1) -> (lista1.get(lista1.size() - 1).equals("true")));     //Verificamos si algun elemento retorna un valor True
    }

    public boolean procesarCadenaConDetalles(String cadena) {
        ArrayList<ArrayList<String>> lista = new ArrayList<>();
        for (int i = 0; i < 50000 * cadena.length(); i++) {       //Funcion Probabilística
            recorridoAFPN(cadena, q0);
            if (!lista.contains(recorridos)) {
                lista.add(recorridos);
            }
            this.recorridos = new ArrayList<>();
            this.stack = new Stack<>();
        }
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).get(lista.get(i).size() - 1).equals("true")) {
                System.out.print("Procesamiento " + (i + 1) + ": \t");
                System.out.print(lista.get(i).get(0));
                for (int j = 0; j < lista.get(i).size(); j++) {
                    if (lista.get(i).get(j).equals("true")) {
                        System.out.print(">>");
                        lista.get(i).set(j, "acepted");
                        System.out.println(lista.get(i).get(j));
                        return true;
                    }
                    System.out.print("->" + lista.get(i).get(j));
                }
            }
        }

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).get(lista.get(i).size() - 1).equals("false")) {
                System.out.print("Procesamiento " + (i + 1) + ": \t");
                System.out.print(lista.get(i).get(0));
                for (int j = 0; j < lista.get(i).size(); j++) {
                    if (lista.get(i).get(j).equals("false")) {
                        System.out.print(">>");
                        lista.get(i).set(j, "rejected");
                        System.out.println(lista.get(i).get(j));
                        continue;
                    }
                    System.out.print("->" + lista.get(i).get(j));
                }
            }
        }
        return false;
    }

    public String procesarCadenaConDetallesString(String cadena) {
        String procesamiento = "";
        ArrayList<ArrayList<String>> lista = new ArrayList<>();
        for (int i = 0; i < 50000 * cadena.length(); i++) {       //Funcion Probabilística
            recorridoAFPN(cadena, q0);
            if (!lista.contains(recorridos)) {
                lista.add(recorridos);
            }
            this.recorridos = new ArrayList<>();
            this.stack = new Stack<>();
        }
        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).get(lista.get(i).size() - 1).equals("true")) {
                System.out.print("Procesamiento " + (i + 1) + ": \t");
                procesamiento += "Procesamiento " + (i + 1) + ": \t";
                System.out.print(lista.get(i).get(0));
                procesamiento += lista.get(i).get(0);
                for (int j = 0; j < lista.get(i).size(); j++) {
                    if (lista.get(i).get(j).equals("true")) {
                        System.out.print(">>");
                        procesamiento += ">>";
                        lista.get(i).set(j, "acepted");
                        System.out.println(lista.get(i).get(j));
                        procesamiento += lista.get(i).get(j);
                        return procesamiento;
                    }
                    System.out.print("->" + lista.get(i).get(j));
                    procesamiento += "->" + lista.get(i).get(j);
                }
            }
        }

        for (int i = 0; i < lista.size(); i++) {
            if (lista.get(i).get(lista.get(i).size() - 1).equals("false")) {
                System.out.print("Procesamiento " + (i + 1) + ": \t");
                procesamiento += "Procesamiento " + (i + 1) + ": \t";
                System.out.print(lista.get(i).get(0));
                procesamiento += lista.get(i).get(0);
                for (int j = 0; j < lista.get(i).size(); j++) {
                    if (lista.get(i).get(j).equals("false")) {
                        System.out.print(">>");
                        procesamiento += ">>";
                        lista.get(i).set(j, "rejected");
                        System.out.println(lista.get(i).get(j));
                        procesamiento += lista.get(i).get(j);
                        continue;
                    }
                    System.out.print("->" + lista.get(i).get(j));
                    procesamiento += "->" + lista.get(i).get(j);
                }
            }
        }
        return procesamiento;
    }

    public int computarTodosLosProcesamientos(String cadena, String nombreArchivo) {
        System.out.println("Cadena: " + cadena);

        ArrayList<ArrayList<String>> lista = new ArrayList<>();

        ArrayList<ArrayList<String>> listaAceptacion = new ArrayList<>();
        ArrayList<ArrayList<String>> listaRechazo = new ArrayList<>();

        for (int i = 0; i < 50000 * cadena.length(); i++) {       //Funcion Probabilística
            recorridoAFPN(cadena, q0);
            if (!lista.contains(recorridos)) {
                lista.add(recorridos);
            }
            this.recorridos = new ArrayList<>();
            this.stack = new Stack<>();
        }

        for (int i = 0; i < lista.size(); i++) {
            System.out.print("Procesamiento " + (i + 1) + ": \t");
            System.out.print(lista.get(i).get(0));
            for (int j = 1; j < lista.get(i).size(); j++) {
                if (lista.get(i).get(j).equals("false") || lista.get(i).get(j).equals("true")) {
                    System.out.print(">>");
                    if (lista.get(i).get(j).equals("false")) {
                        lista.get(i).set(j, "rejected");
                        listaRechazo.add(lista.get(i));
                        System.out.print(lista.get(i).get(j));
                    } else if (lista.get(i).get(j).equals("true")) {
                        lista.get(i).set(j, "acepted");
                        listaAceptacion.add(lista.get(i));
                        System.out.print(lista.get(i).get(j));
                    }
                    continue;
                }
                System.out.print("->" + lista.get(i).get(j));
            }
            System.out.println("");
        }

        try (FileWriter fw = new FileWriter(nombreArchivo + "RechazadasAFPN.txt", false);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println("Cadena: " + cadena);
            for (int i = 0; i < listaRechazo.size(); i++) {
                out.print("Procesamiento " + (i + 1) + ": \t");
                out.print(listaRechazo.get(i).get(0));
                for (int j = 0; j < listaRechazo.get(i).size(); j++) {
                    if (listaRechazo.get(i).get(j).equals("rejected")) {
                        out.print(">>");
                        out.print(listaRechazo.get(i).get(j));
                        continue;
                    }
                    out.print("->" + listaRechazo.get(i).get(j));
                }
                out.println("");
            }
        } catch (IOException e) {
            System.out.println("Error, problemas al crear el Documento" + nombreArchivo + "RechazadasAFPN.txt");
        }

        try (FileWriter fw = new FileWriter(nombreArchivo + "AceptadasAFPN.txt", false);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            out.println("Cadena: " + cadena);
            for (int i = 0; i < listaAceptacion.size(); i++) {
                out.print("Procesamiento " + (i + 1) + ": \t");
                out.print(listaAceptacion.get(i).get(0));
                for (int j = 0; j < listaAceptacion.get(i).size(); j++) {
                    if (listaAceptacion.get(i).get(j).equals("acepted")) {
                        out.print(">>");
                        out.print(listaAceptacion.get(i).get(j));
                        continue;
                    }
                    out.print("->" + listaAceptacion.get(i).get(j));
                }
                out.println("");
            }
        } catch (IOException e) {
            System.out.println("Error, problemas al crear el Documento" + nombreArchivo + "AceptadasAFPN.txt");
        }

        return lista.size();
    }

    public void procesarListaCadenas(ArrayList<String> cadenas, String nombreArchivo, boolean imprimirPantalla) {
        try {
            nombreArchivo = URLEncoder.encode(nombreArchivo, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            nombreArchivo = "procesamientoAFPNDefault.pda";
        }
        try (FileWriter fw = new FileWriter(wPath + File.separator + nombreArchivo + ".dfa");
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            boolean flag = false;
            for (String cadena : cadenas) {
                out.print(cadena + "\t");
                ArrayList<ArrayList<String>> lista = new ArrayList<>();

                ArrayList<ArrayList<String>> listaAceptacion = new ArrayList<>();
                ArrayList<ArrayList<String>> listaRechazo = new ArrayList<>();

                for (int i = 0; i < 50000 * cadena.length(); i++) {       //Funcion Probabilística
                    recorridoAFPN(cadena, q0);
                    if (!lista.contains(recorridos)) {
                        lista.add(recorridos);
                    }
                    this.recorridos = new ArrayList<>();
                    this.stack = new Stack<>();
                }

                for (int i = 0; i < lista.size(); i++) {
                    for (int j = 1; j < lista.get(i).size(); j++) {
                        if (lista.get(i).get(j).equals("false") || lista.get(i).get(j).equals("true")) {
                            if (lista.get(i).get(j).equals("false")) {
                                lista.get(i).set(j, "rejected");
                                listaRechazo.add(lista.get(i));
                            } else if (lista.get(i).get(j).equals("true")) {
                                lista.get(i).set(j, "acepted");
                                listaAceptacion.add(lista.get(i));
                            }
                        }
                    }
                }

                if (!listaAceptacion.isEmpty()) {
                    flag = true;
                    for (int i = 0; i < listaAceptacion.size(); i++) {
                        out.print(listaAceptacion.get(i).get(0));
                        for (int j = 0; j < listaAceptacion.get(i).size(); j++) {
                            if (listaAceptacion.get(i).get(j).equals("acepted")) {
                                out.print(">>");
                                out.print(listaAceptacion.get(i).get(j));
                                break;
                            }
                            out.print("->" + listaAceptacion.get(i).get(j));
                        }
                        break;
                    }
                } else {
                    for (int i = 0; i < listaRechazo.size(); i++) {
                        out.print(listaRechazo.get(i).get(0));
                        for (int j = 0; j < listaRechazo.get(i).size(); j++) {
                            if (listaRechazo.get(i).get(j).equals("rejected")) {
                                out.print(">>");
                                out.print(listaRechazo.get(i).get(j));
                                break;
                            }
                            out.print("->" + listaRechazo.get(i).get(j));
                        }
                        break;
                    }
                }
                out.print("\t" + listaAceptacion.size() + "\t" + listaRechazo.size() + "\t");
                if (flag) {
                    out.print("yes");
                } else {
                    out.print("no");
                }
                out.println();
            }
        } catch (IOException e) {
            System.out.println("Error, problemas al crear el Documento" + nombreArchivo);
        }
        if (imprimirPantalla) {
            boolean flag = false;
            for (String cadena : cadenas) {
                System.out.print(cadena + "\t");
                ArrayList<ArrayList<String>> lista = new ArrayList<>();

                ArrayList<ArrayList<String>> listaAceptacion = new ArrayList<>();
                ArrayList<ArrayList<String>> listaRechazo = new ArrayList<>();

                for (int i = 0; i < 50000 * cadena.length(); i++) {       //Funcion Probabilística
                    recorridoAFPN(cadena, q0);
                    if (!lista.contains(recorridos)) {
                        lista.add(recorridos);
                    }
                    this.recorridos = new ArrayList<>();
                    this.stack = new Stack<>();
                }

                for (int i = 0; i < lista.size(); i++) {
                    for (int j = 1; j < lista.get(i).size(); j++) {
                        if (lista.get(i).get(j).equals("false") || lista.get(i).get(j).equals("true")) {
                            if (lista.get(i).get(j).equals("false")) {
                                lista.get(i).set(j, "rejected");
                                listaRechazo.add(lista.get(i));
                            } else if (lista.get(i).get(j).equals("true")) {
                                lista.get(i).set(j, "acepted");
                                listaAceptacion.add(lista.get(i));
                            }
                        }
                    }
                }

                if (!listaAceptacion.isEmpty()) {
                    flag = true;
                    for (int i = 0; i < listaAceptacion.size(); i++) {
                        System.out.print(listaAceptacion.get(i).get(0));
                        for (int j = 0; j < listaAceptacion.get(i).size(); j++) {
                            if (listaAceptacion.get(i).get(j).equals("acepted")) {
                                System.out.print(">>");
                                System.out.print(listaAceptacion.get(i).get(j));
                                break;
                            }
                            System.out.print("->" + listaAceptacion.get(i).get(j));
                        }
                        break;
                    }
                } else {
                    for (int i = 0; i < listaRechazo.size(); i++) {
                        System.out.print(listaRechazo.get(i).get(0));
                        for (int j = 0; j < listaRechazo.get(i).size(); j++) {
                            if (listaRechazo.get(i).get(j).equals("rejected")) {
                                System.out.print(">>");
                                System.out.print(listaRechazo.get(i).get(j));
                                break;
                            }
                            System.out.print("->" + listaRechazo.get(i).get(j));
                        }
                        break;
                    }
                }
                System.out.print("\t" + listaAceptacion.size() + "\t" + listaRechazo.size() + "\t");
                if (flag) {
                    System.out.print("yes");
                } else {
                    System.out.print("no");
                }
                System.out.println();
            }
        }
    }

    public String procesarListaCadenasString(ArrayList<String> cadenas, String nombreArchivo) {
        String procesamiento = "";
        boolean flag = false;
        for (String cadena : cadenas) {
            System.out.print(cadena + "\t");
            procesamiento += cadena + "\t";
            ArrayList<ArrayList<String>> lista = new ArrayList<>();

            ArrayList<ArrayList<String>> listaAceptacion = new ArrayList<>();
            ArrayList<ArrayList<String>> listaRechazo = new ArrayList<>();

            for (int i = 0; i < 50000 * cadena.length(); i++) {       //Funcion Probabilística
                recorridoAFPN(cadena, q0);
                if (!lista.contains(recorridos)) {
                    lista.add(recorridos);
                }
                this.recorridos = new ArrayList<>();
                this.stack = new Stack<>();
            }

            for (int i = 0; i < lista.size(); i++) {
                for (int j = 1; j < lista.get(i).size(); j++) {
                    if (lista.get(i).get(j).equals("false") || lista.get(i).get(j).equals("true")) {
                        if (lista.get(i).get(j).equals("false")) {
                            lista.get(i).set(j, "rejected");
                            listaRechazo.add(lista.get(i));
                        } else if (lista.get(i).get(j).equals("true")) {
                            lista.get(i).set(j, "acepted");
                            listaAceptacion.add(lista.get(i));
                        }
                    }
                }
            }

            if (!listaAceptacion.isEmpty()) {
                flag = true;
                for (int i = 0; i < listaAceptacion.size(); i++) {
                    System.out.print(listaAceptacion.get(i).get(0));
                    procesamiento += listaAceptacion.get(i).get(0);
                    for (int j = 0; j < listaAceptacion.get(i).size(); j++) {
                        if (listaAceptacion.get(i).get(j).equals("acepted")) {
                            System.out.print(">>");
                            procesamiento += ">>";
                            System.out.print(listaAceptacion.get(i).get(j));
                            procesamiento += listaAceptacion.get(i).get(j);
                            break;
                        }
                        System.out.print("->" + listaAceptacion.get(i).get(j));
                        procesamiento += "->" + listaAceptacion.get(i).get(j);
                    }
                    break;
                }
            } else {
                for (int i = 0; i < listaRechazo.size(); i++) {
                    System.out.print(listaRechazo.get(i).get(0));
                    procesamiento += listaRechazo.get(i).get(0);
                    for (int j = 0; j < listaRechazo.get(i).size(); j++) {
                        if (listaRechazo.get(i).get(j).equals("rejected")) {
                            System.out.print(">>");
                            procesamiento += ">>";
                            System.out.print(listaRechazo.get(i).get(j));
                            procesamiento += listaRechazo.get(i).get(j);
                            break;
                        }
                        System.out.print("->" + listaRechazo.get(i).get(j));
                        procesamiento += "->" + listaRechazo.get(i).get(j);
                    }
                    break;
                }
            }
            System.out.print("\t" + listaAceptacion.size() + "\t" + listaRechazo.size() + "\t");
            procesamiento += "\t" + listaAceptacion.size() + "\t" + listaRechazo.size() + "\t";
            if (flag) {
                System.out.print("yes");
                procesamiento += "yes";
            } else {
                System.out.print("no");
                procesamiento += "no";
            }
            System.out.println();
            procesamiento += "\n";
        }
        return procesamiento;
    }

    public AFPN hallarProductoCartesianoConAFD(AFD afd) {
        ArrayList<String> estados = new ArrayList<>();
        String estadoInicial = "";
        ArrayList<String> estadosFinales = new ArrayList<>();
        ArrayList<TransitionAFPN> transcisiones = new ArrayList<>();

        for (TransitionAFD DeltaAFD : afd.Delta) {
            for (TransitionAFPN DeltaAFPN : this.Delta) {
                if (DeltaAFD.getSymbol().equals(DeltaAFPN.getCharacter())) {
                    estados.add("(" + DeltaAFPN.getQ0() + "," + DeltaAFD.getInitialState() + ")");
                    estados.add("(" + DeltaAFPN.getFinalStates() + "," + DeltaAFD.getNextState() + ")");
                    transcisiones.add(new TransitionAFPN("(" + DeltaAFPN.getQ0() + "," + DeltaAFD.getInitialState() + ")", DeltaAFPN.getCharacter(), DeltaAFPN.getPilaIn(), "(" + DeltaAFPN.getFinalStates() + "," + DeltaAFD.getNextState() + ")", DeltaAFPN.getPilaOut()));
                    if (afd.F.contains(DeltaAFD.getNextState()) && this.F.contains(DeltaAFPN.getFinalStates()) && !estadosFinales.contains("(" + DeltaAFPN.getFinalStates() + "," + DeltaAFD.getNextState() + ")")) {
                        estadosFinales.add("(" + DeltaAFPN.getFinalStates() + "," + DeltaAFD.getNextState() + ")");
                    }
                } else if (String.valueOf(DeltaAFPN.getCharacter()).equals("$")) {
                    estados.add("(" + DeltaAFPN.getQ0() + "," + DeltaAFD.getInitialState() + ")");
                    estados.add("(" + DeltaAFPN.getFinalStates() + "," + DeltaAFD.getInitialState() + ")");
                    transcisiones.add(new TransitionAFPN("(" + DeltaAFPN.getQ0() + "," + DeltaAFD.getInitialState() + ")", DeltaAFPN.getCharacter(), DeltaAFPN.getPilaIn(), "(" + DeltaAFPN.getFinalStates() + "," + DeltaAFD.getInitialState() + ")", DeltaAFPN.getPilaOut()));
                    if (afd.F.contains(DeltaAFD.getNextState()) && this.F.contains(DeltaAFPN.getFinalStates()) && !estadosFinales.contains("(" + DeltaAFPN.getFinalStates() + "," + DeltaAFD.getNextState() + ")")) {
                        estadosFinales.add("(" + DeltaAFPN.getFinalStates() + "," + DeltaAFD.getNextState() + ")");
                    }
                }
            }
        }
        //          (Estados,estado Inicial, estados Aceptacion, Alfabeto, Alfabeto Pila, Transiciones)
        return new AFPN(estados, estadoInicial, estadosFinales, Sigma, Gamma, transcisiones); //Modificar
    }

    public ArrayList<TransitionAFPN> getDelta() {
        return Delta;
    }

    public void setDelta(ArrayList<TransitionAFPN> Delta) {
        this.Delta = Delta;
    }

    @Override
    public ArrayList<String> getGamma() {
        return Gamma;
    }

    @Override
    public void setGamma(ArrayList<String> gamma) {
        this.Gamma = gamma;
    }

    public ArrayList<String> getRecorridos() {
        return recorridos;
    }

    public void setRecorridos(ArrayList<String> recorridos) {
        this.recorridos = recorridos;
    }

    public Stack<String> getStack() {
        return stack;
    }

    public void setStack(Stack<String> stack) {
        this.stack = stack;
    }

    @Override
    public ArrayList<String> getSigma() {
        return Sigma;
    }

    @Override
    public void setSigma(ArrayList<String> Sigma) {
        this.Sigma = Sigma;
    }

    @Override
    public ArrayList<String> getQ() {
        return Q;
    }

    @Override
    public void setQ(ArrayList<String> Q) {
        this.Q = Q;
    }

    @Override
    public ArrayList<String> getF() {
        return F;
    }

    @Override
    public void setF(ArrayList<String> F) {
        this.F = F;
    }

    public String getQ0() {
        return q0;
    }

    public void setQ0(String q0) {
        this.q0 = q0;
    }

    @Override
    public String toString() {
        String AFPN = "#alphabet\n";
        for (int i = 0; i < Sigma.size(); i++) {
            AFPN += Sigma.get(i) + "\n";
        }
        AFPN += "#states\n";
        for (int i = 0; i < Q.size(); i++) {
            AFPN += Q.get(i) + "\n";
        }
        AFPN += "#initial\n";
        AFPN += q0 + "\n";
        AFPN += "#accepting\n";
        for (int i = 0; i < F.size(); i++) {
            AFPN += F.get(i) + "\n";
        }
        AFPN += "#transitions\n";
        for (int i = 0; i < Delta.size(); i++) {
            TransitionAFPN transition = Delta.get(i);
            AFPN += transition.getq0() + ":" + transition.getCharacter() + ":" + transition.getPilaIn() + ">" + transition.getFinalStates() + ":" + transition.getPilaOut() + "\n";
        }
        return AFPN;
    }
}
