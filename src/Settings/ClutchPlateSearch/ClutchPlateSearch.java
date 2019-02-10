/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Settings.ClutchPlateSearch;

import DBController.DataBaseConnector;
import javax.swing.JComboBox;

/**
 *
 * @author manual-pc
 */
public class ClutchPlateSearch {
    private static ClutchPlateSearch clutchPlateSearch;
    
    //Field items
    JComboBox<String> grew_combo;
    JComboBox<String> inner_combo;
    JComboBox<String> outer_combo;
    
    DataBaseConnector connector;
    
    private ClutchPlateSearch(){
        
    }
    
    public static ClutchPlateSearch getInstance(){
        if(clutchPlateSearch == null)
            clutchPlateSearch = new ClutchPlateSearch();
        return clutchPlateSearch;
    }
    
    private void autoCompleteFieldsO(){
        
    }
    
    public void setFields(JComboBox grew_combo, JComboBox inner_combo,JComboBox outer_combo){
        
    }
}
