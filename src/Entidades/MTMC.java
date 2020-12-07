package Entidades;

import java.util.ArrayList;

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

    private MTMC(ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<String> Sigma, ArrayList<String> Gamma, ArrayList<String[]> Delta) {
        super(Q, q0, F);
        this.Sigma = Sigma;
        this.Gamma = Gamma;
        this.Delta = Delta;
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
    
    private boolean procesarCadenaConDetalles(String cadena){
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
    
    private void procesarListaCadenas(ArrayList<String> listaCadenas, String nombreArchivo, boolean imprimirPantalla){
        for(String cadena : listaCadenas){
            
        }
    }
}

