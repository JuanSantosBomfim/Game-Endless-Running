package thread;

import objetofamily.Item;
import view.TelaJogo;

public class ItemThread extends Thread{
	
	private Item item;
	
	public ItemThread(Item item) {
		this.item = item;
	}
	
	public void run() {

		Item();
	}
	
	public void Item() {

		// ENQUANTO O JOGADOR ESTIVER VIVO
		while (TelaJogo.personagemVivo == true && TelaJogo.jogando == true) {

			try {		
				// VERIFICA SE A MELHORIA DE IMA DE VOTOS ESTA ATIVADA
				if (JogoThread.personagemComIma == true && item.getTipo().equals("voto")) {
					
					item.CalcularMovimentoSeguirJogador(TelaJogo.jogador);					
					
				}else {
					item.CalcularMovimento();
					//item.CalcularGravidade();				
				}			
				
				item.CalcularRetangulo();
				
				Thread.sleep(20);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
