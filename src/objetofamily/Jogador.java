package objetofamily;

import java.awt.Graphics2D;

import main.Main;
import model.Progresso;
import sqlitedb.JogadorDAO;
import thread.JogadorThread;
import view.GerenciarTelas;
import view.TelaJogo;

public class Jogador extends Objeto {

	private boolean personagemVivo = true;
	private boolean personagemEmCimaPlataforma = false;
	private boolean personagemEmCimaObstaculo = false;
	// private boolean personagemDescendoPlataforma = false;
	private boolean pulo = false;

	private int frameAtual, largura_frame, altura_frame;
	private int inicioFrameHorizontal, inicioFrameVertical, fimFrameHorizontal, fimFrameVertical;
	private Progresso progresso;
	private int id_jogador;
	private int total_voto;

	public Jogador(String tipoObjeto, String nomeImagem, int tamanho_x, int tamanho_y, int posicao_x, int posicao_y,
			int movimentar_x, TelaJogo fase, Progresso progresso) {
		super(tipoObjeto, nomeImagem, tamanho_x, tamanho_y, posicao_x, posicao_y, movimentar_x);

		// INICIA A THREAD
		JogadorThread personagemThread = new JogadorThread(fase, this);
		personagemThread.start();

		frameAtual = 0;
		largura_frame = 68;
		altura_frame = 77;

		this.progresso = progresso;
	}

	public Jogador() {

	}

	public void SalvarVotoTotalBanco() {

		JogadorDAO jogadorDAO = new JogadorDAO(Main.conexaoBanco);
		jogadorDAO.Update(jogadorDAO.Select() + total_voto);
	}

	public void CalcularMovimento() {

		if (pulo) {
			if (posicao[1] > 50) {

				posicao[1] = posicao[1] - 10;

			} else {
				progresso.setQtd_pulo_realizado(progresso.getQtd_pulo_realizado() + 1);
				pulo = false;
			}
		}

		// if (personagemEmCimaPlataforma == true && movimentar[1] == 3) {

		// System.out.println("DEVE CAIR PLATAFORMA");
		// personagemEmCimaPlataforma = false;
		// personagemDescendoPlataforma = true;

		// }

		posicao[0] += movimentarVetor.getX();
		posicao[1] += movimentarVetor.getY();
	}

	public void AnimacaoPersonagemCaindo() {

		frameAtual = 0;
		setImagem("personagem_caindo.png");
	}

	public void CalcularFrame() {

		// Como os frames do personagem na imagem estao todos alinhados verticalmente,
		// nao precisamos percorrer a imagem horizontalmente.
		inicioFrameHorizontal = 0;// inicioFrameHorizontal = (frameAtual % qtdMaximaFrame) * tamanho_frame_x;
		inicioFrameVertical = (frameAtual / 32) * altura_frame;

		fimFrameHorizontal = inicioFrameHorizontal + largura_frame;
		fimFrameVertical = inicioFrameVertical + altura_frame;

		if (frameAtual > 256) {
			frameAtual = 0;
		}

		frameAtual++;

		// System.out.println("inicioFrameVertical = "+inicioFrameVertical);
		// System.out.println("FRAME ATUAL = " + (frameAtual / 32));
		// System.out.println("fimFrameVertical = "+fimFrameVertical);
	}

	public void CalcularGravidade() {

		if (personagemEmCimaPlataforma == false && personagemEmCimaObstaculo == false) {

			// GRAVIDADE
			if (posicao[1] >= 0) {
				posicao[1] += 4;
			}

		}

		// LIMITA A AREA QUE O PERSONAGEM PODE SE MOVER NA TELA. IMPEDE QUE O PERSONAGEM
		// SAIA DA TELA.
		if (posicao[0] < 0) {
			posicao[0] = 0;
		}
		if (posicao[0] > (GerenciarTelas.telaLargura - tamanho[0])) {
			posicao[0] = GerenciarTelas.telaLargura - tamanho[0];
		}
		if (posicao[1] < 0) {
			posicao[1] = 0;
		}
		if (posicao[1] > (GerenciarTelas.telaAltura - 125)) {
			posicao[1] = GerenciarTelas.telaAltura - 125;
			// personagemDescendoPlataforma = false;
		}
		
		//System.out.println("posicao_y = "+posicao[1]);

	}

	public void DesenharPersonagem(Graphics2D graficos2d) {

		graficos2d.drawImage(getImagem(), getPosicao()[0], getPosicao()[1], getRetangulo()[0], getRetangulo()[1],
				getInicioFrameHorizontal(), getInicioFrameVertical(), getFimFrameHorizontal(), getFimFrameVertical(),
				null);
	}

	public boolean isPersonagemVivo() {
		return personagemVivo;
	}

	public void setPersonagemVivo(boolean personagemVivo) {
		this.personagemVivo = personagemVivo;
	}

	public boolean isPersonagemEmCimaPlataforma() {
		return personagemEmCimaPlataforma;
	}

	public void setPersonagemEmCimaPlataforma(boolean personagemEmCimaPlataforma) {
		this.personagemEmCimaPlataforma = personagemEmCimaPlataforma;
	}

	public boolean isPersonagemEmCimaObstaculo() {
		return personagemEmCimaObstaculo;
	}

	public void setPersonagemEmCimaObstaculo(boolean personagemEmCimaObstaculo) {
		this.personagemEmCimaObstaculo = personagemEmCimaObstaculo;
	}

	// public boolean isPersonagemDescendoPlataforma() {
	// return personagemDescendoPlataforma;
	// }

	// public void setPersonagemDescendoPlataforma(boolean
	// personagemDescendoPlataforma) {
	// this.personagemDescendoPlataforma = personagemDescendoPlataforma;
	// }

	public boolean isPulo() {
		return pulo;
	}

	public void setPulo(boolean pulo) {
		this.pulo = pulo;
	}

	public int getFrameAtual() {
		return frameAtual;
	}

	public void setFrameAtual(int frameAtual) {
		this.frameAtual = frameAtual;
	}

	public int getLargura_frame() {
		return largura_frame;
	}

	public void setLargura_frame(int largura_frame) {
		this.largura_frame = largura_frame;
	}

	public int getAltura_frame() {
		return altura_frame;
	}

	public void setAltura_frame(int altura_frame) {
		this.altura_frame = altura_frame;
	}

	public int getInicioFrameHorizontal() {
		return inicioFrameHorizontal;
	}

	public void setInicioFrameHorizontal(int inicioFrameHorizontal) {
		this.inicioFrameHorizontal = inicioFrameHorizontal;
	}

	public int getInicioFrameVertical() {
		return inicioFrameVertical;
	}

	public void setInicioFrameVertical(int inicioFrameVertical) {
		this.inicioFrameVertical = inicioFrameVertical;
	}

	public int getFimFrameHorizontal() {
		return fimFrameHorizontal;
	}

	public void setFimFrameHorizontal(int fimFrameHorizontal) {
		this.fimFrameHorizontal = fimFrameHorizontal;
	}

	public int getFimFrameVertical() {
		return fimFrameVertical;
	}

	public void setFimFrameVertical(int fimFrameVertical) {
		this.fimFrameVertical = fimFrameVertical;
	}

	public int getTotal_voto() {
		return total_voto;
	}

	public void setTotal_voto(int total_voto) {
		this.total_voto = total_voto;
	}

	public int getId_jogador() {
		return id_jogador;
	}

	public void setId_jogador(int id_jogador) {
		this.id_jogador = id_jogador;
	}

}