/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewManipulation;

import java.awt.Component;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author manual-pc
 */
public class ViewManipulation {
    public static void changePanel(JPanel firstPanel,Component  movingPanel){
        firstPanel.removeAll();
        firstPanel.repaint();
        firstPanel.revalidate();

        firstPanel.add(movingPanel);
        firstPanel.repaint();
        firstPanel.revalidate();
    }
    
    public static void emptyComboBoxes(ArrayList<JComboBox> combos){
        for (int i = 0; i < combos.size(); i++) {
            JComboBox combo = combos.get(i);
            combo.removeAllItems();
        }
    }
    
    public static void emptyTable(JTable table){
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        for (int i = model.getRowCount() - 1; i >= 0; i--) {
            model.removeRow(i);
        }        
    }
}
