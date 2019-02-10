/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import DBController.DataBaseConnector;
import java.util.ArrayList;

/**
 *
 * @author KTN-PC
 */
public class EditCustomer {

    
    private static EditCustomer editCustomer;
    
    private DataBaseConnector connector;
    
    private EditCustomer(DataBaseConnector connector){
       this.connector = connector;
       changeCustomers();
    }
    
    public static EditCustomer getInstance(DataBaseConnector connector){
         if(editCustomer == null)
            editCustomer = new EditCustomer(connector);
        return editCustomer; 
    }
    
    private void changeCustomers(){
        ArrayList<String[]> retreveDataColumn = connector.retreveDataColumn2("customers", "name","customer_code");
        
        for (int i = 0; i < retreveDataColumn.size(); i++) {
            String temp[] = retreveDataColumn.get(i);
            String name = temp[0];
            String id = temp[1];
            String first2Chars = name.substring(0, Math.min(name.length(), 2));
            String first3Chars = name.substring(0, Math.min(name.length(), 3));
            String first4Chars = name.substring(0, Math.min(name.length(), 4));
            
            if(first2Chars.equals("MR")){
                name = name.substring(3);
                System.out.println("Name :"+name);
                System.out.println("ID :"+id);
                updateName(name, id);
                
            }else if(first3Chars.equals("M/S")){
                 name = name.substring(4);
                System.out.println("Name :"+name);
                System.out.println("ID :"+id);
                updateName(name, id);
            }else if(first4Chars.equals("REV.")){
                name = name.substring(5);
                System.out.println("Name :"+name);
                System.out.println("ID :"+id);
                updateName(name, id);
            }
            
        
        }
    }
    
    
    private void updateName(String name, String id){
        if(connector.editRecordInTable("customers", "customer_code", "name", name, id)){
            System.out.println("Name change success id:"+id+" to name: "+name);
        }
    }
    
}
