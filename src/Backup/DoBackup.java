/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Backup;

import java.io.File;
import java.io.IOException;
import java.security.CodeSource;
import javax.swing.JOptionPane;

/**
 *
 * @author manual-pc
 */
public class DoBackup {
   public static void Backupdbtosql() {
    try {

//        /*NOTE: Getting path to the Jar file being executed*/
//        /*NOTE: YourImplementingClass-> replace with the class executing the code*/
//        CodeSourcerce codeSource = YourImplementingClass.class.getProtectionDomain().getCodeSource();
//        File jarFile = new File(codeSource.getLocation().toURI().getPath());
//        String jarDir = jarFile.getParentFile().getPath();


        /*NOTE: Creating Database Constraints*/
        String dbName = "prototype";
        String dbUser = "root";
        String dbPass = "";

        /*NOTE: Creating Path Constraints for folder saving*/
        /*NOTE: Here the backup folder is created for saving inside it*/
        String folderPath = "G:\\backup\\";

        /*NOTE: Creating Folder if it does not exist*/
        File f1 = new File(folderPath);
        f1.mkdir();

        /*NOTE: Creating Path Constraints for backup saving*/
        /*NOTE: Here the backup is saved in a folder called backup with the name backup.sql*/
         String savePath = folderPath + "backup.sql\"";

        /*NOTE: Used to create a cmd command*/
        String executeCmd = "mysqldump -u" + dbUser + " -p" + dbPass + " --database " + dbName + " -r " + savePath;

        /*NOTE: Executing the command here*/
        Process runtimeProcess = Runtime.getRuntime().exec(executeCmd);
        int processComplete = runtimeProcess.waitFor();

        /*NOTE: processComplete=0 if correctly executed, will contain other values if not*/
        if (processComplete == 0) {
            System.out.println("Backup Complete");
        } else {
            System.out.println("Backup Failure");
        }

    } catch (Exception ex) {
        JOptionPane.showMessageDialog(null, "Error at Backuprestore" + ex.getMessage());
    }
} 
}
