package com.github.khalemano.vdjmut.utilities;


import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalani
 */
public class FastapairFile implements AutoCloseable {
    private BufferedReader data;
    private Pattern queryPattern = Pattern.compile(">(\\w+)\\s+Cts=(\\d+)");
    private Pattern refPattern = Pattern.compile(">([\\w-\\*]+)");
    private Fastapair next;
    
    
    public FastapairFile(String fileName){
        try{
            data = new BufferedReader(new FileReader(fileName));
        } catch (IOException e){
            System.out.println(e);
        }
    }

    public boolean hasNext(){
       String line1 = "";
       String line2 = "";
       String line3 = "";
       String line4 = "";
       
       try{
           line1 = data.readLine();
           line2 = data.readLine();
           line3 = data.readLine();
           line4 = data.readLine();
           data.readLine();
       } catch (IOException e){
           System.out.println(e);
       }
       
       if (line1 != null && line1.length() != 0 ){
           
               next = new Fastapair();
               
                Matcher queryMatcher = queryPattern.matcher(line1);
                if (queryMatcher.find()){
                    next.setQueryName(queryMatcher.group(1));
                    next.setCount(Integer.parseInt(queryMatcher.group(2)));
                }
                
                next.setQuerySeq(line2);
                
                Matcher refMatcher = refPattern.matcher(line3);
                if (refMatcher.find()){
                    next.setRefName(refMatcher.group(1));
                }
    
                next.setRefSeq(line4);
                
                return true;
       }
       return false;
    }
            
    public Fastapair next(){
        return next;
    }
    
    
    @Override
    public void close() throws Exception {
        data.close();
    }
    
    
    
}
