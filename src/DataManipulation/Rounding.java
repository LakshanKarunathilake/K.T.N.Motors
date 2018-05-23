/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManipulation;

import java.text.DecimalFormat;

/**
 *
 * @author lakshan
 */
public class Rounding {
    public static String RoundTo5(double value,boolean shouldFormat){
        double result;
        DecimalFormat df = new DecimalFormat("#.00");
        if ((value % 5) >= 2.5) {            
            result = 5 * (Math.ceil(Math.abs(value / 5)));
        } else {            
            result = 5 * (Math.floor(Math.abs(value / 5)));
        }
        System.out.println("Val :" + result);
        if(shouldFormat){            
            return df.format(result);
        }else{
            
            return String.valueOf(result);
        }
        
    }
    
     public static void RoundTo5(String value){
        
    }
}
