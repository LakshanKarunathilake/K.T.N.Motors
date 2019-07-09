/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Purchaising;

import DBController.DataBaseConnector;
import DataManipulation.DataManipulation;
import DataManipulation.MyCombo;
import DataManipulation.Rounding;
import ViewManipulation.ViewManipulation;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author lakshan
 */
public class Purchaise {
    
    JComboBox itemNo_combo;
    JComboBox retailers_combo;
    
    JTextField description_txt;
    JLabel cost_lbl;
    JLabel selling_lbl;
    JLabel available_lbl;
    JTextField billPrice_txt;
    JTextField costP_txt;
    JTextField sellingP_txt;
    JTextField qty_txt;
    JTextField invoiceNo_txt;
    
    DataBaseConnector connector;
     
    String itemNo;
    String description;
    String cost;
    String selling;
    String available_qty;
    
    private static Purchaise purchaise;
    
    private Purchaise(){
        
    }
    
    public static Purchaise getInstance(){
        if(purchaise == null)
            purchaise = new Purchaise();
        return purchaise;
    }
    
    
    
   public void setFields(JComboBox itemNo_combo,JComboBox retailers_combo,JTextField invoiceNo_txt,JTextField description_txt,JTextField billPrice_txt,JTextField costP_txt,JLabel cost_lbl,JTextField sellingP_txt,JLabel selling_lbl,JLabel available_lbl,JTextField qty_txt,DataBaseConnector connector){
       this.itemNo_combo = itemNo_combo;
       this.retailers_combo = retailers_combo;
       this.connector = connector;
       
       this.description_txt = description_txt;
       this.costP_txt = costP_txt;
       this.sellingP_txt = sellingP_txt;
       this.cost_lbl = cost_lbl;
       this.selling_lbl = selling_lbl;
       this.available_lbl = available_lbl; 
       this.qty_txt = qty_txt;
       this.billPrice_txt = billPrice_txt;
       this.invoiceNo_txt = invoiceNo_txt;
       
//       populateFields();
       
   } 
   
   public void autoFill(){
       itemNo_combo.removeAllItems();
       DataManipulation dm = new DataManipulation(connector);
       dm.getRecords("items", "item_code", itemNo_combo);
       
       retailers_combo.removeAllItems();
       dm.getRecords("retailers", "name", retailers_combo);
       
//       MyCombo combo1 = new MyCombo();
//       combo1.setSearchableCombo(itemNo_combo, false, "No such item exit");
//       combo1.moveFocusToNext(itemNo_combo, billPrice_txt);


       AutoCompleteDecorator.decorate(itemNo_combo);
       AutoCompleteDecorator.decorate(retailers_combo);
       
       ViewManipulation.moveFocusToNext(itemNo_combo, billPrice_txt);
       ViewManipulation.moveFocusToNext(retailers_combo, invoiceNo_txt);
   }
   
   public void retailerAutoFill(){
       DataManipulation dm = new DataManipulation(connector);
       retailers_combo.removeAllItems();
       dm.getRecords("retailers", "name", retailers_combo);
       
   }
   
   private void populateFields(){
       
       String itemNo = String.valueOf(itemNo_combo.getSelectedItem());

//       ArrayList list = connector.readRow("items", "item_code", itemNo);
//
//       description = String.valueOf(list.get(4));
//       cost = String.valueOf(list.get(6));
//       selling = String.valueOf(list.get(7));
//       available_qty = String.valueOf(list.get(5));
//       
//       description_txt.setText(description);
//       cost_lbl.setText(cost);
//       selling_lbl.setText(selling);
//       available_lbl.setText(available_qty);
   }
   
   public boolean updateQty_save(){
        int updateQty = Integer.parseInt(qty_txt.getText());
        int avilableQty = Integer.parseInt(available_lbl.getText());
        int nowQty = updateQty + avilableQty;
        
        String itemNo = String.valueOf(itemNo_combo.getSelectedItem());
        String cost = cost_lbl.getText();
        String selling = selling_lbl.getText();
        
        String retailer = String.valueOf(retailers_combo.getSelectedItem());
        String retailer_id = connector.getRelavantRecord("retailers", "retailer_id", "name", retailer);
        String invoice_id = invoiceNo_txt.getText();

        Timestamp now = new Timestamp(System.currentTimeMillis());
        String timeStamp = String.valueOf(now);

        ArrayList list = new ArrayList();
        list.add(itemNo);
        list.add(updateQty);
        list.add(billPrice_txt.getText());
        list.add(timeStamp);
        list.add(retailer_id);
        list.add(invoice_id);
        try {
            connector.editRecordInTable("items", "item_code", "stock",String.valueOf(nowQty),itemNo);
            connector.editRecordInTable("items", "item_code", "cost", cost,itemNo);
            connector.editRecordInTable("items", "item_code", "selling",selling,itemNo);
            connector.insertRecord("purchaising", list);
            updateClearFields();
            return true;
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Error in Qty updating" + e.getMessage());
            return false;
        }
        
    }
   
   public void updateClearFields() {
        billPrice_txt.setText("");
        description_txt.setText("");        
        available_lbl.setText("");
        qty_txt.setText("");
    }
   
    public void updateQTyPanel() {
        ArrayList list = connector.readRow("items", "item_code", String.valueOf(itemNo_combo.getSelectedItem()));

        description_txt.setText(String.valueOf(list.get(1)));
        cost_lbl.setText(String.valueOf(list.get(2)));
        selling_lbl.setText(String.valueOf(list.get(3)));
        available_lbl.setText(String.valueOf(list.get(4)));

    }
    
    public String calculateCost(){
        double costP = Double.parseDouble(costP_txt.getText());
        double bill_price = Double.parseDouble(billPrice_txt.getText());
        
        double cost = bill_price - (bill_price*(costP/100));
        String cost_text = String.valueOf(Rounding.roundCommon(cost, 0));
        
        String itemno = String.valueOf(itemNo_combo.getSelectedItem());
        double previous_cost = Double.parseDouble(connector.getRelavantRecord("items", "cost", "item_code", itemno));
        if(cost > previous_cost){
//            int showConfirmDialog = JOptionPane.showConfirmDialog(null, "The previous cost is lower than current do you want to proceed","Warning",JOptionPane.YES_NO_OPTION);
//            
//            if(showConfirmDialog == JOptionPane.NO_OPTION){
//                return null;
//            }

            JOptionPane.showConfirmDialog(null, "The previous cost is lower than current do you want to proceed", "Warning", JOptionPane.CANCEL_OPTION);
        }
        
        return cost_text; 
    }
    
    public String calculateSelling(){
        double cost = Double.valueOf(cost_lbl.getText());
        double selling_p = Double.valueOf(sellingP_txt.getText());
        
        double selling = cost + (cost*(selling_p/100));
        String selling_text = String.valueOf(Rounding.RoundTo5(selling, true));
        
        String item_no = String.valueOf(itemNo_combo.getSelectedItem());
        double previous_selling = Double.parseDouble(connector.getRelavantRecord("items", "selling", "item_code", item_no));
        if(previous_selling<selling){
//            int showConfirmDialog = JOptionPane.showConfirmDialog(null, "Your previous selling value is lower than this do you want to proceed","Warning",JOptionPane.YES_NO_OPTION);
//            if(showConfirmDialog == JOptionPane.NO_OPTION){
//                return null;
//            }
            JOptionPane.showConfirmDialog(null, "Your previous selling value is lower than this do you want to proceed", "Warning", JOptionPane.CANCEL_OPTION);
        }
        return selling_text;
        
    }
    
    public boolean comparePrices(){
        double cost = Double.valueOf(cost_lbl.getText());
        double selling = Double.valueOf(selling_lbl.getText());
        
        if(selling>cost){
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Selling price is < than cost price.. please check");
            return false;
        }
        
    }
   
   
}
