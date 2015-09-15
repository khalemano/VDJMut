/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khalemano.vdjmut.utilities;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author kalani
 */
public class Regions {
    FastaDatabase aligned = new FastaDatabase("/home/kejun/MiSeq/Reference_db_analysis/C57BL6_IMGT_VH_germline_shortID_aligned.fa");
    
    //Start and stop 1-based indexes for the framework and cdr regions
    //in the reference sequences aligned to other reference sequences
    int alignedFWR1Start = 1;
    int alignedFWR1End   = 78; 
    int alignedFWR2Start = 112;
    int alignedFWR2End   = 162;
    int alignedFWR3Start = 193;
    int alignedFWR3End   = 306;    
    
    //Corresponding 1-based indexes in the query sequence aligned
    //to its reference sequence
    int queryFWR1Start;
    int queryFWR1End; 
    int queryFWR2Start;
    int queryFWR2End;
    int queryFWR3Start;
    int queryFWR3End;    

    
    public void setRegions(String seq, String ighv){
        String reference = aligned.getSequence(ighv);
        
        queryFWR1Start=1;
        
        queryFWR1End=findSubsequenceInSequenceBackwards(seq,
                getSubsequenceBackwards(reference,alignedFWR1End,10));

        queryFWR2Start=findSubsequenceInSequenceBackwards(seq,
                getSubsequenceBackwards(reference,alignedFWR2Start,10));        
        
        queryFWR2End=findSubsequenceInSequenceBackwards(seq,
                getSubsequenceBackwards(reference,alignedFWR2End,10));
        
        queryFWR3Start=findSubsequenceInSequenceBackwards(seq,
                getSubsequenceBackwards(reference,alignedFWR3Start,10));        
        
        queryFWR3End=findSubsequenceInSequenceBackwards(seq,
                getSubsequenceBackwards(reference,alignedFWR3End,10));
        
    }
    
    public String getRegion(int pos){
            if(pos >= queryFWR1Start && pos <= queryFWR1End){
                return "FWR1";
            } else if (pos < queryFWR2Start){
                return "CDR1";
            } else if (pos <= queryFWR2End){
                return "FWR2";
            } else if (pos < queryFWR3Start){
                return "CDR2";
            } else if (pos <= queryFWR3End){
                return "FWR3";
            } else {
                return "CDR3";
            }
    }
    
    /***
     * Finds a subsequence of a desired length that ends at the specified
     * 1-based index
     * @param seq sequence to subsequence
     * @param index the 1-based index that ENDS the subsequence
     * @param length the length of the subsequence
     * @return subsequence of nucleotides
     */
    public String getSubsequenceBackwards(String seq, int index, int length){
        char[] seqArray = seq.toUpperCase().toCharArray();
        
        StringBuilder bases = new StringBuilder();
        
        int startIndex = index-1;
        while (bases.length() < length && startIndex >= 0){
            char base = seqArray[startIndex];
            if (base == 'A' || base == 'T' || base == 'C' || base == 'G'){
                bases.insert(0, base);
            }
            startIndex--;
        }
        
        return bases.toString();
    }
    
    public int findSubsequenceInSequenceBackwards(String seq, String subsequence){
        
        for (int i =1; i<=seq.length();i++){
            String query = getSubsequenceBackwards(seq,i,10);
            if(query.equalsIgnoreCase(subsequence)){
                return i;
            }
        }
        return -1;
    }
 
    
    // This is a method I wrote to create a region table
    // for an unrelated analysis.
    // I just placed it here in case I need it again later
    // The region table will indicate the 1-based position of the last
    // nucleotide for each region
    public static void createRegionTable(){
        FastaFile data = new FastaFile("/home/kejun/MiSeq/Reference_db_analysis"
                + "/C57BL6_IMGT_VH_germline_shortID_aligned.fa");
        
        Pattern refPattern = Pattern.compile(">([\\w\\-\\*]+)");
        
        try(BufferedWriter writer = 
            Files.newBufferedWriter(Paths.get("/home/kejun/MiSeq/Reference_db_analysis/VH_germline_regionTable.csv"),StandardCharsets.UTF_8)){
            //write out the header
            writer.write("ighv,FWR1,CDR1,FWR2,CDR2,FWR3");
            writer.newLine();
            
            while(data.hasNext()){
                //arraylist to hold the output string
                ArrayList<String> result = new ArrayList<>();
                ArrayList<String> regions = new ArrayList<>();
                //find the ighv name from the description line
                Matcher refMatcher = refPattern.matcher(data.getCurrentDesc());
                if (refMatcher.find()){
                    result.add(refMatcher.group(1));
                } else {
                    result.add("ighv unknown");
                }
                //FWR1
                regions.add(data.getCurrentSeq().substring(0, 78));
                //CDR1
                regions.add(data.getCurrentSeq().substring(78, 111));
                //FWR2
                regions.add(data.getCurrentSeq().substring(111, 162));
                //CDR2
                regions.add(data.getCurrentSeq().substring(162, 192));
                //FWR3
                regions.add(data.getCurrentSeq().substring(192, 306));

                for(String reg : regions){
                    Integer ct = SeqProcess.onlyATCG(reg).length();
                    result.add(ct.toString());
                }
                //create line for printing
                String line = "";
                //append results to string
                for(String res : result){
                    line = line + res + ",";
                }
                writer.write(line);
                writer.newLine();
            }
        }catch (IOException e){
            System.out.println(e);
        }
        
    }
    
}
