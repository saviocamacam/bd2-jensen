package jensen;

import java.util.LinkedList;

public class Escalonador {
	private Schedule scheduleEscalonado;
	private LinkedList<Operacao> operacoesEscalonadas;
	private LinkedList<Dado> conjuntoDados;
	private LinkedList<Transacao> conjuntoTransacoes;
	
	public Escalonador(Schedule s) {
		conjuntoDados = new LinkedList<>();
		scheduleEscalonado = new Schedule();
		escalonar(s);
	}
	
	public Schedule escalonar(Schedule s) {
		while(!s.getScheduleinlist().isEmpty()) {
			
			Operacao o  = s.getScheduleinlist().remove();
				if(o.getAcesso() == Acesso.START) {
					conjuntoTransacoes.add(o.getIndex(), new Transacao(o.getIndex()));
				}
				else if(o.getAcesso() == Acesso.READ) {
					if(!conjuntoDados.contains(o.getDado())) {
						conjuntoDados.add(o.getDado());
					}
					else {
						int indiceDado = conjuntoDados.indexOf(o.getDado());
						Dado dadoTemp = conjuntoDados.remove(indiceDado);
						if(dadoTemp.getEstado() == 'S' || dadoTemp.getEstado() == 'U') {
							dadoTemp.addLockS(o.getIndex());
							dadoTemp.setEstado('S');
							
							Transacao transacaoTemp = conjuntoTransacoes.remove(o.getIndex());
							if(!transacaoTemp.containsDado(o.getDado())) {
								transacaoTemp.addDado(o.getDado());
							}
							operacoesEscalonadas.add(o);
						}
						else {
							dadoTemp.addListaWait(o.getIndex(), 'S');
						}
						conjuntoDados.add(indiceDado, dadoTemp);
					}
				}
				else if(o.getAcesso() == Acesso.WRITE) {
					
				}
				else if(o.getAcesso() == Acesso.END) {
					if(!isWait(o.getIndex())) {
						
					}
				}
		}
		scheduleEscalonado.setScheduleinlist(operacoesEscalonadas);
		return scheduleEscalonado;
	}
	
	public void lockS(Dado dado, int indiceTransacao) {
		if(dado.unlock(dado))
			dado.enqueueS(dado, indiceTransacao);
		else if(dado.isLockS(dado))
			dado.enqueueS(dado, indiceTransacao);
		/*else
			dado.*/
	}
	
	public void lockX(Dado dado, int indiceTransacao) {
		
	}
	
	public void unlock(Dado dado, int indiceTransacao) {
		
	}
	
	private boolean isWait(int indice) {
		
		return true;
	}
	

}
