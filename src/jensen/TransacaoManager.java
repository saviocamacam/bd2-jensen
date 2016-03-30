package jensen;

import java.util.LinkedList;

public class TransacaoManager {
		
	ItemDado dados;
    LinkedList<Transacao> listaTransacoes;
    int numeroAcessos;
	
	
	public TransacaoManager( int numeroDados, int numeroTransacoes, int numeroAcessos) {
		dados = new ItemDado(numeroDados);
		listaTransacoes = new LinkedList<>();
		this.numeroAcessos = numeroAcessos;
		createTransacoes(dados, numeroTransacoes, numeroAcessos);	
	}


	private void createTransacoes(ItemDado dados, int numeroTransacoes, int numeroAcessos) {
		
		while( numeroTransacoes > 0 ) {
			listaTransacoes.add(new Transacao(dados, numeroAcessos));
			numeroTransacoes--;
		}
	}


	@Override
	public String toString() {
		String tmString = "";
		tmString =  dados.getDados().size() + "," + listaTransacoes.size() + "," + numeroAcessos + "\n";
		for( Transacao t : listaTransacoes ){
			tmString = tmString + t;
		}
		
		return tmString;
	}
	
	
}
