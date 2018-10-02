/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Imesh Ranawaka
 */
public class DailyProcess {

    private String date;
    private ArrayList<String> Shifts;
    private ArrayList<String> Steps;
    private List<Integer> shiftRefIdList;

    public DailyProcess(String date, ArrayList<String> Shifts, ArrayList<String> Steps) {
        this.date = date;
        this.Shifts = Shifts;
        this.Steps = Steps;
    }

    public String getDate() {
        return date;
    }

    public ArrayList<String> getShifts() {
        return Shifts;
    }

    public ArrayList<String> getSteps() {
        return Steps;
    }

    public List<Integer> getShiftRefIdList() {
        return shiftRefIdList;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setShifts(ArrayList<String> Shifts) {
        this.Shifts = Shifts;
    }

    public void setSteps(ArrayList<String> Steps) {
        this.Steps = Steps;
    }

    public void setShiftRefIdList(List<Integer> shiftRefIdList) {
        this.shiftRefIdList = shiftRefIdList;
    }
}
