/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings.ClutchPlateSearch;

import DBController.DataBaseConnector;
import java.awt.Font;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author manual-pc
 */
public class ClutchPlateAdd {
    private static ClutchPlateAdd clutchPlate;
    
    //Fields for clutch_plate
    JTextField grew_txt;
    JTextField inner_txt;
    JTextField outer_txt;
    JTextField plate_number_txt;
    JTable table;
    //DB Connector
    DataBaseConnector connector;
    
    private ClutchPlateAdd(){
        
    }
    
    public static ClutchPlateAdd getInstance(){
        if(clutchPlate == null) 
            clutchPlate = new ClutchPlateAdd();
        return clutchPlate;
    }
    
    public void setFields( JTextField grew_txt,JTextField inner_txt,JTextField outer_txt,JTextField plate_number_txt,JTable table,DataBaseConnector connector) {
        this.grew_txt = grew_txt;
        this.inner_txt = inner_txt;
        this.outer_txt = outer_txt;
        this.plate_number_txt = plate_number_txt;
        this.table = table;
        
        this.connector = connector;
    }
    
    public void add(){
        String grew = grew_txt.getText();
        String inner = inner_txt.getText();
        String outer = outer_txt.getText();
        String plate_number = plate_number_txt.getText();     
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        
        
        ArrayList list = new ArrayList();
        list.add(plate_number);
        list.add(grew);
        list.add(inner);
        list.add(outer);        
        list.add(timestamp);
        
        if(!checkAvailability(plate_number)){            
            if (connector.insertRecord("clutch_plate_view", list)) {
                DefaultTableModel dtm = (DefaultTableModel) table.getModel();
                dtm.addRow(new Object[]{plate_number, grew, inner, outer});
                JLabel label = new JLabel("Insertion Successful to table");
                label.setFont(new Font("Arial", Font.BOLD, 18));
                JOptionPane.showMessageDialog(null, label, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JLabel label = new JLabel("Insertion Failure");
                label.setFont(new Font("Arial", Font.BOLD, 18));
                JOptionPane.showMessageDialog(null, label, "Failure", JOptionPane.WARNING_MESSAGE);
            }
        }else{
            JLabel label = new JLabel("Insertion Failure, Item exists already");
            label.setFont(new Font("Arial", Font.BOLD, 18));
            JOptionPane.showMessageDialog(null, label, "Failure", JOptionPane.WARNING_MESSAGE);
        }
        
        
    }
    
    public boolean checkAvailability(String plate_number){
        if(connector.getRelavantRecord("clutch_plate_view", "plate_number", "plate_number",plate_number) != null ){
            return true;
        }else{
            return false;
        }
        
    }
}
