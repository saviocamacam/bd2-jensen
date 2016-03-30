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
}
