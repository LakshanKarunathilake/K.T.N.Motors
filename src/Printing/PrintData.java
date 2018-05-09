/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Printing;

import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author manual-pc
 */
public class PrintData {
   
   

    
    public Object[][] setPrintable(JTable table){
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int rowCount = dtm.getRowCount();
        int columnCount = dtm.getColumnCount();
        Object records[][]=new Object[rowCount][columnCount];
        System.out.println("Row count: "+rowCount);
        System.out.println("Row count: "+columnCount);
        int l=0;
        for (int i = 0; i < rowCount; i++) {
            l=0;
            for (int j = 0; j < columnCount; j++) {
                
                if(j!=1){
                    records[i][j] = dtm.getValueAt(i, l);
                    l++; 
                }
               
            }
                       
        }
        System.out.println("Current rows:"+l);        
        return records;
        
    }
    
}
