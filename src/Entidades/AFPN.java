package Entidades;

import java.io.*;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.Random;
import java.util.Stack;

public class AFPN extends AFP {

    private ArrayList<TransitionAFPN> Delta;
    private Stack<String> stack;
    private ArrayList<String> recorridos = new ArrayList<>();

    //          (Estados,   estado Inicial, estados Aceptacion, Alfabeto, Alfabeto Pila, Transiciones)
    public AFPN(ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<String> Sigma, ArrayList<String> Gamma, ArrayList<TransitionAFPN> Delta) {
        super(Q, q0, F, Sigma, Gamma);
        this.Delta = Delta;
        this.stack = new Stack<>();
    }

    public TransitionAFPN getNextState(String estadoActual, char caracter) {
        int index = new Random().nextInt(this.Delta.size());
        TransitionAFPN transicion = this.Delta.get(index);
        if (transicion.getq0().equals(estadoActual) && transicion.getCharacter() == caracter) {
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

    public boolean recorridoAFPN(String cadena, String state) {
        String estadoActual = state;
        String cadenaAlterna = cadena;
//        System.out.print("(" + estadoActual + "," + cadena + ")");
        recorridos.add("(" + estadoActual + "," + cadena + "," + verPila() + ")");
        try {
            TransitionAFPN aux = getNextState(estadoActual, cadenaAlterna.charAt(0));
            String[] s = aux.getFinalStates();
            estadoActual = s[new Random().nextInt(s.length)];
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
//            System.out.print("(" + estadoActual + ",$)");
            recorridos.add("(" + estadoActual + ",$," + verPila() + ")");
            recorridos.add(String.valueOf(F.contains(estadoActual) && this.stack.isEmpty()));
            return F.contains(estadoActual) && this.stack.isEmpty();
        } else {
//            System.out.println("Upps Error");
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

    public void procesarListaCadenas(ArrayList<String> cadenas, String nombreArchivo, boolean imprimirPantalla) {
        try (FileWriter fw = new FileWriter(nombreArchivo, false);
                BufferedWriter bw = new BufferedWriter(fw);
                PrintWriter out = new PrintWriter(bw)) {
            for (String cadena : cadenas) {
                out.print(cadena + "\t");
            }
        } catch (IOException e) {
            System.out.println("Error, problemas al crear el Documento" + nombreArchivo);
        }
        if (imprimirPantalla) {
//            Se imprime el resultado
        }
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

}
