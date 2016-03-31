
package jensen;
/*@author savio*/

public enum Operacao {
    READ("R"),
    WRITE("W"),
    START("S"),
    END("E"),
    COMMIT("C"),
    ABORT("A");
	
    String texto;

    private Operacao(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
    
}
