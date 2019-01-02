package thread;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import main.Main;
import model.Frase;
import model.Progresso;
import sqlitedb.FraseDAO;
import view.TelaJogo;

// Classe responsavel por ESCOLHER ALEATORIAMENTE AS FRASES PARA O DEBATE, verificar se quem foi o vencedor do debate, 
public class DebateThread extends Thread {

	// ATRIBUTOS
	private Progresso progresso;
	
	private double tempoMostrarFraseDebate = 3;
	private double tempoMostrarVencedorDebate = 3;

	// CONSTRUTOR
	public DebateThread(Progresso progresso) {
		this.progresso = progresso;
	}

	public void run() {

		while (TelaJogo.debatendo == true) {
			try {

				GerenciaTurnoDebate();
				CalcularTempoMostrarFraseDebate();
				CalcularTempoMostrarVencedorDebate();
				
				Thread.sleep(20);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void GerenciaTurnoDebate() {

		// VERIFICA SE É O TURNO DO JOGADOR
		if(TelaJogo.turnoJogador == 1) {
			
			// VERIFICA SE O SORTEIO DE FRASES DEVE SER INICIADO
			if (TelaJogo.iniciarSorteioFrase == true) {

				// ACESSA O BANCO DE DADOS E ESCOLHE ALEATORIAMENTE AS FRASES PARA O DEBATE
				FraseDAO fraseDAO = new FraseDAO(Main.conexaoBanco);
				Main.listaFrasesAleatorias = fraseDAO.EscolheFraseAleatoria();
				
				// RESETA AS VARIAVEIS
				TelaJogo.iniciarSorteioFrase = false;
				TelaJogo.terminouSorteioFrase = true;

				// É UTILIZADO NA TELAJOGO PARA HABILITAR OS BOTOES COM AS NOVAS FRASES DO BANCO
				if (TelaJogo.mostrarBotaoDebate == 0) {
					TelaJogo.mostrarBotaoDebate = 1;
				}
			}
			
		}else if(TelaJogo.turnoJogador == 2) {
			// TURNO DO ADVERSARIO
			
			// ACESSA O BANCO DE DADOS E ESCOLHE ALEATORIAMENTE UMA FRASE
			ArrayList<Frase> fraseAdversario = new ArrayList<>();
			FraseDAO fraseDAO = new FraseDAO(Main.conexaoBanco);
			fraseAdversario = fraseDAO.EscolheFraseAleatoria();
			
			// DIMINUI A VIDA DO JOGADOR DE ACORDO COM O DANO DA FRASE ESCOLHIDA PELO ADVERSARIO
			TelaJogo.jogadorPontuacao = (TelaJogo.jogadorPontuacao - fraseAdversario.get(0).getDano());	
			TelaJogo.fraseEscolhidaAdversario = fraseAdversario.get(0).getTextobr();
			
			//System.out.println("ADVERSARIO = "+fraseAdversario.get(0).getTextobr());				
			
			// INICIA O TURNO DO JOGADOR
			//TelaJogo.turnoJogador = true;
			
			TelaJogo.adversarioEscolheuFrase = true;
			TelaJogo.turnoJogador = 3;
		}
	}
	
	public void VerificaGanhadorDebate() {
		
		// VERIFICA SE O JOGADOR OU O ADVERSARIO PERDEU O DEBATE
		if (TelaJogo.jogadorPontuacao <= 0) {

			// RESETA AS VARIAVEIS USADAS DURANTE O DEBATE		
			TelaJogo.iniciarSorteioFrase = false;
			TelaJogo.terminouSorteioFrase = false;
			
			TelaJogo.mostrarVencedorDebate = true;
			
			System.out.println("JOGADOR PERDEU!!!");
			
			TelaJogo.jogadorEscolheuFrase = false;
			TelaJogo.adversarioEscolheuFrase = false;
			TelaJogo.turnoJogador = 0;
			
			// DEFINE O VENCEDOR DO DEBATE
			TelaJogo.vencedorDebate = "adversario";

		// VERIFICA SE O JOGADOR OU O ADVERSARIO PERDEU O DEBATE
		} else if (TelaJogo.adversarioPontuacao <= 0) {

			// RESETA AS VARIAVEIS USADAS DURANTE O DEBATE
			TelaJogo.iniciarSorteioFrase = false;
			TelaJogo.terminouSorteioFrase = false;

			TelaJogo.mostrarVencedorDebate = true;			
			
			System.out.println("JOGADOR GANHOU!!!");
			
			TelaJogo.jogadorEscolheuFrase = false;
			TelaJogo.adversarioEscolheuFrase = false;
			TelaJogo.turnoJogador = 0;
			
			// DEFINE O VENCEDOR DO DEBATE
			TelaJogo.vencedorDebate = "jogador";
			
			// GERA UMA QTD DE VOTOS ALEATORIO
			int votosBonus = ThreadLocalRandom.current().nextInt(50, 200);
			
			progresso.setQtd_adversario_derrotado(progresso.getQtd_adversario_derrotado() + 1);
			progresso.setQtd_votos_obtido(progresso.getQtd_votos_obtido() + (votosBonus * progresso.getQtd_adversario_derrotado()));
			
			// ALTERA A PROBABILIDADE DOS ITENS APARECEREM
			JogoThread.probabilidade_item = 10 + (2 * progresso.getQtd_adversario_derrotado());
			JogoThread.probabilidade_obstaculo = 10 + (2 * progresso.getQtd_adversario_derrotado());
		}		
	}

	public void CalcularTempoMostrarFraseDebate() {
		
		if ((TelaJogo.jogadorEscolheuFrase == true) || (TelaJogo.adversarioEscolheuFrase == true)) {		
			
			tempoMostrarFraseDebate = (tempoMostrarFraseDebate - 0.02);
			
			if (tempoMostrarFraseDebate <= 0) {
				
				if(TelaJogo.jogadorEscolheuFrase == true) {							
					
					TelaJogo.turnoJogador = 2;
				}
				
				if(TelaJogo.adversarioEscolheuFrase == true) {
					
					TelaJogo.turnoJogador = 1;
					TelaJogo.recarregarBotaoDebate = 1;
				}
					
				TelaJogo.jogadorEscolheuFrase = false;
				TelaJogo.adversarioEscolheuFrase = false;
				
				tempoMostrarFraseDebate = 3;
				
				VerificaGanhadorDebate();
			}
		}
	}
	
	public void CalcularTempoMostrarVencedorDebate() {
		
		if (TelaJogo.mostrarVencedorDebate == true) {		
			
			tempoMostrarVencedorDebate = (tempoMostrarVencedorDebate - 0.02);
			
			if (tempoMostrarVencedorDebate <= 0) {	
							
				TelaJogo.debatendo = false;
				TelaJogo.finalizarDebate = true;
				
				tempoMostrarVencedorDebate = 5;
			}
		}
	}
}
