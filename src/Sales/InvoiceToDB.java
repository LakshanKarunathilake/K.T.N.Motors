/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import DBController.DataBaseConnector;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

/**
 *
 * @author lakshan
 */
public class InvoiceToDB {
    JComboBox customer_code;
    DataBaseConnector connector;
    JTable table;
    JTextField invoiceID_text;
    JTextField totalTxt;
    JTextField discountTxt;
    JTextField grandTxt;
    
    public InvoiceToDB(JComboBox customer_code,JTextField invoiceID_text,JTable table, DataBaseConnector connector){
        this.customer_code = customer_code;
        this.connector = connector;
        this.table = table;
        this.invoiceID_text = invoiceID_text;
    }
    
    public boolean validUserPurchaise() {
        String userID = String.valueOf(customer_code.getSelectedItem());
        Date d = new Date();
        String currentDate = new SimpleDateFormat("yyyy-MM-dd").format(d);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, -3);
        d = c.getTime();
        String beforeDate = new SimpleDateFormat("yyyy-MM-dd").format(d);
        System.out.println("Current Date :" + currentDate);
        System.out.println("Current Date :" + beforeDate);

        String sql = "Select invoice_id from invoices where customer_code like \"" + userID + "\" And orderDate <= \"" + beforeDate + "\" AND status like '0'";
        System.out.println("SQL :" + sql);
        ArrayList list = connector.sqlExecutionaArray(sql, "orderID");
        System.out.println("Lits SIze : " + list.size());
        if (list.size() > 0) {
            String values = "";
            for (int i = 0; i < list.size(); i++) {
                values += list.get(i);
                values += " ,";
            }
            JOptionPane.showMessageDialog(null, "Sorry This user have 3 months old unpaid bills : " + values);
            return false;
        }
        return true;
    }
    
    public String TableToDB(){
        TableModel model = table.getModel();
        int rowCount  = model.getRowCount();
        String msg = null;
        
        if(InsertToOrder()){
          if(InsertToOrderItems(model,rowCount)){
              msg="Enter completed";  
          }else{
              msg="OrderItemsNotDoneButOrderDone";
          }          
        }else{
            msg = "Complete failure";
        }
        return msg;  
    }
    
    public boolean InsertToOrderItems(TableModel model, int rowCount) {
        ArrayList record = new ArrayList();
        String invoiceID = invoiceID_text.getText();

        for (int i = 0; i < rowCount; i++) {
            String itemNo = String.valueOf(model.getValueAt(i, 0));
            String qty = String.valueOf(model.getValueAt(i, 4));
            String rate = String.valueOf(model.getValueAt(i, 3));
            double rate_double = Double.parseDouble(rate);
            
            double discount = Double.parseDouble(discountTxt.getText());
            double discounted_price = rate_double - (rate_double*(discount/100));
            
           
            double total = Double.parseDouble(qty) * discounted_price;
            record.add(itemNo);
            record.add(invoiceID);
            record.add(qty);
            record.add(total);
            if (!connector.insertRecord("invoiceitems", record)) {
                JOptionPane.showMessageDialog(null, "Error addition in orderItems");
            }

//            int availableQty = Integer.parseInt(sales_available_qty_txt.getText());
            int availableQty = Integer.parseInt(connector.getRelavantRecord("items", "stock", "item_code", itemNo));
            int iqty = Integer.parseInt(qty);
            availableQty -= iqty;

            System.out.println("QTy edit : " + iqty);
            if (!connector.editRecordInTable("items", "item_code", "stock", String.valueOf(availableQty), itemNo)) {
                JOptionPane.showMessageDialog(null, "Error quantity change ");
                return false;
            } else {
                record.clear();
            }
        }
        if (rowCount < 0) {
            return false;
        } else {
            return true;
        }
    }
    
    public void setValuesForInsertOrder(JTextField totalTxt,JTextField discountTxt,JTextField grandTxt){
        this.totalTxt = totalTxt;
        this.discountTxt = discountTxt;
        this.grandTxt = grandTxt;        
                
    }

    public boolean InsertToOrder() {

        String invoiceID = invoiceID_text.getText();
        String userID = String.valueOf(customer_code.getSelectedItem());
        String total = totalTxt.getText();
        String discount = discountTxt.getText();
        String grandTotal = grandTxt.getText();

        Timestamp now = new Timestamp(System.currentTimeMillis());
        String timeStamp = String.valueOf(now);

        ArrayList record = new ArrayList();
        record.add(invoiceID);
        record.add(total);
        record.add(discount);
        record.add(grandTotal);
        record.add(timeStamp);
        record.add(userID);
        if (userID.equals("1")) {
            record.add("1");
        } else {
            record.add("0");
        }

        if (!connector.insertRecord("invoices", record)) {
            JOptionPane.showMessageDialog(null, "Insertion fails in order ");
            return false;
        } else {
            return true;
        }

    }
}
