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


    private String startDate;
    private String endDate;
    private  String stepId;
    private String aggTime;
    private  String empName;
    private  String empID;

    public String getEmpID() {
        return empID;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public Filter(String startDate, String endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
    
   
    
    public Filter(String startDate, String endDate, String stepId, String aggTime, String empID) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.stepId = stepId;
        this.aggTime = aggTime;
        this.empID = empID;
    }

    public Filter() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }
    

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    

    public String getStepId() {
        return stepId;
    }

    public String getAggTime() {
        return aggTime;
    }

    public void setAggTime(String aggTime) {
        this.aggTime = aggTime;
    }
    

    public String getEmpName() {
        return empName;
    }  
    
}
