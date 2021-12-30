/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import DBController.DataBaseConnector;
import DataManipulation.DataManipulation;
import DataManipulation.MyCombo;
import java.awt.Component;
import java.awt.Font;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;
import ViewManipulation.ViewManipulation;

/**
 *
 * @author lakshan
 */
public class Invoice {
    
       
    JComboBox itemNo;
    JComboBox category;
    JComboBox customerID;
    JComboBox customerName;
    JComboBox salesRepName;
    DataBaseConnector connector;
    JTable table;
    JTextField qtyText;
    
    MyCombo autoCombo1 = new MyCombo();
    MyCombo autoCombo2 = new MyCombo();
//    MyCombo autoCombo3 = new MyCombo();
//    MyCombo autoCombo4 = new MyCombo();
    
    boolean isNotDecorated = true;
    
    public Invoice(JComboBox itemNo,JComboBox category,JComboBox customerID,JComboBox customerName,JTextField qtyText,JComboBox salesRepName,DataBaseConnector connector){
        this.itemNo = itemNo;
        this.category = category;
        this.connector = connector;
        this.customerID = customerID;
        this.customerName = customerName;
        this.salesRepName = salesRepName;
        this.qtyText = qtyText;
    }
    
    public void salesAutoCombo(){
        autoCombo1.setSearchableCombo(this.itemNo, true, "No Result Found");
        autoCombo2.setSearchableCombo(this.category, true, "No Result Found");
    }
    
    public void decoratingCustomers(){
        ArrayList boxes = new ArrayList<JComboBox>();
        boxes.add(customerID);
        boxes.add(customerName);
        boxes.add(salesRepName);
        ViewManipulation.emptyComboBoxes(boxes);
        if(isNotDecorated){
            AutoCompleteDecorator.decorate(customerID);
            AutoCompleteDecorator.decorate(customerName);
            AutoCompleteDecorator.decorate(salesRepName);
            isNotDecorated = false;            
        }
        DataManipulation manipulation = new DataManipulation(connector);
        
        manipulation.getRecords("customers", "customer_code", customerID);
        manipulation.getRecords("customers", "name", customerName);
        manipulation.getRecords("reps", "name", salesRepName);
        
        ViewManipulation.moveFocusToNext(customerID, itemNo);
        ViewManipulation.moveFocusToNext(customerName, itemNo);
        ViewManipulation.moveFocusToNext(salesRepName, itemNo);
        
        customerID.setSelectedItem("1");
        salesRepName.setSelectedItem("Nimal");
        
    }
   
    
    private void autoCompleteCombo() {
       
        salesAutoCombo();

        ArrayList<String> catList1 = new ArrayList<>();
        
        //       Getting the value from a second table -- userID from the user table
        
        catList1.add("items");
        catList1.add("item_code");
        catList1.add("category");

        autoCombo2.populateSecondCombo(category, itemNo, connector, catList1, null, false);
        autoCombo2.moveFocusToNext(itemNo, qtyText);
        
    
    }
       
    public ArrayList[]  fillDataToCombo() {
        
        ArrayList[] lists = new ArrayList[2];
        
        DataManipulation manipulation = new DataManipulation(connector); 
        
        lists[0] = manipulation.getRecords("items", "item_code", itemNo);
        lists[1] = manipulation.getRecords("items", "category", category);
        
        decoratingCustomers();
        autoCompleteCombo();
        return lists;
    }
    
    public static String generateSaleID(DataBaseConnector connector){
        String lastID = connector.retreveLastRecord("invoices","invoice_id", "orderDate");
        int currentID,nextID;
        if(lastID == null){
            nextID = 1;
        }else{
            String parts[] = lastID.split("A");
            currentID = Integer.parseInt(parts[1]);
            nextID = ++currentID;
        }
        
        
        DecimalFormat formatter = new DecimalFormat("0000");
        String idFormatted = formatter.format(nextID);
        
        
        return "A"+idFormatted;
    }
    
    public static void setSaleID(JTextField txt,DataBaseConnector connector){
        String ID = generateSaleID(connector);
        txt.setText(ID);
        txt.setEditable(false);
    }
    
    public void makeSalesComponents(boolean b,ArrayList<Component> comps){
        ViewManipulation.makeAllComponents(comps, b);
    }
    
    public static void changeTableView(JTable table,int[] columnWidths){
        
        //Changing the font of the table
        Font f = new Font("Arial", Font.BOLD, 15);
        JTableHeader header = table.getTableHeader();
        header.setFont(f);
        
        //Defining the table lengths
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }
}
