/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ViewManipulation;

import java.awt.Component;
import javax.swing.JPanel;

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
}
