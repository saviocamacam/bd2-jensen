package jensen;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Random;
import java.util.Scanner;

/*@author savio*/
public class Jensen {
    /*@param args the command line arguments*/
    
    public static void main(String[] args) throws IOException {
        
    	System.out.println("Informe: \n número de itens de dados: ");
        
    	int nro_itens = new Scanner(System.in).nextInt();
        
        System.out.println("número de transacoes: ");
        int nro_transacoes = new Scanner(System.in).nextInt();
        
        System.out.println("número de acessos: ");
        int nro_acessos = new Scanner(System.in).nextInt();
        
        TransacaoManager tm = new TransacaoManager(nro_itens, nro_transacoes, nro_acessos);
        
        ArquivoManager.gravarArquivoTransacao(tm);
        
        System.out.println(tm);
        
        LinkedList<Transacao> list = ArquivoManager.lerArquivoTransacao();
        Random r = new Random();
        
        ArquivoManager.gravarSchedule(list, nro_itens, nro_transacoes, nro_acessos);
        
   //     System.out.println(list);
        
/*        while( !list.isEmpty() ){
        	int n = r.nextInt(list.size());
        	if(!list.get(n).transIsEmpty()) {
        		System.out.println(list.get(n).getFirstOp());
        		list.get(n).removeOp();
        	} else {
        		list.remove(n);
        	}
        } */     
    }
}