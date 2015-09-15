package com.github.khalemano.vdjmut.utilities;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import java.util.TreeSet;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalani
 */
public class ScoreKeeper {
    private final String category;
    private HashMap<String,Integer> data;
    
    public ScoreKeeper(String category){
        this.category = category;
        data = new HashMap();
    }
    
    public void addToScore(String name, int i){
        if (data.containsKey(name)){
            data.put(name, data.get(name) + i);
        } else {
            data.put(name, i);
        }
    }
    
    public String getCategory(){
        return this.category;
    }
    
    public TreeSet getRowSet(){
        return new TreeSet(data.keySet());
    }
    
    public int getScore(String name){
        if (data.containsKey(name)){
            return data.get(name);
        } else {
            return 0;
        }
    }
}
