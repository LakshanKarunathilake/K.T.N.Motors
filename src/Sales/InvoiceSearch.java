/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import DBController.DataBaseConnector;
import DataManipulation.Rounding;
import ViewManipulation.ViewManipulation;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lakshan
 */
public class InvoiceSearch {
    
    JTextField invoice_txt;
    JComboBox customerID_combo;
    JComboBox customerName_combo;
    JTable table;
    JTextField totalTxt;
    JTextField discountTxt;
    JTextField grandTxt;
    
    DataBaseConnector connector;
    
    JCheckBox check;
    JTextField cash_txt;
    JTextField credit_txt;
    JPanel halfpay;
    
    public InvoiceSearch(JTextField invoice_txt,JComboBox customerID_combo,JComboBox customerName_combo,JTable table,JTextField totalTxt,JTextField discountTxt,JTextField grandTxt,DataBaseConnector connector){
        this.invoice_txt = invoice_txt;
        this.customerID_combo = customerID_combo;
        this.customerName_combo = customerName_combo;
        this.table = table;
        this.totalTxt = totalTxt;
        this.discountTxt = discountTxt;
        this.grandTxt = grandTxt;
        this.connector = connector;
    }
    
    public void setHalfPayComponents(JCheckBox check,JTextField cash_txt,JTextField credit_txt,JPanel halfpay){
        this.check = check;
        this.credit_txt = credit_txt;
        this.cash_txt = cash_txt;
        this.halfpay = halfpay;
    }
    
    public void fillInvoice(String invoiceID) {
        invoice_txt.setText(invoiceID);
        String userID = connector.getRelavantRecord("invoices", "customer_code", "invoice_id", invoiceID);
        customerID_combo.setSelectedItem(userID);
        customerName_combo.setSelectedItem(connector.getRelavantRecord("customers", "name", "customer_code", userID));

        ArrayList itemNoList;
        ArrayList itemQtyList;
        ArrayList itemTotalList;
        itemNoList = connector.retreveDataColoumnWithCondition("invoiceitems", "item_code", "invoice_id", invoiceID);
        itemQtyList = connector.retreveDataColoumnWithCondition("invoiceitems", "qty", "invoice_id", invoiceID);
        itemTotalList = connector.retreveDataColoumnWithCondition("invoiceitems", "total", "invoice_id", invoiceID);

        ViewManipulation.emptyTable(table);
        
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        
        Object rowData[] = new Object[6];

        for (int i = 0; i < itemNoList.size(); i++) {
            String itemNo = String.valueOf(itemNoList.get(i));
            ArrayList item_data = new ArrayList();
            item_data = connector.readRow("items", "item_code", itemNo);
            rowData[0] = item_data.get(0);
            rowData[1] = item_data.get(4);
            rowData[2] = item_data.get(9);
            rowData[3] = item_data.get(7);
            rowData[4] = itemQtyList.get(i);
            rowData[5] = itemTotalList.get(i);
            model.addRow(rowData);

        }
        
        String cash = connector.getRelavantRecord("invoices", "cash_paid", "invoice_id", invoiceID);
        String grandTotal = connector.getRelavantRecord("invoices", "grandTotal", "invoice_id", invoiceID);
        halfpay.setVisible(false);
        if(!cash.equals(grandTotal)){
            double credit = Double.parseDouble(grandTotal)- Double.parseDouble(cash);
            halfpay.setVisible(true);
            check.setSelected(true);
            credit_txt.setText(Rounding.RoundTo5(credit, true));
            cash_txt.setText(cash);
        }

        totalTxt.setText(connector.getRelavantRecord("invoices", "total", "invoice_id", invoiceID));
        discountTxt.setText(connector.getRelavantRecord("invoices", "discount", "invoice_id", invoiceID));
        grandTxt.setText(connector.getRelavantRecord("invoices", "grandTotal", "invoice_id", invoiceID));

    }
}
