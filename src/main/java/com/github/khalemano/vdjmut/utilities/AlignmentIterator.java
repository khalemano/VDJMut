/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khalemano.vdjmut.utilities;

import java.util.ArrayList;

/**
 *
 * @author kalani
 */
public class AlignmentIterator {
    private ArrayList<String> nts;
    private ArrayList<SeqIterator> iterators;
    
    
    private String queryName;
    private String refName;
    private SeqIterator query;
    private SeqIterator ref;
    
    private char q;
    private char r;
    
    public AlignmentIterator(String... sequences){
        for (String seq : sequences){
            iterators.add(new SeqIterator(seq));
        }
    }
    
    
    private boolean hasNext(){
        boolean result = false;
        
        for (SeqIterator x: iterators){
            result = x.hasNext() && result;
        }
        
        return result;
    }
    
    
    public void setQueryName (String name){
        queryName = name;
    }
    
    public void setQuerySeq (String seq){
        query = new SeqIterator(seq);
    }
    
    public void setRefName (String name){
        refName = name;
    }
    
    public void setRefSeq (String seq){
        ref = new SeqIterator(seq);
    }
    
    public String getQueryName(){
        return queryName;
    }
    
    public String getRefName(){
        return refName;
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
