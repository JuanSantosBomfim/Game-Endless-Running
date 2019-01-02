package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import main.Main;
import sqlitedb.ProgressoDAO;
import view.TelaJogo;

//Classe modelo, com metodo para calcular a distancia percorrida, desenhar a quantidade de votos obtidos na corrida, e salvar os votos no banco
public class Progresso {

	// DADOS QUE SERAO SALVOS NO BANCO
	private int id_progresso;
	private int distancia_percorrida;
	private int velocidade_maxima_atingida;
	private int qtd_item_obtido;
	private int qtd_votos_obtido;
	private int qtd_pulo_realizado;
	private int qtd_adversario_derrotado = 0;

	// DADOS TEMPORARIOS
	private int qtd_item_voto_obtido;
	private int qtd_item_invencivel_obtido;
	private int qtd_item_ima_voto_obtido;

	public void CalcularDistanciaPercorrida() {

		if (distancia_percorrida + (int) TelaJogo.velocidadeGlobal >= 1000) {

			TelaJogo.finalizarJogo = true;

		} else {
			//distancia_percorrida = distancia_percorrida + (int) TelaJogo.velocidadeGlobal;
			distancia_percorrida = distancia_percorrida + 80;
		}
	}

	public void DesenharQuantidadeVoto(Graphics2D graficos2d) {
		
		graficos2d.setFont(new Font("Pixeled", Font.BOLD, 13));
		
		if(TelaJogo.img1.equals("fundo4.png")) {
			
			graficos2d.setColor(Color.WHITE);
			
		}else {
			
			graficos2d.setColor(Color.BLACK);
		}
		
		graficos2d.drawString("VOTOS : " + qtd_votos_obtido , 20, 90);
	}

	public void InserirProgressoBanco() {

		ProgressoDAO progressoDAO = new ProgressoDAO(Main.conexaoBanco);
		progressoDAO.Insert((distancia_percorrida + (qtd_adversario_derrotado * 1000)), qtd_item_obtido, qtd_votos_obtido, qtd_pulo_realizado, qtd_adversario_derrotado);
	}

	public int getId_progresso() {
		return id_progresso;
	}

	public void setId_progresso(int id_progresso) {
		this.id_progresso = id_progresso;
	}

	public int getDistancia_percorrida() {
		return distancia_percorrida;
	}

	public void setDistancia_percorrida(int distancia_percorrida) {
		this.distancia_percorrida = distancia_percorrida;
	}

	public int getVelocidade_maxima_atingida() {
		return velocidade_maxima_atingida;
	}

	public void setVelocidade_maxima_atingida(int velocidade_maxima_atingida) {
		this.velocidade_maxima_atingida = velocidade_maxima_atingida;
	}

	public int getQtd_item_obtido() {
		return qtd_item_obtido;
	}

	public void setQtd_item_obtido(int qtd_item_obtido) {
		this.qtd_item_obtido = qtd_item_obtido;
	}

	public int getQtd_votos_obtido() {
		return qtd_votos_obtido;
	}

	public void setQtd_votos_obtido(int qtd_votos_obtido) {
		this.qtd_votos_obtido = qtd_votos_obtido;
	}

	public int getQtd_pulo_realizado() {
		return qtd_pulo_realizado;
	}

	public void setQtd_pulo_realizado(int qtd_pulo_realizado) {
		this.qtd_pulo_realizado = qtd_pulo_realizado;
	}

	public int getQtd_adversario_derrotado() {
		return qtd_adversario_derrotado;
	}

	public void setQtd_adversario_derrotado(int qtd_adversario_derrotado) {
		this.qtd_adversario_derrotado = qtd_adversario_derrotado;
	}

	public int getQtd_item_voto_obtido() {
		return qtd_item_voto_obtido;
	}

	public void setQtd_item_voto_obtido(int qtd_item_voto_obtido) {
		this.qtd_item_voto_obtido = qtd_item_voto_obtido;
	}

	public int getQtd_item_invencivel_obtido() {
		return qtd_item_invencivel_obtido;
	}

	public void setQtd_item_invencivel_obtido(int qtd_item_invencivel_obtido) {
		this.qtd_item_invencivel_obtido = qtd_item_invencivel_obtido;
	}

	public int getQtd_item_ima_voto_obtido() {
		return qtd_item_ima_voto_obtido;
	}

	public void setQtd_item_ima_voto_obtido(int qtd_item_ima_voto_obtido) {
		this.qtd_item_ima_voto_obtido = qtd_item_ima_voto_obtido;
	}

}
