/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.util.StringTokenizer;
import static org.junit.Assert.*;
import org.junit.Before;
import org.junit.Test;
import proyecto.compiladores.CompiladorSoloPaso;
import static proyecto.compiladores.CompiladorSoloPaso.readFile;

/**
 * Clase que hace los test del compilador de un solo paso
 *
 * @author Alejando Escobedo Garcia, Erik David Zubia Hernandez
 * @version 1.0
 * @since 19/Mar/2015
 */
public class CompiladorSoloPasoUnitTest {

    /**
     * Prueba que verifica el error de escribir mal el nombre del archivo
     *
     * @throws IOException cuando no existe el archivo con el nombre dado
     */
    @Test(expected = Exception.class)
    public void FileNotFoundTest() throws IOException {

        CompiladorSoloPaso.readFile("FileTooInspec");
    }

    /**
     * Prueba que verifica que el nombre del archivo este correctamente escrito
     * así como que exista y este no este vacio.
     *
     * @throws IOException cuando no existe el archivo con el nombre dado
     */
    @Test
    public void FileFoundTest() throws IOException {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = analizador.readFile("FileToInspect");
        assert (!"".equals(codigo));
    }

    /**
     * Prueba que verifica si se puede compilar una expresion valida dentro de
     * un archivo
     *
     * @throws IOException cuando el archivo no es encontrado
     */
    @Test
    public void procesarExpresion() throws IOException {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = readFile("codigoFuente");
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica que las llaves "()" y "{]" tengan su cierre al abrir.
     *
     */
    @Test
    public void testCerraduraDerecha() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Variable1>::={(<Variable3>)}&{(<Variable2>)};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica el error de dejar una llave sin su par, mientras se
     * extrae el codigo de un archivo.
     *
     * @throws IOException cuando no encuentra el archivo a leer
     */
    @Test(expected = Error.class)
    public void testCerraduraDerechaError() throws IOException {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = readFile("FileToInspect");
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica que las llaves "()" y "{]" tengan una apertura antes
     * de cerrarlo del lado derecho.
     *
     */
    @Test
    public void testCerraduraIzquierda() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Variable1>::={(<Variable3>)}&{(<Variable2>)};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica el error de dejar una llave sin abrir pero cerrandola
     * mas adelante en la expresión.
     *
     */
    @Test(expected = Error.class)
    public void testCerraduraIzquierdaError() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Variable1>::=<Variable3>)}&{(<Variable2>)};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica los inicios y cierres de variable entre el diamante
     * (<>).
     *
     */
    @Test
    public void testInicioFinVariable() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::=<Variable>;";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica el fallo de los inicios de variable entre el diamante
     * (<>).
     *
     */
    @Test(expected = Error.class)
    public void testInicioFinVariableError() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::=<Variable;";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica que despues de la apertura de la variable esta
     * comienze con una letra sin importar lo siguiente a esa letra.
     *
     */
    @Test
    public void testVariableEmpiezaLetra() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::=<Variable>;";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica que el fallo de poner un digito despues de la
     * apertura de una variable.
     * 
     */
    @Test(expected = Error.class)
    public void testVariableEmpiezaLetraError() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::=<5ariable;";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica que el simbolo de asignación (::=) este correctamente
     * escrito así como que este se encuentre despues de una variable.
     *
     */
    @Test
    public void testAsignacion() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::={{['+'|'-']&<Variable>}};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica si el token de asignacion es mal utilizado.
     * 
     */
    @Test(expected = Error.class)
    public void testAsignacionError() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>:={{['+'|'-']&<Variable>}};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica que el token de concatenacion sea utilizado de manera
     * adecuada.
     * 
     */
    @Test
    public void testConcatenacion() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::={{<Variable1>&<Variable>&<Variable33>}};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica que cuando el token de concatenacion sea utilizado de
     * manera erronea, mande un error.
     * 
     */
    @Test(expected = Error.class)
    public void testConcatenacionError() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::={{<Variable1><Variable>&<Variable33>}};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica si se esta utilizando de manera correcta el token de
     * alternacion.
     * 
     */
    @Test
    public void testAlternacion() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::={{['+'|'-']&<Variable>}};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    /**
     * Prueba que verifica si el token de alternacion es utilizado de manera
     * incorrecta y manda un error en caso de que sea el caso.
     * 
     */
    @Test(expected = Error.class)
    public void testAlternacionError() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::={{['+''-']&<Variable>}};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

}
