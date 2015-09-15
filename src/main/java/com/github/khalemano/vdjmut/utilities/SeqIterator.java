package com.github.khalemano.vdjmut.utilities;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalani
 */
public class SeqIterator {
    private final char[] seq;
    private final String strSeq;
    private int pos;
    private int nexti;

    
    public SeqIterator(String sequence){
        seq = sequence.toUpperCase().toCharArray();
        strSeq = sequence.toUpperCase();
        pos = 0;
        nexti = 0;
    }
    
    public String getSequence(){
        return strSeq;
    }

    public static boolean isNuc(char x){
        return x=='A' || x=='T' || x=='C' || x=='G';
    }
    
    public void reset(){
        pos = 0;
        nexti = 0;
    }
    
    public int getSeqPosition(){
        return pos;
    }

    public int getAlignmentPosition(){
        return nexti-1;
    }
    
    public boolean hasNext(){
        return nexti<seq.length;
    }
    
    public char next(){
        char result;
        
        result = seq[nexti];
        
        nexti++;
        
        if (isNuc(result)){
            pos++;
        }
        
        return result;
    }
    
    public String getContextReverse(int length){
        String result = "";

        int i = nexti - 1;
        
        while (result.length() < length && i >= 0){
            if (isNuc(seq[i])){
                result = seq[i] + result;
            }
            i--;
        }
        
        return result;
    }
    
    public String getContextForward(int length){
        String result = "";

        int i = nexti - 1;
        
        while (result.length() < length && i < seq.length){
            if (isNuc(seq[i])){
                result = result + seq[i];
            }
            i++;
        }
        return result;
    }
}
