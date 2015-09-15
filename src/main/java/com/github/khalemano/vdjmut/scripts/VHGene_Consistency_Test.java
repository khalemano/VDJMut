/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khalemano.vdjmut.scripts;


import com.github.khalemano.vdjmut.utilities.Fastapair;
import com.github.khalemano.vdjmut.utilities.FastapairFile;
import com.github.khalemano.vdjmut.utilities.ScoreKeeper;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;

/**
 *
 * @author kalani
 */
public class VHGene_Consistency_Test {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
                Path output = Paths.get("/home/kejun/MiSeq/VHGene_Consistency_Test/A3KO_seqcts.csv");
        
//        String suffix = "/home/kejun/MiSeq/AIDKO/Fastapairs/";
        String suffix = "/home/kejun/MiSeq/VHGene_Consistency_Test/fastapair/";
        
        ArrayList<String> samples = new ArrayList<>();
        ArrayList<String> runs = new ArrayList<>();

        samples.add("A3KO1-DG"); runs.add("Run2");
        samples.add("A3KO1-DG"); runs.add("Run3");
        samples.add("A3KO1-DG"); runs.add("Run5");
        samples.add("A3KO1-DG"); runs.add("Run6");
        samples.add("A3KO1-DG"); runs.add("Run8");
        samples.add("A3KO2-DG"); runs.add("Run2");
        samples.add("A3KO2-DG"); runs.add("Run3");
        samples.add("A3KO2-DG"); runs.add("Run5");
        samples.add("A3KO2-DG"); runs.add("Run6");
        samples.add("A3KO2-DG"); runs.add("Run8");
        samples.add("A3KO3-DG"); runs.add("Run2");
        samples.add("A3KO3-DG"); runs.add("Run3");
        samples.add("A3KO3-DG"); runs.add("Run5");
        samples.add("A3KO3-DG"); runs.add("Run6");
        samples.add("A3KO3-DG"); runs.add("Run8");

        try(BufferedWriter writer = 
            Files.newBufferedWriter(output,StandardCharsets.UTF_8)){

            //Creates and prints the header of the csv table
            String header = "mouse,run,ighv,seq_cts,total";
            writer.write(header);
            writer.newLine();
            
            for(int i = 0; i<samples.size(); i++){

                ArrayList<ScoreKeeper> scorekeepers = Analyze(suffix + samples.get(i) + "-" + runs.get(i) + ".fasta");
                
                Set<String> rows = scorekeepers.get(0).getRowSet();
            
                for (String name : rows){
                    String line = samples.get(i) + ","
                            + runs.get(i) + "," 
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
    

    public static ArrayList<ScoreKeeper> Analyze(String path) {
        ArrayList<ScoreKeeper> results = new ArrayList<>();

        System.out.println("Analyzing: " + path);

        ScoreKeeper seq_cts = new ScoreKeeper("seq_cts");
        ScoreKeeper total = new ScoreKeeper("total");
        int total_cts = 0;        
        
        try (FastapairFile file = new FastapairFile(path)) {

            while (file.hasNext()) {
                Fastapair seqs = file.next();
                
                //gets the count of the unique sequence
                int cts = seqs.getCount();
                //adds the count to the total
                total_cts += cts;
                
                //Obtains name of reference sequence
                String IGHV = seqs.getRefName();

                //Adds the sequence counts to the count-score
                seq_cts.addToScore(IGHV, cts);
            }
        } catch (Exception e) {
            System.out.println("Error in Main script!" + e);
        }

        Set<String> ighvs = seq_cts.getRowSet();
        for (String ighv : ighvs){
            total.addToScore(ighv, total_cts);            
        }
        //load the return array with scorekeepers
        results.add(seq_cts);
        results.add(total);

        return results;
    }

}
