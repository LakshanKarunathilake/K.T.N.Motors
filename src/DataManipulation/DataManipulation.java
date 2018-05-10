/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManipulation;

import DBController.DataBaseConnector;
import java.util.ArrayList;
import javax.swing.JComboBox;

/**
 *
 * @author manual-pc
 */
public class DataManipulation {
    
    DataBaseConnector connector;
    
    public DataManipulation(DataBaseConnector connector){
        this.connector = connector;
    }
    
    public void getRecords(String tableName,String coloumn,JComboBox combo){
        ArrayList list = new ArrayList();        
        list = connector.retreveDataColumn(tableName, coloumn);       
        for (int i = 0; i < list.size(); i++) {
            combo.addItem(list.get(i));
        }
    }
    
     public void getRecordsWithCondtion(String tableName,String coloumn1,String coloumn2,String condition,JComboBox combo){
        ArrayList list = new ArrayList();        
        list = connector.retreveDataColoumnWithCondition(tableName, coloumn1,coloumn2,condition);
       
        for (int i = 0; i < list.size(); i++) {
            combo.addItem(list.get(i));
        }
    }
}
