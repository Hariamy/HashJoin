package visao.janela;

import modelo.Database;
import modelo.HashJoin;
import modelo.Tabela;
import modelo.conexao.Conectar;
import modelo.conexao.Executar;
import visao.layout.Botao;
import visao.layout.Cores;
import visao.layout.Fontes;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.TableColumn;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class PainelHashJoin extends JPanel{
	private ArrayList<String> bancos = new ArrayList<>();
	private Border bordaVazia = BorderFactory.createEmptyBorder(0, 0, 0, 0);

	private Janela janela;
	private Conectar conexao;

	private Dimension dimensaoCental;
	private Dimension dimensaoLateral;
	private Box boxCentral;
	private Box boxLateral;

	private JPanel painelSuperior = new JPanel();
	private JPanel painelCentro = new JPanel();
	private JPanel painelLateral = new JPanel();

	private Executar ex = new Executar();
	private Database banco;
	private JTextArea consulta;

	private String expansaoAtual = "";


	public PainelHashJoin(Janela janela, Conectar conexao, Database banco){
		super();
		this.janela = janela;
		this.conexao = conexao;
		this.banco = banco;
		this.configuracoes();
		this.setVisible(true);
	}

	private void configuracoes(){

		//-----------------\\ LAYOUT DA PÁGINA INICIAL //-----------------\\

		painelSuperior.setBorder(bordaVazia);
		painelCentro.setBorder(bordaVazia);
		painelLateral.setBorder(bordaVazia);


		this.setLayout(new BorderLayout());
		painelSuperior.setLayout(new BorderLayout());
		painelCentro.setLayout(new FlowLayout(FlowLayout.CENTER));
		painelLateral.setLayout(new FlowLayout(FlowLayout.CENTER));

		this.setBackground(Cores.rosaClaro);
		painelSuperior.setBackground(Cores.rosaClaro);
		painelCentro.setBackground(Cores.azulEscuro);
		painelLateral.setBackground(Cores.corBranca);



		//-----------------\\ INÍCIO - CARREGA IMAGEM BOTAO VOLTAR //-----------------\\
		try {
			URL voltarIcone = ClassLoader.getSystemResource("imagens/volta.png");
			Icon botaoVoltar = new ImageIcon(voltarIcone);

			Botao voltar = new Botao(botaoVoltar);
			voltar.setContentAreaFilled(false);
			voltar.addActionListener(new BotaoVoltar());

			painelSuperior.add(voltar, BorderLayout.WEST);
			this.add(painelSuperior, BorderLayout.NORTH);

		} catch (Exception e){
			Icon botaoVoltar = new ImageIcon(getClass().getResource("/imagens\\volta.png"));

			Botao voltar = new Botao(botaoVoltar);
			voltar.setContentAreaFilled(false);
			voltar.addActionListener(new BotaoVoltar());

			painelSuperior.add(voltar, BorderLayout.WEST);
			this.add(painelSuperior, BorderLayout.NORTH);
		}
		//-----------------\\ FIM - CARREGA IMAGEM BOTAO VOLTAR //-----------------\\


		//-----------------\\ INÍCIO - CONFIGURA AS COISAS DO PAINEL CENTRAL //-----------------\\

		dimensaoCental = new Dimension(400, 0);

		painelCentro.setPreferredSize(dimensaoCental);

		boxCentral = Box.createHorizontalBox();

		JScrollPane rolagem = new JScrollPane(painelCentro);
		rolagem.setPreferredSize(new Dimension( 400,0));
		rolagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		boxCentral.add(rolagem);
		boxCentral.setBorder(bordaVazia);
		this.add(boxCentral, BorderLayout.CENTER);

		//-----------------\\ FIM - CONFIGURA AS COISAS DO PAINEL CENTRAL //-----------------\\


		//-----------------\\ INÍCIO - CONFIGURA AS COISAS DO PAINEL LATERAL //-----------------\\

		dimensaoLateral = new Dimension(350, 0);
		painelLateral.setPreferredSize(dimensaoLateral);

		boxLateral = Box.createHorizontalBox();

		JScrollPane rolagemLateral = new JScrollPane(painelLateral);
		rolagemLateral.setPreferredSize(new Dimension( 350,0));
		rolagemLateral.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		boxLateral.add(rolagemLateral);
		boxLateral.setBorder(bordaVazia);
		this.add(boxLateral, BorderLayout.WEST);

		HashMap<String, ArrayList<String>> infoBanco = banco.getTabelas();
		Set<String> chaves = infoBanco.keySet();

		JPanel painelTitulo = new JPanel();
		painelTitulo.setBorder(bordaVazia);
		painelTitulo.setPreferredSize(new Dimension(350, 60));
		painelTitulo.setBackground(Cores.corBranca);
		painelTitulo.setLayout(new FlowLayout());

		JLabel titulo = new JLabel("TABELAS E ATRIBUTOS");
		titulo.setFont(Fontes.ROBOTO_BOLD_MENOR);
		titulo.setForeground(Cores.corBotaoAzulEscuro);

		painelTitulo.add(titulo, "Center");

		adicionaPainelLateral(painelTitulo, 80);

		for(String chave: chaves) {
			JPanel painelBotao = new JPanel();
			painelBotao.setBorder(bordaVazia);
			painelBotao.setPreferredSize(new Dimension(350, 60));

			Botao botaoBanco = new Botao(chave);
			botaoBanco.setMargin(new Insets(5, 10, 5, 10));
			botaoBanco.configurarFonteCorFundo(Fontes.ROBOTO_MENOR, Cores.corBranca, Cores.rosaForte);
			botaoBanco.setPreferredSize(new Dimension(350, 60));
			botaoBanco.setHorizontalAlignment(SwingConstants.LEFT);
			botaoBanco.addActionListener(new BotaoPainelLateral(chave));


			painelBotao.add(botaoBanco);

			adicionaPainelLateral(painelBotao, 70);

		}


		//-----------------\\ FIM - CONFIGURA AS COISAS DO PAINEL LATERAL //-----------------\\


		//-----------------\\ INÍCIO - CAOXA DE TEXTO PARA A CONNS //-----------------\\

		JPanel painelConsulta = new JPanel();
		painelConsulta.setBorder(bordaVazia);
		painelConsulta.setPreferredSize(new Dimension(700, 400));
		painelConsulta.setBackground(Cores.corVerde);
		painelConsulta.setLayout(new BorderLayout());

		JLabel tituloConsulta = new JLabel("ESCREVA A JUNÇÃO SQL", SwingConstants.CENTER);
		tituloConsulta.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
		tituloConsulta.setFont(Fontes.ROBOTO_BOLD_PEQUENA);
		tituloConsulta.setForeground(Cores.corBotaoAzulEscuro);

		consulta = new JTextArea(2, 1);
		consulta.setLineWrap(true);
		consulta.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
		consulta.setWrapStyleWord(true);
		consulta.setFont(Fontes.ROBOTO_PEQUENA);
		consulta.setForeground(Cores.corBotaoAzulEscuro);

		JScrollPane scroll = new JScrollPane(consulta);

		painelConsulta.add(tituloConsulta, BorderLayout.NORTH);
		painelConsulta.add(scroll, BorderLayout.CENTER);


		//-----------------\\ FIM - CAOXA DE TEXTO PARA A CONNS //-----------------\\



		//-----------------\\ INÍCIO - ADICIONA OS BOTÃO REFERENTE A JUNÇÃO //-----------------\\

		JPanel painelBotao = new JPanel();
		painelBotao.setBorder(bordaVazia);
		painelBotao.setPreferredSize(new Dimension(400, 80));
		painelBotao.setBackground(Cores.azulEscuro);

		Botao botaoBanco = new Botao("CONSULTAR");
		botaoBanco.setMargin(new Insets(10, 80, 10, 80));
		botaoBanco.configurarFonteCorFundo(Fontes.ROBOTO_BOLD_MENOR, Cores.corBranca, Cores.azulEscuro2);
		botaoBanco.addActionListener(new BotaoConsulta());

		painelBotao.add(botaoBanco);

		painelConsulta.add(painelBotao, BorderLayout.SOUTH);

		//-----------------\\ FIM - ADICIONA OS BOTÃO REFERENTE A JUNÇÃO //-----------------\\

		painelCentro.add(painelConsulta, "North");
	}

	public void adicionaPainelCentral(JPanel painel, int quanto){
		dimensaoCental.setSize(400, dimensaoCental.getHeight()+quanto);
		painelCentro.add(painel);
		painelCentro.revalidate();
		boxCentral.revalidate();
	}

	public void adicionaPainelLateral(JPanel painel, int quanto){
		dimensaoLateral.setSize(350, dimensaoLateral.getHeight()+quanto);
		painelLateral.add(painel);
		painelLateral.revalidate();
		boxLateral.revalidate();
	}



	// É ISSO MESMO PRODUÇÃO - CLASSES DENTRO DE CLASSES
	public class BotaoVoltar implements ActionListener {
		public void actionPerformed(ActionEvent evento) {
			janela.getContentPane().removeAll();
			janela.conteudoJanela(new PainelBancos(janela, conexao));
			janela.revalidate();
			janela.repaint();
		}
	}

	public class BotaoConsulta implements ActionListener {
		public void actionPerformed(ActionEvent evento) {
			new CarregaInfo().start();
			new Uniao().start();
		}
	}

	class CarregaInfo extends Thread {
		public void run() {
			//-----------------\\ INÍCIO - ADQUIRINDO OS DADOS DA TABELA  //-----------------\\

			painelCentro.removeAll();

			JLabel informacao = new JLabel("Executando a união ...");
			informacao.setFont(Fontes.ROBOTO_BOLD_MENOR);
			informacao.setForeground(Cores.corBotaoAzulEscuro);

			painelCentro.add(informacao);
			painelCentro.revalidate();
			painelCentro.repaint();

			this.interrupt();

			//-----------------\\ FIM - ADQUIRINDO OS DADOS DA TABELA  //-----------------\\
		}
	}

	class Uniao extends Thread {
		public void run() {
			//-----------------\\ INÍCIO - PAINEL DE CARREGAMENTO  //-----------------\\

			String textoConsulta = consulta.getText().replaceAll(",", " ")
					.replaceAll("\n", " ").replaceAll("\t", " ")
					.replaceAll(" +", " ").toLowerCase()
					.replaceAll(" as ", ".")
					.replaceAll(" == ", ".")
					.replaceAll(" and ", " ")
					.replaceAll(" < ", "<")
					.replaceAll(" > ", ">")
					.replaceAll(" = ", "=")
					.replaceAll(" <= ", "<=")
					.replaceAll(" >= ", ">=");

			String arrayConsulta[] = textoConsulta.split(" ");

			// select a.nome, a.cpf, c.nome from aluno as a, curso as c where a.cod_curso == c.cod_curso and a.rg<=2
			HashMap<String, ArrayList<String>> colunasSelecionadas = new HashMap<>();
			HashMap<String, ArrayList<String>> colunasExibir = new HashMap<>();
			HashMap<String, String> tabelasLabel = new HashMap<>();
			HashMap<String, String> tabelaSelecao = new HashMap<>();
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

							if (select && !from && !where ) {

								String atributo[] = elemento.split("\\.");

								ArrayList<String> valor = colunasSelecionadas.containsKey(atributo[0]) ? colunasSelecionadas.get(atributo[0]) : new ArrayList<>();
								valor.add(atributo[1]);
								colunasSelecionadas.put(atributo[0], valor);
								colunasExibir.put(atributo[0], valor);
							}

							if (select && from && !where ) {

								String atributo[] = elemento.split("\\.");

								tabelasLabel.put(atributo[0], atributo[1]);
							}

							if (select && from && where ) {


								String atributo[] = elemento.split("\\.");

								if (atributo.length == 4){
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

								} else {
									String aux = tabelaSelecao.containsKey(atributo[0]) ? tabelaSelecao.get(atributo[0]) + " and "+atributo[1] : " where "+atributo[1];
									tabelaSelecao.put(atributo[0], aux);
								}

							}

						}

					}

				}
			}


			ArrayList<Tabela> tabelas = banco.lerTabelasBanco(tabelasLabel, colunasSelecionadas, tabelaSelecao, conexao);
			HashJoin hashjoin = new HashJoin();

			Tabela juncao = hashjoin.multiplaUniao(tabelas, atributosJuncao, colunasExibir);

			juncao.iniciarLeitura();

			String dados[][] = new String[juncao.getLinhaTotal()][];
			for (int i = 0; i < juncao.getLinhaTotal() && i < 10000; i++){
				dados[i] = juncao.getLinha();
			}

			JTable valor = new JTable(dados, juncao.getCabecalho());

			setTamanhoColuna(valor, juncao.getCabecalho());
			valor.setFillsViewportHeight(true);
			valor.getTableHeader().setReorderingAllowed(false);
			valor.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
			JScrollPane scrollPane = new JScrollPane (valor);
			scrollPane.setPreferredSize(new Dimension( 700,550));


			Botao botaoBanco = new Botao("NOVA CONSULTAR");
			botaoBanco.setMargin(new Insets(10, 80, 10, 80));
			botaoBanco.configurarFonteCorFundo(Fontes.ROBOTO_BOLD_MENOR, Cores.corBranca, Cores.azulEscuro2);
			botaoBanco.addActionListener(new BotaoNovaConsulta());

			painelCentro.removeAll();

			painelCentro.add(scrollPane, BorderLayout.NORTH);
			painelCentro.add(botaoBanco);

			painelCentro.revalidate();
			painelCentro.repaint();
			//-----------------\\ FIM - PAINEL DE CARREGAMENTO  //-----------------\\
		}
	}

	public class BotaoNovaConsulta implements ActionListener {
		public void actionPerformed(ActionEvent evento) {
			janela.getContentPane().removeAll();
			janela.conteudoJanela(new PainelHashJoin(janela, conexao, banco));
			janela.revalidate();
			janela.repaint();
		}
	}

	void setTamanhoColuna(JTable valor, String[] titulo){
		TableColumn colunas = null;
		int tamanho = titulo.length;
		int largura = 770/tamanho < 60? 60 : 770/tamanho - 1;

		for (int coluna = 0; coluna < tamanho; coluna++){
			colunas = valor.getColumnModel().getColumn(coluna);
			colunas.setPreferredWidth(largura);
		}
	}


	public class BotaoPainelLateral implements ActionListener {
		boolean pressionado = false;

		String expandir;
		BotaoPainelLateral(String expandir) {
			this.expandir = expandir;
		}

		public void actionPerformed(ActionEvent evento) {
			painelLateral.removeAll();

			if (! expansaoAtual.equals(expandir)){
				expansaoAtual = expandir;
				pressionado = true;
			}

			else {
				expansaoAtual = "";
				pressionado = false;
			}

			dimensaoLateral.setSize(350, 0);

			HashMap<String, ArrayList<String>> infoBanco = banco.getTabelas();
			Set<String> chaves = infoBanco.keySet();


			JPanel painelTitulo = new JPanel();
			painelTitulo.setBorder(bordaVazia);
			painelTitulo.setPreferredSize(new Dimension(350, 60));
			painelTitulo.setBackground(Cores.corBranca);
			painelTitulo.setLayout(new FlowLayout());

			JLabel titulo = new JLabel("TABELAS E ATRIBUTOS");
			titulo.setFont(Fontes.ROBOTO_BOLD_MENOR);
			titulo.setForeground(Cores.corBotaoAzulEscuro);

			painelTitulo.add(titulo, "Center");

			adicionaPainelLateral(painelTitulo, 80);


			for(String chave: chaves) {
				JPanel painelBotao = new JPanel();
				painelBotao.setBorder(bordaVazia);
				painelBotao.setPreferredSize(new Dimension(350, 60));

				Botao botaoBanco = new Botao(chave);
				botaoBanco.setMargin(new Insets(5, 10, 5, 10));
				botaoBanco.configurarFonteCorFundo(Fontes.ROBOTO_MENOR, Cores.corBranca, Cores.rosaForte);
				botaoBanco.setPreferredSize(new Dimension(350, 60));
				botaoBanco.setHorizontalAlignment(SwingConstants.LEFT);
				botaoBanco.addActionListener(new BotaoPainelLateral(chave));
				painelBotao.add(botaoBanco);

				adicionaPainelLateral(painelBotao, 70);

				if (chave.equals(expandir) && pressionado) {

					ArrayList<String> atributos = infoBanco.get(chave);
					for (int i = 0; i < atributos.size(); i++) {
						JPanel painelAtributo = new JPanel();
						painelAtributo.setBorder(bordaVazia);
						painelAtributo.setPreferredSize(new Dimension(300, 40));
						painelAtributo.setBackground(Cores.corBranca);

						JLabel labelAtributo = new JLabel(atributos.get(i));
						labelAtributo.setBorder( BorderFactory.createEmptyBorder(0, 0, 0, 0));
						labelAtributo.setHorizontalAlignment(SwingConstants.LEFT);
						labelAtributo.setFont(Fontes.ROBOTO_MENOR);
						labelAtributo.setForeground(Cores.azulEscuro2);

						painelAtributo.add(labelAtributo);

						adicionaPainelLateral(painelAtributo, 50);
					}
				}
			}

			painelLateral.revalidate();
			painelLateral.repaint();
		}
	}
}
