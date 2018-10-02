/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Pamuditha
 */
public class EmployeePerformance {
    String EmpID;
    Double Eerror;
    Double Eprocess;

    public EmployeePerformance(String EmpID, Double Eerror, Double Eprocess) {
        this.EmpID = EmpID;
        this.Eerror = Eerror;
        this.Eprocess = Eprocess;
    }

    public String getEmpID() {
        return EmpID;
    }

    public void setEmpID(String EmpID) {
        this.EmpID = EmpID;
    }

    public Double getEerror() {
        return Eerror;
    }

    public void setEerror(Double Eerror) {
        this.Eerror = Eerror;
    }

    public Double getEprocess() {
        return Eprocess;
    }

    public void setEprocess(Double Eprocess) {
        this.Eprocess = Eprocess;
    }
    
    
}
