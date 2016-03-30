
package jensen;

import java.util.LinkedList;

/*@author savio*/

public class ItemDado {
    private LinkedList<String> dados;
    
    public ItemDado(int nro_dados) {
        dados = new LinkedList<>();
        geradorDado(dados, nro_dados);
    }
    
    private void geradorDado(LinkedList<String> conjunto_dados, int nro_dados) {
        byte[] cont = {65};
        
        while(nro_dados > 0){
           String dado = new String(cont);
            conjunto_dados.add(dado);
            nro_dados--;
            cont[0]++;
        }
    }

    @Override
    public String toString() {
        return "ItemDado{" + "dados=" + dados + '}';
    }

    public LinkedList<String> getDados() {
        return dados;
    }
  
}
