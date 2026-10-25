package props_sessao;

public enum Categoria {
	NAO_DEF(0, "Categoria não definida"),
	TERROR(1, "Terror"),
	ACAO(2, "Ação"),
	COMEDIA(3, "Comédia"),
	SUSPENSE(4, "Suspense"),
	FICCAO_CIENTIFICA(5, "Ficção Científica");
	
	private int id;
	private String label;
	
	private Categoria(int id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static Categoria getById(int id) {
		for(Categoria c: values()) {
			if(c.id == id) return c;
		} 
		return NAO_DEF;
	}
}
