package jensen;

import java.util.LinkedList;
import java.util.Random;

public class Schedule {
	private LinkedList<Operacao> scheduleinlist;
	private int itemdado;
	private int transacao;
	private int acesso;
	
	public Schedule(LinkedList<Transacao> transacao, int itemDado, int nroTransacao, int acesso) {
		this.itemdado = itemDado;
		this.transacao = nroTransacao;
		this.acesso = acesso;
		
		scheduleinlist = new LinkedList<>();
		ligaOperacoes(transacao);
	}
	
	private void ligaOperacoes(LinkedList<Transacao> listaTransacao) {
		Random r = new Random();
		while( !listaTransacao.isEmpty() ){
	       	int n = r.nextInt(listaTransacao.size());
	       	if(!listaTransacao.get(n).transIsEmpty()) {
	       		scheduleinlist.add(listaTransacao.get(n).getFirstOp());
	       		listaTransacao.get(n).removeOp();
	       	} else {
	       		listaTransacao.remove(n);
	       	}
		   }
	}

	public LinkedList<Operacao> getScheduleinlist() {
		return scheduleinlist;
	}

	public int getItemdado() {
		return itemdado;
	}

	public int getTransacao() {
		return transacao;
	}

	public int getAcesso() {
		return acesso;
	}

	@Override
	public String toString() {
		String str = "";
		for (Operacao op : scheduleinlist) {
			str += op.toString() + " ";
		}
		return str;
	}
}
