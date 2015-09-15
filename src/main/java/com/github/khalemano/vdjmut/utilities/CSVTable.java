package com.github.khalemano.vdjmut.utilities;


import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Set;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author kalani
 */
public class CSVTable {
    public static void printToFile (String pathString, Set<String> rows, 
            ScoreKeeper... scorekeepers){
        Path path = Paths.get(pathString);

        try(BufferedWriter writer = 
                Files.newBufferedWriter(path,StandardCharsets.UTF_8)){

            //Creates and prints the header of the csv table
            String header = "name";
            for (ScoreKeeper sk : scorekeepers){
                header = header + "," + sk.getCategory();
            }
            writer.write(header);
            writer.newLine();
            
            for (String name : rows){
                String line = name;
                for (ScoreKeeper sk : scorekeepers){
                    line = line + "," + sk.getScore(name);
                }
                writer.write(line);
                writer.newLine();
            }
            
        }catch (IOException e){
            System.out.println(e);
        }
    }
    
}
