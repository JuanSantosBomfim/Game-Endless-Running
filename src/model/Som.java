package model;

import thread.SomThread;

//Classe modelo
public class Som {

	private String nomeSom;
	private boolean repetirSom;

	private SomThread musicaTelaInicial;

	public Som(String nomeSom, boolean repetirSom) {

		this.nomeSom = nomeSom;
		this.repetirSom = repetirSom;
	}

	public void IniciarSom() {

		musicaTelaInicial = new SomThread(nomeSom, repetirSom);
		musicaTelaInicial.start();
	}

	public void PararSom() {

		musicaTelaInicial.setPararSom(true);
	}

}
