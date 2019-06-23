package modelo;

import modelo.conexao.Conectar;
import modelo.conexao.Executar;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

public class Database {
	private HashMap<String, ArrayList<String>> tabelas;
	private String nome;

	public Database(String nome, HashMap<String, ArrayList<String>> tabelas) {
		this.nome = nome;
		this.tabelas = tabelas;
	}

	public HashMap<String, ArrayList<String>> getTabelas() {
		return tabelas;
	}

	public ArrayList<Tabela> lerTabelasBanco(HashMap<String, String> tabelasLabel, HashMap<String, ArrayList<String>> colunasSelecionadas, HashMap<String, String> tabelaSelecao, Conectar conexao){
		Executar ex = new Executar();
		ArrayList<Tabela> resultado = new ArrayList<>();

		for (String chave: tabelasLabel.keySet()){
			ArrayList<String> selecionar = colunasSelecionadas.get(tabelasLabel.get(chave));

			String selecao = tabelaSelecao.containsKey(tabelasLabel.get(chave)) ? tabelaSelecao.get(tabelasLabel.get(chave)) : "";

			resultado.add(ex.getTabela(conexao, chave, selecionar, selecao, tabelasLabel.get(chave)));
		}
		return resultado;
	}

}
