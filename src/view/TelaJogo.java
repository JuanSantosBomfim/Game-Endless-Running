package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import main.Main;
import model.Fundo;
import model.InterfaceJogo;
import model.Melhoria;
import model.JogadorMelhoria;
import model.Progresso;
import objetofamily.Item;
import objetofamily.Jogador;
import objetofamily.Objeto;
import sqlitedb.JogadorDAO;
import sqlitedb.MelhoriaDAO;
import sqlitedb.JogadorMelhoriaDAO;
import thread.DebateThread;
import thread.JogoThread;
import thread.ProgressoThread;

// Classe importante, responsavel pelo jogo
// Desenha na tela todas as coisas, e instancia classes e thread para fazer os calculos.
// Tambem acessa o banco de dados no para salvar o progresso do jogador, e o total de votos obtidos
public class TelaJogo extends JPanel {
	
	// Cria um novo progresso, para calcular a distancia percorrida
	private Progresso progresso = new Progresso();
	
	// JOGO
	public static boolean iniciarJogo;
	public static boolean jogando;
	public static boolean finalizarJogo;
	public static boolean personagemVivo;
	
	public static double velocidadeGlobal;
	
	// DEBATE
	public static boolean iniciarDebate;
	public static boolean debatendo;
	public static boolean mostrarVencedorDebate;
	public static boolean finalizarDebate;
	
	public static boolean iniciarSorteioFrase;
	public static boolean terminouSorteioFrase;
	public static int mostrarBotaoDebate;
	public static int recarregarBotaoDebate;
	
	private String fraseEscolhidaJogador;
	public static String fraseEscolhidaAdversario;
	
	public static boolean jogadorEscolheuFrase;
	public static boolean adversarioEscolheuFrase;
	
	public static int turnoJogador;
	public static String vencedorDebate;
	
	// INTERFACE
	private InterfaceJogo interfaceProgresso;
	private InterfaceJogo interfaceProgressoPercorrido;
	private InterfaceJogo interfaceMsgFimJogo;
	private InterfaceJogo interfaceFundoDebate;
	private InterfaceJogo interfaceFraseDebateJogador;
	private InterfaceJogo interfaceFraseDebateAdversario;
	private InterfaceJogo interfaceVencedorDebate;

	// FUNDO
	private Fundo fundo1;
	private Fundo fundo2;
	private Fundo fundo3;
	
	// OBJETO
	private Objeto[] objeto = new Objeto[4];
	public static Item[] item = new Item[2];

	// PERSONAGEM
	public static Jogador jogador;
	private Objeto jogadorDebate;
	private Objeto adversarioDebate;
	public static int jogadorPontuacao = 10;
	public static int adversarioPontuacao = 10;

	// BOTAO
	private JButton botaoJogarNovamente, botaoTelaInicial, botaoItensObtido;
	private JButton botaoFrase1, botaoFrase2, botaoFrase3, botaoFrase4;
	private int numeroBotao;
	
	private boolean opcaoFimJogo;
	private boolean salvarProgresso;
	
	public static String img1 = "";
	private String img2 = "";
	private String img3 = "";

	public TelaJogo() {
		
		this.setLayout(null);
		this.setFocusable(true);
		this.setDoubleBuffered(true);
		
		iniciarJogo = true;
		velocidadeGlobal = 10;	
	}

	// METODO RESPONSAVEL POR DESENHAR NA TELA
	public void paintComponent(Graphics graphics) {		
		super.paintComponent(graphics);

		// INSTANCIA O OBJETO RESPONSAVEL POR DESENHAR
		Graphics2D graficos2d = (Graphics2D) graphics;	

		IniciarJogo();
		Jogando(graficos2d);
		FinalizarJogo();
		
		IniciarDebate();
		Debatendo(graficos2d);
		FinalizarDebate();
		
		//System.
		
		// FIM DE JOGO
		if (opcaoFimJogo == true) {	
			BotaoMenuOpcao();
			SalvarProgresso();	
			interfaceMsgFimJogo.DesenharInterface(graficos2d);
			
		}else {			
			this.requestFocus();		
		}

		// PINTA TUDO NOVAMENTE
		repaint();
	}
	
	// Metodo responsavel por inicializar todas as variaveis e threads utilizadas no jogo "corrida"
	public void IniciarJogo() {
		
		if(iniciarJogo == true) {
			
			// RESETAR VALORES
			JogoThread.personagemInvencivel = false;
			JogoThread.personagemComIma = false;
				
			iniciarJogo = false;
			iniciarDebate = false;
			debatendo = false;
			finalizarDebate = false;		
			iniciarSorteioFrase = false;
			terminouSorteioFrase = false;
			mostrarBotaoDebate = 0;
			recarregarBotaoDebate = 0;
			jogadorEscolheuFrase = false;
			adversarioEscolheuFrase = false;
			opcaoFimJogo = false;
			progresso.setDistancia_percorrida(0);
			salvarProgresso = false;
			mostrarVencedorDebate = false;
			vencedorDebate = "";
		
			turnoJogador = 0;
			jogando = true;
			personagemVivo = true;		
			
			// INSTANCIA AS IMAGENS DA INTERFACE
			interfaceProgresso = new InterfaceJogo("img_progresso.png", 1000, 10, 0, 20);
			interfaceProgressoPercorrido = new InterfaceJogo("img_cabeca_jogador.png", 25, 25, 0, 20);
			interfaceMsgFimJogo = new InterfaceJogo("jogoDerrota.png", 630, 290, 200, 100);
			
			int numeroImg  = ThreadLocalRandom.current().nextInt(0, 2);
			
			
			if (numeroImg == 0) {
				img1 = "fundo1.png";
				img2 = "fundo2.png";
				img3 = "fundo3.png";
				
				
			}else {
				img1 = "fundo4.png";
				img2 = "fundo1.png";
				img3 = "fundo5.png";
			}
			
			// INSTANCIA OS FUNDOS
			fundo1 = new Fundo(img1, 0, 0, 3360, 0, (int)TelaJogo.velocidadeGlobal, 0, 3360, 620);
			fundo2 = new Fundo(img2, 0, 0, 2500, 0, (int)TelaJogo.velocidadeGlobal / 2, 0, 2500, 620);
			fundo3 = new Fundo(img3, 0, 0, 2000, 0, (int)TelaJogo.velocidadeGlobal / 3, 0, 2000, 620);
			
			// INSTANCIA OS OBJETOS
			jogador = new Jogador("personagem", "personagem.png", 80, 100, 50, 50, 0, this, progresso);
					
			objeto[0] = new Objeto("plataforma", "plataforma1.png", 360, 200, GerenciarTelas.telaLargura + 700, GerenciarTelas.telaAltura - 260, -(int)TelaJogo.velocidadeGlobal);
			objeto[1] = new Objeto("plataforma", "plataforma1.png", 360, 200, GerenciarTelas.telaLargura + 700, GerenciarTelas.telaAltura - 260, -(int)TelaJogo.velocidadeGlobal);
			
			objeto[2] = new Objeto("obstaculo", "obstaculo1.gif", 100, 100, GerenciarTelas.telaLargura + 700, GerenciarTelas.telaAltura - 150, -(int)TelaJogo.velocidadeGlobal);
			objeto[3] = new Objeto("obstaculo", "obstaculo1.gif", 100, 100, GerenciarTelas.telaLargura + 700, GerenciarTelas.telaAltura - 150, -(int)TelaJogo.velocidadeGlobal);
			
			
			item[0] = new Item("item", "voto", "voto1.gif", 100, 100, GerenciarTelas.telaLargura + 700, GerenciarTelas.telaAltura, -(int)TelaJogo.velocidadeGlobal);
			item[1] = new Item("item", "voto", "voto1.gif", 100, 100, GerenciarTelas.telaLargura + 700, GerenciarTelas.telaAltura, -(int)TelaJogo.velocidadeGlobal);
			
			// INICIA AS THREADS
			JogoThread jogoThread = new JogoThread(jogador, objeto, item, progresso);
			jogoThread.start();

			ProgressoThread progressoThread = new ProgressoThread(progresso);
			progressoThread.start();
		}	
	}
	
	// Metodo responsavel por desenhar somente as coisas enquanto jogando"corrida"
	public void Jogando(Graphics2D graficos2d) {
		
		if(jogando == true || opcaoFimJogo == true) {
			
			// Desenha os fundos na tela
			fundo3.Desenhar(graficos2d);
			fundo2.Desenhar(graficos2d);
			fundo1.Desenhar(graficos2d);			
			
			// DESENHA OS ITENS
			for (int numeroItem = 0; numeroItem < objeto.length; numeroItem++) {
				if (objeto[numeroItem].getTipoObjeto().equals("plataforma")) {
					objeto[numeroItem].DesenharObjeto(graficos2d);
				}		
			}
			
			for (int numeroItem = 0; numeroItem < objeto.length; numeroItem++) {
				if (objeto[numeroItem].getTipoObjeto().equals("obstaculo")) {
					objeto[numeroItem].DesenharObjeto(graficos2d);
				}
			}
			
			for (int numeroItem = 0; numeroItem < item.length; numeroItem++) {
				item[numeroItem].DesenharObjeto(graficos2d);
			}
			
			// Desenha o personagem na tela
			jogador.DesenharPersonagem(graficos2d);
			
			// DESENHA A INTERFACE
			interfaceProgresso.DesenharInterface(graficos2d);
			interfaceProgressoPercorrido.DesenharInterface(graficos2d, progresso.getDistancia_percorrida());	
			
			// DESENHA O PROGRESSO
			progresso.DesenharQuantidadeVoto(graficos2d);
			
			// DESENHA MENSAGEM JOGADOR INVENCIVEL
			if(JogoThread.personagemComIma == true) {	
				
				graficos2d.setFont(new Font("Pixeled", Font.BOLD, 13));
				JogadorDAO jogadorDAO = new JogadorDAO(Main.conexaoBanco);
				
				// MUDA A COR DA FONTE DE ACORDO COM O FUNDO
				if(TelaJogo.img1.equals("fundo4.png")) {
					
					graficos2d.setColor(Color.WHITE);
					
				}else {				
					graficos2d.setColor(Color.BLACK);
				}
				
				graficos2d.drawString("COLETOR DE VOTOS " , 300, 90);		
				graficos2d.drawString(""+String.format( "%.0f", JogoThread.tempo_ima) , 380, 130);						
			}	
			
			// DESENHA MENSAGEM JOGADOR INVENCIVEL
			if(JogoThread.personagemInvencivel == true) {	
							
				graficos2d.setFont(new Font("Pixeled", Font.BOLD, 13));
				JogadorDAO jogadorDAO = new JogadorDAO(Main.conexaoBanco);
				
				// MUDA A COR DA FONTE DE ACORDO COM O FUNDO
				if(TelaJogo.img1.equals("fundo4.png")) {
					
					graficos2d.setColor(Color.WHITE);
					
				}else {				
					graficos2d.setColor(Color.BLACK);
				}
				
				graficos2d.drawString("INVENCIVEL ", 550, 90);
				graficos2d.drawString(""+String.format( "%.0f", JogoThread.tempo_invencibilidade) , 600, 130);	
			}		
		}
	}
	
	// Metodo responsavel por finalizar o jogo e(ou) iniciar o debate
	public void FinalizarJogo() {
		
		if (finalizarJogo == true) {	
			finalizarJogo = false;
			jogando = false;
			
			if(personagemVivo == true) {
				
				iniciarDebate = true;
				velocidadeGlobal = velocidadeGlobal +(1.5 * progresso.getQtd_adversario_derrotado());
				
			}else {		
				opcaoFimJogo = true;
				salvarProgresso = true;
			}
		}
	}
	
	// Metodo responsavel por inicializar todas as variaveis e threads utilizadas no jogo "debate"
	public void IniciarDebate() {
		
		if (iniciarDebate == true) {
			iniciarDebate = false;
			
			jogadorPontuacao = 10;
			adversarioPontuacao = 10;
			ProgressoThread.delay = 1000;
			iniciarSorteioFrase = true;
			debatendo = true;
			turnoJogador = 1;
			
			int numeroAleatorio = ThreadLocalRandom.current().nextInt(1, 5);
			String nomeAdversario = "";			
			
			String imagemAdversario = "adversarioDebate"+numeroAleatorio+".gif";
			
			jogadorDebate = new Objeto("personagem", "personagemDebate.gif", 100, 150, 190, 310, 0);
			adversarioDebate = new Objeto("adversario", imagemAdversario, 150, 180, 680, 230, 0);
			
			interfaceFundoDebate =  new InterfaceJogo("debateFundo1.jpg", GerenciarTelas.telaLargura, GerenciarTelas.telaAltura, 0, 0);						
			interfaceFraseDebateAdversario = new InterfaceJogo("debateTexto2.png", 550, 220, 300, 50);
			interfaceFraseDebateJogador = new InterfaceJogo("debateTexto1.png", 550, 220, 120, 100);
			interfaceVencedorDebate = new InterfaceJogo("", 630, 290, 200, 100);
			
			DebateThread debateThread = new DebateThread(progresso);
			debateThread.start();
		}
	}
	
	// Metodo responsavel por desenhar somente as coisas enquanto jogando"debate"
	public void Debatendo(Graphics2D graficos2d) {	
		
		if (debatendo == true) {

			interfaceFundoDebate.DesenharInterface(graficos2d);

			jogadorDebate.DesenharObjeto(graficos2d);
			//jogadorDebate.DesenharVida(graficos2d);

			adversarioDebate.DesenharObjeto(graficos2d);
			//adversarioDebate.DesenharNome(graficos2d);
			
			// APOS O JOGADOR ESCOLHER UMA FRASE
			if (jogadorEscolheuFrase) {
				
				RemoverBotaoDebate();				
					
				interfaceFraseDebateJogador.DesenharInterface(graficos2d);				
				graficos2d.setFont(new Font("Pixeled", Font.BOLD, 10));
				graficos2d.drawString(fraseEscolhidaJogador , 140, 140);
				
			// RECARREGA OS BOTOES COM AS FRASES
			}else if((jogadorEscolheuFrase == false) && (recarregarBotaoDebate == 1)) {
				
				System.out.println("ATIVA BOTAO");
				recarregarBotaoDebate = 2;
				mostrarBotaoDebate = 1;			
			}	
			
			// APOS O ADVERSARIO ESCOLHER UMA FRASE
			if (adversarioEscolheuFrase) {			
							
				interfaceFraseDebateAdversario.DesenharInterface(graficos2d);			
				graficos2d.setFont(new Font("Times new roman", Font.BOLD, 20));
				graficos2d.drawString(fraseEscolhidaAdversario , 320, 90);
			}

			if (mostrarBotaoDebate == 1) {
				BotaoDebate();
				mostrarBotaoDebate = 2;
			}
					
			// MOSTRA O VENCEDOR DO DEBATE
			if(mostrarVencedorDebate == true) {
				
				RemoverBotaoDebate();
				
				if(vencedorDebate.equals("adversario")) {
					interfaceVencedorDebate.setImagem("jogoDerrota.png");
				}
				
				if(vencedorDebate.equals("jogador")) {
					interfaceVencedorDebate.setImagem("jogoVitoria.png");
				}
				
				interfaceVencedorDebate.DesenharInterface(graficos2d);			
			}		
		}
	}
	
	// Metodo responsavel por finalizar o debate e passar para a proxima fase
	public void FinalizarDebate() {
		
		if (finalizarDebate == true) {
			finalizarDebate = false;
			
			System.out.println("FIM DEBATE");
			
			if(vencedorDebate.equals("jogador")) {
				
				iniciarJogo = true;
						
			}else if(vencedorDebate.equals("adversario")) {
				
				opcaoFimJogo = true;
				salvarProgresso = true;		
			}
		}	
	}
	
	// Metodo responsavel por salvar o progresso(qtd votos obtidos, itens, etc...)
	public void SalvarProgresso() {
		
		if (salvarProgresso == true) {
			salvarProgresso = false;
			
			jogador.setTotal_voto(progresso.getQtd_votos_obtido());
			
			// ACESSA O METODO QUE ACESSA O BANCO E SALVA OS DADOS
			progresso.InserirProgressoBanco();
			jogador.SalvarVotoTotalBanco();
			
			JogadorDAO jogadorDAO = new JogadorDAO(Main.conexaoBanco);
			//System.out.println("Total de votos "+jogadorDAO.Select());
			//System.out.println("Progresso SALVO!");		
		}	
	}

	public void BotaoDebate() {

		botaoFrase1 = new JButton(Main.listaFrasesAleatorias.get(0).getTextobr());
		botaoFrase2 = new JButton(Main.listaFrasesAleatorias.get(1).getTextobr());
		botaoFrase3 = new JButton(Main.listaFrasesAleatorias.get(2).getTextobr());
		botaoFrase4 = new JButton(Main.listaFrasesAleatorias.get(3).getTextobr());
		
		botaoFrase1.setFont(new Font("Arial", Font.BOLD, 15));
		botaoFrase2.setFont(new Font("Arial", Font.BOLD, 15));
		botaoFrase3.setFont(new Font("Arial", Font.BOLD, 15));
		botaoFrase4.setFont(new Font("Arial", Font.BOLD, 15));

		botaoFrase1.setBounds(0, GerenciarTelas.telaAltura - 100, GerenciarTelas.telaLargura / 2, 50);
		GerenciarTelas.telaJogoPainel.add(botaoFrase1);

		botaoFrase2.setBounds(GerenciarTelas.telaLargura - (GerenciarTelas.telaLargura / 2), GerenciarTelas.telaAltura - 100, GerenciarTelas.telaLargura / 2, 50);
		GerenciarTelas.telaJogoPainel.add(botaoFrase2);

		botaoFrase3.setBounds(0, GerenciarTelas.telaAltura - 150, GerenciarTelas.telaLargura / 2, 50);
		GerenciarTelas.telaJogoPainel.add(botaoFrase3);

		botaoFrase4.setBounds(GerenciarTelas.telaLargura - (GerenciarTelas.telaLargura / 2), GerenciarTelas.telaAltura - 150, GerenciarTelas.telaLargura / 2, 50);
		GerenciarTelas.telaJogoPainel.add(botaoFrase4);

		botaoFrase1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numeroBotao = 0;
				BotaoDebateClicado(botaoFrase1, botaoFrase2, botaoFrase3, botaoFrase4, numeroBotao);
			}
		});

		botaoFrase2.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numeroBotao = 1;
				BotaoDebateClicado(botaoFrase1, botaoFrase2, botaoFrase3, botaoFrase4, numeroBotao);
			}
		});

		botaoFrase3.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numeroBotao = 2;
				BotaoDebateClicado(botaoFrase1, botaoFrase2, botaoFrase3, botaoFrase4, numeroBotao);
			}
		});

		botaoFrase4.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				numeroBotao = 3;
				BotaoDebateClicado(botaoFrase1, botaoFrase2, botaoFrase3, botaoFrase4, numeroBotao);
			}
		});
	}

	public void BotaoDebateClicado(JButton botao_1, JButton botao_2, JButton botao_3, JButton botao_4, int numeroBotao) {

		Main.somMouseClique.IniciarSom();
		
		// REMOVE A VIDA DO ADVERSARIO COM BASE NO DANO DA FRASE
		adversarioPontuacao = (adversarioPontuacao - Main.listaFrasesAleatorias.get(numeroBotao).getDano());
		fraseEscolhidaJogador = Main.listaFrasesAleatorias.get(numeroBotao).getTextobr();
		
		jogadorEscolheuFrase = true;

		// SERA UTILIZADO PELA THREAD DO JOGO PARA INICIAR UM NOVO SORTEIO DE FRASES
		TelaJogo.iniciarSorteioFrase = true;
		TelaJogo.terminouSorteioFrase = false;

		// ESPERA A THREAD DO JOGO TERMINAR DE ESCOLHER AS NOVAS FRASES
		while (finalizarDebate == false && terminouSorteioFrase == false) {
			try {
				//System.out.println("terminouSorteioFrase= "+terminouSorteioFrase);
				Thread.sleep(200);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// COLOCA AS NOVAS FRASES NOS BOTOES
		botao_1.setText(Main.listaFrasesAleatorias.get(0).getTextobr());
		botao_2.setText(Main.listaFrasesAleatorias.get(1).getTextobr());
		botao_3.setText(Main.listaFrasesAleatorias.get(2).getTextobr());
		botao_4.setText(Main.listaFrasesAleatorias.get(3).getTextobr());
		
		//turnoJogador = false;
	}
	
	public void RemoverBotaoDebate() {	
		try {	
			GerenciarTelas.telaJogoPainel.remove(botaoFrase1);
			GerenciarTelas.telaJogoPainel.remove(botaoFrase2);
			GerenciarTelas.telaJogoPainel.remove(botaoFrase3);
			GerenciarTelas.telaJogoPainel.remove(botaoFrase4);
			
		} catch (Exception e) {
			System.out.println("Não foi possivel remover os Botoes!");			
		}		
	}

	public void BotaoMenuOpcao() {

		botaoJogarNovamente = new JButton("  Jogar Novamente");
		botaoTelaInicial = new JButton("  Tela Inicial");
		botaoItensObtido = new JButton("  Itens Obtidos");				
		
		botaoJogarNovamente.setFont(Main.fontePixel);
		botaoTelaInicial.setFont(Main.fontePixel);
		botaoItensObtido.setFont(Main.fontePixel);
		
		botaoJogarNovamente.setIcon(new ImageIcon("UtilitarioJogo/imagem/refresh.png"));
		botaoTelaInicial.setIcon(new ImageIcon("UtilitarioJogo/imagem/door.png"));
		botaoItensObtido.setIcon(new ImageIcon("UtilitarioJogo/imagem/pie-chart.png"));

		botaoJogarNovamente.setBounds(400, 350, 240, 50);
		GerenciarTelas.telaJogoPainel.add(botaoJogarNovamente);

		botaoTelaInicial.setBounds(400, 400, 240, 50);
		GerenciarTelas.telaJogoPainel.add(botaoTelaInicial);
		
		botaoItensObtido.setBounds(400, 450, 240, 50);
		GerenciarTelas.telaJogoPainel.add(botaoItensObtido);

		botaoJogarNovamente.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Main.somMouseClique.IniciarSom();
				
				try {
					Main.somPolicia.PararSom();
				} catch (Exception e2) {
				}
				
				Main.musicaJogo.PararSom();
				Main.musicaJogo.IniciarSom();

				System.out.println("Recomecar o jogo");

				opcaoFimJogo = false;
				iniciarJogo = true;

				velocidadeGlobal = 15;

				GerenciarTelas.telaJogoPainel.setVisible(false);
				GerenciarTelas.Jogar();

			}
		});

		botaoTelaInicial.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Main.somMouseClique.IniciarSom();

				try {
					Main.somPolicia.PararSom();
				} catch (Exception e2) {
				}
				Main.musicaJogo.PararSom();
				Main.musicaMenu.IniciarSom();

				System.out.println("Tela inicial");

				opcaoFimJogo = false;
				iniciarJogo = true;

				GerenciarTelas.telaJogoPainel.setVisible(false);
				GerenciarTelas.TelaInicial();

			}
		});
		
		botaoItensObtido.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				
				if ((progresso.getQtd_item_voto_obtido() > 0) || (progresso.getQtd_item_invencivel_obtido() > 0) || (progresso.getQtd_item_ima_voto_obtido() > 0)) {
					
					DefaultPieDataset dadosGraficoPizza = new DefaultPieDataset();
					dadosGraficoPizza.setValue("Votos", progresso.getQtd_item_voto_obtido());
					dadosGraficoPizza.setValue("Invencivel", progresso.getQtd_item_invencivel_obtido());
					dadosGraficoPizza.setValue("Ima de votos", progresso.getQtd_item_ima_voto_obtido());
							 
					JFreeChart grafico = ChartFactory.createPieChart3D("Tipos de Itens mais obtidos", dadosGraficoPizza, true, true, false);
					
					ChartFrame frame = new ChartFrame("Grafico de Vendas", grafico);
					frame.setSize(1200, 500);
					frame.setLocationRelativeTo(null);
					frame.setVisible(true);
					
				}else {
					JOptionPane.showMessageDialog(null, "Você não obteve nenhum item!", "Não é possivel Abrir!", 1);	
				}		
			}
		});
	}

}
