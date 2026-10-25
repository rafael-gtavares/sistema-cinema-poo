package props_sessao;

import java.util.ArrayList;
public class Filme extends Midia {
   private String roteirista;
   private ArrayList<String> elenco;
   
   public Filme(int idMidia, String nome, int duracao, String diretor, int idCat, int idClassInd, String roteirista, ArrayList<String> elenco) {
	   super(idMidia, nome, duracao, diretor, idCat, idClassInd);
       this.roteirista = roteirista;
       this.elenco = elenco;
   }
   
   public String getRoteirista() {
       return roteirista;
   }
   public ArrayList<String> getElenco() {
       return elenco;
   }
}