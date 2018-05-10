/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalesReturn;

import DBController.DataBaseConnector;
import DataManipulation.DataManipulation;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JComboBox;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author manual-pc
 */
public class SalesReturn {
    JComboBox invoice_no ;
    JComboBox customer_no ;
    JComboBox customer_name ;
    DataBaseConnector connector;
    
    public SalesReturn(JComboBox invoice_no,JComboBox customer_no,JComboBox customer_name,DataBaseConnector connector){
        this.invoice_no = invoice_no;
        this.customer_name = customer_name;
        this.customer_no = customer_no;
        this.connector = connector;
    }

    private void autoCompleteCombo() {
        AutoCompleteDecorator.decorate(customer_no);
        AutoCompleteDecorator.decorate(invoice_no);
        AutoCompleteDecorator.decorate(customer_name);
    }

    public void  fillDataToCombo() {
        
        autoCompleteCombo();       
        
        DataManipulation manipulation = new DataManipulation(connector);
        
        manipulation.getRecords("orders", "orderID", invoice_no);
        manipulation.getRecords("users", "userID", customer_no);
        manipulation.getRecords("users", "name", customer_name);
    }
    
    public void fill_search_table(String itemNo){
        
    }
    
    
}
