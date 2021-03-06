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
    
    static DataBaseConnector connector;
    
    public DataManipulation(DataBaseConnector connector){
        this.connector = connector;
    }

   
    public ArrayList getRecords(String tableName,String coloumn,JComboBox combo){
        if(connector == null){
            System.out.println("Connector eerror");
        }    
        ArrayList list = connector.retreveDataColumn(tableName, coloumn);       
        for (int i = 0; i < list.size(); i++) {
            combo.addItem(list.get(i));
        }
        return list;
    }
    
     public void getRecordsWithCondtion(String tableName,String coloumn1,String coloumn2,String condition,JComboBox combo){
        ArrayList list;       
        list = connector.retreveDataColoumnWithCondition(tableName, coloumn1,coloumn2,condition);
       
        for (int i = 0; i < list.size(); i++) {
            combo.addItem(list.get(i));
        }
    }
}
