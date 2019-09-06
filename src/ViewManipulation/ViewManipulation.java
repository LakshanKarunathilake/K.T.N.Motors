/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewManipulation;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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
    
    public static void makeAllComponents(ArrayList<Component> comps,boolean enabled){
        for (int i = 0; i < comps.size(); i++) {
            Component comp = comps.get(i);
            comp.setEnabled(enabled);
        }
    }
    
    public static void emptyTextBoxes(ArrayList<JTextField> texts){
        for (int i = 0; i < texts.size(); i++) {
            JTextField text = texts.get(i);
            text.setText("");
            
        }
    }
    
    public static void moveFocusToNext(final JComboBox combo,final Component comp){
        final JTextField txt = (JTextField) combo.getEditor().getEditorComponent();
        txt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                   
                    if(txt.isFocusOwner()){
                        
                        comp.requestFocusInWindow();
                        
                    }
                    
                }
            }
        });
    }
    
    public static void changeTableView(JTable table,int[] columnWidths){
        
        //Changing the font of the table
        Font f = new Font("Arial", Font.BOLD, 15);
        JTableHeader header = table.getTableHeader();
        header.setFont(f);
        
        //Defining the table lengths
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        for (int i = 0; i < columnWidths.length; i++) {
            table.getColumnModel().getColumn(i).setPreferredWidth(columnWidths[i]);
        }
    }
}
