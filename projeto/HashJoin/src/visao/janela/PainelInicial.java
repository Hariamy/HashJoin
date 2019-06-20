package visao.janela;
import visao.layout.Botao;
import visao.layout.Cores;
import visao.layout.Fontes;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;

import modelo.conexao.Conectar;

public class PainelInicial extends JPanel {
	private Janela janela;
	private boolean focoInicial = true;

	private JPanel painelSuperior = new JPanel();
	private JPanel painelInferior = new JPanel();
	private JPanel painelLogin = new JPanel();

	private JTextField servidor;
	private JTextField porta;
	private JTextField login;
	private JPasswordField senha;

	private Conectar conexao;

	public PainelInicial(Janela janela){
		super();
		this.configuracoes();
		this.janela = janela;
		this.setVisible(true);
	}

	public Conectar getConexao() {
		return conexao;
	}

	private void configuracoes(){

		//-----------------\\ INÍCIO - LAYOUT DA PÁGINA INICIAL //-----------------\\

		Border bordaVazia = BorderFactory.createEmptyBorder(0, 0, 0, 0);
		this.setBorder(bordaVazia);
		painelInferior.setBorder(bordaVazia);
		painelSuperior.setBorder(bordaVazia);

		this.setLayout(new BorderLayout());
		painelSuperior.setLayout(new BorderLayout());
		painelInferior.setLayout(new FlowLayout());
		painelLogin.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 50));

		this.setBackground(Cores.rosaClaro);
		painelSuperior.setBackground(Cores.rosaClaro);
		painelInferior.setBackground(Cores.rosaClaro);
		painelLogin.setBackground(Cores.rosaClaro);

		painelLogin.setPreferredSize(new Dimension(550, 500));

		//-----------------\\ FIM - LAYOUT DA PÁGINA INICIAL //-----------------\\


		//-----------------\\ INÍCIO Carrega Imagtem da Tela Inicial - Capa //-----------------\\
		try {
			//IMAGEM DA TELA DE INICIO
			URL inicial = ClassLoader.getSystemResource("imagens/inicial.png");
			Icon imagemTitulo = new ImageIcon(inicial);
			JLabel titulo = new JLabel(imagemTitulo, SwingConstants.CENTER);
			painelSuperior.add(titulo, BorderLayout.CENTER);
			painelSuperior.setPreferredSize(new Dimension(400, 200));

		}catch (Exception e){
			//Carrega icone alternativo
			Icon imagemTitulo = new ImageIcon(getClass().getResource("/imagens\\inicial.png"));
			JLabel titulo = new JLabel(imagemTitulo, SwingConstants.CENTER);
			painelSuperior.add(titulo, BorderLayout.CENTER);
			painelSuperior.setPreferredSize(new Dimension(400, 200));
		}
		//-----------------\\ FIM - Carrega Imagtem da Tela Inicial - Capa  //-----------------\\


		//-----------------\\ INÍCIO - INSERIR NOME DO SERVIDOR, PORTA, LOGIN E SENHA  //-----------------\\
		Border bordaTexto = BorderFactory.createEmptyBorder(7, 5, 7, 5);
		Cursor cursor = new Cursor(Cursor.TEXT_CURSOR);
		Dimension dimensaoDoTitulo = new Dimension(160,20);


		// CAMPO SERVIDOR
		JLabel titulServidor = new JLabel("SERVIDOR", SwingConstants.CENTER);
		titulServidor.setFont(Fontes.ROBOTO_BOLD_PEQUENA);
		titulServidor.setForeground(Cores.corBotaoAzulEscuro);
		titulServidor.setPreferredSize(dimensaoDoTitulo);

		servidor = new JTextField("", 14);
		servidor.setCursor(cursor);
		servidor.setBorder(bordaTexto);
		servidor.setSize(18, 5);
		servidor.setFont(Fontes.ROBOTO_MENOR);

		painelLogin.add(titulServidor, BorderLayout.NORTH);
		painelLogin.add(servidor, BorderLayout.NORTH);


		// CAMPO PORTA
		JLabel titulPorta = new JLabel("PORTA", SwingConstants.CENTER);
		titulPorta.setFont(Fontes.ROBOTO_BOLD_PEQUENA);
		titulPorta.setForeground(Cores.corBotaoAzulEscuro);
		titulPorta.setPreferredSize(dimensaoDoTitulo);

		porta = new JTextField("", 14);
		porta.setCursor(cursor);
		porta.setBorder(bordaTexto);
		porta.setSize(18, 5);
		porta.setFont(Fontes.ROBOTO_MENOR);

		painelLogin.add(titulPorta, BorderLayout.NORTH);
		painelLogin.add(porta, BorderLayout.NORTH);


		// CAMPO LOGIN
		JLabel tituloLogin = new JLabel("LOGIN", SwingConstants.CENTER);
		tituloLogin.setFont(Fontes.ROBOTO_BOLD_PEQUENA);
		tituloLogin.setForeground(Cores.corBotaoAzulEscuro);
		tituloLogin.setPreferredSize(dimensaoDoTitulo);

		login = new JTextField("", 14);
		login.setCursor(cursor);
		login.setBorder(bordaTexto);
		login.setSize(18, 5);
		login.setFont(Fontes.ROBOTO_MENOR);

		painelLogin.add(tituloLogin, BorderLayout.NORTH);
		painelLogin.add(login, BorderLayout.NORTH);

		// CAMPO SENHA
		JLabel tituloSenha = new JLabel("SENHA", SwingConstants.CENTER);
		tituloSenha.setFont(Fontes.ROBOTO_BOLD_PEQUENA);
		tituloSenha.setForeground(Cores.corBotaoAzulEscuro);
		tituloSenha.setPreferredSize(dimensaoDoTitulo);

		senha = new JPasswordField("", 14);
		senha.setCursor(cursor);
		senha.setBorder(bordaTexto);
		senha.setSize(18, 5);
		senha.setEchoChar('*');
		senha.setFont(Fontes.ROBOTO_MENOR);

		painelLogin.add(tituloSenha, BorderLayout.NORTH);
		painelLogin.add(senha, BorderLayout.NORTH);

		painelInferior.add(painelLogin, "North");


		//-----------------\\ FIM - INSERIR NOME DO SERVIDOR, PORTA, LOGIN E SENHA  //-----------------\\



		//-----------------\\ INÍCIO - BOTAO DO LOGAR //-----------------\\

		Botao uruario = new Botao("CONECTAR");
		uruario.setMargin(new Insets(2, 55, 2, 55));
		uruario.configurarFonteCorFundo(Fontes.ROBOTO_BOLD_PEQUENA, Cores.corBranca, Cores.azulEscuro2);
		uruario.addActionListener(new BotaoConectar());

		painelLogin.add(uruario, "Center");
		painelInferior.add(painelLogin, "Center");

		//-----------------\\ FIM - BOTAO DO LOGAR //-----------------\\


		//-----------------\\ INÍCIO - ADICIONA OS PAINEIS NA JANELA PRINCIPAL //-----------------\\

		this.add(painelSuperior, "North");
		this.add(painelInferior, "Center");

		//-----------------\\ FIM - ADICIONA OS PAINEIS NA JANELA PRINCIPAL //-----------------\\

	}

	//-----------------\\ CLASSE IMPLEMENTA A CHAMADA DO PAINEL DO USUÁRIO //-----------------\\
	public class BotaoConectar implements ActionListener {
		public void actionPerformed(ActionEvent evento) {

			// Recupera os valores
			String textServidor = servidor.getText().equals("") ? "Ubuntinho-s2" : servidor.getText();
			String textPorta = porta.getText().equals("") ? "1433" : porta.getText();
			String textLogin = porta.getText().equals("") ? "sa" : login.getText();
			String textSenha = porta.getText().equals("") ? "admin123H" : senha.getText();

			// Configura a conexão
			conexao = new modelo.conexao.Conectar();
			conexao.setServer(textServidor, textPorta);
			conexao.setLogin(textLogin, textSenha);

			janela.getContentPane().removeAll();
			janela.conteudoJanela(new PainelBancos(janela));
			janela.revalidate();
			janela.repaint();
			
		}
	}

}