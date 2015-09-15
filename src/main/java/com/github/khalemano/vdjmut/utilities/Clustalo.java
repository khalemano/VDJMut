package com.github.khalemano.vdjmut.utilities;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalani
 */
public class Clustalo {
    public static ArrayList<String> Alignment(String... seqs){
         ArrayList<String> results= new ArrayList<>();
        String command = "clustalo -i - --wrap=1000 --output-order input-order";

        try{
            //Open a process to execute the blastn command
            Process p = Runtime.getRuntime().exec(command);
            
            //Creates a BufferedWriter to output the sequence string to blastn
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                p.getOutputStream()));
            
            //Creates BufferedReader to accept the error stream of blastn
            BufferedReader err = new BufferedReader(
                new InputStreamReader(p.getErrorStream()));            
            
            //Creates BufferedReader to accept the output of blastn
            BufferedReader in = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            
            //sends stdin to blastn
            String fasta="";
            int i = 0;
            for (String seq : seqs){
                fasta += ">" + i + "\n" + seq.toUpperCase() + "\n";
                i++;
            }
            
            out.write(fasta);
            out.flush();
            out.close();
            
            while (in.readLine() != null){
                results.add(in.readLine());
            }
        } catch (IOException e){
            System.out.println(e);
        }
        
        return results;
    }
}
