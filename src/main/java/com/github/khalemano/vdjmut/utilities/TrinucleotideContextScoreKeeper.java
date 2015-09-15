/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.github.khalemano.vdjmut.utilities;

import java.util.HashMap;
import java.util.TreeSet;

/**
 *
 * @author kalani
 */
public class TrinucleotideContextScoreKeeper {
    HashMap<String,HashMap> data = new HashMap<>();
    
    public void add(String ighv, String context, int cts){
        if (!data.containsKey(ighv)){
            data.put(ighv, new HashMap<String,Integer>());
            data.get(ighv).put(context, cts);
        } else if (!data.get(ighv).containsKey(context)){
            data.get(ighv).put(context, cts);
        } else {
            int temp =(int)data.get(ighv).get(context);
            data.get(ighv).put(context, temp + cts);
        }
    }
    
    public int getScore(String ighv, String context){
        if (!data.containsKey(ighv)){
            return 0;
        } else if (!data.get(ighv).containsKey(context)){
            return 0;
        } else {
            return (int)data.get(ighv).get(context);
        }
    }
    
    public TreeSet getRowSet(){
        return new TreeSet(data.keySet());
    }
}
