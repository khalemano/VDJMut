package com.github.khalemano.vdjmut.scripts;

import com.github.khalemano.vdjmut.utilities.CSVTable;
import com.github.khalemano.vdjmut.utilities.Fastapair;
import com.github.khalemano.vdjmut.utilities.FastapairFile;
import com.github.khalemano.vdjmut.utilities.Mutation;
import com.github.khalemano.vdjmut.utilities.ScoreKeeper;





/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalani
 */
public class MutationAnalysisScript {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        String[] samples14 = {
            "406N",
            "406R",
            "958N",
            "958R"
        };
        
        String[] samples15 = {
            "605N",
            "605R",
            "957N",
            "789N",
            "789R",
            "953N",
            "489N",
            "489R",
            "952N",
            "841L",
            "841B",
            "431R"
        };
        
        String[] samples16 = {
            "230R",
            "841N",
            "841R",
            "431N"
        };
        
        String[] samples18 = {
            "705N",
            "705R",
            "705L",
            "293N",
            "293R",
            "294N"
        };
        
        String[] plasmids = {
            "pWT6D5_1-MG1",
            "pWT6D5_1-MG2",
            "pWT6D5_1-MG3",
            "pWT6D5_1-MG4",
            "pWT6D5_1-MG5"
            
        };
        
        String[] samples18IgG = {
            "WTSP4-MG1",
            "WTSP5-MG1",
            "WTSP6-MG1",
            "A3KO4-MG1",
            "A3KO5-MG1",
            "A3KO6-MG1",
            "841N-MG1",
            "841R-MG1",
            "841L-MG1"
        };
        
        for (String sample : samples18IgG){
        
            String input = "/home/kejun/MiSeq/Amplicon_Run18/Kalani/IgG/"+
                sample + "/Contigs/IMGT/" + sample +
                "_VH_fastapairs.fasta";
            
            String output = "/home/kejun/MiSeq/Amplicon_Run18/Analysis/IgG/collapsed_mutThreshold3/"
                + sample + "_mutThreshold0.csv";
        
            MutationAnalysisScript.run(input, output);
        }
    }
    
    public static void run(String input, String output){
        int ctThreshold = 2;
        int mutThreshold = 3;
        
        ScoreKeeper seq_cts = new ScoreKeeper("seq_cts");
        ScoreKeeper nt_cts = new ScoreKeeper("nt_cts");
        ScoreKeeper ABTV = new ScoreKeeper("AB/TV");
        ScoreKeeper CDGH = new ScoreKeeper("CD/GH");
        ScoreKeeper deam = new ScoreKeeper("CtoT/GtoA");
        
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
        
        
        try (FastapairFile file  = new FastapairFile(input)){
            
            while (file.hasNext()){
                Fastapair seqs = file.next();
                 
                //Checks the count threshold
                if (seqs.getCount() < ctThreshold){
                    continue;
                }

                //Looks for mutations and checks mutation threshold
                int mut=0;
                while (seqs.hasNextNtPair()){
                    char[] pair = seqs.nextPair();
                    if (pair[0] != pair[1]){
                        mut++;
                    }
                }

                if(mut < mutThreshold){
                    continue;
                }
                
                //Resets the fastapair iterator and analyzes the pair
                seqs.reset();
                String IGHV = seqs.getRefName();
                
                seq_cts.addToScore(IGHV, 1);
                
                while(seqs.hasNextNtPair()){
                    nt_cts.addToScore(IGHV, 1);
                    char[] pair = seqs.nextPair();
                    if (pair[0] != pair[1]){
                        if(pair[1]=='A'||pair[1]=='T')ABTV.addToScore(IGHV, 1);
                        if(pair[1]=='C'||pair[1]=='G')CDGH.addToScore(IGHV, 1);
                        
                        if(pair[1]=='C'){
                            deam.addToScore(IGHV, 1);
                            String[] trinucs = seqs.getContextReverse(3);
                            String trinuc = trinucs[0] + "_" + trinucs[1];
                            String[] dinucs = seqs.getContextReverse(2);
                            String dinuc = dinucs[0] + "_" + dinucs[1];
                            if(Mutation.isWRC_WRT(trinuc))WRC_WRT.addToScore(IGHV,1);
                            if(Mutation.isTTC_TTT(trinuc))TTC_TTT.addToScore(IGHV,1);
                            if(Mutation.isTCC_TCT(trinuc))TCC_TCT.addToScore(IGHV,1);
                            if(Mutation.isRC_RT(dinuc))RC_RT.addToScore(IGHV,1);
                            if(Mutation.isTC_TT(dinuc))TC_TT.addToScore(IGHV,1);
                            if(Mutation.isCC_CT(dinuc))CC_CT.addToScore(IGHV,1);
                            if(Mutation.isWRC_WRD(trinuc))WRC_WRD.addToScore(IGHV,1);
                            if(Mutation.isTTC_TTD(trinuc))TTC_TTD.addToScore(IGHV,1);
                            if(Mutation.isTCC_TCD(trinuc))TCC_TCD.addToScore(IGHV,1);
                            if(Mutation.isRC_RD(dinuc))RC_RD.addToScore(IGHV,1);
                            if(Mutation.isTC_TD(dinuc))TC_TD.addToScore(IGHV,1);
                            if(Mutation.isCC_CD(dinuc))CC_CD.addToScore(IGHV,1);
                        }
                        
                        if(pair[1]=='G'){
                            deam.addToScore(IGHV, 1);
                            String[] trinucs = seqs.getContextForward(3);
                            String trinuc = trinucs[0] + "_" + trinucs[1];
                            String[] dinucs = seqs.getContextForward(2);
                            String dinuc = dinucs[0] + "_" + dinucs[1];
                            if(Mutation.isWRC_WRT(trinuc))WRC_WRT.addToScore(IGHV,1);
                            if(Mutation.isTTC_TTT(trinuc))TTC_TTT.addToScore(IGHV,1);
                            if(Mutation.isTCC_TCT(trinuc))TCC_TCT.addToScore(IGHV,1);
                            if(Mutation.isRC_RT(dinuc))RC_RT.addToScore(IGHV,1);
                            if(Mutation.isTC_TT(dinuc))TC_TT.addToScore(IGHV,1);
                            if(Mutation.isCC_CT(dinuc))CC_CT.addToScore(IGHV,1);
                            if(Mutation.isWRC_WRD(trinuc))WRC_WRD.addToScore(IGHV,1);
                            if(Mutation.isTTC_TTD(trinuc))TTC_TTD.addToScore(IGHV,1);
                            if(Mutation.isTCC_TCD(trinuc))TCC_TCD.addToScore(IGHV,1);
                            if(Mutation.isRC_RD(dinuc))RC_RD.addToScore(IGHV,1);
                            if(Mutation.isTC_TD(dinuc))TC_TD.addToScore(IGHV,1);
                            if(Mutation.isCC_CD(dinuc))CC_CD.addToScore(IGHV,1);
                        }
                    }
                }
            }
        } catch (Exception e){
            System.out.println("Error in Main script!" + e);
        }
        
        CSVTable.printToFile(output, seq_cts.getRowSet(), 
            seq_cts, nt_cts, CDGH, ABTV, deam, 
            RC_RT, TC_TT, CC_CT, WRC_WRT, TTC_TTT, TCC_TCT,
            RC_RD, TC_TD, CC_CD, WRC_WRD, TTC_TTD, TCC_TCD);
        
    }
}