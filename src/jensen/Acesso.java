/*Classe respons�velo pela retorno dos tipos de acessos*/
package jensen;
/*@author savio*/

public enum Acesso {
    READ("R"),
    WRITE("W"),
    START("S"),
    END("E"),
    COMMIT("C"),
    ABORT("A");
    String texto;

    private Acesso(String texto) {
        this.texto = texto;
    }

    @Override
    public String toString() {
        return texto;
    }
    
}
