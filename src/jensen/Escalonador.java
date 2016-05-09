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
	/*O funcionamento do escalonador trata do consumo dos itens da lista de opera��es capturada no arquivo de schedule e cada item (operacao) tem
	 * uma fun��o a ser executada:
	 * Tipo S indica que uma transacao foi criada, assim eu crio todos os objetos de transacao que precisam ser gerenciados.
	 * Tipo R ou W aqueles que precisam ser ordenados de acordo com a politica de escalonamento
	 * Tipo E indica que uma transacao deve ser submetida ao Commit e com ele come�a a busca pelas esperas em todos os itens de dados
	 * coletados com o consumo desses itens na lista.
	 * Enquanto a lista de opera��es n�o se esgota com o consumo dos itens, indica que h� itens a serem escalonados. Itens que conseguiram ser
	 * escalonados se tornam outra lista e os que n�o conseguiram se tornam outra e o loop fica se repetindo at� n�o sobrar ninguem ou se
	 * se repetir por duas vezes, o que indica o deadlock e o conjunto de transacoes que sobra s�o as que est�o em espera.
	 * */
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
						if(conjuntoDados.get(getIndiceDado(o.getDado())).getListaWait().contains(new Wait(o.getIndex(), 'R')) && o.getFlagLoopWait() == 1){
							conjuntoDados.get(getIndiceDado(o.getDado())).getListaWait().remove(new Wait(o.getIndex(), 'R'));
							o.setFlagLoopD();
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
								o.setFlagLoopE();
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
						if(conjuntoDados.get(getIndiceDado(o.getDado())).getListaWait().contains(new Wait(o.getIndex(), 'W')) && o.getFlagLoopWait() == 1){
							conjuntoDados.get(getIndiceDado(o.getDado())).getListaWait().remove(new Wait(o.getIndex(), 'W'));
							o.setFlagLoopD();
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
								o.setFlagLoopE();
								operacoesEspera.add(o);
							}
						}
						else if (getEstado(o.getDado()) == 'X') {
							if(conjuntoDados.get(getIndiceDado(o.getDado())).getLockX() == o.getIndex())
								operacoesEscalonadas.add(o);
							else {
								conjuntoDados.get(getIndiceDado(o.getDado())).addListaWait(o.getIndex(), 'W');
								o.setFlagLoopE();
								operacoesEspera.add(o);
							}
						}
						else {
							conjuntoDados.get(getIndiceDado(o.getDado())).addListaWait(o.getIndex(), 'W');
							o.setFlagLoopE();
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
					else {
						o.setFlagLoopE();
						operacoesEspera.add(o);
					}
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
		System.out.print("DEADLOCK ENCONTRADO � TRANSA�OES ENVOLVIDAS ");
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
