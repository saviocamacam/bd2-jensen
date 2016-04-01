
package jensen;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;
/*@author savio*/

public class Transacao {
	private String labelTransacao;
    private LinkedList<Operacao> filaOperacoes;
    
    private static int index=0;
    
    public Transacao() {
		super();
		filaOperacoes = new LinkedList<>();
		labelTransacao += "T";
	}

	public Transacao(ItemDado dados, int numeroAcessos) {
    	index++;
        filaOperacoes = new LinkedList<>();
        labelTransacao = "T" + index + ":";
        
        filaOperacoes.add(new Operacao(Acesso.START, index));
        randomOp(filaOperacoes, dados.getDados(), numeroAcessos);
        filaOperacoes.add(new Operacao(Acesso.END, index));

	}

	private void randomOp(LinkedList<Operacao> filaOperacoes, LinkedList<String> conjunto, int nro_acessos) {
        int tamanhoConjunto = conjunto.size();
        int[] vetorPosicoes;
        int[] vetorAcessos;
        int cursor = 0;
        
        Random posAleatorias = new Random();
        IntStream streamPosicoes = posAleatorias.ints(nro_acessos, 0, tamanhoConjunto);
        vetorPosicoes = streamPosicoes.toArray();
        
        Random acessosaleatorios = new Random();
        IntStream streamAcessos = acessosaleatorios.ints(nro_acessos, 0, 2);
        vetorAcessos = streamAcessos.toArray();
        
        while(cursor < nro_acessos) {
            int operacao = vetorAcessos[cursor];
            int posDado = vetorPosicoes[cursor];
            
            if(operacao == 0) {
                filaOperacoes.add(new Operacao(conjunto.get(posDado), Acesso.READ, index));
            }
            else if (operacao == 1) {
            	filaOperacoes.add(new Operacao(conjunto.get(posDado), Acesso.WRITE, index));
            }
            cursor++;
        }
    }
	
	public void addOperacao(Operacao novaOp){
		filaOperacoes.add(novaOp);
	}
	
	public void removeOp() {
		filaOperacoes.removeFirst();
	}
	
	public void setLabelTransacao(String index) {
		this.labelTransacao += index + ":";
	}
	
	public boolean transIsEmpty(){
		return filaOperacoes.isEmpty();
		
	}
	
	public Operacao getFirstOp() {
		return filaOperacoes.getFirst();
	}

	@Override
	public String toString() {
		String transacao = "";
		transacao += labelTransacao;
		for( Operacao op : filaOperacoes ){
			transacao += " " + op;
		}
		
		transacao = transacao + '\n';
		return transacao;
	}

	
}
