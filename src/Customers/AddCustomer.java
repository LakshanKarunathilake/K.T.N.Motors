package Customers;


import DBController.DataBaseConnector;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author manual-pc
 */
public class AddCustomer {
    
    private static AddCustomer addCustomerInstance;
    
    JTextField customerID_txt;
    JTextField name_txt;
    JTextField address_txt;
    JTextField telephone_txt;
    JTextField address2_txt;
    
    DataBaseConnector connector;
    
    JButton new_btn;
    
    private AddCustomer(){}
    
    public static AddCustomer getInstance(){
        if(addCustomerInstance == null)
            addCustomerInstance = new AddCustomer();
        return addCustomerInstance;
        
    }
    
    public void saveUser() {
        
        String name;
        String address;
        String address2;
        String designation;
        String telNumber;
        long creditLimit;

        
        name = name_txt.getText();
        address = address_txt.getText();
        address2 = address2_txt.getText();
        telNumber = telephone_txt.getText();
        
        String last_id = connector.retreveLastRecord("customers", "customer_code", "customer_code");
        int new_id = Integer.valueOf(last_id)+1;

        ArrayList userList = new ArrayList();
        userList.add(new_id);        
        userList.add(name);
        userList.add(address);
        userList.add(address2);
        userList.add("");
        userList.add(telNumber);

        boolean success = connector.insertRecord("customers", userList);

        if (success) {
            JOptionPane.showMessageDialog(null, "User Successfull added...");
            changeStateAddUser(false);
            emptyUserFields();
        }
        new_btn.setEnabled(true);
    }
    
    public void setFields(JTextField name_txt,JTextField address_txt,JTextField address2_txt,JTextField telephone_txt,JButton new_btn,DataBaseConnector connector){
        
        this.address_txt =address_txt;
        this.name_txt = name_txt;
        this.telephone_txt = telephone_txt;
        this.address2_txt = address2_txt;
        
        this.connector = connector;
        
        this.new_btn = new_btn;
    }
    
    public void changeStateAddUser(Boolean b){
        
        address_txt.setEditable(b);
        address2_txt.setEditable(b);        
        name_txt.setEditable(b);
        telephone_txt.setEditable(b);
        
    }
    
    public void emptyUserFields(){
        
        name_txt.setText("");
        address_txt.setText("");
        address2_txt.setText("");
        telephone_txt.setText("");        
        new_btn.setEnabled(true);
    }
}
