package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

import main.Main;
import model.Progresso;
import sqlitedb.ProgressoDAO;

// Classe responsavel pelo Ranking do jogo, acessa o banco de dados para pegar as infomações referentes ao progresso do jogador, e montar os graficos
public class TelaRanking {
	
	private static ChartPanel painelGrafico;
	private static String graficoAtual;
	private JPanel telaRankingPainel;
	
	public TelaRanking(JFrame janela) {
		
		// CRIA UM NOVO PAINEL
		telaRankingPainel = new JPanel();
		telaRankingPainel.setLayout(null);
		telaRankingPainel.setBackground(Color.white);
		telaRankingPainel.setPreferredSize(new Dimension(2500, 0));
		
		// PEGA OS DADOS DO BANCO UTILIZANDO A CONEXAO CARREGADA NA CLASSE "MAIN"
		ProgressoDAO progressoDAO = new ProgressoDAO(Main.conexaoBanco);
		ArrayList<Progresso> lstCincoUltProgresso = progressoDAO.UltimosCincoProgresso();
		
		// CRIA UM GRAFICO
		graficoAtual = "estatisticas";	
		CriarGrafico("Estatística das 5 Ultimas Corridas", "Corridas em Ordem Decrecente", "Valores", "estatisticas", 0, 0, lstCincoUltProgresso);
		
		// CRIA OS BOTOES
		JButton botaoVoltar, botaoAnterior, botaoProximo;
		
		botaoVoltar = new JButton("Voltar");
		botaoVoltar.setBounds(50, GerenciarTelas.telaAltura - 120, 200, 50);
		
		botaoAnterior = new JButton("Anterior");
		botaoAnterior.setBounds(550, GerenciarTelas.telaAltura - 120, 200, 50);	
		
		botaoProximo = new JButton("Proximo");
		botaoProximo.setBounds(750, GerenciarTelas.telaAltura - 120, 200, 50);	
		
		botaoVoltar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Main.somMouseClique.IniciarSom();

				telaRankingPainel.setVisible(false);
				GerenciarTelas.TelaInicial();

			}
		});
		
		botaoAnterior.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Main.somMouseClique.IniciarSom();

				painelGrafico.setVisible(false);
				
				if(graficoAtual.equals("estatisticas")) {
					
					graficoAtual = "qtd_adversario_derrotado";
					CriarGrafico("Qtd Adversario Derrotado nas 5 Ultimas Corridas", "Corridas em Ordem Decrecente", "Quantidade de Adversarios Derrotados", "qtd_adversario_derrotado", 0, 0, lstCincoUltProgresso);
					
				}else if(graficoAtual.equals("qtd_adversario_derrotado")) {
					
					graficoAtual = "qtd_pulo_realizado";
					CriarGrafico("Qtd Pulos Realizados nas 5 Ultimas Corridas", "Corridas em Ordem Decrecente", "Quantidade de Pulos Realizados", "qtd_pulo_realizado", 0, 0, lstCincoUltProgresso);
					
				}else if(graficoAtual.equals("qtd_pulo_realizado")) {
					
					graficoAtual = "qtd_votos_obtido";
					CriarGrafico("Qtd Votos Obtidos nas 5 Ultimas Corridas", "Corridas em Ordem Decrecente", "Quantidade de Votos", "qtd_votos_obtido", 0, 0, lstCincoUltProgresso);
					
				}else if(graficoAtual.equals("qtd_votos_obtido")) {
					
					graficoAtual = "qtd_item_obtido";
					CriarGrafico("Qtd Itens Obtidos nas 5 Ultimas Corridas", "Corridas em Ordem Decrecente", "Quantidade de Itens Obtidos", "qtd_item_obtido", 0, 0, lstCincoUltProgresso);
					
				}else if(graficoAtual.equals("qtd_item_obtido")) {
					
					graficoAtual = "distancia_percorrida";
					CriarGrafico("Distancias Percorrida nas 5 Ultimas Corridas", "Corridas em Ordem Decrecente", "Distancia em Metros", "distancia_percorrida", 0, 0, lstCincoUltProgresso);	
					
				}else if(graficoAtual.equals("distancia_percorrida")) {
					
					graficoAtual = "estatisticas";
					CriarGrafico("Estatística das 5 Ultimas Corridas", "Corridas em Ordem Decrecente", "Valores", "estatisticas", 0, 0, lstCincoUltProgresso);
					
				}
				
			}
		});
		
		botaoProximo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				Main.somMouseClique.IniciarSom();

				painelGrafico.setVisible(false);
				
				if(graficoAtual.equals("estatisticas")) {
					
					graficoAtual = "distancia_percorrida";
					CriarGrafico("Distancias Percorrida nas 5 Ultimas Corridas", "Corridas em Ordem Decrecente", "Distancia em Metros", "distancia_percorrida", 0, 0, lstCincoUltProgresso);
					
				}else if(graficoAtual.equals("distancia_percorrida")) {
					
					graficoAtual = "qtd_item_obtido";
					CriarGrafico("Qtd Itens Obtidos nas 5 Ultimas Corridas", "Corridas em Ordem Decrecente", "Quantidade de Itens Obtidos", "qtd_item_obtido", 0, 0, lstCincoUltProgresso);
					
				}else if(graficoAtual.equals("qtd_item_obtido")) {
					
					graficoAtual = "qtd_votos_obtido";
					CriarGrafico("Qtd Votos Obtidos nas 5 Ultimas Corridas", "Corridas em Ordem Decrecente", "Quantidade de Votos", "qtd_votos_obtido", 0, 0, lstCincoUltProgresso);
					
				}else if(graficoAtual.equals("qtd_votos_obtido")) {
					
					graficoAtual = "qtd_pulo_realizado";
					CriarGrafico("Qtd Pulos Realizados nas 5 Ultimas Corridas", "Corridas em Ordem Decrecente", "Quantidade de Pulos Realizados", "qtd_pulo_realizado", 0, 0, lstCincoUltProgresso);
					
				}else if(graficoAtual.equals("qtd_pulo_realizado")) {
					
					graficoAtual = "qtd_adversario_derrotado";
					CriarGrafico("Qtd Adversario Derrotado nas 5 Ultimas Corridas", "Corridas em Ordem Decrecente", "Quantidade de Adversarios Derrotados", "qtd_adversario_derrotado", 0, 0, lstCincoUltProgresso);
				
				}else if(graficoAtual.equals("qtd_adversario_derrotado")) {
					
					graficoAtual = "estatisticas";
					CriarGrafico("Estatística das 5 Ultimas Corridas", "Corridas em Ordem Decrecente", "Valores", "estatisticas", 0, 0, lstCincoUltProgresso);
							
				}
				
			}
		});
		
		// ADICIONA OS COMPONENTES NO PAINEL
		telaRankingPainel.add(botaoVoltar);
		telaRankingPainel.add(botaoAnterior);
		telaRankingPainel.add(botaoProximo);
		
		// COLOCA O PAINEL COM TODOS COMPONENTES NA JANELA PRINCIPAL
		janela.setContentPane(telaRankingPainel);
	}
	
	// Metodo responsavel por criar os graficos
	public void CriarGrafico(String tituloGrafico, String descricaoLateral, String descricaoInferior, String tipoProgresso, int posicao_x, int posicao_y, ArrayList<Progresso> lstCincoUltProgresso) {
		
		JFreeChart grafico;
		
		// CRIA OS DADOS DO GRAFICO
		DefaultCategoryDataset dadosGrafico = new DefaultCategoryDataset();
		
		//DefaultPieDataset dadosGraficoPizza = new DefaultPieDataset();
		//dadosGraficoPizza.setValue("Corrida °"+nCorrida, lstCincoUltProgresso.get(lstCincoUltProgresso.size()-i).getQtd_item_obtido());	
		//grafico = ChartFactory.createPieChart3D(tituloGrafico, dadosGraficoPizza, true, true, false);
		
		int nCorrida = 5;
		for(int i = 1; i < 6; i++) {	
			
			if (tipoProgresso.equals("distancia_percorrida")) {			
				dadosGrafico.setValue(lstCincoUltProgresso.get(lstCincoUltProgresso.size()-i).getDistancia_percorrida(), "Distancia percorrida", "Corrida °"+nCorrida);
			}else if(tipoProgresso.equals("qtd_item_obtido")) {			
				dadosGrafico.setValue(lstCincoUltProgresso.get(lstCincoUltProgresso.size()-i).getQtd_item_obtido(), "Qtd Itens Obtido", "Corrida °"+nCorrida);			
			}else if(tipoProgresso.equals("qtd_votos_obtido")) {		
				dadosGrafico.setValue(lstCincoUltProgresso.get(lstCincoUltProgresso.size()-i).getQtd_votos_obtido(), "Qtd Votos Obtido", "Corrida °"+nCorrida);		
			}else if(tipoProgresso.equals("qtd_pulo_realizado")) {			
				dadosGrafico.setValue(lstCincoUltProgresso.get(lstCincoUltProgresso.size()-i).getQtd_pulo_realizado(), "Qtd Pulos Realizado", "Corrida °"+nCorrida);
			}else if(tipoProgresso.equals("qtd_adversario_derrotado")) {		
				dadosGrafico.setValue(lstCincoUltProgresso.get(lstCincoUltProgresso.size()-i).getQtd_adversario_derrotado(), "Qtd Adversario Derrotado", "Corrida °"+nCorrida);
				
			}else if(tipoProgresso.equals("estatisticas")) {	
				//dadosGrafico.setValue(lstCincoUltProgresso.get(lstCincoUltProgresso.size()-i).getDistancia_percorrida(), "Distancia percorrida", "Corrida °"+nCorrida);
				dadosGrafico.setValue(lstCincoUltProgresso.get(lstCincoUltProgresso.size()-i).getQtd_item_obtido(), "Qtd Itens Obtido", "Corrida °"+nCorrida);	
				dadosGrafico.setValue(lstCincoUltProgresso.get(lstCincoUltProgresso.size()-i).getQtd_votos_obtido(), "Qtd Votos Obtido", "Corrida °"+nCorrida);
				dadosGrafico.setValue(lstCincoUltProgresso.get(lstCincoUltProgresso.size()-i).getQtd_pulo_realizado(), "Qtd Pulos Realizado", "Corrida °"+nCorrida);
				dadosGrafico.setValue(lstCincoUltProgresso.get(lstCincoUltProgresso.size()-i).getQtd_adversario_derrotado(), "Qtd Adversario Derrotado", "Corrida °"+nCorrida);
			}
			
			nCorrida--;
		}
		
		// CRIA UM GRAFICO
		grafico = ChartFactory.createBarChart3D(tituloGrafico, descricaoLateral, descricaoInferior, (CategoryDataset) dadosGrafico, PlotOrientation.VERTICAL, true, true, true);
		
		// CRIA UM PAINEL COM O GRAFICO
		painelGrafico = new ChartPanel(grafico);
		painelGrafico.setBounds(posicao_x, posicao_y, 990, 500);
		
		painelGrafico.restoreAutoBounds();
		
		// COLOCA O PAINEL COM O GRAFICO NO PAINEL QUE APARECE NA TELA
		telaRankingPainel.add(painelGrafico);
	}

}
