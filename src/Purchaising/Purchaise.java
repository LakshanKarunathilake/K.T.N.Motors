/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Purchaising;

import DBController.DataBaseConnector;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author lakshan
 */
public class Purchaise {
    
    static JComboBox itemNo_combo;
    
    static JTextField description_txt;
    static JTextField cost_txt;
    static JTextField selling_txt;
    static JLabel available_lbl;
    
    static DataBaseConnector connector;
     
    static String itemNo;
    static String description;
    static String cost;
    static String selling;
    static String available_qty;
    
    private static Purchaise purchaise;
    
    private Purchaise(){}
    
    public Purchaise getInstance(){
        if(purchaise == null)
            purchaise = new Purchaise();
        return purchaise;
    }
    
    public void 
    
   public static void setFields(JComboBox itemNo_combo,JTextField description_txt,JTextField cost_txt,JTextField selling_txt,JLabel available_lbl,DataBaseConnector connector){
       Purchaise.itemNo_combo = itemNo_combo;
       Purchaise.connector = connector;
       
       Purchaise.description_txt = description_txt;
       Purchaise.cost_txt = cost_txt;
       Purchaise.selling_txt = selling_txt;
       Purchaise.available_lbl = available_lbl;       
       
   } 
   
   public static void populateFields(){
       itemNo = String.valueOf(itemNo_combo.getSelectedItem());

       ArrayList list = connector.readRow("items", "item_code", itemNo);

       description = String.valueOf(list.get(4));
       cost = String.valueOf(list.get(6));
       selling = String.valueOf(list.get(7));
       available_qty = String.valueOf(list.get(5));
       
       description_txt.setText(description);
       cost_txt.setText(cost);
       selling_txt.setText(selling);
       available_lbl.setText(available_qty);
   }
   
   
}
