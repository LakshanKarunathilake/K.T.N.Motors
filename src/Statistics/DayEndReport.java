/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import DBController.DataBaseConnector;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author lakshan
 */
public class DayEndReport {
    
    private String date = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private String date_begin;
    private String date_end;
    private DataBaseConnector connector;
    
    private static DayEndReport dayEndReport;
    
    private DayEndReport(){
        connector = DataBaseConnector.getInstance();  
    }
    
    public static DayEndReport getInstance(){
        if(dayEndReport == null)
            dayEndReport = new DayEndReport();
        return dayEndReport;
    }
    
    public String getCashSales(){ 
        setBeginAndEndDate();
        String sql = "SELECT SUM(grandTotal) as val FROM invoices WHERE invoices.orderDate BETWEEN '"+date_begin+"' AND '"+date_end+"' AND Status like '1'";
        System.out.println("sql : "+sql);
        return returningVal(sql);
    }
    
    public void setDate(String date){
        this.date = date;
        setBeginAndEndDate();
    }
    
    public void setBeginAndEndDate(){
        date_begin = date + " 00:00:00";
        date_end = date + " 23:59:00";
    }
    
    public String getCreditSales(){
        String sql = "SELECT SUM(grandTotal) as val FROM invoices WHERE invoices.orderDate BETWEEN '"+date_begin+"' AND '"+date_end+"' AND Status like '0'";
        return returningVal(sql);
    }
    
    public String getHalfPayments(){
        String sql = "SELECT SUM(cash_paid) as val FROM invoices WHERE invoices.orderDate BETWEEN '"+date_begin+"' AND '"+date_end+"' AND Status like '0'";
        return returningVal(sql);
    }
    
    public String getCashReturns(){
        String sql = "SELECT SUM(sales_return.amount) as val FROM sales_return WHERE sales_return.date BETWEEN '"+date_begin+"' AND '"+date_end+"' AND sales_return.invoice_id in(SELECT invoices.invoice_id FROM invoices WHERE invoices.Status like '1')";
        return returningVal(sql);
    }
    
    public String getCreditReturns(){
        String sql = "SELECT SUM(sales_return.amount) as val FROM sales_return WHERE sales_return.date BETWEEN '"+date_begin+"' AND '"+date_end+"' AND sales_return.invoice_id in(SELECT invoices.invoice_id FROM invoices WHERE invoices.Status like '0')";
        return returningVal(sql);
    }
    
    public String getPartPayments(){
        String sql = "SELECT sum(credit_payments.amount) as val FROM credit_payments WHERE credit_payments.paid_date BETWEEN '"+date_begin+"' AND '"+date_end+"'";
        return  returningVal(sql);
    }
    
   
    
    public String returningVal(String sql){
        
        ArrayList data = connector.sqlExecutionaArray(sql, "val");
        System.out.println("SQL :"+sql);
        String cash_sales="0.00";
        if(data.size()>0){
            cash_sales = String.valueOf(data.get(0)); 
        }
        return cash_sales;
    }
}
