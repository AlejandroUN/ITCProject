/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aleja
 */
public class Mt extends Afd {   
    
    private  ArrayList<String> gamma = new ArrayList<String>();
    private String tape = "";   

    public Mt(ArrayList<String> setSigma, ArrayList<String> states, String initialState, ArrayList<String> acceptanceStates, ArrayList<TransitionAFD> transitions, ArrayList<String> gamma) {
        super(setSigma, states, initialState, acceptanceStates, transitions);
        this.gamma = gamma;
    }
    
    public Mt(String nombreArchivo) {
        super(nombreArchivo);
    }

    @Override
    public String toString() {
        return super.toString(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<TransitionAFD> getTransitions() {
        return super.getTransitions(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getAcceptanceStates() {
        return super.getAcceptanceStates(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getInitialState() {
        return super.getInitialState(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getStates() {
        return super.getStates(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<String> getSigma() {
        return super.getSigma(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void procesarListaCadenas(String[] listaCadenas, String nombreArchivo, boolean imprimirPantalla) {
        super.procesarListaCadenas(listaCadenas, nombreArchivo, imprimirPantalla); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean procesarCadenaConDetalles(String cadena) {
        return super.procesarCadenaConDetalles(cadena); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean procesarCadena(String cadena) {
        return super.procesarCadena(cadena); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public String getNextState(String currentState, String symbol) {
        return super.getNextState(currentState, symbol); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean setAtributesGivenAFile(String nombreArchivo) {
        return super.setAtributesGivenAFile(nombreArchivo); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean isEmpty() {
        return super.isEmpty(); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean personalContain(String s1, String s2) {
        return super.personalContain(s1, s2); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void addToAlphabetFromARange(String range) {
        super.addToAlphabetFromARange(range); //To change body of generated methods, choose Tools | Templates.
    }
    
    
}
