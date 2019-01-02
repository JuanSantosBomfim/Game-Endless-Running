package model;

//Classe modelo
public class Melhoria {

	private int id_melhoria;
	private String nome;
	private String descricao;
	private int valor;
	private int efeito;

	public int getId_melhoria() {
		return id_melhoria;
	}

	public void setId_melhoria(int id_melhoria) {
		this.id_melhoria = id_melhoria;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public int getValor() {
		return valor;
	}

	public void setValor(int valor) {
		this.valor = valor;
	}

	public int getEfeito() {
		return efeito;
	}

	public void setEfeito(int efeito) {
		this.efeito = efeito;
	}
	

}
