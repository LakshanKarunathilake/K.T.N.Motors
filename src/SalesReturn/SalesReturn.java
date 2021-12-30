/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SalesReturn;

import DBController.DataBaseConnector;
import DataManipulation.DataManipulation;
import DataManipulation.MyCombo;
import DataManipulation.Rounding;
import ViewManipulation.ViewManipulation;
import com.toedter.calendar.JDateChooser;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

/**
 *
 * @author manual-pc
 */
public class SalesReturn {

    JComboBox invoice_no;
    JComboBox customer_no;
    JComboBox customer_name;
    JComboBox return_item_combo;
    JTable return_invoiceSearch_table;
    JTable return_item_table;
    DataBaseConnector connector;
    JPanel panel1;
    JScrollPane panel2;
    JLabel total_lbl;
    JDateChooser from;
    JDateChooser to;

    boolean isDecorated = false;
    
    private static SalesReturn salesReturn;
    
    private SalesReturn(){
        
    }
    
    public static SalesReturn getInstance(){
        if(salesReturn == null)
            salesReturn = new SalesReturn();
        return salesReturn;
    }

    public void setReturnItems(JComboBox invoice_no, JComboBox customer_no, JComboBox customer_name, JComboBox return_item_combo,JTable return_invoiceSearch_table,JTable return_item_table, JLabel total_lbl, JDateChooser from, JDateChooser to, DataBaseConnector connector) {
        this.invoice_no = invoice_no;
        this.customer_name = customer_name;
        this.customer_no = customer_no;
        this.return_item_combo = return_item_combo;
        this.total_lbl = total_lbl;
        this.from = from;
        this.to = to;
        this.connector = connector;
        this.return_invoiceSearch_table = return_invoiceSearch_table;
        this.return_item_table = return_item_table;
        
        if (!isDecorated) {
            this.fillDataToCombo();
        }
        isDecorated = true;
    }
    
  
    private void autoCompleteCombo() {
        MyCombo autoCombo1 = new MyCombo();
        MyCombo autoCombo2 = new MyCombo();
        MyCombo autoCombo3 = new MyCombo();
        MyCombo autoCombo4 = new MyCombo();

        autoCombo1.setSearchableCombo(this.invoice_no, true, "No Result Found");
        autoCombo2.setSearchableCombo(this.customer_no, true, "No Result Found");
        autoCombo3.setSearchableCombo(this.customer_name, true, "No Result Found");
        autoCombo4.setSearchableCombo(this.return_item_combo, true, "No Result Found");

        DataManipulation dm = new DataManipulation(connector);
        dm.getRecords("items", "item_code", this.return_item_combo);
        autoCombo4.populateAJTable(this.return_item_combo, this.return_invoiceSearch_table, from, to, connector);
        eventForSearchTable(this.return_invoiceSearch_table, this.return_item_table);

        ArrayList<String> myList = new ArrayList<>();
        //       Getting the value from a second table -- userID from the user table
        ArrayList<String> secondTableCondition = new ArrayList<>();

        myList.add("invoices");
        myList.add("invoice_id");
        myList.add("customer_code");

        autoCombo2.populateSecondCombo(customer_no, invoice_no, connector, myList, null, false);

//       Adding values to the secondtableCondition
        secondTableCondition.add("customers");
        secondTableCondition.add("customer_code");
        secondTableCondition.add("name");

        autoCombo3.populateSecondCombo(customer_name, invoice_no, connector, myList, secondTableCondition, true);
    }

    public void fillDataToCombo() {
        System.out.println("Populating data");
        DataManipulation manipulation = new DataManipulation(connector);

        manipulation.getRecords("invoices", "invoice_id", invoice_no);
        manipulation.getRecords("customers", "customer_code", customer_no);
        manipulation.getRecords("customers", "name", customer_name);

        autoCompleteCombo();

    }

    public void getInvoiceRecords(String invoiceNo, JTable table, DataBaseConnector connector) {
        ArrayList conditionCols = new ArrayList();
        conditionCols.add("invoice_id");

        ArrayList conditionVals = new ArrayList();
        conditionVals.add(invoiceNo);

        DefaultTableModel dtm = (DefaultTableModel) table.getModel();

        ArrayList<String[]> records = connector.retreveLargeDataSet(conditionCols, conditionVals, "invoiceitems");
        System.out.println("Array list : " + records.size());
        for (int i = 0; i < records.size(); i++) {
            Object rowData[] = new Object[5];
            String row[] = records.get(i);
            System.out.println("row" + row.toString());
            rowData[0] = row[0];

            double price = Double.parseDouble(row[3]);
            double qty = Double.parseDouble(row[2]);

            rowData[1] = Rounding.RoundTo5((price / (qty)), true);
            rowData[2] = price;
            rowData[3] = row[4];
            rowData[4] = row[2];
            System.out.println("For loop" + i);

            dtm.addRow(rowData);
        }
//        eventForItemTable(table);

    }

    public void changeTableView(JTable table) {

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

    public void selectAllInTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = 0; i < model.getRowCount(); i++) {
            model.setValueAt(true, i, 5);
        }

    }

    public void searchInvoice(JTable table, JTable item_table, DataBaseConnector connector) {
        //First Have to clear comboboxes for replication of data
//        ArrayList<JComboBox> combos = new ArrayList<JComboBox>();
//        combos.add(combo);
//
//        ViewManipulation.emptyComboBoxes(combos);
//        //Populating combo with values
//        DataManipulation dm = new DataManipulation(connector);
//        dm.getRecords("items", "item_code", combo);
//        //Make the combo autocomplete
//        MyCombo autoCombo = new MyCombo();
//        autoCombo.setSearchableCombo(combo, true, "No result Found");
//
//        autoCombo.populateAJTable(combo, table,from,to, connector);

//        eventForSearchTable(table, item_table);

    }

    private void eventForSearchTable(JTable table, final JTable item_table) {
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                if (mouseEvent.getClickCount() == 2 && table.getSelectedRow() != -1) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    String invoiceNo = String.valueOf(model.getValueAt(row, 0));
                    salesInvoiceSelection(invoiceNo, item_table);
                }
            }
        });
    }

    public void eventForItemTable(JTable table) {
        table.addMouseListener(new MouseAdapter() {
            public void mousePressed(MouseEvent mouseEvent) {
                JTable table = (JTable) mouseEvent.getSource();
                Point point = mouseEvent.getPoint();
                int row = table.rowAtPoint(point);
                int column = table.columnAtPoint(point);
                if (mouseEvent.getClickCount() == 1 && table.getSelectedRow() != -1 && column == 5) {
                    DefaultTableModel model = (DefaultTableModel) table.getModel();
                    if (model.getValueAt(row, 3).equals("0")) {
                        JOptionPane.showMessageDialog(null, "Sorry this item is fully Returned");
                    } else {
                        model.setValueAt(true, row, 5);
                    }

                }
            }
        });
    }

    public void calculateReturningTotal(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        int rowCount = model.getRowCount();
        double fullTotal = 0;
        for (int i = 0; i < rowCount; i++) {
            if (model.getValueAt(i, 5) != null) {
                String returning_qty = String.valueOf(model.getValueAt(i, 3));
                String bought_qty = String.valueOf(model.getValueAt(i, 4));
                if (returning_qty.equals(bought_qty)) {
                    String bought_total = String.valueOf(model.getValueAt(i, 2));
                    fullTotal += Double.parseDouble(bought_total);
                } else {
                    String unitPrice = String.valueOf(model.getValueAt(i, 1));
                    double total = Double.parseDouble(returning_qty) * Double.parseDouble(unitPrice);
                    fullTotal += total;
                }

                total_lbl.setText(Rounding.RoundTo5(fullTotal, true));
            }
        }
    }

    public void setPanels(JPanel panel1, JScrollPane panel2) {
        this.panel1 = panel1;
        this.panel2 = panel2;
    }

    private void salesInvoiceSelection(String invoiceNo, JTable item_table) {
        invoice_no.setSelectedItem(invoiceNo);

        ViewManipulation.emptyTable(item_table);
        ViewManipulation.changePanel(panel1, panel2);
        getInvoiceRecords(invoiceNo, item_table, connector);
    }

}
