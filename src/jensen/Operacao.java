package jensen;

public class Operacao {
	
	private String dado;
	private Acesso acesso;
	private int index;
	
	public Operacao(String dado, Acesso acesso, int index) {
		this.dado = dado;
		this.acesso = acesso;
		this.index = index;
	}
	
	public Operacao(Acesso acesso, int index){
		this.dado = null;
		this.acesso = acesso;
		this.index = index;
	}

	public String getDado() {
		return dado;
	}

	public Acesso getAcesso() {
		return acesso;
	}

	public int getIndex() {
		return index;
	}

	@Override
	public String toString() {
		String operacao = "";
		operacao += acesso.toString() + index;
		if( dado != null )
			operacao += "(" + dado + ");";
		else
			operacao += ";";
		
		return operacao;
	}
	
}
