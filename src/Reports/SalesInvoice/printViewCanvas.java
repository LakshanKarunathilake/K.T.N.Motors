/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Reports.SalesInvoice;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Graphics;
import javax.swing.JFrame;

/**
 *
 * @author manual-pc
 */
public class printViewCanvas extends JFrame{
    
    private printViewCanvas canvas = new printViewCanvas();
    
    public printViewCanvas(){
        setLayout(new BorderLayout());
        setSize(500,375);
        setTitle("Print View");
        add("Center", canvas);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
    }
    
    private class Mycanvas extends Canvas{
        public void paid(Graphics g){
            g.drawString("Hello asasas",10,10);
        }
    }
}

