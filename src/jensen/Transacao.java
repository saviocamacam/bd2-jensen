
package jensen;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.LongStream;
/*@author savio*/

public class Transacao {
    LinkedList<String> filaOperacoes;
    
    private static int index=0;

    public Transacao(ItemDado dados, int numeroAcessos) {
    	index++;
        filaOperacoes = new LinkedList<>();
        filaOperacoes.add("T" + index + ": " + Operacao.START.texto + index + "; ");
        randomOp(filaOperacoes, dados.getDados(), numeroAcessos);
        filaOperacoes.add(Operacao.END.texto + index + ";");

	}

	private void randomOp(LinkedList<String> fila, LinkedList<String> conjunto, int nro_acessos) {
        int tamanhoConjunto = conjunto.size();
        long[] vetorPosicoes;
        long[] vetorAcessos;
        int cursor = 0;
        
        Random posAleatorias = new Random();
        LongStream streamPosicoes = posAleatorias.longs(nro_acessos, 0, tamanhoConjunto);
        vetorPosicoes = streamPosicoes.toArray();
        
        Random acessosaleatorios = new Random();
        LongStream streamAcessos = acessosaleatorios.longs(nro_acessos, 0, 2);
        vetorAcessos = streamAcessos.toArray();
        
        while(cursor < nro_acessos) {
            long operacao = vetorAcessos[cursor];
            long posDado = vetorPosicoes[cursor];
            
            if(operacao == 0) {
                fila.add(Operacao.READ.texto + index + "("+ conjunto.get((int) posDado)+"); ");
            }
            else if (operacao == 1) {
                fila.add(Operacao.WRITE.texto + index + "("+ conjunto.get((int) posDado)+"); ");
            }
            cursor++;
        }
    }

	@Override
	public String toString() {
		String transacao = "";
		for( String s : filaOperacoes ){
			transacao = transacao + s;
		}
		transacao = transacao + '\n';
		return transacao;
	}

	
}
