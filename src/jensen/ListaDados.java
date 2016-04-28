
package jensen;

import java.util.LinkedList;

/*@author savio*/

public class ListaDados {
    private LinkedList<Dado> dados;
    
    public ListaDados(int nro_dados) {
        dados = new LinkedList<>();
        geradorDado(dados, nro_dados);
    }
    //Método de geração dos itens de dados, suporta itens alfanumericos
    private void geradorDado(LinkedList<Dado> dados2, int nro_dados) {
        byte[] cont = {65};
        
        while(nro_dados > 0){
           String dado = new String(cont);
            dados2.add(new Dado(dado));
            nro_dados--;
            cont[0]++;
        }
    }

    @Override
    public String toString() {
        return "ItemDado{" + "dados=" + dados + '}';
    }

    public LinkedList<Dado> getDados() {
        return dados;
    }
  
}
