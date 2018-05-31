/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Inventory;

import org.apache.batik.util.RunnableQueue;

/**
 *
 * @author manual-pc
 */
public class CheckMainFrameLiveTask implements Runnable{

    Thread mflt;
    LoadingScreenInvokeTask lt;
    MainFrameInvokeTask mfit;
            
    
    public CheckMainFrameLiveTask(Thread mflt,LoadingScreenInvokeTask lt,MainFrameInvokeTask mfit){
        this.mflt=mflt;
        this.lt=lt;
        this.mfit=mfit;
    }
    
    @Override
    public void run() {
       while(true){
           if(!mflt.isAlive()){
               lt.j.setVisible(false);
               mfit.j.setVisible(true);
               break;
           }
       }
    }
    
}
