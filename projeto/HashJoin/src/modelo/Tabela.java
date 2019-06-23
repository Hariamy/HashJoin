package modelo;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.*;

import java.io.File;

public class Tabela {
    private String nome;
    private String[] cabecalho;
    private HashMap<String, Integer> cabecalhoIndice;
    private String[][] dadosLeitura;
    private String[][] dadosEscrita;
    private int linhaTotal;
    private int colunaTotal;

    private int linhaAtualEscrita;
    private int linhaAtualLeitura;

    private int totalArquivo;
    private int arquivoAtualEscrita;
    private int arquivoAtualLeitura;

    private int linhaDoGetLinha;

    private int quantidadeMaximaLinhas = 10000;

    private String caminho = "";

    public Tabela(String nome) {
        cabecalhoIndice = new HashMap<>();
        dadosLeitura = new String[quantidadeMaximaLinhas][];
        dadosEscrita = new String[quantidadeMaximaLinhas][];
        linhaAtualEscrita = 0; // Índice da linha que deve ser escirta
        linhaAtualLeitura = 0; // Índice da linha que deve ser lida
        linhaTotal = 0; // Quantidade total de linhas da tabela
        linhaDoGetLinha = 0; // Linha a ser lida quando requerida
        this.nome = nome;

        try {
            caminho = "/HashJoin/src/data/tabela/"+nome+"/";
            File diretorio = new File(caminho);
            boolean deu = diretorio.mkdirs();

        } catch (Exception ex) {
            System.out.println(ex);
        }
    }

    public void setCabecalho(String[] cabecalho) {
        this.cabecalho = cabecalho;
        colunaTotal = cabecalho.length;
    }

    public int getLinhaTotal() {
        return linhaTotal;
    }

    public void addLinha(String[] linha, boolean ultima) {
        dadosEscrita[linhaAtualEscrita] = linha;
        linhaAtualEscrita ++;
        linhaTotal ++;
        if (linhaAtualEscrita == quantidadeMaximaLinhas || ultima) {
            descarregaArquivo();
        }
    }

    public String[] getLinha(){
        if (linhaDoGetLinha == linhaAtualLeitura) {
            carregaArquivo(new File(caminho+nome+String.valueOf(arquivoAtualLeitura)));
            linhaDoGetLinha = 0;
        }
        linhaDoGetLinha++;

        return  dadosLeitura[linhaDoGetLinha-1];

    }
    public String[][] getDados() {
        return dadosLeitura;
    }

    private void descarregaArquivo(){
        StringBuilder valores = new StringBuilder();
        int cont = 0;
        for (String[] linha: dadosEscrita) {
            if (cont < linhaAtualEscrita) {
                StringBuilder linhaAtual = new StringBuilder();
                for (String palavra: linha) {
                    linhaAtual.append(palavra+",");
                }
                valores.append(linhaAtual+"\n");
            }
            cont ++;
        }

        try {
            PrintWriter writer = new PrintWriter(caminho+nome+arquivoAtualEscrita, "UTF-8");
            writer.println(valores);
            writer.close();

        } catch (IOException ioe) {
            System.out.println(ioe);

        }

        linhaAtualEscrita = 0;
        arquivoAtualEscrita++;
    }

    private void carregaArquivo(File arquivo) {
        Scanner leitor = null;
        linhaAtualLeitura = 0;
        try {
            leitor = new Scanner(arquivo, "utf-8");

            String linhasDoArquivo;
            while(leitor.hasNext()) {
                linhasDoArquivo = leitor.nextLine();
                dadosLeitura[linhaAtualLeitura] = linhasDoArquivo.split(",");
                linhaAtualLeitura++;
            }

            leitor.close();

        } catch(Exception e) {

            if (leitor != null) leitor.close();
            System.out.println("Erro ao carregar o CSV:. Erro: "+e);
        }
        arquivoAtualLeitura++;
    }

}
