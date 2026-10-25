package props_sessao;

public enum ClassificacaoIndicativa {
	NAO_DEF(0, "Classificação indicativa não definida"),
	LIVRE(1, "Livre para todas as idades"),
	M10(2, "Mínimo 10 anos"),
	M12(3, "Mínimo 12 anos"),
	M14(4, "Mínimo 14 anos"),
	M16(5, "Mínimo 16 anos"),
	M18(6, "Mínimo 18 anos");
	
	private int id;
	private String label;
	
	private ClassificacaoIndicativa(int id, String label) {
		this.id = id;
		this.label = label;
	}
	
	public int getId() {
		return id;
	}
	
	public String getLabel() {
		return label;
	}
	
	public static ClassificacaoIndicativa getById(int id) {
		for(ClassificacaoIndicativa c: values()) {
			if(c.id == id) return c;
		} 
		return NAO_DEF;
	}
}
