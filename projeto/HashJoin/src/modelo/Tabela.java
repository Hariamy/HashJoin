package modelo;

import java.util.*;
import java.io.File;

public class Tabela {
    private String nome;
    private ArrayList<String> cabecalho;
    private ArrayList<String[]> dados;
    private int numeroLinhas;
    private int numeroColunas;

    public Tabela(String nome) {
        cabecalho = new ArrayList<>();
        dados = new ArrayList<>();
        numeroLinhas = 0;
        this.nome = nome;
    }

    public void setCabecalho(ArrayList<String> cabecalho) {
        this.cabecalho = cabecalho;
        numeroColunas = cabecalho.size();
    }

    public void addDado(String[] dado) {
        dados.add(dado);
        numeroLinhas ++;
    }

    ArrayList<String> getCabecalho(){
        return this.cabecalho;
    }

    ArrayList<String[]> getDados(){
        return this.dados;
    }

    int getNumeroLinhas() {
        return this.numeroLinhas;
    }

    int getNumeroColunas() {
        return this.numeroColunas;
    }

    public String getNome() {
        return nome;
    }

    String getElemento(int linha, int coluna) {
        return this.dados.get(linha)[coluna];
    }

}
