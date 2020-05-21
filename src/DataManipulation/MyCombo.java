/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DataManipulation;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import DBController.DataBaseConnector;
import Purchaising.Purchaise;
import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author kanishkamadhuranga
 */
public class MyCombo {

    private ArrayList<String> ar;
    private JTextField txt;
    public KeyEvent eventEnter;
    Component comp;

    public void setSearchableCombo(final JComboBox cmb, boolean mustSort, final String noReultsText) {
        ar = new ArrayList<String>();
        final int itemCount = cmb.getItemCount();
        for (int i = 0; i < itemCount; i++) {
            boolean exists = false;
            for (int j = 0; j < ar.size(); j++) {
                if (ar.get(j).equalsIgnoreCase(String.valueOf(cmb.getItemAt(i)))) {
                    exists = true;
                    break;
                }
            }
            if (!exists) {
                ar.add(String.valueOf(cmb.getItemAt(i)));
            }
        }
        if (mustSort) {
            Collections.sort(ar);
        }
        cmb.setEditable(true);
        txt = (JTextField) cmb.getEditor().getEditorComponent();
        txt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                eventEnter = evt;
                int key = evt.getKeyCode();
                if (!(key == KeyEvent.VK_ESCAPE || key == KeyEvent.VK_ENTER || key == KeyEvent.VK_DOWN || key == KeyEvent.VK_UP)) {
                    String text = txt.getText();
                    int caret = txt.getCaretPosition();
                    cmb.hidePopup();
                    cmb.removeAllItems();
                    for (int i = 0; i < ar.size(); i++) {
                        if (ar.get(i).toUpperCase().startsWith(text.substring(0, caret).toUpperCase())) {
                            cmb.addItem(ar.get(i));
                        }
                    }
                    cmb.showPopup();
                    if (cmb.getItemCount() == 0) {
                        cmb.addItem(noReultsText);
                    }
                    txt.setText(text);
                    txt.setCaretPosition(caret);
                } else if (key == KeyEvent.VK_ESCAPE) {
                    cmb.setSelectedItem(txt.getText());
                    cmb.hidePopup();
                } else if (key == KeyEvent.VK_ENTER && cmb.getSelectedIndex() == -1) {
                    if (cmb.getItemCount() == 1 && !cmb.getItemAt(0).equals(noReultsText)) {
                        cmb.setSelectedIndex(0);
                    } else if (cmb.getItemCount() > 1) {
                        cmb.setSelectedIndex(0);
                    }

                }
            }
        });
    }

    public void populateSecondCombo(final JComboBox first, final JComboBox second, final DataBaseConnector connector, final ArrayList<String> list, final ArrayList<String> secondTable, final boolean haveAcondition) {

        txt = (JTextField) first.getEditor().getEditorComponent();
        txt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    String value = (String) first.getSelectedItem();
                    if (haveAcondition) {
                        System.out.println("In the condition");
                        value = connector.getRelavantRecord(secondTable.get(0), secondTable.get(1), secondTable.get(2), value);
                    }
                    DataManipulation DM = new DataManipulation(connector);
                    second.removeAllItems();
                    DM.getRecordsWithCondtion(list.get(0), list.get(1), list.get(2), value, second);
                    second.getEditor().selectAll();
                    second.requestFocus();
                    second.showPopup();
                }
            }
        });

    }

//    public void populateTextBox(final JComboBox combo,DataBaseConnector connector){
//        txt = (JTextField) combo.getEditor().getEditorComponent();
//        txt.addKeyListener(new KeyAdapter() {
//            public void keyReleased(KeyEvent evt) {
//                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
//                    Purchaise.populateFields();
//                }
//            }
//        });
//    }
    public void populateAJTable(final JComboBox combo, final JTable table, final JDateChooser from, final JDateChooser to, final DataBaseConnector connector) {
        txt = (JTextField) combo.getEditor().getEditorComponent();
        txt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
                    String itemNo = String.valueOf(combo.getSelectedItem());

                    Date fromDate = reports_date1_picker.getDate();
                    Date toDate = reports_date2_picker.getDate();
                    String fromDateString = new SimpleDateFormat("yyyy-MM-dd").format(fromDate);
                    String toDateString = new SimpleDateFormat("yyyy-MM-dd").format(toDate);

                    ViewManipulation.ViewManipulation.emptyTable(table);

                    String sql = "SELECT i.invoice, i.createdAt, it.qty, it.selling,it.returnedQty from InvoiceItems it,Invoices i where i.createdAt BETWEEN CAST('" + fromDateString + "' AS DATE) AND CAST('" + toDateString + "' AS DATE) \n"
                            + "AND it.invoiceId = i.invoice AND \n"
                            + "it.code like '" + itemNo + "' Order by i.createdAt DESC";

                    ArrayList<String[]> list = connector.sqlPlainExecution(sql);

                    if (list.size() == 0) {
                        JOptionPane.showMessageDialog(null, "This has no records....");
                    } else {
                        DefaultTableModel model = (DefaultTableModel) table.getModel();

                        for (int i = 0; i < list.size(); i++) {
                            String[] row = list.get(i);
                            Object[] rowData = new Object[5];
                            System.out.println("row" + row);
                            rowData[0] = row[1];
                            rowData[1] = row[2];
                            rowData[2] = row[3];
                            rowData[3] = row[4];
                            rowData[4] = row[5];
                            System.out.println("Row 4 :" + row[4]);

                            model.addRow(rowData);
                        }
                    }

                }
            }
        });
    }

    int focusCount = 0;

    public void moveFocusToNext(final JComboBox combo, final Component comp) {
        txt = (JTextField) combo.getEditor().getEditorComponent();
        txt.addKeyListener(new KeyAdapter() {
            public void keyReleased(KeyEvent evt) {
                if (evt.getKeyCode() == KeyEvent.VK_ENTER) {

                    if (txt.isFocusOwner()) {

                        comp.requestFocusInWindow();

                    }

                }
            }
        });
    }

}
