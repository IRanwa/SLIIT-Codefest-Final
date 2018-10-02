/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Imesh Ranawaka
 */
public class Filter {

    private final String startDate;
    private final String endDate;
    private final String stepId;
    private final String aggTime;
    private final String empName;
    
    public Filter(String startDate, String endDate, String stepId, String aggTime, String empName) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.stepId = stepId;
        this.aggTime = aggTime;
        this.empName = empName;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getStepId() {
        return stepId;
    }

    public String getAggTime() {
        return aggTime;
    }

    public String getEmpName() {
        return empName;
    }  
    
}
