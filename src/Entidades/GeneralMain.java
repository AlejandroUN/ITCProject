/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class GeneralMain {

    public static void main(String[] args) throws FileNotFoundException, IOException {
/*        
        // AFD
        
//        AFD afd = new AFD("afdd");
//        String[] cadenas = {"Cd","ABC","ABCd"};
//        System.out.println(afd.procesarCadena("Cd"));
//        System.out.println(afd.procesarCadena("ABC"));
//        System.out.println(afd.procesarCadenaConDetalles("ABC"));
//        afd.procesarListaCadenas(cadenas,"EsteEsElOriginalNombreDelArchivo",true);
//        System.out.println(afd.toString());
//        
//        // AFPD
//        
//        AFPD afpd = new AFPD("afpd");        
//        ArrayList<String> alfabetoafpd = new ArrayList<>();
//        alfabetoafpd.add("a");
//        alfabetoafpd.add("b");
//        ArrayList<String> gammaafpd = new ArrayList<>();
//        gammaafpd.add("A");
//        gammaafpd.add("B");
//        
//        String cadenaafpd = "aaabbb";
//                
//        ArrayList<String> estadosafpd = new ArrayList<>();
//        estadosafpd.add("q0");
//        String estadoInicialafpd = "q0";
//        ArrayList<String> estadosAceptacionafpd = new ArrayList<>();
//        estadosAceptacionafpd.add("q0");
                
        // AFPN

//        AFPN A1 = new AFPN("AFPN.pda");
//        String cadena = "abab";
//        System.out.println(A1.procesarCadena(cadena));
//        System.out.println(A1.procesarCadenaConDetalles(cadena));
//        System.out.println(A1.computarTodosLosProcesamientos(cadena,"nombreArchivo"));

        
//        MT mt = new MT("MaquinaDeTuring");
//        String[] cadenasmt = {"abb","abbb","abba"};
//        System.out.println(mt.procesarCadena(cadenasmt[0]));
//        System.out.println(mt.procesarCadena(cadenasmt[1]));
//        System.out.println(mt.procesarCadenaConDetalles(cadenasmt[0]));
//        System.out.println(mt.procesarCadenaConDetalles(cadenasmt[1]));
//        System.out.println(mt.procesarCadenaConDetalles(cadenasmt[2]));
//        mt.procesarListaCadenas(cadenasmt,"PruebaCadenasParaMaquinaTuring",true);
        */
    //    MTP
    
        String cadena = "aabbcc";
        String q0 = "q0";
        ArrayList<String> Sigma = new ArrayList<>();
        Sigma.add("A");
        Sigma.add("B");
        Sigma.add("C");
        Sigma.add("a");
        Sigma.add("b");
        Sigma.add("c");
        Sigma.add("!");
        Sigma.add("X");
        Sigma.add("Y");
        Sigma.add("Z");
        ArrayList<String> Q = new ArrayList<>();
        Q.add("q0");
        Q.add("q1");
        Q.add("q2");
        Q.add("q3");
        ArrayList<String> F = new ArrayList<>();
        F.add("q0");
        
        
        ArrayList<ArrayList<String>> S = new ArrayList<>();
        for (int i = 0; i <cadena.length() ; i++) {
            ArrayList<String> aux = new ArrayList<>();
            aux.add(String.valueOf(cadena.charAt(i)));
            S.add(aux);
        }
        
        ArrayList<TransitionMTP> Delta = new ArrayList<TransitionMTP>();
        Delta.add(new TransitionMTP("q0", new ArrayList<String>(Arrays.asList("a","!")), "q1", new ArrayList<String>(Arrays.asList("a","X")) ,">"));
        Delta.add(new TransitionMTP("q1", new ArrayList<String>(Arrays.asList("a","!")), "q1", new ArrayList<String>(Arrays.asList("a","!")) ,">"));
        Delta.add(new TransitionMTP("q1", new ArrayList<String>(Arrays.asList("b","Y")), "q1", new ArrayList<String>(Arrays.asList("b","Y")) ,">"));
        Delta.add(new TransitionMTP("q1", new ArrayList<String>(Arrays.asList("b","!")), "q2", new ArrayList<String>(Arrays.asList("b","Y")) ,">"));
        Delta.add(new TransitionMTP("q2", new ArrayList<String>(Arrays.asList("b","!")), "q2", new ArrayList<String>(Arrays.asList("b","!")) ,">"));
        Delta.add(new TransitionMTP("q2", new ArrayList<String>(Arrays.asList("c","Z")), "q2", new ArrayList<String>(Arrays.asList("c","Z")) ,">"));
        Delta.add(new TransitionMTP("q2", new ArrayList<String>(Arrays.asList("c","!")), "q3", new ArrayList<String>(Arrays.asList("c","Z")) ,"<"));
        Delta.add(new TransitionMTP("q3", new ArrayList<String>(Arrays.asList("b","!")), "q3", new ArrayList<String>(Arrays.asList("b","X")) ,"<"));
        Delta.add(new TransitionMTP("q3", new ArrayList<String>(Arrays.asList("a","!")), "q3", new ArrayList<String>(Arrays.asList("a","!")) ,"<"));
        Delta.add(new TransitionMTP("q3", new ArrayList<String>(Arrays.asList("b","Y")), "q3", new ArrayList<String>(Arrays.asList("b","Y")) ,"<"));
        Delta.add(new TransitionMTP("q3", new ArrayList<String>(Arrays.asList("c","Z")), "q3", new ArrayList<String>(Arrays.asList("c","Z")) ,"<"));
        Delta.add(new TransitionMTP("q3", new ArrayList<String>(Arrays.asList("a","X")), "q0", new ArrayList<String>(Arrays.asList("a","X")) ,">"));
        
        MTP mtp1 = new MTP(Sigma, Sigma, Q, "q0", F, Delta, S);
        System.out.println(mtp1.procesarCadena(cadena));
        
        
        


    }
}


