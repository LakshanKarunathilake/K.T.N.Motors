/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalesReturn;

import DBController.DataBaseConnector;
import DataManipulation.Rounding;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lakshan
 */
public class ReturnToDB {
    
    DataBaseConnector connector;
    
    String invoiceID;
    String return_type;
    JTable table;
    
    String userID;
    double invoiceTotalReturn
    
    public ReturnToDB(JComboBox invoiceID_combo,JComboBox type_combo,JTable table,DataBaseConnector connector){
        invoiceID = String.valueOf(invoiceID_combo.getSelectedItem());
        return_type = String.valueOf(type_combo.getSelectedItem());
        this.table = table;
        
        this.connector = connector;
    }
    
    private boolean isCustomerCash(){
         userID = connector.getRelavantRecord("invoices", "customer_code", "invoice_id", invoiceID);
         if(userID.equals("1")){
             return true;
         }else{
             return false;
         }
    }
    
    
    
    public void saveToDB(){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        for (int i = 0; i < model.getRowCount(); i++) {
            String itemNo = String.valueOf(model.getValueAt(i, 0));
            String bought_qty = String.valueOf(model.getValueAt(i, 4));
            String return_qty = String.valueOf(model.getValueAt(i, 3));

            Timestamp now = new Timestamp(System.currentTimeMillis());
            String timeStamp = String.valueOf(now);
            
            double total_sold = Double.parseDouble(getSoldPrice(itemNo));
            double selling_price = total_sold/Double.parseDouble(bought_qty);
            
            double returnAmount = Double.parseDouble(return_qty)* selling_price;
            

            ArrayList list = new ArrayList();
            list.add(invoiceID);
            list.add(itemNo);
            list.add(return_qty);
            list.add(timeStamp);
            list.add(return_type);
            list.add(Rounding.RoundTo5(returnAmount, true));
            
            ArrayList columns = new ArrayList();
            columns.add("invoice_id");
            columns.add("item_code");
            columns.add("qty");
            columns.add("date");
            columns.add("Reason");
            columns.add("amount");
            
            
            if((model.getValueAt(i, 5)!= null) && (!return_qty.equals("0"))){
                connector.insertRecordColoumnCount("sales_return", list, columns);

                ArrayList temp = connector.retreveDataColoumnWithTwoCondition("invoiceitems", "returnable_qty", "invoice_id", invoiceID, "item_code", itemNo);
                String ta = String.valueOf(temp.get(0));
                int current_returnable = Integer.valueOf(ta);
                current_returnable -= Integer.valueOf(return_qty);
                changeReturnableQty(itemNo, current_returnable);

                if (isCustomerCash()) {
                    if (return_type.equals("Not Suitable")) {
                        uncompatibleAction(return_qty, itemNo);
                    } else if (return_type.equals("Damaged Replacing")) {
                        damageReplaceAction(return_qty, itemNo);
                    }
                } else {
                    //Creit user invoice and its not paid
                    String paidOrNot = connector.getRelavantRecord("invoices", "status", "invoice_id", invoiceID);
                    
                    if (paidOrNot.equals("0")) {
                        if (return_type.equals("Not Suitable")) {
                            uncompatibleAction(return_qty, itemNo);

                        } else if (return_type.equals("Damaged Replacing")) {
                            damageReplaceAction(return_qty, itemNo);
                        }
                    }else{
                        
                        if (return_type.equals("Not Suitable")) {
                            uncompatibleAction(return_qty, itemNo);
                        } else if (return_type.equals("Damaged Replacing")) {
                            damageReplaceAction(return_qty, itemNo);
                        }
                    }
                    

                }
            }

            

        }
        updateTotalReturn();
        paymentUpdate();
        JOptionPane.showMessageDialog(null, "Item return recorded successfully..");
        
    }
    
    
    private String getSoldPrice(String itemNo){
        String sql = "SELECT total from invoiceitems WHERE item_code like ? and invoice_id like ? ";
        ArrayList list = new ArrayList();
        list.add(itemNo);
        list.add(invoiceID);

        return connector.sqlExecution(sql, "total", list);
    }
    
    private void updateTotalReturn(){
        ArrayList list = connector.retreveDataColoumnWithCondition("sales_return", "amount", "invoice_id", invoiceID);
        invoiceTotalReturn=0;
        for (int i = 0; i < list.size(); i++) {
            String t = String.valueOf(list.get(i));
            double total = Double.valueOf(t);
            invoiceTotalReturn+=total;
        }
        
        String status = connector.getRelavantRecord("invoices", "status", "invoice_id", invoiceID);
        
        if(status.equals("0")){
            double paid_amount = Double.valueOf(connector.getRelavantRecord("invoices", "cash_paid", "invoice_id", invoiceID));
            double invoice_amount = Double.valueOf(connector.getRelavantRecord("invoices", "grandTotal", "invoice_id", invoiceID));

            double returned = invoice_amount-paid_amount-invoiceTotalReturn;
            if(returned < 0){
                JOptionPane.showMessageDialog(null, "You have to pay to the custoemr Rs."+Rounding.decimalFormatiing(returned));
            }else{
                JOptionPane.showMessageDialog(null, "The customer need to pay you Rs."+Rounding.decimalFormatiing(returned));
            }
        }else{
            JOptionPane.showMessageDialog(null, "YOu have to pay to the customer Rs."+Rounding.decimalFormatiing(invoiceTotalReturn));
        }

        
        connector.editRecordInTable("invoices", "invoice_id", "returned", Rounding.RoundTo5(invoiceTotalReturn, true), invoiceID);
        
    }
    
    private void uncompatibleAction(String qty,String itemNo){
        //If customer is cash and not suitable qty should be updated
        System.out.println("Not Suitable return");

        int current_stock = Integer.parseInt(connector.getRelavantRecord("items", "stock", "item_code", itemNo));
        current_stock += Integer.parseInt(qty);

        connector.editRecordInTable("items", "item_code", "stock", String.valueOf(current_stock), itemNo);
    }
    
    private void damageReplaceAction(String qty,String itemNo){
        System.out.println("Damaged Replacing return");

        int current_stock = Integer.parseInt(connector.getRelavantRecord("items", "stock", "item_code", itemNo));
        if (current_stock < Integer.parseInt(qty)) {
            JOptionPane.showConfirmDialog(null, "Sorry cant replace don't have that much stock Cash should be returned");
            uncompatibleAction(qty, itemNo);
        } else {
            current_stock -= Integer.parseInt(qty);
            connector.editRecordInTable("items", "item_code", "stock", String.valueOf(current_stock), itemNo);
        }
    }
    
    private void changeReturnableQty(String itemNo,int updating){
        String sql = "Update invoiceitems set returnable_qty = ? where invoice_id like ? AND item_code LIKE ?";        
        ArrayList list = new ArrayList();
        list.add(updating);
        list.add(invoiceID);
        list.add(itemNo);
        connector.sqlUpdate(sql,list);
    }
    
    public boolean checkReturnTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int row = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            if (model.getValueAt(i, 5) == null) {
                row++;
            }
            if (row == model.getRowCount()) {
                JOptionPane.showMessageDialog(null, "You have to select at least one item in the invoice");
                return false;
            }
        }
        return true;
    }
    
    private void paymentUpdate(){
        String status = connector.getRelavantRecord("invoices", "status", "invoice_id", invoiceID);
        double paid_amount = Double.valueOf(connector.getRelavantRecord("invoices", "cash_paid", "invoice_id", invoiceID));
        double invoice_amount = Double.valueOf(connector.getRelavantRecord("invoices", "grandTotal", "invoice_id", invoiceID));
        
    }
    
    
}
