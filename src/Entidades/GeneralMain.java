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
        ArrayList<String> gama = new ArrayList<>();
        gama.add("A");
        gama.add("B");
        
        String cadena = "ababa";             //CADENA,   bbab     aabaaba

        ArrayList<String> estados = new ArrayList<>();
        estados.add("q0");

        String estadoInicial = "q0";
        ArrayList<String> estadosAceptacion = new ArrayList<>();
        estadosAceptacion.add("q0");

        ArrayList<TransitionAFPN> transiciones = new ArrayList<>();
        transiciones.add(new TransitionAFPN("q0", 'a', 'A', new String[]{"q0"}, '$'));
        transiciones.add(new TransitionAFPN("q0", 'b', 'B', new String[]{"q0"}, '$'));
        transiciones.add(new TransitionAFPN("q0", 'a', '$', new String[]{"q0"}, 'B'));
        transiciones.add(new TransitionAFPN("q0", 'b', '$', new String[]{"q0"}, 'A'));

        AFPN A1 = new AFPN(estados, estadoInicial, estadosAceptacion, alfabeto, gama, transiciones);
        System.out.println(A1.procesarCadena(cadena));
//        System.out.println(A1.computarTodosLosProcesamientos(cadena));
    }
}