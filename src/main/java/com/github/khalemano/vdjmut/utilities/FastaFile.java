/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khalemano.vdjmut.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author kalani
 */
public class FastaFile implements AutoCloseable {

    private BufferedReader data;


    private String currentDesc;
    private String currentSeq;
    
    public FastaFile(String fileName){
        try{
            data = new BufferedReader(new FileReader(fileName));
        } catch (IOException e){
            System.out.println(e);
        }
    }
    
    
    public boolean hasNext(){
       String line1 = "";
       String line2 = "";
       
       try{
           line1 = data.readLine();
           line2 = data.readLine();
       } catch (IOException e){
           System.out.println(e);
       }
       
       if (line1 != null && line1.length() != 0 ){
           
                currentDesc = line1;
                currentSeq = line2.toUpperCase();
                
                return true;
       }
       return false;
    }
            
    public String getCurrentSeq(){
        return currentSeq;
    }
    
    public String getCurrentDesc(){
        return currentDesc;
    }
    
    @Override
    public void close() throws Exception {
        data.close();
    }   
    
}
