package com.github.khalemano.vdjmut.scripts;



import com.github.khalemano.vdjmut.utilities.Translator;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
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
public class SortPseudogenesOutOfFastaStript {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Pattern ctsPattern = Pattern.compile("Cts=(\\d+)");
        int lowCts = 0;
        int lowPseudoCts = 0;
        int highPseudoCts = 0;
        int totalCts = 0;
        int ctsAtEnd = 0;
        String sampleName = "431N-MM1";
        
        try(BufferedReader fastaReader = new BufferedReader(
                new FileReader("/home/kejun/MiSeq/Amplicon_Run16/Kalani/" +
                        sampleName + "/Contigs/" +
                        sampleName + "_Contigs_Unique.fasta"));
                BufferedWriter fastaWriter = new BufferedWriter(
                new FileWriter("/home/kejun/MiSeq/Amplicon_RunB/Kalani/ProcessedUniqueFastas/"+
                        sampleName + "_Unique.fasta"));)
        {
           String line1, line2;
           while((line1=fastaReader.readLine())!=null && line1.length()!=0){
               line2 = fastaReader.readLine();
               totalCts++;
               boolean keep = true;
               
               Matcher ctsMatcher = ctsPattern.matcher(line1);
               if (ctsMatcher.find()){
                   if (Integer.parseInt(ctsMatcher.group(1)) <2){
                       keep = false;
                       lowCts++;
                   }                   
               } else {
                   keep = false;
                   lowCts++;
               }

               if (Translator.countStopCodons(line2, 1) != 0 &&
                       Translator.countStopCodons(line2, 2) != 0 &&
                       Translator.countStopCodons(line2, 3) != 0){
                   if (keep){
                       highPseudoCts++;
                   } else {
                       lowPseudoCts++;
                   }
                   keep = false;
               }
               
               if (keep){
                   fastaWriter.write(line1 + "\n");
                   fastaWriter.write(line2 + "\n");
                   ctsAtEnd++;
               }
            }
        
        }catch (FileNotFoundException e){
            System.out.println(e);
        }catch (IOException e){
            System.out.println(e);
        }
        
        System.out.println("Sample : " + sampleName);
        System.out.println("Total Count : " + totalCts);
        System.out.println("Below Threshold % : " + (100*lowCts/totalCts));
        System.out.println("Above Threshold Pseudogene % : " + (100*highPseudoCts/(totalCts-lowCts)));
        System.out.println("Below Threshold Pseudogene % : " + (100*lowPseudoCts/lowCts));
        System.out.println("Count at end: " + ctsAtEnd);
    }
    
}
