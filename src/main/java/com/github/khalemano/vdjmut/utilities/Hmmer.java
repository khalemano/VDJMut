package com.github.khalemano.vdjmut.utilities;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalani
 */
public class Hmmer {
    
    /**
     * Finds the 1-based indexes for the start and ends of the four framework
     * regions
     * 
     * @param seq A string of [ATCG]s
     * @return Array of 8 1-based index values for the start and ends
     * of each framework region
     * 
     */
    public static int[] findFrameworkRegions(String seq){
        
        int[] results = new int[8];
        
        //Construct hmmscan command
        String command = 
                // call to hmmscan
                "/home/kejun/MiSeq/Reference_databases/hmm_databases/" +
                "hmmer-3.1b1-linux-intel-x86_64/binaries/hmmscan " +
                //--domE sets a threshold for the domain E score
                "--domE 0.01 " + 
                //this sets the location of the hmm database
                "/home/kejun/MiSeq/Reference_databases/hmm_databases/FWR.hmm " + 
                //this sets the input to stdin
                "-";
//                "/home/kejun/MiSeq/Reference_databases/hmm_databases/test.fasta";
        
        try{
            //Open a process to execute the hmmscan command
            Process p = Runtime.getRuntime().exec(command);
            
            //Creates a BufferedWriter to output the sequence string to hmmscan
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                p.getOutputStream()));
            
            //Creates BufferedReader to accept the output of hmmscan
            BufferedReader err = new BufferedReader(
                new InputStreamReader(p.getErrorStream()));            
            
            //Creates BufferedReader to accept the output of hmmscan
            BufferedReader in = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            
            //sends stdin to hmmscan
            seq = ">test\n" + seq;
            out.write(seq);
            out.flush();
            out.close();

            
            //reads stdout from hmmscan; stdout is in the form of a 
            // human-readable table, so it must be parsed with spaces
            //rather than tabs or commas

            String line;

            while ((line = in.readLine()) != null){
                //find segments with indexes for the four framework regions
                if(line.startsWith(">> FWR")){
                    String FWR = line.substring(3, 7);
                    
                    int indexStart =0;
                    int indexEnd = 0;
                    
                    switch (FWR) {
                        case "FWR1":
                            indexStart = 0;
                            indexEnd = 1;
                            break;
                        case "FWR2":
                            indexStart = 2;
                            indexEnd = 3;
                            break;
                        case "FWR3":
                            indexStart = 4;
                            indexEnd = 5;
                            break;
                        case "FWR4":
                            indexStart = 6;
                            indexEnd = 7;
                            break;
                    }
                    
                    in.readLine();
                    in.readLine();
                    String line2 = in.readLine();
                    
                    String[] data = line2.split("\\s+");
                    results[indexStart] = Integer.valueOf(data[13]);
                    results[indexEnd] = Integer.valueOf(data[14]);
                }
                
            }
            
        } catch (IOException e){
            System.out.println(e);
        }
        
        return results;
        
    }// End of method FindFrameworkRegions
}//End of class Hmmer
