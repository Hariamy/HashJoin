package visao.janela;

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

	private Dimension dimensaoCental;
	private Box boxCentral;

	private JPanel painelSuperior = new JPanel();
	private JPanel painelCentro = new JPanel();

	public PainelBancos(Janela janela){
		super();
		this.janela = janela;
		this.configuracoes();
		this.setVisible(true);
	}


	private void configuracoes(){

		//-----------------\\ LAYOUT DA PÁGINA INICIAL //-----------------\\


		painelSuperior.setBorder(bordaVazia);
		painelCentro.setBorder(bordaVazia);

		this.setLayout(new BorderLayout());
		painelSuperior.setLayout(new BorderLayout());
		painelCentro.setLayout(new FlowLayout());

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




		dimensaoCental = new Dimension(800, 0);

		painelCentro.setPreferredSize(dimensaoCental);
		boxCentral = Box.createHorizontalBox();

		JScrollPane rolagem = new JScrollPane(painelCentro);
		rolagem.setPreferredSize(new Dimension( 800,0));
		rolagem.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

		boxCentral.add(rolagem);
		boxCentral.setBorder(bordaVazia);
		this.add(boxCentral, BorderLayout.CENTER);

	}

	public void adicionaPainelCentral(JPanel painel, int quanto){
		dimensaoCental.setSize(800, dimensaoCental.getHeight()+quanto);
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

}
