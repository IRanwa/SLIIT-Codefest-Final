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
    String empID;
    double eError;
    double eProcess;
    double errorbenchmark;
    double processbenchmark;

    public EmployeePerformance(String empID, double eError, double eProcess, double errorbenchmark, double processbenchmark) {
        this.empID = empID;
        this.eError = eError;
        this.eProcess = eProcess;
        this.errorbenchmark = errorbenchmark;
        this.processbenchmark = processbenchmark;
    }

    public String getEmpID() {
        return empID;
    }

    public double geteError() {
        return eError;
    }

    public double geteProcess() {
        return eProcess;
    }

    public double getErrorbenchmark() {
        return errorbenchmark;
    }

    public double getProcessbenchmark() {
        return processbenchmark;
    }

    public void setEmpID(String empID) {
        this.empID = empID;
    }

    public void seteError(double eError) {
        this.eError = eError;
    }

    public void seteProcess(double eProcess) {
        this.eProcess = eProcess;
    }

    public void setErrorbenchmark(double errorbenchmark) {
        this.errorbenchmark = errorbenchmark;
    }

    public void setProcessbenchmark(double processbenchmark) {
        this.processbenchmark = processbenchmark;
    }

    
    
    
    
}
