package jensen;

import java.util.LinkedList;

public class Escalonador {
	private Schedule scheduleEscalonado;
	private LinkedList<Operacao> operacoesEscalonadas;
	private LinkedList<Operacao> operacoesEspera;
	private LinkedList<Dado> conjuntoDados;
	private LinkedList<Transacao> conjuntoTransacoes;
	private int flagEscalonador = -1;
	private int sizeOperacoesEspera = -1;
	
	public Escalonador() {
		conjuntoDados = new LinkedList<>();
		scheduleEscalonado = new Schedule();
		conjuntoTransacoes = new LinkedList<>();
		operacoesEscalonadas = new LinkedList<>();
		operacoesEspera = new LinkedList<>();
		new LinkedList<>();
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
						if(conjuntoDados.get(getIndiceDado(o.getDado())).getListaWait().contains(new Wait(o.getIndex(), 'R'))){
							conjuntoDados.get(getIndiceDado(o.getDado())).getListaWait().remove(new Wait(o.getIndex(), 'R'));
						}
						if(getEstado(o.getDado()) == 'S' || getEstado(o.getDado()) == 'U') {
							if(!contemIndice(o.getDado(),o.getIndex()))
								conjuntoDados.get(getIndiceDado(o.getDado())).addLockS(o.getIndex());
							conjuntoDados.get(getIndiceDado(o.getDado())).setEstado('S');
							
							if(!conjuntoTransacoes.get(getIndiceTransacao(o.getIndex())).containsDado(o.getDado())) {
								conjuntoTransacoes.get(getIndiceTransacao(o.getIndex())).getConjuntoDados().add(conjuntoDados.get(getIndiceDado(o.getDado())));
							}
							operacoesEscalonadas.add(o);
						}
						else {
							if(conjuntoDados.get(getIndiceDado(o.getDado())).getLockX() == o.getIndex()) {
								operacoesEscalonadas.add(o);
							}
							else {
								conjuntoDados.get(getIndiceDado(o.getDado())).addListaWait(o.getIndex(), 'S');
								operacoesEspera.add(o);
							}
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
						if(conjuntoDados.get(getIndiceDado(o.getDado())).getListaWait().contains(new Wait(o.getIndex(), 'W'))){
							conjuntoDados.get(getIndiceDado(o.getDado())).getListaWait().remove(new Wait(o.getIndex(), 'W'));
						}
						if(isUnlock(o.getDado()) && listaEsperaVazia(o.getDado())) {
							conjuntoDados.get(getIndiceDado(o.getDado())).setEstado('X');
							conjuntoDados.get(getIndiceDado(o.getDado())).setLockX(o.getIndex());
							if(!conjuntoTransacoes.get(getIndiceTransacao(o.getIndex())).containsDado(o.getDado())) {
								conjuntoTransacoes.get(getIndiceTransacao(o.getIndex())).getConjuntoDados().add(o.getDado());
							}
							operacoesEscalonadas.add(o);
						}
						else if(isUnlock(o.getDado()) && !listaEsperaVazia(o.getDado())) {
						}
						else if(getEstado(o.getDado()) == 'S') {
							if(onlyMeLockS(o.getDado(), o.getIndex())) {
								upgradeLock(conjuntoDados.get(getIndiceDado(o.getDado())), o.getIndex());
								operacoesEscalonadas.add(o);
							}
							else {
								conjuntoDados.get(getIndiceDado(o.getDado())).addListaWait(o.getIndex(), 'W');
								operacoesEspera.add(o);
							}
						}
						else if (getEstado(o.getDado()) == 'X') {
							if(conjuntoDados.get(getIndiceDado(o.getDado())).getLockX() == o.getIndex())
								operacoesEscalonadas.add(o);
							else {
								conjuntoDados.get(getIndiceDado(o.getDado())).addListaWait(o.getIndex(), 'W');
								operacoesEspera.add(o);
							}
						}
						else {
							conjuntoDados.get(getIndiceDado(o.getDado())).addListaWait(o.getIndex(), 'W');
							operacoesEspera.add(o);
						}
					}
				}
				else if(o.getAcesso() == Acesso.END) {
					if(!isWait(o.getIndex())) {
						unlockData(o.getIndex());
						conjuntoTransacoes.remove(getIndiceTransacao(o.getIndex()));
						operacoesEscalonadas.add(new Operacao(Acesso.COMMIT, o.getIndex()));
					}
					else
						operacoesEspera.add(o);
				}
			if(s.getScheduleinlist().size() == 0) {
				if(!waitOperationsSizeEqual(operacoesEspera)) {
					s.setScheduleinlist(operacoesEspera);
					operacoesEspera = new LinkedList<>();
				}
				else {
					flagEscalonador = 0;
					printDeadLock();
					scheduleEscalonado.setScheduleinlist(operacoesEspera);
					return scheduleEscalonado;
				}
				
			}
		}
		flagEscalonador = 1;
		scheduleEscalonado.setScheduleinlist(operacoesEscalonadas);
		return scheduleEscalonado;
	}
	
	private void printDeadLock() {
		System.out.print("DEADLOCK ENCONTRADO – TRANSAÇOES ENVOLVIDAS ");
		for(Transacao t : conjuntoTransacoes) {
			System.out.print("T" + t.getIndexNovo() + ", ");
		}
		System.out.println();
	}

	public int getFlagEscalonador() {
		return flagEscalonador;
	}
	
	private boolean waitOperationsSizeEqual(LinkedList<Operacao> operacoesEspera2) {
		if(sizeOperacoesEspera == operacoesEspera2.size()) {
			return true;
		}
		else {
			sizeOperacoesEspera = operacoesEspera2.size();
			return false;
		}
	}

	private boolean unlockData(int index) {
		while(!conjuntoTransacoes.get(getIndiceTransacao(index)).getConjuntoDados().isEmpty()) {
			Dado dado = conjuntoTransacoes.get(getIndiceTransacao(index)).getConjuntoDados().remove();
			if(meuBloqueioExclusivo(dado, index)) {
				conjuntoDados.get(getIndiceDado(dado)).setLockX(-1);
				conjuntoDados.get(getIndiceDado(dado)).setEstado('U');
			}
			else if(onlyMeLockS(dado, index)) {
				conjuntoDados.get(getIndiceDado(dado)).getLockS().remove((Integer)index);
				conjuntoDados.get(getIndiceDado(dado)).setEstado('U');
			}
			else if(!onlyMeLockS(dado, index)) {
				conjuntoDados.get(getIndiceDado(dado)).getLockS().remove((Integer)index);
				conjuntoDados.get(getIndiceDado(dado)).setEstado('S');
			}
		}
		return true;
	}

	private boolean meuBloqueioExclusivo(Dado dado, int index) {
		if(conjuntoDados.get(getIndiceDado(dado)).getLockX() == index)
			return true;
		else return false;
	}

	private boolean listaEsperaVazia(Dado dado) {
		if(conjuntoDados.get(getIndiceDado(dado)).getListaWait().isEmpty()) {
			return true;
		}
		else return false;
	}

	public boolean contemIndice(Dado dado, int indice) {
		if(conjuntoDados.get(getIndiceDado(dado)).getLockS().contains(indice)){
			return true;
		}
		else return false;
	}
	
	public char getEstado(Dado dado) {
		return conjuntoDados.get(getIndiceDado(dado)).getEstado();
	}

	private void upgradeLock(Dado dado, int indice) {
		dado.getLockS().remove();
		dado.setLockX(indice);
		dado.setEstado('X');
	}

	private boolean onlyMeLockS(Dado dado, int indice) {
		if(conjuntoDados.get(getIndiceDado(dado)).getLockS().size() == 1 && conjuntoDados.get(getIndiceDado(dado)).getLockS().getLast() == indice) {
			return true;
		}
		return false;
	}

	private boolean isWait(int indice) {
		for(Dado d : conjuntoDados) {
			if(d.getListaWait().contains(new Wait(indice))) {
				return true;
			}
		}
		return false;
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
