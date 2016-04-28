package jensen;

import java.util.LinkedList;

public class Escalonador {
	private LinkedList<Operacao> scheduleEscalonado;
	private ListaDados dados;
	
	public Escalonador(Schedule s) {
		dados = new ListaDados(10);
		scheduleEscalonado = new LinkedList<>();
	}

}
