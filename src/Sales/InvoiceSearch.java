/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import DBController.DataBaseConnector;
import ViewManipulation.ViewManipulation;
import java.util.ArrayList;
import javax.swing.JComboBox;
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

        totalTxt.setText(connector.getRelavantRecord("invoices", "total", "invoice_id", invoiceID));
        discountTxt.setText(connector.getRelavantRecord("invoices", "discount", "invoice_id", invoiceID));
        grandTxt.setText(connector.getRelavantRecord("invoices", "grandTotal", "invoice_id", invoiceID));

    }
}
