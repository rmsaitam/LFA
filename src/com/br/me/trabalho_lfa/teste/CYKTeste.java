/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.me.trabalho_lfa.teste;

import com.br.me.trabalho_lfa.CYK;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wesklei Migliorini <wesklei at wbezerra.com.br>
 */
public class CYKTeste {

    public static void main(String argv[]) {

        String inicio = "S";
        //String palavra = "abaab";
      //  String palavra = "aaa";
      //  String palavra = "abaa";
       // String palavra = "a";
       // String palavra = "aa";
      // String palavra = "baba";
        //String palavra = "aaa4aa";
       // String palavra = "bbbbbbbaa";
        String palavra = "ba";
        Map<String, List<String>> gramatica = new HashMap<String, List<String>>();

        List<String> deriva = new ArrayList<String>();
        deriva.add("AA");
        deriva.add("AS");
        deriva.add("b");
        gramatica.put("S", deriva);

        deriva = new ArrayList<String>();
        deriva.add("SA");
        deriva.add("AS");
        deriva.add("a");
        gramatica.put("A", deriva);

        CYK cyk = new CYK(palavra,gramatica, inicio);
        
        cyk.parseCYK();
        cyk.printMatriz();
        
        System.out.println("Aceitou? " + cyk.isAceito());
    }

}