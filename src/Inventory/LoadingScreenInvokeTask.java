/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory;

import javax.swing.JFrame;

/**
 *
 * @author manual-pc
 */
public class LoadingScreenInvokeTask implements Runnable{
    
    JFrame j;
    
    @Override
    public void run() {
       j= new LoadingScreen();
       j.setLocationRelativeTo(null);
       j.setVisible(true);
        
    }
    
}
