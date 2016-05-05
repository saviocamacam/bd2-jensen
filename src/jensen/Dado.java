package jensen;

import java.util.LinkedList;
import java.util.Queue;

public class Dado {
	private String nome;
	private LinkedList<Integer> lockS;
	private LinkedList<Integer> lockX;
	private LinkedList<Wait> listaWait;
	private char estado;
	
	public Dado(String nomeDado) {
		this.nome = nomeDado;
		this.lockS = new LinkedList<>();
		this.lockX = new LinkedList<>();
		this.listaWait = new LinkedList<>();
		this.estado = 'U';
	}

	public String getNome() {
		return nome;
	}
	public void enqueueS(Dado dado, Integer indice) {
		dado.lockS.add(indice);
	}
	public boolean unlock(Dado dado) {
		if(dado.lockS.isEmpty() && dado.lockX.isEmpty())
			return true;
		else return false;
	}
	public boolean isLockS(Dado dado) {
		if(!dado.lockS.isEmpty())
			return true;
		else return false;
	}
	public boolean isLockX(Dado dado) {
		if(!dado.lockX.isEmpty())
			return true;
		else return false;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public LinkedList<Wait> getListaWait() {
		return listaWait;
	}

	public void setListaWait(LinkedList<Wait> listaWait) {
		this.listaWait = listaWait;
	}

	public char getEstado() {
		return estado;
	}

	public void setEstado(char estado) {
		this.estado = estado;
	}

	public void addLockS(Integer indice) {
		lockS.add(indice);
	}

	public void addListaWait(Integer indice, char bloqueio) {
		listaWait.add(new Wait(indice, bloqueio));
	}

}
