/*
Classe responsável pela gravação e leitura de arquivos
 */
package jensen;


import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;



/*@author savio*/
public class ArquivoManager {
    private static Writer writer;

	public ArquivoManager() {
        
    }
	
	//Método de gravação das transações no arquivo
   public static void gravarArquivoTransacao(TransacaoManager tm, String nomeArquivo){
	   try {
		writer = new BufferedWriter(
		new OutputStreamWriter(new FileOutputStream(nomeArquivo + ".txt"), "utf-8"));
		writer.write(tm.toString());
		writer.close();
	} catch (UnsupportedEncodingException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
   }
   //Método de gravação do schedule gerado
   public static void gravarSchedule(Schedule s, String nomeArquivo) {
	   try {
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(nomeArquivo+".txt"), "utf-8"));
		writer.write(s.getItemdado() +", "+ s.getTransacao()+ ", " + s.getAcesso()+ "\n");
	    writer.write(s.toString());
		writer.close();
		   
	} catch (UnsupportedEncodingException e) {
		e.printStackTrace();
		
	} catch (FileNotFoundException e) {
		e.printStackTrace();
		
	} catch (IOException e) {
		e.printStackTrace();
	}
	   
	   
   }
   //Método de leitura do arquivo de transações que retorna uma lista linkada de transacoes
   public static LinkedList<Transacao> lerArquivoTransacao(String nomeArquivo){
	   LinkedList<Transacao> transacoes = new LinkedList<>();
	   List<String> linhas = new ArrayList<>();
	   try {
		linhas = Files.readAllLines(Paths.get(nomeArquivo+".txt", ""));
		
		String temp = linhas.get(0);
		
		linhas.remove(0);
	} catch (IOException e) {
		e.printStackTrace();
	}
	   
	   for( String s : linhas ){
		 transacoes.add(new Transacao());
		 int indiceDoisPontos = s.indexOf(':');
		 String[] temp = s.substring(indiceDoisPontos+2).split(" ");
		 for( String opString : temp){
			 Operacao op = null;
			 Acesso acesso = null;
			 int index = 0;
			 
			 // verifica se start_ ou end_transaction
			 if ( opString.charAt(0) == 'S' || opString.charAt(0) == 'E') {
				 if ( opString.contains("S")) {
					 acesso = Acesso.START;
				 } else {
					 acesso = Acesso.END;
				 }
				int indicePontoVirgula = opString.indexOf(';');
				op = new Operacao(acesso, Integer.parseInt(opString.substring(1, indicePontoVirgula)));
			 } else {
				
				switch ( opString.substring(0, 1) ) {
				case "R":
					acesso = Acesso.READ;
					break;
				case "W":
					acesso = Acesso.WRITE;
				default:
					break;
				}
				
				int indiceParentese = opString.indexOf('(');
				index = Integer.parseInt(opString.substring(1, indiceParentese));
				
				int indiceParenteseFecha = opString.indexOf(')');
				
				String dado = opString.substring(indiceParentese+1, indiceParenteseFecha);
				op = new Operacao( dado , acesso, index );
			 }
			 
			 if( op != null){
				 transacoes.getLast().addOperacao(op);
			 }
				 
		 }
	   }
	   
	   return transacoes;
   }
   
   public static String getCabecalho(String nomeArquivo) {
	List<String> linha;
	String temp = null;
	try {
		linha = Files.readAllLines(Paths.get(nomeArquivo+".txt", ""));
		temp = linha.get(0);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	return temp;
   }
   
 }
