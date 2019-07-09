/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

/**
 *
 * @author lakshan
 */
public class ItemSearch {
    private ItemSearch itemSearch;
    
    private ItemSearch(){
        
    }
    
    public void getInstance(){
        if(itemSearch){
            itemSearch = new ItemSearch();
        }
        retur itemSearch;
    }
    
    public ArrayList getSearchResults(String value){
        
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
