/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics.commision;

import DBController.DataBaseConnector;
import Statistics.DayEndReport;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author lakshan
 */
public class Commision {
    private String fromDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private String toDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
    private String date_begin;
    private String repName = "nimal";
    private String date_end;
    private DataBaseConnector connector;
    
    private static Commision commissionReport;
    
    private Commision(){
        connector = DataBaseConnector.getInstance(); 
    }
    public static Commision getInstance(){
        if(commissionReport == null)
            commissionReport = new Commision();
        return commissionReport;
    }
    
    public String getCashSales(){ 
        setBeginAndEndDate();
        String sql = "SELECT SUM(grandTotal) as val FROM invoices WHERE invoices.orderDate BETWEEN '"+date_begin+"' AND '"+date_end+"' AND Status like '1' AND repName like '"+repName+"'";
        System.out.println("sql : "+sql);
        return returningVal(sql);
    }
    
    public void setDate(String from,String to){
        this.fromDate = from;
        this.toDate = to;
        setBeginAndEndDate();
    }
    
    public void setBeginAndEndDate(){
        date_begin = fromDate + " 00:00:00";
        date_end = toDate + " 23:59:00";
    }
    
    public String getCreditSales(){
        String sql = "SELECT SUM(grandTotal) as val FROM invoices WHERE invoices.orderDate BETWEEN '"+date_begin+"' AND '"+date_end+"' AND Status like '0'  AND repName like '"+repName+"'";
        String credit  = returningVal(sql);
//        if(credit.equals("null"))
//            credit = "0.00";
        return credit;
    }
    
    public String getHalfPayments(){
        String sql = "SELECT SUM(cash_paid) as val FROM invoices WHERE invoices.orderDate BETWEEN '"+date_begin+"' AND '"+date_end+"' AND Status like '0' AND repName like '"+repName+"'";
        String half_payments = returningVal(sql);
        if(half_payments.equals("null"))
            half_payments = "0.00";
        return returningVal(sql);
    }
    
    public String getCashReturns(){
        String sql = "SELECT SUM(sales_return.amount) as val FROM sales_return WHERE sales_return.date BETWEEN '"+date_begin+"' AND '"+date_end+"' AND sales_return.invoice_id in(SELECT invoices.invoice_id FROM invoices WHERE invoices.Status like '1' AND repName like '"+repName+ "'"+ ")";
        String cash_return = returningVal(sql);
        if(cash_return.equals("null"))
            cash_return = "0.00";
        return returningVal(sql);
    }
    
    public String getCreditReturns(){
        String sql = "SELECT SUM(sales_return.amount) as val FROM sales_return WHERE sales_return.date BETWEEN '"+date_begin+"' AND '"+date_end+"' AND sales_return.invoice_id in(SELECT invoices.invoice_id FROM invoices WHERE invoices.Status like '0'AND repName like '"+repName+ "'"+ ")";
        String credit_returns = returningVal(sql);
        if(credit_returns.equals("null"))
            credit_returns = "0.00";
        return returningVal(sql);
    }
    
    public String getPartPayments(){
        String sql = "SELECT sum(credit_payments.amount) as val FROM credit_payments WHERE credit_payments.paid_date BETWEEN '"+date_begin+"' AND '"+date_end+"'"+repName+"'";
        String part_payments = returningVal(sql);
        if(part_payments.equals("null"))
            part_payments = "0.00";
        return  returningVal(sql);
    }
    
    public void setRepName(String repName){
        this.repName = repName;
    }
   
    
    public String returningVal(String sql){
        
        ArrayList data = connector.sqlExecutionaArray(sql, "val");        
        String amount="0.00";
        if(data.size()>0){
            amount = String.valueOf(data.get(0)); 
        }
        return amount;
    }
}
