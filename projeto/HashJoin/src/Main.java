/*
// --------- \\ ATENÇÃO // --------- \\

      A VERSÃO DO JDK DEVE SER A 8

*/
import modelo.HashJoin;
import modelo.Tabela;
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

*/
        /*
		modelo.conexao.Conectar conexao = new modelo.conexao.Conectar();
		conexao.setLogin("sa", "admin123H");
		conexao.setServer("Ubuntinho-s2", "1433");
		conexao.setDatabase("Acad");

		String consulta =
				"create table unidade (\n" +
				"\tcod_uni int not null,\n" +
				"\tnome varchar(30) not null,\n" +
				"\tendereco varchar(50) not null,\n" +
				"\tconstraint PKuni primary key(cod_uni)\n" +
				")\n" +
				"create table telefones_uni(\n" +
				"\tnum_fone int not null,\n" +
				"\ttipo_fone varchar(30) not null,\n" +
				"\tcod_uni int not null,\n" +
				"\tconstraint PKtelUA primary key (num_fone),\n" +
				"\tconstraint FKtelUA foreign key(cod_uni) references unidade\n" +
				")\n" +
				"\n" +
				"create table departamento(\n" +
				"\tcod_dep int not null,\n" +
				"\tnome varchar(30) not null,\n" +
				"\tendereco varchar(50) not null,\n" +
				"\tcod_uni int not null,\n" +
				"\tconstraint PKdep primary key(cod_dep),\n" +
				"\tconstraint FKdep foreign key (cod_uni) references unidade\n" +
				"\ton delete no action\n" +
				"\ton update cascade \n" +
				")\n" +
				"\n" +
				"create table telefone_dep(\n" +
				"\tnum_fone int not null,\n" +
				"\ttipo_fone varchar (20) not null,\n" +
				"\tcod_dep int not null,\n" +
				"\tconstraint PKteldep primary key (num_Fone),\n" +
				"\tconstraint FKteldep foreign key(cod_dep) references departamento \n" +
				"\ton delete no action\n" +
				"\ton update cascade \n" +
				")\n" +
				"\n" +
				"create table curso(\n" +
				"\tcod_curso int not null,\n" +
				"\tnome varchar (30) not null,\n" +
				"\tendereco varchar (50) not null,\n" +
				"\tconstraint PKcur primary key (cod_curso) \n" +
				")\n" +
				"\n" +
				"create table dep_curso(\n" +
				"\tcod_curso int not null,\n" +
				"\tcod_dep int not null,\n" +
				"\tconstraint FKdepc foreign key (cod_dep) references departamento,\n" +
				"\tconstraint FKcdep foreign key (cod_curso) references curso\n" +
				"\ton delete no action\n" +
				"\ton update cascade \n" +
				")\n" +
				"\n" +
				"create table disciplina(\n" +
				"\tcod_disc int not null,\n" +
				"\tnome varchar(60) not null,\n" +
				"\tcreditos int not null\n" +
				"\tconstraint PKdis primary key(cod_disc)\n" +
				"\n" +
				")\n" +
				"\n" +
				"create table pre_requisito(\n" +
				"\tcod_disc int not null,\n" +
				"\tcod_pre int not null,\n" +
				"\tconstraint PKpre primary key (cod_pre, cod_disc),\n" +
				"\tconstraint FKpre foreign key(cod_disc) references disciplina\n" +
				"\ton delete no action\n" +
				"\ton update cascade \n" +
				")\n" +
				"\n" +
				"\n" +
				"create table professor(\n" +
				"\tmatr int not null,\n" +
				"\tnome varchar (20) not null,\n" +
				"\tCPF int not null,\n" +
				"\tRG int not null,\n" +
				"\tsalario dec(9,2) not null,\n" +
				"\tendereco varchar(50) not null, \n" +
				"\tlota int not null,\n" +
				"\tdiretor int,\n" +
				"\tchefe int,\n" +
				"\tcoordenador int,\n" +
				"\tconstraint PKpro primary key(matr),\n" +
				"\tconstraint FKprolo foreign key(lota) references departamento\n" +
				"\ton delete no action\n" +
				"\ton update cascade,\n" +
				"\tconstraint FKproch foreign key(chefe) references departamento\n" +
				"\ton delete no action\n" +
				"\ton update no action,\n" +
				"\tconstraint FKprodi foreign key(diretor) references unidade\n" +
				"\ton delete no action\n" +
				"\ton update no action,\n" +
				"\tconstraint FKproco foreign key(coordenador) references curso\n" +
				"\ton delete no action\n" +
				"\ton update cascade,\n" +
				"\tconstraint RGpro unique(RG),\n" +
				"\tconstraint CPFpro unique(CPF),\n" +
				")\n" +
				"\n" +
				"create table prof_disc(\n" +
				"\tmat_prof int not null,\n" +
				"\tcod_disc int not null,\n" +
				"\tsemestre int not null,\n" +
				"\tconstraint PKpdisc primary key (semestre, mat_prof, cod_disc),\n" +
				"\tconstraint FKpromatp foreign key (mat_prof) references professor\n" +
				"\ton delete no action\n" +
				"\ton update cascade, \n" +
				"\tconstraint FKdiscodp foreign key (cod_disc) references disciplina\n" +
				"\ton delete no action\n" +
				"\ton update cascade \n" +
				")\n" +
				"\n" +
				"create table aluno(\n" +
				"\tmatr int not null,\n" +
				"\tnome varchar (20) not null,\n" +
				"\tendereco varchar (30) not null,\n" +
				"\tCPF int not null,\n" +
				"\tRG int not null,\n" +
				"\tcod_curso int not null,\n" +
				"\tconstraint PKalu primary key(matr),\n" +
				"\tconstraint FKalu foreign key (cod_curso) references curso\n" +
				"\ton delete no action\n" +
				"\ton update cascade,\n" +
				"\tconstraint CPFalu unique(CPF),\n" +
				"\tconstraint RGalu unique(RG),\n" +
				")\n" +
				"\n" +
				"create table curso_disc(\n" +
				"\tcod_curso int not null,\n" +
				"\tcod_disc int not null,\n" +
				"\tconstraint PKcurdisc primary key (cod_curso, cod_disc),\n" +
				"\tconstraint FKcurcodc foreign key (cod_curso) references curso\n" +
				"\ton delete no action\n" +
				"\ton update cascade,\n" +
				"\tconstraint FKdiscodc foreign key (cod_disc) references disciplina\n" +
				"\ton delete no action\n" +
				"\ton update cascade \n" +
				")\n" +
				"\n" +
				"create table aluno_disc(\n" +
				"\tcod_disc int not null,\n" +
				"\tmatr int not null,\n" +
				"\tsemestre int not null,\n" +
				"\tap1 decimal(5, 2),\n" +
				"\tap2 decimal(5, 2),\n" +
				"\taf decimal(5, 2),\n" +
				"\tconstraint PKaludis primary key (semestre, matr, cod_disc),\n" +
				"\tconstraint FKdiscoda foreign key (cod_disc) references disciplina\n" +
				"\ton delete no action\n" +
				"\ton update cascade,\n" +
				"\tconstraint FKalmatra foreign key (matr) references aluno\n" +
				"\ton delete no action\n" +
				"\ton update cascade \n" +
				")\n" +
				"\n" +
				"\n" +
				"insert into unidade values (1, 'Centro de Ciências', 'Bloco 902 Pici')\n" +
				"insert into unidade values (2, 'Centro de Tecnologia', 'Bloco 707 Pici')\n" +
				"insert into unidade values (3, 'Instituto de Cultura e Arte', 'Bloco ICA Pici')\n" +
				"\n" +
				"insert into telefones_uni values (40028922, 'comercial', 1)\n" +
				"insert into telefones_uni values (23863049, 'pessoal', 1)\n" +
				"insert into telefones_uni values (48435252, 'fax', 1)\n" +
				"insert into telefones_uni values (23523522, 'comercial', 3)\n" +
				"\n" +
				"insert into departamento values (1, 'Computação', 'Bloco 910 Pici', 1)\n" +
				"insert into departamento values (2, 'Quimica Inorganica', 'Bloco 938 Pici', 1)\n" +
				"insert into departamento values (3, 'Filosofia', 'Bloco ICA Pici', 3)\n" +
				"insert into departamento values (4, 'Musica - Licenciatura', 'Bloco ICA Pici', 3)\n" +
				"\n" +
				"insert into telefone_dep values(494529, 'comercial', 2)\n" +
				"insert into telefone_dep values(352929, 'comercial', 2)\n" +
				"insert into telefone_dep values(434252, 'comercial', 1)\n" +
				"insert into telefone_dep values(847299, 'comercial', 3)\n" +
				"\n" +
				"insert into curso values(1, 'Ciência da Computação', 'Bloco 910 Pici')\n" +
				"insert into curso values(2, 'Química Licenciatura', 'Bloco 950 Pici')\n" +
				"insert into curso values(3, 'Música', 'ICA Pici')\n" +
				"insert into curso values(4, 'Engenharia Elétrica', 'Bloco 710 Pici')\n" +
				"\n" +
				"\n" +
				"insert into dep_curso values(1,1)\n" +
				"insert into dep_curso values(2,1)\n" +
				"insert into dep_curso values(3,3)\n" +
				"insert into dep_curso values(4,2)\n" +
				"\n" +
				"\n" +
				"insert into disciplina values(1,'Banco de Dados',4)\n" +
				"insert into disciplina values(2,'Análise de Alg',4)\n" +
				"insert into disciplina values(5,'Grafos',4)\n" +
				"insert into disciplina values(3,'Quimica Orga I',6)\n" +
				"insert into disciplina values(4,'Fund da Dança',6)\n" +
				"\n" +
				"insert into pre_requisito values(5,2)\n" +
				"\n" +
				"insert into professor values(1,'Judith', 1, 1, 45000, 'Rua Ricaa', 1, NULL, NULL, 0)\n" +
				"insert into professor values(2,'Pablo', 2, 2, 1000, 'Rua Medio', 1, NULL, NULL, 1)\n" +
				"insert into professor values(3,'Jackson', 3, 3, 45000, 'Rua Ricoo', 1, NULL, NULL, 2)\n" +
				"insert into professor values(4,'Dancarino', 4, 4, 45000, 'Rua Ricoo', 1, NULL, NULL, 3)\n" +
				"insert into professor values(5,'Engenheiro', 5, 5, 45000, 'Rua Ricaa', 1, NULL, NULL, 4)\n" +
				"insert into professor values(6,'Welliandre', 6, 6, 45000, 'Rua Ricaa', 1, NULL, 1, 0)\n" +
				"\n" +
				"insert into prof_disc values(2,5,3)\n" +
				"insert into prof_disc values(1,3,3)\n" +
				"insert into prof_disc values(2,2,4)\n" +
				"insert into prof_disc values(4,4,5)\n" +
				"\n" +
				"insert into aluno values(1, 'Aline', 'Av principal', 1, 1, 1)\n" +
				"insert into aluno values(2, 'Mauricio', 'Av secundaria', 2, 2, 2)\n" +
				"insert into aluno values(3, 'Julio', 'Av qualquer', 3, 3, 3)\n" +
				"\n" +
				"insert into curso_disc values(1,1)\n" +
				"insert into curso_disc values(1,2)\n" +
				"insert into curso_disc values(2,3)\n" +
				"insert into curso_disc values(3,4)\n" +
				"\n" +
				"insert into aluno_disc values(1,1,8,10,10,NULL)\n" +
				"insert into aluno_disc values(1,2,8,9,4,NULL)";
		modelo.conexao.Executar ex = new modelo.conexao.Executar();
		boolean resp = ex.update(consulta, conexao);

		System.out.println(resp);

         */
/*
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


        System.setProperty("file.encoding", "UTF-8");

        Janela janela = new Janela();
        janela.conteudoJanela(new PainelInicial(janela));
        janela.exibirJanela();

        Fontes.setFonte();


/*
		String consulta =  "select p.nome, q.idade, e.cpf, p.mae, p.pai, q.tio from parto as p, quarto as q, edade as e where p.nome = q.nome and p.idade = e.idade";

		String textoConsulta = consulta.replaceAll(",", " ").replaceAll("\n", " ").replaceAll("\t", " ").replaceAll(" +", " ").toLowerCase().replaceAll(" as ", ".").replaceAll(" = ", ".").replaceAll(" and ", " ");

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

							// adiciona coluna a seleção
							ArrayList<String> valor2 = colunasSelecionadas.containsKey(atributo[0]) ? colunasSelecionadas.get(atributo[0]) : new ArrayList<>();
							if (! valor2.contains(atributo[1])) valor2.add(atributo[1]);
							colunasSelecionadas.put(atributo[0], valor2);

							ArrayList<String> valor3 = colunasSelecionadas.containsKey(atributo[2]) ? colunasSelecionadas.get(atributo[2]) : new ArrayList<>();
							if (! valor3.contains(atributo[3])) valor3.add(atributo[3]);
							colunasSelecionadas.put(atributo[2], valor3);
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
*/

/*

		Tabela tab = new Tabela("teste");

		for (int i = 0; i < 70; i++ ){
			String[] linha = {"eu B", "estava B", "testando B", String.valueOf(i)};
			tab.addLinha(linha, false);
		}
		String[] linha = {"eu B", "estava B", "testando B","123456789"};

		tab.addLinha(linha, true);

		for (int i = 0; i<tab.getLinhaTotal(); i++){
			for (String elemento: tab.getLinha()){
				//System.out.print(elemento+"  ");
			}
			//System.out.println();
		}


		Tabela tab2 = new Tabela("teste1");

		for (int i = 0; i < 20; i++ ){
			if (i % 3 == 0) {
				String[] linha2 = {"eu A", "estava A", "testando A", String.valueOf(i)};
				tab2.addLinha(linha2, false);
			}
		}
		String[] linha3 = {"eu A", "estava A", "testando A","1234567879"};

		tab2.addLinha(linha3, true);


		HashJoin uniao = new HashJoin();
		Tabela result = uniao.uniao(tab, 3, tab2, 3);

		tab2.cleanDisco();
		tab.cleanDisco();

 */
	}

}
