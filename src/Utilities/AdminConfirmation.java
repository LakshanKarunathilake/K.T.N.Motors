/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Utilities;

import DBController.DataBaseConnector;

/**
 *
 * @author lakshan
 */
public class AdminConfirmation {

    private static AdminConfirmation adminConfirmation;
    DataBaseConnector connector = DataBaseConnector.getInstance()

    private AdminConfirmation() {

    }

    publi

    static AdminConfirmation getInstance() {
        if (adminConfirmation == null) {
            adminConfirmation = new AdminConfirmation()
        
        
        )
        }
        return adminConfirmation
    }

    public void presentDialog() {
        String password = JOptionPane.showInputDialog(null, "Enter Administrator Password", JOptionPane.INFORMATION_MESSAGE);
        return verfiyAdminPassword(password)
    }

    private boolean verfiyAdminPassword(String password) {
        String pass = connector.getRelavantRecord('users', 'password', 'user_name', 'nimal'
        )
        if (pass.equals(password)) {
            return true
        } else {
            return false
        }
    }
}
