package com.github.khalemano.vdjmut.utilities;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalani
 * This class is used to translate dna sequences into amino acid sequences
 */
public class Translator {
    private static final Map<String, String> universalCode;
    static{
        universalCode = new HashMap();
        universalCode.put("TCA","S"); //Serine
        universalCode.put("TCC","S"); //Serine
        universalCode.put("TCG","S"); //Serine
        universalCode.put("TCT","S"); //Serine
        
        universalCode.put("TTC","F"); //Phenylalanine
        universalCode.put("TTT","F"); //Phenylalanine
        universalCode.put("TTA","L"); //Leucine
        universalCode.put("TTG","L"); //Leucine
        
        universalCode.put("TAC","Y"); //Tyrosine
        universalCode.put("TAT","Y"); //Tyrosine
        universalCode.put("TAA","_"); //Stop
        universalCode.put("TAG","_"); //Stop
        
        universalCode.put("TGC","C"); //Cysteine
        universalCode.put("TGT","C"); //Cysteine
        universalCode.put("TGA","_"); //Stop
        universalCode.put("TGG","W"); //Tryptophan
        
        universalCode.put("CTA","L"); //Leucine
        universalCode.put("CTC","L"); //Leucine
        universalCode.put("CTG","L"); //Leucine
        universalCode.put("CTT","L"); //Leucine
        
        universalCode.put("CCA","P"); //Proline
        universalCode.put("CCC","P"); //Proline
        universalCode.put("CCG","P"); //Proline
        universalCode.put("CCT","P"); //Proline
        
        universalCode.put("CAC","H"); //Histidine
        universalCode.put("CAT","H"); //Histidine
        universalCode.put("CAA","Q"); //Glutamine
        universalCode.put("CAG","Q"); //Glutamine
        
        universalCode.put("CGA","R"); //Arginine
        universalCode.put("CGC","R"); //Arginine
        universalCode.put("CGG","R"); //Arginine
        universalCode.put("CGT","R"); //Arginine
        
        universalCode.put("ATA","I"); //Isoleucine
        universalCode.put("ATC","I"); //Isoleucine
        universalCode.put("ATT","I"); //Isoleucine
        universalCode.put("ATG","M"); //Methionine
        
        universalCode.put("ACA","T"); //Threonine
        universalCode.put("ACC","T"); //Threonine
        universalCode.put("ACG","T"); //Threonine
        universalCode.put("ACT","T"); //Threonine
        
        universalCode.put("AAC","N"); //Asparagine
        universalCode.put("AAT","N"); //Asparagine
        universalCode.put("AAA","K"); //Lysine
        universalCode.put("AAG","K"); //Lysine
        
        universalCode.put("AGC","S"); //Serine
        universalCode.put("AGT","S"); //Serine
        universalCode.put("AGA","R"); //Arginine
        universalCode.put("AGG","R"); //Arginine
        
        universalCode.put("GTA","V"); //Valine
        universalCode.put("GTC","V"); //Valine
        universalCode.put("GTG","V"); //Valine
        universalCode.put("GTT","V"); //Valine
        
        universalCode.put("GCA","A"); //Alanine
        universalCode.put("GCC","A"); //Alanine
        universalCode.put("GCG","A"); //Alanine
        universalCode.put("GCT","A"); //Alanine
        
        universalCode.put("GAC","D"); //Aspartic Acid
        universalCode.put("GAT","D"); //Aspartic Acid
        universalCode.put("GAA","E"); //Glutamic Acid
        universalCode.put("GAG","E"); //Glutamic Acid
        
        universalCode.put("GGA","G"); //Glycine
        universalCode.put("GGC","G"); //Glycine
        universalCode.put("GGG","G"); //Glycine
        universalCode.put("GGT","G"); //Glycine
    }
    public static String translateCodon(String a){
        return universalCode.get(a);
    }
    
    public static int countStopCodons(String seq, int phase){
        //uppercase and remove non-nucleotide characters
        String seqCopy = SeqProcess.onlyATCG(seq);
        
        //removes phase-1 nucleotides from seq start
        seqCopy = seqCopy.substring(phase - 1);
        
        //breaks seq into codons
        List<String> codons = SeqProcess.breakIntoCodons(seqCopy);
        
        //looks for stop codons and counts them in variable "hits"
        int hits = 0;
        for (String codon : codons){
            if (codon.equals("TAA") | codon.equals("TAG") 
                    | codon.equals("TGA")){
                hits++;
            }
        }
        return hits;
        
    }
}