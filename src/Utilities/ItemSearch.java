/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import DBController.DataBaseConnector;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lakshan
 */
public class ItemSearch {
    private static ItemSearch itemSearch;
    private DataBaseConnector connector;
    private JTable resultsTable;
    private JComboBox category;
    private ItemSearch(){
        
    }
    
    public static ItemSearch getInstance(){
        
        if(itemSearch == null){
            itemSearch = new ItemSearch();
        }
        return itemSearch;
    }
    
    public void getSearchResults(DataBaseConnector connector,JTable resultTable,JComboBox category,String value,Boolean categorySelection){
        this.connector = connector;
        this.resultsTable = resultTable;
        this.category = category;
        String condition = "";
        if (categorySelection) {
            condition = "AND category like '%" + category.getSelectedItem().toString() + "'";
            
        }
        ArrayList<String[]> results = this.connector.sqlPlainExecution("SELECT Item_code,category,Vehicle,brand,stock FROM items\n"
                + " WHERE MATCH (description) AGAINST ('" + value + "')" + condition + " order by category");
        System.out.println("results" + results.size());

        setResultsForTable(results);
    }
    
    private void setResultsForTable(ArrayList<String []> results){
        DefaultTableModel dtm = (DefaultTableModel) resultsTable.getModel();
        dtm.setRowCount(0);
        resultsTable.revalidate();
        for (int i = 0; i < results.size(); i++) {
            Object rowData[] = new Object[5];
            String row[] = results.get(i);           
            for (int j = 0; j < row.length; j++) {
                rowData[j] = row[j];
            }
            dtm.addRow(rowData);
        }
    }
}
