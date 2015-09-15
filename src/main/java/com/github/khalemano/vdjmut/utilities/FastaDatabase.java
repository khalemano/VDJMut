package com.github.khalemano.vdjmut.utilities;


import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kejun
 */
public class FastaDatabase {
    private HashMap<String,String> database = new HashMap();
    
    public FastaDatabase(String pathString){
        Path path = Paths.get(pathString);
        
        Pattern pattern = Pattern.compile(">\\s*(\\S+)");
        try(Scanner scanner = new Scanner(path)){
            while(scanner.hasNextLine()){
                if (scanner.hasNext(">.*")){
                    String line1 = scanner.nextLine();
                    String line2 = scanner.nextLine();

                    Matcher matcher = pattern.matcher(line1);
                    matcher.find();


                    database.put(matcher.group(1),line2.trim().toUpperCase());
                }
            }
        } catch (IOException e){
            System.out.println(e);
        }
    }
    
    public String getSequence(String key){
        return database.get(key);
    }
    
}
