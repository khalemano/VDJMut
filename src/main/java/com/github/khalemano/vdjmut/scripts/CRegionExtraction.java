/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khalemano.vdjmut.scripts;


import com.github.khalemano.vdjmut.utilities.FastaFile;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author kalani
 */
public class CRegionExtraction {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String filepath = "/home/kejun/MiSeq/AIDKO/UniqueFastas/";
        String outputStr = "/home/kejun/MiSeq/AIDKO/cregionalignments/";
        String fastapair = "/home/kejun/MiSeq/AIDKO/Fastapairs/";
        ArrayList<String> samples = new ArrayList<>();
        
        samples.add("604N");
        samples.add("604R");
        samples.add("958N");
        samples.add("958R");
        samples.add("605N");
        samples.add("605R");
        samples.add("220N");
        samples.add("220R");
        samples.add("957N");
        samples.add("789N");
        samples.add("789R");
        samples.add("953N");
        samples.add("489N");
        samples.add("489R");
        samples.add("952N");
        samples.add("841L");
        samples.add("841B");
        samples.add("431R");
        samples.add("230N");
        samples.add("230R");
        samples.add("230L");
        samples.add("841N");
        samples.add("841R");
        samples.add("431N");
        samples.add("705N");
        samples.add("705R");
        samples.add("705L");
        samples.add("293N");
        samples.add("293R");
        samples.add("294N");
        
        for (String s : samples){
            run(filepath,outputStr,fastapair,s);
        }
    }
    
    public static int getMismatches(String str1, String str2){
        char[] ca1 = str1.toCharArray();
        char[] ca2 = str2.toCharArray();
        
        int mismatches = 0;
        
        for (int i=0; i<ca1.length; i++){
            if (ca1[i] != ca2[i]) mismatches++;
        }
        
        return mismatches;
    }
    
    public static void run(String filepath, String outputStr,String fastapair, String sample){
        
        HashMap<String,String> fp = new HashMap();
        
        try(BufferedReader data = new BufferedReader(new FileReader(fastapair + sample))){
            String line1 = "";
            String line3 = "";
            
            while (data.ready()){
                try{
                   line1 = data.readLine();
                   data.readLine();
                   line3 = data.readLine();
                   data.readLine();
                   data.readLine();
                } catch (IOException e){
                    System.out.println(e);
                }

                if (line1 != null && line1.length() != 0 ){
                    fp.put(line1, line3);
                }
            }
        } catch (IOException e){
            System.out.println(e);
        }
        
        
        
        Path output = Paths.get(outputStr + sample);
        String mstr30 = "GAGAGTCAGTCCTTCCCAAATGTCTTCCCC";
        String mstr31 = "GAGAGTCAGTCCTTCCCAAATGTCTTCCCCC";
        String mstr32 = "GAGAGTCAGTCCTTCCCAAATGTCTTCCCCCT";
        String mstr29 = "GAGAGTCAGTCCTTCCCAAATGTCTTCCC";
        String mstr28 = "GAGAGTCAGTCCTTCCCAAATGTCTTCC";
        
        
        System.out.println("Sample: "+sample);
        try(BufferedWriter writer = 
            Files.newBufferedWriter(output,StandardCharsets.UTF_8)){
        
            FastaFile fasta = new FastaFile(filepath + sample);
            int seq_ct = 0;
            int match_ct = 0;
            int miss_ct = 0;
            
            
            while (fasta.hasNext()){
                
                String seq = fasta.getCurrentSeq();
                if(!fp.containsKey(fasta.getCurrentDesc())) continue;

                seq_ct++;
                int mismatches;
                
                
                String last30 = seq.substring(seq.length()-30);
                mismatches = getMismatches(mstr30,last30);
                if (mismatches<15){
                    writer.write(fasta.getCurrentDesc());
                    writer.newLine();

                    writer.write(last30);
                    writer.newLine();

                    writer.write(fp.get(fasta.getCurrentDesc()));
                    writer.newLine();

                    writer.write(mstr30);
                    writer.newLine();
                    writer.newLine();
                    match_ct++;
                    continue;
                }
                
                String last31 = seq.substring(seq.length()-31);
                mismatches = getMismatches(mstr31,last31); 
                if (mismatches<15){
                    writer.write(fasta.getCurrentDesc());
                    writer.newLine();

                    writer.write(last31);
                    writer.newLine();

                    writer.write(fp.get(fasta.getCurrentDesc()));
                    writer.newLine();

                    writer.write(mstr31);
                    writer.newLine();
                    writer.newLine();
                    match_ct++;
                    continue;
                }         
                
                String last29 = seq.substring(seq.length()-29);
                mismatches = getMismatches(mstr29,last29); 
                if (mismatches<15){
                    writer.write(fasta.getCurrentDesc());
                    writer.newLine();

                    writer.write(last29);
                    writer.newLine();

                    writer.write(fp.get(fasta.getCurrentDesc()));
                    writer.newLine();

                    writer.write(mstr29);
                    writer.newLine();
                    writer.newLine();
                    match_ct++;
                    continue;
                }                 
                
                String last32 = seq.substring(seq.length()-32);
                mismatches = getMismatches(mstr32,last32); 
                if (mismatches<15){
                    writer.write(fasta.getCurrentDesc());
                    writer.newLine();

                    writer.write(last32);
                    writer.newLine();

                    writer.write(fp.get(fasta.getCurrentDesc()));
                    writer.newLine();

                    writer.write(mstr32);
                    writer.newLine();
                    writer.newLine();
                    match_ct++;
                    continue;
                } 
                
                String last28 = seq.substring(seq.length()-28);
                mismatches = getMismatches(mstr28,last28); 
                if (mismatches<15){
                    writer.write(fasta.getCurrentDesc());
                    writer.newLine();

                    writer.write(last28);
                    writer.newLine();

                    writer.write(fp.get(fasta.getCurrentDesc()));
                    writer.newLine();

                    writer.write(mstr28);
                    writer.newLine();
                    writer.newLine();
                    match_ct++;
                    continue;
                } 
                
                miss_ct++;
                System.out.println("Mismatches: "+ mismatches);
                System.out.println(last30);
                System.out.println(mstr30);
            }

            System.out.println(match_ct + "/" + seq_ct + " Matches!");
            System.out.println(miss_ct + "/" + seq_ct + " Misses!");
            
        }catch (IOException e){
            System.out.println(e);
        }
    }
}
