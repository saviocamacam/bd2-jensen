package jensen;

import java.util.LinkedList;
import java.util.Queue;

public class Dado {
	private String nome;
	private LinkedList<Integer> lockS;
	private int lockX;
	private LinkedList<Wait> listaWait;
	private char estado;
	
	public Dado(String nomeDado) {
		this.nome = nomeDado;
		this.lockS = new LinkedList<>();
		//this.lockX = null;
		this.listaWait = new LinkedList<>();
		this.estado = 'U';
	}

	public String getNome() {
		return nome;
	}
	public void enqueueS(Dado dado, Integer indice) {
		dado.lockS.add(indice);
	}
	public boolean isLockS(Dado dado) {
		if(!dado.lockS.isEmpty())
			return true;
		else return false;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public LinkedList<Wait> getListaWait() {
		return listaWait;
	}
	
	public LinkedList<Integer> getLockS() {
		return lockS;
	}
	
	public void setLockX(Integer indice) {
		this.lockX = indice;
	}
	
	public int getLockX() {
		return lockX;
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
	
	public void addLockX(Integer indice) {
		lockX = indice; 
	}

	public void addListaWait(Integer indice, char bloqueio) {
		listaWait.add(new Wait(indice, bloqueio));
	}
	
	@Override
	public boolean equals(Object o) {
		return this.nome.equals(((Dado) o).getNome());
	}

}
