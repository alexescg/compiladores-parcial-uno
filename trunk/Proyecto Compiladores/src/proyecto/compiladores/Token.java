package proyecto.compiladores;

/**
 * El modelo que contiene los elementos que conforman la expresión, divididos 
 * en los elementos que describe el alfabeto y la gramática.
 * @author Alejandro Escobedo Garcia
 * @since  11/3/2015
 */
public class Token {
    
    //Número de linea donde esta el token
    private Integer linea;
    
    //Valor del token / identificador
    private int token;
    
    //El conjunto de caracteres que va a ser udebtufucadi a traves del token - 
    //por ejemplo ';' punto y coma -- la cadena como tal
    private String lexema;

    public Integer getLinea() {
        return linea;
    }

    public void setLinea(Integer linea) {
        this.linea = linea;
    }

    public int getToken() {
        return token;
    }

    public void setToken(int token) {
        this.token = token;
    }

    public String getLexema() {
        return lexema;
    }

    public void setLexema(String lexema) {
        this.lexema = lexema;
    }

    /**
     * Metodo que sobreescribe el método toString y genera una cadena con los elemenntos del token
     * @return cadena con el formato linea - token - lexema
     */
    @Override
    public String toString() {
        return String.format("%s - %s - %s",
                this.linea,
                this.token,
                this.lexema);
    }
    
    
    
}
