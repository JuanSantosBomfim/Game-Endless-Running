package sqlitedb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Random;

import model.Frase;

public class FraseDAO {

	private ConexaoBanco conexaoBanco;

	public FraseDAO(ConexaoBanco conexaoBanco) {
		this.conexaoBanco = conexaoBanco;
	}

	public void Create() {

		String sql = "CREATE TABLE IF NOT EXISTS tblfrase (id_frase integer PRIMARY KEY AUTOINCREMENT, textobr varchar, textoen varchar, dano integer)";

		conexaoBanco.Conectar();

		try {

			Statement statement = conexaoBanco.CriarStatement();
			statement.execute(sql);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();

	}

	public void Insert(String textobr, String textoen, int dano) {

		String sql = "INSERT INTO tblfrase (textobr, textoen, dano) VALUES(?, ?, ?)";

		conexaoBanco.Conectar();

		PreparedStatement preparedStatement = conexaoBanco.CriarPreparedStatement(sql);

		try {

			preparedStatement.setString(1, textobr);
			preparedStatement.setString(2, textoen);
			preparedStatement.setInt(3, dano);

			int resultado = preparedStatement.executeUpdate();

			if (resultado == 1) {
				System.out.println("Frase Inserida Com Sucesso!");
			} else {
				System.out.println("Não foi Possivel Inserir a Frase!");
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();

	}

	public ArrayList<Frase> Select() {

		ResultSet resultSet;
		Statement statement;

		ArrayList<Frase> listaFrases = new ArrayList<>();

		String sql = "SELECT * FROM tblfrase";

		conexaoBanco.Conectar();

		statement = conexaoBanco.CriarStatement();

		try {

			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {

				Frase frase = new Frase();
				frase.setId_frase(resultSet.getInt("id_frase"));
				frase.setTextobr(resultSet.getString("textobr"));
				frase.setTextoen(resultSet.getString("textoen"));
				frase.setDano(resultSet.getInt("dano"));

				listaFrases.add(frase);

			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();

		return listaFrases;

	}

	public ArrayList<Frase> EscolheFraseAleatoria() {

		ArrayList<Frase> listaFrases = Select();

		ArrayList<Frase> listaFraseAleatoria = new ArrayList<>();
		Random random = new Random();

		for (int i = 0; 4 > i; i++) {

			// System.out.println("listaFrases.size() = " + listaFrases.size());
			int numeroAleatorio = random.nextInt(listaFrases.size());
			numeroAleatorio = numeroAleatorio + 1;
			
			// VERIFICA SE JA EXISTE ESSA FRASE NA LISTA
			for (int n = 0; listaFraseAleatoria.size() > n; n++) {

				if (numeroAleatorio == listaFraseAleatoria.get(n).getId_frase()) {

					numeroAleatorio = random.nextInt(listaFrases.size());
					numeroAleatorio = numeroAleatorio + 1;
				}
			}

			// ADICIONA UMA FRASE ALEATORIA NA LISTA
			for (int n = 0; listaFrases.size() > n; n++) {

				if (numeroAleatorio == listaFrases.get(n).getId_frase()) {

					Frase frase = new Frase();
					frase.setId_frase(listaFrases.get(n).getId_frase());
					frase.setTextobr(listaFrases.get(n).getTextobr());
					frase.setTextoen(listaFrases.get(n).getTextoen());
					frase.setDano(listaFrases.get(n).getDano());

					listaFraseAleatoria.add(frase);

				}
			}
		}

		return listaFraseAleatoria;

	}

}
