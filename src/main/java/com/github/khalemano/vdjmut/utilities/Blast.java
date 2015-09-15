package com.github.khalemano.vdjmut.utilities;


import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
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
public class Blast {
    /**
     * 
     * @param seq
     * @return The name of the IGHV region
     */
    public static String findIGHV (String seq){
        String result = "";
        String command = "blastn -task blastn -max_target_seqs 1 " + 
            "-db /home/kejun/MiSeq/Reference_databases/blast_databases/" +
            "C57BL6_IMGT_VH_germline_shortID.fa";

        try{
            //Open a process to execute the blastn command
            Process p = Runtime.getRuntime().exec(command);
            
            //Creates a BufferedWriter to output the sequence string to blastn
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                p.getOutputStream()));
            
            //Creates BufferedReader to accept the error stream of blastn
            BufferedReader err = new BufferedReader(
                new InputStreamReader(p.getErrorStream()));            
            
            //Creates BufferedReader to accept the output of blastn
            BufferedReader in = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            
            //sends stdin to blastn
            seq = ">test\n" + seq;
            out.write(seq);
            out.flush();
            out.close();
            
            String line;
            Pattern IGHVpattern = Pattern.compile("mIGHV\\w+-\\w+\\*\\w+");
            while ((line = in.readLine()) != null){
                   if(line.contains("mIGHV")){
                       Matcher IGHVmatcher = IGHVpattern.matcher(line);
                       IGHVmatcher.find();
                       result = IGHVmatcher.group();
                       break;
                   }
            }
        } catch (IOException e){
            System.out.println(e);
        }
        
      return result;
    }
    
    
    public static String findIGHC (String seq){
        String result = "";
        String command = "blastn -task blastn -max_target_seqs 1 " + 
            "-db /home/kejun/MiSeq/Reference_databases/blast_databases/" +
            "C57BL6_IMGT_CH_germline.fa";

        try{
            //Open a process to execute the blastn command
            Process p = Runtime.getRuntime().exec(command);
            
            //Creates a BufferedWriter to output the sequence string to blastn
            BufferedWriter out = new BufferedWriter(new OutputStreamWriter(
                p.getOutputStream()));
            
            //Creates BufferedReader to accept the error stream of blastn
            BufferedReader err = new BufferedReader(
                new InputStreamReader(p.getErrorStream()));            
            
            //Creates BufferedReader to accept the output of blastn
            BufferedReader in = new BufferedReader(
                new InputStreamReader(p.getInputStream()));
            
            //sends stdin to blastn
            seq = ">test\n" + seq;
            out.write(seq);
            out.flush();
            out.close();
            
            String line;
            Pattern IGHCpattern = Pattern.compile("IGH\\w+");
            while ((line = in.readLine()) != null){
                   if(line.contains("IGH")){
                       Matcher IGHCmatcher = IGHCpattern.matcher(line);
                       IGHCmatcher.find();
                       result = IGHCmatcher.group();
                       break;
                   }
            }
        } catch (IOException e){
            System.out.println(e);
        }
        
      return result;
    }
}