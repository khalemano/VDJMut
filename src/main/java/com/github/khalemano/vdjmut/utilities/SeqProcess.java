package com.github.khalemano.vdjmut.utilities;


import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalani
 */
public class SeqProcess {

    public static String onlyATCG (String seq){
        String seqcopy = seq.toUpperCase();
        String resultseq = "";
        Matcher ATCGMatcher = 
                Pattern.compile("[ATCG]*").matcher(seqcopy); 
        while (ATCGMatcher.find()){
            resultseq = resultseq + ATCGMatcher.group();
        }
        return resultseq;
    }
    
    public static ArrayList<String> breakIntoCodons(String seq){
        ArrayList<String> codonList = new ArrayList<>();
        
        //the following code clips off the remaining 1 or 2 nucleotides
        String seqCopy = seq.substring(0,seq.length()-(seq.length()%3));
        
        for (int i = 0; i<seqCopy.length(); i+=3){
            codonList.add(seqCopy.substring(i, i+3));
        }
        return codonList;
    }
    
}
