/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khalemano.vdjmut.scripts;


import com.github.khalemano.vdjmut.utilities.Fastapair;
import com.github.khalemano.vdjmut.utilities.FastapairFile;
import com.github.khalemano.vdjmut.utilities.Mutation;
import com.github.khalemano.vdjmut.utilities.Regions;
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
public class RegionMutationAnalysis1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        
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
                
        Path output = Paths.get("/home/kejun/MiSeq/AIDKO/mut_regions_collapsed.csv");
        
        String suffix = "/home/kejun/MiSeq/AIDKO/Fastapairs/";
        
        try(BufferedWriter writer = 
            Files.newBufferedWriter(output,StandardCharsets.UTF_8)){

            //Creates and prints the header of the csv table
            String header = "sample,genotype, dpi, ighv,seq_cts,"
                    + "FWR1_nt_cts,"
                    + "FWR1_ABTV,"
                    + "FWR1_CDGH,"
                    + "FWR1_deam,"
                    + "FWR1_RC_RT,"
                    + "FWR1_TC_TT,"
                    + "FWR1_CC_CT,"
                    + "FWR1_WRC_WRT,"
                    + "FWR1_TTC_TTT,"
                    + "FWR1_TCC_TCT,"
                    + "FWR1_RC_RD,"
                    + "FWR1_TC_TD,"
                    + "FWR1_CC_CD,"
                    + "FWR1_WRC_WRD,"
                    + "FWR1_TTC_TTD,"
                    + "FWR1_TCC_TCD,"
                    
                    + "CDR1_nt_cts,"
                    + "CDR1_ABTV,"
                    + "CDR1_CDGH,"
                    + "CDR1_deam,"
                    + "CDR1_RC_RT,"
                    + "CDR1_TC_TT,"
                    + "CDR1_CC_CT,"
                    + "CDR1_WRC_WRT,"
                    + "CDR1_TTC_TTT,"
                    + "CDR1_TCC_TCT,"
                    + "CDR1_RC_RD,"
                    + "CDR1_TC_TD,"
                    + "CDR1_CC_CD,"
                    + "CDR1_WRC_WRD,"
                    + "CDR1_TTC_TTD,"
                    + "CDR1_TCC_TCD,"
                    
                    + "FWR2_nt_cts,"
                    + "FWR2_ABTV,"
                    + "FWR2_CDGH,"
                    + "FWR2_deam,"
                    + "FWR2_RC_RT,"
                    + "FWR2_TC_TT,"
                    + "FWR2_CC_CT,"
                    + "FWR2_WRC_WRT,"
                    + "FWR2_TTC_TTT,"
                    + "FWR2_TCC_TCT,"
                    + "FWR2_RC_RD,"
                    + "FWR2_TC_TD,"
                    + "FWR2_CC_CD,"
                    + "FWR2_WRC_WRD,"
                    + "FWR2_TTC_TTD,"
                    + "FWR2_TCC_TCD,"
                    
                    + "CDR2_nt_cts,"
                    + "CDR2_ABTV,"
                    + "CDR2_CDGH,"
                    + "CDR2_deam,"
                    + "CDR2_RC_RT,"
                    + "CDR2_TC_TT,"
                    + "CDR2_CC_CT,"
                    + "CDR2_WRC_WRT,"
                    + "CDR2_TTC_TTT,"
                    + "CDR2_TCC_TCT,"
                    + "CDR2_RC_RD,"
                    + "CDR2_TC_TD,"
                    + "CDR2_CC_CD,"
                    + "CDR2_WRC_WRD,"
                    + "CDR2_TTC_TTD,"
                    + "CDR2_TCC_TCD,"
                    
                    + "FWR3_nt_cts,"
                    + "FWR3_ABTV,"
                    + "FWR3_CDGH,"
                    + "FWR3_deam,"
                    + "FWR3_RC_RT,"
                    + "FWR3_TC_TT,"
                    + "FWR3_CC_CT,"
                    + "FWR3_WRC_WRT,"
                    + "FWR3_TTC_TTT,"
                    + "FWR3_TCC_TCT,"
                    + "FWR3_RC_RD,"
                    + "FWR3_TC_TD,"
                    + "FWR3_CC_CD,"
                    + "FWR3_WRC_WRD,"
                    + "FWR3_TTC_TTD,"
                    + "FWR3_TCC_TCD,"
                    ;
            
            writer.write(header);
            writer.newLine();
            
            for(int i = 0; i<samples.size(); i++){

                ArrayList<ScoreKeeper> scorekeepers = Analyze(suffix + samples.get(i),1,true);
                
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

        ScoreKeeper FWR1_nt_cts =   new ScoreKeeper("FWR1_nt_cts");
        ScoreKeeper FWR1_ABTV =     new ScoreKeeper("FWR1_ABTV");
        ScoreKeeper FWR1_CDGH =     new ScoreKeeper("FWR1_CDGH");
        ScoreKeeper FWR1_deam =     new ScoreKeeper("FWR1_deam");  
        ScoreKeeper FWR1_RC_RT =    new ScoreKeeper("FWR1_RC_RT");
        ScoreKeeper FWR1_TC_TT =    new ScoreKeeper("FWR1_TC_TT");
        ScoreKeeper FWR1_CC_CT =    new ScoreKeeper("FWR1_CC_CT");
        ScoreKeeper FWR1_WRC_WRT =  new ScoreKeeper("FWR1_WRC_WRT");
        ScoreKeeper FWR1_TTC_TTT =  new ScoreKeeper("FWR1_TTC_TTT");
        ScoreKeeper FWR1_TCC_TCT =  new ScoreKeeper("FWR1_TCC_TCT");        
        ScoreKeeper FWR1_RC_RD =    new ScoreKeeper("FWR1_RC_RD");
        ScoreKeeper FWR1_TC_TD =    new ScoreKeeper("FWR1_TC_TD");
        ScoreKeeper FWR1_CC_CD =    new ScoreKeeper("FWR1_CC_CD");
        ScoreKeeper FWR1_WRC_WRD =  new ScoreKeeper("FWR1_WRC_WRD");
        ScoreKeeper FWR1_TTC_TTD =  new ScoreKeeper("FWR1_TTC_TTD");
        ScoreKeeper FWR1_TCC_TCD =  new ScoreKeeper("FWR1_TCC_TCD");  

        ScoreKeeper CDR1_nt_cts =   new ScoreKeeper("CDR1_nt_cts");
        ScoreKeeper CDR1_ABTV =     new ScoreKeeper("CDR1_ABTV");
        ScoreKeeper CDR1_CDGH =     new ScoreKeeper("CDR1_CDGH");
        ScoreKeeper CDR1_deam =     new ScoreKeeper("CDR1_deam");
        ScoreKeeper CDR1_RC_RT =    new ScoreKeeper("CDR1_RC_RT");
        ScoreKeeper CDR1_TC_TT =    new ScoreKeeper("CDR1_TC_TT");
        ScoreKeeper CDR1_CC_CT =    new ScoreKeeper("CDR1_CC_CT");
        ScoreKeeper CDR1_WRC_WRT =  new ScoreKeeper("CDR1_WRC_WRT");
        ScoreKeeper CDR1_TTC_TTT =  new ScoreKeeper("CDR1_TTC_TTT");
        ScoreKeeper CDR1_TCC_TCT =  new ScoreKeeper("CDR1_TCC_TCT");        
        ScoreKeeper CDR1_RC_RD =    new ScoreKeeper("CDR1_RC_RD");
        ScoreKeeper CDR1_TC_TD =    new ScoreKeeper("CDR1_TC_TD");
        ScoreKeeper CDR1_CC_CD =    new ScoreKeeper("CDR1_CC_CD");
        ScoreKeeper CDR1_WRC_WRD =  new ScoreKeeper("CDR1_WRC_WRD");
        ScoreKeeper CDR1_TTC_TTD =  new ScoreKeeper("CDR1_TTC_TTD");
        ScoreKeeper CDR1_TCC_TCD =  new ScoreKeeper("CDR1_TCC_TCD");          

        ScoreKeeper FWR2_nt_cts =   new ScoreKeeper("FWR2_nt_cts");
        ScoreKeeper FWR2_ABTV =     new ScoreKeeper("FWR2_ABTV");
        ScoreKeeper FWR2_CDGH =     new ScoreKeeper("FWR2_CDGH");
        ScoreKeeper FWR2_deam =     new ScoreKeeper("FWR2_deam");
        ScoreKeeper FWR2_RC_RT =    new ScoreKeeper("FWR2_RC_RT");
        ScoreKeeper FWR2_TC_TT =    new ScoreKeeper("FWR2_TC_TT");
        ScoreKeeper FWR2_CC_CT =    new ScoreKeeper("FWR2_CC_CT");
        ScoreKeeper FWR2_WRC_WRT =  new ScoreKeeper("FWR2_WRC_WRT");
        ScoreKeeper FWR2_TTC_TTT =  new ScoreKeeper("FWR2_TTC_TTT");
        ScoreKeeper FWR2_TCC_TCT =  new ScoreKeeper("FWR2_TCC_TCT");        
        ScoreKeeper FWR2_RC_RD =    new ScoreKeeper("FWR2_RC_RD");
        ScoreKeeper FWR2_TC_TD =    new ScoreKeeper("FWR2_TC_TD");
        ScoreKeeper FWR2_CC_CD =    new ScoreKeeper("FWR2_CC_CD");
        ScoreKeeper FWR2_WRC_WRD =  new ScoreKeeper("FWR2_WRC_WRD");
        ScoreKeeper FWR2_TTC_TTD =  new ScoreKeeper("FWR2_TTC_TTD");
        ScoreKeeper FWR2_TCC_TCD =  new ScoreKeeper("FWR2_TCC_TCD");  

        ScoreKeeper CDR2_nt_cts =   new ScoreKeeper("CDR2_nt_cts");
        ScoreKeeper CDR2_ABTV =     new ScoreKeeper("CDR2_ABTV");
        ScoreKeeper CDR2_CDGH =     new ScoreKeeper("CDR2_CDGH");
        ScoreKeeper CDR2_deam =     new ScoreKeeper("CDR2_deam");
        ScoreKeeper CDR2_RC_RT =    new ScoreKeeper("CDR2_RC_RT");
        ScoreKeeper CDR2_TC_TT =    new ScoreKeeper("CDR2_TC_TT");
        ScoreKeeper CDR2_CC_CT =    new ScoreKeeper("CDR2_CC_CT");
        ScoreKeeper CDR2_WRC_WRT =  new ScoreKeeper("CDR2_WRC_WRT");
        ScoreKeeper CDR2_TTC_TTT =  new ScoreKeeper("CDR2_TTC_TTT");
        ScoreKeeper CDR2_TCC_TCT =  new ScoreKeeper("CDR2_TCC_TCT");        
        ScoreKeeper CDR2_RC_RD =    new ScoreKeeper("CDR2_RC_RD");
        ScoreKeeper CDR2_TC_TD =    new ScoreKeeper("CDR2_TC_TD");
        ScoreKeeper CDR2_CC_CD =    new ScoreKeeper("CDR2_CC_CD");
        ScoreKeeper CDR2_WRC_WRD =  new ScoreKeeper("CDR2_WRC_WRD");
        ScoreKeeper CDR2_TTC_TTD =  new ScoreKeeper("CDR2_TTC_TTD");
        ScoreKeeper CDR2_TCC_TCD =  new ScoreKeeper("CDR2_TCC_TCD");         

        ScoreKeeper FWR3_nt_cts =   new ScoreKeeper("FWR3_nt_cts");
        ScoreKeeper FWR3_ABTV =     new ScoreKeeper("FWR3_ABTV");
        ScoreKeeper FWR3_CDGH =     new ScoreKeeper("FWR3_CDGH");
        ScoreKeeper FWR3_deam =     new ScoreKeeper("FWR3_deam");
        ScoreKeeper FWR3_RC_RT =    new ScoreKeeper("FWR3_RC_RT");
        ScoreKeeper FWR3_TC_TT =    new ScoreKeeper("FWR3_TC_TT");
        ScoreKeeper FWR3_CC_CT =    new ScoreKeeper("FWR3_CC_CT");
        ScoreKeeper FWR3_WRC_WRT =  new ScoreKeeper("FWR3_WRC_WRT");
        ScoreKeeper FWR3_TTC_TTT =  new ScoreKeeper("FWR3_TTC_TTT");
        ScoreKeeper FWR3_TCC_TCT =  new ScoreKeeper("FWR3_TCC_TCT");        
        ScoreKeeper FWR3_RC_RD =    new ScoreKeeper("FWR3_RC_RD");
        ScoreKeeper FWR3_TC_TD =    new ScoreKeeper("FWR3_TC_TD");
        ScoreKeeper FWR3_CC_CD =    new ScoreKeeper("FWR3_CC_CD");
        ScoreKeeper FWR3_WRC_WRD =  new ScoreKeeper("FWR3_WRC_WRD");
        ScoreKeeper FWR3_TTC_TTD =  new ScoreKeeper("FWR3_TTC_TTD");
        ScoreKeeper FWR3_TCC_TCD =  new ScoreKeeper("FWR3_TCC_TCD");  

        Regions reg = new Regions();
        
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
                
                reg.setRegions(seqs.getRefSeq(),IGHV);
                //Analyze the fastapair for mutations
                while(seqs.hasNextNtPair()){
                    //increment the position to keep track of where we are 
                    //in the alignment, then check to see if we are past the 
                    //limit
                    
                    String region = reg.getRegion(seqs.getAlignmentPosition() + 1);
                    
                    if (region.equalsIgnoreCase("FWR1")){
                        FWR1_nt_cts.addToScore(IGHV, cts);
                        char[] pair = seqs.nextPair();
                        if (pair[0] != pair[1]){
                            if(pair[1]=='A'||pair[1]=='T')FWR1_ABTV.addToScore(IGHV, cts);
                            if(pair[1]=='C'||pair[1]=='G')FWR1_CDGH.addToScore(IGHV, cts);


                            if(pair[1]=='C'){
                                if (pair[0] == 'T') FWR1_deam.addToScore(IGHV, cts);
                                String[] trinucs = seqs.getContextReverse(3);
                                String trinuc = trinucs[1] + "_" + trinucs[0];
                                String[] dinucs = seqs.getContextReverse(2);
                                String dinuc = dinucs[1] + "_" + dinucs[0];
                                if(Mutation.isWRC_WRT(trinuc))FWR1_WRC_WRT.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTT(trinuc))FWR1_TTC_TTT.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCT(trinuc))FWR1_TCC_TCT.addToScore(IGHV,cts);
                                if(Mutation.isRC_RT(dinuc))FWR1_RC_RT.addToScore(IGHV,cts);
                                if(Mutation.isTC_TT(dinuc))FWR1_TC_TT.addToScore(IGHV,cts);
                                if(Mutation.isCC_CT(dinuc))FWR1_CC_CT.addToScore(IGHV,cts);
                                if(Mutation.isWRC_WRD(trinuc))FWR1_WRC_WRD.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTD(trinuc))FWR1_TTC_TTD.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCD(trinuc))FWR1_TCC_TCD.addToScore(IGHV,cts);
                                if(Mutation.isRC_RD(dinuc))FWR1_RC_RD.addToScore(IGHV,cts);
                                if(Mutation.isTC_TD(dinuc))FWR1_TC_TD.addToScore(IGHV,cts);
                                if(Mutation.isCC_CD(dinuc))FWR1_CC_CD.addToScore(IGHV,cts);
                            }

                            if(pair[1]=='G'){
                                if (pair[0] == 'A') FWR1_deam.addToScore(IGHV, cts);
                                String[] trinucs = seqs.getContextForward(3);
                                String trinuc = trinucs[1] + "_" + trinucs[0];
                                String[] dinucs = seqs.getContextForward(2);
                                String dinuc = dinucs[1] + "_" + dinucs[0];
                                if(Mutation.isWRC_WRT(trinuc))FWR1_WRC_WRT.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTT(trinuc))FWR1_TTC_TTT.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCT(trinuc))FWR1_TCC_TCT.addToScore(IGHV,cts);
                                if(Mutation.isRC_RT(dinuc))FWR1_RC_RT.addToScore(IGHV,cts);
                                if(Mutation.isTC_TT(dinuc))FWR1_TC_TT.addToScore(IGHV,cts);
                                if(Mutation.isCC_CT(dinuc))FWR1_CC_CT.addToScore(IGHV,cts);
                                if(Mutation.isWRC_WRD(trinuc))FWR1_WRC_WRD.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTD(trinuc))FWR1_TTC_TTD.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCD(trinuc))FWR1_TCC_TCD.addToScore(IGHV,cts);
                                if(Mutation.isRC_RD(dinuc))FWR1_RC_RD.addToScore(IGHV,cts);
                                if(Mutation.isTC_TD(dinuc))FWR1_TC_TD.addToScore(IGHV,cts);
                                if(Mutation.isCC_CD(dinuc))FWR1_CC_CD.addToScore(IGHV,cts);
                            }
                        }
                    }
                    
                    if (region.equalsIgnoreCase("CDR1")){
                        CDR1_nt_cts.addToScore(IGHV, cts);
                        char[] pair = seqs.nextPair();
                        if (pair[0] != pair[1]){
                            if(pair[1]=='A'||pair[1]=='T')CDR1_ABTV.addToScore(IGHV, cts);
                            if(pair[1]=='C'||pair[1]=='G')CDR1_CDGH.addToScore(IGHV, cts);


                            if(pair[1]=='C'){
                                if (pair[0] == 'T') CDR1_deam.addToScore(IGHV, cts);
                                String[] trinucs = seqs.getContextReverse(3);
                                String trinuc = trinucs[1] + "_" + trinucs[0];
                                String[] dinucs = seqs.getContextReverse(2);
                                String dinuc = dinucs[1] + "_" + dinucs[0];
                                if(Mutation.isWRC_WRT(trinuc))CDR1_WRC_WRT.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTT(trinuc))CDR1_TTC_TTT.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCT(trinuc))CDR1_TCC_TCT.addToScore(IGHV,cts);
                                if(Mutation.isRC_RT(dinuc))CDR1_RC_RT.addToScore(IGHV,cts);
                                if(Mutation.isTC_TT(dinuc))CDR1_TC_TT.addToScore(IGHV,cts);
                                if(Mutation.isCC_CT(dinuc))CDR1_CC_CT.addToScore(IGHV,cts);
                                if(Mutation.isWRC_WRD(trinuc))CDR1_WRC_WRD.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTD(trinuc))CDR1_TTC_TTD.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCD(trinuc))CDR1_TCC_TCD.addToScore(IGHV,cts);
                                if(Mutation.isRC_RD(dinuc))CDR1_RC_RD.addToScore(IGHV,cts);
                                if(Mutation.isTC_TD(dinuc))CDR1_TC_TD.addToScore(IGHV,cts);
                                if(Mutation.isCC_CD(dinuc))CDR1_CC_CD.addToScore(IGHV,cts);
                            }

                            if(pair[1]=='G'){
                                if (pair[0] == 'A') CDR1_deam.addToScore(IGHV, cts);
                                String[] trinucs = seqs.getContextForward(3);
                                String trinuc = trinucs[1] + "_" + trinucs[0];
                                String[] dinucs = seqs.getContextForward(2);
                                String dinuc = dinucs[1] + "_" + dinucs[0];
                                if(Mutation.isWRC_WRT(trinuc))CDR1_WRC_WRT.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTT(trinuc))CDR1_TTC_TTT.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCT(trinuc))CDR1_TCC_TCT.addToScore(IGHV,cts);
                                if(Mutation.isRC_RT(dinuc))CDR1_RC_RT.addToScore(IGHV,cts);
                                if(Mutation.isTC_TT(dinuc))CDR1_TC_TT.addToScore(IGHV,cts);
                                if(Mutation.isCC_CT(dinuc))CDR1_CC_CT.addToScore(IGHV,cts);
                                if(Mutation.isWRC_WRD(trinuc))CDR1_WRC_WRD.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTD(trinuc))CDR1_TTC_TTD.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCD(trinuc))CDR1_TCC_TCD.addToScore(IGHV,cts);
                                if(Mutation.isRC_RD(dinuc))CDR1_RC_RD.addToScore(IGHV,cts);
                                if(Mutation.isTC_TD(dinuc))CDR1_TC_TD.addToScore(IGHV,cts);
                                if(Mutation.isCC_CD(dinuc))CDR1_CC_CD.addToScore(IGHV,cts);
                            }
                        }
                    }
                    
                    if (region.equalsIgnoreCase("FWR2")){
                        FWR2_nt_cts.addToScore(IGHV, cts);
                        char[] pair = seqs.nextPair();
                        if (pair[0] != pair[1]){
                            if(pair[1]=='A'||pair[1]=='T')FWR2_ABTV.addToScore(IGHV, cts);
                            if(pair[1]=='C'||pair[1]=='G')FWR2_CDGH.addToScore(IGHV, cts);


                            if(pair[1]=='C'){
                                if (pair[0] == 'T') FWR2_deam.addToScore(IGHV, cts);
                                String[] trinucs = seqs.getContextReverse(3);
                                String trinuc = trinucs[1] + "_" + trinucs[0];
                                String[] dinucs = seqs.getContextReverse(2);
                                String dinuc = dinucs[1] + "_" + dinucs[0];
                                if(Mutation.isWRC_WRT(trinuc))FWR2_WRC_WRT.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTT(trinuc))FWR2_TTC_TTT.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCT(trinuc))FWR2_TCC_TCT.addToScore(IGHV,cts);
                                if(Mutation.isRC_RT(dinuc))FWR2_RC_RT.addToScore(IGHV,cts);
                                if(Mutation.isTC_TT(dinuc))FWR2_TC_TT.addToScore(IGHV,cts);
                                if(Mutation.isCC_CT(dinuc))FWR2_CC_CT.addToScore(IGHV,cts);
                                if(Mutation.isWRC_WRD(trinuc))FWR2_WRC_WRD.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTD(trinuc))FWR2_TTC_TTD.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCD(trinuc))FWR2_TCC_TCD.addToScore(IGHV,cts);
                                if(Mutation.isRC_RD(dinuc))FWR2_RC_RD.addToScore(IGHV,cts);
                                if(Mutation.isTC_TD(dinuc))FWR2_TC_TD.addToScore(IGHV,cts);
                                if(Mutation.isCC_CD(dinuc))FWR2_CC_CD.addToScore(IGHV,cts);
                            }

                            if(pair[1]=='G'){
                                if (pair[0] == 'A') FWR2_deam.addToScore(IGHV, cts);
                                String[] trinucs = seqs.getContextForward(3);
                                String trinuc = trinucs[1] + "_" + trinucs[0];
                                String[] dinucs = seqs.getContextForward(2);
                                String dinuc = dinucs[1] + "_" + dinucs[0];
                                if(Mutation.isWRC_WRT(trinuc))FWR2_WRC_WRT.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTT(trinuc))FWR2_TTC_TTT.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCT(trinuc))FWR2_TCC_TCT.addToScore(IGHV,cts);
                                if(Mutation.isRC_RT(dinuc))FWR2_RC_RT.addToScore(IGHV,cts);
                                if(Mutation.isTC_TT(dinuc))FWR2_TC_TT.addToScore(IGHV,cts);
                                if(Mutation.isCC_CT(dinuc))FWR2_CC_CT.addToScore(IGHV,cts);
                                if(Mutation.isWRC_WRD(trinuc))FWR2_WRC_WRD.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTD(trinuc))FWR2_TTC_TTD.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCD(trinuc))FWR2_TCC_TCD.addToScore(IGHV,cts);
                                if(Mutation.isRC_RD(dinuc))FWR2_RC_RD.addToScore(IGHV,cts);
                                if(Mutation.isTC_TD(dinuc))FWR2_TC_TD.addToScore(IGHV,cts);
                                if(Mutation.isCC_CD(dinuc))FWR2_CC_CD.addToScore(IGHV,cts);
                            }
                        }
                    }
                    
                    if (region.equalsIgnoreCase("CDR2")){
                        CDR2_nt_cts.addToScore(IGHV, cts);
                        char[] pair = seqs.nextPair();
                        if (pair[0] != pair[1]){
                            if(pair[1]=='A'||pair[1]=='T')CDR2_ABTV.addToScore(IGHV, cts);
                            if(pair[1]=='C'||pair[1]=='G')CDR2_CDGH.addToScore(IGHV, cts);


                            if(pair[1]=='C'){
                                if (pair[0] == 'T') CDR2_deam.addToScore(IGHV, cts);
                                String[] trinucs = seqs.getContextReverse(3);
                                String trinuc = trinucs[1] + "_" + trinucs[0];
                                String[] dinucs = seqs.getContextReverse(2);
                                String dinuc = dinucs[1] + "_" + dinucs[0];
                                if(Mutation.isWRC_WRT(trinuc))CDR2_WRC_WRT.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTT(trinuc))CDR2_TTC_TTT.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCT(trinuc))CDR2_TCC_TCT.addToScore(IGHV,cts);
                                if(Mutation.isRC_RT(dinuc))CDR2_RC_RT.addToScore(IGHV,cts);
                                if(Mutation.isTC_TT(dinuc))CDR2_TC_TT.addToScore(IGHV,cts);
                                if(Mutation.isCC_CT(dinuc))CDR2_CC_CT.addToScore(IGHV,cts);
                                if(Mutation.isWRC_WRD(trinuc))CDR2_WRC_WRD.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTD(trinuc))CDR2_TTC_TTD.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCD(trinuc))CDR2_TCC_TCD.addToScore(IGHV,cts);
                                if(Mutation.isRC_RD(dinuc))CDR2_RC_RD.addToScore(IGHV,cts);
                                if(Mutation.isTC_TD(dinuc))CDR2_TC_TD.addToScore(IGHV,cts);
                                if(Mutation.isCC_CD(dinuc))CDR2_CC_CD.addToScore(IGHV,cts);
                            }

                            if(pair[1]=='G'){
                                if (pair[0] == 'A') CDR2_deam.addToScore(IGHV, cts);
                                String[] trinucs = seqs.getContextForward(3);
                                String trinuc = trinucs[1] + "_" + trinucs[0];
                                String[] dinucs = seqs.getContextForward(2);
                                String dinuc = dinucs[1] + "_" + dinucs[0];
                                if(Mutation.isWRC_WRT(trinuc))CDR2_WRC_WRT.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTT(trinuc))CDR2_TTC_TTT.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCT(trinuc))CDR2_TCC_TCT.addToScore(IGHV,cts);
                                if(Mutation.isRC_RT(dinuc))CDR2_RC_RT.addToScore(IGHV,cts);
                                if(Mutation.isTC_TT(dinuc))CDR2_TC_TT.addToScore(IGHV,cts);
                                if(Mutation.isCC_CT(dinuc))CDR2_CC_CT.addToScore(IGHV,cts);
                                if(Mutation.isWRC_WRD(trinuc))CDR2_WRC_WRD.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTD(trinuc))CDR2_TTC_TTD.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCD(trinuc))CDR2_TCC_TCD.addToScore(IGHV,cts);
                                if(Mutation.isRC_RD(dinuc))CDR2_RC_RD.addToScore(IGHV,cts);
                                if(Mutation.isTC_TD(dinuc))CDR2_TC_TD.addToScore(IGHV,cts);
                                if(Mutation.isCC_CD(dinuc))CDR2_CC_CD.addToScore(IGHV,cts);
                            }
                        }
                    }                    
                    
                    if (region.equalsIgnoreCase("FWR3")){
                        FWR3_nt_cts.addToScore(IGHV, cts);
                        char[] pair = seqs.nextPair();
                        if (pair[0] != pair[1]){
                            if(pair[1]=='A'||pair[1]=='T')FWR3_ABTV.addToScore(IGHV, cts);
                            if(pair[1]=='C'||pair[1]=='G')FWR3_CDGH.addToScore(IGHV, cts);


                            if(pair[1]=='C'){
                                if (pair[0] == 'T') FWR3_deam.addToScore(IGHV, cts);
                                String[] trinucs = seqs.getContextReverse(3);
                                String trinuc = trinucs[1] + "_" + trinucs[0];
                                String[] dinucs = seqs.getContextReverse(2);
                                String dinuc = dinucs[1] + "_" + dinucs[0];
                                if(Mutation.isWRC_WRT(trinuc))FWR3_WRC_WRT.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTT(trinuc))FWR3_TTC_TTT.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCT(trinuc))FWR3_TCC_TCT.addToScore(IGHV,cts);
                                if(Mutation.isRC_RT(dinuc))FWR3_RC_RT.addToScore(IGHV,cts);
                                if(Mutation.isTC_TT(dinuc))FWR3_TC_TT.addToScore(IGHV,cts);
                                if(Mutation.isCC_CT(dinuc))FWR3_CC_CT.addToScore(IGHV,cts);
                                if(Mutation.isWRC_WRD(trinuc))FWR3_WRC_WRD.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTD(trinuc))FWR3_TTC_TTD.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCD(trinuc))FWR3_TCC_TCD.addToScore(IGHV,cts);
                                if(Mutation.isRC_RD(dinuc))FWR3_RC_RD.addToScore(IGHV,cts);
                                if(Mutation.isTC_TD(dinuc))FWR3_TC_TD.addToScore(IGHV,cts);
                                if(Mutation.isCC_CD(dinuc))FWR3_CC_CD.addToScore(IGHV,cts);
                            }

                            if(pair[1]=='G'){
                                if (pair[0] == 'A') FWR3_deam.addToScore(IGHV, cts);
                                String[] trinucs = seqs.getContextForward(3);
                                String trinuc = trinucs[1] + "_" + trinucs[0];
                                String[] dinucs = seqs.getContextForward(2);
                                String dinuc = dinucs[1] + "_" + dinucs[0];
                                if(Mutation.isWRC_WRT(trinuc))FWR3_WRC_WRT.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTT(trinuc))FWR3_TTC_TTT.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCT(trinuc))FWR3_TCC_TCT.addToScore(IGHV,cts);
                                if(Mutation.isRC_RT(dinuc))FWR3_RC_RT.addToScore(IGHV,cts);
                                if(Mutation.isTC_TT(dinuc))FWR3_TC_TT.addToScore(IGHV,cts);
                                if(Mutation.isCC_CT(dinuc))FWR3_CC_CT.addToScore(IGHV,cts);
                                if(Mutation.isWRC_WRD(trinuc))FWR3_WRC_WRD.addToScore(IGHV,cts);
                                if(Mutation.isTTC_TTD(trinuc))FWR3_TTC_TTD.addToScore(IGHV,cts);
                                if(Mutation.isTCC_TCD(trinuc))FWR3_TCC_TCD.addToScore(IGHV,cts);
                                if(Mutation.isRC_RD(dinuc))FWR3_RC_RD.addToScore(IGHV,cts);
                                if(Mutation.isTC_TD(dinuc))FWR3_TC_TD.addToScore(IGHV,cts);
                                if(Mutation.isCC_CD(dinuc))FWR3_CC_CD.addToScore(IGHV,cts);
                            }
                        }
                    }                    
      
                }
            }
        } catch (Exception e){
            System.out.println("Error in Main script!" + e);
        }
        
        //load the return array with scorekeepers
        results.add(seq_cts);

        results.add(FWR1_nt_cts);
        results.add(FWR1_ABTV);
        results.add(FWR1_CDGH);
        results.add(FWR1_deam);
        results.add(FWR1_RC_RT);
        results.add(FWR1_TC_TT);
        results.add(FWR1_CC_CT);
        results.add(FWR1_WRC_WRT);
        results.add(FWR1_TTC_TTT);
        results.add(FWR1_TCC_TCT);
        results.add(FWR1_RC_RD);
        results.add(FWR1_TC_TD);
        results.add(FWR1_CC_CD);
        results.add(FWR1_WRC_WRD);
        results.add(FWR1_TTC_TTD);
        results.add(FWR1_TCC_TCD);

        results.add(CDR1_nt_cts);
        results.add(CDR1_ABTV);
        results.add(CDR1_CDGH);
        results.add(CDR1_deam);
        results.add(CDR1_RC_RT);
        results.add(CDR1_TC_TT);
        results.add(CDR1_CC_CT);
        results.add(CDR1_WRC_WRT);
        results.add(CDR1_TTC_TTT);
        results.add(CDR1_TCC_TCT);
        results.add(CDR1_RC_RD);
        results.add(CDR1_TC_TD);
        results.add(CDR1_CC_CD);
        results.add(CDR1_WRC_WRD);
        results.add(CDR1_TTC_TTD);
        results.add(CDR1_TCC_TCD);
        
        results.add(FWR2_nt_cts);
        results.add(FWR2_ABTV);
        results.add(FWR2_CDGH);
        results.add(FWR2_deam);
        results.add(FWR2_RC_RT);
        results.add(FWR2_TC_TT);
        results.add(FWR2_CC_CT);
        results.add(FWR2_WRC_WRT);
        results.add(FWR2_TTC_TTT);
        results.add(FWR2_TCC_TCT);
        results.add(FWR2_RC_RD);
        results.add(FWR2_TC_TD);
        results.add(FWR2_CC_CD);
        results.add(FWR2_WRC_WRD);
        results.add(FWR2_TTC_TTD);
        results.add(FWR2_TCC_TCD);

        results.add(CDR2_nt_cts);
        results.add(CDR2_ABTV);
        results.add(CDR2_CDGH);
        results.add(CDR2_deam);
        results.add(CDR2_RC_RT);
        results.add(CDR2_TC_TT);
        results.add(CDR2_CC_CT);
        results.add(CDR2_WRC_WRT);
        results.add(CDR2_TTC_TTT);
        results.add(CDR2_TCC_TCT);
        results.add(CDR2_RC_RD);
        results.add(CDR2_TC_TD);
        results.add(CDR2_CC_CD);
        results.add(CDR2_WRC_WRD);
        results.add(CDR2_TTC_TTD);
        results.add(CDR2_TCC_TCD);

        results.add(FWR3_nt_cts);
        results.add(FWR3_ABTV);
        results.add(FWR3_CDGH);
        results.add(FWR3_deam);
        results.add(FWR3_RC_RT);
        results.add(FWR3_TC_TT);
        results.add(FWR3_CC_CT);
        results.add(FWR3_WRC_WRT);
        results.add(FWR3_TTC_TTT);
        results.add(FWR3_TCC_TCT);
        results.add(FWR3_RC_RD);
        results.add(FWR3_TC_TD);
        results.add(FWR3_CC_CD);
        results.add(FWR3_WRC_WRD);
        results.add(FWR3_TTC_TTD);
        results.add(FWR3_TCC_TCD);
        
        return results;
    }
    
}
