/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalesReturn;

import DBController.DataBaseConnector;
import DataManipulation.Rounding;
import java.awt.Font;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
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
    double invoiceTotalReturn;
    
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
        ArrayList list = new ArrayList();
        ArrayList columns = new ArrayList();
        
        double one_time_return_total = 0;
        boolean not_violated = true;
        for (int i = 0; i < model.getRowCount(); i++) {
            String return_qty = String.valueOf(model.getValueAt(i, 3));
            Object condition_tick = model.getValueAt(i, 5);
            String itemNo = String.valueOf(model.getValueAt(i, 0));
            ArrayList temp = connector.retreveDataColoumnWithTwoCondition("invoiceitems", "returnable_qty", "invoice_id", invoiceID, "item_code", itemNo);
            String ta = String.valueOf(temp.get(0));
            int current_returnable = Integer.valueOf(ta);
            
            if((condition_tick != null) && (!return_qty.equals("0")) && (Integer.parseInt(return_qty)<=current_returnable) ){
                
                
                String bought_qty = String.valueOf(model.getValueAt(i, 4));
                

                Timestamp now = new Timestamp(System.currentTimeMillis());
                String timeStamp = String.valueOf(now);

                double total_sold = Double.parseDouble(getSoldPrice(itemNo));
                double selling_price = total_sold / Double.parseDouble(bought_qty);

                double returnAmount = Double.parseDouble(return_qty) * selling_price;
                
                //Adding current items return to one time return total
                one_time_return_total+=returnAmount;
                
                //For Entering Records to the sales_return nomatter cash or credit sale
                list.clear();
                list.add(invoiceID);
                list.add(itemNo);
                list.add(return_qty);
                list.add(timeStamp);
                list.add(return_type);
                list.add(returnAmount);
                
               
                columns.clear();
                columns.add("invoice_id");
                columns.add("item_code");
                columns.add("qty");
                columns.add("date");
                columns.add("Reason");
                columns.add("amount");
                
                connector.insertRecordColoumnCount("sales_return", list, columns);
                
                //Updating the returnable_qty to make sure the qty reduced in the table record
                current_returnable -= Integer.valueOf(return_qty);
                changeReturnableQty(itemNo, current_returnable);

                if (isCustomerCash()) {
                    if (return_type.equals("Not Suitable")) {
                        uncompatibleAction(return_qty, itemNo);
                    } else if (return_type.equals("Damaged Replacing")) {
                        damageReplaceAction(return_qty, itemNo);
                    }
                } else {
                    
                    String paidOrNot = connector.getRelavantRecord("invoices", "status", "invoice_id", invoiceID);
                    //Creit user invoice and its not paid
                    if (paidOrNot.equals("0")) {
                        if (return_type.equals("Not Suitable")) {
                            uncompatibleAction(return_qty, itemNo);

                        } else if (return_type.equals("Damaged Replacing")) {
                            damageReplaceAction(return_qty, itemNo);
                        }
                    }else{
                        //Creit user invoice and its paid
                        if (return_type.equals("Not Suitable")) {
                            uncompatibleAction(return_qty, itemNo);
                        } else if (return_type.equals("Damaged Replacing")) {
                            damageReplaceAction(return_qty, itemNo);
                        }
                    }
                }
                paymentUpdate();
                JOptionPane.showMessageDialog(null, "Item return recorded successfully..");
                
            }else if(condition_tick != null){
                not_violated = false;
                JOptionPane.showMessageDialog(null, "You are violating rules please check the returnable qty is set to max");
            }
        }
        
        //Updating the sales table coloumn total_return
        if(not_violated)
            updateTotalReturn(one_time_return_total);
       
        
    }
    
    
    private String getSoldPrice(String itemNo){
        String sql = "SELECT total from invoiceitems WHERE item_code like ? and invoice_id like ? ";
        ArrayList list = new ArrayList();
        list.add(itemNo);
        list.add(invoiceID);

        return connector.sqlExecution(sql, "total", list);
    }
    
    private double updateTotalReturn(double one_time_overall){
        //Calculating the alltime
        double existing_returns = Double.parseDouble(connector.getRelavantRecord("invoices", "returned", "invoice_id", invoiceID));
        double current_returns = existing_returns + one_time_overall;
        
         //Update the total returns of the invoice in DB
        connector.editRecordInTable("invoices", "invoice_id", "returned", String.valueOf(current_returns), invoiceID);
        
              
        String status = connector.getRelavantRecord("invoices", "status", "invoice_id", invoiceID);
        double returned = 0;
        JLabel label;
        if(status.equals("0")){
            System.out.println("Not paid bill return");
            double paid_amount = Double.valueOf(connector.getRelavantRecord("invoices", "cash_paid", "invoice_id", invoiceID));
            double invoice_amount = Double.valueOf(connector.getRelavantRecord("invoices", "grandTotal", "invoice_id", invoiceID));
            ArrayList list = connector.retreveDataColoumnWithCondition("sales_return", "amount", "invoice_id", invoiceID);
            
            double credit_return_overall = 0;
            for (int i = 0; i < list.size(); i++) {
                String t = String.valueOf(list.get(i));
                double total = Double.valueOf(t);
                credit_return_overall += total;
            }
            
            returned = invoice_amount-paid_amount-credit_return_overall;
            if(returned < 0){
                label = new JLabel("You have to pay to the customer Rs."+Rounding.decimalFormatiing(returned));               

            }else{
                label = new JLabel("The customer need to pay you Rs." + Rounding.decimalFormatiing(returned));
                
//                JOptionPane.showMessageDialog(null, "The customer need to pay you Rs."+Rounding.decimalFormatiing(returned));
            }
        }else{
            returned = one_time_overall;
            label = new JLabel("You have to pay to the customer Rs." + Rounding.decimalFormatiing(returned));
//            JOptionPane.showMessageDialog(null, "YOu have to pay to the customer Rs."+Rounding.decimalFormatiing(invoiceTotalReturn));
        }
        
        label.setFont(new Font("Arial", Font.BOLD, 18));
        JOptionPane.showMessageDialog(null, label, "ERROR", JOptionPane.WARNING_MESSAGE);
        
        return returned;
    }
    
    private void uncompatibleAction(String qty,String itemNo){
        //If customer is cash and not suitable qty should be updated
        System.out.println("Not Suitable return");

        //Adding the restock to the current stock count
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
        double returned = Double.valueOf(connector.getRelavantRecord("invoices", "returned", "invoice_id", invoiceID));
        double cash_paid = Double.valueOf(connector.getRelavantRecord("invoices", "cash_paid", "invoice_id", invoiceID));
        double invoice_amount = Double.valueOf(connector.getRelavantRecord("invoices", "grandTotal", "invoice_id", invoiceID));
        if(status.equals("0")){
            if ((returned + cash_paid) >= invoice_amount) {
                connector.editRecordInTable("invoices", "invoice_id", "status", "1", invoiceID);
            }

        }
        
        
    }
    
    
}
