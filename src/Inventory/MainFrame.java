/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory;

import Customers.AddCustomer;
import DBController.DataBaseConnector;
import DataManipulation.DataManipulation;
import DataManipulation.JSONReading;
import DataManipulation.MyCombo;
import DataManipulation.Rounding;
import ItemAdding.ItemAdd;
import Payments.BillPay;
import Purchaising.Purchaise;
import Purchaising.Retailer;
import Sales.AddPrecentage;
import Sales.Invoice;
import Sales.InvoicePrint;
import Sales.InvoiceSearch;
import Sales.InvoiceToDB;
import Sales.ItemToTable;
import SalesReturn.ReturnToDB;
import SalesReturn.SalesReturn;
import Settings.ClutchPlateSearch.ClutchPlateAdd;
import Settings.ClutchPlateSearch.ClutchPlateSearch;
import Settings.EditCustomer;
import Settings.EditQty;
import Settings.PartNumberChange;
import Statistics.DayEndView;
import StockCounting.Stock;
import Utilities.AdminConfirmation;
import Utilities.ItemSearch;
import Utilities.UserSearch;

import Validation.StartUpValidation;
import ViewManipulation.ViewManipulation;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.io.File;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ButtonGroup;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.text.JTextComponent;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import net.sf.jasperreports.view.JasperViewer;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author manual-pc
 */
public class MainFrame extends javax.swing.JFrame {

    /**
     * Creates new form MainFrame
     */
    DataBaseConnector connector;
    DataManipulation manipulation;
    SalesReturn sales_return;
    Invoice item_sale;
    ArrayList[] invoiceData;

//    String report_folder_path = "E:\\K.T.N.Motors\\src\\reports";
//    String report_folder_path = "C:\\Users\\lakshan\\Documents\\GitHub\\K.T.N.Motors\\src\\Reports";
    String report_folder_path = JSONReading.getInstance().getReportLocation();

    String report_folder_path_sub = "\"E:\\\\kade-1.0\\\\src\\\\Reports";

    String backup_path = "";
    Connection conn = null;
    String admin_pass = "puma";

    public MainFrame() {
        setIconImage(Toolkit.getDefaultToolkit().getImage(getClass().getResource("feature.png")));

        connector = DataBaseConnector.getInstance();
        manipulation = new DataManipulation(connector);

        initComponents();
        showTime();
        showDate();
        try {
            conn = connector.startConnection();
        } catch (Exception e) {
            int selection = JOptionPane.showConfirmDialog(null, e.getMessage() + " \n\n Contact Administrator for solving", "Error in starting", JOptionPane.DEFAULT_OPTION);
            if (selection == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        }

        StartUpValidation s = new StartUpValidation(conn, connector);
        if (!s.validateDate()) {
            JOptionPane.showMessageDialog(null, "The last date in the db is after than the current date set the time!!");
            System.exit(0);
        }

        makeAllSalesComponents(false);
        sales_save_btn.setEnabled(false);
        sales_print_btn.setEnabled(false);

        ArrayList emptyCombos = new ArrayList<JComboBox>();
        emptyCombos.add(sales_CID_combo);
        emptyCombos.add(sales_CName_combo);
        emptyCombos.add(sales_item_name_combo);
        emptyCombos.add(sales_itemno_combo);

        ViewManipulation.emptyComboBoxes(emptyCombos);

        int salesTableView[] = {190, 320, 55, 100, 80, 100};
        int itemSearchTableView[] = {190, 190, 150, 170, 55};
        int userSearchTableView[] = {60, 250, 750};

        ViewManipulation.changeTableView(sales_item_table, salesTableView);
        ViewManipulation.changeTableView(itemSearchTable, itemSearchTableView);
        ViewManipulation.changeTableView(userSearchTable, userSearchTableView);

        item_sale = new Invoice(sales_itemno_combo, sales_item_name_combo, sales_CID_combo, sales_CName_combo, sales_qty_Txt, connector);
        invoiceData = item_sale.fillDataToCombo();

        Invoice.setSaleID(sales_InvoiceID_txt, connector);

        sales_halfPay_panel.setVisible(false);

    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jDateChooser1 = new com.toedter.calendar.JDateChooser();
        MenuBar = new javax.swing.JPanel();
        AddProductLabel = new javax.swing.JLabel();
        SalesLabel = new javax.swing.JLabel();
        ReportLabel = new javax.swing.JLabel();
        updateQtylbl = new javax.swing.JLabel();
        AddUserLabel = new javax.swing.JLabel();
        payBillsLabel = new javax.swing.JLabel();
        Return_label = new javax.swing.JLabel();
        jButton18 = new javax.swing.JButton();
        MainChangeFrame = new javax.swing.JPanel();
        SalesPanel = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        sales_search_user_btn = new javax.swing.JButton();
        sales_itemno_combo = new javax.swing.JComboBox<>();
        jButton14 = new javax.swing.JButton();
        jLabel16 = new javax.swing.JLabel();
        sales_item_name_combo = new javax.swing.JComboBox<>();
        jLabel17 = new javax.swing.JLabel();
        sales_qty_Txt = new javax.swing.JTextField();
        jLabel18 = new javax.swing.JLabel();
        sales_available_qty_txt = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        sales_item_table = new javax.swing.JTable();
        jLabel19 = new javax.swing.JLabel();
        sales_InvoiceID_txt = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        sales_new_btn = new javax.swing.JButton();
        sales_save_btn = new javax.swing.JButton();
        sales_print_btn = new javax.swing.JButton();
        sales_searchI_btn1 = new javax.swing.JButton();
        sales_cancel_btn = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        sales_total_txt = new javax.swing.JTextField();
        sales_discount_txt = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        sales_grand_txt = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        sales_CName_combo = new javax.swing.JComboBox<>();
        sales_CID_combo = new javax.swing.JComboBox<>();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        sales_remove_btn = new javax.swing.JButton();
        sales_unit_Txt = new javax.swing.JTextField();
        jLabel55 = new javax.swing.JLabel();
        sales_halfPay_check = new javax.swing.JCheckBox();
        sales_halfPay_panel = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        sales_halfPay_creditTxt = new javax.swing.JTextField();
        sales_halfPay_txt = new javax.swing.JTextField();
        jLabel56 = new javax.swing.JLabel();
        jLabel88 = new javax.swing.JLabel();
        sales_addPrecent_txt = new javax.swing.JTextField();
        AdditionalTextPannel = new javax.swing.JPanel();
        jLabel96 = new javax.swing.JLabel();
        sales_additional_txt = new javax.swing.JTextField();
        settings_panel = new javax.swing.JPanel();
        jButton20 = new javax.swing.JButton();
        settings_sub_panel = new javax.swing.JPanel();
        Clutch_Plate_Main = new javax.swing.JPanel();
        Clutch_Plate_Tabbed = new javax.swing.JTabbedPane();
        Clutch_Plate_Search = new javax.swing.JPanel();
        jLabel89 = new javax.swing.JLabel();
        clutch_plate_search_grew_combo = new javax.swing.JComboBox<>();
        jLabel90 = new javax.swing.JLabel();
        clutch_plate_search_inner_combo = new javax.swing.JComboBox<>();
        jLabel91 = new javax.swing.JLabel();
        clutch_plate_search_outer_combo = new javax.swing.JComboBox<>();
        jScrollPane5 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        Clutch_Plate_Add = new javax.swing.JPanel();
        jLabel92 = new javax.swing.JLabel();
        clutch_plate_add_grew_txt = new javax.swing.JTextField();
        jLabel93 = new javax.swing.JLabel();
        clutch_plate_add_inner_txt = new javax.swing.JTextField();
        jLabel94 = new javax.swing.JLabel();
        clutch_plate_add_outer_txt = new javax.swing.JTextField();
        jLabel95 = new javax.swing.JLabel();
        clutch_plate_add_plateNumber_txt = new javax.swing.JTextField();
        jScrollPane6 = new javax.swing.JScrollPane();
        clutch_plate_add_table = new javax.swing.JTable();
        clutch_plate_add_btn = new javax.swing.JButton();
        Clutch_Plate_Edit = new javax.swing.JPanel();
        settings_sub_edit_panel = new javax.swing.JPanel();
        settings_qty_edit_qty_lbl = new javax.swing.JLabel();
        settings_qty_edit_itemNo_combo = new javax.swing.JComboBox<>();
        jLabel82 = new javax.swing.JLabel();
        jLabel83 = new javax.swing.JLabel();
        jLabel84 = new javax.swing.JLabel();
        settings_qty_edit_desctiption_lbl = new javax.swing.JLabel();
        jLabel85 = new javax.swing.JLabel();
        settings_qty_edit_qty_txt = new javax.swing.JTextField();
        jButton21 = new javax.swing.JButton();
        part_number_change_panel = new javax.swing.JPanel();
        jLabel80 = new javax.swing.JLabel();
        change_item_combo = new javax.swing.JComboBox<>();
        jLabel87 = new javax.swing.JLabel();
        jButton22 = new javax.swing.JButton();
        changed_item_txt = new javax.swing.JTextField();
        jButton23 = new javax.swing.JButton();
        jButton24 = new javax.swing.JButton();
        stock_count_panel = new javax.swing.JPanel();
        stock_update_btn = new javax.swing.JButton();
        stock_item_combo = new javax.swing.JComboBox<>();
        stock_selling_lbl = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        stock_qty_txt = new javax.swing.JTextField();
        stock_selling_txt = new javax.swing.JTextField();
        jLabel70 = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        stock_description_lbl = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        stock_availableQty_lbl = new javax.swing.JLabel();
        stock_c_selling_lbl = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        stock_item_table = new javax.swing.JTable();
        jButton19 = new javax.swing.JButton();
        jLabel81 = new javax.swing.JLabel();
        jLabel86 = new javax.swing.JLabel();
        AddUserPannel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        NameTxt = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        AddressTxt = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        TelephoneTxt = new javax.swing.JTextField();
        cancelBtn = new javax.swing.JButton();
        UserPanel = new javax.swing.JPanel();
        saveBtn = new javax.swing.JButton();
        jLabel67 = new javax.swing.JLabel();
        AddressTxt1 = new javax.swing.JTextField();
        newBtn = new javax.swing.JButton();
        editBtn = new javax.swing.JButton();
        jButton25 = new javax.swing.JButton();
        SalesReturnPanel = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        return_userName_combo = new javax.swing.JComboBox<>();
        return_total_txt = new javax.swing.JLabel();
        return_reason_combo = new javax.swing.JComboBox<>();
        jLabel47 = new javax.swing.JLabel();
        return_userID_combo = new javax.swing.JComboBox<>();
        jLabel49 = new javax.swing.JLabel();
        selectAll_button = new javax.swing.JToggleButton();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        return_no = new javax.swing.JRadioButton();
        return_yes = new javax.swing.JRadioButton();
        return_calculation_btn = new javax.swing.JButton();
        return_cancel_btn = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        sales_return_subPanel = new javax.swing.JPanel();
        returns_item_table = new javax.swing.JScrollPane();
        return_item_table = new javax.swing.JTable();
        returns_search_panel = new javax.swing.JPanel();
        return_search_item_combo = new javax.swing.JComboBox<>();
        jLabel48 = new javax.swing.JLabel();
        return_from_picker = new com.toedter.calendar.JDateChooser();
        jLabel53 = new javax.swing.JLabel();
        return_to_picker = new com.toedter.calendar.JDateChooser();
        jScrollPane4 = new javax.swing.JScrollPane();
        return_invoiceSearch_table = new javax.swing.JTable();
        jLabel52 = new javax.swing.JLabel();
        return_invoiceID_combo = new javax.swing.JComboBox<>();
        return_search_invoice = new javax.swing.JButton();
        jLabel54 = new javax.swing.JLabel();
        return_button1 = new javax.swing.JButton();
        ReportPanel = new javax.swing.JPanel();
        reports_dayEnd_btn = new javax.swing.JButton();
        report_items_btn = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        reports_customer_btn1 = new javax.swing.JButton();
        reports_customer_btn2 = new javax.swing.JButton();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        userWisePanel = new javax.swing.JPanel();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jLabel29 = new javax.swing.JLabel();
        report_name_combo = new javax.swing.JComboBox<>();
        report_userID_combo = new javax.swing.JComboBox<>();
        jLabel32 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jButton13 = new javax.swing.JButton();
        jButton30 = new javax.swing.JButton();
        jButton31 = new javax.swing.JButton();
        itemWisePanel = new javax.swing.JPanel();
        report_item_combo = new javax.swing.JComboBox<>();
        jButton9 = new javax.swing.JButton();
        jButton10 = new javax.swing.JButton();
        jButton11 = new javax.swing.JButton();
        jLabel41 = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        jButton12 = new javax.swing.JButton();
        purchasingPanel = new javax.swing.JPanel();
        report_purchasing_invoice_no = new javax.swing.JComboBox<>();
        jButton32 = new javax.swing.JButton();
        jLabel60 = new javax.swing.JLabel();
        item_report_checkBox = new javax.swing.JCheckBox();
        reports_date1_picker = new com.toedter.calendar.JDateChooser();
        reports_date2_picker = new com.toedter.calendar.JDateChooser();
        item_date1_label = new javax.swing.JLabel();
        item_date2_label = new javax.swing.JLabel();
        SettingsPanel = new javax.swing.JPanel();
        AddItemPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        add_itemNo_txt = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        add_item_vehicle_txt = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        add_item_desc_txt = new javax.swing.JTextField();
        add_item_cancel_btn = new javax.swing.JButton();
        jLabel11 = new javax.swing.JLabel();
        UserPanel1 = new javax.swing.JPanel();
        item_add_edit_btn = new javax.swing.JButton();
        item_add_new_btn = new javax.swing.JButton();
        add_item_save_btn = new javax.swing.JButton();
        jLabel57 = new javax.swing.JLabel();
        add_item_category_combo = new javax.swing.JComboBox<>();
        jLabel58 = new javax.swing.JLabel();
        add_item_brand_txt = new javax.swing.JTextField();
        jLabel63 = new javax.swing.JLabel();
        add_item_location_txt = new javax.swing.JTextField();
        add_item_unit_combo = new javax.swing.JComboBox<>();
        Purchaising_base_panel = new javax.swing.JPanel();
        purchaising_main_panel = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        update_description_txt = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        update_billPrice_txt = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        update_costP_txt = new javax.swing.JTextField();
        itemCancelBtn1 = new javax.swing.JButton();
        jLabel28 = new javax.swing.JLabel();
        UserPanel2 = new javax.swing.JPanel();
        update_save_btn = new javax.swing.JButton();
        update_qty_txt = new javax.swing.JTextField();
        update_available_lbl = new javax.swing.JLabel();
        update_itemNo_combo = new javax.swing.JComboBox<>();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel72 = new javax.swing.JLabel();
        update_cost_lbl = new javax.swing.JLabel();
        update_sellingP_txt = new javax.swing.JTextField();
        jLabel74 = new javax.swing.JLabel();
        update_selling_lbl = new javax.swing.JLabel();
        jLabel73 = new javax.swing.JLabel();
        purchaise_retailer_combo = new javax.swing.JComboBox<>();
        jLabel75 = new javax.swing.JLabel();
        purchaise_invoiceno_txt = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        retailer_panel = new javax.swing.JPanel();
        jLabel76 = new javax.swing.JLabel();
        retailer_name_txt = new javax.swing.JTextField();
        jLabel77 = new javax.swing.JLabel();
        retailer_address_txt = new javax.swing.JTextField();
        jLabel78 = new javax.swing.JLabel();
        retailer_contact_txt = new javax.swing.JTextField();
        jButton15 = new javax.swing.JButton();
        jButton16 = new javax.swing.JButton();
        jButton17 = new javax.swing.JButton();
        bill_pay_panel = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        bill_name_combo = new javax.swing.JComboBox<>();
        bill_InvoiceID_combo = new javax.swing.JComboBox<>();
        bill_userID_combo = new javax.swing.JComboBox<>();
        bill_paymentPane = new javax.swing.JTabbedPane();
        jPanel4 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        cashPay = new javax.swing.JButton();
        bill_currentDate = new javax.swing.JRadioButton();
        bill_chooseDate = new javax.swing.JRadioButton();
        bill_cash_datePicker = new com.toedter.calendar.JDateChooser();
        bill_cheque_number = new javax.swing.JPanel();
        bill_bank_comnbo = new javax.swing.JComboBox<>();
        bill_chequeNo_txt = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        chequePay = new javax.swing.JButton();
        bill_chequeDatePicker = new com.toedter.calendar.JDateChooser();
        jPanel6 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Bill_table = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        bill_pay_select_btn = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        Bill_date_txt = new javax.swing.JTextField();
        jLabel37 = new javax.swing.JLabel();
        bill_invoiceValue_txt = new javax.swing.JTextField();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel64 = new javax.swing.JLabel();
        bill_paying_txt = new javax.swing.JTextField();
        jLabel65 = new javax.swing.JLabel();
        bill_payable_txt = new javax.swing.JTextField();
        jLabel66 = new javax.swing.JLabel();
        bill_return_label = new javax.swing.JLabel();
        ItemSearchPanel = new javax.swing.JPanel();
        item_search_txt = new javax.swing.JTextField();
        jButton26 = new javax.swing.JButton();
        jScrollPane7 = new javax.swing.JScrollPane();
        itemSearchTable = new javax.swing.JTable();
        jButton27 = new javax.swing.JButton();
        jLabel97 = new javax.swing.JLabel();
        item_search_category_combo = new javax.swing.JComboBox<>();
        jLabel98 = new javax.swing.JLabel();
        item_search_checkbox = new javax.swing.JCheckBox();
        UserSearchPanel = new javax.swing.JPanel();
        user_search_txt = new javax.swing.JTextField();
        jButton28 = new javax.swing.JButton();
        jScrollPane8 = new javax.swing.JScrollPane();
        userSearchTable = new javax.swing.JTable();
        jButton29 = new javax.swing.JButton();
        jLabel99 = new javax.swing.JLabel();
        TitlePanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        clock_txt = new javax.swing.JLabel();
        date_txt = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jLabel79 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1024, 768));
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        MenuBar.setBackground(new java.awt.Color(51, 102, 255));
        MenuBar.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        AddProductLabel.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        AddProductLabel.setText(" Add Product");
        AddProductLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        AddProductLabel.setMinimumSize(new java.awt.Dimension(63, 16));
        AddProductLabel.setOpaque(true);
        AddProductLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddProductLabelMouseClicked(evt);
            }
        });
        MenuBar.add(AddProductLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 180, 200, 80));

        SalesLabel.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        SalesLabel.setText("      Sales");
        SalesLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        SalesLabel.setOpaque(true);
        SalesLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SalesLabelMouseClicked(evt);
            }
        });
        MenuBar.add(SalesLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 360, 200, 80));

        ReportLabel.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        ReportLabel.setText("     Reports");
        ReportLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        ReportLabel.setOpaque(true);
        ReportLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ReportLabelMouseClicked(evt);
            }
        });
        MenuBar.add(ReportLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, 200, 80));

        updateQtylbl.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        updateQtylbl.setText("  Purchaising");
        updateQtylbl.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        updateQtylbl.setOpaque(true);
        updateQtylbl.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                updateQtylblMouseClicked(evt);
            }
        });
        MenuBar.add(updateQtylbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 10, 200, 80));

        AddUserLabel.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        AddUserLabel.setText("   Add User");
        AddUserLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        AddUserLabel.setOpaque(true);
        AddUserLabel.setPreferredSize(new java.awt.Dimension(63, 16));
        AddUserLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                AddUserLabelMouseClicked(evt);
            }
        });
        MenuBar.add(AddUserLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 100, 200, 70));

        payBillsLabel.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        payBillsLabel.setText("    Pay Bills");
        payBillsLabel.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        payBillsLabel.setOpaque(true);
        payBillsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                payBillsLabelMouseClicked(evt);
            }
        });
        MenuBar.add(payBillsLabel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 200, 80));

        Return_label.setFont(new java.awt.Font("Arial Black", 1, 24)); // NOI18N
        Return_label.setText("     Returns");
        Return_label.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(0, 0, 0), 2, true));
        Return_label.setOpaque(true);
        Return_label.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Return_labelMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Return_labelMouseEntered(evt);
            }
        });
        MenuBar.add(Return_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, 200, 80));

        jButton18.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton18.setForeground(new java.awt.Color(204, 0, 0));
        jButton18.setText("Backup");
        jButton18.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton18ActionPerformed(evt);
            }
        });
        MenuBar.add(jButton18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 660, 180, 50));

        getContentPane().add(MenuBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 220, 770));

        MainChangeFrame.setBackground(new java.awt.Color(255, 204, 204));
        MainChangeFrame.setMinimumSize(new java.awt.Dimension(814, 850));
        MainChangeFrame.setPreferredSize(new java.awt.Dimension(814, 850));
        MainChangeFrame.setLayout(new java.awt.CardLayout());

        SalesPanel.setBackground(new java.awt.Color(255, 255, 255));
        SalesPanel.setMinimumSize(new java.awt.Dimension(1366, 768));
        SalesPanel.setPreferredSize(new java.awt.Dimension(1366, 768));
        SalesPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel14.setFont(new java.awt.Font("SF New Republic SC", 0, 18)); // NOI18N
        jLabel14.setText("Customer ID: ");
        SalesPanel.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 60, -1, -1));

        jLabel15.setFont(new java.awt.Font("SF New Republic SC", 0, 18)); // NOI18N
        jLabel15.setText("Name:");
        SalesPanel.add(jLabel15, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 20, -1, 20));

        sales_search_user_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_search_user_btn.setText("Search User");
        sales_search_user_btn.setFocusable(false);
        sales_search_user_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_search_user_btnActionPerformed(evt);
            }
        });
        SalesPanel.add(sales_search_user_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 170, 40));

        sales_itemno_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_itemno_combo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sales_itemno_comboFocusGained(evt);
            }
        });
        sales_itemno_combo.addPopupMenuListener(new javax.swing.event.PopupMenuListener() {
            public void popupMenuCanceled(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeInvisible(javax.swing.event.PopupMenuEvent evt) {
            }
            public void popupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {
                sales_itemno_comboPopupMenuWillBecomeVisible(evt);
            }
        });
        sales_itemno_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_itemno_comboActionPerformed(evt);
            }
        });
        sales_itemno_combo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sales_itemno_comboKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sales_itemno_comboKeyReleased(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sales_itemno_comboKeyTyped(evt);
            }
        });
        SalesPanel.add(sales_itemno_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, 210, 40));

        jButton14.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton14.setText("Search Item");
        jButton14.setFocusable(false);
        jButton14.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton14ActionPerformed(evt);
            }
        });
        SalesPanel.add(jButton14, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 70, 180, 40));

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel16.setText("Part Number");
        SalesPanel.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 120, 120, 20));

        sales_item_name_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_item_name_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_item_name_comboActionPerformed(evt);
            }
        });
        SalesPanel.add(sales_item_name_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 150, 260, 40));

        jLabel17.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel17.setText("Category");
        SalesPanel.add(jLabel17, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 120, 120, 20));

        sales_qty_Txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_qty_Txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sales_qty_TxtFocusGained(evt);
            }
        });
        sales_qty_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_qty_TxtActionPerformed(evt);
            }
        });
        sales_qty_Txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sales_qty_TxtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sales_qty_TxtKeyTyped(evt);
            }
        });
        SalesPanel.add(sales_qty_Txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 150, 90, 40));

        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setText("Unit");
        SalesPanel.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 120, -1, -1));

        sales_available_qty_txt.setEditable(false);
        sales_available_qty_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_available_qty_txt.setFocusable(false);
        SalesPanel.add(sales_available_qty_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(690, 150, 80, 40));

        jScrollPane1.setFont(new java.awt.Font("Dialog", 1, 20)); // NOI18N

        sales_item_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Part Number", "Description", "Unit", "UnitPrice", "Qty", "Total", "Select"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(sales_item_table);
        if (sales_item_table.getColumnModel().getColumnCount() > 0) {
            sales_item_table.getColumnModel().getColumn(6).setResizable(false);
        }

        SalesPanel.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 200, 670, 200));

        jLabel19.setFont(new java.awt.Font("SF New Republic SC", 0, 18)); // NOI18N
        jLabel19.setText("Invoice ID: ");
        SalesPanel.add(jLabel19, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, -1, -1));

        sales_InvoiceID_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        SalesPanel.add(sales_InvoiceID_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 10, 140, 30));

        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        sales_new_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_new_btn.setText("New");
        sales_new_btn.setPreferredSize(new java.awt.Dimension(79, 29));
        sales_new_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_new_btnActionPerformed(evt);
            }
        });
        jPanel1.add(sales_new_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 90, 60));

        sales_save_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_save_btn.setText("Save");
        sales_save_btn.setFocusable(false);
        sales_save_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_save_btnActionPerformed(evt);
            }
        });
        jPanel1.add(sales_save_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 90, 50));

        sales_print_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_print_btn.setText("Print");
        sales_print_btn.setFocusable(false);
        sales_print_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_print_btnActionPerformed(evt);
            }
        });
        jPanel1.add(sales_print_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 220, 90, 50));

        sales_searchI_btn1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_searchI_btn1.setText("Search ");
        sales_searchI_btn1.setFocusable(false);
        sales_searchI_btn1.setMaximumSize(new java.awt.Dimension(79, 29));
        sales_searchI_btn1.setMinimumSize(new java.awt.Dimension(79, 29));
        sales_searchI_btn1.setPreferredSize(new java.awt.Dimension(79, 29));
        sales_searchI_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_searchI_btn1ActionPerformed(evt);
            }
        });
        jPanel1.add(sales_searchI_btn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 120, 90, 50));

        sales_cancel_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_cancel_btn.setText("Cancel");
        sales_cancel_btn.setFocusable(false);
        sales_cancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_cancel_btnActionPerformed(evt);
            }
        });
        jPanel1.add(sales_cancel_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 90, 60));

        SalesPanel.add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 200, 90, 270));

        jLabel20.setFont(new java.awt.Font("SF New Republic SC", 0, 18)); // NOI18N
        jLabel20.setText("Total");
        SalesPanel.add(jLabel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 430, -1, -1));

        sales_total_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        SalesPanel.add(sales_total_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 420, 120, 30));

        sales_discount_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_discount_txt.setText("0");
        sales_discount_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sales_discount_txtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sales_discount_txtKeyTyped(evt);
            }
        });
        SalesPanel.add(sales_discount_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 460, 120, 30));

        jLabel21.setFont(new java.awt.Font("Snickers", 0, 24)); // NOI18N
        jLabel21.setText("%");
        SalesPanel.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(880, 730, -1, -1));

        sales_grand_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_grand_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_grand_txtActionPerformed(evt);
            }
        });
        SalesPanel.add(sales_grand_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(550, 500, 120, 30));

        jLabel22.setFont(new java.awt.Font("SF New Republic SC", 0, 18)); // NOI18N
        jLabel22.setText("Grand Total");
        SalesPanel.add(jLabel22, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 510, -1, -1));

        sales_CName_combo.setEditable(true);
        sales_CName_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_CName_combo.setNextFocusableComponent(sales_itemno_combo);
        sales_CName_combo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sales_CName_comboFocusGained(evt);
            }
        });
        sales_CName_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_CName_comboActionPerformed(evt);
            }
        });
        sales_CName_combo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sales_CName_comboKeyPressed(evt);
            }
        });
        SalesPanel.add(sales_CName_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 10, 280, 30));

        sales_CID_combo.setEditable(true);
        sales_CID_combo.setFont(new java.awt.Font("Dialog", 1, 14)); // NOI18N
        sales_CID_combo.setToolTipText("");
        sales_CID_combo.setAutoscrolls(true);
        sales_CID_combo.setNextFocusableComponent(sales_itemno_combo);
        sales_CID_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_CID_comboActionPerformed(evt);
            }
        });
        sales_CID_combo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sales_CID_comboKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                sales_CID_comboKeyReleased(evt);
            }
        });
        SalesPanel.add(sales_CID_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 60, 140, 30));

        jLabel23.setFont(new java.awt.Font("SF New Republic SC", 0, 18)); // NOI18N
        jLabel23.setText("Discount");
        SalesPanel.add(jLabel23, new org.netbeans.lib.awtextra.AbsoluteConstraints(450, 470, -1, -1));

        jLabel24.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel24.setText("Availbale Qty");
        SalesPanel.add(jLabel24, new org.netbeans.lib.awtextra.AbsoluteConstraints(680, 120, -1, -1));

        sales_remove_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_remove_btn.setText("Remove");
        sales_remove_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_remove_btnActionPerformed(evt);
            }
        });
        SalesPanel.add(sales_remove_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 410, 130, 40));

        sales_unit_Txt.setEditable(false);
        sales_unit_Txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        sales_unit_Txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                sales_unit_TxtFocusGained(evt);
            }
        });
        sales_unit_Txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_unit_TxtActionPerformed(evt);
            }
        });
        sales_unit_Txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sales_unit_TxtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sales_unit_TxtKeyTyped(evt);
            }
        });
        SalesPanel.add(sales_unit_Txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 150, 80, 40));

        jLabel55.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel55.setText("Qty Need");
        SalesPanel.add(jLabel55, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 120, -1, -1));

        sales_halfPay_check.setBackground(new java.awt.Color(255, 255, 255));
        sales_halfPay_check.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        sales_halfPay_check.setText("Half Pay");
        sales_halfPay_check.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_halfPay_checkActionPerformed(evt);
            }
        });
        SalesPanel.add(sales_halfPay_check, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 450, -1, -1));

        sales_halfPay_panel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel46.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel46.setText("Pay In Cash");

        sales_halfPay_creditTxt.setEditable(false);
        sales_halfPay_creditTxt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        sales_halfPay_txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sales_halfPay_txt.setText("0");
        sales_halfPay_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sales_halfPay_txtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                sales_halfPay_txtKeyTyped(evt);
            }
        });

        jLabel56.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel56.setText("Crediting amount");

        javax.swing.GroupLayout sales_halfPay_panelLayout = new javax.swing.GroupLayout(sales_halfPay_panel);
        sales_halfPay_panel.setLayout(sales_halfPay_panelLayout);
        sales_halfPay_panelLayout.setHorizontalGroup(
            sales_halfPay_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sales_halfPay_panelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(sales_halfPay_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel46)
                    .addComponent(sales_halfPay_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(sales_halfPay_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel56)
                    .addComponent(sales_halfPay_creditTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(102, 102, 102))
        );
        sales_halfPay_panelLayout.setVerticalGroup(
            sales_halfPay_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(sales_halfPay_panelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(sales_halfPay_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel46)
                    .addComponent(jLabel56))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(sales_halfPay_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sales_halfPay_creditTxt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(sales_halfPay_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        SalesPanel.add(sales_halfPay_panel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 480, 340, 60));

        jLabel88.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel88.setText("Add :");
        SalesPanel.add(jLabel88, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 410, 70, 40));

        sales_addPrecent_txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        sales_addPrecent_txt.setText("0");
        sales_addPrecent_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                sales_addPrecent_txtKeyPressed(evt);
            }
        });
        SalesPanel.add(sales_addPrecent_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 410, 90, 40));

        AdditionalTextPannel.setBackground(new java.awt.Color(255, 255, 255));

        jLabel96.setFont(new java.awt.Font("Tahoma", 1, 13)); // NOI18N
        jLabel96.setText("Additional Note");

        sales_additional_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sales_additional_txtActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout AdditionalTextPannelLayout = new javax.swing.GroupLayout(AdditionalTextPannel);
        AdditionalTextPannel.setLayout(AdditionalTextPannelLayout);
        AdditionalTextPannelLayout.setHorizontalGroup(
            AdditionalTextPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdditionalTextPannelLayout.createSequentialGroup()
                .addComponent(jLabel96)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(sales_additional_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 649, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        AdditionalTextPannelLayout.setVerticalGroup(
            AdditionalTextPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(AdditionalTextPannelLayout.createSequentialGroup()
                .addGroup(AdditionalTextPannelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(sales_additional_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel96, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 18, Short.MAX_VALUE))
        );

        SalesPanel.add(AdditionalTextPannel, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, 760, 80));

        MainChangeFrame.add(SalesPanel, "card2");

        jButton20.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton20.setText("Edit Qty");
        jButton20.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton20ActionPerformed(evt);
            }
        });

        settings_sub_panel.setLayout(new java.awt.CardLayout());

        Clutch_Plate_Tabbed.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Clutch_Plate_TabbedMouseClicked(evt);
            }
        });

        jLabel89.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel89.setText("Grew");

        clutch_plate_search_grew_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel90.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel90.setText("Diameter");

        clutch_plate_search_inner_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel91.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel91.setText("Outer");

        clutch_plate_search_outer_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane5.setViewportView(jTable1);

        javax.swing.GroupLayout Clutch_Plate_SearchLayout = new javax.swing.GroupLayout(Clutch_Plate_Search);
        Clutch_Plate_Search.setLayout(Clutch_Plate_SearchLayout);
        Clutch_Plate_SearchLayout.setHorizontalGroup(
            Clutch_Plate_SearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Clutch_Plate_SearchLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(Clutch_Plate_SearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clutch_plate_search_grew_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(29, 29, 29)
                .addGroup(Clutch_Plate_SearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clutch_plate_search_inner_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(45, 45, 45)
                .addGroup(Clutch_Plate_SearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clutch_plate_search_outer_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 97, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(303, Short.MAX_VALUE))
            .addGroup(Clutch_Plate_SearchLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane5)
                .addContainerGap())
        );
        Clutch_Plate_SearchLayout.setVerticalGroup(
            Clutch_Plate_SearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Clutch_Plate_SearchLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Clutch_Plate_SearchLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(Clutch_Plate_SearchLayout.createSequentialGroup()
                        .addComponent(jLabel91, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clutch_plate_search_outer_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Clutch_Plate_SearchLayout.createSequentialGroup()
                        .addComponent(jLabel90, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clutch_plate_search_inner_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(Clutch_Plate_SearchLayout.createSequentialGroup()
                        .addComponent(jLabel89, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clutch_plate_search_grew_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 332, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        Clutch_Plate_Tabbed.addTab("SEARCH", Clutch_Plate_Search);

        jLabel92.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel92.setText("Grew");

        clutch_plate_add_grew_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel93.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel93.setText("Inner");

        clutch_plate_add_inner_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel94.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel94.setText("Outer");

        clutch_plate_add_outer_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel95.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jLabel95.setText("Plate Number");

        clutch_plate_add_plateNumber_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        clutch_plate_add_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Plate Number", "Gew", "Inner", "Outer"
            }
        ));
        jScrollPane6.setViewportView(clutch_plate_add_table);

        clutch_plate_add_btn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        clutch_plate_add_btn.setText("Add");
        clutch_plate_add_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clutch_plate_add_btnActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout Clutch_Plate_AddLayout = new javax.swing.GroupLayout(Clutch_Plate_Add);
        Clutch_Plate_Add.setLayout(Clutch_Plate_AddLayout);
        Clutch_Plate_AddLayout.setHorizontalGroup(
            Clutch_Plate_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Clutch_Plate_AddLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(Clutch_Plate_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jScrollPane6)
                    .addGroup(Clutch_Plate_AddLayout.createSequentialGroup()
                        .addGroup(Clutch_Plate_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(30, 30, 30)
                        .addGroup(Clutch_Plate_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(clutch_plate_add_outer_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clutch_plate_add_inner_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(clutch_plate_add_grew_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(Clutch_Plate_AddLayout.createSequentialGroup()
                                .addComponent(clutch_plate_add_plateNumber_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 161, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(99, 99, 99)
                                .addComponent(clutch_plate_add_btn)))
                        .addGap(248, 248, 248)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Clutch_Plate_AddLayout.setVerticalGroup(
            Clutch_Plate_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Clutch_Plate_AddLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(Clutch_Plate_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel92, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clutch_plate_add_grew_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Clutch_Plate_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel93, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clutch_plate_add_inner_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(Clutch_Plate_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel94, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clutch_plate_add_outer_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(Clutch_Plate_AddLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel95, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clutch_plate_add_plateNumber_txt, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clutch_plate_add_btn))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Clutch_Plate_Tabbed.addTab("ADD", Clutch_Plate_Add);

        javax.swing.GroupLayout Clutch_Plate_EditLayout = new javax.swing.GroupLayout(Clutch_Plate_Edit);
        Clutch_Plate_Edit.setLayout(Clutch_Plate_EditLayout);
        Clutch_Plate_EditLayout.setHorizontalGroup(
            Clutch_Plate_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 753, Short.MAX_VALUE)
        );
        Clutch_Plate_EditLayout.setVerticalGroup(
            Clutch_Plate_EditLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 459, Short.MAX_VALUE)
        );

        Clutch_Plate_Tabbed.addTab("EDIT", Clutch_Plate_Edit);

        javax.swing.GroupLayout Clutch_Plate_MainLayout = new javax.swing.GroupLayout(Clutch_Plate_Main);
        Clutch_Plate_Main.setLayout(Clutch_Plate_MainLayout);
        Clutch_Plate_MainLayout.setHorizontalGroup(
            Clutch_Plate_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Clutch_Plate_Tabbed)
        );
        Clutch_Plate_MainLayout.setVerticalGroup(
            Clutch_Plate_MainLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Clutch_Plate_MainLayout.createSequentialGroup()
                .addComponent(Clutch_Plate_Tabbed, javax.swing.GroupLayout.PREFERRED_SIZE, 486, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 22, Short.MAX_VALUE))
        );

        settings_sub_panel.add(Clutch_Plate_Main, "card4");

        settings_sub_edit_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        settings_qty_edit_qty_lbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        settings_sub_edit_panel.add(settings_qty_edit_qty_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 230, 530, 30));

        settings_qty_edit_itemNo_combo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        settings_qty_edit_itemNo_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settings_qty_edit_itemNo_comboActionPerformed(evt);
            }
        });
        settings_sub_edit_panel.add(settings_qty_edit_itemNo_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 80, 260, 50));

        jLabel82.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel82.setText("Item number");
        settings_sub_edit_panel.add(jLabel82, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, -1, -1));

        jLabel83.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel83.setText("Change Qty To :");
        settings_sub_edit_panel.add(jLabel83, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, -1, -1));

        jLabel84.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel84.setText("Description  : ");
        settings_sub_edit_panel.add(jLabel84, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 170, -1, -1));

        settings_qty_edit_desctiption_lbl.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        settings_sub_edit_panel.add(settings_qty_edit_desctiption_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 170, 530, 30));

        jLabel85.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel85.setText("Available Qty :");
        settings_sub_edit_panel.add(jLabel85, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 230, -1, -1));

        settings_qty_edit_qty_txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        settings_qty_edit_qty_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                settings_qty_edit_qty_txtActionPerformed(evt);
            }
        });
        settings_sub_edit_panel.add(settings_qty_edit_qty_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 290, 140, -1));

        jButton21.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton21.setText("Save");
        jButton21.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton21ActionPerformed(evt);
            }
        });
        settings_sub_edit_panel.add(jButton21, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 340, 210, 70));

        settings_sub_panel.add(settings_sub_edit_panel, "card2");

        jLabel80.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel80.setText("Change Item Number to");

        change_item_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel87.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel87.setText("Previous Item Number");

        jButton22.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton22.setText("Save");
        jButton22.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton22ActionPerformed(evt);
            }
        });

        changed_item_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        javax.swing.GroupLayout part_number_change_panelLayout = new javax.swing.GroupLayout(part_number_change_panel);
        part_number_change_panel.setLayout(part_number_change_panelLayout);
        part_number_change_panelLayout.setHorizontalGroup(
            part_number_change_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, part_number_change_panelLayout.createSequentialGroup()
                .addContainerGap(497, Short.MAX_VALUE)
                .addComponent(jButton22, javax.swing.GroupLayout.PREFERRED_SIZE, 215, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(46, 46, 46))
            .addGroup(part_number_change_panelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(part_number_change_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel87)
                    .addComponent(jLabel80)
                    .addComponent(change_item_combo, 0, 336, Short.MAX_VALUE)
                    .addComponent(changed_item_txt))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        part_number_change_panelLayout.setVerticalGroup(
            part_number_change_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(part_number_change_panelLayout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addComponent(jLabel87)
                .addGap(18, 18, 18)
                .addComponent(change_item_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addComponent(jLabel80)
                .addGap(18, 18, 18)
                .addComponent(changed_item_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 53, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(60, 60, 60)
                .addComponent(jButton22, javax.swing.GroupLayout.DEFAULT_SIZE, 106, Short.MAX_VALUE)
                .addGap(101, 101, 101))
        );

        settings_sub_panel.add(part_number_change_panel, "card3");

        jButton23.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton23.setText("Change Number");
        jButton23.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton23ActionPerformed(evt);
            }
        });

        jButton24.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton24.setText("Clutch Plate Find");
        jButton24.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton24ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout settings_panelLayout = new javax.swing.GroupLayout(settings_panel);
        settings_panel.setLayout(settings_panelLayout);
        settings_panelLayout.setHorizontalGroup(
            settings_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settings_panelLayout.createSequentialGroup()
                .addGroup(settings_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(settings_panelLayout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jButton20, javax.swing.GroupLayout.PREFERRED_SIZE, 154, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(36, 36, 36)
                        .addComponent(jButton23)
                        .addGap(30, 30, 30)
                        .addComponent(jButton24, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(settings_panelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(settings_sub_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 758, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(46, Short.MAX_VALUE))
        );
        settings_panelLayout.setVerticalGroup(
            settings_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(settings_panelLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(settings_panelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jButton23, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE)
                    .addComponent(jButton24, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jButton20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 239, Short.MAX_VALUE)
                .addComponent(settings_sub_panel, javax.swing.GroupLayout.PREFERRED_SIZE, 508, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        MainChangeFrame.add(settings_panel, "card11");

        stock_count_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        stock_update_btn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        stock_update_btn.setText("ADD");
        stock_update_btn.setName(""); // NOI18N
        stock_update_btn.setNextFocusableComponent(stock_item_combo);
        stock_update_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stock_update_btnActionPerformed(evt);
            }
        });
        stock_update_btn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                stock_update_btnKeyPressed(evt);
            }
        });
        stock_count_panel.add(stock_update_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 70, 130, 50));

        stock_item_combo.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        stock_item_combo.setNextFocusableComponent(stock_qty_txt);
        stock_item_combo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                stock_item_comboFocusGained(evt);
            }
        });
        stock_item_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                stock_item_comboActionPerformed(evt);
            }
        });
        stock_count_panel.add(stock_item_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 260, 50));

        stock_selling_lbl.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        stock_selling_lbl.setText("0.00");
        stock_count_panel.add(stock_selling_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 180, -1, -1));

        jLabel68.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel68.setText("Item Code");
        stock_count_panel.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        jLabel69.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel69.setText("Qty");
        stock_count_panel.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 30, -1, -1));

        stock_qty_txt.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        stock_qty_txt.setNextFocusableComponent(stock_selling_txt);
        stock_qty_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                stock_qty_txtFocusGained(evt);
            }
        });
        stock_qty_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                stock_qty_txtKeyPressed(evt);
            }
        });
        stock_count_panel.add(stock_qty_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(320, 70, 100, 50));

        stock_selling_txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        stock_selling_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                stock_selling_txtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                stock_selling_txtFocusLost(evt);
            }
        });
        stock_selling_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                stock_selling_txtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                stock_selling_txtKeyTyped(evt);
            }
        });
        stock_count_panel.add(stock_selling_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 70, 170, 50));

        jLabel70.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel70.setText("Selling ");
        stock_count_panel.add(jLabel70, new org.netbeans.lib.awtextra.AbsoluteConstraints(440, 30, -1, -1));

        jLabel71.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel71.setText("New Selling");
        stock_count_panel.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 180, -1, -1));

        stock_description_lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        stock_count_panel.add(stock_description_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 230, 510, 40));

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setText("Description :");
        stock_count_panel.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 230, -1, -1));

        stock_availableQty_lbl.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        stock_availableQty_lbl.setText("0");
        stock_count_panel.add(stock_availableQty_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 140, 60, 30));

        stock_c_selling_lbl.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        stock_c_selling_lbl.setText("0.00");
        stock_count_panel.add(stock_c_selling_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 140, -1, -1));

        stock_item_table.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        stock_item_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Number", "qty", "Cost", "Selling", "Time"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(stock_item_table);

        stock_count_panel.add(jScrollPane3, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 290, 740, 270));

        jButton19.setText("Qty Only Update");
        jButton19.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton19ActionPerformed(evt);
            }
        });
        stock_count_panel.add(jButton19, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 120, 50));

        jLabel81.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel81.setText("Current stock-");
        stock_count_panel.add(jLabel81, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 140, -1, -1));

        jLabel86.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel86.setText("Current Selling-");
        stock_count_panel.add(jLabel86, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 140, -1, -1));

        MainChangeFrame.add(stock_count_panel, "card10");

        AddUserPannel.setBackground(new java.awt.Color(255, 255, 255));
        AddUserPannel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel3.setFont(new java.awt.Font("Slimaniabold", 1, 16)); // NOI18N
        jLabel3.setText("Name :");
        AddUserPannel.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 181, 40));

        NameTxt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        NameTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                NameTxtFocusGained(evt);
            }
        });
        NameTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NameTxtActionPerformed(evt);
            }
        });
        NameTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                NameTxtKeyPressed(evt);
            }
        });
        AddUserPannel.add(NameTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 30, 320, 30));

        jLabel4.setFont(new java.awt.Font("Slimaniabold", 1, 16)); // NOI18N
        jLabel4.setText("Address 1 : ");
        AddUserPannel.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 181, 56));

        AddressTxt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        AddressTxt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AddressTxtFocusGained(evt);
            }
        });
        AddressTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddressTxtKeyPressed(evt);
            }
        });
        AddUserPannel.add(AddressTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 80, 320, 30));

        jLabel5.setFont(new java.awt.Font("Slimaniabold", 1, 16)); // NOI18N
        jLabel5.setText("Telephone No : ");
        AddUserPannel.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 130, 30));

        TelephoneTxt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        TelephoneTxt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                TelephoneTxtKeyPressed(evt);
            }
        });
        AddUserPannel.add(TelephoneTxt, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 200, 320, 30));

        cancelBtn.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        cancelBtn.setText("Cancel");
        cancelBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBtnActionPerformed(evt);
            }
        });
        AddUserPannel.add(cancelBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(190, 280, 142, 81));

        UserPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        AddUserPannel.add(UserPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(870, 70, -1, -1));

        saveBtn.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        saveBtn.setText("Save");
        saveBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveBtnActionPerformed(evt);
            }
        });
        saveBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                saveBtnKeyPressed(evt);
            }
        });
        AddUserPannel.add(saveBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 280, 134, 81));

        jLabel67.setFont(new java.awt.Font("Slimaniabold", 1, 16)); // NOI18N
        jLabel67.setText("Address 2 : ");
        AddUserPannel.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, 100, 30));

        AddressTxt1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        AddressTxt1.setNextFocusableComponent(AddressTxt1);
        AddressTxt1.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                AddressTxt1FocusGained(evt);
            }
        });
        AddressTxt1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                AddressTxt1KeyPressed(evt);
            }
        });
        AddUserPannel.add(AddressTxt1, new org.netbeans.lib.awtextra.AbsoluteConstraints(170, 140, 320, 30));

        newBtn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        newBtn.setText("New");
        newBtn.setAlignmentY(0.0F);
        newBtn.setBorder(null);
        newBtn.setNextFocusableComponent(NameTxt);
        newBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                newBtnActionPerformed(evt);
            }
        });
        newBtn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                newBtnKeyPressed(evt);
            }
        });
        AddUserPannel.add(newBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 30, 80, 50));

        editBtn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        editBtn.setText("Edit");
        editBtn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editBtnActionPerformed(evt);
            }
        });
        AddUserPannel.add(editBtn, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 80, 80, 50));

        jButton25.setText("Search");
        jButton25.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton25ActionPerformed(evt);
            }
        });
        AddUserPannel.add(jButton25, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 190, 80, 60));

        MainChangeFrame.add(AddUserPannel, "card5");

        SalesReturnPanel.setBackground(new java.awt.Color(255, 255, 255));
        SalesReturnPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel45.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel45.setText("User Name");
        SalesReturnPanel.add(jLabel45, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 30, -1, -1));

        return_userName_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        return_userName_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        return_userName_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_userName_comboActionPerformed(evt);
            }
        });
        SalesReturnPanel.add(return_userName_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(470, 70, 300, 40));

        return_total_txt.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        return_total_txt.setText("0");
        SalesReturnPanel.add(return_total_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 550, -1, -1));

        return_reason_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        return_reason_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Not Suitable", "Damaged ", "Damaged Replacing" }));
        SalesReturnPanel.add(return_reason_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 470, 190, 40));

        jLabel47.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel47.setText("User ID");
        SalesReturnPanel.add(jLabel47, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 30, -1, -1));

        return_userID_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        return_userID_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        return_userID_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_userID_comboActionPerformed(evt);
            }
        });
        return_userID_combo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                return_userID_comboKeyPressed(evt);
            }
        });
        SalesReturnPanel.add(return_userID_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(370, 70, 80, 40));

        jLabel49.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel49.setText("Returned Cash");
        SalesReturnPanel.add(jLabel49, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 470, -1, -1));

        selectAll_button.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        selectAll_button.setText("Select All");
        selectAll_button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                selectAll_buttonActionPerformed(evt);
            }
        });
        SalesReturnPanel.add(selectAll_button, new org.netbeans.lib.awtextra.AbsoluteConstraints(530, 470, 90, 40));

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel50.setText("Total Return Amount :");
        SalesReturnPanel.add(jLabel50, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 550, -1, -1));

        jLabel51.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel51.setText("Reason:");
        SalesReturnPanel.add(jLabel51, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 480, -1, -1));

        return_no.setBackground(new java.awt.Color(255, 255, 255));
        return_no.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        return_no.setText("No");
        return_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_noActionPerformed(evt);
            }
        });
        SalesReturnPanel.add(return_no, new org.netbeans.lib.awtextra.AbsoluteConstraints(720, 500, -1, -1));

        return_yes.setBackground(new java.awt.Color(255, 255, 255));
        return_yes.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        return_yes.setText("Yes");
        return_yes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_yesActionPerformed(evt);
            }
        });
        SalesReturnPanel.add(return_yes, new org.netbeans.lib.awtextra.AbsoluteConstraints(640, 500, -1, -1));

        return_calculation_btn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        return_calculation_btn.setText("Calculate");
        return_calculation_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_calculation_btnActionPerformed(evt);
            }
        });
        SalesReturnPanel.add(return_calculation_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 470, 190, 40));

        return_cancel_btn.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        return_cancel_btn.setText("Cancel");
        return_cancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_cancel_btnActionPerformed(evt);
            }
        });
        SalesReturnPanel.add(return_cancel_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(650, 130, 120, 50));

        jButton4.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jButton4.setText("Select Invoice");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });
        SalesReturnPanel.add(jButton4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 130, 210, 50));

        sales_return_subPanel.setLayout(new java.awt.CardLayout());

        return_item_table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        return_item_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Item Number", "UnitPrice", "TotalPrice", "Returning", "Purchaised", "Selection"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Boolean.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        returns_item_table.setViewportView(return_item_table);

        sales_return_subPanel.add(returns_item_table, "card2");

        returns_search_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        return_search_item_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        return_search_item_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        return_search_item_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_search_item_comboActionPerformed(evt);
            }
        });
        returns_search_panel.add(return_search_item_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 190, 40));

        jLabel48.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel48.setText("From");
        returns_search_panel.add(jLabel48, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 20, -1, -1));

        return_from_picker.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        returns_search_panel.add(return_from_picker, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 10, 170, 40));

        jLabel53.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel53.setText("To");
        returns_search_panel.add(jLabel53, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        return_to_picker.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        returns_search_panel.add(return_to_picker, new org.netbeans.lib.awtextra.AbsoluteConstraints(570, 10, 170, 40));

        return_invoiceSearch_table.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        return_invoiceSearch_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Invoice No", "Purchaised Date", "Purchaised Qty", "Total", "Returnable Qty"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, true, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(return_invoiceSearch_table);

        returns_search_panel.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 750, 170));

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel52.setText("Item No");
        returns_search_panel.add(jLabel52, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 20, -1, -1));

        sales_return_subPanel.add(returns_search_panel, "card3");

        SalesReturnPanel.add(sales_return_subPanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 200, 760, 250));

        return_invoiceID_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        return_invoiceID_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { " " }));
        return_invoiceID_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_invoiceID_comboActionPerformed(evt);
            }
        });
        SalesReturnPanel.add(return_invoiceID_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 70, 160, 40));

        return_search_invoice.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        return_search_invoice.setText("Search Invoice");
        return_search_invoice.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_search_invoiceActionPerformed(evt);
            }
        });
        SalesReturnPanel.add(return_search_invoice, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 130, 210, 50));

        jLabel54.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel54.setText("Invoice No");
        SalesReturnPanel.add(jLabel54, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 30, -1, -1));

        return_button1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        return_button1.setText("Return");
        return_button1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                return_button1ActionPerformed(evt);
            }
        });
        SalesReturnPanel.add(return_button1, new org.netbeans.lib.awtextra.AbsoluteConstraints(600, 530, 170, 60));

        MainChangeFrame.add(SalesReturnPanel, "card9");

        ReportPanel.setBackground(new java.awt.Color(255, 255, 255));
        ReportPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        reports_dayEnd_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        reports_dayEnd_btn.setText("Day End Report");
        reports_dayEnd_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reports_dayEnd_btnActionPerformed(evt);
            }
        });
        ReportPanel.add(reports_dayEnd_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(577, 70, 160, 70));

        report_items_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        report_items_btn.setText("Item Rport");
        report_items_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                report_items_btnActionPerformed(evt);
            }
        });
        ReportPanel.add(report_items_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 110, 70));

        jLabel6.setFont(new java.awt.Font("Verdana", 1, 24)); // NOI18N
        jLabel6.setText("Total Reports");
        ReportPanel.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 20, 180, 30));

        reports_customer_btn1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        reports_customer_btn1.setText("Customer Rport");
        reports_customer_btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reports_customer_btn1ActionPerformed(evt);
            }
        });
        ReportPanel.add(reports_customer_btn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(160, 70, 150, 70));

        reports_customer_btn2.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        reports_customer_btn2.setText("Invoice Summary");
        reports_customer_btn2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                reports_customer_btn2ActionPerformed(evt);
            }
        });
        ReportPanel.add(reports_customer_btn2, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 70, 180, 70));

        jTabbedPane1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jTabbedPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTabbedPane1MouseClicked(evt);
            }
        });

        userWisePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jButton6.setText("Last Bill");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });
        userWisePanel.add(jButton6, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, 160, 35));

        jButton7.setText("All Selling");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });
        userWisePanel.add(jButton7, new org.netbeans.lib.awtextra.AbsoluteConstraints(250, 110, 160, 35));

        jButton8.setText("user payments");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });
        userWisePanel.add(jButton8, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 110, 160, 35));

        jLabel29.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel29.setText("UserID :");
        userWisePanel.add(jLabel29, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 50, -1, -1));

        report_name_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        report_name_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                report_name_comboActionPerformed(evt);
            }
        });
        userWisePanel.add(report_name_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(360, 50, 299, 36));

        report_userID_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        report_userID_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                report_userID_comboActionPerformed(evt);
            }
        });
        userWisePanel.add(report_userID_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(150, 50, 75, 36));

        jLabel32.setFont(new java.awt.Font("Tahoma", 1, 20)); // NOI18N
        jLabel32.setText("Select user");
        userWisePanel.add(jLabel32, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 10, -1, -1));

        jLabel43.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel43.setText("All Users Reports");
        userWisePanel.add(jLabel43, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 180, -1, -1));

        jLabel44.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel44.setText("Name :");
        userWisePanel.add(jLabel44, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 50, -1, -1));

        jButton13.setText("All Unpaid");
        jButton13.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton13ActionPerformed(evt);
            }
        });
        userWisePanel.add(jButton13, new org.netbeans.lib.awtextra.AbsoluteConstraints(480, 220, 160, 30));

        jButton30.setText("All Part Paymanets");
        jButton30.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton30ActionPerformed(evt);
            }
        });
        userWisePanel.add(jButton30, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 220, 160, 30));

        jButton31.setText("Summary");
        jButton31.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton31ActionPerformed(evt);
            }
        });
        userWisePanel.add(jButton31, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 220, 160, 30));

        jTabbedPane1.addTab("User Wise", userWisePanel);

        itemWisePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        report_item_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        itemWisePanel.add(report_item_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 370, 50));

        jButton9.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton9.setText("Empty Stock");
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });
        itemWisePanel.add(jButton9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 190, 160, 35));

        jButton10.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton10.setText("Purchaising History");
        jButton10.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton10ActionPerformed(evt);
            }
        });
        itemWisePanel.add(jButton10, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 100, -1, 35));

        jButton11.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton11.setText("Selling History");
        jButton11.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton11ActionPerformed(evt);
            }
        });
        itemWisePanel.add(jButton11, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 160, 40));

        jLabel41.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel41.setText("Complete Item Report Reports");
        itemWisePanel.add(jLabel41, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, -1, -1));

        jLabel42.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel42.setText("Part Number");
        itemWisePanel.add(jLabel42, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jButton12.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton12.setText("Sales Returns");
        jButton12.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton12ActionPerformed(evt);
            }
        });
        itemWisePanel.add(jButton12, new org.netbeans.lib.awtextra.AbsoluteConstraints(400, 100, 160, 35));

        jTabbedPane1.addTab("Item Wise", itemWisePanel);

        purchasingPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        report_purchasing_invoice_no.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        report_purchasing_invoice_no.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                report_purchasing_invoice_noActionPerformed(evt);
            }
        });
        purchasingPanel.add(report_purchasing_invoice_no, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 20, 370, 50));

        jButton32.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton32.setText("View Invoice");
        jButton32.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton32ActionPerformed(evt);
            }
        });
        purchasingPanel.add(jButton32, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, 180, 50));

        jLabel60.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel60.setText("Invoice Number");
        purchasingPanel.add(jLabel60, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 30, -1, -1));

        jTabbedPane1.addTab("Purchasing Panel", purchasingPanel);

        ReportPanel.add(jTabbedPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 730, 340));

        item_report_checkBox.setBackground(new java.awt.Color(255, 255, 255));
        item_report_checkBox.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        item_report_checkBox.setText("Date Range");
        item_report_checkBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_report_checkBoxActionPerformed(evt);
            }
        });
        ReportPanel.add(item_report_checkBox, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 180, -1, -1));

        reports_date1_picker.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        ReportPanel.add(reports_date1_picker, new org.netbeans.lib.awtextra.AbsoluteConstraints(280, 170, 180, 50));

        reports_date2_picker.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        ReportPanel.add(reports_date2_picker, new org.netbeans.lib.awtextra.AbsoluteConstraints(560, 170, 180, 50));

        item_date1_label.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        item_date1_label.setText("Start : ");
        ReportPanel.add(item_date1_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 190, -1, -1));

        item_date2_label.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        item_date2_label.setText("End :");
        ReportPanel.add(item_date2_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(490, 170, -1, 40));

        MainChangeFrame.add(ReportPanel, "card3");

        SettingsPanel.setBackground(new java.awt.Color(255, 102, 255));

        javax.swing.GroupLayout SettingsPanelLayout = new javax.swing.GroupLayout(SettingsPanel);
        SettingsPanel.setLayout(SettingsPanelLayout);
        SettingsPanelLayout.setHorizontalGroup(
            SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 814, Short.MAX_VALUE)
        );
        SettingsPanelLayout.setVerticalGroup(
            SettingsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 850, Short.MAX_VALUE)
        );

        MainChangeFrame.add(SettingsPanel, "card6");

        AddItemPanel.setBackground(new java.awt.Color(255, 255, 255));
        AddItemPanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel7.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel7.setText("Item No : ");
        AddItemPanel.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 40, 181, 30));

        add_itemNo_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        add_itemNo_txt.setNextFocusableComponent(add_item_category_combo);
        add_itemNo_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                add_itemNo_txtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                add_itemNo_txtFocusLost(evt);
            }
        });
        add_itemNo_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_itemNo_txtActionPerformed(evt);
            }
        });
        add_itemNo_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                add_itemNo_txtKeyPressed(evt);
            }
        });
        AddItemPanel.add(add_itemNo_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(231, 37, 380, 40));

        jLabel8.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel8.setText("Category :");
        AddItemPanel.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 90, 181, 40));

        add_item_vehicle_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        add_item_vehicle_txt.setNextFocusableComponent(add_item_brand_txt);
        add_item_vehicle_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                add_item_vehicle_txtFocusLost(evt);
            }
        });
        add_item_vehicle_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_item_vehicle_txtActionPerformed(evt);
            }
        });
        add_item_vehicle_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                add_item_vehicle_txtKeyPressed(evt);
            }
        });
        AddItemPanel.add(add_item_vehicle_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 140, 380, 40));

        jLabel9.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel9.setText("Description");
        AddItemPanel.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 240, 181, 40));

        add_item_desc_txt.setEditable(false);
        add_item_desc_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        add_item_desc_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_item_desc_txtActionPerformed(evt);
            }
        });
        add_item_desc_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                add_item_desc_txtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                add_item_desc_txtKeyTyped(evt);
            }
        });
        AddItemPanel.add(add_item_desc_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 240, 380, 40));

        add_item_cancel_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        add_item_cancel_btn.setText("Cancel");
        add_item_cancel_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_item_cancel_btnActionPerformed(evt);
            }
        });
        AddItemPanel.add(add_item_cancel_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(520, 490, 90, 60));

        jLabel11.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel11.setText("Unit :");
        AddItemPanel.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 410, 181, 30));

        UserPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        item_add_edit_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        item_add_edit_btn.setText("Edit");
        item_add_edit_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_add_edit_btnActionPerformed(evt);
            }
        });
        UserPanel1.add(item_add_edit_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 96, 120, 84));

        item_add_new_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        item_add_new_btn.setText("New");
        item_add_new_btn.setAlignmentY(0.0F);
        item_add_new_btn.setBorder(null);
        item_add_new_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                item_add_new_btnMouseClicked(evt);
            }
        });
        item_add_new_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_add_new_btnActionPerformed(evt);
            }
        });
        item_add_new_btn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                item_add_new_btnKeyPressed(evt);
            }
        });
        UserPanel1.add(item_add_new_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 120, 89));

        AddItemPanel.add(UserPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(630, 130, -1, 180));

        add_item_save_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        add_item_save_btn.setText("Save");
        add_item_save_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                add_item_save_btnMouseClicked(evt);
            }
        });
        add_item_save_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_item_save_btnActionPerformed(evt);
            }
        });
        add_item_save_btn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                add_item_save_btnKeyPressed(evt);
            }
        });
        AddItemPanel.add(add_item_save_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 490, 90, 60));

        jLabel57.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel57.setText("Vehicle");
        AddItemPanel.add(jLabel57, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 150, 181, 30));

        add_item_category_combo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        add_item_category_combo.setNextFocusableComponent(add_item_vehicle_txt);
        add_item_category_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                add_item_category_comboActionPerformed(evt);
            }
        });
        AddItemPanel.add(add_item_category_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 90, 380, 40));

        jLabel58.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel58.setText("Brand");
        AddItemPanel.add(jLabel58, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 200, 181, 30));

        add_item_brand_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        add_item_brand_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                add_item_brand_txtFocusLost(evt);
            }
        });
        add_item_brand_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                add_item_brand_txtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                add_item_brand_txtKeyTyped(evt);
            }
        });
        AddItemPanel.add(add_item_brand_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 190, 380, 40));

        jLabel63.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel63.setText("Location");
        AddItemPanel.add(jLabel63, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 350, 181, 30));

        add_item_location_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        add_item_location_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                add_item_location_txtKeyPressed(evt);
            }
        });
        AddItemPanel.add(add_item_location_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 350, 120, 40));

        add_item_unit_combo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        add_item_unit_combo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "Set", "Feet", "Meter" }));
        add_item_unit_combo.setNextFocusableComponent(add_item_save_btn);
        add_item_unit_combo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                add_item_unit_comboKeyPressed(evt);
            }
        });
        AddItemPanel.add(add_item_unit_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 410, 120, -1));

        MainChangeFrame.add(AddItemPanel, "card5");

        Purchaising_base_panel.setBackground(new java.awt.Color(255, 255, 255));
        Purchaising_base_panel.setLayout(new java.awt.CardLayout());

        purchaising_main_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel12.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel12.setText("Item No : ");
        purchaising_main_panel.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 180, 181, 40));

        jLabel25.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel25.setText("Description :");
        purchaising_main_panel.add(jLabel25, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 181, 40));

        update_description_txt.setEditable(false);
        update_description_txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        update_description_txt.setFocusable(false);
        update_description_txt.setNextFocusableComponent(update_billPrice_txt);
        update_description_txt.setRequestFocusEnabled(false);
        update_description_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                update_description_txtFocusGained(evt);
            }
        });
        update_description_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                update_description_txtKeyPressed(evt);
            }
        });
        purchaising_main_panel.add(update_description_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 240, 450, 40));

        jLabel26.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel26.setText("Bill Price");
        purchaising_main_panel.add(jLabel26, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 300, 181, 40));

        update_billPrice_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        update_billPrice_txt.setNextFocusableComponent(update_costP_txt);
        update_billPrice_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                update_billPrice_txtFocusGained(evt);
            }
        });
        update_billPrice_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                update_billPrice_txtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                update_billPrice_txtKeyTyped(evt);
            }
        });
        purchaising_main_panel.add(update_billPrice_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 300, 130, 40));

        jLabel27.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel27.setText("Cost");
        purchaising_main_panel.add(jLabel27, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 360, 181, 40));

        update_costP_txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        update_costP_txt.setText("0");
        update_costP_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                update_costP_txtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                update_costP_txtFocusLost(evt);
            }
        });
        update_costP_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_costP_txtActionPerformed(evt);
            }
        });
        update_costP_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                update_costP_txtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                update_costP_txtKeyTyped(evt);
            }
        });
        purchaising_main_panel.add(update_costP_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 360, 60, 40));

        itemCancelBtn1.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        itemCancelBtn1.setText("Cancel");
        itemCancelBtn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                itemCancelBtn1ActionPerformed(evt);
            }
        });
        purchaising_main_panel.add(itemCancelBtn1, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 520, 142, 60));

        jLabel28.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel28.setText("Selling :");
        purchaising_main_panel.add(jLabel28, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 410, 181, 40));

        UserPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());
        purchaising_main_panel.add(UserPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(850, 140, -1, -1));

        update_save_btn.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        update_save_btn.setText("Save");
        update_save_btn.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                update_save_btnMouseClicked(evt);
            }
        });
        update_save_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_save_btnActionPerformed(evt);
            }
        });
        update_save_btn.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                update_save_btnKeyPressed(evt);
            }
        });
        purchaising_main_panel.add(update_save_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 520, 134, 60));

        update_qty_txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        update_qty_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                update_qty_txtFocusGained(evt);
            }
        });
        update_qty_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                update_qty_txtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                update_qty_txtKeyTyped(evt);
            }
        });
        purchaising_main_panel.add(update_qty_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 460, 119, 40));

        update_available_lbl.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        purchaising_main_panel.add(update_available_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 460, 110, 40));

        update_itemNo_combo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        update_itemNo_combo.setNextFocusableComponent(update_billPrice_txt);
        update_itemNo_combo.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                update_itemNo_comboFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                update_itemNo_comboFocusLost(evt);
            }
        });
        update_itemNo_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_itemNo_comboActionPerformed(evt);
            }
        });
        purchaising_main_panel.add(update_itemNo_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 180, 450, 40));

        jLabel30.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel30.setText("Update Qty BY  : ");
        purchaising_main_panel.add(jLabel30, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 250, 40));

        jLabel31.setFont(new java.awt.Font("Slimaniabold", 1, 28)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(51, 51, 51));
        jLabel31.setText("Available : ");
        purchaising_main_panel.add(jLabel31, new org.netbeans.lib.awtextra.AbsoluteConstraints(410, 460, 110, 40));

        jLabel72.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel72.setText("%");
        purchaising_main_panel.add(jLabel72, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 360, -1, 40));

        update_cost_lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        purchaising_main_panel.add(update_cost_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 360, 140, 40));

        update_sellingP_txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        update_sellingP_txt.setText("0");
        update_sellingP_txt.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                update_sellingP_txtFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                update_sellingP_txtFocusLost(evt);
            }
        });
        update_sellingP_txt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                update_sellingP_txtActionPerformed(evt);
            }
        });
        update_sellingP_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                update_sellingP_txtKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                update_sellingP_txtKeyTyped(evt);
            }
        });
        purchaising_main_panel.add(update_sellingP_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(270, 410, 60, 40));

        jLabel74.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        jLabel74.setText("%");
        purchaising_main_panel.add(jLabel74, new org.netbeans.lib.awtextra.AbsoluteConstraints(340, 410, -1, 40));

        update_selling_lbl.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        purchaising_main_panel.add(update_selling_lbl, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 410, 140, 40));

        jLabel73.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel73.setText("Invoice No");
        purchaising_main_panel.add(jLabel73, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 30, -1, -1));

        purchaise_retailer_combo.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        purchaising_main_panel.add(purchaise_retailer_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 60, 250, 40));

        jLabel75.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel75.setText("Retailer");
        purchaising_main_panel.add(jLabel75, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 30, -1, -1));

        purchaise_invoiceno_txt.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        purchaise_invoiceno_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                purchaise_invoiceno_txtKeyPressed(evt);
            }
        });
        purchaising_main_panel.add(purchaise_invoiceno_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 60, 160, 40));

        jButton2.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton2.setText("Add Retailer");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        purchaising_main_panel.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 50, 150, 50));

        Purchaising_base_panel.add(purchaising_main_panel, "card2");

        retailer_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel76.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel76.setText("Name");
        retailer_panel.add(jLabel76, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 60, 70, 30));

        retailer_name_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                retailer_name_txtKeyPressed(evt);
            }
        });
        retailer_panel.add(retailer_name_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 60, 600, 40));

        jLabel77.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel77.setText("Address");
        retailer_panel.add(jLabel77, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 140, 90, 30));

        retailer_address_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                retailer_address_txtKeyPressed(evt);
            }
        });
        retailer_panel.add(retailer_address_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 140, 600, 40));

        jLabel78.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel78.setText("Contact");
        retailer_panel.add(jLabel78, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 230, 90, 30));
        retailer_panel.add(retailer_contact_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(140, 230, 600, 40));

        jButton15.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton15.setText("Cancel");
        jButton15.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton15ActionPerformed(evt);
            }
        });
        retailer_panel.add(jButton15, new org.netbeans.lib.awtextra.AbsoluteConstraints(580, 370, 140, 60));

        jButton16.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton16.setText("Save");
        jButton16.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton16ActionPerformed(evt);
            }
        });
        retailer_panel.add(jButton16, new org.netbeans.lib.awtextra.AbsoluteConstraints(420, 370, 140, 60));

        jButton17.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jButton17.setText("Back");
        jButton17.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton17ActionPerformed(evt);
            }
        });
        retailer_panel.add(jButton17, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 370, 110, 60));

        Purchaising_base_panel.add(retailer_panel, "card3");

        MainChangeFrame.add(Purchaising_base_panel, "card5");

        bill_pay_panel.setBackground(new java.awt.Color(255, 255, 255));
        bill_pay_panel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel33.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel33.setText("User Name:");
        bill_pay_panel.add(jLabel33, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 40, -1, 50));

        bill_name_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        bill_name_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bill_name_comboActionPerformed(evt);
            }
        });
        bill_pay_panel.add(bill_name_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(430, 50, 220, 50));

        bill_InvoiceID_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        bill_pay_panel.add(bill_InvoiceID_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 110, 151, 50));

        bill_userID_combo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        bill_userID_combo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bill_userID_comboActionPerformed(evt);
            }
        });
        bill_pay_panel.add(bill_userID_combo, new org.netbeans.lib.awtextra.AbsoluteConstraints(130, 40, 151, 50));

        bill_paymentPane.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N

        jLabel35.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel35.setText("Pay Date : ");

        cashPay.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        cashPay.setText("Pay");
        cashPay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cashPayActionPerformed(evt);
            }
        });

        bill_currentDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bill_currentDate.setText("Current Date");
        bill_currentDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bill_currentDateActionPerformed(evt);
            }
        });

        bill_chooseDate.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bill_chooseDate.setText("Choose Date");
        bill_chooseDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bill_chooseDateActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(39, 39, 39)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addComponent(bill_currentDate)
                    .addComponent(bill_cash_datePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 193, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(bill_chooseDate))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(cashPay, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel35, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bill_currentDate)
                .addGap(4, 4, 4)
                .addComponent(bill_chooseDate)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bill_cash_datePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(cashPay, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(101, Short.MAX_VALUE))
        );

        bill_paymentPane.addTab("CASH", jPanel4);

        bill_bank_comnbo.setEditable(true);
        bill_bank_comnbo.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        bill_bank_comnbo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sampath Bank", "Bank Of Ceylon", "Commercial Bank", "Seylan Bank", "DFCC", "HNB", "Union Bank", " " }));

        bill_chequeNo_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N

        jLabel34.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel34.setText("Cheque Date  ");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel40.setText("Cheque Number ");

        chequePay.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        chequePay.setText("Pay");
        chequePay.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                chequePayActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout bill_cheque_numberLayout = new javax.swing.GroupLayout(bill_cheque_number);
        bill_cheque_number.setLayout(bill_cheque_numberLayout);
        bill_cheque_numberLayout.setHorizontalGroup(
            bill_cheque_numberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bill_cheque_numberLayout.createSequentialGroup()
                .addGroup(bill_cheque_numberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(bill_cheque_numberLayout.createSequentialGroup()
                        .addGroup(bill_cheque_numberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(bill_cheque_numberLayout.createSequentialGroup()
                                .addGap(49, 49, 49)
                                .addComponent(jLabel40))
                            .addGroup(bill_cheque_numberLayout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(bill_chequeDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 224, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(bill_cheque_numberLayout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(chequePay, javax.swing.GroupLayout.PREFERRED_SIZE, 174, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bill_cheque_numberLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(bill_cheque_numberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(bill_chequeNo_txt)
                            .addComponent(bill_bank_comnbo, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, bill_cheque_numberLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel34)
                .addGap(57, 57, 57))
        );
        bill_cheque_numberLayout.setVerticalGroup(
            bill_cheque_numberLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(bill_cheque_numberLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(bill_bank_comnbo, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel40, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bill_chequeNo_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(1, 1, 1)
                .addComponent(jLabel34, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(bill_chequeDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(chequePay, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(33, Short.MAX_VALUE))
        );

        bill_paymentPane.addTab("CHEQUE", bill_cheque_number);

        bill_pay_panel.add(bill_paymentPane, new org.netbeans.lib.awtextra.AbsoluteConstraints(500, 170, 250, 400));

        Bill_table.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        Bill_table.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Bill Number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        Bill_table.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Bill_tableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(Bill_table);
        if (Bill_table.getColumnModel().getColumnCount() > 0) {
            Bill_table.getColumnModel().getColumn(0).setResizable(false);
        }

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 361, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(289, 289, 289))
        );

        bill_pay_panel.add(jPanel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 190, 180, 380));

        jButton1.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        jButton1.setText("Select User");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        bill_pay_panel.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(660, 50, 110, 50));

        bill_pay_select_btn.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        bill_pay_select_btn.setText("Select Bill");
        bill_pay_select_btn.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bill_pay_select_btnActionPerformed(evt);
            }
        });
        bill_pay_panel.add(bill_pay_select_btn, new org.netbeans.lib.awtextra.AbsoluteConstraints(310, 110, 120, 50));

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel36.setText("Bill ID :");
        bill_pay_panel.add(jLabel36, new org.netbeans.lib.awtextra.AbsoluteConstraints(40, 110, -1, 50));

        Bill_date_txt.setEditable(false);
        Bill_date_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        bill_pay_panel.add(Bill_date_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 420, 270, 40));

        jLabel37.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel37.setText("Invoice Value:");
        bill_pay_panel.add(jLabel37, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 190, -1, 50));

        bill_invoiceValue_txt.setEditable(false);
        bill_invoiceValue_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        bill_pay_panel.add(bill_invoiceValue_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 230, 180, 40));

        jLabel38.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel38.setText("User ID : ");
        bill_pay_panel.add(jLabel38, new org.netbeans.lib.awtextra.AbsoluteConstraints(37, 38, -1, 50));

        jLabel39.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel39.setText("Returns : ");
        bill_pay_panel.add(jLabel39, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 270, -1, 50));

        jLabel64.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel64.setText("Paying = ");
        bill_pay_panel.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 470, -1, 50));

        bill_paying_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        bill_paying_txt.setForeground(new java.awt.Color(255, 51, 51));
        bill_pay_panel.add(bill_paying_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 470, 140, 40));

        jLabel65.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel65.setText("Invoice Date: ");
        bill_pay_panel.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 370, -1, 50));

        bill_payable_txt.setEditable(false);
        bill_payable_txt.setFont(new java.awt.Font("Tahoma", 0, 16)); // NOI18N
        bill_pay_panel.add(bill_payable_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 330, 140, 40));

        jLabel66.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel66.setText("Should Pay");
        bill_pay_panel.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 320, -1, 50));

        bill_return_label.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        bill_return_label.setText("0");
        bill_pay_panel.add(bill_return_label, new org.netbeans.lib.awtextra.AbsoluteConstraints(330, 280, 130, 30));

        MainChangeFrame.add(bill_pay_panel, "card8");

        ItemSearchPanel.setForeground(new java.awt.Color(255, 255, 255));

        item_search_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                item_search_txtKeyPressed(evt);
            }
        });

        jButton26.setBackground(new java.awt.Color(255, 255, 255));
        jButton26.setText("Go Back");
        jButton26.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton26ActionPerformed(evt);
            }
        });

        itemSearchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ITEM CODE", "CATEGORY", "VEHICLE", "BRAND", "QTY"
            }
        ));
        jScrollPane7.setViewportView(itemSearchTable);
        if (itemSearchTable.getColumnModel().getColumnCount() > 0) {
            itemSearchTable.getColumnModel().getColumn(3).setHeaderValue("Contact Number");
            itemSearchTable.getColumnModel().getColumn(4).setHeaderValue("QTY");
        }

        jButton27.setBackground(new java.awt.Color(255, 255, 255));
        jButton27.setText("Search");
        jButton27.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton27ActionPerformed(evt);
            }
        });

        jLabel97.setText("Search Value");

        jLabel98.setText("Category");

        item_search_checkbox.setText("Activate");
        item_search_checkbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                item_search_checkboxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout ItemSearchPanelLayout = new javax.swing.GroupLayout(ItemSearchPanel);
        ItemSearchPanel.setLayout(ItemSearchPanelLayout);
        ItemSearchPanelLayout.setHorizontalGroup(
            ItemSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ItemSearchPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(ItemSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(ItemSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(ItemSearchPanelLayout.createSequentialGroup()
                            .addComponent(item_search_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(ItemSearchPanelLayout.createSequentialGroup()
                            .addComponent(jLabel97)
                            .addGap(240, 240, 240)
                            .addComponent(item_search_checkbox)
                            .addGap(18, 18, 18)
                            .addComponent(jLabel98)
                            .addGap(18, 18, 18)
                            .addComponent(item_search_category_combo, javax.swing.GroupLayout.PREFERRED_SIZE, 253, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        ItemSearchPanelLayout.setVerticalGroup(
            ItemSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ItemSearchPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(ItemSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(ItemSearchPanelLayout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel97))
                    .addGroup(ItemSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(item_search_category_combo, javax.swing.GroupLayout.DEFAULT_SIZE, 34, Short.MAX_VALUE)
                        .addComponent(jLabel98)
                        .addComponent(item_search_checkbox)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(ItemSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(item_search_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton27, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton26, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );

        MainChangeFrame.add(ItemSearchPanel, "card12");

        UserSearchPanel.setForeground(new java.awt.Color(255, 255, 255));

        user_search_txt.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                user_search_txtKeyPressed(evt);
            }
        });

        jButton28.setBackground(new java.awt.Color(255, 255, 255));
        jButton28.setText("Go Back");
        jButton28.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton28ActionPerformed(evt);
            }
        });

        userSearchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Contact Details"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane8.setViewportView(userSearchTable);

        jButton29.setBackground(new java.awt.Color(255, 255, 255));
        jButton29.setText("Search");
        jButton29.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton29ActionPerformed(evt);
            }
        });

        jLabel99.setText("Search Value");

        javax.swing.GroupLayout UserSearchPanelLayout = new javax.swing.GroupLayout(UserSearchPanel);
        UserSearchPanel.setLayout(UserSearchPanelLayout);
        UserSearchPanelLayout.setHorizontalGroup(
            UserSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserSearchPanelLayout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(UserSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 766, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(UserSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addGroup(UserSearchPanelLayout.createSequentialGroup()
                            .addComponent(user_search_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(18, 18, 18)
                            .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(UserSearchPanelLayout.createSequentialGroup()
                            .addComponent(jLabel99)
                            .addGap(653, 653, 653))))
                .addContainerGap(27, Short.MAX_VALUE))
        );
        UserSearchPanelLayout.setVerticalGroup(
            UserSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(UserSearchPanelLayout.createSequentialGroup()
                .addContainerGap(241, Short.MAX_VALUE)
                .addComponent(jLabel99)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(UserSearchPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(user_search_txt, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton29, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton28, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane8, javax.swing.GroupLayout.PREFERRED_SIZE, 454, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(81, 81, 81))
        );

        MainChangeFrame.add(UserSearchPanel, "card12");

        getContentPane().add(MainChangeFrame, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 110, 810, 660));

        TitlePanel.setBackground(new java.awt.Color(51, 51, 51));
        TitlePanel.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 48)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("K.T.N. Motors");
        TitlePanel.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 30, -1, 60));

        clock_txt.setFont(new java.awt.Font("Digital-7", 1, 24)); // NOI18N
        clock_txt.setForeground(new java.awt.Color(0, 165, 255));
        clock_txt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        clock_txt.setText("jLabel42");
        TitlePanel.add(clock_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 70, 160, 40));

        date_txt.setFont(new java.awt.Font("Digital-7", 1, 24)); // NOI18N
        date_txt.setForeground(new java.awt.Color(0, 165, 255));
        date_txt.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        date_txt.setText("jLabel42");
        TitlePanel.add(date_txt, new org.netbeans.lib.awtextra.AbsoluteConstraints(610, 30, 160, 40));

        jButton3.setText("Stock Count");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });
        TitlePanel.add(jButton3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 40, 100, 60));

        jLabel79.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/Services_52px.png"))); // NOI18N
        jLabel79.setPreferredSize(new java.awt.Dimension(50, 50));
        jLabel79.setRequestFocusEnabled(false);
        jLabel79.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel79MouseClicked(evt);
            }
        });
        TitlePanel.add(jLabel79, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, -1, -1));

        getContentPane().add(TitlePanel, new org.netbeans.lib.awtextra.AbsoluteConstraints(220, 0, 810, 110));

        pack();
    }// </editor-fold>//GEN-END:initComponents

    public void showTime() {
        new Thread() {
            int timeRun = 0;

            public void run() {
                while (timeRun == 0) {
                    Calendar cal = new GregorianCalendar();

                    int hour = cal.get(Calendar.HOUR);
                    int min = cal.get(Calendar.MINUTE);
                    int sec = cal.get(Calendar.SECOND);
                    int AM_PM = cal.get(Calendar.AM_PM);

                    String day_night = "";
                    if (AM_PM == 1) {
                        day_night = "PM";
                    } else {
                        day_night = "AM";
                    }
                    String time = hour + ":" + min + ":" + sec + "  " + day_night;
                    clock_txt.setText(time);
                }
            }
        }.start();
    }

    void showDate() {
        Date d = new Date();
        SimpleDateFormat s = new SimpleDateFormat("dd-MM-yyyy");
        date_txt.setText(s.format(d));
    }
    private void AddProductLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddProductLabelMouseClicked

        ViewManipulation.changePanel(MainChangeFrame, AddItemPanel);

        ItemAdd item_add = ItemAdd.getInstance();

        item_add.setFields(add_itemNo_txt, add_item_vehicle_txt, add_item_brand_txt, add_item_desc_txt, add_item_location_txt, add_item_category_combo, add_item_unit_combo, connector);

        item_add.changeStateAddItem(false);

        item_add_new_btn.requestFocusInWindow();
        item_add_new_btn.setEnabled(true);

    }//GEN-LAST:event_AddProductLabelMouseClicked

    private void ReportLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ReportLabelMouseClicked

        ViewManipulation.changePanel(MainChangeFrame, ReportPanel);

        item_date1_label.setVisible(false);
        item_date2_label.setVisible(false);
        reports_date2_picker.setVisible(false);
        reports_date1_picker.setVisible(false);

        setDefaultDateRange(reports_date1_picker, reports_date2_picker, 12);

        populateWiseReportPanel();
    }//GEN-LAST:event_ReportLabelMouseClicked

    public void setDefaultDateRange(JDateChooser from, JDateChooser to, int range) {
        Date d = new Date();
        to.setDate(d);
        Calendar c = Calendar.getInstance();
        c.setTime(d);
        c.add(Calendar.MONTH, -range);
        d = c.getTime();
        from.setDate(d);

    }

    boolean once = true;

    public void populateWiseReportPanel() {
        if (once) {
            AutoCompleteDecorator.decorate(report_item_combo);
            AutoCompleteDecorator.decorate(report_name_combo);
            AutoCompleteDecorator.decorate(report_userID_combo);
            once = false;
        }

        report_item_combo.removeAllItems();
        report_name_combo.removeAllItems();
        report_userID_combo.removeAllItems();
        manipulation.getRecords("items", "item_code", report_item_combo);
        manipulation.getRecords("customers", "customer_code", report_userID_combo);
        manipulation.getRecords("customers", "name", report_name_combo);

    }

    private void newBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_newBtnActionPerformed
        AdminConfirmation confirmation = AdminConfirmation.getInstance();
        boolean output = confirmation.presentDialog();
        if (output) {
            AddCustomer.getInstance().changeStateAddUser(true);
            AddCustomer.addOrUpdate = "add";
            AddCustomer.getInstance().emptyUserFields();
            newBtn.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Sorry you are not authorized", "Authorization Failed", 0);
        }
    }//GEN-LAST:event_newBtnActionPerformed

    private void SalesLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SalesLabelMouseClicked

        ViewManipulation.changePanel(MainChangeFrame, SalesPanel);

        sales_new_btn.requestFocusInWindow();

    }//GEN-LAST:event_SalesLabelMouseClicked

    private void saveBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveBtnActionPerformed
        if (AddCustomer.addOrUpdate.equals("add")) {
            AddCustomer.getInstance().saveUser();
        } else {
            AddCustomer.getInstance().updateUser();
        }
        saveBtn.setEnabled(false);

    }//GEN-LAST:event_saveBtnActionPerformed


    private void editBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editBtnActionPerformed
        AddCustomer.addOrUpdate = "update";
        editBtn.setEnabled(false);
        JComboBox edit_combo = new JComboBox();
        AutoCompleteDecorator.decorate(edit_combo);

        manipulation.getRecords("customers", "customer_code", edit_combo);
        String userID = null;

        final JComponent[] inputs = new JComponent[]{
            new JLabel("UserID"),
            edit_combo

        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            userID = String.valueOf(edit_combo.getSelectedItem());
            System.out.println("userid" + userID);
//            ItemAdd.getInstance().fillEdit(userID);
            AddCustomer.getInstance().updateUserFields(userID);
//            editable=true;
            add_item_save_btn.setEnabled(true);
            AddCustomer.getInstance().changeStateAddUser(true);
        } else {
            System.out.println("User canceled / closed the dialog, result = " + result);
        }

    }//GEN-LAST:event_editBtnActionPerformed

    private void NameTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NameTxtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            AddressTxt.requestFocus();
        }
    }//GEN-LAST:event_NameTxtKeyPressed

    private void AddressTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AddressTxtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            TelephoneTxt.requestFocus();
        }
    }//GEN-LAST:event_AddressTxtKeyPressed

    private void TelephoneTxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TelephoneTxtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            saveBtn.requestFocus();
        }
    }//GEN-LAST:event_TelephoneTxtKeyPressed

    private void cancelBtnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBtnActionPerformed
        AddCustomer.getInstance().emptyUserFields();
        editBtn.setEnabled(true);
    }//GEN-LAST:event_cancelBtnActionPerformed

    private void item_add_new_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_add_new_btnActionPerformed
        ItemAdd.getInstance().changeStateAddItem(true);
        ItemAdd.getInstance().emptyItemFields();
        editable = false;
        add_item_save_btn.setEnabled(true);
        add_itemNo_txt.setFocusable(true);
        add_itemNo_txt.setEditable(true);
        add_itemNo_txt.requestFocusInWindow();

    }//GEN-LAST:event_item_add_new_btnActionPerformed

    boolean editable = false;

    private void item_add_edit_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_add_edit_btnActionPerformed
        JComboBox edit_combo = new JComboBox();

        AutoCompleteDecorator.decorate(edit_combo);

        manipulation.getRecords("items", "item_code", edit_combo);
        String itemNo = null;

        final JComponent[] inputs = new JComponent[]{
            new JLabel("UserID"),
            edit_combo

        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            itemNo = String.valueOf(edit_combo.getSelectedItem());
            ItemAdd.getInstance().fillEdit(itemNo);
            editable = true;
            add_item_save_btn.setEnabled(true);
        } else {
            System.out.println("User canceled / closed the dialog, result = " + result);
        }
    }//GEN-LAST:event_item_add_edit_btnActionPerformed


    private void add_item_save_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_item_save_btnActionPerformed
        if (editable) {
            ItemAdd.getInstance().itemSave(editable);
            add_item_save_btn.setEnabled(false);
        } else {
            if (!ItemAdd.getInstance().CheckAvailability()) {
                if (ItemAdd.getInstance().itemSave(editable)) {
                    add_item_save_btn.setEnabled(false);
                }
            } else {
                ItemAdd.getInstance().emptyItemFields();
                add_item_save_btn.setEnabled(false);
                ItemAdd.getInstance().changeStateAddItem(false);
            }
        }

        item_add_new_btn.setEnabled(true);
        item_add_new_btn.requestFocusInWindow();

    }//GEN-LAST:event_add_item_save_btnActionPerformed

    private void add_item_cancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_item_cancel_btnActionPerformed
        ItemAdd.getInstance().changeStateAddItem(false);
        ItemAdd.getInstance().emptyItemFields();
        item_add_new_btn.setEnabled(true);
        item_add_new_btn.requestFocusInWindow();
    }//GEN-LAST:event_add_item_cancel_btnActionPerformed

    private void add_itemNo_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_itemNo_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_itemNo_txtActionPerformed

    private void add_itemNo_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_itemNo_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            boolean CheckAvailability = ItemAdd.getInstance().CheckAvailability();
            if (CheckAvailability) {
                add_item_category_combo.requestFocus();
            }

        }
    }//GEN-LAST:event_add_itemNo_txtKeyPressed

    public void checkItemAlreadyInDB() {
        if (connector.getRelavantRecord("item", "itemNo", "itemNo", add_itemNo_txt.getText()).isEmpty()) {
            add_item_vehicle_txt.requestFocus();
        } else {
            JOptionPane.showMessageDialog(null, "The item is already added to the database");
        }
    }

    private void add_item_vehicle_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_item_vehicle_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            add_item_brand_txt.requestFocus();
        }
    }//GEN-LAST:event_add_item_vehicle_txtKeyPressed

    private void add_item_desc_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_item_desc_txtKeyPressed
    }//GEN-LAST:event_add_item_desc_txtKeyPressed

    private void add_item_save_btnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_item_save_btnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            ItemAdd.getInstance().itemSave(editable);
            ItemAdd.getInstance().emptyItemFields();
            item_add_new_btn.setEnabled(true);
            item_add_new_btn.requestFocusInWindow();

        }
    }//GEN-LAST:event_add_item_save_btnKeyPressed

    private void item_add_new_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_item_add_new_btnMouseClicked
        item_add_new_btn.setEnabled(false);
        add_itemNo_txt.grabFocus();
        add_itemNo_txt.requestFocusInWindow();

    }//GEN-LAST:event_item_add_new_btnMouseClicked

    private void item_add_new_btnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_item_add_new_btnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            item_add_new_btn.setEnabled(false);
            add_itemNo_txt.grabFocus();
            add_itemNo_txt.requestFocusInWindow();
            ItemAdd.getInstance().changeStateAddItem(true);
        }


    }//GEN-LAST:event_item_add_new_btnKeyPressed

    private void add_item_save_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_add_item_save_btnMouseClicked

    }//GEN-LAST:event_add_item_save_btnMouseClicked

    private void saveBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_saveBtnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

            AddCustomer.getInstance().saveUser();
            newBtn.requestFocusInWindow();

        }
    }//GEN-LAST:event_saveBtnKeyPressed

    private void newBtnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_newBtnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            newBtn.setEnabled(false);
            AddCustomer.getInstance().changeStateAddUser(true);
            newBtn.setEnabled(false);
            AddCustomer.getInstance().emptyUserFields();
            String ID = generateUserID();

        }
    }//GEN-LAST:event_newBtnKeyPressed

    private void add_item_desc_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_item_desc_txtKeyTyped

    }//GEN-LAST:event_add_item_desc_txtKeyTyped

    private void sales_new_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_new_btnActionPerformed
        makeAllSalesComponents(true);
        sales_save_btn.setEnabled(true);
        sales_print_btn.setEnabled(true);

        sales_InvoiceID_txt.setText(item_sale.generateSaleID(connector));

        sales_discount_txt.setText("0");

//        item_sale.salesAutoCombo();
        sales_CID_combo.setEnabled(true);
        sales_CName_combo.setEnabled(true);
        sales_item_name_combo.setEnabled(true);
        sales_itemno_combo.setEnabled(true);
        sales_itemno_combo.setSelectedIndex(0);
        sales_CID_combo.requestFocusInWindow();
        sales_new_btn.setEnabled(false);
        sales_save_btn.setEnabled(false);
        sales_print_btn.setEnabled(false);

        sales_save_btn.setEnabled(true);
        sales_remove_btn.setEnabled(true);
        sales_total_txt.setEditable(true);
        sales_discount_txt.setEditable(true);
        sales_grand_txt.setEditable(true);

        sales_halfPay_check.setSelected(false);
        sales_halfPay_panel.setVisible(false);

        sales_additional_txt.setEditable(true);

        sales_CID_combo.setSelectedItem("1");
        ViewManipulation.emptyTable(sales_item_table);

        item_sale.decoratingCustomers();

    }//GEN-LAST:event_sales_new_btnActionPerformed

    private void sales_CID_comboKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_CID_comboKeyPressed

    }//GEN-LAST:event_sales_CID_comboKeyPressed

    private void sales_CID_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_CID_comboActionPerformed

        String userID = (String) sales_CID_combo.getSelectedItem();

        String userName = connector.getRelavantRecord("customers", "name", "customer_code", userID);

        sales_CName_combo.setSelectedItem(userName);


    }//GEN-LAST:event_sales_CID_comboActionPerformed

    private void sales_CID_comboKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_CID_comboKeyReleased
//        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//          
//            String userID = (String) sales_CID_combo.getSelectedItem();
//            
//            String userName = searchRecord("customers", "name", "c", userID);
//           
//            sales_CName_combo.setSelectedItem(userName);
//        }
    }//GEN-LAST:event_sales_CID_comboKeyReleased

    private void sales_CName_comboKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_CName_comboKeyPressed

    }//GEN-LAST:event_sales_CName_comboKeyPressed

    private void sales_CName_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_CName_comboActionPerformed

        String name = (String) sales_CName_combo.getSelectedItem();

        String userID = connector.getRelavantRecord("customers", "customer_code", "name", name);

        sales_CID_combo.setSelectedItem(userID);
    }//GEN-LAST:event_sales_CName_comboActionPerformed
    int focusCounterItemNoCombo = 1;
    private void sales_itemno_comboFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sales_itemno_comboFocusGained
        focus_counter = 0;

    }//GEN-LAST:event_sales_itemno_comboFocusGained

    private void sales_itemno_comboKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_itemno_comboKeyPressed

    }//GEN-LAST:event_sales_itemno_comboKeyPressed

    private void sales_itemno_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_itemno_comboActionPerformed
        String itemNo = (String) sales_itemno_combo.getSelectedItem();

        String description = connector.getRelavantRecord("items", "category", "item_code", itemNo);
        sales_item_name_combo.setSelectedItem(description);

        //Qty Adding to the text Box
        String qty = connector.getRelavantRecord("items", "stock", "item_code", itemNo);
        sales_available_qty_txt.setText(qty);

        String unit = connector.getRelavantRecord("items", "unit", "item_code", itemNo);
        sales_unit_Txt.setText(unit);

    }//GEN-LAST:event_sales_itemno_comboActionPerformed

    private void sales_qty_TxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_qty_TxtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            ItemToTable toTable = new ItemToTable(sales_itemno_combo, sales_qty_Txt, sales_available_qty_txt, sales_total_txt, sales_discount_txt, sales_grand_txt, sales_item_table, connector);

            /*
                Obtain customer code to check whether user is cash
                Obtain row_count of item table to check whther it's exceeding the row limit
             */
            int customer_code = Integer.parseInt(sales_CID_combo.getSelectedItem().toString());
            int row_count = sales_item_table.getRowCount();
            String salesQty = sales_qty_Txt.getText();
            if (!salesQty.equals("") || !salesQty.equals("0.00")) {
                try {
                    if (customer_code == 1 || row_count < 21) {
                        if (toTable.itemToTable()) {
                            sales_itemno_combo.requestFocusInWindow();
                            toTable.calculatetotal();
                            sales_save_btn.setEnabled(true);

                        }
                    } else {
                        JOptionPane.showMessageDialog(null, "You are exceeding maximum number of items per invoice for the credit invoice please start a new invoice");

                    }

                } catch (Exception e) {
                    JOptionPane.showMessageDialog(null, "Exception in table adding :" + e.getMessage() + " Error line : ");
                    Logger.getLogger(DataBaseConnector.class.getName()).log(Level.SEVERE, null, e);
                }
            }
        }
    }//GEN-LAST:event_sales_qty_TxtKeyPressed

    private void sales_qty_TxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_qty_TxtKeyTyped
        String type = sales_unit_Txt.getText();
        char c = evt.getKeyChar();
        System.out.println("Type" + type);
        if (type.equals("Feet") || type.equals("Meter") || type.equals("Set")) {
            System.out.println("first");
            if (Character.isDigit(c) || evt.getKeyChar() == '.') {

            } else {
                evt.consume();
            }
        } else {
            System.out.println("else");
            if (!Character.isDigit(c)) {
                evt.consume();

            }
        }

    }//GEN-LAST:event_sales_qty_TxtKeyTyped

    private void sales_CName_comboFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sales_CName_comboFocusGained
        sales_itemno_combo.requestFocusInWindow();
    }//GEN-LAST:event_sales_CName_comboFocusGained

    private void sales_item_name_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_item_name_comboActionPerformed

    }//GEN-LAST:event_sales_item_name_comboActionPerformed

    private void sales_itemno_comboKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_itemno_comboKeyTyped

    }//GEN-LAST:event_sales_itemno_comboKeyTyped

    private void sales_itemno_comboKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_itemno_comboKeyReleased

    }//GEN-LAST:event_sales_itemno_comboKeyReleased

    private void sales_itemno_comboPopupMenuWillBecomeVisible(javax.swing.event.PopupMenuEvent evt) {//GEN-FIRST:event_sales_itemno_comboPopupMenuWillBecomeVisible

    }//GEN-LAST:event_sales_itemno_comboPopupMenuWillBecomeVisible

    private void sales_discount_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_discount_txtKeyTyped

    }//GEN-LAST:event_sales_discount_txtKeyTyped

    private void sales_discount_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_discount_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            calculateGrandTotal();
        }
    }//GEN-LAST:event_sales_discount_txtKeyPressed

    private void sales_save_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_save_btnActionPerformed
        calculateGrandTotal();

        InvoiceToDB toDB = new InvoiceToDB(sales_CID_combo, sales_InvoiceID_txt, sales_item_table, sales_additional_txt, connector);
        toDB.setValuesForInsertOrder(sales_total_txt, sales_discount_txt, sales_grand_txt, sales_halfPay_txt, sales_halfPay_check);

        if (toDB.validUserPurchaise()) {
            String msg = toDB.TableToDB();
            String error1 = "Enter completed";
            String error2 = "OrderItemsNotDoneButOrderDone";
            String error3 = "Complete failure";

            if (msg.equals(error1)) {
                JOptionPane.showMessageDialog(null, "The record is added Successfully", "Successful", JOptionPane.INFORMATION_MESSAGE);

                makeAllSalesComponents(false);
                sales_new_btn.setEnabled(true);
                sales_save_btn.setEnabled(false);
                sales_print_btn.setEnabled(true);
            } else {
                JOptionPane.showMessageDialog(null, "Error has been occured during : " + msg);
            }

        }

    }//GEN-LAST:event_sales_save_btnActionPerformed

    private void sales_cancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_cancel_btnActionPerformed
        emptyFieldsInSales();
        makeAllSalesComponents(false);
        sales_new_btn.setEnabled(true);
        sales_save_btn.setEnabled(false);
    }//GEN-LAST:event_sales_cancel_btnActionPerformed

    int focus_counter = 0;
    private void sales_qty_TxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sales_qty_TxtFocusGained

        if (sales_available_qty_txt.getText().equals("0") && (focus_counter == 0)) {
            focus_counter++;
            JOptionPane.showMessageDialog(null, "Sorry qty finished", "Qty Finished", JOptionPane.WARNING_MESSAGE);
            sales_itemno_combo.requestFocusInWindow();
        }


    }//GEN-LAST:event_sales_qty_TxtFocusGained

    private void updateQtylblMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_updateQtylblMouseClicked
        update_itemNo_combo.removeAllItems();
        ViewManipulation.changePanel(MainChangeFrame, Purchaising_base_panel);
        Purchaise.getInstance().setFields(update_itemNo_combo, purchaise_retailer_combo, purchaise_invoiceno_txt, update_description_txt, update_billPrice_txt, update_costP_txt, update_cost_lbl, update_sellingP_txt, update_selling_lbl, update_available_lbl, update_qty_txt, connector);
        Purchaise.getInstance().autoFill();
    }//GEN-LAST:event_updateQtylblMouseClicked

    private void update_description_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_update_description_txtKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_update_description_txtKeyPressed

    private void update_billPrice_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_update_billPrice_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            update_costP_txt.selectAll();
            update_costP_txt.requestFocusInWindow();

        }

    }//GEN-LAST:event_update_billPrice_txtKeyPressed

    private void update_billPrice_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_update_billPrice_txtKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c) && !evt.isAltDown()) {
            evt.consume();
        }
    }//GEN-LAST:event_update_billPrice_txtKeyTyped

    private void update_costP_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_update_costP_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                update_sellingP_txt.selectAll();
                update_sellingP_txt.requestFocusInWindow();
                String cost = Purchaise.getInstance().calculateCost();
                if (!cost.isEmpty()) {
                    update_cost_lbl.setText(cost);
                }

            }

        }
    }//GEN-LAST:event_update_costP_txtKeyPressed

    private void update_costP_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_update_costP_txtKeyTyped
        char c = evt.getKeyChar();
        if (Character.isLetter(c) && !evt.isAltDown()) {
            evt.consume();
        }
    }//GEN-LAST:event_update_costP_txtKeyTyped

    private void itemCancelBtn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_itemCancelBtn1ActionPerformed
        Purchaise.getInstance().updateClearFields();
    }//GEN-LAST:event_itemCancelBtn1ActionPerformed

    private void update_save_btnMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_update_save_btnMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_update_save_btnMouseClicked

    private void update_save_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_save_btnActionPerformed
        if ((!purchaise_invoiceno_txt.getText().equals("")) && (!update_qty_txt.getText().equals(""))) {
            if (Purchaise.getInstance().comparePrices()) {
                if (Purchaise.getInstance().updateQty_save()) {
                    JOptionPane.showMessageDialog(null, "Qty is successfully Updated...");
                    update_itemNo_combo.requestFocusInWindow();
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Please fill the necessary fields before saving");
        }

    }//GEN-LAST:event_update_save_btnActionPerformed

    private void update_save_btnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_update_save_btnKeyPressed
        if ((!purchaise_invoiceno_txt.getText().equals("")) && (!update_qty_txt.getText().equals(""))) {
            if (Purchaise.getInstance().comparePrices()) {
                if (Purchaise.getInstance().updateQty_save()) {
                    JOptionPane.showMessageDialog(null, "Qty is successfully Updated...");
                    update_itemNo_combo.requestFocusInWindow();
                }
            }

        } else {
            JOptionPane.showMessageDialog(null, "Please fill the necessary fields before saving");
        }

    }//GEN-LAST:event_update_save_btnKeyPressed

    private void update_qty_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_update_qty_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            update_save_btn.requestFocusInWindow();
        }
    }//GEN-LAST:event_update_qty_txtKeyPressed

    private void update_qty_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_update_qty_txtKeyTyped
        char keyChar = evt.getKeyChar();
        if (!Character.isDigit(keyChar)) {
            evt.consume();
        }
    }//GEN-LAST:event_update_qty_txtKeyTyped

    private void update_itemNo_comboFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_update_itemNo_comboFocusGained


    }//GEN-LAST:event_update_itemNo_comboFocusGained

    private void update_description_txtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_update_description_txtFocusGained

    }//GEN-LAST:event_update_description_txtFocusGained

    private void update_billPrice_txtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_update_billPrice_txtFocusGained

    }//GEN-LAST:event_update_billPrice_txtFocusGained

    private void sales_print_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_print_btnActionPerformed
        InvoicePrint print = new InvoicePrint(report_folder_path, sales_InvoiceID_txt, sales_CID_combo, sales_item_table, connector);

        ArrayList<String> list = new ArrayList<String>();

        list.add(String.valueOf(sales_InvoiceID_txt.getText()));
        list.add(String.valueOf(sales_total_txt.getText()));
        list.add(String.valueOf(sales_discount_txt.getText()));
        list.add(String.valueOf(sales_grand_txt.getText()));

        print.setMetaArrayList(list);

        if (String.valueOf(sales_CID_combo.getSelectedItem()).equals("1")) {
            print.cashPrint();
        } else {
            print.creditPrint2();
//            print.creditPrint();
        }
    }//GEN-LAST:event_sales_print_btnActionPerformed


    private void sales_remove_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_remove_btnActionPerformed
        DefaultTableModel model = (DefaultTableModel) sales_item_table.getModel();
        try {
            int rowIndex = sales_item_table.getSelectedRow();
            model.removeRow(rowIndex);
            calculatetotal();
            calculateGrandTotal();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Select a Row first....");

        }
    }//GEN-LAST:event_sales_remove_btnActionPerformed

    private void reports_dayEnd_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reports_dayEnd_btnActionPerformed
        DayEndView view = new DayEndView();
        view.setVisible(true);
    }//GEN-LAST:event_reports_dayEnd_btnActionPerformed

    private void report_items_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_report_items_btnActionPerformed
        String path = report_folder_path + "\\item\\items.jrxml";

        JasperReport jr;
        try {
            jr = JasperCompileManager.compileReport(path);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, connector.startConnection());
            JasperViewer jw = new JasperViewer(jp, false);
            jw.viewReport(jp, false);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "ERROR in Reporting all items...");
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

    }//GEN-LAST:event_report_items_btnActionPerformed

    private void reports_customer_btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reports_customer_btn1ActionPerformed
        String path = report_folder_path + "\\user\\userReport.jrxml";

        JasperReport jr;
        try {
            jr = JasperCompileManager.compileReport(path);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, connector.startConnection());
            JasperViewer jw = new JasperViewer(jp, false);
            jw.viewReport(jp, false);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "ERROR in Reporting all items...");
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_reports_customer_btn1ActionPerformed

    private void reports_customer_btn2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_reports_customer_btn2ActionPerformed
        // TODO add your handling code here:
        InvoiceSearch search = new InvoiceSearch(sales_InvoiceID_txt, sales_CID_combo, sales_CName_combo, sales_item_table, sales_total_txt, sales_discount_txt, sales_grand_txt, sales_additional_txt, connector);
        search.setHalfPayComponents(sales_halfPay_check, sales_halfPay_txt, sales_halfPay_creditTxt, sales_halfPay_panel);
        JComboBox search_invoiceID = new JComboBox();
        AutoCompleteDecorator.decorate(search_invoiceID);

        manipulation.getRecords("invoices", "invoice_id", search_invoiceID);
        String invoiceID = "";

        final JComponent[] inputs = new JComponent[]{
            new JLabel("Invoice ID"),
            search_invoiceID

        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Select Invoice number", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            invoiceID = String.valueOf(search_invoiceID.getSelectedItem());
            String path = report_folder_path + "\\SalesInvoice\\invoice_summary.jrxml";
            HashMap hm = new HashMap();
            hm.put("invoiceID", invoiceID);
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

        } else {
            System.out.println("User canceled / closed the dialog, result = " + result);
        }
    }//GEN-LAST:event_reports_customer_btn2ActionPerformed

    private void AddUserLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_AddUserLabelMouseClicked
        ViewManipulation.changePanel(MainChangeFrame, AddUserPannel);
        AddCustomer.getInstance().setFields(NameTxt, AddressTxt, AddressTxt1, TelephoneTxt, newBtn, connector);
        AddCustomer.getInstance().changeStateAddUser(false);
        newBtn.requestFocusInWindow();


    }//GEN-LAST:event_AddUserLabelMouseClicked

    private void payBillsLabelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_payBillsLabelMouseClicked

        ViewManipulation.changePanel(MainChangeFrame, bill_pay_panel);

        ButtonGroup group = new ButtonGroup();
        group.add(bill_chooseDate);
        group.add(bill_currentDate);
        bill_currentDate.setSelected(true);

        bill_paymentPane.setVisible(false);
        bill_cash_datePicker.setEnabled(false);

        BillPay.getInstance().setFields(bill_userID_combo, bill_name_combo, bill_InvoiceID_combo, bill_paying_txt, Bill_table, connector);
        BillPay.getInstance().fillDataToCombo();
//        newBillPay();
    }//GEN-LAST:event_payBillsLabelMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        BillPay.getInstance().checkForBills();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void Bill_tableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Bill_tableMouseClicked
        if (evt.getClickCount() == 2 && !evt.isConsumed()) {
            evt.consume();
            Point point = evt.getPoint();
            int row = Bill_table.rowAtPoint(point);
            DefaultTableModel model = (DefaultTableModel) Bill_table.getModel();
            String invoiceID = String.valueOf(model.getValueAt(row, 0));
            bill_paymentPane.setVisible(true);
            bill_InvoiceID_combo.setSelectedItem(invoiceID);
            BillPay.getInstance().setBillPaymentFields(bill_invoiceValue_txt, Bill_date_txt, bill_payable_txt, bill_return_label, bill_paymentPane);
            BillPay.getInstance().filLBillPay();
        }
    }//GEN-LAST:event_Bill_tableMouseClicked

    private void cashPayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cashPayActionPerformed
        AdminConfirmation confirmation = AdminConfirmation.getInstance();
        boolean output = confirmation.presentDialog();
        if (output) {
            if (!bill_payable_txt.getText().equals("0.0")) {
                BillPay.getInstance().setCashPayFields(bill_currentDate, bill_chooseDate, bill_cash_datePicker);
                BillPay.getInstance().cashPay();

                Bill_table.setVisible(false);
                bill_paymentPane.setVisible(false);
                bill_invoiceValue_txt.setText("");
                Bill_date_txt.setText("");
            } else {
                JOptionPane.showMessageDialog(null, "This does not have any payable amount");
            }
            newBtn.setEnabled(false);
        } else {
            JOptionPane.showMessageDialog(null, "Sorry you are not authorized", "Authorization Failed", 0);
        }    
    }//GEN-LAST:event_cashPayActionPerformed

    public boolean validationChequePanel() {
        if (bill_chequeNo_txt.getText().equals("") || bill_chequeDatePicker.getDate().equals("")) {
            JOptionPane.showMessageDialog(null, "Please Enter chque number and date");
            return true;
        }
        return false;

    }

    private void bill_chooseDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bill_chooseDateActionPerformed
        bill_cash_datePicker.setEnabled(true);
    }//GEN-LAST:event_bill_chooseDateActionPerformed

    private void bill_currentDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bill_currentDateActionPerformed
        bill_cash_datePicker.setEnabled(false);
    }//GEN-LAST:event_bill_currentDateActionPerformed

    private void chequePayActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_chequePayActionPerformed

        if (!bill_payable_txt.getText().equals("0.0")) {
            BillPay.getInstance().setChequePayFields(bill_bank_comnbo, bill_chequeDatePicker, bill_chequeNo_txt);
            BillPay.getInstance().chequePay();

            Bill_table.setVisible(false);
            bill_paymentPane.setVisible(false);
            bill_invoiceValue_txt.setText("");
            Bill_date_txt.setText("");
        } else {
            JOptionPane.showMessageDialog(null, "This does not have any payable amount");

        }

    }//GEN-LAST:event_chequePayActionPerformed

    private void bill_pay_select_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bill_pay_select_btnActionPerformed
        BillPay.getInstance().setBillPaymentFields(bill_invoiceValue_txt, Bill_date_txt, bill_payable_txt, bill_return_label, bill_paymentPane);
        BillPay.getInstance().filLBillPay();
    }//GEN-LAST:event_bill_pay_select_btnActionPerformed

    private void bill_userID_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bill_userID_comboActionPerformed
        String userID = String.valueOf(bill_userID_combo.getSelectedItem());

        String name = connector.getRelavantRecord("customers", "name", "customer_code", userID);
        bill_name_combo.setSelectedItem(name);
    }//GEN-LAST:event_bill_userID_comboActionPerformed

    private void bill_name_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bill_name_comboActionPerformed
        String name = String.valueOf(bill_name_combo.getSelectedItem());

        String userID = connector.getRelavantRecord("customers", "customer_code", "name", name);
        bill_userID_combo.setSelectedItem(userID);
    }//GEN-LAST:event_bill_name_comboActionPerformed

    private void NameTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_NameTxtFocusGained
        NameTxt.selectAll();
    }//GEN-LAST:event_NameTxtFocusGained

    private void AddressTxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AddressTxtFocusGained
        AddressTxt.selectAll();
    }//GEN-LAST:event_AddressTxtFocusGained

    private void update_costP_txtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_update_costP_txtFocusGained
        update_costP_txt.selectAll();
    }//GEN-LAST:event_update_costP_txtFocusGained

    private void update_qty_txtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_update_qty_txtFocusGained

        update_qty_txt.selectAll();
    }//GEN-LAST:event_update_qty_txtFocusGained

    private void report_userID_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_report_userID_comboActionPerformed
        String userID = String.valueOf(report_userID_combo.getSelectedItem());

        String name = connector.getRelavantRecord("customers", "name", "customer_code", userID);
        report_name_combo.setSelectedItem(name);
    }//GEN-LAST:event_report_userID_comboActionPerformed

    private void report_name_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_report_name_comboActionPerformed
        String name = String.valueOf(report_name_combo.getSelectedItem());

        String userID = connector.getRelavantRecord("customers", "customer_code", "name", name);
        report_userID_combo.setSelectedItem(userID);
    }//GEN-LAST:event_report_name_comboActionPerformed

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        String path = report_folder_path + "\\item\\emptyStock.jrxml";

        JasperReport jr;
        try {
            jr = JasperCompileManager.compileReport(path);
            JasperPrint jp = JasperFillManager.fillReport(jr, null, connector.startConnection());
            JasperViewer jw = new JasperViewer(jp, false);
            jw.viewReport(jp, false);
        } catch (JRException ex) {
            JOptionPane.showMessageDialog(null, "ERROR in Reporting all items...");
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton11ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton11ActionPerformed
        String path = report_folder_path + "\\item\\selling_history.jrxml";
        HashMap hm = new HashMap();
        hm.put("itemNo", String.valueOf(report_item_combo.getSelectedItem()));
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
    }//GEN-LAST:event_jButton11ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
        String path = report_folder_path + "\\SalesInvoice\\sales_invoice.jrxml";
        System.out.println("Path :" + path);
        String userID = String.valueOf(report_userID_combo.getSelectedItem());
        String sql = "select invoice_id,orderDate FROM invoices WHERE customer_code like ? ORDER BY orderDate DESC LIMIT 1 ";
        ArrayList list = new ArrayList();
        list.add(userID);
        String invoiceID = connector.sqlExecution(sql, "invoice_id", list);
        System.out.println("Invoice ID : " + invoiceID);
        String sub_report_directory = report_folder_path + "\\SalesInvoice\\";
        System.out.println("Sub report " + sub_report_directory);
        if (invoiceID != null) {
            HashMap hm = new HashMap();
            hm.put("userID", userID);
            hm.put("invoiceID", invoiceID);
            hm.put("subReport", sub_report_directory);

            try {
                JasperReport jr = JasperCompileManager.compileReport(path);
                JasperPrint jp = JasperFillManager.fillReport(jr, hm, connector.startConnection());
                JasperViewer jw = new JasperViewer(jp, false);
                jw.viewReport(jp, false);

            } catch (Exception ex) {
                JOptionPane.showMessageDialog(null, "Error in printing : " + ex.getMessage());

            }
        } else {
            JOptionPane.showMessageDialog(null, "Sorry This User Does not have any bills...");
        }

    }//GEN-LAST:event_jButton6ActionPerformed

    private void update_costP_txtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_update_costP_txtFocusLost

    }//GEN-LAST:event_update_costP_txtFocusLost

    private void update_itemNo_comboFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_update_itemNo_comboFocusLost

    }//GEN-LAST:event_update_itemNo_comboFocusLost

    private void add_itemNo_txtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_add_itemNo_txtFocusLost

    }//GEN-LAST:event_add_itemNo_txtFocusLost

    private void sales_searchI_btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_searchI_btn1ActionPerformed
        InvoiceSearch search = new InvoiceSearch(sales_InvoiceID_txt, sales_CID_combo, sales_CName_combo, sales_item_table, sales_total_txt, sales_discount_txt, sales_grand_txt, sales_additional_txt, connector);
        search.setHalfPayComponents(sales_halfPay_check, sales_halfPay_txt, sales_halfPay_creditTxt, sales_halfPay_panel);
        JComboBox search_invoiceID = new JComboBox();
        AutoCompleteDecorator.decorate(search_invoiceID);

        manipulation.getRecords("invoices", "invoice_id", search_invoiceID);
        String invoiceID = "";

        final JComponent[] inputs = new JComponent[]{
            new JLabel("Invoice ID"),
            search_invoiceID

        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "Select Invoice number", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            invoiceID = String.valueOf(search_invoiceID.getSelectedItem());

            search.fillInvoice(invoiceID);
            sales_save_btn.setEnabled(false);
            sales_remove_btn.setEnabled(false);
            sales_total_txt.setEditable(false);
            sales_discount_txt.setEditable(false);
            sales_grand_txt.setEditable(false);
            sales_print_btn.setEnabled(true);
            sales_additional_txt.setEnabled(false);
        } else {
            System.out.println("User canceled / closed the dialog, result = " + result);
        }


    }//GEN-LAST:event_sales_searchI_btn1ActionPerformed

    private void sales_grand_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_grand_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sales_grand_txtActionPerformed

    private void add_itemNo_txtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_add_itemNo_txtFocusGained

    }//GEN-LAST:event_add_itemNo_txtFocusGained

    private void update_costP_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_costP_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_update_costP_txtActionPerformed

    private void jButton14ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton14ActionPerformed
        ViewManipulation.changePanel(MainChangeFrame, ItemSearchPanel);
        DataManipulation manipulation = new DataManipulation(connector);
        manipulation.getRecords("category", "CatName", item_search_category_combo);
        MyCombo autoCombo1 = new MyCombo();
        autoCombo1.setSearchableCombo(item_search_category_combo, true, "No Result Found");
        item_search_category_combo.setEnabled(false);
    }//GEN-LAST:event_jButton14ActionPerformed

    private void jButton10ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton10ActionPerformed
        String path = report_folder_path + "\\item\\purchaise_history\\purchaise_history.jrxml";
//        String sub_path = report_folder_path+"\\item\\emptyStock_subreport1.jrxml";
        HashMap hm = new HashMap();
        hm.put("itemNo", String.valueOf(report_item_combo.getSelectedItem()));
//        hm.put("SUBREPORT_DIR",sub_path);
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
    }//GEN-LAST:event_jButton10ActionPerformed

    private void item_report_checkBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_report_checkBoxActionPerformed
        if (item_report_checkBox.isSelected()) {
            item_date1_label.setVisible(true);
            item_date2_label.setVisible(true);
            reports_date2_picker.setVisible(true);
            reports_date1_picker.setVisible(true);
        } else {
            item_date1_label.setVisible(false);
            item_date2_label.setVisible(false);
            reports_date2_picker.setVisible(false);
            reports_date1_picker.setVisible(false);
        }
    }//GEN-LAST:event_item_report_checkBoxActionPerformed

    private void jButton13ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton13ActionPerformed
        String path = report_folder_path + "\\user\\unpaid-bills.jrxml";
        System.out.println("path" + path);
//        Calendar c = Calendar.getInstance();
//        Timestamp toDate = new Timestamp(c.getTimeInMillis());
//        c.add(Calendar.YEAR,-1);
        Timestamp toDate = new Timestamp(reports_date2_picker.getDate().getTime());

        Timestamp fromDate = new Timestamp(reports_date1_picker.getDate().getTime());
        String fromDateString = String.valueOf(fromDate);
        String toDateString = String.valueOf(toDate);

        HashMap hm = new HashMap();
        hm.put("FromDate", fromDateString);
        hm.put("ToDate", toDateString);
        System.out.println("FromDate : " + fromDateString);
        System.out.println("FromDate : " + toDateString);

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
    }//GEN-LAST:event_jButton13ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        String path = report_folder_path + "\\user\\all_selling.jrxml";
        Date fromDate = reports_date1_picker.getDate();
        Date toDate = reports_date2_picker.getDate();
        String fromDateString = new SimpleDateFormat("yyyy-MM-dd").format(fromDate);
        String toDateString = new SimpleDateFormat("yyyy-MM-dd").format(toDate);

//        Calendar c = Calendar.getInstance();
////        Timestamp toDate = new Timestamp(c.getTimeInMillis());
//        c.add(Calendar.YEAR,-1);
//        Timestamp fromDate = new Timestamp(c.getTimeInMillis());
//        String fromDateString = String.valueOf(fromDate);
//        String toDateString = String.valueOf(toDate);
        System.out.println("FRom " + fromDateString + " To " + toDateString + " id " + String.valueOf(report_userID_combo.getSelectedItem()));
        HashMap hm = new HashMap();
        hm.put("FromDate", fromDateString);
        hm.put("ToDate", toDateString);
        hm.put("customer_code", String.valueOf(report_userID_combo.getSelectedItem()));
        System.out.println("FromDate : " + fromDateString);
        System.out.println("FromDate : " + toDateString);

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
    }//GEN-LAST:event_jButton7ActionPerformed

    private void Return_labelMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Return_labelMouseClicked
//        return_cancel_btn.setEnabled(false);

        ArrayList<JComboBox> combos = new ArrayList<JComboBox>();

        combos.add(return_invoiceID_combo);
        combos.add(return_userID_combo);
        combos.add(return_userName_combo);

        ViewManipulation.emptyComboBoxes(combos);

        ButtonGroup return_yesno = new ButtonGroup();
        return_yesno.add(return_no);
        return_yesno.add(return_yes);
        return_yes.setSelected(true);

        setDefaultDateRange(return_from_picker, return_to_picker, 6);
        ViewManipulation.changePanel(MainChangeFrame, SalesReturnPanel);
        sales_return = new SalesReturn(return_invoiceID_combo, return_userID_combo, return_userName_combo, return_total_txt,return_from_picker,return_to_picker, connector);
        sales_return.fillDataToCombo();
        sales_return.changeTableView(return_item_table);

        sales_return.eventForItemTable(return_item_table);


    }//GEN-LAST:event_Return_labelMouseClicked

    private void return_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_noActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_return_noActionPerformed

    private void return_yesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_yesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_return_yesActionPerformed

    private void return_cancel_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_cancel_btnActionPerformed
        return_cancel_btn.setEnabled(false);
        return_search_invoice.setEnabled(true);

        ViewManipulation.changePanel(sales_return_subPanel, returns_item_table);

    }//GEN-LAST:event_return_cancel_btnActionPerformed

    private void return_search_invoiceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_search_invoiceActionPerformed
        return_cancel_btn.setEnabled(true);
        sales_return.setPanels(sales_return_subPanel, returns_item_table);
        sales_return.searchInvoice(return_search_item_combo, return_invoiceSearch_table, return_item_table, connector);
        ViewManipulation.changePanel(sales_return_subPanel, returns_search_panel);
        return_search_invoice.setEnabled(false);

    }//GEN-LAST:event_return_search_invoiceActionPerformed

    private void return_invoiceID_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_invoiceID_comboActionPerformed
        String invoice_id = (String) return_invoiceID_combo.getSelectedItem();

        String customer_id = connector.getRelavantRecord("invoices", "customer_code", "invoice_id", invoice_id);
        return_userID_combo.setSelectedItem(customer_id);

        //Qty Adding to the text Box
        String customer_name = connector.getRelavantRecord("customers", "name", "customer_code", customer_id);
        return_userName_combo.setSelectedItem(customer_name);
    }//GEN-LAST:event_return_invoiceID_comboActionPerformed

    private void Return_labelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Return_labelMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_Return_labelMouseEntered

    private void return_search_item_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_search_item_comboActionPerformed

    }//GEN-LAST:event_return_search_item_comboActionPerformed

    private void return_userID_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_userID_comboActionPerformed

    }//GEN-LAST:event_return_userID_comboActionPerformed

    private void return_userID_comboKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_return_userID_comboKeyPressed

        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            if (((JTextComponent) ((JComboBox) ((Component) evt
                    .getSource()).getParent()).getEditor()
                    .getEditorComponent()).getText().isEmpty()) {
                System.out.println("please dont make me blank");
            }

            //        Populating the combobox and make them dropdown
            String customer_id = (String) return_userID_combo.getSelectedItem();

            String customer_name = connector.getRelavantRecord("users", "name", "userID", customer_id);
            return_userName_combo.setSelectedItem(customer_name);

            return_invoiceID_combo.removeAllItems();
            manipulation.getRecordsWithCondtion("orders", "orderID", "userID", customer_id, return_invoiceID_combo);
            return_invoiceID_combo.showPopup();
        }
    }//GEN-LAST:event_return_userID_comboKeyPressed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        ViewManipulation.emptyTable(return_item_table);
        ViewManipulation.changePanel(sales_return_subPanel, returns_item_table);
        String invoiceNo = String.valueOf(return_invoiceID_combo.getSelectedItem());
        sales_return.getInvoiceRecords(invoiceNo, return_item_table, connector);
    }//GEN-LAST:event_jButton4ActionPerformed

    private void sales_search_user_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_search_user_btnActionPerformed
        ViewManipulation.changePanel(MainChangeFrame, UserSearchPanel);
    }//GEN-LAST:event_sales_search_user_btnActionPerformed

    private void selectAll_buttonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_selectAll_buttonActionPerformed
        sales_return.selectAllInTable(return_item_table);
    }//GEN-LAST:event_selectAll_buttonActionPerformed

    private void sales_qty_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_qty_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sales_qty_TxtActionPerformed

    private void sales_unit_TxtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_sales_unit_TxtFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_sales_unit_TxtFocusGained

    private void sales_unit_TxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_unit_TxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sales_unit_TxtActionPerformed

    private void sales_unit_TxtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_unit_TxtKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_sales_unit_TxtKeyPressed

    private void sales_unit_TxtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_unit_TxtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_sales_unit_TxtKeyTyped

    private void return_calculation_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_calculation_btnActionPerformed
        sales_return.calculateReturningTotal(return_item_table);
    }//GEN-LAST:event_return_calculation_btnActionPerformed

    private void sales_halfPay_checkActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_halfPay_checkActionPerformed
        if (sales_CID_combo.getSelectedItem().equals("1")) {
            JOptionPane.showMessageDialog(null, "Sorry half payments can only be done with credit users");
            sales_halfPay_check.setSelected(false);
        } else {
            if (sales_halfPay_check.isSelected()) {
                sales_halfPay_panel.setVisible(true);
                sales_halfPay_txt.requestFocusInWindow();
            } else {
                sales_halfPay_panel.setVisible(false);
            }
        }


    }//GEN-LAST:event_sales_halfPay_checkActionPerformed

    private void sales_halfPay_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_halfPay_txtKeyTyped
//        char c = evt.getKeyChar();
//        
//            double grand = Double.parseDouble(sales_grand_txt.getText());
//            if(!sales_halfPay_txt.getText().equals("")){
//                double cash = Double.parseDouble(sales_halfPay_txt.getText());
//                double credit = grand - cash;
//                if (credit < 0) {
//                    JOptionPane.showMessageDialog(null, "Sorry you are entering wrong cash amount");
//                }
//                sales_halfPay_creditTxt.setText(Rounding.RoundTo5(credit, editable));
//            }
//            


    }//GEN-LAST:event_sales_halfPay_txtKeyTyped

    private void sales_halfPay_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_halfPay_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            double grand = Double.parseDouble(sales_grand_txt.getText());
            if (!sales_halfPay_txt.getText().equals("")) {
                double cash = Double.parseDouble(sales_halfPay_txt.getText());
                double credit = grand - cash;

                if (credit < 0) {
                    JOptionPane.showMessageDialog(null, "Sorry you are entering wrong cash amount");
                    sales_halfPay_txt.setText("");
                }
                sales_halfPay_creditTxt.setText(Rounding.RoundTo5(credit, editable));
            }
        }

    }//GEN-LAST:event_sales_halfPay_txtKeyPressed

    private void add_item_brand_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_item_brand_txtKeyPressed

    }//GEN-LAST:event_add_item_brand_txtKeyPressed

    private void add_item_brand_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_item_brand_txtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_add_item_brand_txtKeyTyped

    private void return_button1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_button1ActionPerformed

        ReturnToDB toDB = new ReturnToDB(return_invoiceID_combo, return_reason_combo, return_item_table, connector);
        if (toDB.checkReturnTable(return_item_table)) {
            toDB.saveToDB();
        }

    }//GEN-LAST:event_return_button1ActionPerformed

    private void return_userName_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_return_userName_comboActionPerformed
        String name = (String) return_userName_combo.getSelectedItem();

        String customer_id = connector.getRelavantRecord("customers", "customer_code", "name", name);
        return_userID_combo.setSelectedItem(customer_id);
    }//GEN-LAST:event_return_userName_comboActionPerformed

    private void NameTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NameTxtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_NameTxtActionPerformed

    private void AddressTxt1FocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_AddressTxt1FocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_AddressTxt1FocusGained

    private void AddressTxt1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_AddressTxt1KeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_AddressTxt1KeyPressed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String workingDir = System.getProperty("user.dir");
        System.out.println("Current :" + workingDir);
        AdminConfirmation confirmation = AdminConfirmation.getInstance();
        boolean output = confirmation.presentDialog();
        if (output) {
            ViewManipulation.changePanel(MainChangeFrame, stock_count_panel);
            Stock.getInstance().setFields(stock_item_combo, stock_qty_txt, stock_selling_txt, stock_selling_lbl, stock_description_lbl, stock_availableQty_lbl, stock_c_selling_lbl, stock_item_table, connector);
        } else {
            JOptionPane.showMessageDialog(null, "Sorry you are not authorized", "Authorization Failed", 0);
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void stock_selling_txtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stock_selling_txtFocusLost
        if (!stock_selling_txt.getText().isEmpty()) {
            Stock stock = Stock.getInstance();
            String selling = stock_selling_txt.getText();
            stock_selling_txt.setText(selling.toUpperCase());
            double selling_val = Double.valueOf(stock.parseToNumbers(selling));
            stock_selling_lbl.setText(Rounding.decimalFormatiing(selling_val));
        }
    }//GEN-LAST:event_stock_selling_txtFocusLost

    private void stock_qty_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stock_qty_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            stock_selling_txt.requestFocusInWindow();
        }
    }//GEN-LAST:event_stock_qty_txtKeyPressed

    private void stock_selling_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stock_selling_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Stock stock = Stock.getInstance();
            if (!stock_selling_txt.getText().equals("")) {
                String selling = stock_selling_txt.getText();
                stock_selling_txt.setText(selling.toUpperCase());
                double selling_val = Double.valueOf(stock.parseToNumbers(selling));
                stock_selling_lbl.setText(Rounding.decimalFormatiing(selling_val));
            }

            stock_update_btn.requestFocusInWindow();

        }
    }//GEN-LAST:event_stock_selling_txtKeyPressed

    private void stock_qty_txtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stock_qty_txtFocusGained
        Stock stock = Stock.getInstance();
        stock.fillDescription();
        stock_qty_txt.selectAll();
    }//GEN-LAST:event_stock_qty_txtFocusGained

    private void stock_update_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stock_update_btnActionPerformed
        Stock stock = Stock.getInstance();
        stock.update();
    }//GEN-LAST:event_stock_update_btnActionPerformed

    private void stock_update_btnKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stock_update_btnKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            Stock stock = Stock.getInstance();
            stock.update();
            stock_item_combo.getEditor().selectAll();

        }
    }//GEN-LAST:event_stock_update_btnKeyPressed

    private void stock_selling_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_stock_selling_txtKeyTyped
        char c = evt.getKeyChar();

        if (!((c == KeyEvent.VK_A) || (c == KeyEvent.VK_N) || (c == KeyEvent.VK_K) || (c == KeyEvent.VK_T) || (c == KeyEvent.VK_G) || (c == KeyEvent.VK_S) || (c == KeyEvent.VK_L) || (c == KeyEvent.VK_M) || (c == KeyEvent.VK_P) || (c == KeyEvent.VK_J) || (c == KeyEvent.VK_BACK_SPACE) || c == KeyEvent.VK_DELETE)) {
            evt.consume();
        }
    }//GEN-LAST:event_stock_selling_txtKeyTyped

    private void stock_selling_txtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stock_selling_txtFocusGained
        stock_selling_txt.selectAll();
    }//GEN-LAST:event_stock_selling_txtFocusGained

    private void stock_item_comboFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_stock_item_comboFocusGained
        stock_item_combo.getEditor().selectAll();
    }//GEN-LAST:event_stock_item_comboFocusGained

    private void add_item_category_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_item_category_comboActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_item_category_comboActionPerformed

    private void add_item_vehicle_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_item_vehicle_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_item_vehicle_txtActionPerformed

    private void add_item_desc_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_add_item_desc_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_add_item_desc_txtActionPerformed

    private void add_item_unit_comboKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_item_unit_comboKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            add_item_save_btn.requestFocusInWindow();
        }
    }//GEN-LAST:event_add_item_unit_comboKeyPressed

    private void update_sellingP_txtFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_update_sellingP_txtFocusGained
        // TODO add your handling code here:
    }//GEN-LAST:event_update_sellingP_txtFocusGained

    private void update_sellingP_txtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_update_sellingP_txtFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_update_sellingP_txtFocusLost

    private void update_sellingP_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_sellingP_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_update_sellingP_txtActionPerformed

    private void update_sellingP_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_update_sellingP_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            update_qty_txt.requestFocusInWindow();
            update_qty_txt.selectAll();
            update_selling_lbl.setText(Purchaise.getInstance().calculateSelling());
        }
    }//GEN-LAST:event_update_sellingP_txtKeyPressed

    private void update_sellingP_txtKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_update_sellingP_txtKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_update_sellingP_txtKeyTyped

    private void update_itemNo_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_update_itemNo_comboActionPerformed

        String itemNo = String.valueOf(update_itemNo_combo.getSelectedItem());

        ArrayList list = connector.readRow("items", "item_code", itemNo);
        if (list.size() != 0) {
            String description = String.valueOf(list.get(4));
            String cost = String.valueOf(list.get(6));
            String selling = String.valueOf(list.get(7));
            String available_qty = String.valueOf(list.get(5));

            update_description_txt.setText(description);
            update_cost_lbl.setText(cost);
            update_selling_lbl.setText(selling);
            update_available_lbl.setText(available_qty);
        }


    }//GEN-LAST:event_update_itemNo_comboActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        ViewManipulation.changePanel(Purchaising_base_panel, retailer_panel);
        Retailer.getInstance().setFields(retailer_name_txt, retailer_address_txt, retailer_contact_txt, connector);
        retailer_name_txt.requestFocusInWindow();
        retailer_name_txt.selectAll();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton16ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton16ActionPerformed
        Retailer.getInstance().saveRetailer();

    }//GEN-LAST:event_jButton16ActionPerformed

    private void jButton15ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton15ActionPerformed
        Retailer.getInstance().emptyFields();
    }//GEN-LAST:event_jButton15ActionPerformed

    private void retailer_name_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_retailer_name_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            retailer_address_txt.requestFocusInWindow();
        }
    }//GEN-LAST:event_retailer_name_txtKeyPressed

    private void retailer_address_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_retailer_address_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            retailer_contact_txt.requestFocusInWindow();
        }
    }//GEN-LAST:event_retailer_address_txtKeyPressed

    private void jButton17ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton17ActionPerformed
        ViewManipulation.changePanel(Purchaising_base_panel, purchaising_main_panel);
        Purchaise.getInstance().retailerAutoFill();
    }//GEN-LAST:event_jButton17ActionPerformed

    private void purchaise_invoiceno_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_purchaise_invoiceno_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            update_itemNo_combo.getEditor().getEditorComponent().requestFocusInWindow();
        }
    }//GEN-LAST:event_purchaise_invoiceno_txtKeyPressed

    private void jButton18ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton18ActionPerformed
        JFileChooser jf = new JFileChooser();
        jf.showOpenDialog(this);
        String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());

        String dbName = "prototype";
        String dbUser = "root";
        String dbPass = "";

        String default_backup = "C:/Inventory";
        default_backup += "/backups/" + date + ".sql";
        default_backup = default_backup.replace("\\", "/");

        try {
            File f = jf.getSelectedFile();
            backup_path = f.getAbsolutePath();
            backup_path = backup_path.replace('\\', '/');
            backup_path = backup_path + "_" + date + ".sql";

            String executeCmd = "C:/wamp/bin/mysql/mysql5.7.21/bin/mysqldump.exe -u lakshan -p123 --add-drop-database -B prototype -r";
            executeCmd = executeCmd + backup_path;
            System.out.println("execute : " + executeCmd);
            Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);

            int processComplete = runtimeProcess.waitFor();

            if (processComplete == 0) {
                JOptionPane.showMessageDialog(null, "Backup Created Successfully....");
            } else {
                JOptionPane.showMessageDialog(null, "Backup Created Failure....");
            }

            String executeCmd2 = "C:/wamp/bin/mysql/mysql5.7.21/bin/mysqldump.exe -u lakshan -p123 --add-drop-database -B prototype -r";
            executeCmd2 += default_backup;
            System.out.println("Auto backup :" + executeCmd2);
            Process runtimeProcess2 = Runtime.getRuntime().exec(executeCmd2);
            int processComplete2 = runtimeProcess2.waitFor();

            if (processComplete2 == 0) {
                JOptionPane.showMessageDialog(null, "Auto Backup Created Successfully....");
            } else {
                JOptionPane.showMessageDialog(null, "Auto backup failure...");
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "error " + e.getMessage());
        }
//        DoBackup.Backupdbtosql();
    }//GEN-LAST:event_jButton18ActionPerformed

    private void jButton19ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton19ActionPerformed
        JComboBox combo = new JComboBox();
        AutoCompleteDecorator.decorate(combo);

        JTextField text = new JTextField();
        manipulation.getRecords("items", "item_code", combo);
        String item_code = "";

        final JComponent[] inputs = new JComponent[]{
            new JLabel("Item ID"),
            combo,
            new JLabel("Qty to be added"),
            text

        };
        int result = JOptionPane.showConfirmDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
        if (result == JOptionPane.OK_OPTION) {
            item_code = String.valueOf(combo.getSelectedItem());
            int qty = Integer.parseInt(text.getText());
            String available_qty = connector.getRelavantRecord("items", "stock", "item_code", item_code);
            System.out.println("Available :" + available_qty);
            qty += Integer.valueOf(available_qty);
            if (connector.editRecordInTable("items", "item_code", "stock", String.valueOf(qty), item_code)) {
                JOptionPane.showMessageDialog(null, "Item qty added scuccessfully \n Item_code : " + item_code + " previous qty : " + available_qty + " after_update :  " + qty);
            } else {
                JOptionPane.showMessageDialog(null, "Qty Update failure");
            }

        } else {
            System.out.println("User canceled / closed the dialog, result = " + result);
        }
    }//GEN-LAST:event_jButton19ActionPerformed

    private void jLabel79MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel79MouseClicked
        AdminConfirmation confirmation = AdminConfirmation.getInstance();
        boolean output = confirmation.presentDialog();
        if (output) {
            ViewManipulation.changePanel(MainChangeFrame, settings_panel);

        } else {
            JOptionPane.showMessageDialog(null, "Sorry you are not authorized", "Authorization Failed", 0);
        }
    }//GEN-LAST:event_jLabel79MouseClicked

    private void jButton20ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton20ActionPerformed
//        settings_qty_edit_itemNo_combo.removeAllItems(); 
//        ViewManipulation.changePanel(settings_sub_panel, settings_sub_edit_panel);               
//        DataManipulation dm = new DataManipulation(connector);
//        dm.getRecords("items", "item_code", settings_qty_edit_itemNo_combo);
//        AutoCompleteDecorator.decorate(settings_qty_edit_itemNo_combo);

        ViewManipulation.changePanel(settings_sub_panel, settings_sub_edit_panel);
        EditQty.getInstance().setFields(settings_qty_edit_itemNo_combo, settings_qty_edit_desctiption_lbl, settings_qty_edit_qty_lbl, settings_qty_edit_qty_txt, connector);

    }//GEN-LAST:event_jButton20ActionPerformed

    private void settings_qty_edit_qty_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settings_qty_edit_qty_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_settings_qty_edit_qty_txtActionPerformed

    private void settings_qty_edit_itemNo_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_settings_qty_edit_itemNo_comboActionPerformed
//        String item_no = String.valueOf(settings_qty_edit_itemNo_combo.getSelectedItem());
//        if(item_no != null){
//            String description = connector.getRelavantRecord("items", "description", "item_code", item_no);
//            String stock = connector.getRelavantRecord("items", "stock", "item_code", item_no);
//            settings_qty_edit_desctiption_lbl.setText(description);
//            settings_qty_edit_qty_lbl.setText(stock);
//        }
    }//GEN-LAST:event_settings_qty_edit_itemNo_comboActionPerformed

    private void jButton21ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton21ActionPerformed
        String qty = settings_qty_edit_qty_txt.getText();
        if (connector.editRecordInTable("items", "item_code", "stock", qty, String.valueOf(settings_qty_edit_itemNo_combo.getSelectedItem()))) {
            JOptionPane.showMessageDialog(null, "Qty Edited Successfully");
        } else {
            JOptionPane.showMessageDialog(null, "Qty Edit Failure");
        }
    }//GEN-LAST:event_jButton21ActionPerformed

    private void stock_item_comboActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_stock_item_comboActionPerformed
        Stock.getInstance().fillDescription();
    }//GEN-LAST:event_stock_item_comboActionPerformed

    private void jButton22ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton22ActionPerformed
        PartNumberChange.getInstance().changeItemNumber();
    }//GEN-LAST:event_jButton22ActionPerformed

    private void jButton23ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton23ActionPerformed
        ViewManipulation.changePanel(settings_sub_panel, part_number_change_panel);
        PartNumberChange.getInstance().setFields(change_item_combo, changed_item_txt, connector);
    }//GEN-LAST:event_jButton23ActionPerformed

    private void sales_addPrecent_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_sales_addPrecent_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            AddPrecentage addPrecentage = AddPrecentage.getInstance();
            addPrecentage.setTable(sales_item_table);
            addPrecentage.setPrecentage(sales_addPrecent_txt.getText());
            addPrecentage.updateTable();
            addPrecentage.calculateTotal(sales_total_txt, sales_discount_txt, sales_grand_txt);

        }
    }//GEN-LAST:event_sales_addPrecent_txtKeyPressed

    private void jButton24ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton24ActionPerformed
        ViewManipulation.changePanel(settings_sub_panel, Clutch_Plate_Main);
    }//GEN-LAST:event_jButton24ActionPerformed

    private void clutch_plate_add_btnActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clutch_plate_add_btnActionPerformed
        ClutchPlateAdd clutch_plate = ClutchPlateAdd.getInstance();
        clutch_plate.setFields(clutch_plate_add_grew_txt,
                clutch_plate_add_inner_txt,
                clutch_plate_add_outer_txt,
                clutch_plate_add_plateNumber_txt,
                clutch_plate_add_table,
                connector);
        clutch_plate.add();


    }//GEN-LAST:event_clutch_plate_add_btnActionPerformed

    private void Clutch_Plate_TabbedMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Clutch_Plate_TabbedMouseClicked
        ClutchPlateSearch clutchPlateSearch = ClutchPlateSearch.getInstance();
//        clutchPlateSearch.setFields(clutch_plate_search_grew_combo,clutch_plate_search_inner_combo,clutch_plate_search_outer_combo,connector);
    }//GEN-LAST:event_Clutch_Plate_TabbedMouseClicked

    private void sales_additional_txtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sales_additional_txtActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_sales_additional_txtActionPerformed

    private void jButton26ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton26ActionPerformed
        ViewManipulation.changePanel(MainChangeFrame, SalesPanel);
    }//GEN-LAST:event_jButton26ActionPerformed

    private void item_search_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_item_search_txtKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_item_search_txtKeyPressed

    private void jButton27ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton27ActionPerformed
        String searchPhrase = item_search_txt.getText();
        if (searchPhrase.equals("")) {
            JOptionPane.showMessageDialog(null, "You should enter value for search an item");
        } else {
            ItemSearch search = ItemSearch.getInstance();
            search.getSearchResults(connector, itemSearchTable, item_search_category_combo, searchPhrase, item_search_checkbox.isSelected());
        }
    }//GEN-LAST:event_jButton27ActionPerformed

    private void item_search_checkboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_item_search_checkboxActionPerformed
        if (item_search_checkbox.isSelected()) {
            item_search_category_combo.setEnabled(true);
        } else {
            item_search_category_combo.setEnabled(false);
        }
    }//GEN-LAST:event_item_search_checkboxActionPerformed

    private void user_search_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_user_search_txtKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_user_search_txtKeyPressed

    private void jButton28ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton28ActionPerformed
        ViewManipulation.changePanel(MainChangeFrame, SalesPanel);
    }//GEN-LAST:event_jButton28ActionPerformed

    private void jButton29ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton29ActionPerformed
        String searchPhrase = user_search_txt.getText();
        if (searchPhrase.equals("")) {
            JOptionPane.showMessageDialog(null, "You should enter value for search an item");
        } else {
            UserSearch search = UserSearch.getInstance();
            search.getSearchResults(connector, userSearchTable, searchPhrase);
        }
    }//GEN-LAST:event_jButton29ActionPerformed

    private void jButton25ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton25ActionPerformed
        // TODO add your handling code here:
        ViewManipulation.changePanel(MainChangeFrame, UserSearchPanel);
    }//GEN-LAST:event_jButton25ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        // TODO add your handling code here:
        String path = report_folder_path + "\\user\\user_payments.jrxml";
        Date fromDate = reports_date1_picker.getDate();
        Date toDate = reports_date2_picker.getDate();
        String fromDateString = new SimpleDateFormat("yyyy-MM-dd").format(fromDate);
        String toDateString = new SimpleDateFormat("yyyy-MM-dd").format(toDate);

        System.out.println("FRom " + fromDateString + " To " + toDateString + " id " + String.valueOf(report_userID_combo.getSelectedItem()));
        HashMap hm = new HashMap();
        hm.put("FromDate", fromDateString);
        hm.put("ToDate", toDateString);
        hm.put("userID", String.valueOf(report_userID_combo.getSelectedItem()));
        System.out.println("FromDate : " + fromDateString);
        System.out.println("FromDate : " + toDateString);

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
    }//GEN-LAST:event_jButton8ActionPerformed

    private void add_item_location_txtKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_add_item_location_txtKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            add_item_unit_combo.requestFocusInWindow();
        }
    }//GEN-LAST:event_add_item_location_txtKeyPressed

    private void add_item_vehicle_txtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_add_item_vehicle_txtFocusLost
        // TODO add your handling code here:
        ItemAdd.getInstance().generateDescription();
    }//GEN-LAST:event_add_item_vehicle_txtFocusLost

    private void add_item_brand_txtFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_add_item_brand_txtFocusLost
        // TODO add your handling code here:
        ItemAdd.getInstance().generateDescription();
    }//GEN-LAST:event_add_item_brand_txtFocusLost

    private void jButton30ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton30ActionPerformed

        String path = report_folder_path + "\\user\\part_payments.jrxml";
        System.out.println("path" + path);
        Timestamp toDate = new Timestamp(reports_date2_picker.getDate().getTime());
        Timestamp fromDate = new Timestamp(reports_date1_picker.getDate().getTime());
        String fromDateString = String.valueOf(fromDate);
        String toDateString = String.valueOf(toDate);

        HashMap hm = new HashMap();
        hm.put("FromDate", fromDateString);
        hm.put("ToDate", toDateString);
        System.out.println("FromDate : " + fromDateString);
        System.out.println("FromDate : " + toDateString);

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
    }//GEN-LAST:event_jButton30ActionPerformed

    private void jButton31ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton31ActionPerformed
        // TODO add your handling code here:
        String path = report_folder_path + "\\user\\summary.jrxml";
        System.out.println("path" + path);
//        Calendar c = Calendar.getInstance();
//        Timestamp toDate = new Timestamp(c.getTimeInMillis());
//        c.add(Calendar.YEAR,-1);
        Timestamp toDate = new Timestamp(reports_date2_picker.getDate().getTime());

        Timestamp fromDate = new Timestamp(reports_date1_picker.getDate().getTime());
        String fromDateString = String.valueOf(fromDate);
        String toDateString = String.valueOf(toDate);

        HashMap hm = new HashMap();
        hm.put("FromDate", fromDateString);
        hm.put("ToDate", toDateString);
        System.out.println("FromDate : " + fromDateString);
        System.out.println("FromDate : " + toDateString);

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
    }//GEN-LAST:event_jButton31ActionPerformed

    private void jButton32ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton32ActionPerformed
        String path = report_folder_path + "\\purchasing\\invoice\\purchase_invoice.jrxml";
        HashMap hm = new HashMap();
        hm.put("invoice_id", String.valueOf(report_purchasing_invoice_no.getSelectedItem()));
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
    }//GEN-LAST:event_jButton32ActionPerformed

    private void report_purchasing_invoice_noActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_report_purchasing_invoice_noActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_report_purchasing_invoice_noActionPerformed

    private void jTabbedPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTabbedPane1MouseClicked
       DataManipulation dm = new DataManipulation(connector);
       dm.getRecords("purchaising", "invoice_id", report_purchasing_invoice_no);
       MyCombo autocombo = new MyCombo();
       autocombo.setSearchableCombo(report_purchasing_invoice_no, true, "No Result Found");
    }//GEN-LAST:event_jTabbedPane1MouseClicked

    private void jButton12ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton12ActionPerformed
         String path = report_folder_path + "\\item\\returns\\all_returns.jrxml";
        HashMap hm = new HashMap();
        hm.put("item_code", String.valueOf(report_item_combo.getSelectedItem()));
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
    }//GEN-LAST:event_jButton12ActionPerformed

    public void FillBill(String invoiceID) {
        ArrayList list = connector.readRow("orders", "orderID", invoiceID);
        bill_invoiceValue_txt.setText(String.valueOf(list.get(3)));
        Bill_date_txt.setText(String.valueOf(list.get(4)));

    }

    /**
     * @param args the command line arguments
     */
//    public static void main(String args[]) {
//        /* Set the Nimbus look and feel */
//        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
//        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
//         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
//         */
//        try {
//            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
//                if ("Nimbus".equals(info.getName())) {
//                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
//                    break;
//                }
//            }
//        } catch (ClassNotFoundException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (InstantiationException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (IllegalAccessException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
//            java.util.logging.Logger.getLogger(MainFrame.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
//        }
//        //</editor-fold>
//
//        /* Create and display the form */
//        java.awt.EventQueue.invokeLater(new Runnable() {
//            public void run() {
//                new MainFrame().setVisible(true);   
//                
//            }
//        });
//    }
    public String generateUserID() {
        String lastID = connector.retreveLastRecord("users", "userID", "addedDate");
        String parts[] = lastID.split("U");
        int currentID = Integer.parseInt(parts[1]);
        int nextID = ++currentID;

        DecimalFormat formatter = new DecimalFormat("000");
        String idFormatted = formatter.format(nextID);

        return "U" + idFormatted;
    }

    public void makeAllSalesComponents(boolean b) {
        sales_CID_combo.setEnabled(b);
        sales_CName_combo.setEnabled(b);
        sales_InvoiceID_txt.setEnabled(b);
        sales_itemno_combo.setEnabled(b);
        sales_item_name_combo.setEnabled(b);
        sales_item_table.setEnabled(b);
        sales_qty_Txt.setEnabled(b);
        sales_available_qty_txt.setEnabled(b);
        sales_total_txt.setEnabled(b);
        sales_discount_txt.setEnabled(b);
        sales_grand_txt.setEnabled(b);
        sales_halfPay_check.setEnabled(b);
        sales_halfPay_creditTxt.setEnabled(b);
        sales_halfPay_txt.setEnabled(b);
        sales_additional_txt.setEnabled(b);
    }

    public void calculatetotal() {
        TableModel model = sales_item_table.getModel();
        double total = 0;
        for (int i = 0; i < model.getRowCount(); i++) {
            String t1 = String.valueOf(model.getValueAt(i, 5));
            total += Double.parseDouble(t1);

        }
        BigDecimal bd = new BigDecimal(total);
        bd = bd.setScale(2, RoundingMode.HALF_UP);

        total = bd.doubleValue();
        sales_total_txt.setText(String.valueOf(total));
        calculateGrandTotal();
    }

    public void calculateGrandTotal() {
        double total = Double.parseDouble(sales_total_txt.getText());
        double discount = Double.parseDouble(sales_discount_txt.getText());
        double grand = total - (total * (discount / 100));

        sales_grand_txt.setText(Rounding.RoundTo5(grand, true));
    }

    public static double round(double value, int places) {
        if (places < 0) {
            throw new IllegalArgumentException();
        }

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }

    public void emptyFieldsInSales() {
//        sales_InvoiceID_txt.setText("");
//        sales_CID_combo.setSelectedItem("");
//        sales_CName_combo.setSelectedItem("");
//        sales_itemno_combo.setSelectedItem("");
//        sales_item_name_combo.setSelectedItem("");
        sales_qty_Txt.setText("");
//        sales_available_qty_txt.setText("");
        sales_total_txt.setText("");
        sales_grand_txt.setText("");
        sales_discount_txt.setText("");
        ViewManipulation.emptyTable(sales_item_table);

        sales_halfPay_check.setSelected(false);
        sales_halfPay_panel.setVisible(false);

    }

    public void newBillPay() {
        AutoCompleteDecorator.decorate(bill_InvoiceID_combo);
        AutoCompleteDecorator.decorate(bill_name_combo);
        AutoCompleteDecorator.decorate(bill_userID_combo);

        bill_userID_combo.removeAllItems();
        bill_name_combo.removeAllItems();
        bill_InvoiceID_combo.removeAllItems();

        manipulation.getRecords("customers", "customer_code", bill_userID_combo);
        manipulation.getRecords("customers", "name", bill_name_combo);
        manipulation.getRecordsWithCondtion("invoices", "invoice_id", "status", "0", bill_InvoiceID_combo);

    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel AddItemPanel;
    private javax.swing.JLabel AddProductLabel;
    private javax.swing.JLabel AddUserLabel;
    private javax.swing.JPanel AddUserPannel;
    private javax.swing.JPanel AdditionalTextPannel;
    private javax.swing.JTextField AddressTxt;
    private javax.swing.JTextField AddressTxt1;
    private javax.swing.JTextField Bill_date_txt;
    private javax.swing.JTable Bill_table;
    private javax.swing.JPanel Clutch_Plate_Add;
    private javax.swing.JPanel Clutch_Plate_Edit;
    private javax.swing.JPanel Clutch_Plate_Main;
    private javax.swing.JPanel Clutch_Plate_Search;
    private javax.swing.JTabbedPane Clutch_Plate_Tabbed;
    private javax.swing.JPanel ItemSearchPanel;
    private javax.swing.JPanel MainChangeFrame;
    private javax.swing.JPanel MenuBar;
    private javax.swing.JTextField NameTxt;
    private javax.swing.JPanel Purchaising_base_panel;
    private javax.swing.JLabel ReportLabel;
    private javax.swing.JPanel ReportPanel;
    private javax.swing.JLabel Return_label;
    private javax.swing.JLabel SalesLabel;
    private javax.swing.JPanel SalesPanel;
    private javax.swing.JPanel SalesReturnPanel;
    private javax.swing.JPanel SettingsPanel;
    private javax.swing.JTextField TelephoneTxt;
    private javax.swing.JPanel TitlePanel;
    private javax.swing.JPanel UserPanel;
    private javax.swing.JPanel UserPanel1;
    private javax.swing.JPanel UserPanel2;
    private javax.swing.JPanel UserSearchPanel;
    private javax.swing.JTextField add_itemNo_txt;
    private javax.swing.JTextField add_item_brand_txt;
    private javax.swing.JButton add_item_cancel_btn;
    private javax.swing.JComboBox<String> add_item_category_combo;
    private javax.swing.JTextField add_item_desc_txt;
    private javax.swing.JTextField add_item_location_txt;
    private javax.swing.JButton add_item_save_btn;
    private javax.swing.JComboBox<String> add_item_unit_combo;
    private javax.swing.JTextField add_item_vehicle_txt;
    private javax.swing.JComboBox<String> bill_InvoiceID_combo;
    private javax.swing.JComboBox<String> bill_bank_comnbo;
    private com.toedter.calendar.JDateChooser bill_cash_datePicker;
    private com.toedter.calendar.JDateChooser bill_chequeDatePicker;
    private javax.swing.JTextField bill_chequeNo_txt;
    private javax.swing.JPanel bill_cheque_number;
    private javax.swing.JRadioButton bill_chooseDate;
    private javax.swing.JRadioButton bill_currentDate;
    private javax.swing.JTextField bill_invoiceValue_txt;
    private javax.swing.JComboBox<String> bill_name_combo;
    private javax.swing.JPanel bill_pay_panel;
    private javax.swing.JButton bill_pay_select_btn;
    private javax.swing.JTextField bill_payable_txt;
    private javax.swing.JTextField bill_paying_txt;
    private javax.swing.JTabbedPane bill_paymentPane;
    private javax.swing.JLabel bill_return_label;
    private javax.swing.JComboBox<String> bill_userID_combo;
    private javax.swing.JButton cancelBtn;
    private javax.swing.JButton cashPay;
    private javax.swing.JComboBox<String> change_item_combo;
    private javax.swing.JTextField changed_item_txt;
    private javax.swing.JButton chequePay;
    private javax.swing.JLabel clock_txt;
    private javax.swing.JButton clutch_plate_add_btn;
    private javax.swing.JTextField clutch_plate_add_grew_txt;
    private javax.swing.JTextField clutch_plate_add_inner_txt;
    private javax.swing.JTextField clutch_plate_add_outer_txt;
    private javax.swing.JTextField clutch_plate_add_plateNumber_txt;
    private javax.swing.JTable clutch_plate_add_table;
    private javax.swing.JComboBox<String> clutch_plate_search_grew_combo;
    private javax.swing.JComboBox<String> clutch_plate_search_inner_combo;
    private javax.swing.JComboBox<String> clutch_plate_search_outer_combo;
    private javax.swing.JLabel date_txt;
    private javax.swing.JButton editBtn;
    private javax.swing.JButton itemCancelBtn1;
    private javax.swing.JTable itemSearchTable;
    private javax.swing.JPanel itemWisePanel;
    private javax.swing.JButton item_add_edit_btn;
    private javax.swing.JButton item_add_new_btn;
    private javax.swing.JLabel item_date1_label;
    private javax.swing.JLabel item_date2_label;
    private javax.swing.JCheckBox item_report_checkBox;
    private javax.swing.JComboBox<String> item_search_category_combo;
    private javax.swing.JCheckBox item_search_checkbox;
    private javax.swing.JTextField item_search_txt;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton10;
    private javax.swing.JButton jButton11;
    private javax.swing.JButton jButton12;
    private javax.swing.JButton jButton13;
    private javax.swing.JButton jButton14;
    private javax.swing.JButton jButton15;
    private javax.swing.JButton jButton16;
    private javax.swing.JButton jButton17;
    private javax.swing.JButton jButton18;
    private javax.swing.JButton jButton19;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton20;
    private javax.swing.JButton jButton21;
    private javax.swing.JButton jButton22;
    private javax.swing.JButton jButton23;
    private javax.swing.JButton jButton24;
    private javax.swing.JButton jButton25;
    private javax.swing.JButton jButton26;
    private javax.swing.JButton jButton27;
    private javax.swing.JButton jButton28;
    private javax.swing.JButton jButton29;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton30;
    private javax.swing.JButton jButton31;
    private javax.swing.JButton jButton32;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private com.toedter.calendar.JDateChooser jDateChooser1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel63;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JLabel jLabel72;
    private javax.swing.JLabel jLabel73;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JLabel jLabel75;
    private javax.swing.JLabel jLabel76;
    private javax.swing.JLabel jLabel77;
    private javax.swing.JLabel jLabel78;
    private javax.swing.JLabel jLabel79;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel80;
    private javax.swing.JLabel jLabel81;
    private javax.swing.JLabel jLabel82;
    private javax.swing.JLabel jLabel83;
    private javax.swing.JLabel jLabel84;
    private javax.swing.JLabel jLabel85;
    private javax.swing.JLabel jLabel86;
    private javax.swing.JLabel jLabel87;
    private javax.swing.JLabel jLabel88;
    private javax.swing.JLabel jLabel89;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabel90;
    private javax.swing.JLabel jLabel91;
    private javax.swing.JLabel jLabel92;
    private javax.swing.JLabel jLabel93;
    private javax.swing.JLabel jLabel94;
    private javax.swing.JLabel jLabel95;
    private javax.swing.JLabel jLabel96;
    private javax.swing.JLabel jLabel97;
    private javax.swing.JLabel jLabel98;
    private javax.swing.JLabel jLabel99;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JScrollPane jScrollPane8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton newBtn;
    private javax.swing.JPanel part_number_change_panel;
    private javax.swing.JLabel payBillsLabel;
    private javax.swing.JTextField purchaise_invoiceno_txt;
    private javax.swing.JComboBox<String> purchaise_retailer_combo;
    private javax.swing.JPanel purchaising_main_panel;
    private javax.swing.JPanel purchasingPanel;
    private javax.swing.JComboBox<String> report_item_combo;
    private javax.swing.JButton report_items_btn;
    private javax.swing.JComboBox<String> report_name_combo;
    private javax.swing.JComboBox<String> report_purchasing_invoice_no;
    private javax.swing.JComboBox<String> report_userID_combo;
    private javax.swing.JButton reports_customer_btn1;
    private javax.swing.JButton reports_customer_btn2;
    private com.toedter.calendar.JDateChooser reports_date1_picker;
    private com.toedter.calendar.JDateChooser reports_date2_picker;
    private javax.swing.JButton reports_dayEnd_btn;
    private javax.swing.JTextField retailer_address_txt;
    private javax.swing.JTextField retailer_contact_txt;
    private javax.swing.JTextField retailer_name_txt;
    private javax.swing.JPanel retailer_panel;
    private javax.swing.JButton return_button1;
    private javax.swing.JButton return_calculation_btn;
    private javax.swing.JButton return_cancel_btn;
    private com.toedter.calendar.JDateChooser return_from_picker;
    private javax.swing.JComboBox<String> return_invoiceID_combo;
    private javax.swing.JTable return_invoiceSearch_table;
    private javax.swing.JTable return_item_table;
    private javax.swing.JRadioButton return_no;
    private javax.swing.JComboBox<String> return_reason_combo;
    private javax.swing.JButton return_search_invoice;
    private javax.swing.JComboBox<String> return_search_item_combo;
    private com.toedter.calendar.JDateChooser return_to_picker;
    private javax.swing.JLabel return_total_txt;
    private javax.swing.JComboBox<String> return_userID_combo;
    private javax.swing.JComboBox<String> return_userName_combo;
    private javax.swing.JRadioButton return_yes;
    private javax.swing.JScrollPane returns_item_table;
    private javax.swing.JPanel returns_search_panel;
    private javax.swing.JComboBox<String> sales_CID_combo;
    private javax.swing.JComboBox<String> sales_CName_combo;
    private javax.swing.JTextField sales_InvoiceID_txt;
    private javax.swing.JTextField sales_addPrecent_txt;
    private javax.swing.JTextField sales_additional_txt;
    private javax.swing.JTextField sales_available_qty_txt;
    private javax.swing.JButton sales_cancel_btn;
    private javax.swing.JTextField sales_discount_txt;
    private javax.swing.JTextField sales_grand_txt;
    private javax.swing.JCheckBox sales_halfPay_check;
    private javax.swing.JTextField sales_halfPay_creditTxt;
    private javax.swing.JPanel sales_halfPay_panel;
    private javax.swing.JTextField sales_halfPay_txt;
    private javax.swing.JComboBox<String> sales_item_name_combo;
    private javax.swing.JTable sales_item_table;
    private javax.swing.JComboBox<String> sales_itemno_combo;
    private javax.swing.JButton sales_new_btn;
    private javax.swing.JButton sales_print_btn;
    private javax.swing.JTextField sales_qty_Txt;
    private javax.swing.JButton sales_remove_btn;
    private javax.swing.JPanel sales_return_subPanel;
    private javax.swing.JButton sales_save_btn;
    private javax.swing.JButton sales_searchI_btn1;
    private javax.swing.JButton sales_search_user_btn;
    private javax.swing.JTextField sales_total_txt;
    private javax.swing.JTextField sales_unit_Txt;
    private javax.swing.JButton saveBtn;
    private javax.swing.JToggleButton selectAll_button;
    private javax.swing.JPanel settings_panel;
    private javax.swing.JLabel settings_qty_edit_desctiption_lbl;
    private javax.swing.JComboBox<String> settings_qty_edit_itemNo_combo;
    private javax.swing.JLabel settings_qty_edit_qty_lbl;
    private javax.swing.JTextField settings_qty_edit_qty_txt;
    private javax.swing.JPanel settings_sub_edit_panel;
    private javax.swing.JPanel settings_sub_panel;
    private javax.swing.JLabel stock_availableQty_lbl;
    private javax.swing.JLabel stock_c_selling_lbl;
    private javax.swing.JPanel stock_count_panel;
    private javax.swing.JLabel stock_description_lbl;
    private javax.swing.JComboBox<String> stock_item_combo;
    private javax.swing.JTable stock_item_table;
    private javax.swing.JTextField stock_qty_txt;
    private javax.swing.JLabel stock_selling_lbl;
    private javax.swing.JTextField stock_selling_txt;
    private javax.swing.JButton stock_update_btn;
    private javax.swing.JLabel updateQtylbl;
    private javax.swing.JLabel update_available_lbl;
    private javax.swing.JTextField update_billPrice_txt;
    private javax.swing.JTextField update_costP_txt;
    private javax.swing.JLabel update_cost_lbl;
    private javax.swing.JTextField update_description_txt;
    private javax.swing.JComboBox<String> update_itemNo_combo;
    private javax.swing.JTextField update_qty_txt;
    private javax.swing.JButton update_save_btn;
    private javax.swing.JTextField update_sellingP_txt;
    private javax.swing.JLabel update_selling_lbl;
    private javax.swing.JTable userSearchTable;
    private javax.swing.JPanel userWisePanel;
    private javax.swing.JTextField user_search_txt;
    // End of variables declaration//GEN-END:variables

}
