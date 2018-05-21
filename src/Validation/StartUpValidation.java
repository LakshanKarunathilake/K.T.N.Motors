/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Validation;

import DBController.DataBaseConnector;
import java.sql.Connection;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author manual-pc
 */
public class StartUpValidation {
    
    Connection conn;
    DataBaseConnector db;

    public StartUpValidation(Connection conn,DataBaseConnector db) {
        this.conn= conn;
        this.db = db;  
    }
    
    public boolean validateDate(){
        Date d = new Date();
        String last_date_string = db.retreveLastRecord("orders", "orderDate", "orderDate");
        if(last_date_string !=null){
            Timestamp last_date_ts = Timestamp.valueOf(last_date_string);
            System.out.println("Last Order Placed date is : " + last_date_string);

            String current_date_string = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss ").format(new java.util.Date());
            System.out.println("Current date of Pc is : " + current_date_string);
            Timestamp current_date_ts = Timestamp.valueOf(current_date_string);

            if (current_date_ts.before(last_date_ts)) {
                System.out.println("Current is before than last");
                return false;
            } else {
                return true;
            } 
        }
        return true;
    }
    
    
    
    
    
}
