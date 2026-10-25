package props_sessao;
import java.util.ArrayList;

public class Documentario extends Midia {
   private String assunto;
   private ArrayList<String> fontes;
   
   public Documentario(int idMidia, String nome, int duracao, String diretor, int idCat, int idClassInd, String assunto, ArrayList<String> fontes) {
	   super(idMidia, nome, duracao, diretor, idCat, idClassInd);
       this.assunto = assunto;
       this.fontes = fontes;
   }
   
   public String getAssunto() {
       return assunto;
   }
   public ArrayList<String> getFontes() {
       return fontes;
   }
}