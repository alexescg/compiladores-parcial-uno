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
 *  Clase que hace los test del compilador de un solo paso 
 * @author Alejando Escobedo Garcia, Erik David Zubia Hernandez
 * @version 1.0
 * @since 19/Mar/2015
 */
public class CompiladorSoloPasoUnitTest {
    
    /**
     * Prueba que verifica el error de escribir mal el nombre del archivo
     * @throws IOException cuando no existe el archivo con el nombre dado
     */
    @Test(expected = Exception.class)
    public void FileNotFoundTest() throws IOException {

        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        analizador.readFile("FileTooInspec");
    }
    
    /**
     * Prueba que verifica que el nombre del archivo este correctamente escrito
     * así como que exista y este no este vacio.
     * @throws IOException cuando no existe el archivo con el nombre dado
     */
    @Test
    public void FileFoundTest() throws IOException {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = analizador.readFile("FileToInspect");
        assert(codigo != "");
    }

    @Test
    public void procesarExpresion() throws IOException{
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = readFile("codigoFuente");
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    
    /**
     * Prueba que verifica que las llaves "()" y "{]" tengan su cierre al abrir
     * @throws IOException cuando no encuentra la llave que cierra con el par
     */
    @Test
    public void testCerraduraDerecha() throws IOException{
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Variable1>::={(<Variable3>)}&{(<Variable2>)};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    /**
     * Prueba que verifica el error de dejar una llave sin su par
     * @throws IOException cuando no encuentra la llave que cierra con el par
     */
    @Test(expected = Error.class)
    public void testCerraduraDerechaError() throws IOException{
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = readFile("FileToInspect");
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    /**
     * Prueba que verifica que las llaves "()" y "{]" tengan una apertura antes
     * de cerrarlo del lado derecho
     * @throws IOException cuando no encuentra la llave que abre con el par
     */
    @Test
    public void testCerraduraIzquierda() throws IOException{
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Variable1>::={(<Variable3>)}&{(<Variable2>)};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    /**
     * Prueba que verifica el error de dejar una llave sin abrir pero cerrandola
     * mas adelante en la expreción
     * @throws IOException cuando no encuentra la llave que cierra con el par
     */
    @Test(expected = Error.class)
    public void testCerraduraIzquierdaError() throws IOException{
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Variable1>::=<Variable3>)}&{(<Variable2>)};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    /**
     * Prueba que verifica los inicios y cierres de variable entre el diamante (<>)
     * @throws IOException cuando no encuentra un inicio de variable o cierre 
     * de variable
     */
    @Test
    public void testInicioFinVariable() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::=<Variable>;";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    /**
     * Prueba que verifica el fallo de los inicios de variable entre 
     * el diamante (<>)
     * @throws IOException cuando no encuentra un inicio de variable o cierre 
     * de variable
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
     * comienze con una letra sin importar lo siguiente a esa letra
     * @throws IOException cuando no encuentra una letra despues de la apertura
     * de variable
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
     * apertura de una variable
     * @throws IOException cuando no encuentra una letra despues de la apertura
     * de variable
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
     * escrito así como que aparesca despues de una variable
     * @throws IOException cuando no esta bien escrito o no se encuentra despues
     * de una variable
     */
    @Test
    public void testAsignacion() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::={{['+'|'-']&<Variable>}};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test(expected = Error.class)
    public void testAsignacionError() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>:={{['+'|'-']&<Variable>}};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test
    public void testConcatenacion(){
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::={{<Variable1>&<Variable>&<Variable33>}};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test(expected = Error.class)
    public void testConcatenacionError(){
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::={{<Variable1><Variable>&<Variable33>}};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test
    public void testAlternacion(){
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::={{['+'|'-']&<Variable>}};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test(expected = Error.class)
    public void testAlternacionError(){
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::={{['+''-']&<Variable>}};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }

    
}
