package thread;

import view.GerenciarTelas;

public class GerenciarTelasThread extends Thread{
	
	private int posicao_x;
	
	public GerenciarTelasThread(int posicao_x) {		
		this.posicao_x = posicao_x;		
	}
	
	public void run() {
		
		while(GerenciarTelas.personagemCorrendo) {
			
			// DEFINE A POSICAO DO GIF DO PERSONAGEM NA TELA
			GerenciarTelas.imgPersonagemCorrendo.setBounds(posicao_x, 400, 600, 300);
			
			// MOVE O GIF PERSONAGEM NA TELA, E VERIFICA SE O PERSONAGEM SAIU DA TELA, E RESETA A POSICAO
			if(posicao_x >= GerenciarTelas.telaLargura) {
				posicao_x = -60;
				
			}else {
				posicao_x = posicao_x + 13;
			}
					
			try {
				Thread.sleep(160);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}		
		}	
	}
}
