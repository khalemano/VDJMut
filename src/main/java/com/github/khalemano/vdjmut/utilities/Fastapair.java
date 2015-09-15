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
public class Fastapair {
    private String queryName;
    private String refName;
    private int count;
    private SeqIterator query;
    private SeqIterator ref;
    
    private char q;
    private char r;
    
    public void setQueryName (String name){
        queryName = name;
    }
    
    public int getAlignmentPosition(){
        return ref.getAlignmentPosition();
    }
    
    public int getQueryPosition(){
        return query.getSeqPosition();
    }
    
    public int getRefPosition(){
        return ref.getSeqPosition();
    }
    
    //if need to know the FWR and CDR regions, set the 2nd arg to true
    //if not, set to false to improve performance
    public void setQuerySeq (String seq){
        query = new SeqIterator(seq);
    }
    
    public String getQuerySeq(){
        return query.getSequence();
    }
    
    public void setRefName (String name){
        refName = name;
    }
    
    public void setRefSeq (String seq){
        ref = new SeqIterator(seq);
    }
    
    public String getRefSeq(){
        return ref.getSequence();
    }
    
    public void setCount (int x){
        count = x;
    }
    
    public String getQueryName(){
        return queryName;
    }
    
    public String getRefName(){
        return refName;
    }
    
    public int getCount(){
        return count;
    }
    
    public boolean hasNextNtPair(){

        while (query.hasNext() && ref.hasNext()){
            
            q = query.next();
            r = ref.next();
            
            if (SeqIterator.isNuc(q) && SeqIterator.isNuc(r)){
                return true;
            }

        }
        
        return false;
    }
    
    public char[] nextPair(){
        char[] results = new char[2];
        results[0] = q;
        results[1] = r;
        return results;
    }
    
    public void reset(){
        query.reset();
        ref.reset();
    }
    
    public String[] getContextForward(int length){
        String[] results = new String[2];
        results[0] = query.getContextForward(length);
        results[1] = ref.getContextForward(length);
        return results;
    }
    
    public String[] getContextReverse(int length){
        String[] results = new String[2];
        results[0] = query.getContextReverse(length);
        results[1] = ref.getContextReverse(length);
        return results;
    }
    
}
