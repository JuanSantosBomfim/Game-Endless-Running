package objetofamily;

import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.vecmath.Vector2d;

import thread.JogoThread;
import thread.ObjetoThread;
import view.GerenciarTelas;

//Classe importante, é responsavel por definir os tamanhos, posicoes, e as imagens de todos objetos, alem do calculos de movimento, retangulo e gravidade 
public class Objeto {

	protected String tipoObjeto;

	protected int tamanho[] = new int[2];
	protected int retangulo[] = new int[2];

	protected int posicao[] = new int[2];
	protected Vector2d movimentarVetor = new Vector2d();
	

	protected Image imagem;
	protected boolean visivel = true;

	public Objeto(String tipoObjeto, String nomeImagem, int tamanho_x, int tamanho_y, int posicao_x, int posicao_y,
			int movimentar_x) {

		setImagem(nomeImagem);

		this.tipoObjeto = tipoObjeto;

		this.tamanho[0] = tamanho_x;
		this.tamanho[1] = tamanho_y;

		this.posicao[0] = posicao_x;
		this.posicao[1] = posicao_y;

		movimentarVetor.set(movimentar_x, 0);
	
		if (tipoObjeto.equals("plataforma") || tipoObjeto.equals("obstaculo")) {

			// CRIA UMA THREAD PARA O OBJETO
			ObjetoThread objetoThread = new ObjetoThread(this);
			objetoThread.start();
		}
	}

	public Objeto() {
		
	}

	public void CalcularRetangulo() {

		retangulo[0] = tamanho[0] + posicao[0];
		retangulo[1] = tamanho[1] + posicao[1];
	}

	public void CalcularMovimento() {

		posicao[0] += movimentarVetor.getX();
		posicao[1] += movimentarVetor.getY();

		// VERIFICA SE O OBJETO SAIU DA TELA
		if (posicao[0] <= - 500 || (posicao[0] >= (GerenciarTelas.telaLargura + 500))) {	
			visivel = false;
			//System.out.println(" RECARREGA OBJETO ");
		}
	}
	
	public void CalcularMovimentoSeguirJogador(Jogador jogador) {

		if (posicao[0] >= jogador.posicao[0]) {		
			posicao[0] = posicao[0] - 10;
		}
		if (posicao[0] <= jogador.posicao[0]) {	
			posicao[0] = posicao[0] + 10;
		}
		
		if (posicao[1] >= jogador.posicao[1]) {		
			posicao[1] = posicao[1] - 10;
		}
		if (posicao[1] <= jogador.posicao[1]) {	
			posicao[1] = posicao[1] + 10;
		}		
	}

	public void CalcularGravidade() {

		if (posicao[1] >= 0) {
			posicao[1] += 3;
		}

		// IMPEDE QUE O OBJETO CAIA ATE SUMIR DA TELA
		if (posicao[1] < 0) {
			posicao[1] = 0;
		}
		if (posicao[1] > (GerenciarTelas.telaAltura - (tamanho[1] + (tamanho[1] / 2)))) {
			posicao[1] = GerenciarTelas.telaAltura - (tamanho[1] + (tamanho[1] / 2));
		}
	}

	public void DesenharObjeto(Graphics2D graficos2d) {

		if (visivel == true) {
			graficos2d.drawImage(imagem, posicao[0], posicao[1], tamanho[0], tamanho[1], null);
		}
	}

	public Rectangle getRectangle() {
		return new Rectangle(posicao[0], posicao[1], tamanho[0], tamanho[1]);
	}

	public String getTipoObjeto() {
		return tipoObjeto;
	}

	public void setTipoObjeto(String tipoObjeto) {
		this.tipoObjeto = tipoObjeto;
	}

	public int[] getTamanho() {
		return tamanho;
	}

	public void setTamanho(int x, int y) {
		this.tamanho[0] = x;
		this.tamanho[1] = y;
	}

	public int[] getRetangulo() {
		return retangulo;
	}

	public void setRetangulo(int x, int y) {
		this.retangulo[0] = x;
		this.retangulo[1] = y;
	}

	public int[] getPosicao() {
		return posicao;
	}

	public void setPosicao(int x, int y) {
		this.posicao[0] = x;
		this.posicao[1] = y;
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

	public Vector2d getMovimentarVetor() {
		return movimentarVetor;
	}

	public void setMovimentarVetor(int x, int y) {
		this.movimentarVetor.set(x, y);
	}
	
	

}
