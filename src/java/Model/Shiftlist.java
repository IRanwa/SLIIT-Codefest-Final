/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author USER
 */
public class Shiftlist {
    private String shiftId;
    private String stepId;
    private String employeeID;
    private String employeeName;
    private String employeeRole;

    public Shiftlist(String shiftId,String stepId, String employeeID, String employeeName, String employeeRole) {
        this.shiftId = shiftId;
        this.stepId = stepId;
        this.employeeID = employeeID;
        this.employeeName = employeeName;
        this.employeeRole = employeeRole;
    }
    
    public String getShiftId() {
        return shiftId;
    }

    public String getStepId() {
        return stepId;
    }

    public String getEmployeeID() {
        return employeeID;
    }

    public String getEmployeeName() {
        return employeeName;
    }
    
    public String getEmployeeRole() {
        return employeeRole;
    }
    
    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }
    
    public void setStepId(String stepId) {
        this.stepId = stepId;
    }

    public void setEmployeeID(String employeeID) {
        this.employeeID = employeeID;
    }

    public void setEmployeeName(String employeeName) {
        this.employeeName = employeeName;
    }
    
    public void setEmployeeRole(String employeeRole) {
        this.employeeRole = employeeRole;
    }
}
