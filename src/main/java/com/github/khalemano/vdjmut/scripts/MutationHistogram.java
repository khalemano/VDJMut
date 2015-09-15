/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khalemano.vdjmut.scripts;

import com.github.khalemano.vdjmut.utilities.CSVTable;
import com.github.khalemano.vdjmut.utilities.ScoreKeeper;
import com.github.khalemano.vdjmut.utilities.Fastapair;
import com.github.khalemano.vdjmut.utilities.FastapairFile;


/**
 *
 * @author kalani
 */
public class MutationHistogram {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
       String filepath = "/home/kejun/MiSeq/Amplicon_Run18/Kalani/IgG/"+
                "841L-MG1/Contigs/IMGT/841L-MG1_VH_fastapairs.fasta";
        
        
        String outpath = "/home/kejun/Desktop/histogram.csv";
        
        int threshold = 2;
        
        ScoreKeeper seqCts = new ScoreKeeper("cts");

        
        try (FastapairFile file  = new FastapairFile(filepath)){
            
             while (file.hasNext()){
                 Fastapair seqs = file.next();
                 
                 if (seqs.getCount() < threshold){
                     continue;
                 }
                 
                 int muts = 0;
                 
                 while (seqs.hasNextNtPair()){
                     char[] pair = seqs.nextPair();
                     if (pair[0] != pair[1]){
                         muts++;
                     }
                 }
                 
                 seqCts.addToScore(Integer.toString(muts), 1);
                 
             }
            
        } catch (Exception e){
            System.out.println("Error in Main script!" + e);
        }
        
        CSVTable.printToFile(outpath, seqCts.getRowSet(), seqCts);
    }//end of main
    
}//end of class
