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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 *
 * @author Imesh Ranawaka
 */
public class loadData {

    DailyProcess dailyProcess;
    
    public loadData() {
        DAO dao = new DAO();
        readFromCSV csv = new readFromCSV();
        ArrayList<Shiftlist> list = csv.readFile();

        ArrayList<String> shifts = new ArrayList<>();
        ArrayList<String> steps = new ArrayList<>();
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
            dailyProcess = new DailyProcess(formatter.format(date), shifts, steps);
            List<Integer> shiftRefIdList = dao.addShiftDetails(dailyProcess);
            dailyProcess.setShiftRefIdList(shiftRefIdList);
            //Add Employee stats (This represent which shift and step which employee works)
            dao.addEmployeeStats(shiftRefIdList, list);
        }
    }
    
    public DailyProcess getDailyProcess() {
        return dailyProcess;
    }
}
