/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.me.trabalho_lfa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wesklei Migliorini <wesklei at wbezerra.com.br>
 */
public class CYK {

    private final String palavra;
    private final Map<String, List<String>> gramatica;
    private final Boolean aceitou = false;
    private List<List<String>> matriz;

    public CYK(String palavra, Map<String, List<String>> gramatica) {
        this.palavra = palavra;
        this.gramatica = gramatica;

        matriz = new ArrayList<List<String>>();

        //monta a base da matriz        
        List<String> baseMatriz = new ArrayList<String>();
        for (Character base : palavra.toCharArray()) {
            baseMatriz.add(base.toString());
        }
        matriz.add(baseMatriz);
    }

    public boolean parseCYK() {

        matriz = new ArrayList<List<String>>();

        //monta a base da matriz        
        List<String> baseMatriz = new ArrayList<String>();
        for (Character base : palavra.toCharArray()) {
            baseMatriz.add(base.toString());
        }
        matriz.add(baseMatriz);

        //derivacoes
        List<String> linhaAnterior = baseMatriz;
        List<String> linhaAtual = new ArrayList<String>();
       // int topo = 0;
      //  do {

            for (String s : linhaAnterior) {
                List<String> chaves = getChave(s);

                for (String c : chaves) {
                    linhaAtual.add(c);
                }
            }

            linhaAnterior = linhaAtual;
            matriz.add(linhaAtual);
            
         //   topo++;
        //} while (!linhaAtual.isEmpty() && topo < palavra.length());

        printMatriz();
        return aceitou;
    }

    /**
     * retorna as chaves de uma letra da palavra
     *
     * @param letra
     * @return lista vazia se nao achou nada
     */
    private List<String> getChave(String letra) {
        List<String> retorno = new ArrayList<String>();

        for (Map.Entry<String, List<String>> e : gramatica.entrySet()) {
            String key = e.getKey();
            List<String> value = e.getValue();

            for (String s : value) {
                if (s.equals(letra)) {
                    retorno.add(key); //achou a chave
                }
            }
        }

        return retorno;
    }

    public boolean isAceptable() {
        return aceitou;
    }

    public void printMatriz() {

        Collections.reverse(matriz); //usa para mostrar na ordem correta
        for (List<String> linha : matriz) {
            for (String coluna : linha) {
                System.out.print("  |   " + coluna);
            }
            System.out.println("  |");
        }
        Collections.reverse(matriz);
    }

    public void printEmptyMatriz() {

        //matriz vazia
        for (int linha = 0; linha <= palavra.length(); linha++) {
            for (int coluna = 0; coluna <= linha; coluna++) {
                System.out.print("  |");
            }
            System.out.println("  |");
        }

        //base da matriz
        for (int coluna = 0; coluna <= palavra.length(); coluna++) {
            System.out.print("  |");
        }
        System.out.println("  |");

    }

    public void printTree() {

    }

}
