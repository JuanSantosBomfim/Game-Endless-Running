package model;

import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.ImageIcon;
import javax.vecmath.Vector2d;

import thread.FundoThread;

// Classe modelo, com metodo para calcular o movimento do fundo, e desenhar o fundo
public class Fundo {

	private String nomeImagem;
	private Image imagem_fundo;
	
	private int tamanho[] = new int[2];
	
	private Vector2d fundo1_posicao_vetor = new Vector2d();
	private Vector2d fundo2_posicao_vetor = new Vector2d();
	
	private Vector2d movimentarVetor = new Vector2d();

	public Fundo(String nomeImagem, int fundo1_posicao_x, int fundo1_posicao_y, int fundo2_posicao_x,
			int fundo2_posicao_y, int movimentar_x, int movimentar_y, int tamanho_x, int tamanho_y) {

		fundo1_posicao_vetor.set(fundo1_posicao_x, fundo1_posicao_y);
		fundo2_posicao_vetor.set(fundo2_posicao_x, fundo2_posicao_y);

		movimentarVetor.set(movimentar_x, movimentar_y);

		this.tamanho[0] = tamanho_x;
		this.tamanho[1] = tamanho_y;

		ImageIcon caminho_img = new ImageIcon("UtilitarioJogo/imagem/" + nomeImagem);
		imagem_fundo = caminho_img.getImage();

		FundoThread fundoThread = new FundoThread(this);
		fundoThread.start();
		
		fundo1_posicao_vetor.add(movimentarVetor);
	}

	public void CalcularMovimento() {
		
		// 
		fundo1_posicao_vetor.sub(movimentarVetor);
		fundo2_posicao_vetor.sub(movimentarVetor);
		
		//fundo1_posicao_vetor.set(fundo1_posicao_vetor.getX() - movimentarVetor.getX(), fundo1_posicao_vetor.getY() - movimentarVetor.getY());
		//fundo2_posicao_vetor.set(fundo2_posicao_vetor.getX() - movimentarVetor.getX(), fundo2_posicao_vetor.getY() - movimentarVetor.getY());


		if (fundo1_posicao_vetor.getX() <= -tamanho[0]) {
			fundo1_posicao_vetor.set(tamanho[0], 0);
		}
		if (fundo2_posicao_vetor.getX() <= -tamanho[0]) {
			fundo2_posicao_vetor.set(tamanho[0], 0);
		}
	}

	public void Desenhar(Graphics2D graficos2d) {

		graficos2d.drawImage(imagem_fundo, (int)fundo1_posicao_vetor.getX(), (int)fundo1_posicao_vetor.getY() , tamanho[0] + 100, tamanho[1], null);
		graficos2d.drawImage(imagem_fundo, (int)fundo2_posicao_vetor.getX(), (int)fundo2_posicao_vetor.getY(), tamanho[0]+ 100, tamanho[1], null);
	}

	public String getNomeImagem() {
		return nomeImagem;
	}

	public void setNomeImagem(String nomeImagem) {
		this.nomeImagem = nomeImagem;
	}

	public Image getImagem_fundo() {
		return imagem_fundo;
	}

	public void setImagem_fundo(Image imagem_fundo) {
		this.imagem_fundo = imagem_fundo;
	}

	public int[] getTamanho() {
		return tamanho;
	}

	public void setTamanho(int[] tamanho) {
		this.tamanho = tamanho;
	}

}
