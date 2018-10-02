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
public class Employee {

    String empId;
    String empName;
    char empRole;
    
    public Employee(String empId, String empName, char empRole) {
        this.empId = empId;
        this.empName = empName;
        this.empRole = empRole;
    }

    public String getEmpId() {
        return empId;
    }

    public String getEmpName() {
        return empName;
    }

    public char getEmpRole() {
        return empRole;
    }

    public void setEmpId(String empId) {
        this.empId = empId;
    }

    public void setEmpName(String empName) {
        this.empName = empName;
    }

    public void setEmpRole(char empRole) {
        this.empRole = empRole;
    }
    
}
