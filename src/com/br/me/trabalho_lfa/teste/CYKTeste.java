/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.me.trabalho_lfa.teste;

import com.br.me.trabalho_lfa.controller.CYK;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wesklei Migliorini <wesklei at wbezerra.com.br>
 */
public class CYKTeste {

    public static void mainTestes(String argv[]) {

        CYKTeste grama1 = new CYKTeste();
        
        if(!grama1.testeGramatica1()){
            System.out.println("ERROR TESTE IN GRAMA1");
        }else{
            System.out.println("SUCESS! All teste pass!");
        }
        
//        List<String> deriva = new ArrayList<String>();
//        deriva.add("AB");
//        deriva.add("XB");
//        gramatica.put("S", deriva);
//
//        deriva = new ArrayList<String>();
//        deriva.add("AB");
//        gramatica.put("X", deriva);
//
//        deriva = new ArrayList<String>();
//        deriva.add("a");
//        gramatica.put("A", deriva);
//
//        deriva = new ArrayList<String>();
//        deriva.add("b");
//        gramatica.put("B", deriva);
        // cyk.printMatriz();
    }

    public boolean testeGramatica1() {
        Map<String, List<String>> gramatica = new HashMap<String, List<String>>();
        String inicio = "S";

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

        List<String> palavrasAceitas = new ArrayList<String>();
        palavrasAceitas.add("abb");
        palavrasAceitas.add("abaab");
        palavrasAceitas.add("abaa");
        palavrasAceitas.add("aaa");
        palavrasAceitas.add("baba");
        palavrasAceitas.add("bbbbbbbaa");
        palavrasAceitas.add("aabaa");
        palavrasAceitas.add("aabaaaaaaaaaaaaaaaaaaaaabbbbbbbbbbaaaaaaaa");
        palavrasAceitas.add("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbabbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbba");
        //String palavra = "aaa4aa";
        List<String> palavrasRejeitadas = new ArrayList<String>();
        palavrasRejeitadas.add("a");
        palavrasRejeitadas.add("aaa4aa");
        palavrasRejeitadas.add("aaabbbb5aa");
        palavrasRejeitadas.add("bbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbba");

        //testa palavras aceitas
        for (String palavra : palavrasAceitas) {
            CYK cyk = new CYK(palavra, gramatica, inicio);
            cyk.parseCYK(false);

            if (!cyk.isAceito()) { //se nao aceitou ja retorna dando erro
                return cyk.isAceito();
            }
        }

        //testa palavras rejeitadas
        for (String palavra : palavrasRejeitadas) {
            CYK cyk = new CYK(palavra, gramatica, inicio);
            cyk.parseCYK(false);
            if (cyk.isAceito()) { //se aceitou ja retorna dando erro
                return !cyk.isAceito();
            }
        }

        //System.out.println("Teste Result " + cyk.isAceito());
        return true; //passou no teste
    }
}
