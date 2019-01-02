package thread;

import java.io.File;
import jaco.mp3.player.MP3Player;

// Classe responsavel por tocar as musicas, e sons
public class SomThread extends Thread {

	private String nomeSom;
	private boolean repetirSom;
	private MP3Player lista_som;
	private boolean pararSom;

	public SomThread(String nomeSom, boolean repetirSom) {
		this.nomeSom = nomeSom;
		this.repetirSom = repetirSom;
	}

	@Override
	public void run() {

		TocarSom(nomeSom, repetirSom);

		while (pararSom == false) {

			try {
				// VERIFICA SE A MUSICA TERMINOU DE TOCAR
				if (lista_som.isStopped()) {
					
					// TERMINA O WHILE
					pararSom = true;

				} else {
					//System.out.println("Som Tocando!!!");
				}

				Thread.sleep(500);

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		lista_som.stop();
	}

	private void TocarSom(String nomeSom, boolean repetirSom) {

		// CARREGA A MUSICA E TOCA
		lista_som = new MP3Player();
		lista_som.addToPlayList(new File("UtilitarioJogo/som/" + nomeSom + ".mp3"));
		lista_som.setRepeat(repetirSom);
		lista_som.play();
	}

	public boolean isPararSom() {
		return pararSom;
	}

	public void setPararSom(boolean pararSom) {
		this.pararSom = pararSom;
	}
}
