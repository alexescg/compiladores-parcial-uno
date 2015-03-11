/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package proyecto.compiladores;

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
 * @author alex
 */
public class CompiladorSoloPaso {
    
}
