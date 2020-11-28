package Entidades;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class AFPN {

    private ArrayList<TransicionNoDeterminista> transiciones;
    private ArrayList<String> alfabeto;
    private ArrayList<String> pila;
    private ArrayList<String> estados;
    private String estadoInicial;
    private ArrayList<String> estadosAceptacion;
    private ArrayList<String> recorridos = new ArrayList<>();

    public AFPN(ArrayList<TransicionNoDeterminista> transiciones, ArrayList<String> alfabeto, ArrayList<String> pila, ArrayList<String> estados, String estadoInicial, ArrayList<String> estadosAceptacion) {
        this.transiciones = transiciones;
        this.alfabeto = alfabeto;
        this.pila = pila;
        this.estados = estados;
        this.estadoInicial = estadoInicial;
        this.estadosAceptacion = estadosAceptacion;
    }

    public ArrayList<TransicionNoDeterminista> getTransiciones() {
        return transiciones;
    }

    public void setTransiciones(ArrayList<TransicionNoDeterminista> transiciones) {
        this.transiciones = transiciones;
    }

    public ArrayList<String> getAlfabeto() {
        return alfabeto;
    }

    public void setAlfabeto(ArrayList<String> alfabeto) {
        this.alfabeto = alfabeto;
    }

    public ArrayList<String> getPila() {
        return pila;
    }

    public void setPila(ArrayList<String> pila) {
        this.pila = pila;
    }

    public ArrayList<String> getEstados() {
        return estados;
    }

    public void setEstados(ArrayList<String> estados) {
        this.estados = estados;
    }

    public String getEstadoInicial() {
        return estadoInicial;
    }

    public void setEstadoInicial(String estadoInicial) {
        this.estadoInicial = estadoInicial;
    }

    public ArrayList<String> getEstadosAceptacion() {
        return estadosAceptacion;
    }

    public void setEstadosAceptacion(ArrayList<String> estadosAceptacion) {
        this.estadosAceptacion = estadosAceptacion;
    }

    public ArrayList<String> getRecorridos() {
        return recorridos;
    }

    public void setRecorridos(ArrayList<String> recorridos) {
        this.recorridos = recorridos;
    }

    public String[] getNextState(String estadoActual, char caracter) {
        for (TransicionNoDeterminista transicion : transiciones) {
            if (transicion.getEstadoInicial().equals(estadoActual) && transicion.getCaracter() == caracter) {
//                return transicion.getEstadosFinales()[0];        //Retornar estados finales
                return transicion.getEstadosFinales();        //Retornar estados finales
            }
        }
        return null;
    }

    public int posibles(String cadena) {
        ArrayList<ArrayList<String>> lista = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            recorridoAFPN(cadena, estadoInicial);
            if (!lista.contains(recorridos)) {
                lista.add(recorridos);
            }
            this.recorridos = new ArrayList<>();
        }
        for (int i = 0; i < lista.size(); i++) {
            System.out.println(lista.get(i));
        }
        System.out.println("");
        return lista.size();
    }

    public boolean recorridoAFPN(String cadena, String state) {
        String estadoActual = state;
        String cadenaAlterna = cadena;
//        System.out.print("(" + estadoActual + "," + cadena + ")");
        recorridos.add("(" + estadoActual + "," + cadena + ")");
        
        try {
            estadoActual = getNextState(estadoActual, cadenaAlterna.charAt(0))[new Random().nextInt(getNextState(estadoActual, cadenaAlterna.charAt(0)).length)];
        } catch (NullPointerException nu) {
            recorridos.add("Procesamiento Abortado");
            return false;
        }
        
        if (cadenaAlterna.length() > 1) {
            cadenaAlterna = cadenaAlterna.substring(1);         //Eliminamos el primer caracter
            return recorridoAFPN(cadenaAlterna, estadoActual);
        }
        if (cadenaAlterna.length() == 1) {   //No ha mas cadena pa recorrer y estado Aceptacion
//            System.out.print("(" + estadoActual + ",$)");
            recorridos.add("(" + estadoActual + ",$)");
            recorridos.add(String.valueOf(estadosAceptacion.contains(estadoActual)));
            return estadosAceptacion.contains(estadoActual);
        } else {
//            System.out.println("Upps Error");
            recorridos.add("Upps Error");
            return false;
        }
    }

    public boolean procesarCadena(String cadena) {
        
        String estadoActual = estadoInicial;
        for (int i = 0; i < cadena.length(); i++) {
            estadoActual = getNextState(estadoActual, cadena.charAt(i))[0];
        }
        for (int j = 0; j < estadosAceptacion.size(); j++) {        //¿Quedamos en un estado de aceptación?
            if (estadoActual.equals(estadosAceptacion.get(j))) {
                return true;
            }
        }
        return false;
    }

    public int procesarCadenaConDetalles(String cadena) {   //Falta añadir que si no es Aceptada imprima todos los procesamientos fallidos
        return 0;   
    }

    public int computarTodosLosProcesamientos(String cadena, String nombreArchivo) {
        getNextState(estadoInicial, cadena.charAt(0));
        return 0;
    }

}
