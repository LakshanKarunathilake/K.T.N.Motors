/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManipulation;

import java.math.BigDecimal;
import java.math.RoundingMode;
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
    
    public static double roundCommon(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }
        
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        
        
        return bd.doubleValue();
//    }
    }
    
    public static String decimalFormatiing(double val){
        DecimalFormat df = new DecimalFormat("#.00");
        String formatedVal = df.format(val);
        return formatedVal;
    }
    
}
