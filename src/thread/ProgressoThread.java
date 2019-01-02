package thread;

import model.Progresso;
import view.TelaJogo;

// Classe responsavel por calcular a distancia percorrida
public class ProgressoThread extends Thread{
	
	private Progresso progresso;
	public static int delay = 1000;

	// RECEBE O OBJETO DO TIPO PROGRESSO
	public ProgressoThread(Progresso progresso) {
		this.progresso = progresso;
	}

	@Override
	public void run() {

		while (TelaJogo.personagemVivo == true && TelaJogo.jogando == true) {
			try {

				if (TelaJogo.debatendo != true) {
					
					// CALCULA A DISTANCIA PERCORRIDA NO PROGRESSO
					progresso.CalcularDistanciaPercorrida();		
				}
				
				Thread.sleep(delay);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
