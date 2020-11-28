/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class Afp {
    
    private ArrayList<String> states = new ArrayList<String>();
    private ArrayList<String> setSigma = new ArrayList<String>();
    private ArrayList<String> setGamma = new ArrayList<String>();
    private ArrayList<String> aceptationStates = new ArrayList<String>();
    private ArrayList<String[]> transitionsSet = new ArrayList<String[]>();
    private String intialState;
    
    private String stackFirstSymbol;
    private String actualState;
    private Stack<String> stack;
    
}
