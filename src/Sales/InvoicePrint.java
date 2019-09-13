/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Sales;

import DBController.DataBaseConnector;
import Inventory.MainFrame;
import Printing.PrintData;
import Printing.Printsupport;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.print.PrintService;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.view.JasperViewer;

/**
 *
 * @author manual-pc
 */
public class InvoicePrint {

    String report_folder_path;
    JTextField invoiceID_txt;
    JComboBox customerID_combo;
    JTable table;

    DataBaseConnector connector;

    ArrayList<String> list;

    public InvoicePrint(String report_folder_path, JTextField invoiceID_txt, JComboBox customerID_combo, JTable table, DataBaseConnector connector) {
        this.report_folder_path = report_folder_path;
        this.invoiceID_txt = invoiceID_txt;
        this.customerID_combo = customerID_combo;
        this.table = table;
        this.connector = connector;
    }

    public void setMetaArrayList(ArrayList<String> list) {
        this.list = list;
    }

    public void creditPrint() {

        String path = report_folder_path + "\\SalesInvoice\\sales_invoice.jrxml";
//          String path = "E:\\K.T.N.Motors\\src\\reports\\SalesInvoice\\sales_invoice.jrxml";
        System.out.println("Path :" + path);

        HashMap hm = new HashMap();
        hm.put("userID", String.valueOf(customerID_combo.getSelectedItem()));
        hm.put("invoiceID", String.valueOf(invoiceID_txt.getText()));
        String folder = report_folder_path + "\\SalesInvoice\\";
        System.out.println("FOlder path :" + folder);
        hm.put("subReport", folder);

        JasperReport jr;
        try {
            jr = JasperCompileManager.compileReport(path);
            JasperPrint jp = JasperFillManager.fillReport(jr, hm, connector.startConnection());
            JasperViewer jw = new JasperViewer(jp, false);
            jw.viewReport(jp, false);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "ERROR in Reporting all items...");
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void cashPrint() {

        Printsupport ps = new Printsupport();
//        PrintData pd = new PrintData();
        Object printitem[][] = ps.getTableData(table, "cash");
        ps.setItems(printitem,"cash");
        ps.setMetaData(list);
        PrinterJob pj = PrinterJob.getPrinterJob();

        pj.setPrintable(new Printsupport.MyPrintable(), ps.getCashPageFormat(pj));
        try {
            pj.print();

        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }

    public void creditPrint2() {
        String printerName = "Microsoft Print to PDF";
//        String printerName = "Canon LBP6030/6040/6018L";
        Printsupport ps = new Printsupport();
        Object printitem[][] = ps.getTableData(table, "credit");
        ps.setItems(printitem,"credit");
        ps.setMetaData(list);
        ps.setUserDetails(connector.readRow("customers", "customer_code", customerID_combo.getSelectedItem().toString()));
        ps.setPaymentDetails(connector.readRow("invoices", "invoice_id", invoiceID_txt.getText()));
        PrinterJob pj = PrinterJob.getPrinterJob();

        pj.setPrintable(new Printsupport.CreditPrintable(), ps.getCreditPageFormat(pj));
        try {
            for (PrintService printService : PrinterJob.lookupPrintServices()) {
                System.out.println("check printer name of service " + printService);
                if (printerName.equals(printService.getName())) {
                    System.out.println("correct printer service do print...");
                    pj.setPrintService(printService);
                    pj.print();
                    break;
                }
            }
//            pj.print();

        } catch (PrinterException ex) {
            ex.printStackTrace();
        }
    }
}
