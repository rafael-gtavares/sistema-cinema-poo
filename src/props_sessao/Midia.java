package props_sessao;

public abstract class Midia {
	private String nome;
	private Categoria categoria;
	private ClassificacaoIndicativa classInd;
	private int duracao;
	private String diretor;
	private int idMidia;
	
	public Midia(int idMidia, String nome, int duracao, String diretor, int idCat, int idClassInd) {
		this.idMidia = idMidia;
		this.nome = nome;
		this.duracao = duracao;
		this.diretor = diretor;
		this.categoria = Categoria.getById(idCat);
		this.classInd = ClassificacaoIndicativa.getById(idClassInd);
	}
	
	public String getNome() {
		return nome;
	}
	
	public Categoria getCategoria() {
		return categoria;
	}
	
	public ClassificacaoIndicativa getClassInd() {
		return classInd;
	}
	
	public int getDuracao() {
		return duracao;
	}
	
	public String getDiretor() {
		return diretor;
	}
	
	public int getIdMidia() {
		return idMidia;
	}
}
