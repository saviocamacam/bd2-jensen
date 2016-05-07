package jensen;

import java.util.LinkedList;

public class Escalonador {
	private Schedule scheduleEscalonado;
	private LinkedList<Operacao> operacoesEscalonadas;
	private LinkedList<Operacao> operacoesEspera;
	private LinkedList<Dado> conjuntoDados;
	private LinkedList<Transacao> conjuntoTransacoes;
	
	public Escalonador(Schedule s) {
		conjuntoDados = new LinkedList<>();
		scheduleEscalonado = new Schedule();
		conjuntoTransacoes = new LinkedList<>();
		operacoesEscalonadas = new LinkedList<>();
		operacoesEspera = new LinkedList<>();
		escalonar(s);
	}
	
	public Schedule escalonar(Schedule s) {
		while(!s.getScheduleinlist().isEmpty()) {
			Operacao o  = s.getScheduleinlist().remove();
				if(o.getAcesso() == Acesso.START) {
					conjuntoTransacoes.add(new Transacao(o.getIndex()));
				}
				else if(o.getAcesso() == Acesso.READ) {
					if(!conjuntoDados.contains(o.getDado())) {
						conjuntoDados.add(o.getDado());
						conjuntoDados.getLast().setEstado('S');
						conjuntoDados.getLast().addLockS(o.getIndex());
						conjuntoTransacoes.get(getIndiceTransacao(o.getIndex())).getConjuntoDados().add(o.getDado());
						operacoesEscalonadas.add(o);
					}
					else {
						if(getStatus(o.getDado()) == 'S' || getStatus(o.getDado()) == 'U') {
							if(!constainsIndice(o.getDado(),o.getIndex()))
								conjuntoDados.get(getIndiceDado(o.getDado())).addLockS(o.getIndex());
							conjuntoDados.get(getIndiceDado(o.getDado())).setEstado('S');
							
							if(!conjuntoTransacoes.get(getIndiceTransacao(o.getIndex())).containsDado(o.getDado())) {
								conjuntoTransacoes.get(getIndiceTransacao(o.getIndex())).getConjuntoDados().add(o.getDado());
							}
							operacoesEscalonadas.add(o);
						}
						else {
							conjuntoDados.get(getIndiceDado(o.getDado())).addListaWait(o.getIndex(), 'S');
							operacoesEspera.add(o);
						}
					}
				}
				else if(o.getAcesso() == Acesso.WRITE) {
					if(!conjuntoDados.contains(o.getDado())) {
						conjuntoDados.add(o.getDado());
						conjuntoDados.getLast().setEstado('X');
						conjuntoDados.getLast().addLockX(o.getIndex());
						conjuntoTransacoes.get(getIndiceTransacao(o.getIndex())).getConjuntoDados().add(o.getDado());
						operacoesEscalonadas.add(o);
					}
					else {
						if(isUnlock(o.getDado())) {
							//conjuntoDados.get(getIndiceDado(o.getDado())).getLockX().contains(o.getDado());
							if(!conjuntoTransacoes.get(getIndiceTransacao(o.getIndex())).containsDado(o.getDado())) {
								conjuntoTransacoes.get(getIndiceTransacao(o.getIndex())).getConjuntoDados().add(o.getDado());
							}
							operacoesEscalonadas.add(o);
						}
						else {
							if(onlyOneLockS(conjuntoDados.get(getIndiceDado(o.getDado())), o.getIndex())) {
								upgradeLock(conjuntoDados.get(getIndiceDado(o.getDado())), o.getIndex());
								operacoesEscalonadas.add(o);
							}
							else {
								conjuntoDados.get(getIndiceDado(o.getDado())).addListaWait(o.getIndex(), 'W');
								operacoesEspera.add(o);
							}
						}
					}
				}
				else if(o.getAcesso() == Acesso.END) {
					if(!isWait(o.getIndex())) {
						
					}
				}
		}
		scheduleEscalonado.setScheduleinlist(operacoesEscalonadas);
		return scheduleEscalonado;
	}
	
	public boolean constainsIndice(Dado dado, int indice) {
		if(conjuntoDados.get(getIndiceDado(dado)).getLockS().contains(indice)){
			return true;
		}
		else return false;
	}
	
	public char getStatus(Dado dado) {
		return conjuntoDados.get(getIndiceDado(dado)).getEstado();
	}

	private void upgradeLock(Dado dado, int indice) {
		dado.getLockS().remove();
		dado.setLockX(indice);
		dado.setEstado('X');
	}

	private boolean onlyOneLockS(Dado dado, int indice) {
		int lenWait = dado.getListaWait().size();
		int lenLockS = dado.getLockS().size();
		int indiceCompare = dado.getLockS().getFirst();
		
		if(dado.getListaWait().size() == 0 && dado.getLockS().size() == 1 && dado.getLockS().getLast() == indice) {
			return true;
		}
		return false;
	}

	private boolean isWait(int indice) {
		return true;
	}
	
	private boolean isUnlock(Dado dado) {
		if(conjuntoDados.get(getIndiceDado(dado)).getEstado() == 'U') {
			return true;
		}
		else return false;
	}
	
	private int getIndiceTransacao(int indiceObjeto) {
		return conjuntoTransacoes.indexOf(new Transacao(indiceObjeto));
	}
	
	private int getIndiceDado(Dado dado) {
		return conjuntoDados.indexOf(dado);
	}
	

}
