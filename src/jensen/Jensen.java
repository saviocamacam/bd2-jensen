package jensen;

import java.io.IOException;
import java.util.Scanner;

/*@author savio*/
public class Jensen {
    /*@param args the command line arguments*/
    
    public static void main(String[] args) throws IOException {
        
    	System.out.println("Informe: \n n�mero de itens de dados: ");
        
    	int nro_itens = new Scanner(System.in).nextInt();
        
        System.out.println("n�mero de transacoes: ");
        int nro_transacoes = new Scanner(System.in).nextInt();
        
        System.out.println("n�mero de acessos: ");
        int nro_acessos = new Scanner(System.in).nextInt();
        
        TransacaoManager tm = new TransacaoManager(nro_itens, nro_transacoes, nro_acessos);
        
        //ArquivoManager.gravarArquivoTransacao(tm);
        
        System.out.println(tm);
         
    }
}