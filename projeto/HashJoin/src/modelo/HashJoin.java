package modelo;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Scanner;

public class HashJoin {


	public Tabela multiplaUniao(ArrayList<Tabela> tabelas, ArrayList<ArrayList<String>> atributosJuncao, HashMap<String, ArrayList<String>> colunasExibir) {

		Tabela A = null;
		Tabela B = null;

		for (ArrayList<String> elemento: atributosJuncao){
			String labelA = elemento.get(0);
			String atributoA = elemento.get(1);

			String labelB = elemento.get(2);
			String atributoB = elemento.get(3);

			for (Tabela tabela: tabelas) {
				if (tabela.isConcatenacaoOf(labelA)) A = tabela;
				else if (tabela.isConcatenacaoOf(labelB)) B = tabela;
			}

			Tabela resultado = uniao(A, A.getIndice(atributoA), true, B, B.getIndice(atributoB), false);

			A.cleanDisco();
			B.cleanDisco();

			tabelas.remove(A);
			tabelas.remove(B);

			resultado.addArrayConcatenacao(A.getConcatenacao());
			resultado.addArrayConcatenacao(B.getConcatenacao());

			ArrayList<String> cabecalho = new ArrayList<>();

			for (String valor: A.getCabecalho()) cabecalho.add(valor);
			for (String valor: B.getCabecalho()) cabecalho.add(valor);

			resultado.setCabecalho(cabecalho.toArray(new String[0]));

			tabelas.add(resultado);

		}

		return tabelas.get(0);
	}

	// select a.nome, c.nome from aluno as a, curso as c where c.cod_curso = a.cod_curso
	public Tabela uniao(Tabela A, int atributoA, boolean continuaA, Tabela B, int atributoB, boolean continuaB){
		String caminhoA = "src/data/buckets/"+A.getNome()+"/";
		String caminhoB = "src/data/buckets/"+B.getNome()+"/";

		HashSet<Integer> valoresHashA = criarBucket(A, atributoA, caminhoA);
		HashSet<Integer> valoresHashB = criarBucket(B, atributoB, caminhoB);

		Tabela resultado = new Tabela(A.getNome()+"U"+B.getNome());

		for (Integer hash: valoresHashA){
			if (valoresHashB.contains(hash)){
				File caminhoBucketA = new File(caminhoA+"bucket"+hash);
				File caminhoBucketB = new File(caminhoB+"bucket"+hash);

				Scanner leitorA = null;
				Scanner leitorB = null;

				if (caminhoBucketB.exists()){
					try {
						leitorA = new Scanner(caminhoBucketA, "utf-8");

						String linhasDoArquivoA;
						String linhasDoArquivoB;

						ArrayList<String[]> bucketBMemoria = new ArrayList<>();

						leitorB = new Scanner(caminhoBucketB, "utf-8");

						while (leitorB.hasNext()) {
							linhasDoArquivoB = leitorB.nextLine();
							String[] linhaB = linhasDoArquivoB.split(",");
							bucketBMemoria.add(linhaB);
						}

						leitorB.close();

						while(leitorA.hasNext()) {
							linhasDoArquivoA = leitorA.nextLine();
							String[] linhaA = linhasDoArquivoA.split(",");


							for (String[] tupla: bucketBMemoria) {
								if (linhaA[atributoA].equals(tupla[atributoB])) {
									int tamanho = linhaA.length + tupla.length;

									String[] linhaResult = new String[tamanho];

									System.arraycopy(linhaA, 0, linhaResult, 0, linhaA.length);
									System.arraycopy(tupla, 0, linhaResult, linhaA.length, tupla.length);

									resultado.addLinha(linhaResult);
								}
							}

						}

						leitorA.close();

					} catch(Exception e) {

						if (leitorA != null) leitorA.close();
						if (leitorB != null) leitorB.close();

						System.out.println("Erro ao carregar o CSV:. Erro: "+e);
					}
				}
			}
		}

		resultado.saveDisco();

		cleanDisco(caminhoA);
		cleanDisco(caminhoB);

		return resultado;
	}

	// select a.nome. c.nome from aluno as a, curso as c where a.cod_curso = c.cod_curso
	// select d.nome, u.nome, c.nome from departamento as d, unidade as u, curso as c, dep_curso as dc where d.cod_uni = u.cod_uni and dc.cod_curso = c.cod_curso and dc.cod_dep = d.cod_dep

	private HashSet<Integer> criarBucket(Tabela tab, int atributo, String caminho){
		HashMap<Integer, String> buckets = new HashMap<>();
		HashSet<Integer> valoresHashs = new HashSet<>();
		try {
			File diretorio = new File(caminho);
			boolean deu = diretorio.mkdirs();

		} catch (Exception ex) {
			System.out.println(ex);
		}
		int hash;

		tab.iniciarLeitura();

		for (int i = 0; i<tab.getLinhaTotal(); i++){
			String[] linha = tab.getLinha();


			hash = funcaoHash(linha[atributo]);
			valoresHashs.add(hash);
			StringBuilder valores = new StringBuilder();

			for (String elemento: linha){
				valores.append(elemento+",");
			}

			valores.append("\n");

			String valor = buckets.containsKey(hash) ? buckets.get(hash)+valores : valores.toString();
			buckets.put(hash, valor);

			if (i/10000 == 10000) {
				descarregaBucket(buckets, caminho);
				buckets = new HashMap<>();
			}

		}
		descarregaBucket(buckets, caminho);

		return valoresHashs;
	}

	private void descarregaBucket(HashMap<Integer, String> buckets, String caminho){
		for (Integer chave: buckets.keySet()) {
			try {
				PrintWriter writer = new PrintWriter(caminho+"bucket"+chave, "UTF-8");
				writer.println(buckets.get(chave));
				writer.close();

			} catch (IOException ioe) {
				System.out.println(ioe);

			}
		}
	}

	private int funcaoHash(String palavra){
		int hash = 7;
		for (int i = 0; i < palavra.length(); i++) {
			hash = (hash*31 + palavra.charAt(i))/20;
		}
		return hash;
	}

	public void cleanDisco(String caminho){
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
