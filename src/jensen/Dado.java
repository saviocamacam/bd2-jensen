package jensen;

import java.util.LinkedList;

public class Dado {
	private String nome;
	private LinkedList<Acesso> filaBloqueios;
	
	public Dado(String nomeDado) {
		this.nome = nomeDado;
	}

	public String getNome() {
		return nome;
	}

}
