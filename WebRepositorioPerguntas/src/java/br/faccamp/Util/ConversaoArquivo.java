/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package br.faccamp.Util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

/**
 *
 * @author Edu - Notebook
 */
public class ConversaoArquivo {
    
    public byte[] getBytes(File file) 
    {  
        int len = (int)file.length();    
        byte[] sendBuf = new byte[len];  
        FileInputStream inFile  = null;  
        
        try 
        {  
            inFile = new FileInputStream(file);           
            inFile.read(sendBuf, 0, len);    
          
        } 
        catch (FileNotFoundException fnfex) 
        {  
             
        } 
        catch (IOException ioex) 
        {  
  
        }  
        return sendBuf;  
    }
    
    public File getFile(byte[] binario,String extensao,String Diretorio,String Nome) throws IOException
    {
        FileOutputStream fileOutput = new FileOutputStream(Diretorio + "/" + Nome + "." + extensao );
        
        fileOutput.write(binario);
        fileOutput.close();
        
        return new File (Diretorio + "/" + Nome + "." + extensao);
        
    }
    
    
    public File SalvarArquivoEmDisco(String Diretorio, String NomeArquivo,InputStream imputstream)
            throws IOException
    {
        File file = new File(Diretorio + "/" + NomeArquivo);
        
        BufferedInputStream bufferedInputStream = null;  
        FileOutputStream fileOutputStream = null;
        
        // Salvandfileo o arquivo  
       
        bufferedInputStream = new BufferedInputStream(imputstream);  
        fileOutputStream = new FileOutputStream(file);  
        byte[] buffer = new byte[1024];  
        int count;  
        
        while ((count = bufferedInputStream.read(buffer)) > 0)  
             fileOutputStream.write(buffer, 0, count);  
        
        return file;
    }
    
}
