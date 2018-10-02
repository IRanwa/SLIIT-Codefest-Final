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
public class Report {

    private String shiftId;
    private String StartTime;
    private String EndTime;
    private String ProcessRate;
    private String ErrorRate;
    
    public Report(String shiftId, String StartTime, String EndTime, String ProcessRate, String ErrorRate) {
        this.shiftId = shiftId;
        this.StartTime = StartTime;
        this.EndTime = EndTime;
        this.ProcessRate = ProcessRate;
        this.ErrorRate = ErrorRate;
    }

    public String getShiftId() {
        return shiftId;
    }

    public String getStartTime() {
        return StartTime;
    }

    public String getEndTime() {
        return EndTime;
    }

    public String getProcessRate() {
        return ProcessRate;
    }

    public String getErrorRate() {
        return ErrorRate;
    }

    public void setShiftId(String shiftId) {
        this.shiftId = shiftId;
    }

    public void setStartTime(String StartTime) {
        this.StartTime = StartTime;
    }

    public void setEndTime(String EndTime) {
        this.EndTime = EndTime;
    }

    public void setProcessRate(String ProcessRate) {
        this.ProcessRate = ProcessRate;
    }

    public void setErrorRate(String ErrorRate) {
        this.ErrorRate = ErrorRate;
    }
    
}
