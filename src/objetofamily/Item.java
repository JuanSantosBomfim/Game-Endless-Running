package objetofamily;

import thread.ItemThread;
import thread.ObjetoThread;
import view.GerenciarTelas;

public class Item extends Objeto {

	private boolean pegou = false;
	private int multiplicador = 1;
	private int valor_voto = 1;
	private String tipo;

	public Item(String tipoObjeto, String tipoItem, String nomeImagem, int tamanho_x, int tamanho_y, int posicao_x,
			int posicao_y, int movimentar_x) {
		super(tipoObjeto, nomeImagem, tamanho_x, tamanho_y, posicao_x, posicao_y, movimentar_x);

		this.tipo = tipoItem;
		
		// CRIA UMA THREAD PARA O OBJETO
		ItemThread itemThread = new ItemThread(this);
		itemThread.start();
	}
	
	public void CalcularMovimento() {

		posicao[0] += movimentarVetor.getX();
		posicao[1] += movimentarVetor.getY();

		// VERIFICA SE O OBJETO SAIU DA TELA
		if (posicao[0]< -(GerenciarTelas.telaLargura + tamanho[0])) {
			
			visivel = false;
			pegou = false;	
		}
	}

	public boolean isPegou() {
		return pegou;
	}

	public void setPegou(boolean pegou) {
		this.pegou = pegou;
	}

	public int getMultiplica() {
		return multiplicador;
	}

	public void setMultiplica(int multiplica) {
		this.multiplicador = multiplica;
	}

	public int getVoto() {
		return valor_voto * multiplicador;
	}

	public void setVoto(int voto) {
		this.valor_voto = voto;
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
}
