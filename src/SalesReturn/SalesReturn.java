/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalesReturn;

import DBController.DataBaseConnector;
import DataManipulation.DataManipulation;
import DataManipulation.MyCombo;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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
       MyCombo autoCombo1 = new MyCombo();
       MyCombo autoCombo2 = new MyCombo();
       MyCombo autoCombo3 = new MyCombo();
       
       
       autoCombo1.setSearchableCombo(this.invoice_no, true, "No Result Found");
       autoCombo2.setSearchableCombo(this.customer_no, true, "No Result Found");
       autoCombo3.setSearchableCombo(this.customer_name, true, "No Result Found");
       
        ArrayList<String> myList = new ArrayList<>();
        //       Getting the value from a second table -- userID from the user table
        ArrayList<String> secondTableCondition = new ArrayList<>();
        
       myList.add("Orders");
       myList.add("orderID");
       myList.add("userID");
        
       autoCombo2.populateSecondCombo(customer_no, invoice_no,connector,myList,null,false);       
       
//       Adding values to the secondtableCondition
        secondTableCondition.add("users");
        secondTableCondition.add("userID");
        secondTableCondition.add("name");
       
        autoCombo3.populateSecondCombo(customer_name, invoice_no,connector,myList,secondTableCondition,true);
    }

    public void  fillDataToCombo() {
        
        DataManipulation manipulation = new DataManipulation(connector); 
        
        manipulation.getRecords("orders", "orderID", invoice_no);
        manipulation.getRecords("users", "userID", customer_no);
        manipulation.getRecords("users", "name", customer_name);
        
        autoCompleteCombo();
    }
    
    public static void getInvoiceRecords(String invoiceNo,JTable table,DataBaseConnector connector){
        ArrayList conditionCols = new ArrayList();
        conditionCols.add("orderID");
        
        ArrayList conditionVals = new ArrayList();
        conditionVals.add(invoiceNo);
        
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        
        ArrayList<String []> records = connector.retreveLargeDataSet(conditionCols, conditionVals,"orderItems");
        System.out.println("Array list : "+records.size());
        for (int i = 0; i < records.size(); i++) {
            Object rowData[] = new Object[5];
            String row[] = records.get(i);           
            
            rowData[0] = row[0];
            
            BigDecimal price = new BigDecimal(row[3]);
            price = price.setScale(2, RoundingMode.HALF_UP);
            BigDecimal divider = new BigDecimal(2);
            
            rowData[1] = price.divide(divider);
            rowData[2] = price;
            rowData[3] = row[2];
            rowData[4] = row[2];
            System.out.println("For loop"+i); 
            for (int k = 0; k < rowData.length; k++) {
                System.out.println("In SalesRe " + row[k]);
            }
            dtm.addRow(rowData);
        }
    }
    
    public void changeTableView(JTable table){
        
        //Changing the font of the table
        Font f = new Font("Arial", Font.BOLD, 14);
        JTableHeader header = table.getTableHeader();
        header.setFont(f);
        
        //Defining the table lengths
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        table.getColumnModel().getColumn(0).setPreferredWidth(330);
        table.getColumnModel().getColumn(1).setPreferredWidth(155);
        table.getColumnModel().getColumn(2).setPreferredWidth(155);
        table.getColumnModel().getColumn(3).setPreferredWidth(120);
        table.getColumnModel().getColumn(4).setPreferredWidth(120);
        table.getColumnModel().getColumn(5).setPreferredWidth(80);
        
    }
    
    public void selectAllInTable(JTable table){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(true, i, 5);
        }
        
    }
    
    public static void searchInvoice(JComboBox combo,JTable table,DataBaseConnector connector){
        //First Have to clear comboboxes for replication of data
        ArrayList<JComboBox> combos = new ArrayList<JComboBox>();
        combos.add(combo);

        ViewManipulation.ViewManipulation.emptyComboBoxes(combos);
        //Populating combo with values
        DataManipulation dm = new DataManipulation(connector);
        dm.getRecords("item", "itemNo", combo);
        //Make the combo autocomplete
        MyCombo autoCombo = new MyCombo();
        autoCombo.setSearchableCombo(combo, true, "No result Found");
        
        autoCombo.populateAJTable(combo, table, connector);
        
        eventForTable(table);
        
    }
    
    private static void eventForTable(JTable table){
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    JOptionPane.showMessageDialog(null, "clicked at "+row);
                }
            }
        });
    }
    
}
