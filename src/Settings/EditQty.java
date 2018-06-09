/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import DBController.DataBaseConnector;
import DataManipulation.DataManipulation;
import ViewManipulation.ViewManipulation;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author manual-pc
 */
public class EditQty {
    private static EditQty editQty;
    
    JComboBox itemNo_combo;
    JLabel description_lbl;JLabel available_lbl;
    JTextField qty_txt;
    
    DataBaseConnector connector;
    
    boolean b = true;
    
    private EditQty(){
        
    }
    
    public static EditQty getInstance(){
        if(editQty==null)
            editQty = new EditQty();
        return editQty;
    }
    
    public void setFields(JComboBox itemNo_combo,JLabel description_lbl,JLabel available_lbl,JTextField qty_txt,DataBaseConnector connector){
        this.itemNo_combo = itemNo_combo;
        this.description_lbl =description_lbl;
        this.available_lbl = available_lbl;
        this.qty_txt = qty_txt;
        
        this.connector = connector; 
        actionForCombo();
        if(b){
           autoFill();
           b = false;
        }
        
    }
    
    public void autoFill(){
       itemNo_combo.removeAllItems();
       DataManipulation dm = new DataManipulation(connector);
       dm.getRecords("items", "item_code", itemNo_combo);
       
       AutoCompleteDecorator.decorate(itemNo_combo);
       
       
       ViewManipulation.moveFocusToNext(itemNo_combo, qty_txt);
      
    }
    
    private void actionForCombo(){
        itemNo_combo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String item_no = String.valueOf(itemNo_combo.getSelectedItem());
                if(item_no != null){
                    String description = connector.getRelavantRecord("items", "description", "item_code", item_no);
                    String stock = connector.getRelavantRecord("items", "stock", "item_code", item_no);
                    description_lbl.setText(description);
                    available_lbl.setText(stock);
                }
            }
        });
    }
    
    public void saveQtyEdit(){
        String qty = qty_txt.getText();
        if(connector.editRecordInTable("items", "item_code", "stock", qty, String.valueOf(itemNo_combo.getSelectedItem()))){
            JOptionPane.showMessageDialog(null, "Qty Edited Successfully");
        }else{
            JOptionPane.showMessageDialog(null, "Qty Edit Failure");
        }
    }
    
}
