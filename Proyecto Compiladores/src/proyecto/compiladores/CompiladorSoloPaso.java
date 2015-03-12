package proyecto.compiladores;

import java.util.StringTokenizer;

/**
 * Construir un CSP para un lenguaje de producciones gramaticales,
 * el alfabeto se compone de los siguientes simbolos:
 * <ul>
 * <li><Strong>Variable:</strong> Una variable tiene la estructura <nombre> donde nombre es 
 * una secuencia de uno o mas caracteres tomados del conjunto de letras 
 * y digitos. Una variable debe iniciar con una letra.</li>
 * <li><strong>Operadores:</strong> </li>
 * <ul>
 * <li>Concatenacion: Caracter &</li>
 * <li>Alternacion: Caracter |</li>
 * <li>Cerradura cero o mas: Se representa con los caracteres: '{' y '}' Ejemplo: { <numero> }</li>
 * <li>Cerradura cero o uno: Se representa con los caracteres: '[' ']' ejemplo[+|-] 
 *  significa que se puede tomar el caracter + o - de manera opcional</li>
 * <li>Definicion: Se representa por la secuencia de caracteres "::="</li>
 * <li>fin de produccion: Caracter ';'</li>
 * <li>fin de archivo: Caracter '.'</li>
 * </ul>
 * <li><strong>Terminal:</strong> es Cualquier secuencia de Caracteres entre apostrofes</li>
 * <li><strong>Sintaxis y Semantica</strong> La sintaxis y la semantica del lenguaje se representan con la siguiente GLC</li>
 * </ul>
 * 
 * <ul>
 * <li>prog -> conjProds</li>
 * <li>conjProds -> conjProds | Prod</li>
 * <li>Prod -> variable DEF(definicion) Expr;</li>
 * <li>Expr -> Expr ALT Term | Term</li>
 * <li>Term -> Term & Factor | Factor</li>
 * <li>Factor -> {Expr} | [Expr] | Primario</li>
 * <li>Primario -> (Expr) | Variable | terminal</li>
 * </ul>
 * 
 * Ejemplo <Entero> ::= {{['+'|'-']&<Variable>&(['+'|'-'])&{<Variable2>}}&{['+'|'-'] & <Variable3>};
 * <Entero>'+''-'|] &<variable1>}&'+''-'|]&<variable2>}}&'+''-'|]&<variable3>}::=
 * 
 * Entrada por archivos
 * Codigo funcional
 * JAva Doc
 * Pruebas(almenos 8 de mas de 5 lineas cada uno)
 * Estandar de codificacion
 * tecnica PRD
 * @author Alejandro Escobedo Garcia.
 */
public class CompiladorSoloPaso {
    
    private static final int FIN_SENT = ';';
    private static final int CONCATENACION = '&';
    private static final int ALTERNACION = '|';
    private static final int CERRADURA_CERO_MAS_DER = '}';
    private static final int CERRADURA_CERO_MAS_IZQ = '{';
    private static final int CERRADURA_CERO_UNO_DER = ']';
    private static final int CERRADURA_CERO_UNO_IZQ = '[';
    private static final int ASIGNACIONDOSPUNTOS = ':';
    private static final int ASIGNACIONIGUAL = '=';
    private static final int EOF = '.';
    private static final int APOSTROFES = '\'';
    private static final String ASIGNACION = String.format("%s%s%s", (char) ASIGNACIONDOSPUNTOS,  (char) ASIGNACIONDOSPUNTOS, (char) ASIGNACIONIGUAL);
    private Token currentToken;
    private String salida = "";
    private static Integer linea = 1;
    private StringTokenizer tokenizer = null;
    private int VARIABLE = 500;
    
    //TODO checar este javadoc
    /**
     * Tokenizer para obtener los tokens del codigo fuente
     * @param codigoFuente que se leera mediante un archivo
     * @return codigo fuente sin espacios?
     */
    private StringTokenizer getTokenizer(String codigoFuente){
        if (this.tokenizer == null) {
            //;&|{}[]:;=.'
            String alfabetoSimbolos = String.format("%s%s%s%s%s%s%s%s%s%s%s%s",
                    (char) FIN_SENT,
                    (char) CONCATENACION,
                    (char) ALTERNACION,
                    (char) CERRADURA_CERO_MAS_DER,
                    (char) CERRADURA_CERO_MAS_IZQ,
                    (char) CERRADURA_CERO_UNO_DER,
                    (char) CERRADURA_CERO_UNO_IZQ,
                    (char) ASIGNACIONDOSPUNTOS,
                    (char) ASIGNACIONIGUAL,
                    (char) EOF,
                    (char) APOSTROFES
                    );
            this.tokenizer = new StringTokenizer(codigoFuente.trim(), alfabetoSimbolos, true);
        }
        return this.tokenizer;
    }
    
    public void prog(){
        conjProds();
    }
    
    public void conjProds(){
        prod();
    }
    
    public void prod(){
        expr();
    }
    
    public void expr(){
        term();
    }
    
    public void term(){
        factor();
    }
    
    public void factor(){
        primario();
    }
    
    public void primario(){
        //TODO
    }
    
}
