/*
// --------- \\ ATENÇÃO // --------- \\

      A VERSÃO DO JDK DEVE SER A 8

*/
import visao.janela.Janela;
import visao.layout.Fontes;
import visao.janela.PainelInicial;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

	public static void main(String[] args) {
        /*
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter server name: ");
        String servername = scanner.next();

        System.out.print("Enter server port: ");
        String serverport = scanner.next();

        System.out.print("Enter your username: ");
        String username = scanner.next();

        System.out.print("Enter your password: ");
        String password = scanner.next();



        modelo.conexao.Conectar conexao = new modelo.conexao.Conectar();
        // conexao.setLogin(username, password);
        // conexao.setServer(servername, serverport);

        String consulta = "select * from master.sys.databases";
        modelo.conexao.Executar ex = new modelo.conexao.Executar();
        boolean resp = ex.select(consulta, conexao);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter database name: ");
        String database = scanner.next();

        conexao.setDatabase(database);
        consulta = "select * from INFORMATION_SCHEMA.TABLES";
        resp = ex.select(consulta, conexao);


		modelo.conexao.Conectar conexao = new modelo.conexao.Conectar();
		conexao.setLogin("sa", "admin123H");
		conexao.setServer("Ubuntinho-s2", "1433");
		//conexao.setDatabase("teste");


		String consulta = "select * from master.sys.databases";
		modelo.conexao.Executar ex = new modelo.conexao.Executar();
		boolean resp = ex.select(consulta, conexao);

		System.out.println(resp);

		HashMap<String, ArrayList<String>> databaseValues = new HashMap<>();



        ArrayList<String> resp = ex.selectTableNames(conexao, "lojas");

        for (int i = 0; i < resp.size(); i ++){
            databaseValues.put(resp.get(i), ex.selectTableAtributos(conexao, resp.get(i)));
        }

        for (int i = 0; i < resp.size(); i ++){
            System.out.print(resp.get(i) + " possui os atributos: ");
            for (int j = 0; j < databaseValues.get(resp.get(i)).size(); j++) {
                System.out.print(databaseValues.get(resp.get(i)).get(j) + " :: ");
            }
            System.out.println("\n");
        }
*/
/*
        System.setProperty("file.encoding", "UTF-8");

        Janela janela = new Janela();
        janela.conteudoJanela(new PainelInicial(janela));
        janela.exibirJanela();

        Fontes.setFonte();
*/

		String consulta =  "select p.idadem, p.nome, q.idade from parto as p, quarto as q where p.sexo = q.sexo";

		String textoConsulta = consulta.replaceAll(",", " ").replaceAll("\n", " ").replaceAll("\t", " ").replaceAll(" +", " ").toLowerCase().replaceAll(" as ", ".").replaceAll(" = ", ".");

		String arrayConsulta[] = textoConsulta.split(" ");
		System.out.println(textoConsulta);

		HashMap<String, ArrayList<String>> colunasSelecionadas = new HashMap<>();
		HashMap<String,String> tabelasLabel = new HashMap<>();
		ArrayList<ArrayList<String>> atributosJuncao = new ArrayList<>();

		boolean select = false;
		boolean from = false;
		boolean where = false;

		for (String elemento: arrayConsulta) {
			if (!select && elemento.equals("select") && !from && !where) select = true;
			else {
				if (select && elemento.equals("from") && !from && !where) from = true;
				else {
					if (select && elemento.equals("where") && from && !where) where = true;
					else {
						System.out.println("O que entra no else: "+elemento);
						if (select && !from && !where ) {
							System.out.println("foi aqui?");
							String atributo[] = elemento.split("\\.");

							ArrayList<String> valor = colunasSelecionadas.containsKey(atributo[0]) ? colunasSelecionadas.get(atributo[0]) : new ArrayList<>();
							valor.add(atributo[1]);
							colunasSelecionadas.put(atributo[0], valor);
						}

						if (select && from && !where ) {
							System.out.println("ou foi aqui? ");
							String atributo[] = elemento.split("\\.");

							tabelasLabel.put(atributo[0], atributo[1]);
						}

						if (select && from && where ) {
							System.out.println("ou será que foi aqui?");

							String atributo[] = elemento.split("\\.");

							ArrayList<String> valor = new ArrayList<>();
							valor.add(atributo[0]);
							valor.add(atributo[1]);
							valor.add(atributo[2]);
							valor.add(atributo[3]);

							atributosJuncao.add(valor);
						}

					}

				}

			}
		}
		for (ArrayList<String> elemento: atributosJuncao){
			System.out.println("Atributos de junção: "+elemento.get(0)+"."+elemento.get(1)+" = " +elemento.get(2)+"."+elemento.get(3));
		}
		System.out.println("\n");
		for (String chaves: tabelasLabel.keySet()) {
			System.out.println("chave: "+chaves + " valor "+tabelasLabel.get(chaves));
			for (String valores: colunasSelecionadas.get(tabelasLabel.get(chaves))) System.out.println("valores "+valores);

		}


	}

}
