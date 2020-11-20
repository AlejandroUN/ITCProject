/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Entidades;

import static Entidades.Afd.getNormalString;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author aleja
 */
public class TESTREADERS {
    public boolean setAtributesGivenAFile(String nombreArchivo) {
        boolean state = false;
        String curSection = "WTF";
        String fileName = nombreArchivo + ".dfa";
        String curLine = "";                
        System.out.println(fileName);        
        try {
            BufferedReader myReader = new BufferedReader(new FileReader((fileName)));
            //Scanner myReader = new Scanner(new FileReader(fileName));
            while ((curLine = myReader.readLine()) != null) {

            }
            System.out.println(curLine + " " + curLine.length());
            myReader.close();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TESTREADERS.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(TESTREADERS.class.getName()).log(Level.SEVERE, null, ex);
        }
        return state;
    }
    
    public static void main(String[] args) throws IOException{
//        TESTREADERS tr = new TESTREADERS();
//        tr.setAtributesGivenAFile("afd");
BufferedReader br;
        try {
            br = new BufferedReader(new FileReader("afd.dfa"));
            String s;
        while ((s = br.readLine()) != null) {
            System.out.println(s);
        }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(TESTREADERS.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
}
