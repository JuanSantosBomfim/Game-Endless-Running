package view;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import main.Main;
import model.Progresso;
import sqlitedb.ProgressoDAO;
import thread.CutsceneThread;
import thread.GerenciarTelasThread;

import javax.swing.JLabel;

// Classe responsavel por fazer a troca de telas após um botão ser pressionado
public class GerenciarTelas {

	public static int telaLargura = 1000;
	public static int telaAltura = 640;

	public static JFrame janela;
	public static JPanel telaInicialPainel;
	public static JPanel telaJogoPainel;
	
	public static JLabel imgPersonagemCorrendo;
	public static int x;
	public static boolean personagemCorrendo;

	// Construtor da classe é chamado ao instanciarmos a classe, e isso ocorre assim que os dados do jogo são carregados na classe "Main"
	public GerenciarTelas() {	
		
		CriarJanelaPrincipal();
		TelaInicial();

		//System.out.println("QTD Frames = "+Main.quantidadeFrame);
		
		//CutsceneThread gerenciarTelaThread = new CutsceneThread("UtilitarioJogo/imagem/introJogo.gif", 150, "intro");
		//gerenciarTelaThread.start();
	}

	public static void CriarJanelaPrincipal() {

		janela = new JFrame();

		janela.setSize(telaLargura, telaAltura);
		janela.setTitle("CORRIDA PRESIDENCIAL.EXE");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false);		
		janela.setLocationRelativeTo(null);
		janela.setLayout(null);
		
		ImageIcon img_caminho = new ImageIcon("UtilitarioJogo/imagem/img_cabeca_jogador.png");
		Image imagem = img_caminho.getImage();
		janela.setIconImage(imagem);
						
		janela.setVisible(true);
	}
	
	public static void TelaInicial() {
		
		telaInicialPainel = new JPanel();
		telaInicialPainel.setLayout(null);
		janela.setContentPane(telaInicialPainel);
		
		// CRIA OS BOTOES
		JButton botaoJogar, botaoLoja, botaoRanking;

		botaoJogar = new JButton("  Jogar");
		botaoLoja = new JButton("  Loja");
		botaoRanking = new JButton("  Estatística");
		
		botaoJogar.setIcon(new ImageIcon("UtilitarioJogo/imagem/finish.png"));
		botaoLoja.setIcon(new ImageIcon("UtilitarioJogo/imagem/cart.png"));
		botaoRanking.setIcon(new ImageIcon("UtilitarioJogo/imagem/graph.png"));	
		
		botaoJogar.setFont(Main.fontePixel);
		botaoLoja.setFont(Main.fontePixel);
		botaoRanking.setFont(Main.fontePixel);
		
		botaoJogar.setBounds(400, 300, 200, 50);
		telaInicialPainel.add(botaoJogar);
		botaoLoja.setBounds(400, 350, 200, 50);
		telaInicialPainel.add(botaoLoja);
		botaoRanking.setBounds(400, 400, 200, 50);
		telaInicialPainel.add(botaoRanking);
		
		// DEFINE OS VALORES INICIAIS, DO GIF DO PERSONAGEM
		x = -60;
		personagemCorrendo = true;
		
		// CRIA O GIF DO PERSONAGEM
		imgPersonagemCorrendo = new JLabel("");
		ImageIcon img_caminho3 = new ImageIcon("UtilitarioJogo/imagem/personagem.gif");
		imgPersonagemCorrendo.setIcon(img_caminho3);
		imgPersonagemCorrendo.setBounds(x, 400, 600, 300);
		telaInicialPainel.add(imgPersonagemCorrendo);
		
		// INICIA A THREAD QUE CALCULA A POSICAO DO GIF DO PERSONAGEM
		GerenciarTelasThread gerenciarTelasThread = new GerenciarTelasThread(x);
		gerenciarTelasThread.start();
		
		JLabel lblTituloJogo = new JLabel("");
		ImageIcon img_caminho2 = new ImageIcon("UtilitarioJogo/imagem/tituloJogo.png");
		lblTituloJogo.setIcon(img_caminho2);
		lblTituloJogo.setBounds(190, 30, 630, 300);
		telaInicialPainel.add(lblTituloJogo);
		
		JLabel lblFundoJogo = new JLabel("");
		ImageIcon img_caminho = new ImageIcon("UtilitarioJogo/imagem/fundo3.png");
		lblFundoJogo.setIcon(img_caminho);
		lblFundoJogo.setBounds(2, -15, 1000, 640);
		telaInicialPainel.add(lblFundoJogo);
		
		// REPINTA A TELA
		try {
			janela.repaint(200);
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		botaoJogar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Main.somMouseClique.IniciarSom();
				
				try {
					Main.musicaMenu.PararSom();
				} catch (Exception e2) {
				}
				
				Main.musicaJogo.IniciarSom();

				telaInicialPainel.setVisible(false);
				personagemCorrendo = false;
				
				Jogar();
			}
		});

		botaoLoja.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Main.somMouseClique.IniciarSom();

				telaInicialPainel.setVisible(false);
				personagemCorrendo = false;
				
				new TelaLoja(janela);
			}
		});

		botaoRanking.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Main.somMouseClique.IniciarSom();
				
				ProgressoDAO progressoDAO = new ProgressoDAO(Main.conexaoBanco);
				ArrayList<Progresso> lstCincoUltProgresso = progressoDAO.UltimosCincoProgresso();

				if (lstCincoUltProgresso != null) {	
					
					telaInicialPainel.setVisible(false);
					personagemCorrendo = false;
					
					new TelaRanking(janela);	
					
				}else {					
					JOptionPane.showMessageDialog(null, "Jogue 5 partidas para acessar o Ranking", "Partidas Insuficientes", JOptionPane.INFORMATION_MESSAGE);				
				}							
			}
		});
	}
	
	public static void Jogar() {
		
		telaJogoPainel = new TelaJogo();
		telaJogoPainel.setLayout(null);
		janela.setContentPane(telaJogoPainel);	
	}
}
