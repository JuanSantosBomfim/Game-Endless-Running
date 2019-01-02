package sqlitedb;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import model.Progresso;

public class ProgressoDAO {
	
	private ConexaoBanco conexaoBanco;

	public ProgressoDAO(ConexaoBanco conexaoBanco) {
		this.conexaoBanco = conexaoBanco;
	}

	public void Create() {
		
		String sql = "CREATE TABLE IF NOT EXISTS tblprogresso (id_progresso integer PRIMARY KEY AUTOINCREMENT,"
				+ " distancia_percorrida integer,"
				+ " qtd_item_obtido integer,"
				+ " qtd_votos_obtido integer,"
				+ " qtd_pulo_realizado integer,"
				+ " qtd_adversario_derrotado integer)";

		conexaoBanco.Conectar();

		try {

			Statement statement = conexaoBanco.CriarStatement();
			statement.execute(sql);

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();
	}

	public void Insert(int distancia_percorrida, int qtd_item_obtido, int qtd_votos_obtido, int qtd_pulo_realizado, int qtd_adversario_derrotado) {

		String sql = "INSERT INTO tblprogresso (distancia_percorrida, qtd_item_obtido, qtd_votos_obtido, qtd_pulo_realizado, qtd_adversario_derrotado) VALUES(?, ?, ?, ?, ?)";

		conexaoBanco.Conectar();

		PreparedStatement preparedStatement = conexaoBanco.CriarPreparedStatement(sql);

		try {

			preparedStatement.setInt(1, distancia_percorrida);
			preparedStatement.setInt(2, qtd_item_obtido);
			preparedStatement.setInt(3, qtd_votos_obtido);
			preparedStatement.setInt(4, qtd_pulo_realizado);
			preparedStatement.setInt(5, qtd_adversario_derrotado);

			int resultado = preparedStatement.executeUpdate();

			if (resultado == 1) {
				System.out.println("Progresso Inserido Com Sucesso!");
			} else {
				System.out.println("Não foi Possivel Inserir o Progresso!");
			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();

	}

	public ArrayList<Progresso> Select() {

		ResultSet resultSet;
		Statement statement;

		ArrayList<Progresso> listaProgresso = new ArrayList<>();

		String sql = "SELECT * FROM tblprogresso";

		conexaoBanco.Conectar();

		statement = conexaoBanco.CriarStatement();

		try {

			resultSet = statement.executeQuery(sql);

			while (resultSet.next()) {

				Progresso progresso = new Progresso();
				progresso.setId_progresso(resultSet.getInt("id_progresso"));
				progresso.setDistancia_percorrida(resultSet.getInt("distancia_percorrida"));
				progresso.setQtd_item_obtido(resultSet.getInt("qtd_item_obtido"));
				progresso.setQtd_votos_obtido(resultSet.getInt("qtd_votos_obtido"));
				progresso.setQtd_pulo_realizado(resultSet.getInt("qtd_pulo_realizado"));
				progresso.setQtd_adversario_derrotado(resultSet.getInt("qtd_adversario_derrotado"));

				listaProgresso.add(progresso);

			}

		} catch (SQLException e) {
			System.err.println(e.getMessage());
		}

		conexaoBanco.Desconectar();

		return listaProgresso;

	}
	
	public ArrayList<Progresso> UltimosCincoProgresso(){
		
		ArrayList<Progresso> listaProgresso = new ArrayList<>();
		listaProgresso = Select();
		
		ArrayList<Progresso> listaCincoUltimoProgresso = new ArrayList<>();
		
		for (int i = 1; i < 6; i++) {
			
			//System.out.println("listaCincoUltimoProgresso index = "+listaProgresso.get(listaProgresso.size()-i).getId_progresso());
			
			try {
				
				listaCincoUltimoProgresso.add(listaProgresso.get(listaProgresso.size()-i));	
				
			} catch (Exception e) {
				return null;
			}							
		}
		
		return listaCincoUltimoProgresso;	
	}
}
