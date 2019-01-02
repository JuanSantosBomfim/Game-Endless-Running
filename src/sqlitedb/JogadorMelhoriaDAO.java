package sqlitedb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import model.Melhoria;
import model.JogadorMelhoria;

public class JogadorMelhoriaDAO {
	
	private ConexaoBanco conexaoBanco;

	public JogadorMelhoriaDAO(ConexaoBanco conexaoBanco) {
		this.conexaoBanco = conexaoBanco;
	}

	public void Create() {

		String sql = "CREATE TABLE IF NOT EXISTS tblpersonagemmelhoria (id_personagem_melhoria integer PRIMARY KEY AUTOINCREMENT , id_personagem integer, id_melhoria integer)";

		conexaoBanco.Conectar();

		try {
			Statement statement = conexaoBanco.CriarStatement();
			statement.execute(sql);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();
	}

	public void Insert(int id_personagem, int id_melhoria) {

		String sql = "INSERT INTO tblpersonagemmelhoria (id_personagem, id_melhoria) VALUES(?, ?)";

		conexaoBanco.Conectar();

		PreparedStatement preparedStatement = conexaoBanco.CriarPreparedStatement(sql);

		try {
			preparedStatement.setInt(1, id_personagem);
			preparedStatement.setInt(2, id_melhoria);

			int resultado = preparedStatement.executeUpdate();

			if (resultado == 1) {
				System.out.println("Melhoria Comprada Com Sucesso!");
			} else {
				System.out.println("Não foi Possivel Comprar a Melhoria!");
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();
	}
	
	public ArrayList<JogadorMelhoria> Select() {

		ResultSet resultSet;
		Statement statement;

		ArrayList<JogadorMelhoria> listaMelhoria = new ArrayList<>();

		String sql = "SELECT * FROM tblpersonagemmelhoria";

		conexaoBanco.Conectar();

		statement = conexaoBanco.CriarStatement();

		try {
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {

				JogadorMelhoria melhoria = new JogadorMelhoria();
				melhoria.setId_melhoria(resultSet.getInt("id_melhoria"));
				melhoria.setId_personagem(resultSet.getInt("id_personagem"));

				listaMelhoria.add(melhoria);
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();

		return listaMelhoria;
	}
}
