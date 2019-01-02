package sqlitedb;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class ConexaoBanco {

	private Connection conexao;

	public boolean Conectar() {

		String url = "jdbc:sqlite:UtilitarioJogo/banco_de_dados/banco_sqlite.db";

		try {
			conexao = DriverManager.getConnection(url);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}

		//System.out.println("Conectado Com Sucesso!");
		return true;
	}

	public boolean Desconectar() {

		try {
			if (conexao.isClosed() == false) {
				conexao.close();
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return false;
		}

		//System.out.println("Desconectado Com Sucesso!");
		return true;
	}

	public Statement CriarStatement() {

		try {
			return conexao.createStatement();

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}

	}

	public PreparedStatement CriarPreparedStatement(String sql) {

		try {
			return conexao.prepareStatement(sql);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
			return null;
		}
	}

	public Connection getConexao() {
		return conexao;
	}
}
