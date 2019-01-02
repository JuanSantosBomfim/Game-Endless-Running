package thread;

import objetofamily.Objeto;
import view.TelaJogo;

//Classe responsavel por calcular o movimento e objeto
public class ObjetoThread extends Thread {

	private Objeto objeto;

	// O CONSTRUTOR RECEBE O OBJETO QUE PODE SER UM ITEM, PLATAFORMA OU OBSTACULO
	public ObjetoThread(Objeto objeto) {
		this.objeto = objeto;
	}

	public void run() {

		// ENQUANTO O JOGADOR ESTIVER VIVO
		while (TelaJogo.personagemVivo == true && TelaJogo.jogando == true) {

			try {

				objeto.CalcularMovimento();
				objeto.CalcularRetangulo();		

				Thread.sleep(20);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
