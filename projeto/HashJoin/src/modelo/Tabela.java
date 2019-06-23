package modelo;

import java.io.IOException;
import java.io.PrintWriter;

import java.util.*;

import java.io.File;

public class Tabela {

	private String nome;
	private ArrayList<String> concatenacao;
	private String[] cabecalho;
	private String[] atributosSelect;

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
		concatenacao = new ArrayList<>();

		this.nome = nome;

		try {
			caminho = "src/data/tabela/"+nome+"/";
			File diretorio = new File(caminho);
			boolean deu = diretorio.mkdirs();

		} catch (Exception ex) {
			System.out.println(ex);
		}
	}

	public String[] getCabecalho() {
		return cabecalho;
	}

	public String getNome() {
		return nome;
	}

	public int getIndice(String coluna){
		return cabecalhoIndice.get(coluna);
	}

	public void setCabecalho(String[] cabecalho) {
		this.cabecalho = cabecalho;
		colunaTotal = cabecalho.length;

		int indice = 0;
		for (String coluna: cabecalho){
			cabecalhoIndice.put(coluna, indice);
			indice++;
		}
	}

	public int getColunaTotal() {
		return colunaTotal;
	}

	public int getLinhaTotal() {
		return linhaTotal;
	}

	public void addConcatenacao(String label){
		concatenacao.add(label);
	}

	public void addArrayConcatenacao(ArrayList<String> adicao){
		for (String label: adicao){
			concatenacao.add(label);
		}
	}

	public ArrayList<String> getConcatenacao() {
		return concatenacao;
	}

	public boolean isConcatenacaoOf(String label){
		return concatenacao.contains(label);
	}

	public void addLinha(String[] linha) {
		dadosEscrita[linhaAtualEscrita] = linha;
		linhaAtualEscrita ++;
		linhaTotal ++;

		if (linhaAtualEscrita == quantidadeMaximaLinhas) {
			descarregaArquivo();
		}
	}

	public void saveDisco(){
		descarregaArquivo();
	}
	public void carregaDisco() {carregaArquivo(new File(caminho+nome+String.valueOf(arquivoAtualLeitura))); }

	public void iniciarLeitura(){
		arquivoAtualLeitura = 0;
		linhaDoGetLinha = 0;
	}

	public String[] getLinha(){
		if (linhaDoGetLinha == linhaAtualLeitura) {
			carregaArquivo(new File(caminho+nome+String.valueOf(arquivoAtualLeitura)));
			linhaDoGetLinha = 0;
		}
		linhaDoGetLinha++;

		return  dadosLeitura[linhaDoGetLinha-1];

	}

	public boolean haProximaPagina(){
		return arquivoAtualLeitura == totalArquivo;
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
				String linhaPartida[] = linhasDoArquivo.split(",");
				if (linhaPartida.length == cabecalho.length){
					dadosLeitura[linhaAtualLeitura] = linhasDoArquivo.split(",");
					linhaAtualLeitura++;
				}
			}

			leitor.close();

		} catch(Exception e) {

			if (leitor != null) leitor.close();
			System.out.println("Erro ao carregar o CSV:. Erro: "+e);
		}
		arquivoAtualLeitura++;
	}

	public void cleanDisco(){
		File folder = new File(caminho);
		if (folder.exists() && folder.isDirectory()) {
			File[] sun = folder.listFiles();
			for (File toDelete : sun) {
				if (toDelete.exists()) toDelete.delete();
			}
			folder.delete();
		}
	}

}
