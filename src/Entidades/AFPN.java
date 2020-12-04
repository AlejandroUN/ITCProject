package Entidades;

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

    public int computarTodosLosProcesamientos(String cadena) {
        System.out.println("Cadena: " + cadena);
        ArrayList<ArrayList<String>> lista = new ArrayList<>();
        for (int i = 0; i < 10000; i++) {       //Funcion Probabilística
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
                        System.out.print("rejected");
                    } else if (lista.get(i).get(j).equals("true")) {
                        System.out.print("acepted");
                    }
                    continue;
                }
                System.out.print("->" + lista.get(i).get(j));
            }
            System.out.println("");
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
        for (int i = 0; i < 10000; i++) {       //Funcion Probabilística
            recorridoAFPN(cadena, q0);
            if (!lista.contains(recorridos)) {
                lista.add(recorridos);
            }
            this.recorridos = new ArrayList<>();
            this.stack = new Stack<>();
        }
        for (ArrayList<String> lista1 : lista) {
            if (lista1.get(lista1.size() - 1).equals("true")) {
                return true;
            }
        }
        return false;
    }

    public void procesarListaCadenas(String cadena, String nombreArchivo) {
        getNextState(q0, cadena.charAt(0));
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
