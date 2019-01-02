package sqlitedb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Melhoria;

public class MelhoriaDAO {

	private ConexaoBanco conexaoBanco;

	public MelhoriaDAO(ConexaoBanco conexaoBanco) {
		this.conexaoBanco = conexaoBanco;
	}

	public void Create() {

		String sql = "CREATE TABLE IF NOT EXISTS tblmelhoria (id_melhoria integer PRIMARY KEY AUTOINCREMENT , nome varchar, descricao varchar, valor integer, efeito integer)";

		conexaoBanco.Conectar();

		try {
			Statement statement = conexaoBanco.CriarStatement();
			statement.execute(sql);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();
	}

	public void Insert(String nome, String descricao, int valor, int efeito) {

		String sql = "INSERT INTO tblmelhoria (nome, descricao, valor, efeito) VALUES(?, ?, ?, ?)";

		conexaoBanco.Conectar();

		PreparedStatement preparedStatement = conexaoBanco.CriarPreparedStatement(sql);

		try {
			preparedStatement.setString(1, nome);
			preparedStatement.setString(2, descricao);
			preparedStatement.setInt(3, valor);
			preparedStatement.setInt(4, efeito);

			int resultado = preparedStatement.executeUpdate();

			if (resultado == 1) {
				System.out.println("Melhoria Inserida Com Sucesso!");
			} else {
				System.out.println("Não foi Possivel Inserir a Melhoria!");
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();
	}

	public ArrayList<Melhoria> Select() {

		ResultSet resultSet;
		Statement statement;

		ArrayList<Melhoria> listaMelhoria = new ArrayList<>();

		String sql = "SELECT * FROM tblmelhoria";

		conexaoBanco.Conectar();

		statement = conexaoBanco.CriarStatement();

		try {
			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {

				Melhoria melhoria = new Melhoria();
				melhoria.setId_melhoria(resultSet.getInt("id_melhoria"));
				melhoria.setNome(resultSet.getString("nome"));
				melhoria.setDescricao(resultSet.getString("descricao"));
				melhoria.setValor(resultSet.getInt("valor"));
				melhoria.setEfeito(resultSet.getInt("efeito"));

				listaMelhoria.add(melhoria);
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();

		return listaMelhoria;
	}

}
