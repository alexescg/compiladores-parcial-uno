/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package compiladores;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import static org.junit.Assert.*;
import org.junit.Test;
import proyecto.compiladores.CompiladorSoloPaso;
import static proyecto.compiladores.CompiladorSoloPaso.readFile;

/**
 *
 * @author alex
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
    
    public void testCerradura() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "{};";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test(expected = Error.class)
    public void errorCerraduraDerecha() throws IOException{
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = readFile("FileToInspect");
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test
    public void testVariable() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::=<Variable>;";
        analizador.getTokenizer(codigo.trim()).hasMoreTokens();
        analizador.parser();
    }
    
    @Test(expected = Error.class)
    public void testVariableError() {
        CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = "<Entero>::=<Variable;";
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

    
}
