package model;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;

//Classe modelo, com metodo para desenhar os elementos da interface durante a corrida
public class InterfaceJogo {

	private int tamanho_x, tamanho_y;
	private int posicao_x, posicao_y;

	private Image imagem;
	private boolean visivel = true;

	public InterfaceJogo(String nomeImagem, int tamanho_x, int tamanho_y, int posicao_x, int posicao_y) {

		this.tamanho_x = tamanho_x;
		this.tamanho_y = tamanho_y;

		this.posicao_x = posicao_x;
		this.posicao_y = posicao_y;
		
		ImageIcon img_caminho = new ImageIcon("UtilitarioJogo/imagem/" + nomeImagem);
		imagem = img_caminho.getImage();
	}

	public void DesenharInterface(Graphics2D graficos2d) {
		graficos2d.drawImage(getImagem(), getPosicao_x(), getPosicao_y(), getTamanho_x(), getTamanho_y(), null);
	}
	
	public void DesenharInterface(Graphics2D graficos2d, int distancia_percorrida) {
		graficos2d.drawImage(getImagem(), distancia_percorrida, getPosicao_y(), getTamanho_x(), getTamanho_y(), null);
	}


	public int getTamanho_x() {
		return tamanho_x;
	}

	public void setTamanho_x(int tamanho_x) {
		this.tamanho_x = tamanho_x;
	}

	public int getTamanho_y() {
		return tamanho_y;
	}

	public void setTamanho_y(int tamanho_y) {
		this.tamanho_y = tamanho_y;
	}

	public int getPosicao_x() {
		return posicao_x;
	}

	public void setPosicao_x(int posicao_x) {
		this.posicao_x = posicao_x;
	}

	public int getPosicao_y() {
		return posicao_y;
	}

	public void setPosicao_y(int posicao_y) {
		this.posicao_y = posicao_y;
	}

	public Image getImagem() {
		return imagem;
	}

	public void setImagem(String nomeImagem) {
		
		ImageIcon img_caminho = new ImageIcon("UtilitarioJogo/imagem/" + nomeImagem);
		imagem = img_caminho.getImage();
	}

	public boolean isVisivel() {
		return visivel;
	}

	public void setVisivel(boolean visivel) {
		this.visivel = visivel;
	}

}
