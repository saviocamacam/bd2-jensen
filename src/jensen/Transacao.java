
package jensen;

import java.util.LinkedList;
import java.util.Random;
import java.util.stream.IntStream;
/*@author savio*/

public class Transacao {
	private String labelTransacao;
    private LinkedList<Operacao> filaOperacoes;
    private LinkedList<Dado> conjuntoDados;
    private static int index=0;
    private int indexNovo;
    
    public Transacao() {
		super();
		filaOperacoes = new LinkedList<>();
		labelTransacao += "T";
	}
    
    public Transacao(Integer index) {
    	this.indexNovo = index;
    	this.conjuntoDados = new LinkedList<>();
    }

	public Transacao(ListaDados dados, int numeroAcessos, int indice) {
    	index++;
    	indexNovo = indice;
        filaOperacoes = new LinkedList<>();
        labelTransacao = "T" + indice + ":";
        
        filaOperacoes.add(new Operacao(Acesso.START, indice));
        randomOp(filaOperacoes, dados.getDados(), numeroAcessos);
        filaOperacoes.add(new Operacao(Acesso.END, indice));

	}
	
	private void randomOp(LinkedList<Operacao> filaOperacoes, LinkedList<Dado> conjunto, int nro_acessos) {
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
                filaOperacoes.add(new Operacao(conjunto.get(posDado).getNome(), Acesso.READ, indexNovo ));
            }
            else if (operacao == 1) {
            	filaOperacoes.add(new Operacao(conjunto.get(posDado).getNome(), Acesso.WRITE, indexNovo ));
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
	
	public boolean containsDado(Dado dado) {
		if(conjuntoDados.contains(dado))
			return true;
		else return false;
	}
	
	public void addDado(Dado dado) {
		Dado novoDado = new Dado(dado.getNome());
		conjuntoDados.add(novoDado);
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
	
	@Override
	public boolean equals(Object o) {
		return this.indexNovo == ((Transacao)o).indexNovo;
	}

	public LinkedList<Dado> getConjuntoDados() {
		return conjuntoDados;
	}

	public int getIndexNovo() {
		return indexNovo;
	}
}
