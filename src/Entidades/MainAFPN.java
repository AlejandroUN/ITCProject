/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

public class MainAFPN {

    public static void main(String[] args) throws FileNotFoundException, IOException {

        ArrayList<String> alfabeto = new ArrayList<>();
        alfabeto.add("a");
        alfabeto.add("b");
        ArrayList<String> pila = new ArrayList<>();
        pila.add("A");
        pila.add("B");
        String cadena = "aabbaa";             //CADENA,   bbab     aabaaba

        ArrayList<String> estados = new ArrayList<>();
        estados.add("q0");
        estados.add("q1");
        estados.add("q2");
        estados.add("q3");
        

        String estadoInicial = "q0";
        ArrayList<String> estadosAceptacion = new ArrayList<>();
        estadosAceptacion.add("q1");
        estadosAceptacion.add("q3");

        ArrayList<TransicionNoDeterminista> transiciones = new ArrayList<>();
        transiciones.add(new TransicionNoDeterminista("q0", 'a', new String[]{"q0","q1","q3"}));
        transiciones.add(new TransicionNoDeterminista("q1", 'a', new String[]{"q1"}));
        transiciones.add(new TransicionNoDeterminista("q1", 'b', new String[]{"q2"}));
        transiciones.add(new TransicionNoDeterminista("q2", 'b', new String[]{"q1","q2"}));
        transiciones.add(new TransicionNoDeterminista("q3", 'b', new String[]{"q3"}));

        AFPN A1 = new AFPN(transiciones, alfabeto, pila, estados, estadoInicial, estadosAceptacion);
//        System.out.println(A1.procesarCadena(cadena));
        System.out.println(A1.posibles(cadena));
    }
}
