/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Statistics;

import DBController.DataBaseConnector;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 *
 * @author lakshan
 */
public class DayEndReport {
    private static DayEndReport dayEndReport;
    
    private DayEndReport(){
        
    }
    
    public static DayEndReport getInstance(){
        if(dayEndReport == null)
            dayEndReport = new DayEndReport();
        return dayEndReport;
    }
    
    public void generateReport(){
        DataBaseConnector connector = DataBaseConnector.getInstance();
        String timeStamp = new SimpleDateFormat("yyyy.MM.dd").format(new Date());
        Date dt = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        c.add(Calendar.DATE, 1);
        dt = c.getTime();
        
    }
}
