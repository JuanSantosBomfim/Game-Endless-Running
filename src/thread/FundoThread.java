package thread;

import model.Fundo;
import view.TelaJogo;

public class FundoThread extends Thread {

	private Fundo fundo;

	// CONTRUTOR USADO PARA RECEBER O FUNDO
	public FundoThread(Fundo fundo) {
		this.fundo = fundo;
	}

	@Override
	public void run() {

		while (TelaJogo.personagemVivo == true && TelaJogo.jogando == true) {
			try {

				if (TelaJogo.debatendo != true) {
					
					// CALCULA O MOVIMENTO DO FUNDO RECEBIDO
					fundo.CalcularMovimento();
				}

				Thread.sleep(20);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
