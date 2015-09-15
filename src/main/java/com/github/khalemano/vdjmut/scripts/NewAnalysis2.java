/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khalemano.vdjmut.scripts;


import com.github.khalemano.vdjmut.utilities.Fastapair;
import com.github.khalemano.vdjmut.utilities.FastapairFile;
import com.github.khalemano.vdjmut.utilities.Mutation;
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
public class NewAnalysis2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        // TODO code application logic here
        Path output = Paths.get("/home/kejun/MiSeq/AIDKO/Mut_cregion_thr1_noncollapsed.csv");
        
//        String suffix = "/home/kejun/MiSeq/AIDKO/Fastapairs/";
        String suffix = "/home/kejun/MiSeq/AIDKO/cregionalignments/";
        
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
            String header = "sample,genotype, dpi, ighv,seq_cts,nt_cts,"
                    + "ABTV,CDGH,deam,RC_RT,TC_TT,CC_CT,WRC_WRT,"
                    + "TTC_TTT,TCC_TCT,RC_RD,TC_TD,CC_CD,WRC_WRD,TTC_TTD,TCC_TCD,"
                    + "A_T,A_C,A_G,T_A,T_C,T_G,C_A,C_T,C_G,G_A,G_T,G_C";
            writer.write(header);
            writer.newLine();
            
            for(int i = 0; i<samples.size(); i++){

                ArrayList<ScoreKeeper> scorekeepers = Analyze(suffix + samples.get(i),1,false);
                
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
    
    public static ArrayList<ScoreKeeper> Analyze(String path, int ctsThreshold, boolean collapse){
        ArrayList<ScoreKeeper> results = new ArrayList<>();
        
        System.out.println("Analyzing: " + path);
        
        ScoreKeeper seq_cts = new ScoreKeeper("seq_cts");
        ScoreKeeper nt_cts = new ScoreKeeper("nt_cts");
        ScoreKeeper ABTV = new ScoreKeeper("ABTV");
        ScoreKeeper CDGH = new ScoreKeeper("CDGH");
        ScoreKeeper deam = new ScoreKeeper("deam");        
        ScoreKeeper RC_RT = new ScoreKeeper("RC_RT");
        ScoreKeeper TC_TT = new ScoreKeeper("TC_TT");
        ScoreKeeper CC_CT = new ScoreKeeper("CC_CT");
        ScoreKeeper WRC_WRT = new ScoreKeeper("WRC_WRT");
        ScoreKeeper TTC_TTT = new ScoreKeeper("TTC_TTT");
        ScoreKeeper TCC_TCT = new ScoreKeeper("TCC_TCT");        
        ScoreKeeper RC_RD = new ScoreKeeper("RC_RD");
        ScoreKeeper TC_TD = new ScoreKeeper("TC_TD");
        ScoreKeeper CC_CD = new ScoreKeeper("CC_CD");
        ScoreKeeper WRC_WRD = new ScoreKeeper("WRC_WRD");
        ScoreKeeper TTC_TTD = new ScoreKeeper("TTC_TTD");
        ScoreKeeper TCC_TCD = new ScoreKeeper("TCC_TCD");

        ScoreKeeper A_T = new ScoreKeeper("A_T");
        ScoreKeeper A_C = new ScoreKeeper("A_C");
        ScoreKeeper A_G = new ScoreKeeper("A_G");
        
        ScoreKeeper T_A = new ScoreKeeper("T_A");
        ScoreKeeper T_C = new ScoreKeeper("T_C");
        ScoreKeeper T_G = new ScoreKeeper("T_G");
        
        ScoreKeeper C_T = new ScoreKeeper("C_T");
        ScoreKeeper C_A = new ScoreKeeper("C_A");
        ScoreKeeper C_G = new ScoreKeeper("C_G");
        
        ScoreKeeper G_A = new ScoreKeeper("G_A");
        ScoreKeeper G_C = new ScoreKeeper("G_C");
        ScoreKeeper G_T = new ScoreKeeper("G_T");        
        
 try (FastapairFile file  = new FastapairFile(path)){
            
            while (file.hasNext()){
                Fastapair seqs = file.next();
                 
                /*Checks the count threshold
                If the fastapair count is at or above threshold,
                the program continues. If not, the fastapair is skipped
                After threshold checking, the cts is set to 1 if the dataset
                is to be collapsed*/
                int cts = seqs.getCount();
                if (cts < ctsThreshold) continue;
                if (collapse) cts = 1;
                
                //Obtains name of reference sequence
                String IGHV = seqs.getRefName();
                
                //Adds the sequence counts to the count-score
                seq_cts.addToScore(IGHV, cts);
                
                //sets a limit for how far we can read into the alignment
                int alignmentPosition = 0;
                int alignmentLimit = 265;
                //Analyze the fastapair for mutations
                while(seqs.hasNextNtPair()){
                    //increment the position to keep track of where we are 
                    //in the alignment, then check to see if we are past the 
                    //limit
                    alignmentPosition++;
                    if (alignmentPosition > alignmentLimit)continue;
                    
                    nt_cts.addToScore(IGHV, cts);
                    char[] pair = seqs.nextPair();
                    if (pair[0] != pair[1]){
                        if(pair[1]=='A'||pair[1]=='T')ABTV.addToScore(IGHV, cts);
                        if(pair[1]=='C'||pair[1]=='G')CDGH.addToScore(IGHV, cts);
                        
                        //the next four if statements check the single nucleotide
                        //mutation type and add it to the scorekeepers
                        if(pair[1]=='A'){
                            if(pair[0]=='T')A_T.addToScore(IGHV, cts);
                            if(pair[0]=='C')A_C.addToScore(IGHV, cts);
                            if(pair[0]=='G')A_G.addToScore(IGHV, cts);
                        }
                        
                        if(pair[1]=='T'){
                            if(pair[0]=='A')T_A.addToScore(IGHV, cts);
                            if(pair[0]=='C')T_C.addToScore(IGHV, cts);
                            if(pair[0]=='G')T_G.addToScore(IGHV, cts);
                        }
                        
                        if(pair[1]=='C'){
                            if(pair[0]=='T')C_T.addToScore(IGHV, cts);
                            if(pair[0]=='A')C_A.addToScore(IGHV, cts);
                            if(pair[0]=='G')C_G.addToScore(IGHV, cts);
                        }
                        
                        if(pair[1]=='G'){
                            if(pair[0]=='A')G_A.addToScore(IGHV, cts);
                            if(pair[0]=='C')G_C.addToScore(IGHV, cts);
                            if(pair[0]=='T')G_T.addToScore(IGHV, cts);
                        }
                        
                        if(pair[1]=='C'){
                            if (pair[0] == 'T') deam.addToScore(IGHV, cts);
                            String[] trinucs = seqs.getContextReverse(3);
                            String trinuc = trinucs[1] + "_" + trinucs[0];
                            String[] dinucs = seqs.getContextReverse(2);
                            String dinuc = dinucs[1] + "_" + dinucs[0];
                            if(Mutation.isWRC_WRT(trinuc))WRC_WRT.addToScore(IGHV,cts);
                            if(Mutation.isTTC_TTT(trinuc))TTC_TTT.addToScore(IGHV,cts);
                            if(Mutation.isTCC_TCT(trinuc))TCC_TCT.addToScore(IGHV,cts);
                            if(Mutation.isRC_RT(dinuc))RC_RT.addToScore(IGHV,cts);
                            if(Mutation.isTC_TT(dinuc))TC_TT.addToScore(IGHV,cts);
                            if(Mutation.isCC_CT(dinuc))CC_CT.addToScore(IGHV,cts);
                            if(Mutation.isWRC_WRD(trinuc))WRC_WRD.addToScore(IGHV,cts);
                            if(Mutation.isTTC_TTD(trinuc))TTC_TTD.addToScore(IGHV,cts);
                            if(Mutation.isTCC_TCD(trinuc))TCC_TCD.addToScore(IGHV,cts);
                            if(Mutation.isRC_RD(dinuc))RC_RD.addToScore(IGHV,cts);
                            if(Mutation.isTC_TD(dinuc))TC_TD.addToScore(IGHV,cts);
                            if(Mutation.isCC_CD(dinuc))CC_CD.addToScore(IGHV,cts);
                        }
                        
                        if(pair[1]=='G'){
                            if (pair[0] == 'A') deam.addToScore(IGHV, cts);
                            String[] trinucs = seqs.getContextForward(3);
                            String trinuc = trinucs[1] + "_" + trinucs[0];
                            String[] dinucs = seqs.getContextForward(2);
                            String dinuc = dinucs[1] + "_" + dinucs[0];
                            if(Mutation.isWRC_WRT(trinuc))WRC_WRT.addToScore(IGHV,cts);
                            if(Mutation.isTTC_TTT(trinuc))TTC_TTT.addToScore(IGHV,cts);
                            if(Mutation.isTCC_TCT(trinuc))TCC_TCT.addToScore(IGHV,cts);
                            if(Mutation.isRC_RT(dinuc))RC_RT.addToScore(IGHV,cts);
                            if(Mutation.isTC_TT(dinuc))TC_TT.addToScore(IGHV,cts);
                            if(Mutation.isCC_CT(dinuc))CC_CT.addToScore(IGHV,cts);
                            if(Mutation.isWRC_WRD(trinuc))WRC_WRD.addToScore(IGHV,cts);
                            if(Mutation.isTTC_TTD(trinuc))TTC_TTD.addToScore(IGHV,cts);
                            if(Mutation.isTCC_TCD(trinuc))TCC_TCD.addToScore(IGHV,cts);
                            if(Mutation.isRC_RD(dinuc))RC_RD.addToScore(IGHV,cts);
                            if(Mutation.isTC_TD(dinuc))TC_TD.addToScore(IGHV,cts);
                            if(Mutation.isCC_CD(dinuc))CC_CD.addToScore(IGHV,cts);
                        }
                    }
                }
            }
        } catch (Exception e){
            System.out.println("Error in Main script!" + e);
        }
        
        //load the return array with scorekeepers
        results.add(seq_cts);
        results.add(nt_cts);
        results.add(ABTV);
        results.add(CDGH);
        results.add(deam);
        results.add(RC_RT);
        results.add(TC_TT);
        results.add(CC_CT);
        results.add(WRC_WRT);
        results.add(TTC_TTT);
        results.add(TCC_TCT);
        results.add(RC_RD);
        results.add(TC_TD);
        results.add(CC_CD);
        results.add(WRC_WRD);
        results.add(TTC_TTD);
        results.add(TCC_TCD);

        results.add(A_T);
        results.add(A_C);
        results.add(A_G);
        results.add(T_A);
        results.add(T_C);
        results.add(T_G);
        results.add(C_A);
        results.add(C_T);
        results.add(C_G);
        results.add(G_A);
        results.add(G_T);
        results.add(G_C);
        
        return results;
    }
    
    
}
