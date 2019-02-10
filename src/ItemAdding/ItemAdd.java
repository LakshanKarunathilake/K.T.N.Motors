/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ItemAdding;

import DBController.DataBaseConnector;
import DataManipulation.DataManipulation;
import DataManipulation.MyCombo;
import DataManipulation.Rounding;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/**
 *
 * @author lakshan
 */
public class ItemAdd {
   
    JTextField itemNo_txt;
    JTextField vehicle_txt;
    JTextField brand_txt;
    JTextField description_txt;
    JTextField location_txt;
    
    JTextField price_txt;
    JTextField costP_txt;
    JTextField cost_txt;
    JTextField sellingP_txt;
    JTextField selling_txt;
    
    JComboBox category_combo;
    JComboBox unit_combo;
            
    DataBaseConnector connector;
    
    private static ItemAdd item_add=null;
    
    public void setFields(JTextField itemNo_txt,JTextField vehicle_txt,JTextField brand_txt,JTextField description_txt,JTextField location_txt,JComboBox category_combo,JComboBox unit_combo,DataBaseConnector connector){
       this.itemNo_txt = itemNo_txt;
       this.vehicle_txt =vehicle_txt;     
       this.brand_txt = brand_txt;
       this.description_txt = description_txt;
       this.location_txt =  location_txt;
       
       this.category_combo = category_combo;
       this.unit_combo = unit_combo;
       
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
    
    public boolean CheckAvailability(){
        String itemNo = itemNo_txt.getText();
        String availability = connector.getRelavantRecord("items", "item_code", "item_code", itemNo);
        if(availability != null){
            JOptionPane.showMessageDialog(null, "Item Number Already in the database");
            itemNo_txt.setText("");
            itemNo_txt.requestFocusInWindow();
            return true;
        }else{
            return false;
        }
    }
    
    private void autoComplete(){
        MyCombo combo1 = new MyCombo();
        combo1.setSearchableCombo(category_combo, true, "No such Category");
        combo1.moveFocusToNext(category_combo, vehicle_txt);
    }
    
    private void populateCombo(){
        DataManipulation dm = new DataManipulation(connector);
        dm.getRecords("category", "catName", category_combo);
        autoComplete();
    }
    
    public  void changeStateAddItem(Boolean b) {
        itemNo_txt.setEditable(b);
        vehicle_txt.setEditable(b);        
        brand_txt.setEditable(b);
        costP_txt.setEditable(b);
        sellingP_txt.setEditable(b);
        price_txt.setEditable(b);
        location_txt.setEditable(b);
        category_combo.setEnabled(b);
        unit_combo.setEnabled(b);        
    }
    
    public void emptyItemFields(){
        itemNo_txt.setText("");
        vehicle_txt.setText("");
        description_txt.setText("");
        costP_txt.setText("");
        cost_txt.setText("");
        sellingP_txt.setText("");
        selling_txt.setText("");
        price_txt.setText("");
        itemNo_txt.setText("");
        unit_combo.setSelectedIndex(0);
        brand_txt.setText("");
        location_txt.setText("");
        
    }
    
    public boolean itemSave(boolean editable){
        calculateCost();
        calculateSelling();
        
        String itemNo;
        String category;
        String description;
        String cost;
        String selling;
        String vehicle;
        String brand;
        String location;
        String unit;

        itemNo = itemNo_txt.getText();
        category = String.valueOf(category_combo.getSelectedItem());
        brand = brand_txt.getText();
        vehicle = vehicle_txt.getText();
        description = description_txt.getText();
        cost = cost_txt.getText();
        selling = selling_txt.getText(); 
        
        if(location_txt.getText().isEmpty()){
            location = "undefined";
        }else{
            location = location_txt.getText();
        }
        
        unit = String.valueOf(unit_combo.getSelectedItem());
        
        
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String timeStamp = String.valueOf(now);
        
        ArrayList list = new ArrayList();
        
        ArrayList coloumn = new ArrayList();        
                
        boolean success = false;
        boolean success2 = false;
        
        if(editable){
            
            
            list.add(category);
            list.add(vehicle);
            list.add(brand);
            list.add(description);
            list.add(cost);
            list.add(selling);
            list.add(location);
            list.add(unit);
            list.add(now);
            
            
            coloumn.add("category");
            coloumn.add("vehicle");
            coloumn.add("brand");
            coloumn.add("description");
            coloumn.add("cost");
            coloumn.add("selling");
            coloumn.add("Loctn");
            coloumn.add("Unit");
            coloumn.add("last_edit_date");
            
            
            success2 = connector.editRecordWithColoumns("items","item_code",itemNo,list,coloumn);
        }else{
            list.add(itemNo);
            list.add(category);
            list.add(vehicle);
            list.add(brand);
            list.add(description);
            list.add(cost);
            list.add(selling);
            list.add(location);
            list.add(unit);
            list.add(now);
            list.add(now);
            
            coloumn.add("item_code");
            coloumn.add("category");
            coloumn.add("vehicle");
            coloumn.add("brand");
            coloumn.add("description");
            coloumn.add("cost");
            coloumn.add("selling");
            coloumn.add("Loctn");
            coloumn.add("Unit");
            coloumn.add("last_edit_date");
            coloumn.add("added_date");
            success = connector.insertRecordColoumnCount("items",list,coloumn);  
        }
       
        
        if(success){
            JOptionPane.showMessageDialog(null, "Item Successfull added...");
            success = false;
            changeStateAddItem(false);
            return true;
        } 
        if(success2){
            JOptionPane.showMessageDialog(null, "Item Successfull Edited...");
            success2 = false;
            editable = false;
            changeStateAddItem(false);
            return true;
        } 
        return false;
    }
    
    public void generateDescription(){
        String category = String.valueOf(category_combo.getSelectedItem());
        String vehicle = vehicle_txt.getText();
        String brand = brand_txt.getText();
        
        String description = category+" "+vehicle+" - "+brand;
        description_txt.setText(description);
    }
    
    public void calculateCost(){
        if(!(price_txt.getText().isEmpty()) && (!costP_txt.getText().isEmpty())){
            double bill_price = Double.parseDouble(price_txt.getText());
            double cost_discount = Double.parseDouble(costP_txt.getText());

            double cost = bill_price - (bill_price * (cost_discount / 100));
            cost = Rounding.roundCommon(cost, 0);
            cost_txt.setText(Rounding.decimalFormatiing(cost));
        }
        
    }
    
    public void calculateSelling(){
        if(!(cost_txt.getText().isEmpty()) && (!sellingP_txt.getText().isEmpty())){
            double cost = Double.parseDouble(cost_txt.getText());
            double selling_discount = Double.parseDouble(sellingP_txt.getText());
            double selling = cost + (cost * (selling_discount / 100));
            selling = Rounding.roundCommon(selling, 0);
            selling_txt.setText(Rounding.RoundTo5(selling, true));
        }
        
    }
    
    public void fillEdit(String itemNo) {
        ArrayList list = new ArrayList();
        list = connector.readRow("items", "item_code", itemNo);

        itemNo_txt.setFocusable(false);
        changeStateAddItem(true);

        itemNo_txt.setText(String.valueOf(list.get(0)));
        category_combo.setSelectedItem(String.valueOf(list.get(1)));
        vehicle_txt.setText(String.valueOf(list.get(2)));
        brand_txt.setText(String.valueOf(list.get(3)));
        description_txt.setText(String.valueOf(list.get(4)));
        cost_txt.setText(String.valueOf(list.get(6)));
        selling_txt.setText(String.valueOf(list.get(7)));
        location_txt.setText(String.valueOf(list.get(8)));
        unit_combo.setSelectedItem(String.valueOf(list.get(9)));

    }
}
