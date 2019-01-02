package view;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import main.Main;
import model.Melhoria;
import model.JogadorMelhoria;
import sqlitedb.JogadorDAO;
import sqlitedb.MelhoriaDAO;
import thread.CutsceneThread;
import sqlitedb.JogadorMelhoriaDAO;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.UIManager;
import javax.swing.border.TitledBorder;
import javax.swing.border.SoftBevelBorder;
import javax.swing.border.BevelBorder;

// Classe reponsavel pela loja do jogo, acessa o banco de dados para verificar o total de votos que o jogador possui, e salva no banco a quantidade de votos restantes caso alguma melhoria seja comprada
public class TelaLoja {
	
	private int[] idMelhoriaComprada = new int[4];	
	private int qtd_voto_total;
	private int valor_melhoria;
	private JPanel telaLojaPainel = new JPanel();
	private JFrame janela;
	
	private ArrayList<Melhoria> listMelhoria;
	private int id_melhoria_atual;
	
	// UTILIZADO PARA VERIFICAR A QUANTIDADE DE VOTOS QUE O JOGADOR POSSUI
	private JogadorDAO jogadorDAO = new JogadorDAO(Main.conexaoBanco);

	public TelaLoja(JFrame janela) {		

		this.janela = janela;
		this.janela.getContentPane().setLayout(null);
		this.janela.setContentPane(telaLojaPainel);	
		
		// CRIA OS PAINEIS COM AS MELHORIAS	
		ComprarMelhoria(10, 10, "melhoriaLoja1",0, 5, 0, 5);
		ComprarMelhoria(504, 10, "melhoriaLoja2",5, 10, 1, 5);
		ComprarMelhoria(10, 245, "melhoriaLoja3",10, 15, 2, 5);
		ComprarMelhoria(504, 245, "melhoriaLoja4",15, 16, 3, 1);
		
		// ACESSA O BANCO E VERIFICA A QUANTIDADE DE VOTOS QUE O JOGADOR POSSUI									
		qtd_voto_total = jogadorDAO.Select();
		
		// MOSTRA A QTD DE VOTOS TOTAL
		JLabel lblVotos = new JLabel("VOTOS : "+qtd_voto_total);
		lblVotos.setForeground(new Color(255, 255, 255));
		lblVotos.setFont(new Font("Pixeled", Font.BOLD, 13));
		lblVotos.setBounds(33, 552, 453, 25);
		telaLojaPainel.add(lblVotos);

		// CRIA O BOTAO PARA VOLTAR
		JButton botaoVoltar = new JButton("Voltar");
		botaoVoltar.setBounds(780, 539, 200, 50);
		botaoVoltar.setFont(Main.fontePixel);
		telaLojaPainel.add(botaoVoltar);
		
		JLabel label = new JLabel("");
		ImageIcon img_caminho = new ImageIcon("UtilitarioJogo/imagem/fundo5.png");
		label.setIcon(img_caminho);
		label.setBounds(2, -15, 1000, 640);
		telaLojaPainel.add(label);
		
		botaoVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Main.somMouseClique.IniciarSom();

				telaLojaPainel.setVisible(false);
				GerenciarTelas.TelaInicial();
			}
		});		
	}
	
	public void ComprarMelhoria(int posicao_x_painel_melhoria, int posicao_y_painel_melhoria, String nomeImagem, int idMelhoriaMin, int idMelhoriaMax, int posicaoVetorIdMelhoriaComprada, int nivelMax) {
		
		idMelhoriaComprada[posicaoVetorIdMelhoriaComprada] = idMelhoriaMin;
		
		// ACESSA O BANCO E CRIA UMA LISTA COM TODAS AS MELHORIAS COMPRADAS
		JogadorMelhoriaDAO personagemMelhoriaDAO = new JogadorMelhoriaDAO(Main.conexaoBanco);		
		ArrayList<JogadorMelhoria> personagemMelhorias = new ArrayList<>();
		personagemMelhorias = personagemMelhoriaDAO.Select();
		
		// VERIFICA SE O JOGADOR JÁ COMPROU ALGUMA MELHORIA
		for(int i = 0; i < personagemMelhorias.size(); i++) {			
			
			// VERIFICA QUAL O TIPO DA MELHORIA COMPRADA
			if((personagemMelhorias.get(i).getId_melhoria() > idMelhoriaMin) && (personagemMelhorias.get(i).getId_melhoria() <= idMelhoriaMax)) {
				idMelhoriaComprada[posicaoVetorIdMelhoriaComprada] = personagemMelhorias.get(i).getId_melhoria();
			}	
		}
		
		// ACESSA O BANCO E CRIA UMA LISTA COM OS DADOS DE TODAS AS MELHORIAS
		listMelhoria = new ArrayList<>();
		MelhoriaDAO melhoriaDAO = new MelhoriaDAO(Main.conexaoBanco);
		listMelhoria = melhoriaDAO.Select();
			
		id_melhoria_atual = idMelhoriaComprada[posicaoVetorIdMelhoriaComprada];		
		String texto_botao = "";	
		
		// VERIFICA SE EXISTE UMA MELHORIA DISPONIVEL PARA COMPRAR, E QUAL O SEU VALOR
		if ((id_melhoria_atual >= idMelhoriaMin) && (id_melhoria_atual < idMelhoriaMax)) {
			
			valor_melhoria = listMelhoria.get(id_melhoria_atual).getValor();
			texto_botao = "Comprar ( "+listMelhoria.get(id_melhoria_atual).getValor()+" Votos )";
			
		}else if(id_melhoria_atual == idMelhoriaMin){

			valor_melhoria = 250;
			texto_botao = "Destravar Melhoria ( "+valor_melhoria+" Votos )";

		}else if(id_melhoria_atual == idMelhoriaMax){
			
			id_melhoria_atual = idMelhoriaMax - 1;
			valor_melhoria = 0;
			texto_botao = "Nivel Max";
		}
		
		// CRIA O PAINEL DA MELHORIA
		JPanel imaPainel = new JPanel();
		imaPainel.setBackground(new Color(230, 230, 250));
		imaPainel.setBounds(posicao_x_painel_melhoria, posicao_y_painel_melhoria, 476, 224);
		imaPainel.setBorder(new BevelBorder(BevelBorder.LOWERED, Color.BLACK, Color.BLACK, new Color(0, 0, 0), new Color(0, 0, 0)));
		imaPainel.setLayout(null);
		telaLojaPainel.setLayout(null);
		telaLojaPainel.add(imaPainel);
			
		// IMAGEM MELHORIA
		JLabel img_melhoria_1 = new JLabel();
		img_melhoria_1.setBounds(10, 11, 110, 141);
		imaPainel.add(img_melhoria_1);
		img_melhoria_1.setBackground(new Color(255, 255, 255));
		ImageIcon img_melhoria = new ImageIcon("UtilitarioJogo/imagem/" + nomeImagem + ".gif");
		img_melhoria_1.setIcon(img_melhoria);

		// NOME MELHORIA
		JLabel nome_melhoria_1 = new JLabel(listMelhoria.get(id_melhoria_atual).getNome());
		nome_melhoria_1.setBounds(130, 11, 336, 36);
		imaPainel.add(nome_melhoria_1);
		nome_melhoria_1.setFont(new Font("Pixeled", Font.BOLD, 12));

		// DESCRICAO MELHORIA
		JLabel descricao_melhoria_1 = new JLabel();
		descricao_melhoria_1.setBounds(130, 58, 336, 63);
		imaPainel.add(descricao_melhoria_1);
		descricao_melhoria_1.setFont(new Font("Pixeled", Font.PLAIN, 9));				
		descricao_melhoria_1.setText(listMelhoria.get(id_melhoria_atual).getDescricao());																		
	
		// NIVEL MELHORIA
		JLabel lblNivelAtual = new JLabel();
		lblNivelAtual.setBounds(220, 127, 246, 25);
		lblNivelAtual.setFont(new Font("Pixeled", Font.PLAIN, 9));
		imaPainel.add(lblNivelAtual);
		lblNivelAtual.setText("Nivel atual "+(idMelhoriaComprada[posicaoVetorIdMelhoriaComprada] - idMelhoriaMin)+ " / Nivel Max "+nivelMax);
								
		// BOTAO COMPRAR MELHORIA
		JButton botaoComprarMelhoria1 = new JButton("  "+texto_botao);
		botaoComprarMelhoria1.setBounds(220, 163, 246, 50);
		imaPainel.add(botaoComprarMelhoria1);
		botaoComprarMelhoria1.setFont(new Font("Pixeled", Font.BOLD, 8));
		botaoComprarMelhoria1.setIcon(new ImageIcon("UtilitarioJogo/imagem/notes.png"));
		
		botaoComprarMelhoria1.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				
				try {
					valor_melhoria = listMelhoria.get(idMelhoriaComprada[posicaoVetorIdMelhoriaComprada]).getValor();
				} catch (Exception e2) {
					System.out.println("Melhoria no nivel maximo!");
				}
				
				// VERIFICA SE O JOGADOR POSSUI VOTOS O SUFICIENTE PARA COMPRAR A MELHORIA
				if (qtd_voto_total - valor_melhoria >= 0) {
					
					// VERIFICA SE A MELHORIA NÃO ESTA NO NIVEL MAXIMO
					if ((idMelhoriaComprada[posicaoVetorIdMelhoriaComprada])< idMelhoriaMax){
						
						// ALTERA A QUANTIDADE DE VOTOS DO JOGADOR, COM BASE NO PREÇO DA MELHORIA COMPRADA
						//System.out.println("qtd_voto_total -  valor_melhoria= "+(qtd_voto_total - valor_melhoria));
						jogadorDAO.Update(qtd_voto_total - valor_melhoria);
						
						// INSERE O ID DA MELHORIA COMPRADA NO BANCO DE DADOS
						personagemMelhoriaDAO.Insert(1, idMelhoriaComprada[posicaoVetorIdMelhoriaComprada] + 1);						
						JOptionPane.showMessageDialog(null, "Melhoria Comprada com Sucesso!", "Compra Finalizada!", 1);
						
						// INICIA A CUTSCENE FINAL, APOS COMPRAR A ULTIMA MELHORIA
						//if ((idMelhoriaComprada[posicaoVetorIdMelhoriaComprada] + 1) == 16) {
						//	CutsceneThread gerenciarTelaThread = new CutsceneThread("UtilitarioJogo/imagem/finalJogo.gif", 100, "intro");
						//	gerenciarTelaThread.start();	
						//}
						
					}else {		
						
						//if (idMelhoriaComprada[posicaoVetorIdMelhoriaComprada] == 16) {
							
							//int opcao = JOptionPane.showConfirmDialog(null, "Deseja ver a Cutscene Final?", "Cutscene Final", 0, 3, null);
							
							//if (opcao == 0) {							
							//	CutsceneThread gerenciarTelaThread = new CutsceneThread("UtilitarioJogo/imagem/finalJogo.gif", 100, "intro");
							//	gerenciarTelaThread.start();						
							//}
									
						//}else {						
							JOptionPane.showMessageDialog(null, "Essa melhoria já está no nivel maximo!", "Não foi possivel comprar!", 1);								
						//}
					}
						
				}else {				
					JOptionPane.showMessageDialog(null, "Você não tem votos suficientes!", "Não foi possivel comprar!", 1);	
				}
						
				// RECARREGA A JANELA DA LOJA
				new TelaLoja(janela);
			}
		});	
	}	
}
