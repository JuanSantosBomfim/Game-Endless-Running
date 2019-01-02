package controller;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JPanel;
import javax.swing.KeyStroke;

import objetofamily.Jogador;
import thread.ProgressoThread;
import view.GerenciarTelas;
import view.TelaJogo;


// Essa classe é responsavel por definir as teclas que podem ser utilizadas durante o jogo e suas ações.
public class PersonagemControle {

	private JPanel fase;
	private Jogador personagem;

	public PersonagemControle(TelaJogo fase, Jogador personagem) {

		this.fase = fase;
		this.personagem = personagem;

		// DEFINE AS TECLAS, E CRIA UMA TAG PARA INDENTIFICARMOS QUAL TECLA FOI PRESSIONADA
		fase.getInputMap().put(KeyStroke.getKeyStroke("UP"), "moverCima");
		fase.getInputMap().put(KeyStroke.getKeyStroke("DOWN"), "moverBaixo");
		fase.getInputMap().put(KeyStroke.getKeyStroke("LEFT"), "moverEsquerda");
		fase.getInputMap().put(KeyStroke.getKeyStroke("RIGHT"), "moverDireita");
		
		fase.getInputMap().put(KeyStroke.getKeyStroke("1"), "irDebate");
		fase.getInputMap().put(KeyStroke.getKeyStroke("2"), "terminarDebate");

		// DEFINE AS TECLAS, E CRIA UMA TAG PARA INDENTIFICARMOS QUAL TECLA FOI SOLTA
		fase.getInputMap().put(KeyStroke.getKeyStroke("released DOWN"), "pararMoverBaixo");
		fase.getInputMap().put(KeyStroke.getKeyStroke("released LEFT"), "pararMoverEsquerda");
		fase.getInputMap().put(KeyStroke.getKeyStroke("released RIGHT"), "pararMoverDireita");
	}

	public void AcaoMovimentarPersonagem() {

		// ******************MOVIMENTAR AO PRESSIONAR A TECLA******************
		Action acaoMovimentarCima = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if ((personagem.getPosicao()[1] == (GerenciarTelas.telaAltura - 125)) || personagem.isPersonagemEmCimaPlataforma()) {

					personagem.setPulo(true);
				}
			}
		};

		Action acaoMovimentarBaixo = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				personagem.setMovimentarVetor(0, 3);
			}
		};

		Action acaoMovimentarEsquerda = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				personagem.setMovimentarVetor(-3, 0);
			}
		};

		Action acaoMovimentarDireita = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				personagem.setMovimentarVetor(3, 0);
			}
		};

		Action acaoIrDebate = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("ATIVA DEBATE");
				ProgressoThread.delay = 1;
			}
		};
		
		Action acaoTerminarDebate = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				System.out.println("TERMINAR DEBATE");
				TelaJogo.jogadorPontuacao = 0;
			}
		};
		
		// ******************PARAR DE MOVIMENTAR AO SOLTAR ATECLA******************
		Action acaoPararMovimentarBaixo = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				personagem.setMovimentarVetor(0, 0);
			}
		};

		Action acaoPararMovimentarEsquerda = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				personagem.setMovimentarVetor(0, 0);
			}
		};

		Action acaoPararMovimentarDireita = new AbstractAction() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				personagem.setMovimentarVetor(0, 0);
			}
		};

		// ULTILIZANDO A TAG PODEMOS SABER QUAL TECLA FOI PRESSIONADA, E ASSIM CHAMAR UM METODO
		fase.getActionMap().put("moverCima", acaoMovimentarCima);
		fase.getActionMap().put("moverBaixo", acaoMovimentarBaixo);
		fase.getActionMap().put("moverEsquerda", acaoMovimentarEsquerda);
		fase.getActionMap().put("moverDireita", acaoMovimentarDireita);
		
		fase.getActionMap().put("irDebate", acaoIrDebate);
		fase.getActionMap().put("terminarDebate", acaoTerminarDebate);
		
		// ULTILIZANDO A TAG PODEMOS SABER QUAL TECLA FOI SOLTA, E ASSIM CHAMAR UM METODO
		fase.getActionMap().put("pararMoverBaixo", acaoPararMovimentarBaixo);
		fase.getActionMap().put("pararMoverEsquerda", acaoPararMovimentarEsquerda);
		fase.getActionMap().put("pararMoverDireita", acaoPararMovimentarDireita);

	}

}
