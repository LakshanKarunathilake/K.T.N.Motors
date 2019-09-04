/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Printing;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.Printable;
import static java.awt.print.Printable.NO_SUCH_PAGE;
import static java.awt.print.Printable.PAGE_EXISTS;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import javax.imageio.ImageIO;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

/**
 *
 * @author All Open source developers
 * @version 1.0.0.0
 * @since 2014/12/22
 */
/*This Printsupport java class was implemented to get printout.
* This class was specially designed to print a Jtable content to a paper.
* Specially this class formated to print 7cm width paper.
* Generally for pos thermel printer.
* Free to customize this source code as you want.
* Illustration of basic invoice is in this code.
* demo by gayan liyanaarachchi
 
 */
public class Printsupport {

    static JTable itemsTable;
    public static int total_item_count = 0;
    public static final String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss a";
    public static String title[] = new String[]{"Item", "Rate", "Qty", "Total"};
    public static String Credit_bill_title[] = new String[]{"Part Number", "Description", "Qty", "Rating", "Amount"};

    public static ArrayList<String> list = new ArrayList<>();
    public static HashMap<String, String> hashmap;
    private static ArrayList<String> UserDetails;

    public static void setItems(Object[][] printitem, String type) {
        Object data[][] = printitem;
        DefaultTableModel model = new DefaultTableModel();
        //assume jtable has 4 columns.
        if (type.equals("cash")) {
            for (int i = 0; i < title.length; i++) {
                model.addColumn(title[i]);
            }
        }else{
            for (int i = 0; i < Credit_bill_title.length; i++) {
                model.addColumn(Credit_bill_title[i]);
            }
        }
//        model.addColumn(title[0]);
//        model.addColumn(title[1]);
//        model.addColumn(title[2]);
//        model.addColumn(title[3]);

        int rowcount = printitem.length;

        addtomodel(model, data, rowcount);

        itemsTable = new JTable(model);
    }

    public static void addtomodel(DefaultTableModel model, Object[][] data, int rowcount) {
        int count = 0;
        while (count < rowcount) {
            model.addRow(data[count]);
            count++;
        }
        if (model.getRowCount() != rowcount) {
            addtomodel(model, data, rowcount);
        }

    }

    public Object[][] getTableData(JTable table, String type) {
        int itemcount = table.getRowCount();
        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
        setTotalItemCount(nRow);
        Object[][] tableData = new Object[nRow][nCol];
        int l = 0;
        if (itemcount == nRow) //check is there any data loss.
        {
            for (int i = 0; i < nRow; i++) {
                l = 0;
                for (int j = 0; j < nCol; j++) {
                    if (j != 2 && !(j == 1 && type == "cash")) {
                        tableData[i][l] = dtm.getValueAt(i, j);
                        l++;//pass data into object array.
                    }
                }
            }
            if (tableData.length != itemcount) {                      //check for data losses in object array
                getTableData(table, type);                                  //recursively call method back to collect data
            }
        } else {
            //collecting data again because of data loss.
            getTableData(table, type);
        }
        return tableData;                                       //return object array with data.
    }

    public void setMetaData(ArrayList<String> list) {
        this.list = list;
    }

    public void setMetaData(HashMap hashmap) {
        this.hashmap = hashmap;
    }

    public static void setTotalItemCount(int numberOfRows) {
        total_item_count = numberOfRows;
    }

    public static PageFormat getCashPageFormat(PrinterJob pj) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();

        double middleHeight = total_item_count * 1.0;  //dynamic----->change with the row count of jtable
        double headerHeight = 5.5;                  //fixed----->but can be mod
        double footerHeight = 5.5;                  //fixed----->but can be mod

        double width = convert_CM_To_PPI(8);      //printer know only point per inch.default value is 72ppi
        double height = convert_CM_To_PPI(headerHeight + middleHeight + footerHeight);
        paper.setSize(width, height);
        paper.setImageableArea(
                convert_CM_To_PPI(0.25),
                convert_CM_To_PPI(0.5),
                width - convert_CM_To_PPI(0.35),
                height - convert_CM_To_PPI(1));   //define boarder size    after that print area width is about 180 points

        pf.setOrientation(PageFormat.PORTRAIT);           //select orientation portrait or landscape but for this time portrait
        pf.setPaper(paper);

        return pf;
    }

    protected static double convert_CM_To_PPI(double cm) {
        return toPPI(cm * 0.393600787);
    }

    protected static double toPPI(double inch) {
        return inch * 72d;
    }

    public static String now() {
//get current date and time as a String output   
        Calendar cal = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
        return sdf.format(cal.getTime());

    }

    public static class MyPrintable implements Printable {

        @Override
        public int print(Graphics graphics, PageFormat pageFormat,
                int pageIndex) throws PrinterException {
            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {
                Graphics2D g2d = (Graphics2D) graphics;

                double width = pageFormat.getImageableWidth();
                double height = pageFormat.getImageableHeight();
                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
                Font font = new Font("Monospaced", Font.PLAIN, 8);
                Font font_bold = new Font("Monospaced", Font.BOLD, 9);
                g2d.setFont(font);
                try {
                    /*Draw Header*/
                    int y = 20;
                    Font heading = new Font("Arial", Font.BOLD, 15);
                    g2d.setFont(heading);
                    g2d.drawString("K.T.N.Motors", 60, y);

                    Font subheading = new Font("Arial", Font.PLAIN, 8);
                    g2d.setFont(subheading);
                    g2d.drawString("No-152,Aluthgama Road,Mathugama", 30, y += 10);
                    g2d.drawString("Tel:0344939958,0342248844", 45, y += 10);                 //shift a line by adding 10 to y value
                    g2d.setFont(font);
                    g2d.drawString(now(), 0, y += 10);                                //print date
                    g2d.drawString("Invoice :", 0, y += 10);
                    g2d.drawString(list.get(0), 55, y);
                    g2d.drawString("Customer :", 120, y);
                    g2d.drawString("Cash", 175, y);

                    /*Draw Colums*/
                    g2d.drawLine(0, y += 5, 200, y);
                    g2d.drawString(title[0], 0, y += 15);
                    g2d.drawString(title[1], 110, y);
                    g2d.drawString(title[2], 145, y);
                    g2d.drawString(title[3], 170, y);
                    g2d.drawLine(0, y += 5, 200, y);

                    int cH = 0;
                    TableModel mod = itemsTable.getModel();

                    for (int i = 0; i < mod.getRowCount(); i++) {
                        /*Assume that all parameters are in string data type for this situation
                                 * All other premetive data types are accepted.
                         */
                        String item = mod.getValueAt(i, 0).toString();
                        item = String.format("%-20s", item);
                        String unit = mod.getValueAt(i, 1).toString();
                        unit = String.format("%-6s", unit);
                        String qty = mod.getValueAt(i, 2).toString();
                        qty = String.format("%-3s", qty);
                        String total = mod.getValueAt(i, 3).toString();
                        total = String.format("%-6s", total);

                        cH = (y + 10) + (10 * i);                             //shifting drawing line

                        g2d.drawString(item, 0, cH);
                        g2d.drawString(unit, 105, cH);
                        g2d.drawString(qty, 150, cH);
                        g2d.drawString(total, 165, cH);

                    }
                    y = cH;
                    /*Footer*/
                    g2d.drawLine(0, y += 5, 200, y);
                    g2d.drawString("Total:", 100, y += 10);
                    g2d.setFont(font_bold);
                    String total = list.get(1);
                    total = String.format("%-6s", total);
                    g2d.drawString(total, 160, y);

                    g2d.setFont(font);
                    g2d.drawString("discount:", 100, y += 10);
                    g2d.setFont(font_bold);
                    String discount = list.get(2) + "%";
                    discount = String.format("%8s", discount);
                    g2d.drawString(discount, 160, y);

                    g2d.drawLine(160, y += 5, 200, y);

                    g2d.setFont(font);
                    g2d.drawString("Grand Total:", 100, y += 10);
                    String grand = list.get(3);
                    g2d.setFont(font_bold);
                    grand = String.format("%-6s", grand);
                    g2d.drawString(grand, 160, y);

                    g2d.drawLine(0, y += 5, 200, y);

                    font = new Font("Arial", Font.BOLD, 12);                  //changed font size
                    g2d.setFont(font);
                    g2d.drawString("Thank You Come Again", 35, y += 15);

                    //end of the reciept
                } catch (Exception r) {
                    r.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
        }
    }

    public static class DayEndPrintable implements Printable {

        @Override
        public int print(Graphics graphics, PageFormat pageFormat,
                int pageIndex) throws PrinterException {
            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {
                Graphics2D g2d = (Graphics2D) graphics;

                double width = pageFormat.getImageableWidth();
                double height = pageFormat.getImageableHeight();
                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
                Font font = new Font("Monospaced", Font.PLAIN, 8);
                Font font_bold = new Font("Monospaced", Font.BOLD, 9);
                g2d.setFont(font);

                try {
                    /*Draw Header*/
                    int y = 20;
                    Font heading = new Font("Arial", Font.BOLD, 15);
                    g2d.setFont(heading);
                    g2d.drawString("K.T.N.Motors", 60, y);

                    Font subheading = new Font("Arial", Font.PLAIN, 8);
                    g2d.setFont(subheading);
                    g2d.drawString("No-152,Aluthgama Road,Mathugama", 30, y += 10);
                    g2d.drawString("Tel:0344939958,0342248844", 45, y += 10);                 //shift a line by adding 10 to y value
                    g2d.setFont(font);
                    g2d.drawString("Day End Report :", 0, y += 10);
                    g2d.drawString(now(), 80, y);                                //print date


                    /*Draw Colums*/
                    g2d.drawLine(0, y += 5, 200, y);

                    /*Start of Description*/
                    g2d.setFont(subheading);
                    g2d.drawString("Sales", 0, y += 10);
                    g2d.setFont(font);
                    g2d.drawString("Cash Sales", 10, y += 10);
                    g2d.drawString(String.format("%8s", hashmap.get("cash_sales")), 150, y);
                    g2d.drawString("Credit Sales", 10, y += 10);
                    g2d.drawString(String.format("%8s", hashmap.get("credit_sales")), 150, y);
                    g2d.drawString("(Half Payments)", 20, y += 10);
                    g2d.drawString(String.format("%8s", hashmap.get("half_payments")), 150, y);
                    g2d.drawLine(140, y += 5, 200, y);
                    g2d.drawString("Total Sales", 10, y += 10);
                    g2d.setFont(font_bold);
                    g2d.drawString(String.format("%8s", hashmap.get("total_sales")), 150, y);
                    g2d.drawLine(140, y += 2, 200, y);
                    g2d.drawLine(140, y += 2, 200, y);
                    g2d.setFont(font);
                    g2d.drawString("Total Cash Sales", 10, y += 10);
                    g2d.drawString(String.format("%8s", hashmap.get("total_cash_sales")), 150, y);

                    g2d.setFont(subheading);
                    g2d.drawString("Sales Returns", 0, y += 15);
                    g2d.setFont(font);
                    g2d.drawString("Cash Returns", 10, y += 10);
                    g2d.drawString(String.format("%8s", hashmap.get("cash_returns")), 150, y);
                    g2d.drawString("Credit Returns", 10, y += 10);
                    g2d.drawString(String.format("%8s", hashmap.get("credit_returns")), 150, y);

                    g2d.setFont(subheading);
                    g2d.drawString("Part Payments", 0, y += 15);
                    g2d.setFont(font);
                    g2d.drawString("Part Payments", 10, y += 10);
                    g2d.drawString(String.format("%8s", hashmap.get("part_payments")), 150, y);

                    g2d.drawLine(0, y += 5, 200, y);
                    //end of the reciept
                } catch (Exception r) {
                    r.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
        }
    }

    public static PageFormat getCreditPageFormat(PrinterJob pj) {
        PageFormat pf = pj.defaultPage();
        Paper paper = pf.getPaper();
        double width = convert_CM_To_PPI(14.8);      //printer know only point per inch.default value is 72ppi
        double height = convert_CM_To_PPI(21);
        paper.setSize(width, height);
        paper.setImageableArea(
                convert_CM_To_PPI(0.25),
                convert_CM_To_PPI(0.5),
                width,
                height);   //define boarder size    after that print area width is about 180 points

        pf.setOrientation(PageFormat.LANDSCAPE);           //select orientation portrait or landscape but for this time portrait
        pf.setPaper(paper);

        return pf;
    }

    public static class CreditPrintable implements Printable {

        @Override
        public int print(Graphics graphics, PageFormat pageFormat,
                int pageIndex) throws PrinterException {
            int result = NO_SUCH_PAGE;
            if (pageIndex == 0) {
                Graphics2D g2d = (Graphics2D) graphics;

                double width = pageFormat.getImageableWidth();
                double height = pageFormat.getImageableHeight();
                System.out.println("width" + width);
                System.out.println("heig" + height);
                g2d.translate((int) pageFormat.getImageableX(), (int) pageFormat.getImageableY());
                Font font = new Font("Monospaced", Font.PLAIN, 10);
                Font font_bold = new Font("Monospaced", Font.BOLD, 10);
                g2d.setFont(font);
                try {
                    /*Draw Header*/
                    int y = 20;
                    Font heading = new Font("Arial", Font.BOLD, 17);
                    g2d.setFont(heading);
                    g2d.drawString("K.T.N. Motors", 260, y);
                    Font subheading = new Font("Arial", Font.PLAIN, 8);
                    Font columnHeading = new Font("Arial", Font.PLAIN, 10);
                    g2d.setFont(subheading);
                    g2d.drawString("No-152,Aluthgama Road,Mathugama", 240, y += 10);
                    g2d.drawString("Tel: 0344939958,0342248844 Email: ktn.motos.office@gmail.com", 200, y += 10);                 //shift a line by adding 10 to y value
                    g2d.setFont(font);
                    
                    /* Printing the invoice Id and the time*/
                    g2d.drawString("Invoice ID :", 450, y+10);
                    g2d.drawString(list.get(0), 550, y+10);
                    g2d.drawString(String.format("%-20s", now()), 450, y + 20);
                    
                    ArrayList<String> userDetails = getUserDetails();
                    for (int i = 0; i < userDetails.size(); i++) {
                        System.out.println("val" + userDetails.get(i));
                        if (userDetails.get(i) != "") {
                            g2d.drawString(String.valueOf(userDetails.get(i)), 10, y += 10);
                        }
                    }
                                                    //print date

                    /*Draw Colums*/
                    g2d.drawLine(10, y += 10, 580, y);
                    g2d.setFont(columnHeading);
                    g2d.drawString(Credit_bill_title[0], 10, y += 15);
                    g2d.drawString(Credit_bill_title[1], 110, y);
                    g2d.drawString(Credit_bill_title[2], 455, y);
                    g2d.drawString(Credit_bill_title[3], 495, y);
                    g2d.drawString(Credit_bill_title[4], 540, y);
                    g2d.drawLine(10, y += 5, 580, y);
                    g2d.setFont(font);
                    int cH = 0;
                    TableModel mod = itemsTable.getModel();

                    for (int i = 0; i < mod.getRowCount(); i++) {
                        /*Assume that all parameters are in string data type for this situation
                                 * All other premetive data types are accepted.
                         */
                        System.out.println("row" + mod.getColumnCount());
                        String item = mod.getValueAt(i, 0).toString();
                        item = String.format("%-26s", item);
                        String description = mod.getValueAt(i, 1).toString();
                        description = String.format("%-40s", description);
                        String qty = mod.getValueAt(i, 3).toString();
                        qty = String.format("%-3s", qty);
                        String rate = mod.getValueAt(i, 2).toString();
                        rate = String.format("%8s", rate);
                        String amount = mod.getValueAt(i, 4).toString();
                        amount = String.format("%8s", amount);

                        cH = (y + 10) + (10 * i);                             //shifting drawing line

                        g2d.drawString(item, 10, cH);
                        g2d.drawString(description, 110, cH);
                        g2d.drawString(qty, 465, cH);
                        g2d.drawString(rate, 480, cH);
                        g2d.drawString(amount, 530, cH);

                    }
                    y = cH+200;
                    /*Footer*/
                    g2d.drawLine(10, y += 5, 580, y);
                    g2d.drawString("Total:", 450, y += 10);
                    g2d.setFont(font_bold);
                    String total = list.get(1);
                    total = String.format("%-6s", total);
                    g2d.drawString(total, 530, y);

                    g2d.setFont(font);
                    g2d.drawString("discount:", 450, y += 10);
                    g2d.setFont(font_bold);
                    String discount = list.get(2) + "%";
                    discount = String.format("%8s", discount);
                    g2d.drawString(discount, 530, y);

                    g2d.drawLine(450, y += 5, 580, y);

                    g2d.setFont(font);
                    g2d.drawString("Grand Total:", 450, y += 10);
                    String grand = list.get(3);
                    g2d.setFont(font_bold);
                    grand = String.format("%-6s", grand);
                    g2d.drawString(grand, 530, y);

                    g2d.drawLine(10, y += 5, 580, y);

                    font = new Font("Arial", Font.BOLD, 12);                  //changed font size
                    g2d.setFont(font);
                    g2d.drawString("Thank You Come Again",190, y += 15);

                    //end of the reciept
                } catch (Exception r) {
                    r.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
        }
    }

    /**
     * @return the UserDetails
     */
    public static ArrayList<String> getUserDetails() {
        return UserDetails;
    }

    /**
     * @param aUserDetails the UserDetails to set
     */
    public static void setUserDetails(ArrayList<String> aUserDetails) {
        UserDetails = aUserDetails;
    }
}
/*
 ################# THIS IS HOW TO USE THIS CLASS #######################
 
 Printsupport ps=new Printsupport();
 Object printitem [][]=ps.getTableData(jTable);
 ps.setItems(printitem);
       
 PrinterJob pj = PrinterJob.getPrinterJob();
 pj.setPrintable(new MyPrintable(),ps.getPageFormat(pj));
       try {
            pj.print();
           
            }
        catch (PrinterException ex) {
                ex.printStackTrace();
            }
 ################## JOIN TO SHARE KNOWLADGE ###########################
 
 */
