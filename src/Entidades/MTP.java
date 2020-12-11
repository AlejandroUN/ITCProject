package Entidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Super class for turing machines
 */
public class MTP extends AF {

    private ArrayList<String> Sigma = new ArrayList<String>(); // Alfabeto de cinta
    private ArrayList<String> Gamma = new ArrayList<String>(); // Alfabeto de pila  
    private ArrayList<TransitionMTP> Delta = new ArrayList<TransitionMTP>();
    private ArrayList<ArrayList<String>> Track = new ArrayList<>();
    private ArrayList<String> recorridos = new ArrayList<>();

    public MTP(ArrayList<String> Sigma, ArrayList<String> Gamma, ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<TransitionMTP> Delta, ArrayList<ArrayList<String>> Track) {
        super(Q, q0, F);
        this.Sigma = Sigma;
        this.Gamma = Gamma;
        this.Delta = Delta;
        this.Track = Track;
        Sigma.add("!");
        Gamma.add("!");
    }

    public MTP(String nombreArchivo) {
        //setAtributesGivenAFile(nombreArchivo);
        Sigma.add("!");
        Gamma.add("!");
    }

    public TransitionMTP getNextStep(String currentState, ArrayList<String> pista) {
        //System.out.println(currentState);
        for (int i = 0; i < Delta.size(); i++) {
            TransitionMTP T = this.Delta.get(i);
            if (T.getInitialState().equals(currentState) && T.getSymbol() == pista) {
                return T;
            }
        }
        return null;
    }

    public String replaceChar(String str, char ch, int index) {
        char[] chars = str.toCharArray();
        chars[index] = ch;
        return String.valueOf(chars);
    }

    public boolean recorridoMTP(String cadena, String state) {
        String actualState = state;
        int index = 0;
        String nextState = "";
        String desplazamiento;
        ArrayList<ArrayList<String>> s = new ArrayList<>();
        for (int i = 0; i < cadena.length(); i++) {
            s.add(new ArrayList<>(Arrays.asList(String.valueOf(cadena.charAt(i)), "!")));

        }
        while (index < cadena.length()) {
            if (index < 0) {
                try {
                    nextState = getNextStep(actualState, s.get(index)).getNextState();
                    desplazamiento = getNextStep(actualState, s.get(index)).getDisplacement();
                    cadena = "!" + cadena;
                } catch (NullPointerException e) {
                    System.out.println("procesamiento abortado 1");
                    return false;
                }
            } else if (index > cadena.length() - 1) {
                try {
                    nextState = getNextStep(actualState, s.get(index)).getNextState();
                    desplazamiento = getNextStep(actualState, s.get(index)).getDisplacement();
                    cadena = cadena + "!";
                } catch (NullPointerException e) {
                    System.out.println("procesamiento abortado 2");
                    return false;
                }
            } else {
                try {
                    nextState = getNextStep(actualState, s.get(index)).getNextState();
                    System.out.println(getNextStep(actualState, s.get(index)));
                    desplazamiento = getNextStep(actualState, s.get(index)).getDisplacement();
                    cadena = replaceChar(cadena, getNextStep(actualState, s.get(index)).getSymbol().get(0).charAt(0), index);
                } catch (NullPointerException e) {
                    System.out.println("procesamiento abortado 3");
                    System.out.println(cadena);
                    return false;
                }
            }
            switch (desplazamiento) {
                case "<":
                    index--;
                    break;
                case ">":
                    index++;
                    break;
                default:
                    System.out.println("procesamiento abortado, desplazamiento invalido");
                    return false;
            }
        }

        return this.F.contains(actualState);
    }

    public boolean transitionExists(String state, ArrayList<String> pista) {
        for (int i = 0; i < Delta.size(); i++) {
            if (Delta.get(i).getInitialState().equals(state) && Delta.get(i).getSymbol().equals(pista)) {
                return true;
            }
        }
        return false;
    }

    public boolean procesarCadena(String cadena) {
        return recorridoMTP(cadena, this.q0);
    }

    /*
    
    public boolean procesarCadenaConDetalles(String cadena) {                  //no borrar
        boolean sigmaContainsStringCharacters = true;
        String currentState = q0;
        String nextState = "";
        String desplacement = "";
        String process = "(" + currentState + ")" + cadena + "->";
        int currentStringIndex = 0;
        while (((currentStringIndex) <= cadena.length()) && !isAnAcceptanceState(currentState)
                && sigmaContainsStringCharacters) {
            char[] aux = cadena.toCharArray();
            for (int i = 0; i < aux.length; i++) {
                if (!Sigma.contains(String.valueOf(cadena.charAt(i)))) {
                    sigmaContainsStringCharacters = false;
                    break;
                }
            }
            if (currentStringIndex < 0) {
                if (!transitionExists(currentState, "!")) {
                    break;
                }
                nextState = getNextStepGivenATransition(currentState, "!")[0];
                desplacement = getNextStepGivenATransition(currentState, "!")[2];
                cadena = "!" + cadena;
            } else if (!((currentStringIndex) <= cadena.length() - 1)) {
                if (!transitionExists(currentState, "!")) {
                    break;
                }
                nextState = getNextStepGivenATransition(currentState, "!")[0];
                desplacement = getNextStepGivenATransition(currentState, "!")[2];
                cadena = cadena + "!";
            } else {
                if (!transitionExists(currentState, String.valueOf(cadena.charAt(currentStringIndex)))) {
                    break;
                }
                nextState = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[0];
                desplacement = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2];
                cadena = replaceChar(cadena, getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[1].charAt(0), currentStringIndex);
            }
            if (personalContain(desplacement, ">")) {
                currentStringIndex++;
            } else if (getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2] == "<") {
                currentStringIndex--;
            }
            currentState = nextState;
            if (currentStringIndex < 0) {
                process += "!(" + currentState + ")" + cadena + "->";
            } else if (currentStringIndex > cadena.length() - 1) {
                process += cadena + "(" + currentState + ")!" + "->";
            } else {
                process += cadena.substring(0, currentStringIndex) + "(" + currentState + ")" + cadena.substring(currentStringIndex) + "->";
            }
        }
        process = process.substring(0, process.length() - 2);
        System.out.println(process);
        return isAnAcceptanceState(currentState);
    }

     */
 /*
    public void procesarListaCadenas(String[] listaCadenas, String nombreArchivo, boolean imprimirPantalla) {     //no borrar
        String process = "";
        for (int j = 0; j < listaCadenas.length; j++) {
            boolean sigmaContainsStringCharacters = true;
            String currentState = q0;
            String cadena = listaCadenas[j];
            String currentString = cadena;
            String nextState = "";
            String desplacement = "";
            process += "Cadena: " + cadena + "\t";
            process += "(" + currentState + ")" + cadena + "->";
            int currentStringIndex = 0;
            for (int i = 0; i < listaCadenas[0].length(); i++) {
                if (!Sigma.contains(String.valueOf(cadena.charAt(i)))) {
                    sigmaContainsStringCharacters = false;
                    break;
                }
            }
            while (((currentStringIndex) <= cadena.length()) && !isAnAcceptanceState(currentState)
                    && sigmaContainsStringCharacters) {
                char[] aux = cadena.toCharArray();
                for (int i = 0; i < aux.length; i++) {
                    if (!Sigma.contains(String.valueOf(cadena.charAt(i)))) {
                        sigmaContainsStringCharacters = false;
                        break;
                    }
                }
                if (currentStringIndex < 0) {
                    if (!transitionExists(currentState, "!")) {
                        break;
                    }
                    nextState = getNextStepGivenATransition(currentState, "!")[0];
                    desplacement = getNextStepGivenATransition(currentState, "!")[2];
                    cadena = "!" + cadena;
                } else if (!((currentStringIndex) <= cadena.length() - 1)) {
                    if (!transitionExists(currentState, "!")) {
                        break;
                    }
                    nextState = getNextStepGivenATransition(currentState, "!")[0];
                    desplacement = getNextStepGivenATransition(currentState, "!")[2];
                    cadena = cadena + "!";
                } else {
                    if (!transitionExists(currentState, String.valueOf(cadena.charAt(currentStringIndex)))) {
                        break;
                    }
                    nextState = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[0];
                    desplacement = getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2];
                    cadena = replaceChar(cadena, getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[1].charAt(0), currentStringIndex);
                }
                if (personalContain(desplacement, ">")) {
                    currentStringIndex++;
                } else if (getNextStepGivenATransition(currentState, String.valueOf(cadena.charAt(currentStringIndex)))[2] == "<") {
                    currentStringIndex--;
                }
                currentState = nextState;
                if (currentStringIndex < 0) {
                    process += "!(" + currentState + ")" + cadena + "->";
                } else if (currentStringIndex > cadena.length() - 1) {
                    process += cadena + "(" + currentState + ")!" + "->";
                } else {
                    process += cadena.substring(0, currentStringIndex) + "(" + currentState + ")" + cadena.substring(currentStringIndex) + "->";
                }
            }
            process = process.substring(0, process.length() - 2);
            for (int k = 0; k < F.size(); k++) {
                if (sigmaContainsStringCharacters) {
                    if (currentState.equals(F.get(k))) {
                        process += "\taccepted\t yes";
                        break;
                    } else {
                        process += "\trejected\t no";
                        break;
                    }
                } else {
                    process += "\trejected\t no";
                    break;
                }
            }
            process += "\n";
        }
        try {
            if (nombreArchivo.contains(" ")) {
                FileWriter myWriter = new FileWriter("default.dfa");
                myWriter.write(process);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            } else {
                FileWriter myWriter = new FileWriter(nombreArchivo + ".tm");
                myWriter.write(process);
                myWriter.close();
                System.out.println("Successfully wrote to the file.");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
        if (imprimirPantalla) {
            System.out.println(process);
        }
    }
     */
    public ArrayList<String> getSigma() {
        return Sigma;
    }

    public void setSigma(ArrayList<String> Sigma) {
        this.Sigma = Sigma;
    }

    public ArrayList<String> getGamma() {
        return Gamma;
    }

    public void setGamma(ArrayList<String> Gamma) {
        this.Gamma = Gamma;
    }

    public ArrayList<TransitionMTP> getDelta() {
        return Delta;
    }

    public void setDelta(ArrayList<TransitionMTP> Delta) {
        this.Delta = Delta;
    }

    public ArrayList<String> getQ() {
        return Q;
    }

    public void setQ(ArrayList<String> Q) {
        this.Q = Q;
    }

    public String getQ0() {
        return q0;
    }

    public void setQ0(String q0) {
        this.q0 = q0;
    }

    public ArrayList<String> getF() {
        return F;
    }

    public void setF(ArrayList<String> F) {
        this.F = F;
    }

}
