/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Pamuditha
 */
public class employeeStats {
    DAO dao = new DAO();
    Filter filter;

    public employeeStats(Filter filter) {
        this.filter = filter;
    }
    
    public Stats getAverages(){
        
        filter.setEndDate((LocalDate.now().minusMonths(1).toString()));
        filter.setStartDate(LocalDate.now().toString());
        
        
        List<Stats> averages = dao.getAverages(filter);
        
        double benchmarkProcess = (averages.get(0).getProcessAvg()/100)*70;
        double benchmarkError = (averages.get(0).getErrorAvg()/100)*20;
        Stats avg = new Stats(benchmarkError, benchmarkProcess);
       
       return avg;
    }
    
    public void checkBenchmark(){
        
        List<String> allID = dao.allEmployees();
        List<EmployeePerformance> performancelist = new ArrayList<>();
        
        for(String empID : allID){
        
            Double EA = dao.getEmployeeErrorAverage(empID, filter);
            Double PA = dao.getEmployeeProcessAverage(empID, filter);
            
            EmployeePerformance empP = new EmployeePerformance(empID, EA, PA);
            performancelist.add(empP);
        }
        
       
        
    }
    
    
}
