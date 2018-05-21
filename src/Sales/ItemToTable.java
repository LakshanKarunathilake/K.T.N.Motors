/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import DBController.DataBaseConnector;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author lakshan
 */
public class ItemToTable {
    
    JComboBox itemNo ;
    JTable table;
    DataBaseConnector connector;
    JTextField qtyText;
    JTextField aQtyText;
    JTextField totalTxt;
    JTextField discountTxt;
    JTextField grandTxt;
    String item_code;
    
    DecimalFormat df = new DecimalFormat("#.00");
    
    public ItemToTable(JComboBox itemNo,JTextField qtyText,JTextField aQtyText,JTextField totalTxt,JTextField discountTxt,JTextField grandTxt,JTable table,DataBaseConnector connector){
        this.itemNo = itemNo;
        this.table = table;
        this.connector = connector;
        this.qtyText = qtyText;
        this.aQtyText = aQtyText;
        this.totalTxt = totalTxt;
        this.grandTxt = grandTxt;
        this.discountTxt = discountTxt;
    }
    
    
    public boolean itemToTable() {

        ArrayList list = new ArrayList();
        list = connector.readRow("items", "item_code", String.valueOf(itemNo.getSelectedItem()));

        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object rowData[] = new Object[6];

        rowData[0] = list.get(0); //Item Number to rowData
        rowData[1] = list.get(4); //description to rowData

        String unitP = (String) list.get(7);
        Double unitPrice = Double.parseDouble(unitP);
        
        
        
        rowData[3] = unitP;
        
        rowData[2] = list.get(9);
        

        if (qtyValidation()) {

            int qty = Integer.parseInt(qtyText.getText());
            rowData[4] = qtyText.getText();

            double totalPrice = qty * unitPrice;
            
               
            
            rowData[5] = df.format(totalPrice);
            model.addRow(rowData);
            return true;
        } else {
            return false;
        }

    }
    
    public boolean qtyValidation() {
        int availableQty = Integer.parseInt(aQtyText.getText());
        int requestedQty = Integer.parseInt(qtyText.getText());
        String item_number = (String) this.itemNo.getSelectedItem();

        if (checkItemIsInTable(item_number)) {
            int dialogResult = JOptionPane.showConfirmDialog(null, "Item is already added to the table Do you want to update the qty by :" + qtyText.getText(), "Warning", JOptionPane.YES_NO_OPTION);

            if (dialogResult == JOptionPane.YES_OPTION) {
                addQtyToTheExistingRecord(item_number, qtyText.getText());
                itemNo.requestFocusInWindow();
                calculatetotal();

            } else if (dialogResult == JOptionPane.NO_OPTION) {
                itemNo.requestFocusInWindow();
                calculatetotal();

            }
            return false;

        } else if (availableQty < requestedQty) {
            int number = 0;
            int dialogResult = JOptionPane.showConfirmDialog(null, "Don't have that much stock do you want to add the maximum qty in stock :" + availableQty, "Warning", number);

            if (dialogResult == JOptionPane.YES_OPTION) {
                qtyText.setText(aQtyText.getText());
                calculatetotal();
                return true;
            } else if (dialogResult == JOptionPane.NO_OPTION) {
                qtyText.requestFocusInWindow();
                calculatetotal();

                return false;
            }
        }
        return true;
    }
    
    public boolean checkItemIsInTable(String value){
       int rowID = findIntheTable(value);
       if(rowID==Integer.MAX_VALUE){
           return false;
       }else{
           return true;
       }
        
    }
    
    public int findIntheTable(String value){
        TableModel model = table.getModel();        
        int rowCount = model.getRowCount();
        

        for (int i = 0; i < rowCount; i++) {
            String id = (String) model.getValueAt(i, 0);
            if (id.equals(value)) {
                
                return i;
            }
        }
        return Integer.MAX_VALUE;
    }
    
    public void addQtyToTheExistingRecord(String itemNo,String qty){
        int rowNo = findIntheTable(itemNo);
        TableModel model = table.getModel();
       
       
        String qtyNowString = String.valueOf(model.getValueAt(rowNo, 4));
        int qtyNow = Integer.parseInt(qtyNowString);
        int totalQty  = qtyNow + Integer.parseInt(qty);
        int availability = Integer.parseInt(aQtyText.getText());
        if(totalQty > availability){
            JOptionPane.showMessageDialog(null, "The maximum qty is set");
            totalQty = availability;
        }
        model.setValueAt(totalQty, rowNo, 4); 
        updateTotal(rowNo,totalQty);
           
    }
    
    public void updateTotal(int rowNo, int totalQty) {
        TableModel model = table.getModel();
        double d = Double.parseDouble(String.valueOf(model.getValueAt(rowNo, 3)));
        double total = d * totalQty;
        BigDecimal bd = new BigDecimal(total);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        model.setValueAt(bd, rowNo, 5);

    }
    
    public void calculatetotal() {
        TableModel model = table.getModel();
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            String t1 = String.valueOf(model.getValueAt(i, 5));
            total += Double.parseDouble(t1);

        }
//        BigDecimal bd = new BigDecimal(total);
//        bd = bd.setScale(2, RoundingMode.HALF_UP);

        String totalString = df.format(total);
        totalTxt.setText(totalString);
        calculateGrandTotal();
    }
    
    public void calculateGrandTotal(){
        double total = Double.parseDouble(totalTxt.getText());
        double discount = Double.parseDouble(discountTxt.getText());
        double grand = total-(total*(discount/100));
        grand = round(grand,2);
        
        
        grandTxt.setText(df.format(grand));
    }
    
    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
