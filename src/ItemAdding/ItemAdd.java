/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ItemAdding;

import DBController.DataBaseConnector;
import DataManipulation.DataManipulation;
import DataManipulation.MyCombo;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author lakshan
 */
class ItemAdd {
   
    JTextField itemNo_txt;
    JTextField vehicle_txt;
    JTextField brand_txt;
    JTextField description_txt;
    
    JTextField price_txt;
    JTextField costP_txt;
    JTextField cost_txt;
    JTextField sellingP_txt;
    JTextField selling_txt;
    
    JComboBox category_combo;
    DataBaseConnector connector;
    
    private static ItemAdd item_add=null;
    
    public void setFields(JTextField itemNo_txt,JTextField vehicle_txt,JTextField brand_txt,JTextField description_txt,JComboBox category_combo,DataBaseConnector connector){
       this.itemNo_txt = itemNo_txt;
       this.vehicle_txt =vehicle_txt;     
       this.brand_txt = brand_txt;
       this.description_txt = description_txt;
       this.category_combo = category_combo;
       
       this.connector = connector;
       populateCombo();       
    }
    
    public void setPriceFields(JTextField price_txt,JTextField costP_txt,JTextField cost_txt,JTextField sellingP_txt,JTextField selling_txt){
        this.price_txt = price_txt;
        this.costP_txt = costP_txt;
        this.cost_txt = cost_txt;
        this.sellingP_txt = sellingP_txt;
        this.selling_txt = selling_txt;
    }
    
    public static ItemAdd getInstance(){
        if(item_add == null)
            item_add = new ItemAdd();
        return item_add;
    }
    
    public void CheckAvailability(){
        String itemNo = itemNo_txt.getText();
        String availability = connector.getRelavantRecord("items", "item_code", "item_code", itemNo);
        if(availability != null){
            JOptionPane.showMessageDialog(null, "Item Number Already in the database");
            itemNo_txt.requestFocusInWindow();
        }
    }
    
    private void autoComplete(){
        MyCombo combo1 = new MyCombo();
        combo1.setSearchableCombo(category_combo, true, "No such Category");
    }
    
    private void populateCombo(){
        DataManipulation dm = new DataManipulation(connector);
        dm.getRecords("category", "catName", category_combo);
        autoComplete();
    }
    
    public  void changeStateAddItem(Boolean b) {
        itemNo_txt.setEditable(b);
        vehicle_txt.setEditable(b);
        description_txt.setEditable(b);
        brand_txt.setEditable(b);
        costP_txt.setEditable(b);
        sellingP_txt.setEditable(b);
        price_txt.setEditable(b);
    }
}
