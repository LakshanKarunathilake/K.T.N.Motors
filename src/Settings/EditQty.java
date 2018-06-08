/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings;

import DBController.DataBaseConnector;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JTextField;

/**
 *
 * @author manual-pc
 */
public class EditQty {
    private static EditQty editQty;
    
    JComboBox itemNo_combo;
    JLabel description_lbl;JLabel available_lbl;
    JTextField qty_txt;
    
    DataBaseConnector connector;
    
    private EditQty(){}
    
    public static EditQty getInstance(){
        if(editQty==null)
            editQty = new EditQty();
        return editQty;
    }
    
    public void setFields(JComboBox itemNo_combo,JLabel description_lbl,JLabel available_lbl,JTextField qty_txt,DataBaseConnector connector){
        this.itemNo_combo = itemNo_combo;
        this.description_lbl =description_lbl;
        this.available_lbl = available_lbl;
        this.qty_txt = qty_txt;
        
        this.connector = connector;        
    }
    
    
}
