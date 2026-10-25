package props_sessao;
import props_ingresso.*;

public class Assento {
   private char letra;
   private int numero;
   private boolean ocupado;
   private Sessao sessao;
   
   public Assento(char letra, int numero, Sessao sessao) {
       this.letra = letra;
       this.numero = numero;
       this.ocupado = false;
       this.sessao = sessao;
   }
   
   public char getLetra() {
       return letra;
   }
   public int getNumero() {
       return numero;
   }
   public Sessao getSessao() {
	   return sessao;
   }
   
   public boolean ocupado() {
	   return ocupado;
   }
   
   public void ocupar() {
	   this.ocupado = true;
   }
   
   public void exibeAssento() {
       System.out.println("Assento: " + letra + numero);
   }
}