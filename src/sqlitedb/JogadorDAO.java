package sqlitedb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class JogadorDAO {
	
	private ConexaoBanco conexaoBanco;

	public JogadorDAO(ConexaoBanco conexaoBanco) {
		this.conexaoBanco = conexaoBanco;
	}

	// O Create cria a tabela do jogador, com o id do jogador e o total de votos
	public void Create() {

		String sql = "CREATE TABLE IF NOT EXISTS tbljogador (id_jogador integer PRIMARY KEY AUTOINCREMENT , total_voto integer)";

		conexaoBanco.Conectar();

		try {
			Statement statement = conexaoBanco.CriarStatement();
			statement.execute(sql);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();
	}

	// O Insert insere a quantidade de votos que o jogador vai possuir ao iniciar o jogo
	public void Insert(int total_voto) {

		String sql = "INSERT INTO tbljogador (total_voto) VALUES(?)";

		conexaoBanco.Conectar();

		PreparedStatement preparedStatement = conexaoBanco.CriarPreparedStatement(sql);

		try {
			preparedStatement.setInt(1, total_voto);

			int resultado = preparedStatement.executeUpdate();

			if (resultado == 1) {
				System.out.println("Votos Totais salvos Com Sucesso!");
			} else {
				System.out.println("Não foi Possivel salvar os votos!");
			}
			
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}
		
		conexaoBanco.Desconectar();
	}
	
	// O Update atualiza a quantidade de votos totais que o jogador possui
	public void Update(int total_voto) {

		String sql = "UPDATE tbljogador SET total_voto = ?";

		conexaoBanco.Conectar();

		PreparedStatement preparedStatement = conexaoBanco.CriarPreparedStatement(sql);

		try {
			preparedStatement.setInt(1, total_voto);

			int resultado = preparedStatement.executeUpdate();

			if (resultado == 1) {
				//System.out.println("Votos Totais alterados Com Sucesso!");
			} else {
				//System.out.println("Não foi Possivel alterar os votos!");
			}
		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();
	}

	// O Select Retorna um valor integer, com a quantidade de votos totais que o jogador possui
	public int Select() {

		ResultSet resultSet;
		Statement statement;
		int qtd_voto_atual = 0;

		String sql = "SELECT * FROM tbljogador";

		conexaoBanco.Conectar();

		statement = conexaoBanco.CriarStatement();

		try {
			resultSet = statement.executeQuery(sql);
			qtd_voto_atual = resultSet.getInt("total_voto");

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();

		return qtd_voto_atual;
	}
}
