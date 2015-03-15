/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import org.junit.Test;
import static org.junit.Assert.*;
import proyecto.compiladores.CompiladorSoloPaso;

/**
 *
 * @author alex
 */
public class CompiladorSoloPasoUnitTest {
    
    public CompiladorSoloPasoUnitTest() {
    }

    
    @Test(expected = Exception.class)
    public void FileNotFoundTest() throws IOException{
            
     CompiladorSoloPaso analizador = new CompiladorSoloPaso();
        String codigo = readFile("FileToInspec");
    }
    
    
    private static String readFile(String path) throws IOException {
        String codigoFuente = "";
        FileInputStream fis = new FileInputStream(path);

        BufferedReader br = new BufferedReader(new InputStreamReader(fis));
        String linea = null;
        while ((linea  = br.readLine()) != null) {
            codigoFuente += linea; 
        }
        br.close();
        return codigoFuente;
    }
    
     
}
