package jensen;

import java.io.IOException;
import java.util.LinkedList;
import java.util.Scanner;

public class Jensen {
    public static int opcao;
    static int nro_itens;
    static int nro_transacoes;
    static int nro_acessos;
    static String nomeArquivo;
    //Necessário ainda criar métodos de reset ao final de entradas e geração de schedules
    public static void main(String[] args) throws IOException {
    	do {
    		System.out.println("(1) Gerar transacoes: \n(2) Gerar Schedule:\n(3) Escalonar Schedule:\n");
    		opcao = new Scanner(System.in).nextInt();
    		if(opcao == 1) {
    			System.out.println("Quantos itens de dados devem ser criados? ");
    	    	nro_itens = new Scanner(System.in).nextInt();
    	    	while (nro_itens > 26) {
    	    		System.out.println("Informe valor menor ou igual a 26\n");
    	    		nro_itens = new Scanner(System.in).nextInt();
    	    	}
    	    	System.out.println("Quantas transacoes: ");
    	        nro_transacoes = new Scanner(System.in).nextInt();
    	        System.out.println("Quantos acessos: ");
    	        nro_acessos = new Scanner(System.in).nextInt();
    	        
    	        TransacaoManager tm = new TransacaoManager(nro_itens, nro_transacoes, nro_acessos);
    	        System.out.println("Nome do arquivo de Transações (destino)");
    	        nomeArquivo = new Scanner(System.in).next();
    	        
    	        ArquivoManager.gravarArquivoTransacao(tm, nomeArquivo);
    	        /* Os parâmetros do usuário são enviados para a classe TransacaoManager que gerencia a criação de transações retornando uma listas delas.
    	         * Depois disso há a gravação com método implementado pela classe ArquivoManager. */
    	    	
    		} else if (opcao == 2) {
    			System.out.println("Nome do arquivo de Transações (fonte)");
    			nomeArquivo = new Scanner(System.in).next();
    			Schedule s = new Schedule(nomeArquivo);
    			s.cabecalho(ArquivoManager.getCabecalho(nomeArquivo));
    			
    			//LinkedList<Transacao> list = ArquivoManager.lerArquivoTransacao(nomeArquivo);
    			
    			System.out.println("Nome do arquivo de Schedule (destino)");
    			nomeArquivo = new Scanner(System.in).next();
    			
    			ArquivoManager.gravarSchedule(s, nomeArquivo);
    	        
    			/* Método da classe ArquivoManager ler o arquivo retornando uma lista linkada de Transações, onde cada transação é uma lista de operações
    			 * Com isso é criado um schedule como um conjunto aleatorio de operações extraídas das operações segundo sua ordem
    			 * Depois de gerado o schedule sorteado é feita a gravação com método da classe de gerenciamento de arquivos */
    		} else if (opcao == 3) {
    			System.out.println("Nome do arquivo de Schedule (fonte):");
    			nomeArquivo = new Scanner(System.in).next();
    			
    			LinkedList<Operacao> list = ArquivoManager.lerArquivoSchedule(nomeArquivo);
    		
    			Schedule s = new Schedule(nomeArquivo);
    			s.setScheduleinlist(list);
    			s.cabecalho(ArquivoManager.getCabecalho(nomeArquivo));
    			
    		}
    		
    	} while (opcao != 4);
        
    }
}