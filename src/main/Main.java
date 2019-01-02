package main;

import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GraphicsEnvironment;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import model.Frase;
import model.Som;
import sqlitedb.ConexaoBanco;
import sqlitedb.InserirDadosBanco;
import javax.swing.UIManager.LookAndFeelInfo;

import externo.GifDecoder;
import view.GerenciarTelas;

// Classe principal, essa classe é responsavel por carregar os dados que serão utilizados no jogo como (som, musica, banco de dados, imagens) 
// Após carregar os dados ela abre o menu principal
public class Main extends JFrame {

	public static ConexaoBanco conexaoBanco;

	public static Som musicaMenu;
	public static Som musicaJogo;
	public static Som somMouseClique;
	public static Som somPersonagemMorreu;
	public static Som somPolicia;

	public static ArrayList<Frase> listaFrasesAleatorias;
	public static Font fontePixel;

	public static void main(String[] args) {

		// PEGA UMA FONTE EXTERNA, E REGISTRA SEU TIPO
		try {
			fontePixel = Font.createFont(Font.TRUETYPE_FONT, new File("UtilitarioJogo/fonte/Pixeled.ttf")).deriveFont(10f);
		    
		    GraphicsEnvironment graphicsEnvironment = GraphicsEnvironment.getLocalGraphicsEnvironment();
		    graphicsEnvironment.registerFont(fontePixel);
			
		} catch (FontFormatException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}

		// INICIA A CONEXAO COM BANCO DE DADOS
		conexaoBanco = new ConexaoBanco();
		//new InserirDadosBanco(conexaoBanco);
		
		// CARREGA AS MUSICAS E SONS
		musicaMenu = new Som("menu", true);
		musicaJogo = new Som("jogo", true);
		somMouseClique = new Som("som_mouse_clique", false);
		somPersonagemMorreu = new Som("som_personagem_morreu", false);
		somPolicia = new Som("som_policia", false);
		
		// INICIA A MUSICA DO MENU
		musicaMenu.IniciarSom();

		// ABRE O MENU PRINCIPAL		
		new GerenciarTelas();
	}

}
