package ingresso;
import java.time.format.DateTimeFormatter;
import props_ingresso.*;
import props_sessao.*;

public class Ingresso {
	private Cliente cliente;
	private Sessao sessao;
	private Assento assento;
	
	public Ingresso(Cliente cliente, Sessao sessao, Assento assento) {
		this.cliente = cliente;
		this.sessao = sessao;
		this.assento = assento;
	}
	
	public Cliente getCliente() {
		return cliente;
	}
	
	public Sessao getSessao() {
		return sessao;
	}
	
	public Assento getAssento() {
		return assento;
	}
	
	public void exibir() {
		DateTimeFormatter fmt = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		System.out.println("\n===== INGRESSO =====");
		System.out.println("Cliente: " + cliente.getEmail());
		System.out.println("Exibição: " + sessao.getMidia().getNome());
		System.out.println("Data: " + sessao.getDate().format(fmt) + " às " + sessao.getTime());
		assento.exibeAssento();
	}
}