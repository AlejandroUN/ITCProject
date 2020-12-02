/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class GeneralMain {

    public static void main(String[] args) throws FileNotFoundException, IOException {
        
        // AFD
        /*
        AFD afd = new AFD("afdd");
        String[] cadenas = {"Cd","ABC","ABCd"};
        System.out.println(afd.procesarCadena("Cd"));
        System.out.println(afd.procesarCadena("ABC"));
        System.out.println(afd.procesarCadenaConDetalles("ABC"));
        afd.procesarListaCadenas(cadenas,"EsteEsElOriginalNombreDelArchivo",true);
        System.out.println(afd.toString());
        */
        // AFPN
        
        ArrayList<String> alfabeto = new ArrayList<>();
        alfabeto.add("a");
        alfabeto.add("b");
        ArrayList<String> pila = new ArrayList<>();
        pila.add("A");
        pila.add("B");
        String cadena = "abb";             //CADENA,   bbab     aabaaba

        ArrayList<String> estados = new ArrayList<>();
        estados.add("q0");
        estados.add("q1");
        estados.add("q2");
        estados.add("q3");
        

        String estadoInicial = "q0";
        ArrayList<String> estadosAceptacion = new ArrayList<>();
        estadosAceptacion.add("q1");
        estadosAceptacion.add("q3");

        ArrayList<TransitionAFP> transiciones = new ArrayList<>();
        transiciones.add(new TransitionAFP("q0", 'a', new String[]{"q0","q1","q3"}));
        transiciones.add(new TransitionAFP("q1", 'a', new String[]{"q1"}));
        transiciones.add(new TransitionAFP("q1", 'b', new String[]{"q2"}));
        transiciones.add(new TransitionAFP("q2", 'b', new String[]{"q1","q2"}));
        transiciones.add(new TransitionAFP("q3", 'b', new String[]{"q3"}));

        AFPN A1 = new AFPN(estados, estadoInicial, estadosAceptacion, alfabeto, alfabeto, transiciones, pila);
//        System.out.println(A1.procesarCadena(cadena));
        System.out.println(A1.posibles(cadena));
    }
}