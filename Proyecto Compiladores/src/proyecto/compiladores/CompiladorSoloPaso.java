package proyecto.compiladores;

import java.util.StringTokenizer;
import jdk.nashorn.internal.codegen.CompilerConstants;

/**
 * Construir un CSP para un lenguaje de producciones gramaticales, el alfabeto
 * se compone de los siguientes simbolos:
 * <ul>
 * <li><Strong>Variable:</strong> Una variable tiene la estructura <nombre>
 * donde nombre es una secuencia de uno o mas caracteres tomados del conjunto de
 * letras y digitos. Una variable debe iniciar con una letra.</li>
 * <li><strong>Operadores:</strong> </li>
 * <ul>
 * <li>Concatenacion: Caracter &</li>
 * <li>Alternacion: Caracter |</li>
 * <li>Cerradura cero o mas: Se representa con los caracteres: '{' y '}'
 * Ejemplo: { <numero> }</li>
 * <li>Cerradura cero o uno: Se representa con los caracteres: '[' ']'
 * ejemplo[+|-] significa que se puede tomar el caracter + o - de manera
 * opcional</li>
 * <li>Definicion: Se representa por la secuencia de caracteres "::="</li>
 * <li>fin de produccion: Caracter ';'</li>
 * <li>fin de archivo: Caracter '.'</li>
 * </ul>
 * <li><strong>Terminal:</strong> es Cualquier secuencia de Caracteres entre
 * apostrofes</li>
 * <li><strong>Sintaxis y Semantica</strong> La sintaxis y la semantica del
 * lenguaje se representan con la siguiente GLC</li>
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
 * Ejemplo <Entero> ::=
 * {{['+'|'-']&<Variable>&(['+'|'-'])&{<Variable2>}}&{['+'|'-'] & <Variable3>};
 * <Entero>'+''-'|]
 * &<variable1>}&'+''-'|]&<variable2>}}&'+''-'|]&<variable3>}::=
 *
 * Entrada por archivos Codigo funcional JAva Doc Pruebas(almenos 8 de mas de 5
 * lineas cada uno) Estandar de codificacion tecnica PRD
 *
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
    private static final int VARIABLEIZQ = '<';
    private static final int VARIABLEDER = '>';
    private static final int PAR_DER = ')';
    private static final int PAR_IZQ = '(';
    private static int TERMINAL = 800;
    private static final String ASIGNACIONABSOLUTA = "::=";
    private static final int ASIGNACION = 600; //String.format("%s%s%s", (char) ASIGNACIONDOSPUNTOS, (char) ASIGNACIONDOSPUNTOS, (char) ASIGNACIONIGUAL);
    private Token currentToken;
    private String salida = "";
    private static Integer linea = 1;
    private StringTokenizer tokenizer = null;
    private int VARIABLE = 500;

    //TODO checar este javadoc
    /**
     * Tokenizer para obtener los tokens del codigo fuente
     *
     * @param codigoFuente que se leera mediante un archivo
     * @return codigo fuente sin espacios?
     */
    private StringTokenizer getTokenizer(String codigoFuente) {
        if (this.tokenizer == null) {
            //;&|{}[]:;=.'<>()
            String alfabetoSimbolos = String.format("%s%s%s%s%s%s%s%s%s%s%s%s%s%s%s",
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
                    (char) APOSTROFES,
                    (char) VARIABLEIZQ,
                    (char) VARIABLEDER,
                    (char) PAR_DER,
                    (char) PAR_IZQ
            );
            this.tokenizer = new StringTokenizer(codigoFuente.trim(), alfabetoSimbolos, true);
        }
        return this.tokenizer;
    }

    public void parser() {
        this.currentToken = lexer();
        prog();
        if (!(this.currentToken.getToken() == EOF)) {
            System.out.println(String.format("Resultado: %s \n "
                    + "El programa ha compilado correctamente.", this.salida));
        }
    }

    //listo
    public void prog() {
        conjProds();
    }

    public void conjProds() {
        prod();

    }

    public void prod() {
        if (this.currentToken.getToken() == VARIABLEIZQ) {
            this.salida = String.format("%s%s", this.salida,
                    this.currentToken.getLexema());
            this.currentToken = lexer();
            if (this.currentToken.getToken() == VARIABLE) {
                this.salida = String.format("%s%s", this.salida,
                        this.currentToken.getLexema());
                this.currentToken = lexer();
                if (this.currentToken.getToken() == VARIABLEDER) {
                    this.salida = String.format("%s%s", this.salida,
                            this.currentToken.getLexema());
                    this.currentToken = lexer();
                    if (this.currentToken.getToken() == 600) {
                        this.salida = String.format("%s%s", this.salida,
                                this.currentToken.getLexema());
                        this.currentToken = lexer();
                        expr();
                        if (this.currentToken.getToken() == FIN_SENT) {
                            this.salida = String.format("%s%s", this.salida, this.currentToken.getLexema());
                        } else {
                            throw new Error("Error de sintaxis: Se esperaba ;");
                        }
                    } else {
                        throw new Error("Error de sintaxis 1");
                    }

                } else {
                    throw new Error("Error de sintaxis 2");
                }
            } else {
                throw new Error("Error de sintaxis 3");
            }
        } else {
            throw new Error("Error de sintaxis 4");
        }

    }

    public void expr() {
        term();
        if (this.currentToken.getToken() == ALTERNACION) {
            this.salida = String.format("%s%s", this.salida, this.currentToken.getLexema());
            this.currentToken = lexer();
            term();
        }

    }

    public void term() {
        factor();
        if (this.currentToken.getToken() == CONCATENACION) {
            this.salida = String.format("%s%s", this.salida, this.currentToken.getLexema());
            this.currentToken = lexer();
            term();
           factor();
            
        }
    }

    public void factor() {
        primario();
        while (this.currentToken.getToken() == CERRADURA_CERO_MAS_IZQ || this.currentToken.getToken() == CERRADURA_CERO_UNO_IZQ) {
            if (this.currentToken.getToken() == CERRADURA_CERO_MAS_IZQ) {
                this.salida = String.format("%s%s", this.salida, this.currentToken.getLexema());
                this.currentToken = lexer();
                expr();
                if (this.currentToken.getToken() == CERRADURA_CERO_MAS_DER) {
                    this.salida = String.format("%s%s", this.salida, this.currentToken.getLexema());
                    this.currentToken = lexer();
                } else {
                    //throw new Error("Error yolo");
                }
            } else {
                if (this.currentToken.getToken() == CERRADURA_CERO_UNO_IZQ) {
                    this.salida = String.format("%s%s", this.salida, this.currentToken.getLexema());
                    this.currentToken = lexer();
                    expr();
                    if (this.currentToken.getToken() == CERRADURA_CERO_UNO_DER) {
                        this.salida = String.format("%s%s", this.salida, this.currentToken.getLexema());
                        this.currentToken = lexer();
                    } else {
                       // throw new Error("Error popo");
                    }

                }
            }
        }
    }

    public void primario() {
        if (this.currentToken.getToken() == TERMINAL) {
            this.salida = String.format("%s%s", this.salida,
                    this.currentToken.getLexema());
            this.currentToken = lexer();
        } else {
            if (this.currentToken.getToken() == PAR_IZQ) {
                this.salida = String.format("%s%s", this.salida,
                        this.currentToken.getLexema());
                this.currentToken = lexer();
                expr();
                if (this.currentToken.getToken() == PAR_DER) {
                    this.salida = String.format("%s%s", this.salida,
                            this.currentToken.getLexema());
                    this.currentToken = lexer();
                }
            } else {
                if (this.currentToken.getToken() == VARIABLEIZQ) {
                    this.salida = String.format("%s%s", this.salida,
                            this.currentToken.getLexema());
                    this.currentToken = lexer();
                    if (this.currentToken.getToken() == VARIABLE) {
                        this.salida = String.format("%s%s", this.salida,
                                this.currentToken.getLexema());
                        this.currentToken = lexer();
                        if (this.currentToken.getToken() == VARIABLEDER) {
                            this.salida = String.format("%s%s", this.salida,
                                    this.currentToken.getLexema());
                            this.currentToken = lexer();
                        } else {
                            throw new Error("Error Error Error");
                        }
                    }
                }
            }
        }
    }

    private Token lexer() {
        Token token = null;
        if (this.getTokenizer("").hasMoreTokens()) {
            String currentToken = this.getTokenizer("").nextToken();

            // Por si necesitamos agrupar literalmente toda la declaracion de variable
//             if (currentToken.charAt(0) == (char) VARIABLEIZQ) {
//                currentToken = this.getTokenizer("").nextToken();
//                if (isVariable(currentToken)) {
//                    token = new Token(CompiladorSoloPaso.linea, VARIABLE,currentToken);
//                    currentToken = this.getTokenizer("").nextToken();
//                    if (currentToken.charAt(0) == (char) VARIABLEDER) {
//                        token = new Token(CompiladorSoloPaso.linea, VARIABLE, currentToken);
//                        
//                    }
//                    
//                }
//            }
            if (isVariable(currentToken)) {
                token = new Token(CompiladorSoloPaso.linea, VARIABLE, currentToken);
            } else {
//                if(isTerminal(currentToken)){
//                    token = new Token(CompiladorSoloPaso.linea, TERMINAL, currentToken);
//                } else{
                int tokenSimple = currentToken.charAt(0);
                switch (tokenSimple) {
                    case FIN_SENT:
                        token = new Token(CompiladorSoloPaso.linea,
                                FIN_SENT,
                                String.format("%s", (char) tokenSimple));
                        linea++;
                        break;
                    case CONCATENACION:
                        token = new Token(CompiladorSoloPaso.linea,
                                CONCATENACION,
                                String.format("%s", (char) tokenSimple));
                        break;
                    case ALTERNACION:
                        token = new Token(CompiladorSoloPaso.linea,
                                ALTERNACION,
                                String.format("%s", (char) tokenSimple));
                        break;
                    case CERRADURA_CERO_MAS_DER:
                        token = new Token(CompiladorSoloPaso.linea,
                                CERRADURA_CERO_MAS_DER,
                                String.format("%s", (char) tokenSimple));
                        break;
                    case CERRADURA_CERO_MAS_IZQ:
                        token = new Token(CompiladorSoloPaso.linea,
                                CERRADURA_CERO_MAS_IZQ,
                                String.format("%s", (char) tokenSimple));
                        break;
                    case CERRADURA_CERO_UNO_DER:
                        token = new Token(CompiladorSoloPaso.linea,
                                CERRADURA_CERO_UNO_DER,
                                String.format("%s", (char) tokenSimple));
                        break;
                    case CERRADURA_CERO_UNO_IZQ:
                        token = new Token(CompiladorSoloPaso.linea,
                                CERRADURA_CERO_UNO_IZQ, String.format("%s",
                                        (char) tokenSimple));
                        break;

                    case VARIABLEIZQ:
                        token = new Token(CompiladorSoloPaso.linea,
                                VARIABLEIZQ, String.format("%s",
                                        (char) tokenSimple));
                        break;

                    case ASIGNACIONDOSPUNTOS:
                        currentToken = this.getTokenizer("").nextToken();
                        if (currentToken.charAt(0) == (char) ASIGNACIONDOSPUNTOS) {
                            currentToken = this.getTokenizer("").nextToken();
                            if (currentToken.charAt(0) == (char) ASIGNACIONIGUAL) {
                                token = new Token(this.linea, ASIGNACION,
                                        String.format("%s", ASIGNACIONABSOLUTA));
                            } else {
                                throw new Error("Error de Sintaxis: El caracter no "
                                        + "esta dentro del alfabeto");
                            }
                        } else {
                            throw new Error("Error de Sintaxis: El caracter no "
                                    + "esta dentro del alfabeto");
                        }
                        break;
                    case ASIGNACIONIGUAL:
                        token = new Token(CompiladorSoloPaso.linea, ASIGNACIONIGUAL,
                                String.format("%s", (char) tokenSimple));
                        break;

                    case VARIABLEDER:
                        token = new Token(CompiladorSoloPaso.linea,
                                VARIABLEDER, String.format("%s",
                                        (char) tokenSimple));
                        break;

                    case APOSTROFES:
                        String extra = "";
                        currentToken = this.getTokenizer("").nextToken();
                        while (currentToken.charAt(0) != (char) APOSTROFES) {

                            extra = extra + currentToken.charAt(0);
                            currentToken = this.getTokenizer("").nextToken();
                        }

                        token = new Token(CompiladorSoloPaso.linea, TERMINAL,
                                String.format("%s%s%s", (char) tokenSimple, extra, (char) tokenSimple));

                        break;
                    case PAR_DER:
                        token = new Token(CompiladorSoloPaso.linea, PAR_DER,
                                String.format("%s", (char) tokenSimple));
                        break;
                    case PAR_IZQ:
                        token = new Token(CompiladorSoloPaso.linea, PAR_IZQ,
                                String.format("%s", (char) tokenSimple));
                        break;
                    case EOF:
                        token = new Token(CompiladorSoloPaso.linea, EOF,
                                String.format("%s", (char) tokenSimple));
                    default:
                        throw new Error("Error de Lexico:"
                                + " El caracter no esta dentro del alfabeto");
                }

                //}//--
            }
        } else {
            token = new Token(this.linea, EOF, ".");
        }
        return token;
    }

    public static Boolean isVariable(String textoRevisar) {
        Boolean isVariable = false;
        if (Character.isAlphabetic(textoRevisar.charAt(0))) {
            for (int i = 1; i < textoRevisar.length(); i++) {
                isVariable = Character.isDigit(textoRevisar.charAt(i))
                        || Character.isAlphabetic(textoRevisar.charAt(i));
            }

        }

        return isVariable;
    }

    public static Boolean isTerminal(String textoRevisar) {
        Boolean isTerminal = false;
        if (textoRevisar.charAt(0) == (char) APOSTROFES) {

            System.out.println(textoRevisar);
            for (int i = 0; i < textoRevisar.length(); i++) {
                isTerminal = isVariable(textoRevisar);
            }
        }
        return isTerminal;

    }

    public static void main(String... args) {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        analizador.getTokenizer("<Entero>::=".trim()
                + "{{['+'|'-']&<Variable>&(['+'|'-'])&{<Variable2>}}&{['+'|'-']&<Variable3>};".trim()).hasMoreTokens();
        analizador.parser();

    }

}
