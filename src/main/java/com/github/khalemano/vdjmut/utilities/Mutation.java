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
public class Mutation {
    
//These detect a C changing into a T
    public static boolean isWRC_WRT(String concat) {
        if (concat.length() == 7) {
            if (concat.matches("^([AT][AG])C_\\1T$")) {
                return true;
            } else if (concat.matches("^G([CT][TA])_A\\1$")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isRC_RT(String concat) {

        if (concat.length()==5) {
            if (concat.matches("^([AG])C_\\1T$")) {
                return true;
            } else if (concat.matches("^G([CT])_A\\1$")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTTC_TTT(String concat) {

        if (concat.length() == 7) {
            switch (concat) {
                case "TTC_TTT":
                    return true;
                case "GAA_AAA":
                    return true;
            }
        }

        return false;
    }

    public static boolean isTC_TT(String concat) {

        if (concat.length() == 5) {
            switch (concat) {
                case "TC_TT":
                    return true;
                case "GA_AA":
                    return true;
            }
        }
        return false;
    }

    public static boolean isTCC_TCT(String concat) {

        if (concat.length() == 7) {
            switch (concat) {
                case "TCC_TCT":
                    return true;
                case "GGA_AGA":
                    return true;
            }
        }
        return false;
    }

    public static boolean isCC_CT(String concat) {

        if (concat.length() == 5) {
            switch (concat) {
                case "CC_CT":
                    return true;
                case "GG_AG":
                    return true;
            }
        }
        return false;
    }
    
//These detect a C changing into anyother base
    public static boolean isWRC_WRD(String concat){
        if (concat.length() == 7) {
            if (concat.matches("^([AT][AG])C_\\1[GAT]$")) {
                return true;
            } else if (concat.matches("^G([CT][TA])_[CAT]\\1$")) {
                return true;
            }
        }
        return false;
    }
    
    public static boolean isRC_RD(String concat) {

        if (concat.length() == 5) {
            if (concat.matches("^([AG])C_\\1[GAT]$")) {
                return true;
            } else if (concat.matches("^G([CT])_[CAT]\\1$")) {
                return true;
            }
        }

        return false;
    }
    
    public static boolean isTTC_TTD(String concat) {
        if (concat.length() == 7) {
            if (concat.matches("^TTC_TT[GAT]$")) {
                return true;
            } else if (concat.matches("^GAA_[CAT]AA$")) {
                return true;
            }
        }
        return false;
    }

    public static boolean isTC_TD(String concat) {

        if (concat.length() == 5) {
            if (concat.matches("^TC_T[GAT]$")) {
                return true;
            } else if (concat.matches("^GA_[CAT]A$")) {
                return true;
            }
        }

        return false;
    }

    public static boolean isTCC_TCD(String concat) {

        if (concat.length() == 7) {
            if (concat.matches("^TCC_TC[GAT]$")) {
                return true;
            } else if (concat.matches("^GGA_[CAT]GA$")) {
                return true;
            }
        }

        return false;
    }

    public static boolean isCC_CD(String concat) {

        if (concat.length() == 5) {
            if (concat.matches("^CC_C[GAT]$")) {
                return true;
            } else if (concat.matches("^GG_[CAT]G$")) {
                return true;
            }
        }

        return false;
    }
    
    
    
    
}
