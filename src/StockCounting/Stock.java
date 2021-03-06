/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package StockCounting;

import DBController.DataBaseConnector;
import DataManipulation.DataManipulation;
import DataManipulation.MyCombo;
import DataManipulation.Rounding;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author manual-pc
 */
public class Stock {
    
    JComboBox itemNo_combo;
    JTextField qty_txt;
    JTextField selling_txt;
    JLabel selling_lbl;
    JLabel description_lbl;
    JLabel availableQty_lbl;
    JLabel currentSelling_lbl;
    
    DataBaseConnector connector;
    
    JTable table;
    
    private JTextField txt;
    
    boolean condition = true;
    
    private static Stock stock;
    
    private Stock(){
        
    }
    
    public static Stock getInstance(){
        if(stock==null)
            stock = new Stock();
        return stock;
    }
    
        
    
    public void setFields(JComboBox itemNo_combo,JTextField qty_txt,JTextField selling_txt,JLabel selling_lbl,JLabel description_lbl,JLabel availableQty_lbl,JLabel currentSelling_lbl,JTable table,DataBaseConnector connector){
        this.itemNo_combo = itemNo_combo;
        this.qty_txt = qty_txt;
        this.selling_lbl = selling_lbl;
        this.availableQty_lbl = availableQty_lbl;
        this.selling_txt = selling_txt;
        this.currentSelling_lbl= currentSelling_lbl;
        this.connector = connector; 
        
        this.description_lbl = description_lbl;
        this.table = table;
        
        fillCombo();
        
    }
    
    public void fillDescription(){
        String item_no = String.valueOf(itemNo_combo.getSelectedItem());
        String description = connector.getRelavantRecord("items", "description", "item_code", item_no);
        String qty = connector.getRelavantRecord("items", "stock", "item_code", item_no);
        String selling = connector.getRelavantRecord("items", "selling", "item_code", item_no);
        description_lbl.setText(description);
        availableQty_lbl.setText(qty);
        currentSelling_lbl.setText(selling);
    }
    
    

    
    
    private void autoFillCombo(){
        MyCombo combo1 = new MyCombo();
        combo1.setSearchableCombo(itemNo_combo, true, "Not a valid itemNo");
        combo1.moveFocusToNext(itemNo_combo, qty_txt);
        
    }
    
    public void fillCombo(){
        itemNo_combo.removeAllItems();
        if(condition){
            AutoCompleteDecorator.decorate(itemNo_combo);
            condition = false;
        }
        
        DataManipulation dm = new DataManipulation(connector);
        dm.getRecords("items", "item_code", itemNo_combo);
        
        moveFocusToNext(itemNo_combo, qty_txt);
        
    }
    
//    public void fillCombo(){
//        DataManipulation dm = new DataManipulation(connector);
//        dm.getRecords("items", "item_code", itemNo_combo);
//        
//        autoFillCombo();
//        
//    }
    
    public void update(){
        String item_no = String.valueOf(itemNo_combo.getSelectedItem());
        String qty = qty_txt.getText();
        String selling_abc = selling_txt.getText();
        
        double selling = Double.parseDouble(parseToNumbers(selling_abc));
        
        
        String available_qty = connector.getRelavantRecord("items", "stock", "item_code", item_no);
        double total_qty = Double.valueOf(available_qty)+Integer.valueOf(qty);
        
        Timestamp now = new Timestamp(System.currentTimeMillis());
        String timeStamp = String.valueOf(now);
        
        double current_price = Double.valueOf(connector.getRelavantRecord("items", "selling", "item_code", item_no));
        if(current_price > selling){
            int showConfirmDialog = JOptionPane.showConfirmDialog(null, "Current price is > than the price you are trying to enter +\n Click yes to save the large price click no to save the new selling price","Price matter",JOptionPane.YES_NO_OPTION);
            if(showConfirmDialog == JOptionPane.YES_OPTION){
                selling = current_price;
            }
        }
            
        double cost = calculateCost(selling);
        
        ArrayList data = new ArrayList();
        ArrayList data1 = new ArrayList();
        ArrayList columns = new ArrayList();
        ArrayList columns1 = new ArrayList();
        
        columns.add("stock");
        columns.add("selling");
        columns.add("cost");
        columns.add("last_edit_date");
        
        columns1.add("item_code");
        columns1.add("stock");
        columns1.add("selling");
        columns1.add("cost");
        columns1.add("last_edit_date");
        
        data.add(total_qty);
        data.add(Rounding.RoundTo5(selling, true));
        data.add(Rounding.roundCommon(cost, 0));
        data.add(timeStamp);
        
        data1.add(item_no);
        data1.add(qty);
        data1.add(Rounding.RoundTo5(selling, true));
        data1.add(Rounding.roundCommon(cost, 0));
        data1.add(timeStamp);
        
        if(connector.editRecordWithColoumns("items", "item_code", item_no, data, columns)){
             connector.insertRecordColoumnCount("stock_count", data1, columns1);
             toTable(data1);
             JOptionPane.showMessageDialog(null, "Added Successfully....");
                          
        }else{
            JOptionPane.showMessageDialog(null, "Error Stock updating");  
            
        }
        
        selling_lbl.setText(String.valueOf(selling));
        
        
    }
    
    private void toTable(ArrayList data){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        Object[] rowData = data.toArray();
        model.addRow(rowData); 
    }
    
    public String parseToNumbers(String text){
        String arr[] = text.split("");
        String result = "";
        for (int i = 0; i < arr.length; i++) {
            switch (arr[i]) {
                case "A":
                    result+="0";
                    break;
                case "N": 
                    result+="1";
                    break;
                case "K":
                    result += "2";
                    break;
                case "T": 
                    result += "3";
                    break;
                case "G":
                    result += "4";                    
                    break;
                case "S": 
                    result+="5";
                    break;
                case "L": 
                    result += "6";
                    break;
                case "M": 
                    result += "7";
                    break;                
                case "P":
                    result += "8";
                    break;
                 case "J":
                    result += "9";
                    break;
                
            }
        }
        return result;
    }
    
    public double calculateCost(double selling){       
        double cost = selling -(selling*0.15);
        
        return Rounding.roundCommon(cost, 0);
    }
    
    public void moveFocusToNext(final JComboBox combo,final Component comp){
        txt = (JTextField) combo.getEditor().getEditorComponent();
        txt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                   
                    if(txt.isFocusOwner()){
                        
                        comp.requestFocusInWindow();
                        
                    }
                    
                }
            }
        });
    }
}
