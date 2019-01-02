package thread;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import main.Main;
import model.Melhoria;
import model.JogadorMelhoria;
import model.Progresso;
import objetofamily.Item;
import objetofamily.Jogador;
import objetofamily.Objeto;
import sqlitedb.MelhoriaDAO;
import sqlitedb.JogadorMelhoriaDAO;
import view.GerenciarTelas;
import view.TelaJogo;

// Classe importante, responsavel por calcular todas as colisoes e habilidades ativas do jogador, alem de recarregar a posicao dos objetos e votos
public class JogoThread extends Thread {

	public static Objeto[] objeto;
	private Item[] item;
	
	private Progresso progresso;
	private Jogador personagem;
	private Rectangle formaPersonagem;	
	
	private int id_melhoria_multiplicador_comprada;
	private int id_melhoria_invencivel_comprada;
	private int id_melhoria_ima_comprada;
	private int multiplicador_voto;
	
	public static boolean personagemInvencivel = false;
	public static boolean personagemComIma = false;
	
	public static double tempo_invencibilidade = 10;
	public static double tempo_ima = 10;
	
	public static int probabilidade_obstaculo = 10;
	public static int probabilidade_item = 10;
	
	public JogoThread(Jogador personagem, Objeto[] objeto, Item[] item, Progresso progresso) {
		this.personagem = personagem;
		this.objeto = objeto;
		this.item = item;
		this.progresso = progresso;
		
		// ACESSA O BANCO E CRIA UMA LISTA COM TODAS AS MELHORIAS COMPRADAS
		JogadorMelhoriaDAO personagemMelhoriaDAO = new JogadorMelhoriaDAO(Main.conexaoBanco);		
		ArrayList<JogadorMelhoria> personagemMelhorias = new ArrayList<>();
		personagemMelhorias = personagemMelhoriaDAO.Select();
						
		// VERIFICA SE O JOGADOR JÁ COMPROU ALGUMA MELHORIA DE INVENCIBILIDADE, ou MULTIPLICADOR DE VOTOS
	
		for(int e = 0; e < personagemMelhorias.size(); e++) {								
			// VERIFICA QUAL O TIPO DA MELHORIA COMPRADA, 0 - 5 = MULTIPLICADOR VOTO, 6 - 10 = INVENCIBILIDADE
			if((personagemMelhorias.get(e).getId_melhoria() >= 1) && (personagemMelhorias.get(e).getId_melhoria() <= 5)) {
				id_melhoria_multiplicador_comprada = personagemMelhorias.get(e).getId_melhoria();
			}	
			if((personagemMelhorias.get(e).getId_melhoria() >= 6) && (personagemMelhorias.get(e).getId_melhoria() <= 10)) {
				id_melhoria_invencivel_comprada = personagemMelhorias.get(e).getId_melhoria();
			}
			if((personagemMelhorias.get(e).getId_melhoria() >= 11) && (personagemMelhorias.get(e).getId_melhoria() <= 15)) {
				id_melhoria_ima_comprada = personagemMelhorias.get(e).getId_melhoria();
			}
		}
		
		// ACESSA O BANCO E CRIA UMA LISTA COM OS DADOS DE TODAS AS MELHORIAS
		ArrayList<Melhoria> listMelhoria = new ArrayList<>();
		MelhoriaDAO melhoriaDAO = new MelhoriaDAO(Main.conexaoBanco);
		listMelhoria = melhoriaDAO.Select();
		
		// DEFINE O MULTIPLICADOR DE VOTOS
		if(id_melhoria_multiplicador_comprada == 0) {
			multiplicador_voto = 1;
			
		}else {
			multiplicador_voto = listMelhoria.get(id_melhoria_multiplicador_comprada).getEfeito();
		}
		
		// DEFINE O TEMPO DE INVENCIBILIDADE
		if(id_melhoria_multiplicador_comprada != 0) {
			tempo_invencibilidade = listMelhoria.get(id_melhoria_invencivel_comprada).getEfeito();
		}
			
		//System.out.println("COMPROU MELHORIA DE MULTIPLICAR VOTO NIVEL = "+id_melhoria_multiplicador_comprada+" / MULT = "+multiplicador_voto);
		//System.out.println("COMPROU MELHORIA DE INVENCIBILIDADE NIVEL = "+(id_melhoria_invencivel_comprada - 5)+" / TEMPO INV = "+tempo_invencibilidade);
		//System.out.println("COMPROU MELHORIA DE IMA NIVEL = "+(id_melhoria_ima_comprada - 10)+" / TEMPO IMA = "+tempo_ima);
		//System.out.println("VELOCIDADE GLOBAL = "+TelaJogo.velocidadeGlobal);
		//System.out.println("");
	}

	@Override
	public void run() {

		while (TelaJogo.personagemVivo == true && TelaJogo.jogando == true) {
			try {

				Jogo();
				Thread.sleep(16);		

			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	// Metodo principal
	public void Jogo() {

		formaPersonagem = personagem.getRectangle();
			
		CalcularTempoMelhoriaInvencivel();
		CalcularTempoMelhoriaIma();	
		
		CalcularColisaoPlataforma();
		CalcularColisaoItem();
		
		RecarregarObjeto();
		RecarregarItem();
	}
	
	// Metodo responsavel por recarregar os itens na tela
		public void RecarregarItem() {
			
			int numeroTipo = 0;
			String tipo = "";
			
			for (int i = 0; i < item.length; i++) {
				
				if (item[i].isVisivel() == false) {
					
					// Gera um numero aleatorio entre 0 e 10, se o numero for menor que 10, o item será um voto, se o numero for igual a 10 ele será uma habilidade
					numeroTipo  = ThreadLocalRandom.current().nextInt(0, probabilidade_item+1);
					
					// CRIA OS VOTOS
					if(numeroTipo < probabilidade_item - 1) {
						
						tipo = "voto";
						
						item[i].setTipo(tipo);
						int tipoVoto = ThreadLocalRandom.current().nextInt(1, 3);
						
						if (tipoVoto == 1) {
							
							int numeroAleatorio = ThreadLocalRandom.current().nextInt(1, 7);
							item[i].setImagem("voto"+numeroAleatorio+".gif");							
							item[i].setPosicao(GerenciarTelas.telaLargura, 500);
							
						}else if (tipoVoto == 2) {
							
							int numeroAleatorio = ThreadLocalRandom.current().nextInt(1, 7);
							item[i].setImagem("votoCeu"+numeroAleatorio+".gif");
							item[i].setPosicao(GerenciarTelas.telaLargura, ThreadLocalRandom.current().nextInt(100, 250));
						}			
						
						
						int velocidadeAleatoria  = ThreadLocalRandom.current().nextInt((int)TelaJogo.velocidadeGlobal, (int)TelaJogo.velocidadeGlobal + 10);
						item[i].setMovimentarVetor(-velocidadeAleatoria, 0);
						
						item[i].setTamanho(100, 100);
						item[i].setPegou(false);
						item[i].setVisivel(true);
						
					
					// CRIA MELHORIA DE INVENCIBILIDADE
					}else if((numeroTipo == probabilidade_item - 1) && (id_melhoria_invencivel_comprada != 0)) {
						
						tipo = "invencivel";	
						
						item[i].setTipo(tipo);
						item[i].setImagem("item2.gif");

						int velocidadeAleatoria  = ThreadLocalRandom.current().nextInt((int)TelaJogo.velocidadeGlobal, (int)TelaJogo.velocidadeGlobal + 10);
						item[i].setMovimentarVetor(-velocidadeAleatoria, 0);
						item[i].setTamanho(100, 100);
						item[i].setPosicao(GerenciarTelas.telaLargura, 500);
						
						item[i].setPegou(false);
						item[i].setVisivel(true);
					
					// CRIA MELHORIA DE IMA
					}else if((numeroTipo > probabilidade_item - 1) && (id_melhoria_ima_comprada != 0)) {
						
						tipo = "ima";	
						
						item[i].setTipo(tipo);
						item[i].setImagem("item2.gif");

						int velocidadeAleatoria  = ThreadLocalRandom.current().nextInt((int)TelaJogo.velocidadeGlobal, (int)TelaJogo.velocidadeGlobal + 10);
						item[i].setMovimentarVetor(-velocidadeAleatoria, 0);
						item[i].setTamanho(100, 100);
						item[i].setPosicao(GerenciarTelas.telaLargura, 500);
						
						item[i].setPegou(false);
						item[i].setVisivel(true);
					}
					
					//System.out.println("RECARREGOU VOTO = "+objeto[i].getTipoObjeto());
				}
			}		
		}
	
	// Metodo responsavel por recarregar todos os objetos(plataforma, obstaculo)
	public void RecarregarObjeto() {
		
		int numeroTipo = 0;
		String tipo = "";
		
		for (int i = 0; i < objeto.length; i++) {
			
			if (objeto[i].isVisivel() == false) {
				
				numeroTipo  = ThreadLocalRandom.current().nextInt(0, 14);		

				// CRIA UM OBSTACULO
				if(numeroTipo >= 0 && numeroTipo <= 8) {
					
					tipo = "obstaculo";
					
					objeto[i].setTipoObjeto(tipo);
					
					int numeroAleatorio  = ThreadLocalRandom.current().nextInt(1, 4);
					String nomeImagem = "obstaculo"+numeroAleatorio+".gif";
					objeto[i].setImagem(nomeImagem);
					
					int velocidadeAleatoria  = ThreadLocalRandom.current().nextInt((int)TelaJogo.velocidadeGlobal, (int)TelaJogo.velocidadeGlobal + 5);
					objeto[i].setMovimentarVetor(-velocidadeAleatoria, 0);
					objeto[i].setTamanho(80, 100);
					objeto[i].setPosicao(GerenciarTelas.telaLargura, 500);
					
					objeto[i].setVisivel(true);
					
				// CRIA UMA PLATAFORMA
				}else if(numeroTipo > 8 && numeroTipo <= 10) {
					
					tipo = "plataforma";	
					
					objeto[i].setTipoObjeto(tipo);
					objeto[i].setImagem("plataforma1.png");
					
					//int velocidadeAleatoria  = ThreadLocalRandom.current().nextInt((int)TelaJogo.velocidadeGlobal - 10, (int)TelaJogo.velocidadeGlobal + 10);
					objeto[i].setMovimentarVetor(-(int)TelaJogo.velocidadeGlobal , 0);
					
					//int numeroAleatorio  = ThreadLocalRandom.current().nextInt(1, 4);
					objeto[i].setTamanho(360, 200);
					objeto[i].setPosicao(GerenciarTelas.telaLargura, GerenciarTelas.telaAltura - 260);
					
					objeto[i].setVisivel(true);					
				}
			
			}
		}		
	}
	
	// Metodo responsavel por calcular se o jogador pegou algum item(voto, habilidade)
	public void CalcularColisaoItem() {


		for (int i = 0; i < item.length; i++) {
			
			// VERIFICA O TIPO DE ITEM QUE O JOGADOR COLIDIU
			if (item[i].getTipo().equals("voto")) {
				if (formaPersonagem.intersects(item[i].getRectangle())) {

					// PEGAR ITEM
					if (item[i].isPegou() == false) {
						
						progresso.setQtd_votos_obtido(progresso.getQtd_votos_obtido() + (item[i].getVoto() * multiplicador_voto));
						progresso.setQtd_item_voto_obtido(progresso.getQtd_item_voto_obtido() + 1);
						
						item[i].setPegou(true);
						item[i].setVisivel(false);
						
						Main.somMouseClique.IniciarSom();				
					}
				}
			}
			
			// VERIFICA O TIPO DE ITEM QUE O JOGADOR COLIDIU
			if (item[i].getTipo().equals("invencivel")) {
				if (formaPersonagem.intersects(item[i].getRectangle())) {

					// PEGAR ITEM
					if (item[i].isPegou() == false) {
						
						personagemInvencivel = true;
						
						Main.somMouseClique.IniciarSom();
																						
						progresso.setQtd_item_obtido(progresso.getQtd_item_obtido() + 1);
						progresso.setQtd_item_invencivel_obtido(progresso.getQtd_item_invencivel_obtido() + 1);
						
						item[i].setPegou(true);
						item[i].setVisivel(false);				
					}
				}
			}
			
			// VERIFICA O TIPO DE ITEM QUE O JOGADOR COLIDIU
			if (item[i].getTipo().equals("ima")) {

				if (formaPersonagem.intersects(item[i].getRectangle())) {

					// PEGAR ITEM
					if (item[i].isPegou() == false) {
												
						progresso.setQtd_item_obtido(progresso.getQtd_item_obtido() + 1);
						progresso.setQtd_item_ima_voto_obtido(progresso.getQtd_item_ima_voto_obtido() + 1);
						
						item[i].setPegou(true);
						item[i].setVisivel(false);
						
						Main.somMouseClique.IniciarSom();
						
						personagemComIma = true;
					}
				}
			}
		}
	}

	public void CalcularColisaoPlataforma() {
	
		for (int i = 0; i < objeto.length; i++) {
			
			if(objeto[i].getTipoObjeto().equals("plataforma")) {
			
				// VERIFICA SE O PERSONAGEM ESTA EM CIMA DA PLATAFORMA	
				if (formaPersonagem.intersects(objeto[i].getRectangle())) {

					if (personagem.getRetangulo()[1] < objeto[i].getRetangulo()[1]) {
						
						personagem.setPersonagemEmCimaPlataforma(true);
						
						break;
					}

				} else {					
					personagem.setPersonagemEmCimaPlataforma(false);				
				}
			}		
		}		
	}

	public void CalcularColisaoObstaculo() {
	
		for (int i = 0; i < objeto.length; i++) {
			
			if(objeto[i].getTipoObjeto().equals("obstaculo")) {
				
				// VERIFICA SE O PERSONAGEM ENCOSTOU NO OBSTACULO, E SE ESCOSTOU NA PARTE DE CIMA
				if (formaPersonagem.intersects(objeto[i].getRectangle())) {
					
					if (personagem.getRetangulo()[1] < objeto[i].getRetangulo()[1]) {
						//personagem.setPersonagemEmCimaObstaculo(true);
					}
		
					TelaJogo.finalizarJogo = true;
					TelaJogo.personagemVivo = false;
		
					Main.somPersonagemMorreu.IniciarSom();
					Main.somPolicia.IniciarSom();
				}	
			}
		}
	}
	
	public void CalcularTempoMelhoriaInvencivel() {
		
		if (personagemInvencivel == false) {
			
			CalcularColisaoObstaculo();
			
		}else {
			
			tempo_invencibilidade = (tempo_invencibilidade - 0.02) ; 
			
			if (tempo_invencibilidade <= 0) {
				
				personagemInvencivel = false;
				tempo_invencibilidade = 10;
			}	
		}		
	}
	
	public void CalcularTempoMelhoriaIma() {
		
		if (personagemComIma == true) {		
			
			tempo_ima = (tempo_ima - 0.02) ; 
			
			if (tempo_ima <= 0) {
				
				personagemInvencivel = false;
				tempo_ima = 10;
			}	
		}		
	}
}
