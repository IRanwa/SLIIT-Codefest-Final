/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package codefest;

import Model.Shiftlist;
import Model.DAO;
import Model.DailyProcess;
import Model.Employee;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

/**
 *
 * @author Imesh Ranawaka
 */
public class loadData {

    
    ArrayList<String> steps;
    boolean stopTask;
    public loadData() {
        DAO dao = new DAO();
        readFromCSV csv = new readFromCSV();
        ArrayList<Shiftlist> list = csv.readFile();

        ArrayList<String> shifts = new ArrayList<>();
        steps = new ArrayList<>();
        if (list != null && list.size() >= 0) {
            for (Shiftlist s : list) {
                //Shift Information
                String shiftId = s.getShiftId();
                String stepId = s.getStepId();
                String empId = s.getEmployeeID();
                String empName = s.getEmployeeName();
                String empRole = s.getEmployeeRole();

                //Adding Employee Details
                Employee emp;
                if (empRole.equalsIgnoreCase("manager")) {
                    emp = new Employee(empId, empName, 'M');
                } else {
                    emp = new Employee(empId, empName, 'E');
                }
                dao.addEmployee(emp);

                //Add shift if not inserted perviously
                boolean flag = true;
                for (String shift : shifts) {
                    if (shift.equals(shiftId)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    shifts.add(shiftId);
                }

                //Add steps if not inserted previously
                flag = true;
                for (String step : steps) {
                    if (step.equals(stepId)) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    steps.add(stepId);
                }
            }
            
            //Get Current date and insert shift information
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            Date date = new Date();
            DailyProcess dailyProcess = new DailyProcess(formatter.format(date), shifts, steps);
            List<Integer> shiftRefIdList = dao.addShiftDetails(dailyProcess);
            //Add Employee stats (This represent which shift and step which employee works)
            dao.addEmployeeStats(shiftRefIdList, list);
        }
    }

    public void startReading() {
        Timer timer6hrs = new Timer();

        //Create each IOT device calculation class instance
        ArrayList<Calculations> calList = new ArrayList<>();
        for (int x = 0; steps.size() + 1 > x; x++) {
            calList.add(new Calculations());
        }
        //Assuming 1 Shift goes upto 6 hours
        timer6hrs.schedule(new TimerTask() {
            @Override
            public void run() {
                stopTask = false;
                Timer timer1hrs = new Timer();
                timer1hrs.scheduleAtFixedRate(new TimerTask() {
                    @Override
                    public void run() {
                        if (stopTask) {
                            this.cancel();
                        }
                        //Reset error and total count of each IOT device every 1 Hour
                        for (int x = 0; x < calList.size(); x++) {
                            calList.get(x).reset1Hr();
                        }
                        Timer timer5min = new Timer();
                        timer5min.scheduleAtFixedRate(new TimerTask() {
                            @Override
                            public void run() {
                                if (stopTask) {
                                    this.cancel();
                                }
                                //Reset error and total count of each IOT device every 5 Minutes
                                for (int x = 0; x < calList.size(); x++) {
                                    calList.get(x).reset5Min();
                                }
                                Timer timer1second = new Timer();
                                timer1second.scheduleAtFixedRate(new TimerTask() {

                                    //Get current time in milliseconds
                                    long start = System.currentTimeMillis();

                                    @Override
                                    public void run() {
                                        if (stopTask) {
                                            this.cancel();
                                        }
                                        //Reset error and total count of each IOT device every 1 second
                                        for (int x = 0; x < calList.size(); x++) {
                                            calList.get(x).reset1Sec();
                                        }

                                        //Randomly generate messages
                                        Random r = new Random();
                                        //Get no of items go through each product line randomly (Between 800 - 1000 Items)
                                        int noOfItem = r.nextInt(1000 - 800 + 1) + 800;
                                        for (int x = 0; x < noOfItem; x++) {
                                            //Get IOT Device No
                                            int iotDevNo = r.nextInt(5 - 1 + 1) + 1;
                                            //Get Received item error or not (error when value = 1)
                                            int num = r.nextInt(2 - 1 + 1) + 1;
                                            if (num == 1) {
                                                //Increment each IOT Device error count
                                                calList.get(iotDevNo - 1).incrementError();
                                            }
                                            //Increment each IOT Device total count
                                            calList.get(iotDevNo - 1).incrementTotal();
                                        }

                                        //Display each IOT device error rate and processing rate per second
                                        //dataSet.setupErrorLineChart();
                                        for (int x = calList.size()-1; x > 0; x++) {
                                            Double errorRate = calList.get(x).ErrorRate() - calList.get(x-1).ErrorRate();
                                            Double processRate = calList.get(x).processingRate(System.currentTimeMillis() - start)
                                                    - calList.get(x-1).processingRate(System.currentTimeMillis() - start);
                                        }
                                        //Get current time in milliseconds
                                        start = System.currentTimeMillis();
                                    }
                                }, 0, 1000);
                                //Display each IOT device error rate and processing rate every 5 Minutes
                                for (int x = 0; x < calList.size(); x++) {
                                    double errorRate = calList.get(x).error5MinRate()-calList.get(x-1).error5MinRate();
                                    double processRate = calList.get(x).process5MinRate()-calList.get(x-1).process5MinRate();
                                }
                            }
                        }, 0, (1000 * 60 * 5));
                       
                    }
                }, 0, (1000 * 60 * 60));
                stopTask = true;
            }
        }, 0, (1000 * 60 * 60 * 6));

    }

    private void load() {
        File file = new File("2016-09-19.csv");

        if (file.exists()) {
            Scanner scanner;
            try {
                scanner = new Scanner(file);
                scanner.useDelimiter(",");
                int count = 0;
                while (scanner.hasNextLine()) {
                    System.out.println(scanner.nextLine());
                }
            } catch (FileNotFoundException ex) {
                Logger.getLogger(readFromCSV.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void stopReading() {
        stopTask = true;
    }

}
