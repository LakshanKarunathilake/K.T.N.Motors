/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManipulation;

import java.io.FileReader;
import javax.swing.JOptionPane;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 *
 * @author manual-pc
 */
public class JSONReading {
    private static JSONReading jsonReading;
    
    private JSONReading(){
       
    }
    
    public static JSONReading getInstance(){
        if(jsonReading == null )
            jsonReading = new JSONReading();
        return jsonReading;
    }
    
    public String readFile(){
        JSONParser parser = new JSONParser();
        try{
//            FileReader fr = new FileReader("K:\\DAD\\Front Inventory\\2018-07-15\\K.T.N.Motors\\data.json");
            
            Object obj = parser.parse(new FileReader("K:\\DAD\\Front Inventory\\2018-07-15\\K.T.N.Motors\\data.json"));
            JSONObject jsonObject = (JSONObject) obj;
            String name = (String) jsonObject.get("ReportReference");
//            JOptionPane.showMessageDialog(null, "The read value is : "+name);
            return name;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "ERROR Caught "+e.getMessage());
        }
        return null; 
        
    }
    
    public String getReportLocation(){
        return readFile();
    }
}
