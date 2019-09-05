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
    public static String addOrUpdate = "add";
    private String customer_code = "";
    
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
    
    public void updateUser(){
        String name = name_txt.getText();
        String address = address_txt.getText();
        String address2 = address2_txt.getText();
        String telNumber = telephone_txt.getText();
        
        ArrayList list = new ArrayList();
        list.add(name);
        list.add(address);
        list.add(address2);
        list.add(telNumber);
        
        ArrayList coloumn = new ArrayList();
        coloumn.add("name");
        coloumn.add("Address1");
        coloumn.add("Address2");
        coloumn.add("Telephone");
        
        if(connector.editRecordWithColoumns("customers","customer_code",this.customer_code,list,coloumn)){
            JOptionPane.showMessageDialog(null, "User update successfull...","Successful",JOptionPane.INFORMATION_MESSAGE);
        }else{
            JOptionPane.showMessageDialog(null, "User update failure...","Failure",JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public void updateUserFields(String customerID){
        ArrayList<String> user = this.connector.readRow("customers", "customer_code", customerID);
        this.customer_code = customerID;
        System.out.println("user"+user);
        name_txt.setText(user.get(1));
        address_txt.setText(user.get(2));
        address2_txt.setText(user.get(3));
        telephone_txt.setText(user.get(4));
    }
    
    
}
