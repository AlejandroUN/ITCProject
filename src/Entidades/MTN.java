package Entidades;

import java.util.ArrayList;

public class MTN extends MT{
    
    public MTN(ArrayList<String> Sigma, ArrayList<String> Gamma, ArrayList<String> Q, String q0, ArrayList<String> F, ArrayList<TransitionMT> Delta) {
        super(Sigma, Gamma, Q, q0, F, Delta);
    }
    
    @Override
    public boolean procesarCadena(String cadena) {
        int i = 0;
        String currentState = q0;
        boolean flag = true;
        while (flag){
            char[] temp = cadena.toCharArray();
            char symbol  = temp[i];
            if (Delta.contains(currentState) && Delta.contains(symbol)){
                for (TransitionMT mt : Delta){
                    TransitionMT nsetup = mt;
                    currentState = nsetup.initialState;
                    String nsymbol = nsetup.symbol;
                    if (nsetup.nextState == ">"){
                        cadena = procesarCadenaMini(i, cadena, nsymbol, 1);
                        i++;
                    }
                    else if (nsetup.nextState == "<"){
                        cadena = procesarCadenaMini(i, cadena, nsymbol, 0);
                        i--;
                    }
                    else {
                        String[] ncadena = null;
                        if (nsymbol != "!"){
                            ncadena[i] = nsymbol;
                        }
                        cadena = ncadena[i];
                    }
                    if (F.contains(currentState)){
                        return true;
                    }
                }
            }
        }
        return false;
    } 
    
    public String procesarCadenaMini(int i, String cadena, String symbol, int direction){
        if (direction == 1){
            if (symbol != "!"){
                cadena = symbol;
            }
            if ((i+1 >= cadena.length()) || (i-1 < cadena.length())){
                cadena = "!";
            }
        }
        return cadena;
    }
    
    @Override
    public String toString() {
        String mtn = "#inputAlphabet\n";
        for(int i = 0; i < Sigma.size(); i++){
            mtn += Sigma.get(i) + "\n";
        }
        mtn += "#tapeAlphabet\n";
        for(int i = 0; i < Gamma.size(); i++){
            mtn += Gamma.get(i) + "\n";
        }
        mtn += "#states\n";
        for(int i = 0; i < Q.size(); i++){
            mtn += Q.get(i) + "\n";
        }
        mtn += "#initial\n";
        mtn += q0 + "\n";
        mtn += "#accepting\n";
        for(int i = 0; i < F.size(); i++){
            mtn += F.get(i) + "\n";
        }
        mtn += "#transitions\n";
        for(int i = 0; i < Delta.size(); i++){
            TransitionMT transition = Delta.get(i);
            mtn += transition.getInitialState() + ":" + transition.getSymbol() + "?" + transition.getNextState()  
                    + ":" + transition.getNextSymbol() + ":" + transition.getDisplacement() +"\n";
        }
        return mtn;
    }
    
}

