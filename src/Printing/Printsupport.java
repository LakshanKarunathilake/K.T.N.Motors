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
    public static String title[] = new String[]{"Item", "Rate","Qty", "Total"};
    
    public static ArrayList<String> list = new ArrayList<>();

    public static void setItems(Object[][] printitem) {
        Object data[][] = printitem;
        DefaultTableModel model = new DefaultTableModel();
        //assume jtable has 4 columns.
        model.addColumn(title[0]);
        model.addColumn(title[1]);
        model.addColumn(title[2]);
        model.addColumn(title[3]);

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

        System.out.println("Check Passed.");
    }

    public Object[][] getTableData(JTable table) {
        int itemcount = table.getRowCount();
        System.out.println("Item Count:" + itemcount);

        DefaultTableModel dtm = (DefaultTableModel) table.getModel();
        int nRow = dtm.getRowCount(), nCol = dtm.getColumnCount();
        Object[][] tableData = new Object[nRow][nCol];
        int l = 0;
        if (itemcount == nRow) //check is there any data loss.
            
        {
            for (int i = 0; i < nRow; i++) {
                l=0;
                for (int j = 0; j < nCol; j++) {
                    if(j!=1 && j!=2){
                        tableData[i][l] = dtm.getValueAt(i, j); 
                        l++;//pass data into object array.
                        System.out.println("I value :"+i+" J val: "+j+" l val: "+l);
                    }
                    
                    
                }
            }
            if (tableData.length != itemcount) {                      //check for data losses in object array
                getTableData(table);                                  //recursively call method back to collect data
            }
            System.out.println("Data check passed");
        } else {
            //collecting data again because of data loss.
            getTableData(table);
        }
        return tableData;                                       //return object array with data.
    }
    
    public void setMetaData(ArrayList<String> list){
        this.list = list;
    }
    

    public static PageFormat getPageFormat(PrinterJob pj) {
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

//                try {
//                    /*
//                         * Draw Image*
//                           assume that printing reciept has logo on top 
//                         * that logo image is in .gif format .png also support
//                         * image resolution is width 100px and height 50px
//                         * image located in root--->image folder 
//                     */
//                    int x = 100;                                        //print start at 100 on x axies
//                    int y = 10;                                          //print start at 10 on y axies
//                    int imagewidth = 100;
//                    int imageheight = 50;
//                    BufferedImage read = ImageIO.read(getClass().getResource("/image/logo.png"));
//                    g2d.drawImage(read, x, y, imagewidth, imageheight, null);         //draw image
//                    g2d.drawLine(10, y + 60, 180, y + 60);                          //draw line
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
                try {
                    /*Draw Header*/
                    int y = 20;
                    Font heading = new Font("Arial",Font.BOLD,15);
                    g2d.setFont(heading);
                    g2d.drawString("K.T.N.Motors", 60, y);
                   
                    Font subheading = new Font("Arial",Font.PLAIN,8);
                    g2d.setFont(subheading);
                    g2d.drawString("No-152,Aluthgama Road,Mathugama", 30, y+=10);
                    g2d.drawString("Tel:0344939958,0342248844", 35, y +=10);                 //shift a line by adding 10 to y value
                    g2d.setFont(font);
                    g2d.drawString(now(), 10, y += 10);                                //print date
                    g2d.drawString("Invoice :", 10, y += 10);
                    g2d.drawString(list.get(0), 55, y);
                    g2d.drawString("Customer :", 10, y += 10);
                    g2d.drawString("Cash",65,y);

                    /*Draw Colums*/
                    
                    g2d.drawLine(0, y += 5, 200, y);
                    g2d.drawString(title[0], 0, y += 15);
                    g2d.drawString(title[1], 110, y);
                    g2d.drawString(title[2], 145, y );
                    g2d.drawString(title[3], 170, y);
                    g2d.drawLine(0, y +=5, 200, y );

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

                        cH = (y +10) + (10 * i);                             //shifting drawing line

                        g2d.drawString(item, 0, cH);
                        g2d.drawString(unit, 105, cH);
                        g2d.drawString(qty, 150, cH);
                        g2d.drawString(total, 165, cH);

                    }
                    y =cH;
                    /*Footer*/
                    g2d.drawLine(0, y +=5, 200, y );
                    g2d.drawString("Total:", 100, y += 10);
                    g2d.setFont(font_bold);
                    String total = list.get(1);
                    total = String.format("%-6s", total);
                    g2d.drawString(total,160,y);
                    
                    g2d.setFont(font);
                    g2d.drawString("discount:", 100, y += 10);
                    g2d.setFont(font_bold);
                    String discount = list.get(2)+"%";
                    discount = String.format("%8s", discount);
                    g2d.drawString(discount,160,y);
                    
                    g2d.drawLine(160, y +=5, 200, y );
                    
                    g2d.setFont(font);
                    g2d.drawString("Grand Total:", 100, y += 10);
                    String grand= list.get(3);
                    g2d.setFont(font_bold);
                    grand = String.format("%-6s", grand);
                    g2d.drawString(grand, 160, y);
                    
                    g2d.drawLine(0, y +=5, 200, y );
                    
                    font = new Font("Arial", Font.BOLD, 12);                  //changed font size
                    g2d.setFont(font);
                    g2d.drawString("Thank You Come Again", 10,y += 20);
                    
                    
                    //end of the reciept
                } catch (Exception r) {
                    r.printStackTrace();
                }

                result = PAGE_EXISTS;
            }
            return result;
        }
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
