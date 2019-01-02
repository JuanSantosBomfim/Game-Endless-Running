package thread;

import controller.PersonagemControle;
import objetofamily.Jogador;
import view.TelaJogo;

// Classe responsavel por calcular o movimento e gravidade do jogador
public class JogadorThread extends Thread {

	private PersonagemControle personagemControle;
	private Jogador personagem;

	public JogadorThread(TelaJogo fase, Jogador personagem) {
		this.personagem = personagem;
		this.personagemControle = new PersonagemControle(fase, personagem);

		// DEFINE TODAS AS TECLAS UTILIZADAS PELO JOGADOR, E SUAS AÇÕES
		personagemControle.AcaoMovimentarPersonagem();
	}

	@Override
	public void run() {

		// ENQUANTO O JOGADOR ESTIVER VIVO
		while (TelaJogo.personagemVivo == true && TelaJogo.jogando == true) {
			try {
				
				if (TelaJogo.debatendo != true) {

					personagem.CalcularMovimento();
					personagem.CalcularGravidade();
					personagem.CalcularRetangulo();
					personagem.CalcularFrame();
				}

				Thread.sleep(5);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// APOS A MORTE DO JOGADOR
		if (TelaJogo.personagemVivo == false) {
	
			personagem.AnimacaoPersonagemCaindo();

			while ((personagem.getFrameAtual() / 32) != 8) {
				try {

					personagem.CalcularGravidade();
					personagem.CalcularRetangulo();
					personagem.CalcularFrame();

					Thread.sleep(5);

				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}

		}

	}

}
