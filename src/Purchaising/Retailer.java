/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Purchaising;

import DBController.DataBaseConnector;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author manual-pc
 */
public class Retailer {
    private static Retailer retailer;
    
    JTextField name_txt;
    JTextField address_txt;
    JTextField telephone_txt;
    
    DataBaseConnector connector;
            
    private Retailer(){
        
    }
    
    public static Retailer getInstance(){
        if(retailer == null)
            retailer = new Retailer();
        return retailer;
                    
        
    }
    
    public void setFields(JTextField name_txt,JTextField address_txt,JTextField telephone_txt,DataBaseConnector connector){
        this.name_txt = name_txt;
        this.address_txt = address_txt;
        this.telephone_txt = telephone_txt;
        
        this.connector = connector;
    }
    
    
    public void saveRetailer(){
        String name = name_txt.getText();
        String address = address_txt.getText();
        String telephone = telephone_txt.getText();
        
        ArrayList<String> columns = new ArrayList();
        ArrayList<String> data = new ArrayList();
        
        columns.add("name");
        columns.add("address");
        columns.add("telephone");
        
        data.add(name);
        data.add(address);
        data.add(telephone);       

        
        if(connector.insertRecordColoumnCount("retailers", data, columns)){
            JOptionPane.showMessageDialog(null, "Retailer added success");
        }else{
            JOptionPane.showMessageDialog(null, "Retailure Adding Failure");
        }
    }
    
    public void emptyFields(){
        name_txt.setText("");
        address_txt.setText("");
        telephone_txt.setText("");
    }
    
    
    
    
    
    
    
}
