
package jensen;
/*@author savio*/

public enum Comando {
    READ("R"),
    WRITE("W"),
    START("S"),
    END("E"),
    COMMIT("C"),
    ABORT("A");
    String texto;

    private Comando(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
    
}
