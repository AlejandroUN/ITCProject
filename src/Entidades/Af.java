
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

public class Af {
    
    private  ArrayList<String> setSigma = new ArrayList<String>();
    private  ArrayList<String> states = new ArrayList<String>();
    private static String initialState = "";
    private static ArrayList<String> acceptanceStates = new ArrayList<String>();
    private static ArrayList<TransitionAFD> transitions = new ArrayList<TransitionAFD>(); 
    
}
