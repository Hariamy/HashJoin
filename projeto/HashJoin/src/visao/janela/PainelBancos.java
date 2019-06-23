package visao.janela;

import modelo.Database;
import modelo.conexao.Conectar;
import modelo.conexao.Executar;

import visao.layout.Botao;
import visao.layout.Cores;
import visao.layout.Fontes;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class PainelBancos extends JPanel{

	private ArrayList<String> bancos = new ArrayList<>();
	private Border bordaVazia = BorderFactory.createEmptyBorder(0, 0, 0, 0);

	private Janela janela;
	private Conectar conexao;

	private Dimension dimensaoCental;
	private Box boxCentral;

	private JPanel painelSuperior = new JPanel();
	private JPanel painelCentro = new JPanel();

	private Executar ex = new Executar();

	public PainelBancos(Janela janela, Conectar conexao){
		super();
		this.janela = janela;
		this.conexao = conexao;
		this.configuracoes();
		this.setVisible(true);
	}


	private void configuracoes(){

		//-----------------\\ LAYOUT DA PÁGINA INICIAL //-----------------\\


		painelSuperior.setBorder(bordaVazia);
		painelCentro.setBorder(bordaVazia);

		this.setLayout(new BorderLayout());
		painelSuperior.setLayout(new BorderLayout());
		painelCentro.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));

		this.setBackground(Cores.rosaClaro);
		painelSuperior.setBackground(Cores.rosaClaro);
		painelCentro.setBackground(Cores.azulEscuro);


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

		JLabel informacao = new JLabel("ESCOLHA O BANCO DE DADOS");
		informacao.setPreferredSize(new Dimension(1080, 80));
		informacao.setFont(Fontes.ROBOTO_BOLD_MEDIA);
		informacao.setForeground(Cores.corBotaoAzulEscuro);
		informacao.setHorizontalAlignment(SwingConstants.CENTER);

		JPanel painelTitulo = new JPanel(new FlowLayout());
		painelTitulo.setBorder(bordaVazia);
		painelTitulo.setPreferredSize(new Dimension(1080, 80));
		painelTitulo.setBackground(Cores.azulEscuro);

		painelTitulo.add(informacao, "Center");

		adicionaPainelCentral(painelTitulo, 80);

		//-----------------\\ INÍCIO - ADICIONA OS BOTÕES REFERENTES AOS BANCOS DE DADOS //-----------------\\


		bancos = ex.getNomeBanco( conexao);

		if (ex.getExecutou()) {
			for (int i = 0; i < bancos.size(); i++){
				JPanel painelBotao = new JPanel();
				painelBotao.setBorder(bordaVazia);
				painelBotao.setPreferredSize(new Dimension(400, 80));
				painelBotao.setBackground(Cores.azulEscuro);

				Botao botaoBanco = new Botao(bancos.get(i).toUpperCase());
				botaoBanco.setMargin(new Insets(10, 80, 10, 80));
				botaoBanco.configurarFonteCorFundo(Fontes.ROBOTO_BOLD_MEDIA, Cores.corBranca, Cores.azulEscuro2);
				botaoBanco.addActionListener(new BotaoBanco(bancos.get(i)));

				painelBotao.add(botaoBanco);

				adicionaPainelCentral(painelBotao, 100);

			}
		}
	}

	public void adicionaPainelCentral(JPanel painel, int quanto){
		dimensaoCental.setSize(400, dimensaoCental.getHeight()+quanto);
		painelCentro.add(painel);
		painelCentro.revalidate();
		boxCentral.revalidate();
	}



	// É ISSO MESMO PRODUÇÃO - CLASSES DENTRO DE CLASSES
	public class BotaoVoltar implements ActionListener {
		public void actionPerformed(ActionEvent evento) {
			janela.getContentPane().removeAll();
			janela.conteudoJanela(new PainelInicial(janela));
			janela.revalidate();
			janela.repaint();
		}
	}

	public class BotaoBanco implements ActionListener {
		String banco;

		BotaoBanco(String banco) {
			this.banco = banco;
		}

		public void actionPerformed(ActionEvent evento) {

			new Carregamento(banco).start();
			new CarregaInfo(banco).start();

		}

	}

	class CarregaInfo extends Thread {
		String banco;

		CarregaInfo(String banco) {
			this.banco = banco;
		}

		public void run() {
			//-----------------\\ INÍCIO - ADQUIRINDO OS DADOS DA TABELA  //-----------------\\

			conexao.setDatabase(banco);

			ArrayList<String> resp = ex.getTabelasBanco(conexao);

			HashMap<String, ArrayList<String>> tabelaAtributo = new HashMap<>();

			for (int i = 0; i < resp.size(); i++) {
				tabelaAtributo.put(resp.get(i), ex.getTabelaAtributos(conexao, resp.get(i)));
			}

			Database bancoEscolhido = new Database(banco, tabelaAtributo);

			janela.getContentPane().removeAll();
			janela.conteudoJanela(new PainelHashJoin(janela, conexao, bancoEscolhido));
			janela.revalidate();
			janela.repaint();

			//-----------------\\ FIM - ADQUIRINDO OS DADOS DA TABELA  //-----------------\\
		}
	}

	class Carregamento extends Thread {
		String banco;
		Carregamento(String banco) {
			this.banco = banco;
		}
		public void run() {
			//-----------------\\ INÍCIO - PAINEL DE CARREGAMENTO  //-----------------\\

			painelSuperior.removeAll();
			painelCentro.removeAll();

			JLabel informacao = new JLabel("Adquirindo dados do banco: "+banco+" ...");
			informacao.setFont(Fontes.ROBOTO_GRANDE);
			informacao.setForeground(Cores.corBotaoAzulEscuro);

			painelCentro.add(informacao);

			painelSuperior.revalidate();
			painelCentro.revalidate();

			painelSuperior.repaint();
			painelCentro.repaint();

			//-----------------\\ FIM - PAINEL DE CARREGAMENTO  //-----------------\\
		}
	}
}
