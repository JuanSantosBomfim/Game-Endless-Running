package model;

//Classe modelo
public class Frase {

	private int id_frase;
	private String textobr;
	private String textoen;
	private int dano;

	public int getId_frase() {
		return id_frase;
	}

	public void setId_frase(int id_frase) {
		this.id_frase = id_frase;
	}

	public String getTextobr() {
		return textobr;
	}

	public void setTextobr(String textobr) {
		this.textobr = textobr;
	}

	public String getTextoen() {
		return textoen;
	}

	public void setTextoen(String textoen) {
		this.textoen = textoen;
	}

	public int getDano() {
		return dano;
	}

	public void setDano(int dano) {
		this.dano = dano;
	}

}
