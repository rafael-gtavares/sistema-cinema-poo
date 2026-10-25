package props_ingresso;
import props_sessao.*;
import java.time.LocalDate;
import java.time.LocalTime;

public class Sessao {
	private Midia midia;
	private LocalDate date;
	private LocalTime time;
	
	public Sessao(Midia midia, LocalDate date, LocalTime time) {
		this.midia = midia;		
		this.date = date;
		this.time = time;
	}
	
	public Midia getMidia() {
		return midia;
	}
	public LocalDate getDate() {
		return date;
	}
	public LocalTime getTime() {
		return time;
	}
}
 
