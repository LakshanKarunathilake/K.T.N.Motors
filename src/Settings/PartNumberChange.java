/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import DBController.DataBaseConnector;
import DataManipulation.DataManipulation;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author manual-pc
 */
public class PartNumberChange {
    private static PartNumberChange partNumberChange;
    
    private JComboBox original;
    private JTextField changed;
    private DataBaseConnector connector;
    
    private boolean isDecorated = false;
    
    private PartNumberChange(){
        
    }
    
    public static PartNumberChange getInstance(){
        if(partNumberChange == null)
            partNumberChange = new PartNumberChange();
        return partNumberChange;
    }
    
    public void setFields(JComboBox original,JTextField changed,DataBaseConnector connector){
        this.original = original;
        this.changed = changed;
        this.connector = connector;
        
        decorateCombo();
    }
    
    public void decorateCombo(){
        original.removeAllItems();
        DataManipulation dm = new DataManipulation(connector);
        dm.getRecords("items", "item_code", original);
        if(!isDecorated){
            AutoCompleteDecorator.decorate(original);
            isDecorated = true;
        }
                
    }
    
    public boolean checkAvailability(){
        String new_partNumber = changed.getText();
        String result = connector.getRelavantRecord("items", "item_code", "item_code", new_partNumber);
        
        if(result == null){            
            return true;
        }else{
            JOptionPane.showMessageDialog(null, "Item number already exists so not a valid try","Warning",JOptionPane.WARNING_MESSAGE);
            return false;
        }
    }
    
    public void changeItemNumber(){
        
        String new_partNumber = changed.getText();
        String old_number = String.valueOf(original.getSelectedItem());
        if(checkAvailability()){
            
            if(changeValue(new_partNumber,old_number)){
                JOptionPane.showMessageDialog(null, "Item Number changed from "+old_number+" To "+new_partNumber+" Successfully..!","Successfull",JOptionPane.PLAIN_MESSAGE);
                decorateCombo();
            }else{
                JOptionPane.showMessageDialog(null, "Part Number change failure please check the part numbers","Failure",JOptionPane.ERROR_MESSAGE);
            }
           
            
        }
    }
    
    private boolean changeValue(String new_partNumber,String old_number){        
        try{
            connector.editRecordInTable("items", "item_code", "item_code",new_partNumber, old_number);
            connector.editRecordInTable("invoiceitems", "item_code", "item_code", new_partNumber, old_number);
            connector.editRecordInTable("sales_return", "item_code", "item_code",new_partNumber, old_number);
            return true;
        }catch(Exception e){
            JOptionPane.showMessageDialog(null, "Error in editing records "+e.getMessage());
            return false;
        }
    }
        
    
    
}
