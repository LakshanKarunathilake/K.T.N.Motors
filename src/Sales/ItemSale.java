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
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.TableModel;

/**
 *
 * @author lakshan
 */
public class ItemSale {
    JComboBox itemNo;
    JComboBox category;
    JComboBox customerID;
    JComboBox customerName;
    DataBaseConnector connector;
    JTable table;
    
    public ItemSale(JComboBox itemNo,JComboBox category,JComboBox customerID,JComboBox customerName,DataBaseConnector connector){
        this.itemNo = itemNo;
        this.category = category;
        this.connector = connector;
        this.customerID = customerID;
        this.customerName = customerName;
    }
    
    private void autoCompleteCombo() {
        MyCombo autoCombo1 = new MyCombo();
        MyCombo autoCombo2 = new MyCombo();
        MyCombo autoCombo3 = new MyCombo();
        MyCombo autoCombo4 = new MyCombo();
        

        autoCombo1.setSearchableCombo(this.itemNo, true, "No Result Found");
        autoCombo2.setSearchableCombo(this.category, true, "No Result Found");
        autoCombo3.setSearchableCombo(this.customerID, true, "No Result Found");
        autoCombo4.setSearchableCombo(this.customerName, true, "No Result Found");
       

        ArrayList<String> myList = new ArrayList<>();
        
        //       Getting the value from a second table -- userID from the user table
        
        myList.add("items");
        myList.add("ITCode");
        myList.add("ITCat");

        autoCombo2.populateSecondCombo(itemNo, category, connector, myList, null, false);
        
        myList.set(0, "users");
        myList.set(1, "name");
        myList.set(2, "userID");
        autoCombo3.populateSecondCombo(customerID, customerName, connector, myList, null, false);
        
        myList.set(1, "userID");
        myList.set(2, "name");
        autoCombo4.populateSecondCombo(customerName, customerID, connector, myList, null, false);
   
    }
    
    public void  fillDataToCombo() {
        
        DataManipulation manipulation = new DataManipulation(connector); 
        
//        manipulation.getRecords("items", "ITCode", itemNo);
//        manipulation.getRecords("items", "ITCat", category);
        manipulation.getRecords("users", "userID", customerID);
        manipulation.getRecords("users", "name", customerName);
        
        
        
        autoCompleteCombo();
    }
    
    public static String generateSaleID(DataBaseConnector connector){
        String lastID = connector.retreveLastRecord("Orders","orderID", "orderDate");
        String parts[] = lastID.split("A");
        int currentID = Integer.parseInt(parts[1]);
        int nextID = ++currentID;
        
        DecimalFormat formatter = new DecimalFormat("0000");
        String idFormatted = formatter.format(nextID);
        
        
        return "A"+idFormatted;
    }
    
    public static void setSaleID(JTextField txt,DataBaseConnector connector){
        String ID = generateSaleID(connector);
        txt.setText(ID);
        txt.setEditable(false);
    }
    
    public String searchRecord(String tableName, String coloumnName1, String coloumnName2, String value) {
        return connector.getRelavantRecord(tableName, coloumnName1, coloumnName2, value);
    }

    public ArrayList getRowInATable(String tableName, String coloumn, String condition) {
        return connector.readRow(tableName, coloumn, condition);
    }

    public boolean checkItemIsInTable(String value) {
        int rowID = findIntheTable(value);
        if (rowID == Integer.MAX_VALUE) {
            return false;
        } else {
            return true;
        }

    }

    public int findIntheTable(String value) {
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

    public void addQtyToTheExistingRecord(String itemNo, String qty) {
        int rowNo = findIntheTable(itemNo);
        TableModel model = table.getModel();

        String Stemp = String.valueOf(model.getValueAt(rowNo, 3));
        int qtyNow = Integer.parseInt(Stemp);
        int totalQty = qtyNow + Integer.parseInt(qty);
//        int availability = Integer.parseInt(sales_available_qty_txt.getText());
//        if (totalQty > availability) {
//            JOptionPane.showMessageDialog(null, "The maximum qty is set");
//            totalQty = availability;
//        }
//        model.setValueAt(totalQty, rowNo, 3);
//        updateTotal(rowNo, totalQty);

    }

    public void updateTotal(int rowNo, int totalQty) {
        TableModel model = table.getModel();
        double d = Double.parseDouble((String) model.getValueAt(rowNo, 2));
        double total = d * totalQty;
        model.setValueAt(total, rowNo, 4);

    }
    
    public void makeSalesComponents(boolean b,ArrayList<Component> comps){
        ViewManipulation.ViewManipulation.makeAllComponents(comps, b);
    }
}
