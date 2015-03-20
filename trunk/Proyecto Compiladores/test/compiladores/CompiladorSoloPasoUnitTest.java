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
    
    @Test(expected = Exception.class)
    public void FileNotFoundTest() throws IOException {

        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        analizador.readFile("FileTooInspec");
    }
    
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
    
    
    
    @Test
    public void testCerraduraDerecha() throws IOException{
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Variable1>::={(<Variable3>)}&{(<Variable2>)};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test(expected = Error.class)
    public void testCerraduraDerechaError() throws IOException{
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = readFile("FileToInspect");
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test
    public void testCerraduraIzquierda() throws IOException{
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Variable1>::={(<Variable3>)}&{(<Variable2>)};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test(expected = Error.class)
    public void testCerraduraIzquierdaError() throws IOException{
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Variable1>::=<Variable3>)}&{(<Variable2>)};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test
    public void testInicioFinVariable() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::=<Variable>;";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test(expected = Error.class)
    public void testInicioFinVariableError() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::=<Variable;";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test
    public void testVariableEmpiezaLetra() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::=<Variable>;";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test(expected = Error.class)
    public void testVariableEmpiezaLetraError() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::=<5ariable;";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
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
