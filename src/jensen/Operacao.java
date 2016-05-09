package jensen;
/*@author savio*/


public class Operacao {
	
	//private String dadoNome;
	private Acesso acesso;
	private int index;
	private Dado dado;
	private int flagLoopWait;
	
	public Operacao(String dadoNome, Acesso acesso, int index) {
		//this.dadoNome = dadoNome;
		this.acesso = acesso;
		this.index = index;
		this.dado = new Dado(dadoNome);
	}
	
	public Operacao(Acesso acesso, int index){
		//this.dado.setNome(null);
		this.acesso = acesso;
		this.index = index;
		this.dado = new Dado(null);
	}
	
	public void setFlagLoopE() {
		this.flagLoopWait = 1;
	}
	
	public void setFlagLoopD() {
		this.flagLoopWait = 0;
	}
	
	public int getFlagLoopWait() {
		return flagLoopWait;
	}

	public String getDadoNome() {
		return dado.getNome();
	}
	
	public Dado getDado() {
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
		if( dado.getNome() != null )
			operacao += "(" + dado.getNome() + ");";
		else
			operacao += ";";
		
		return operacao;
	}
	
}