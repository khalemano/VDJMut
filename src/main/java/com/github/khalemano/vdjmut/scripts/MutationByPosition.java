/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khalemano.vdjmut.scripts;


import com.github.khalemano.vdjmut.utilities.FastaDatabase;
import com.github.khalemano.vdjmut.utilities.Fastapair;
import com.github.khalemano.vdjmut.utilities.FastapairFile;
import com.github.khalemano.vdjmut.utilities.ScoreKeeper;
import com.github.khalemano.vdjmut.utilities.TwoDScoreKeeper;
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
public class MutationByPosition {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        FastaDatabase fastadb = new FastaDatabase(
            "/home/kejun/MiSeq/Reference_databases/"
                + "C57BL6_IMGT_VH_germline_shortID.fa");
        
        Path output = Paths.get("/home/kejun/MiSeq/AIDKO/Mut_by_position_collapsed.csv");
        
        String suffix = "/home/kejun/MiSeq/AIDKO/Fastapairs/";
//        String suffix = "/home/kejun/MiSeq/AIDKO/cregionalignments/";
        
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
        samples.add("841B"); genotypes.add("DBKO"); dpi.add("Uninf");
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
        
//        samples.add("pWT6D5-1");
//        samples.add("pWT6D5-2");
//        samples.add("pWT6D5-3");
//        samples.add("pWT6D5-4");
//        samples.add("pWT6D5-5");
                
        try(BufferedWriter writer = 
            Files.newBufferedWriter(output,StandardCharsets.UTF_8)){

            //Creates and prints the header of the csv table
            String header = "sample,genotype, dpi, ighv,seq_cts,nt_cts";
                for (int j=1; j<=300; j++){
                    header = header + "," + j;
                }
                    
            writer.write(header);
            writer.newLine();
            
            for(int i = 0; i<samples.size(); i++){

                //analyze the file and retrieve the scorekeepers
                ArrayList scorekeepers = analyze(suffix + samples.get(i),true,fastadb);
                
                //unpack the scorekeepers and cast them
                ScoreKeeper seq_cts = (ScoreKeeper)scorekeepers.get(0);
                ScoreKeeper nt_cts = (ScoreKeeper)scorekeepers.get(1);
                TwoDScoreKeeper<String,Integer> mutbypos = 
                        (TwoDScoreKeeper<String,Integer>)scorekeepers.get(2);
                
                //retrieve the ighv names
                Set<String> ighvs = seq_cts.getRowSet();
            
                //loop through the scorekeepers to print
                for (String ighv : ighvs){
                    String line = samples.get(i) + "," 
                            + genotypes.get(i) + "," 
                            + dpi.get(i) + "," 
                            + ighv + "," 
                            + seq_cts.getScore(ighv) + "," 
                            + nt_cts.getScore(ighv);
                        for (int j=1; j<=300; j++){
                            line = line + "," + mutbypos.getScore(ighv, j);
                        }
                    writer.write(line);
                    writer.newLine();
                }
            }
        }catch (IOException e){
            System.out.println(e);
        }
        
        
    }
 
    public static ArrayList analyze (String path, boolean collapse, FastaDatabase fastadb){
        ArrayList results = new ArrayList();
        
        TwoDScoreKeeper<String,Integer> mutbypos = new TwoDScoreKeeper<>();
        
        System.out.println("Analyzing: " + path);
        
        ScoreKeeper seq_cts = new ScoreKeeper("seq_cts");
        ScoreKeeper nt_cts = new ScoreKeeper("nt_cts");

        
        
         try (FastapairFile file  = new FastapairFile(path)){
            
            while (file.hasNext()){
                Fastapair seqs = file.next();
                
//              The cts are set to 1 if the dataset
//              is to be collapsed
                int cts = seqs.getCount();
                if (collapse) cts = 1;
                
                //Obtains name of reference sequence
                String IGHV = seqs.getRefName();
                
                //Obtains the position offset in the reference seq
                int offset = getOffset(fastadb, IGHV, seqs.getRefSeq());
                
                //Adds the sequence counts to the count-score
                seq_cts.addToScore(IGHV, cts);
                
                //Analyze the fastapair for mutations
                while(seqs.hasNextNtPair()){
                    //adds the counts to the nucleotide score
                    nt_cts.addToScore(IGHV, cts);
                    char[] pair = seqs.nextPair();
                    // if an mismatch is found
                    if (pair[0] != pair[1]){
                        //record the event by ighv and ref seq position
                        mutbypos.add(IGHV, offset + seqs.getRefPosition(), cts);
                    }
                }
            }
        } catch (Exception e){
            System.out.println("Error in Main script!" + e);
        }
        
        results.add(seq_cts);
        results.add(nt_cts);
        results.add(mutbypos);
        return results;
    }
    
    static int getOffset(FastaDatabase fastadb, String ighv, String trunc){
        String ref = fastadb.getSequence(ighv);
        return ref.indexOf(trunc.substring(0, 15));
    }
    
}
