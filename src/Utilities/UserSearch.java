/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import DBController.DataBaseConnector;
import java.util.ArrayList;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author manual-pc
 */
public class UserSearch {
   private static UserSearch userSearch;
    private DataBaseConnector connector;
    private JTable resultsTable;
    private JTextField search;
    private UserSearch(){
        
    } 
    
    public static UserSearch getInstance(){
        if(userSearch == null){
            userSearch = new UserSearch();
        }
        return userSearch;
    }
    
    public void getSearchResults(DataBaseConnector connector,JTable resultsTable,String searchPhrase){
        this.connector = connector;
        this.resultsTable = resultsTable;
        
        ArrayList<String[]> results = this.connector.sqlPlainExecution(" SELECT customer_code,name, concat(address1,address2,state) as contactInfo FROM customers WHERE MATCH(name) against ('"+searchPhrase+"');");
        System.out.println("results" + results.size());

        setResultsForTable(results);
    }
    
    private void setResultsForTable(ArrayList<String []> results){
        DefaultTableModel dtm = (DefaultTableModel) resultsTable.getModel();
        dtm.setRowCount(0);
        resultsTable.revalidate();
        for (int i = 0; i < results.size(); i++) {
            Object rowData[] = new Object[3];
            String row[] = results.get(i);           
            for (int j = 0; j < row.length; j++) {
                rowData[j] = row[j];
            }
            dtm.addRow(rowData);
        }
    }
}
