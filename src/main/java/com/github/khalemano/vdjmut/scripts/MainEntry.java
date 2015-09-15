package com.github.khalemano.vdjmut.scripts;


import com.github.khalemano.vdjmut.utilities.Fastapair;
import com.github.khalemano.vdjmut.utilities.FastapairFile;
import com.github.khalemano.vdjmut.utilities.Mutation;
import com.github.khalemano.vdjmut.utilities.ScoreKeeper;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Set;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

public class MainEntry {

    public static void main(String[] args) {
        
        //args = new String[]{"-i /this/is/a/path"};

        String[] inputFiles=null;
        String[] outputFiles=null;
        String[] addColumns=null;
        String[] columnValues=null;
        
        int threshold = 0;
        boolean collapse = false;
        
        Options options = new Options();
        options.addOption("c","collapse",false,"Process only distinct sequences");
        options.addOption("h","help",false,"Usage information");
        options.addOption("t","threshold",true,"Process sequences that occur above threshold");
        
         options.addOption(
                 Option.builder("i")
                .longOpt("input-files")
                .desc("Fastapair files to process")
                .required(true)
                .hasArgs()
                .build()
         );
         
        options.addOption(
                Option.builder("o")
                .longOpt("output-files")
                .desc("Names of outputfiles")
                .required(true)
                .hasArgs()
                .build()
        );
         
         options.addOption(
                 Option.builder("a")
                .longOpt("add-columns")
                .desc("Add a column with the same value for all rows")
                .required(false)
                .hasArgs()
                .build()
         );
         
        options.addOption(
                Option.builder("n")
                .longOpt("column-values")
                .desc("Values for added columns")
                .required(true)
                .hasArgs()
                .build()
        );
        
        //parse options
        CommandLineParser parser = new DefaultParser();
        try{
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("help")){
                HelpFormatter formatter = new HelpFormatter();
                formatter.printHelp("java -jar VDJMut.jar", options,true);    
            }
            if(cmd.hasOption("input-files")){
                inputFiles = cmd.getOptionValues("input-files");
            }
            if(cmd.hasOption("output-files")){
                outputFiles = cmd.getOptionValues("output-files");
            }
            if(inputFiles.length != outputFiles.length){
                throw new ParseException("Number of input files does not equal the number of output files");
            }
            if(cmd.hasOption("add-columns")){
                addColumns = cmd.getOptionValues("add-columns");
            }
            if(cmd.hasOption("column-values")){
                columnValues = cmd.getOptionValues("column-values");
            }
            if(addColumns != null | columnValues != null){
                if(addColumns.length != columnValues.length){
                    throw new ParseException("Number of added columns does not equal the number of column values");
                }
            }
            if (cmd.hasOption("collapse")){
                collapse=true;
            }
            if (cmd.hasOption("threshold")){
                threshold= Integer.parseInt(cmd.getOptionValue("threshold"));
            }
        } catch(ParseException exp){
            System.out.println("Unexpected exception: " + exp.getMessage());
            HelpFormatter formatter = new HelpFormatter();
            formatter.printHelp("java -jar VDJMut-jar-with-dependencies.jar", options,true);
            return;
        }

        for (int i = 0; i < inputFiles.length; i++) {
            try (PrintWriter writer = new PrintWriter(outputFiles[i], "UTF-8")) {

                //Creates and prints the header of the csv table
                String header = "ighv";
                if (addColumns != null){
                    for (String colName : addColumns){
                        header = header + "," + colName;
                    }
                }
                
                header = header + ",seq_cts,nt_cts,"
                        + "ABTV,CDGH,deam,RC_RT,TC_TT,CC_CT,WRC_WRT,"
                        + "TTC_TTT,TCC_TCT,RC_RD,TC_TD,CC_CD,WRC_WRD,TTC_TTD,TCC_TCD,"
                        + "A_T,A_C,A_G,T_A,T_C,T_G,C_A,C_T,C_G,G_A,G_T,G_C";
                writer.println(header);

                ArrayList<ScoreKeeper> scorekeepers = Analyze(inputFiles[i], threshold, collapse);

                Set<String> rows = scorekeepers.get(0).getRowSet();

                for (String name : rows) {
                    String line = name;
                    if (addColumns != null){
                        for (String val : columnValues){
                            line = line + "," + val;
                        }
                    }
                    for (ScoreKeeper sk : scorekeepers) {
                        line = line + "," + sk.getScore(name);
                    }
                    writer.println(line);
                }
            }catch (IOException e) {
                System.out.println(e);
            }
        }
    }
    
    public static ArrayList<ScoreKeeper> Analyze(String path, int ctsThreshold, boolean collapse){
        ArrayList<ScoreKeeper> results = new ArrayList<>();
        
        System.out.println("Analyzing: " + path);
        
        ScoreKeeper seq_cts = new ScoreKeeper("seq_cts");
        ScoreKeeper nt_cts = new ScoreKeeper("nt_cts");
        ScoreKeeper ABTV = new ScoreKeeper("ABTV");
        ScoreKeeper CDGH = new ScoreKeeper("CDGH");
        ScoreKeeper deam = new ScoreKeeper("deam");        
        ScoreKeeper RC_RT = new ScoreKeeper("RC_RT");
        ScoreKeeper TC_TT = new ScoreKeeper("TC_TT");
        ScoreKeeper CC_CT = new ScoreKeeper("CC_CT");
        ScoreKeeper WRC_WRT = new ScoreKeeper("WRC_WRT");
        ScoreKeeper TTC_TTT = new ScoreKeeper("TTC_TTT");
        ScoreKeeper TCC_TCT = new ScoreKeeper("TCC_TCT");        
        ScoreKeeper RC_RD = new ScoreKeeper("RC_RD");
        ScoreKeeper TC_TD = new ScoreKeeper("TC_TD");
        ScoreKeeper CC_CD = new ScoreKeeper("CC_CD");
        ScoreKeeper WRC_WRD = new ScoreKeeper("WRC_WRD");
        ScoreKeeper TTC_TTD = new ScoreKeeper("TTC_TTD");
        ScoreKeeper TCC_TCD = new ScoreKeeper("TCC_TCD");

        ScoreKeeper A_T = new ScoreKeeper("A_T");
        ScoreKeeper A_C = new ScoreKeeper("A_C");
        ScoreKeeper A_G = new ScoreKeeper("A_G");
        
        ScoreKeeper T_A = new ScoreKeeper("T_A");
        ScoreKeeper T_C = new ScoreKeeper("T_C");
        ScoreKeeper T_G = new ScoreKeeper("T_G");
        
        ScoreKeeper C_T = new ScoreKeeper("C_T");
        ScoreKeeper C_A = new ScoreKeeper("C_A");
        ScoreKeeper C_G = new ScoreKeeper("C_G");
        
        ScoreKeeper G_A = new ScoreKeeper("G_A");
        ScoreKeeper G_C = new ScoreKeeper("G_C");
        ScoreKeeper G_T = new ScoreKeeper("G_T");        
        
 try (FastapairFile file  = new FastapairFile(path)){
            
            while (file.hasNext()){
                Fastapair seqs = file.next();
                 
                /*Checks the count threshold
                If the fastapair count is at or above threshold,
                the program continues. If not, the fastapair is skipped
                After threshold checking, the cts is set to 1 if the dataset
                is to be collapsed*/
                int cts = seqs.getCount();
                if (cts < ctsThreshold) continue;
                if (collapse) cts = 1;
                
                //Obtains name of reference sequence
                String IGHV = seqs.getRefName();
                
                //Adds the sequence counts to the count-score
                seq_cts.addToScore(IGHV, cts);
                
                //sets a limit for how far we can read into the alignment
                int alignmentPosition = 0;
                int alignmentLimit = 265;
                //Analyze the fastapair for mutations
                while(seqs.hasNextNtPair()){
                    //increment the position to keep track of where we are 
                    //in the alignment, then check to see if we are past the 
                    //limit
                    alignmentPosition++;
                    if (alignmentPosition > alignmentLimit)continue;
                    
                    nt_cts.addToScore(IGHV, cts);
                    char[] pair = seqs.nextPair();
                    if (pair[0] != pair[1]){
                        if(pair[1]=='A'||pair[1]=='T')ABTV.addToScore(IGHV, cts);
                        if(pair[1]=='C'||pair[1]=='G')CDGH.addToScore(IGHV, cts);
                        
                        //the next four if statements check the single nucleotide
                        //mutation type and add it to the scorekeepers
                        if(pair[1]=='A'){
                            if(pair[0]=='T')A_T.addToScore(IGHV, cts);
                            if(pair[0]=='C')A_C.addToScore(IGHV, cts);
                            if(pair[0]=='G')A_G.addToScore(IGHV, cts);
                        }
                        
                        if(pair[1]=='T'){
                            if(pair[0]=='A')T_A.addToScore(IGHV, cts);
                            if(pair[0]=='C')T_C.addToScore(IGHV, cts);
                            if(pair[0]=='G')T_G.addToScore(IGHV, cts);
                        }
                        
                        if(pair[1]=='C'){
                            if(pair[0]=='T')C_T.addToScore(IGHV, cts);
                            if(pair[0]=='A')C_A.addToScore(IGHV, cts);
                            if(pair[0]=='G')C_G.addToScore(IGHV, cts);
                        }
                        
                        if(pair[1]=='G'){
                            if(pair[0]=='A')G_A.addToScore(IGHV, cts);
                            if(pair[0]=='C')G_C.addToScore(IGHV, cts);
                            if(pair[0]=='T')G_T.addToScore(IGHV, cts);
                        }
                        
                        if(pair[1]=='C'){
                            if (pair[0] == 'T') deam.addToScore(IGHV, cts);
                            String[] trinucs = seqs.getContextReverse(3);
                            String trinuc = trinucs[1] + "_" + trinucs[0];
                            String[] dinucs = seqs.getContextReverse(2);
                            String dinuc = dinucs[1] + "_" + dinucs[0];
                            if(Mutation.isWRC_WRT(trinuc))WRC_WRT.addToScore(IGHV,cts);
                            if(Mutation.isTTC_TTT(trinuc))TTC_TTT.addToScore(IGHV,cts);
                            if(Mutation.isTCC_TCT(trinuc))TCC_TCT.addToScore(IGHV,cts);
                            if(Mutation.isRC_RT(dinuc))RC_RT.addToScore(IGHV,cts);
                            if(Mutation.isTC_TT(dinuc))TC_TT.addToScore(IGHV,cts);
                            if(Mutation.isCC_CT(dinuc))CC_CT.addToScore(IGHV,cts);
                            if(Mutation.isWRC_WRD(trinuc))WRC_WRD.addToScore(IGHV,cts);
                            if(Mutation.isTTC_TTD(trinuc))TTC_TTD.addToScore(IGHV,cts);
                            if(Mutation.isTCC_TCD(trinuc))TCC_TCD.addToScore(IGHV,cts);
                            if(Mutation.isRC_RD(dinuc))RC_RD.addToScore(IGHV,cts);
                            if(Mutation.isTC_TD(dinuc))TC_TD.addToScore(IGHV,cts);
                            if(Mutation.isCC_CD(dinuc))CC_CD.addToScore(IGHV,cts);
                        }
                        
                        if(pair[1]=='G'){
                            if (pair[0] == 'A') deam.addToScore(IGHV, cts);
                            String[] trinucs = seqs.getContextForward(3);
                            String trinuc = trinucs[1] + "_" + trinucs[0];
                            String[] dinucs = seqs.getContextForward(2);
                            String dinuc = dinucs[1] + "_" + dinucs[0];
                            if(Mutation.isWRC_WRT(trinuc))WRC_WRT.addToScore(IGHV,cts);
                            if(Mutation.isTTC_TTT(trinuc))TTC_TTT.addToScore(IGHV,cts);
                            if(Mutation.isTCC_TCT(trinuc))TCC_TCT.addToScore(IGHV,cts);
                            if(Mutation.isRC_RT(dinuc))RC_RT.addToScore(IGHV,cts);
                            if(Mutation.isTC_TT(dinuc))TC_TT.addToScore(IGHV,cts);
                            if(Mutation.isCC_CT(dinuc))CC_CT.addToScore(IGHV,cts);
                            if(Mutation.isWRC_WRD(trinuc))WRC_WRD.addToScore(IGHV,cts);
                            if(Mutation.isTTC_TTD(trinuc))TTC_TTD.addToScore(IGHV,cts);
                            if(Mutation.isTCC_TCD(trinuc))TCC_TCD.addToScore(IGHV,cts);
                            if(Mutation.isRC_RD(dinuc))RC_RD.addToScore(IGHV,cts);
                            if(Mutation.isTC_TD(dinuc))TC_TD.addToScore(IGHV,cts);
                            if(Mutation.isCC_CD(dinuc))CC_CD.addToScore(IGHV,cts);
                        }
                    }
                }
            }
        } catch (Exception e){
            System.out.println("Error in Main script!" + e);
        }
        
        //load the return array with scorekeepers
        results.add(seq_cts);
        results.add(nt_cts);
        results.add(ABTV);
        results.add(CDGH);
        results.add(deam);
        results.add(RC_RT);
        results.add(TC_TT);
        results.add(CC_CT);
        results.add(WRC_WRT);
        results.add(TTC_TTT);
        results.add(TCC_TCT);
        results.add(RC_RD);
        results.add(TC_TD);
        results.add(CC_CD);
        results.add(WRC_WRD);
        results.add(TTC_TTD);
        results.add(TCC_TCD);

        results.add(A_T);
        results.add(A_C);
        results.add(A_G);
        results.add(T_A);
        results.add(T_C);
        results.add(T_G);
        results.add(C_A);
        results.add(C_T);
        results.add(C_G);
        results.add(G_A);
        results.add(G_T);
        results.add(G_C);
        
        return results;
    }
    
    
}
