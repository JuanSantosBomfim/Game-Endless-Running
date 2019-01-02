package thread;

import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import externo.GifDecoder;
import main.Main;
import model.Som;
import view.GerenciarTelas;

public class CutsceneThread extends Thread {
	
	private JFrame janela;
	private JPanel telaIntroPainel;	
	private JLabel lblGif = new JLabel();
	
	public static int tempoTransicaoGif;
	private String nomeGif;
	private String nomeMusica;
	
	GifDecoder gifDecoder;
	int quantidadeFrame;
	
	public CutsceneThread(String nomeGif, int tempoTransicaoGif, String nomeMusica) {
		this.nomeGif = nomeGif;
		this.tempoTransicaoGif = tempoTransicaoGif;
		this.nomeMusica = nomeMusica;
		
		
		gifDecoder = new GifDecoder();		
		gifDecoder.read(nomeGif);
		quantidadeFrame = gifDecoder.getFrameCount();
	}

	public void run() {
		try {
			Main.musicaMenu.PararSom();
		} catch (Exception e) {}
			
		CriarJanela();
		Cutscene();
		
		Main.musicaMenu.IniciarSom();
	}
	
	private void Cutscene() {
		
		telaIntroPainel = new JPanel();
		telaIntroPainel.setLayout(null);
		janela.setContentPane(telaIntroPainel);
		
		Som musica = new Som(nomeMusica, true);
		musica.IniciarSom();
		
		PularCutscene pularCutscene = new PularCutscene(telaIntroPainel);
		pularCutscene.start();
		
		// INICIA A CUTSCENE
		GerenciarGif(nomeGif, GerenciarTelas.telaLargura, GerenciarTelas.telaAltura, tempoTransicaoGif, telaIntroPainel, lblGif);		

		musica.PararSom();
		
		telaIntroPainel.setVisible(false);
		janela.dispose();
	}
	
	private void CriarJanela() {

		janela = new JFrame();

		janela.setSize(GerenciarTelas.telaLargura, GerenciarTelas.telaAltura);
		janela.setTitle("CORRIDA PRESIDENCIAL.EXE");
		janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		janela.setResizable(false);
		janela.setVisible(true);
		janela.setLocationRelativeTo(null);
		janela.setLayout(null);
		
		ImageIcon img_caminho = new ImageIcon("UtilitarioJogo/imagem/img_cabeca_jogador.PNG");
		Image imagem = img_caminho.getImage();
		janela.setIconImage(imagem);		
	}
	
	public void GerenciarGif(String caminhoImagem, int telaLargura, int telaAltura, int tempoTransicaoGif, JPanel telaIntroPainel, JLabel lblGif) {
			
		// GifDecoder, PERMITE LER UM GIF E DECODIFICAR A QUANTIDADE DE FRAMES QUE ELE POSSUI
		//GifDecoder gifDecoder = new GifDecoder();		
		//gifDecoder.read(caminhoImagem);
		
		lblGif.setBounds(0, 0, telaLargura, telaAltura);
		
		// ESCONDE A JANELA PRINCIPAL DO JOGO / LOJA
		GerenciarTelas.janela.setVisible(false);	
		//ImageIcon imageIcon;
		
		ImageIcon[] todosFrame = new ImageIcon[quantidadeFrame];
		
		for (int i = 0; i < quantidadeFrame; i++) {
			todosFrame[i] = new ImageIcon(gifDecoder.getFrame(i));
		}
		
		int i = 0;
		while ( i < quantidadeFrame) {
			
			//janela.requestFocus();
			
			// PEGA O FRAME
			//BufferedImage frame = Main.gifDecoder.getFrame(i);

					
			lblGif.setIcon(todosFrame[i]);
			
			System.out.println("lblGif.getIcon() = "+lblGif.getIcon());
			System.out.println("i = "+i);
			
			if(lblGif.getIcon() != todosFrame[i]) {
				System.out.println("true");
			}	
			
			// COLOCA O FRAME NA TELA
			telaIntroPainel.add(lblGif);
			
			
			try {
				Thread.sleep(this.tempoTransicaoGif);
				//Thread.sleep(gifDecoder.getDelay(i));
				i++;
				
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		// MOSTRA A JANELA PRINCIPAL DO JOGO / LOJA
		GerenciarTelas.janela.setVisible(true);	
	}
}