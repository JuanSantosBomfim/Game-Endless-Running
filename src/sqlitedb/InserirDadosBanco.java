package sqlitedb;

public class InserirDadosBanco {
	
	public InserirDadosBanco(ConexaoBanco conexaoBanco) {
		
		FraseDAO fraseDAO = new FraseDAO(conexaoBanco);	
		fraseDAO.Create();
		
		fraseDAO.Insert("Proponho que vossa senhoria v� a merda!", "INGLES", 1);
		fraseDAO.Insert("Proponho o Bolsa Churrasco!", "INGLES", 4);
		fraseDAO.Insert("O Neg�cio � armar a popula��o!", "INGLES", 2);
		fraseDAO.Insert("A crise foi s� uma marolinha!", "INGLES", 1);
		fraseDAO.Insert("Infla��o � coisa da m�dia!", "INGLES", 1);
		fraseDAO.Insert("S� fui preso porque sou honesto!", "INGLES", 3);
		fraseDAO.Insert("Vou tirar o nome de todos do Serasa!", "INGLES", 4);
		fraseDAO.Insert("Proponho o Bolsa Academia!", "INGLES", 4);
		fraseDAO.Insert("Se eleito os jogos de Steam ser�o gratuitos!", "INGLES", 4);
		fraseDAO.Insert("Jogador de console tem que apanhar na inf�ncia!", "INGLES", 2);
		fraseDAO.Insert("Se eleito vou legalizar o uso de hack em FPS!", "INGLES", 4);
		fraseDAO.Insert("Pobreza � quest�o de escolha!", "INGLES", 1);
		fraseDAO.Insert("Proponho o Bolsa Carro Novo Todo Ano!", "INGLES", 4);
		fraseDAO.Insert("Gl�ria a Deux!", "INGLES", 3);
		fraseDAO.Insert("Deus ex Machina!", "INGLES", 2);
		fraseDAO.Insert("Pior do que t� n�o fica!", "INGLES", 4);
		fraseDAO.Insert("*Dar um tapa na cara do oponente*", "INGLES", 1);
		fraseDAO.Insert("Fique quieto e obede�a �s regras do debate!", "INGLES", 2);
		fraseDAO.Insert("Chega de mimimi!", "INGLES", 1);
		fraseDAO.Insert("Voc� � um not�rio mentiroso. Mentiroso!", "INGLES", 2);
		fraseDAO.Insert("Abra o olho, povo brasileiro!", "INGLES", 2);
		fraseDAO.Insert("Vou criar 2 milh�es de empregos no primeiro ano!", "INGLES", 4);
		fraseDAO.Insert("N�o tem proposta nenhuma, s� frases de efeito!", "INGLES", 2);
		fraseDAO.Insert("Temos que apoiar a volta do voto impresso.", "INGLES", 2);
		fraseDAO.Insert("Vamos usar a tecnologia para ajudar o povo!", "INGLES", 4);
		fraseDAO.Insert("Eu n�o tenho nada a ver com isso!", "INGLES", 3);
		fraseDAO.Insert("No meu governo, teremos os melhores memes!", "INGLES", 2);
		fraseDAO.Insert("Eu n�o roubei, peguei emprestado!", "INGLES", 3);
		
		ProgressoDAO progressoDAO = new ProgressoDAO(conexaoBanco);
		progressoDAO.Create();
		
		JogadorDAO jogadorDAO = new JogadorDAO(conexaoBanco);
		jogadorDAO.Create();
		jogadorDAO.Insert(5000000);
		
		MelhoriaDAO melhoriaDAO = new MelhoriaDAO(conexaoBanco);
		melhoriaDAO.Create();
		
		// 1 a 5
		melhoriaDAO.Insert("Comprar Boca de Urna", "Multiplica os votos Obtidos x2", 50, 2);
		melhoriaDAO.Insert("Multiplicador de Votos N�vel 2", "Multiplica os votos Obtidos x4", 500, 4);
		melhoriaDAO.Insert("Multiplicador de Votos N�vel 3", "Multiplica os votos Obtidos x8", 2500, 8);
		melhoriaDAO.Insert("Multiplicador de Votos N�vel 4", "Multiplica os votos Obtidos x16", 10000, 16);
		melhoriaDAO.Insert("Multiplicador de Votos N�vel 5", "Multiplica os votos Obtidos x32", 100000, 32);
		
		// 6 a 10
		melhoriaDAO.Insert("Comprar Foro Privilegiado", "Deixa o personagem invencivel por 10 seg", 250, 10);
		melhoriaDAO.Insert("Melhorar Invencibilidade N�vel 2", "Aumenta o tempo de invencibilidade para 12 seg", 550, 12);
		melhoriaDAO.Insert("Melhorar Invencibilidade N�vel 3", "Aumenta o tempo de invencibilidade para 15 seg", 1050, 15);
		melhoriaDAO.Insert("Melhorar Invencibilidade N�vel 4", "Aumenta o tempo de invencibilidade para 18 seg", 5550, 18);
		melhoriaDAO.Insert("Melhorar Invencibilidade N�vel 5", "Aumenta o tempo de invencibilidade para 20 seg", 12550, 20);
		
		// 10 a 15
		melhoriaDAO.Insert("Comprar Propaganda Eleitoral", "Ativa um Ima que coleta os votos por 10 seg", 250, 10);
		melhoriaDAO.Insert("Melhorar Ima N�vel 2", "Ativa um Ima que coleta os votos por 12 seg", 550, 12);
		melhoriaDAO.Insert("Melhorar Ima N�vel 3", "Ativa um Ima que coleta os votos por 15 seg", 1050, 15);
		melhoriaDAO.Insert("Melhorar Ima N�vel 4", "Ativa um Ima que coleta os votos por 18 seg", 5550, 18);
		melhoriaDAO.Insert("Melhorar Ima N�vel 5", "Ativa um Ima que coleta os votos por 20 seg", 12550, 20);
		
		// 16
		melhoriaDAO.Insert("URSAL", "Se Torna o Presidente da America Latina", 144000, 0);
		
		JogadorMelhoriaDAO personagemMelhoriaDAO = new JogadorMelhoriaDAO(conexaoBanco);
		personagemMelhoriaDAO.Create();
	}

}
