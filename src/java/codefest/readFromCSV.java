/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codefest;

import Model.Shiftlist;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class readFromCSV {

    public ArrayList<Shiftlist> readFile() {
        ArrayList<Shiftlist> shiftdata = new ArrayList<>();
        try {
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            File file = new File(formatter.format(date)+".csv");

            if (file.exists()) {
                Scanner sc = new Scanner(file);
                sc.useDelimiter(",");

                int count = 0;

                while (sc.hasNextLine()) {
                    String[] line = sc.nextLine().split(",");
                    if (count > 0) {
                        String shiftId = line[0];
                        String stepId = line[1];
                        String employeeID = line[2];
                        String employeeName = line[3];
                        String employeeRole = line[4];
                        Shiftlist sl = new Shiftlist(shiftId, stepId, employeeID, employeeName, employeeRole);
                        shiftdata.add(sl);
                    }else{
                        sc.nextLine();
                    }
                    count++;
                }
                return shiftdata;
            }else{
                System.out.println("file not found");
            }
        } catch (FileNotFoundException ex) {
            Logger.getLogger(readFromCSV.class.getName()).log(Level.SEVERE, null, ex);
        }

        return shiftdata;
    }
}
