/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khalemano.vdjmut.scripts;


import com.github.khalemano.vdjmut.utilities.Clustalo;
import com.github.khalemano.vdjmut.utilities.FastaDatabase;
import com.github.khalemano.vdjmut.utilities.Fastapair;
import com.github.khalemano.vdjmut.utilities.ScoreKeeper;
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
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kalani
 */
public class NewMutationAnalysis {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Path output = Paths.get("/home/kejun/MiSeq/AIDKO/VregCregMuts.csv");
        
        String suffix = "/home/kejun/MiSeq/AIDKO/UniqueFastas/";
        
        ArrayList<String> samples = new ArrayList<>();
        ArrayList<String> genotypes = new ArrayList<>();
        ArrayList<String> dpi = new ArrayList<>();
        
        samples.add("604N"); genotypes.add("AIDKO"); dpi.add("7dpi");
        samples.add("604R"); genotypes.add("AIDKO"); dpi.add("7dpi");
        samples.add("958N"); genotypes.add("DBKO"); dpi.add("7dpi");
        samples.add("958R"); genotypes.add("DBKO"); dpi.add("7dpi");
        samples.add("605N"); genotypes.add("AIDKO"); dpi.add("7dpi");
        samples.add("605R"); genotypes.add("AIDKO"); dpi.add("7dpi");
        samples.add("220N"); genotypes.add("AIDHET"); dpi.add("7dpi");
        samples.add("220R"); genotypes.add("AIDHET"); dpi.add("7dpi");
        samples.add("957N"); genotypes.add("DBKO"); dpi.add("7dpi");
        samples.add("789N"); genotypes.add("DBKO"); dpi.add("7dpi");
        samples.add("789R"); genotypes.add("DBKO"); dpi.add("7dpi");
        samples.add("953N"); genotypes.add("DBKO"); dpi.add("7dpi");
        samples.add("489N"); genotypes.add("AIDKO"); dpi.add("Uninf");
        samples.add("489R"); genotypes.add("AIDKO"); dpi.add("Uninf");
        samples.add("952N"); genotypes.add("AIDKO"); dpi.add("Uninf");
        samples.add("841L"); genotypes.add("DBKO"); dpi.add("Uninf");
        samples.add("941B"); genotypes.add("DBKO"); dpi.add("Uninf");
        samples.add("431R"); genotypes.add("DBKO"); dpi.add("Uninf");
        samples.add("230N"); genotypes.add("AIDHET"); dpi.add("28dpi");
        samples.add("230R"); genotypes.add("AIDKO"); dpi.add("28dpi");
        samples.add("230L"); genotypes.add("AIDHET"); dpi.add("28dpi");
        samples.add("841N"); genotypes.add("DBKO"); dpi.add("28dpi");
        samples.add("841R"); genotypes.add("DBKO"); dpi.add("28dpi");
        samples.add("431N"); genotypes.add("DBKO"); dpi.add("28dpi");
        samples.add("705N"); genotypes.add("AIDKO"); dpi.add("28dpi");
        samples.add("705R"); genotypes.add("AIDKO"); dpi.add("28dpi");
        samples.add("705L"); genotypes.add("AIDKO"); dpi.add("28dpi");
        samples.add("293N"); genotypes.add("DBKO"); dpi.add("28dpi");
        samples.add("293R"); genotypes.add("DBKO"); dpi.add("28dpi");
        samples.add("294N"); genotypes.add("DBKO"); dpi.add("28dpi");
                
        try(BufferedWriter writer = 
            Files.newBufferedWriter(output,StandardCharsets.UTF_8)){

            //Creates and prints the header of the csv table
            String header = "sample,genotype,dpi,ighv,cRegNts,cRegMuts,vRegNts,vRegMuts";
            writer.write(header);
            writer.newLine();
            
            for(int i = 0; i<samples.size(); i++){

                ArrayList<ScoreKeeper> scorekeepers = Analyze(suffix + samples.get(i),2);
                
                Set<String> rows = scorekeepers.get(0).getRowSet();
            
                for (String name : rows){
                    String line = samples.get(i) + ","
                            + genotypes.get(i) + "," 
                            + dpi.get(i) + ","
                            + name;
                    for (ScoreKeeper sk : scorekeepers){
                        line = line + "," + sk.getScore(name);
                    }
                    writer.write(line);
                    writer.newLine();
                }
            }
        }catch (IOException e){
            System.out.println(e);
        }
    }
    
    public static ArrayList<ScoreKeeper> Analyze(String path, int ctsThreshold){
        
System.out.println("Analyzing: " + path);
        
        ArrayList<ScoreKeeper> results = new ArrayList<>();
        
        ScoreKeeper cRegNts = new ScoreKeeper("cRegNts");
        ScoreKeeper cRegMuts = new ScoreKeeper("cRegMuts");
        ScoreKeeper vRegNts = new ScoreKeeper("vRegNts");
        ScoreKeeper vRegMuts = new ScoreKeeper("vRegMuts");
        
        Pattern queryPattern = Pattern.compile(">(\\w+)\\s+Cts=(\\d+)");
        FastaDatabase vhdb = new FastaDatabase(
            "/home/kejun/MiSeq/Reference_databases/" +
            "C57BL6_IMGT_VH_germline_shortID.fa");
        HashMap<String,String> ighcs = new HashMap<>();
        ighcs.put("IGHG1", "CCAAAACGACACCCCCATCTGTCTATCCA");
        ighcs.put("IGHG2c", "CCAAAACAACAGCCCCATCGGTCTATCCA");
        ighcs.put("IGHG2b", "CCAAAACAACACCCCCATCAGTCTATCCA");
        ighcs.put("IGHG3", "CTACAACAACAGCCCCATCTGTCTATCCC");
        ighcs.put("IGHM", "AGAGTCAGTCCTTCCCAAATGTCTTCCCC");
                
        try(BufferedReader fasta = new BufferedReader(new FileReader(path))){
            String line1, line2;
            line1 = fasta.readLine();
            line2 = fasta.readLine();
            
            while (line1 != null && 
                    line1.length() != 0 &&
                    line2 != null && 
                    line2.length() != 0){
                
                int cts=0;
                String name="";
                
                String seq = line2.trim().toUpperCase();
                
                Matcher queryMatcher = queryPattern.matcher(line1);
                if (queryMatcher.find()){
                    name = queryMatcher.group(1);
                    cts = Integer.parseInt(queryMatcher.group(2));
                }
                
                //check if the number of sequence counts meets theshold
                if (cts < ctsThreshold){
                    continue;
                }

//System.out.println("name: " + name);
//System.out.println("seq: " + seq);
//                String ighv = Blast.findIGHV(seq);
//                String ighvSeq = vhdb.getSequence(ighv);
                ArrayList<String> cAlignment = Clustalo.Alignment(
                        seq,ighcs.get("IGHM"));
                
                Fastapair cPair = new Fastapair();
                cPair.setQuerySeq(cAlignment.get(0));
                cPair.setRefSeq(cAlignment.get(1));
                
                while (cPair.hasNextNtPair()){
                    char[] x = cPair.nextPair();
                    cRegNts.addToScore("TEST", 1);
                    if (x[0] != x[1]){
                        cRegMuts.addToScore("TEST", 1);
                    }
                }
                
//                ArrayList<String> vAlignment = Clustalo.Alignment(seq,ighvSeq);
//                
//                Fastapair vPair = new Fastapair();
//                vPair.setQuerySeq(vAlignment.get(0));
//                vPair.setRefSeq(vAlignment.get(1));
//                
//                while (vPair.hasNextNtPair()){
//                    char[] x = vPair.nextPair();
//                    vRegNts.addToScore(ighv, 1);
//                    if(x[0] != x[1]){
//                        cRegMuts.addToScore(ighv, 1);
//                    }
//                }
                line1 = fasta.readLine();
                line2 = fasta.readLine();
            }
            
        }catch(IOException e){
            System.out.print(e);
        }
        
        //load the return array with scorekeepers
        results.add(cRegNts);
        results.add(cRegMuts);
        results.add(vRegNts);
        results.add(vRegMuts);
        
        return results;
    }
    
    
}
