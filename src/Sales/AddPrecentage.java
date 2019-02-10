/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import DataManipulation.Rounding;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author manual-pc
 */
public class AddPrecentage {
    
    private static AddPrecentage addPrecentage;
    
    private JTable table;
    private double precentage;
    
    private AddPrecentage(){
        
    }
    
    public static AddPrecentage getInstance(){
        if(addPrecentage == null)
            addPrecentage = new AddPrecentage();
        return addPrecentage;
    }
    
    public void setTable(JTable table){
        this.table = table;
    }
    
    public void setPrecentage(String precentage){
        this.precentage = Double.parseDouble(precentage);
    }
    
    public void calculateTotal(JTextField totalField,JTextField discountField,JTextField grandTotalField){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            
            total+=Double.parseDouble(String.valueOf(model.getValueAt(i, 5)));
            totalField.setText(String.valueOf(total));
            double discount = Double.parseDouble(discountField.getText());
            double grandTotal = total - (total*(discount/100));
            grandTotalField.setText(String.valueOf(grandTotal));
        }
    }
    
    public void updateTable(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        for (int i = 0; i < model.getRowCount(); i++) {
            Object object = model.getValueAt(i, 6);
            System.out.println("Object"+object);
            if((object!=null) ){
                if((Boolean)object == true){
                    double unitPrice = Double.parseDouble(String.valueOf(model.getValueAt(i, 3)));
                    int qty = Integer.parseInt(String.valueOf(model.getValueAt(i, 4)));

                    unitPrice = unitPrice + (unitPrice * (precentage / 100));
                    unitPrice = Double.valueOf(Rounding.RoundTo5(unitPrice, true));

                    double total = unitPrice * qty;

                    model.setValueAt(unitPrice, i, 3);
                    model.setValueAt(total, i, 5);
                    model.setValueAt(false, i, 6);
                }
                
            }
        }        
    }
    
}
