package Entidades;

import java.util.ArrayList;
import java.util.Random;

public class AFPN extends AFP {

    private ArrayList<TransitionAFP> Delta;
    private ArrayList<String> Stack;
    private ArrayList<String> recorridos = new ArrayList<>();

    //          (Estados,   estado Inicial, estados Aceptacion, Alfabeto, Alfabeto Pila, Transiciones, pila)
    public AFPN(ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<String> Sigma, ArrayList<String> Gamma, ArrayList<TransitionAFP> Delta, ArrayList<String> Stack) {
        super(Q, q0, F, Sigma, Gamma);
        this.Delta = Delta;
        this.Stack = Stack;
    }

    public String[] getNextState(String estadoActual, char caracter) {
        for (TransitionAFP transicion : Delta) {
            if (transicion.getq0().equals(estadoActual) && transicion.getCharacter() == caracter) {
                return transicion.getFinalStates();        //Retornar estados finales
            }
        }
        return null;
    }

    public int posibles(String cadena) {
        ArrayList<ArrayList<String>> lista = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            recorridoAFPN(cadena, q0);
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
            recorridos.add(String.valueOf(F.contains(estadoActual)));
            return F.contains(estadoActual);
        } else {
//            System.out.println("Upps Error");
            recorridos.add("Upps Error");
            return false;
        }
    }

    public boolean procesarCadena(String cadena) {

        String actualState = q0;
        for (int i = 0; i < cadena.length(); i++) {
            actualState = getNextState(actualState, cadena.charAt(i))[0];
        }
        for (int j = 0; j < F.size(); j++) {        //¿Quedamos en un estado de aceptación?
            if (actualState.equals(F.get(j))) {
                return true;
            }
        }
        return false;
    }

    public int procesarCadenaConDetalles(String cadena) {   //Falta añadir que si no es Aceptada imprima todos los procesamientos fallidos
        return 0;
    }

    public int computarTodosLosProcesamientos(String cadena, String nombreArchivo) {
        getNextState(q0, cadena.charAt(0));
        return 0;
    }

    public ArrayList<TransitionAFP> getDelta() {
        return Delta;
    }

    public void setDelta(ArrayList<TransitionAFP> Delta) {
        this.Delta = Delta;
    }

    public ArrayList<String> getStack() {
        return Stack;
    }

    public void setStack(ArrayList<String> Stack) {
        this.Stack = Stack;
    }

    public ArrayList<String> getRecorridos() {
        return recorridos;
    }

    public void setRecorridos(ArrayList<String> recorridos) {
        this.recorridos = recorridos;
    }

}
