/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.br.me.trabalho_lfa.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Wesklei Migliorini <wesklei at gmail.com>
 */
public class CYK {

    private final String palavra;
    private final String inicio;
    private final Map<String, List<String>> gramatica;
    private Boolean aceitou = false;
    private List<List<String>> matriz;

    public CYK(String palavra, Map<String, List<String>> gramatica, String inicio) {
        this.palavra = palavra;
        this.gramatica = gramatica;
        this.inicio = inicio;

        matriz = new ArrayList<List<String>>();
    }

    /**
     *  Faz o parse usando o algoritmo CYK
     * @param isPassoAPasso
     * @return retorna uma string contendo todo o output do programa caso seja passado true
     * para isPassoAPasso, se nao retorna uma string vazia
     */
    public String parseCYK(boolean isPassoAPasso) {

        String retorno = "";
        if (isPassoAPasso) {
            retorno += "=> [INICIO] TESTANDO PALAVRA: " + palavra;
            retorno += "\n";// linha vazia
            retorno += printEmptyMatriz();
            retorno += "\n";// linha vazia
        }

        matriz = new ArrayList<List<String>>();

        //monta a base da matriz com a palavra      
        List<String> baseMatriz = new ArrayList<String>();
        for (Character base : palavra.toCharArray()) {
            baseMatriz.add(base.toString());
        }
        matriz.add(baseMatriz);

        if (isPassoAPasso) {
            retorno += "\n";// linha vazia
            retorno += printMatriz();
            retorno += "\n";// linha vazia

        }

        //primeira derivacao, o que leva a cada caracter da palavra
        List<String> linhaAnterior = baseMatriz; //a propria palavra
        List<String> linhaAtual = new ArrayList<String>();

        for (String s : linhaAnterior) {
            String chaves = getChave(s);
            linhaAtual.add(chaves);
        }
        //guarda a linha gerada
        matriz.add(linhaAtual);

        if (isPassoAPasso) {
            retorno += "\n";// linha vazia
            retorno += printMatriz();
            retorno += "\n";// linha vazia
        }

        //derivacoes
        for (int s = 2; s <= palavra.length(); s++) {
            linhaAtual = new ArrayList<String>();

            for (int r = 1; r <= palavra.length() - (s - 1); r++) {

                for (int k = 1; k <= s - 1; k++) {

                    String celulaB = matriz.get(k).get(r - 1);
                    String celulaC = matriz.get(s - k).get((r + k) - 1);
                    String[] celulaContentsB;
                    if (celulaB.contains(",")) {
                        celulaContentsB = celulaB.split(",");
                    } else {
                        celulaContentsB = new String[1];
                        celulaContentsB[0] = celulaB;
                    }

                    String[] celulaContentsC;
                    if (celulaC.contains(",")) {
                        celulaContentsC = celulaC.split(",");
                    } else {
                        celulaContentsC = new String[1];
                        celulaContentsC[0] = celulaC;
                    }

                    String celulaGerada = "";
                    //pega o que ja existe na linha
                    if (linhaAtual.size() > 0 && linhaAtual.size() >= r && !linhaAtual.get(r - 1).isEmpty()) {
                        celulaGerada += linhaAtual.get(r - 1) + ",";
                    }

                    //pega a nova celula
                    for (String B : celulaContentsB) {
                        for (String C : celulaContentsC) {

                            String celChave = getChave(B + C);

                            //remove caracters repetidos (derivacoes iguais)
                            if (celChave.contains(",")) {
                                for (Character c : celChave.toCharArray()) {
                                    if (!celulaGerada.contains(c.toString())) {//se nao contem adiciona
                                        celulaGerada += c.toString() + ",";
                                    }
                                }
                            } else if (celChave.length() > 0) {
                                if (!celulaGerada.contains(celChave)) {//se nao contem adiciona
                                    celulaGerada += celChave + ",";
                                }
                            } //else {//se nao gerou nada, atualiza com vazio
                            //  celulaGerada = "";
                            //}

                        }
                    }

                    //remove "," do final
                    celulaGerada = celulaGerada.length() > 0 ? celulaGerada.substring(0, celulaGerada.length() - 1) : celulaGerada;

                    if (celulaGerada.length() >= 0 && !celulaGerada.equals(",")) {
                        //testa se esta atualizando a celula ou inserindo a primeira vez
                        if (linhaAtual.size() >= r && !celulaGerada.isEmpty()) {
                            linhaAtual.remove(r - 1);//remove o anterior
                            linhaAtual.add(r - 1, celulaGerada);//adiciona denovo na pos, desloca o restante para a direita
                        } else if (linhaAtual.size() < r) {
                            linhaAtual.add(celulaGerada);
                        }
                    }

                }
            }
            //guarda a linha gerada
            matriz.add(linhaAtual);

            if (isPassoAPasso) {
                retorno += "\n";// linha vazia
                retorno += printMatriz();
                retorno += "\n";// linha vazia
            }
        }

        testeAceita();

        if (isPassoAPasso) {
            retorno += "\n";// linha vazia
            retorno += "=> [FIM] TESTANDO PALAVRA: " + palavra;
            retorno += "\n";// linha vazia
        }

        return retorno;
    }

    /**
     * verifica se a linguagem foi aceita
     */
    private void testeAceita() {
        aceitou = false;
        if (!matriz.isEmpty()) {
            List<String> topoMatriz = matriz.get(matriz.size() - 1);
            if (!topoMatriz.isEmpty()) {
                if (topoMatriz.get(0).contains(inicio)) { //sempre zero
                    aceitou = true;
                }
            }
        }
    }

    /**
     * retorna as chaves de uma letra da palavra
     *
     * @param letra
     * @return lista vazia se nao achou nada
     */
    private String getChave(String letra) {
        String retorno = "";

        for (Map.Entry<String, List<String>> e : gramatica.entrySet()) {
            String key = e.getKey();
            List<String> value = e.getValue();

            for (String s : value) {
                if (s.equals(letra)) {
                    retorno += key + ","; //achou a chave
                }
            }
        }

        //usa substring para retirar a ',' do final
        return retorno.length() > 0 ? retorno.substring(0, retorno.length() - 1) : retorno;
    }

    /**
     * Retorna estado de aceitacao da linguagem
     *
     * @return
     */
    public boolean isAceito() {
        return aceitou;
    }

    /**
     * imprime a matriz no console
     *
     * @return
     */
    public String printMatriz() {

        String retorno = "";

        Collections.reverse(matriz); //usa para mostrar na ordem correta
        for (List<String> linha : matriz) {
            for (String coluna : linha) {
                retorno += "  |   " + coluna;
            }
            retorno += "  |\n";
        }
        Collections.reverse(matriz);

        return retorno;
    }

    /**
     * imprime uma matriz vazia
     *
     * @return
     */
    public String printEmptyMatriz() {

        String retorno = "";

        //matriz vazia
        for (int linha = 0; linha <= palavra.length(); linha++) {
            for (int coluna = 0; coluna <= linha; coluna++) {
                retorno += "  |";
            }
            retorno += "  |\n";
        }

        //base da matriz
        for (int coluna = 0; coluna <= palavra.length(); coluna++) {
            retorno += "  |";
        }
        retorno += "  |\n";

        return retorno;
    }

}
