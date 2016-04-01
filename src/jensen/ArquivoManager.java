/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
    
   public static void gravarArquivoTransacao(TransacaoManager tm){
	   try {
		writer = new BufferedWriter(
				   new OutputStreamWriter(new FileOutputStream("arquivoTransacao.txt"), "utf-8"));
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
   
   public static void gravarSchedule(LinkedList<Transacao> list, int itemdado, int transacoes, int acessos) {
	   try {
		writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("arquivoSchedule.txt"), "utf-8"));
		Random r = new Random();
		writer.write(itemdado +", "+ transacoes + ", " + acessos + "\n");
		   while( !list.isEmpty() ){
	       	int n = r.nextInt(list.size());
	       	if(!list.get(n).transIsEmpty()) {
	       		writer.write(list.get(n).getFirstOp().toString());
	       		list.get(n).removeOp();
	       	} else {
	       		list.remove(n);
	       	}
		   }
		   
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
   
   public static LinkedList<Transacao> lerArquivoTransacao(){
	   LinkedList<Transacao> transacoes = new LinkedList<>();
	   List<String> linhas = new ArrayList<>();
	   try {
		linhas = Files.readAllLines(Paths.get("arquivoTransacao.txt", ""));
		linhas.remove(0);
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	   
	   for( String s : linhas ){
		 transacoes.add(new Transacao());
		 String[] temp = s.substring(3).split(" ");
		 for( String opString : temp){
			 Operacao op = null;
			 Acesso acesso = null;
			 int index = 0;
			 
			 // verifica se start_ ou end_transaction
			 if ( opString.length() == 3) {
				 if ( opString.contains("S")) {
					 acesso = Acesso.START;
				 } else {
					 acesso = Acesso.END;
				 }
				op = new Operacao(acesso, Integer.parseInt(opString.substring(1, 2)));
			 } else if( opString.length() > 3 ) {
				
				switch ( opString.substring(0, 1) ) {
				case "R":
					acesso = Acesso.READ;
					break;
				case "W":
					acesso = Acesso.WRITE;
				default:
					break;
				}
				
				index = Integer.parseInt(opString.substring(1, 2));
				String dado = opString.substring(3, 4);
				op = new Operacao( dado , acesso, index );
			 }
			 
			 if( op != null){
				 transacoes.getLast().addOperacao(op);
			 }
				 
		 }
	   }
	   
	   return transacoes;
   }
   
 }
