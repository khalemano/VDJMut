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
 * @param <T1>
 * @param <T2>
 */
public class TwoDScoreKeeper<T1,T2> {
    HashMap<T1,HashMap> data = new HashMap<>();

    public void add(T1 ighv, T2 position, int cts) {
        if (!data.containsKey(ighv)) {
            data.put(ighv, new HashMap<T2, Integer>());
            data.get(ighv).put(position, cts);
        } else if (!data.get(ighv).containsKey(position)) {
            data.get(ighv).put(position, cts);
        } else {
            int temp = (int) data.get(ighv).get(position);
            data.get(ighv).put(position, temp + cts);
        }
    }
    
    public int getScore(T1 ighv, T2 position){
        if (!data.containsKey(ighv)){
            return 0;
        } else if (!data.get(ighv).containsKey(position)){
            return 0;
        } else {
            return (int)data.get(ighv).get(position);
        }
    }
    
    public TreeSet getOuterKeySet(){
        return new TreeSet(data.keySet());
    }
    
    public TreeSet getInnerKeySet(T1 ighv){
        return new TreeSet(data.get(ighv).keySet());
    }    
}
