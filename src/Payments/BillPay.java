/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Payments;

import DBController.DataBaseConnector;
import DataManipulation.DataManipulation;
import DataManipulation.MyCombo;
import com.toedter.calendar.JDateChooser;
import java.sql.Timestamp;
import java.util.ArrayList;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author lakshan
 */
public class BillPay {

    JComboBox customerID_combo;
    JComboBox customerName_combo;
    JComboBox invoiceID_combo;
    JComboBox bankCombo;

    JTextField billValue_txt;
    JTextField billDate_txt;
    JTextField payable_text;
    JTextField paying_txt;
    JTextField chequeNo_txt;

    JLabel return_txt;

    JRadioButton curentDate_check;
    JRadioButton chooseDate_check;

    JDateChooser cashDate_chooser;
    JDateChooser chequeDate_chooser;

    JTabbedPane pane;

    JTable table;

    DataBaseConnector connector;

    private static BillPay bill_pay;

    private BillPay() {
    }

    ;
           
    public static BillPay getInstance() {
        if (bill_pay == null) {
            bill_pay = new BillPay();
        }
        return bill_pay;
    }

    private void autoCompleteCombo() {
        MyCombo autoCombo1 = new MyCombo();
        MyCombo autoCombo2 = new MyCombo();
        MyCombo autoCombo3 = new MyCombo();

        autoCombo1.setSearchableCombo(this.customerID_combo, true, "No Result Found");
        autoCombo2.setSearchableCombo(this.customerName_combo, true, "No Result Found");
        autoCombo3.setSearchableCombo(this.invoiceID_combo, true, "No Result Found");

        ArrayList<String> myList = new ArrayList<>();
        myList.add("customers");
        myList.add("customer_code");
        myList.add("name");

        autoCombo2.populateSecondCombo(customerName_combo, customerID_combo, connector, myList, null, false);
    }

    public void fillDataToCombo() {

        DataManipulation manipulation = new DataManipulation(connector);

        manipulation.getRecords("customers", "customer_code", customerID_combo);
        manipulation.getRecords("customers", "name", customerName_combo);
        manipulation.getRecordsWithCondtion("invoices", "invoice_id", "status", "0", invoiceID_combo,"orderDate","ASC");

        autoCompleteCombo();

    }

    public void cashPay() {
        String orderID = String.valueOf(invoiceID_combo.getSelectedItem());
        String paying = paying_txt.getText();

        if (curentDate_check.isSelected()) {

            Timestamp now = new Timestamp(System.currentTimeMillis());
            String timeStamp = String.valueOf(now);

            ArrayList data = new ArrayList();
            data.add(String.valueOf(invoiceID_combo.getSelectedItem()));
            data.add(timeStamp);
            data.add("CASH");
            data.add(paying);

            ArrayList coloumns = new ArrayList();
            coloumns.add("invoice_id");
            coloumns.add("paid_date");
            coloumns.add("Type");
            coloumns.add("amount");

            connector.insertRecordColoumnCount("credit_payments", data, coloumns);

            if (payable_text.getText().equals(paying_txt.getText())) {
                connector.editRecordInTable("invoices", "invoice_id", "status", "1", orderID);
            }

        } else if (chooseDate_check.isSelected()) {

            Timestamp now = new Timestamp(cashDate_chooser.getDate().getTime());
            String timeStamp = String.valueOf(now);

            ArrayList data = new ArrayList();
            data.add(String.valueOf(invoiceID_combo.getSelectedItem()));
            data.add(timeStamp);
            data.add("CASH");
            data.add(paying);

            ArrayList coloumns = new ArrayList();
            coloumns.add("invoice_id");
            coloumns.add("paid_date");
            coloumns.add("Type");
            coloumns.add("amount");

            connector.insertRecordColoumnCount("credit_payments", data, coloumns);
            if (payable_text.getText().equals(paying_txt.getText())) {
                connector.editRecordInTable("invoices", "invoice_id", "status", "1", orderID);
            }
        }

        updatePaidAmount();
        JOptionPane.showMessageDialog(null, "Payment is recorded sucessfully....");

    }

    public void chequePay() {
        String orderID = String.valueOf(invoiceID_combo.getSelectedItem());
        String paying = paying_txt.getText();

        Timestamp now = new Timestamp(System.currentTimeMillis());
        String timeStamp = String.valueOf(now);
        
        Timestamp cheque_date = new Timestamp(this.chequeDate_chooser.getDate().getTime());
        String chequeDate_str = String.valueOf(cheque_date);

        ArrayList data = new ArrayList();
        data.add(String.valueOf(invoiceID_combo.getSelectedItem()));
        data.add(timeStamp);
        data.add("CHEQUE");
        data.add(paying);
        data.add(chequeDate_str);
        data.add(String.valueOf(this.chequeNo_txt.getText()));
        data.add(String.valueOf(this.bankCombo.getSelectedItem()));

        ArrayList coloumns = new ArrayList();
        coloumns.add("invoice_id");
        coloumns.add("paid_date");
        coloumns.add("Type");
        coloumns.add("amount");
        coloumns.add("chequeDate");
        coloumns.add("chequeNumber");
        coloumns.add("Bank");

        connector.insertRecordColoumnCount("credit_payments", data, coloumns);

        if (payable_text.getText().equals(paying_txt.getText())) {
            connector.editRecordInTable("invoices", "invoice_id", "status", "1", orderID);
        }

        updatePaidAmount();
        JOptionPane.showMessageDialog(null, "Payment is recorded sucessfully....");

    }

    public void updatePaidAmount() {
        String invoice_id = String.valueOf(invoiceID_combo.getSelectedItem());
        String now_paid = connector.getRelavantRecord("invoices", "cash_paid", "invoice_id", invoice_id);

        double now_paying = Double.parseDouble(paying_txt.getText());
        double total_paid = Double.valueOf(now_paid) + now_paying;

        connector.editRecordInTable("invoices", "invoice_id", "cash_paid", String.valueOf(total_paid), invoice_id);

    }

    public void checkForBills() {
        ArrayList list = connector.retreveDataColoumnWithTwoCondition("invoices", "invoice_id", "customer_code", String.valueOf(customerID_combo.getSelectedItem()), "status", "0", "orderDate","ASC");
        System.out.println("ArrayList Size : " + list.size());
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }
        Object ob[] = new Object[1];

        if (list.size() > 0) {
            for (int i = 0; i < list.size(); i++) {
                ob[0] = list.get(i);
                model.addRow(ob);
            }
            table.setVisible(true);
        } else {
            JOptionPane.showMessageDialog(null, "This user do not have unpaid bills!....");
        }

    }

    public void filLBillPay() {
        String invoiceID = String.valueOf(invoiceID_combo.getSelectedItem());
        String userID = connector.getRelavantRecord("invoices", "customer_code", "invoice_id", invoiceID);
        String name = connector.getRelavantRecord("customers", "name", "customer_code", userID);

        String returned_amount = connector.getRelavantRecord("invoices", "returned", "invoice_id", invoiceID);
        String grandTotal = connector.getRelavantRecord("invoices", "grandTotal", "invoice_id", invoiceID);

        invoiceID_combo.setSelectedItem(userID);
        customerName_combo.setSelectedItem(name);
        invoiceID_combo.setSelectedItem(invoiceID);
        return_txt.setText(returned_amount);

        pane.setVisible(true);
        if (returned_amount.equals(grandTotal)) {
            JOptionPane.showMessageDialog(null, "Sorry this bill is fully returned ");
        } else {
            FillBill(invoiceID);
        }

    }

    private void FillBill(String invoiceID) {
        ArrayList list = connector.readRow("invoices", "invoice_id", invoiceID);
        String temp = String.valueOf(list.get(3));
        double bill_val = Double.parseDouble(temp);
        billValue_txt.setText(String.valueOf(list.get(3)));
        billDate_txt.setText(String.valueOf(list.get(4)));
        temp = String.valueOf(list.get(7));
        double paid = Double.parseDouble(temp);
        temp = return_txt.getText();
        double returned_amount = Double.valueOf(temp);
        double payable = bill_val - paid - returned_amount;
        payable_text.setText(String.valueOf(payable));
        paying_txt.setText(String.valueOf(payable));

    }

    public void setBillPaymentFields(JTextField billValue_txt, JTextField billDate_txt, JTextField payable_text, JLabel return_txt, JTabbedPane pane) {
        this.billValue_txt = billValue_txt;
        this.billDate_txt = billDate_txt;
        this.pane = pane;
        this.payable_text = payable_text;
        this.return_txt = return_txt;

    }

    public void setCashPayFields(JRadioButton curentDate_check, JRadioButton chooseDate_check, JDateChooser cashDate_chooser) {
        this.chooseDate_check = chooseDate_check;
        this.curentDate_check = curentDate_check;
        this.cashDate_chooser = cashDate_chooser;
    }

    public void setChequePayFields(JComboBox bankName, JDateChooser chequeDate, JTextField chequeNumber) {
        this.bankCombo = bankName;
        this.chequeDate_chooser = chequeDate;
        this.chequeNo_txt = chequeNumber;
    }

    public void setFields(JComboBox customerID_combo, JComboBox customerName_combo, JComboBox invoiceID_combo, JTextField paying_txt, JTable table, DataBaseConnector connector) {
        this.customerID_combo = customerID_combo;
        this.customerName_combo = customerName_combo;
        this.invoiceID_combo = invoiceID_combo;

        this.paying_txt = paying_txt;
        this.table = table;

        this.connector = connector;
    }
}
