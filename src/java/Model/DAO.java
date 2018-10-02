/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author USER
 */
public class DAO {

    private final Connection connection;

    public DAO() {
        connection = Database.getConnection();
    }

    public boolean getEmployee(Employee employee) {
        try {
            PreparedStatement ps = connection.prepareStatement("select * from employee where EmpID=?");
            ps.setString(1, employee.getEmpId());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addEmployee(Employee employee) {
        boolean status = getEmployee(employee);
        if (!status) {
            try {

                PreparedStatement ps = connection.prepareStatement("Insert into employee(EmpID,EmpName,EmpRole) values(?,?,?)");
                ps.setString(1, employee.getEmpId());
                ps.setString(2, employee.getEmpName());
                ps.setString(3, String.valueOf(employee.getEmpRole()));
                return ps.execute();

            } catch (SQLException ex) {
                Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return false;
    }

    public List<Integer> addShiftDetails(DailyProcess dailyProc) {
        List<Integer> shiftRefIdList = new ArrayList<>();
        try {
            ArrayList<String> shiftsList = dailyProc.getShifts();
            ArrayList<String> stepsList = dailyProc.getSteps();

            for (int shiftC = 0; shiftC < shiftsList.size(); shiftC++) {
                for (int stepsC = 0; stepsC < stepsList.size(); stepsC++) {
                    PreparedStatement ps = connection.prepareStatement("Insert into shiftreference(Date,Shift,Step) values(?,?,?)", PreparedStatement.RETURN_GENERATED_KEYS);
                    ps.setString(1, dailyProc.getDate());
                    ps.setString(2, shiftsList.get(shiftC));
                    ps.setString(3, stepsList.get(stepsC));
                    ps.execute();
                    ResultSet rs = ps.getGeneratedKeys();
                    while (rs.next()) {
                        shiftRefIdList.add(rs.getInt(1));
                    }
                }
            }
            return shiftRefIdList;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return shiftRefIdList;
    }

    public boolean addEmployeeStats(List<Integer> shiftRefIdList, ArrayList<Shiftlist> shiftData) {
        try {
            for (int listId = 0; listId < shiftRefIdList.size(); listId++) {
                PreparedStatement ps = connection.prepareStatement("Insert into employeestats(id,EmpID) values(?,?)");
                ps.setInt(1, shiftRefIdList.get(listId));
                ps.setString(2, shiftData.get(listId).getEmployeeID());
            }
            return true;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean addReport(Report report) {
        try {
            PreparedStatement ps = connection.prepareStatement("Insert into reporttable(shiftRefId,StartTime,EndTime,ProcessRate,Error) values(?,?,?,?,?)");
            ps.setString(1, report.getShiftId());
            ps.setString(2, report.getStartTime());
            ps.setString(3, report.getEndTime());
            ps.setString(4, report.getProcessRate());
            ps.setString(5, report.getErrorRate());
            return ps.execute();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public List<String> getStepID() {

        List<String> StepID = new ArrayList<>();
        try {

            PreparedStatement ps = connection.prepareStatement("select Step from shiftreference group by Step");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                String id = rs.getString("Step");
                StepID.add(id);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return StepID;
    }

    public List<String> getEmpName(Filter filter) {

        List<String> EmpName = new ArrayList<>();
        try {
            String sql;
            if (filter!=null) {
                sql = "select * from employee "
                        + "where EmpID IN (select EmpID from employeestats "
                        + "where id IN (select shiftRefId from reporttable ";
                if (!filter.getStepId().equals("All")) {
                    sql = sql + "where shiftRefId IN (select id from shiftreference "
                            + "where Step= " + filter.getStepId() + ") and EndTime between " + filter.getStartDate() + " and " + filter.getEndDate() + "))";
                } else {
                    sql = sql + ") and EndTime between " + filter.getStartDate() + " and " + filter.getEndDate() + "))";
                }
            }else{
                sql = "select * from employee";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("EmpName");
                EmpName.add(name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return EmpName;
    }

    public List<Stats> getReportDetails(Filter filter) {
        List<Stats> statsList = new ArrayList<>();
        try {
            String sql = "Select avg(Error) as errorPer, avg(ProcessRate) as processRate from reporttable "
                    + "where EndTime between " + filter.getStartDate() + " and " + filter.getEndDate();
            if (!filter.getStepId().equals("All")) {
                sql += "and shiftRefId IN (select id from shiftreference where Step=" + filter.getStepId() + ") ";
            }
            if (!filter.getEmpName().equals("All")) {
                sql += "and shiftRefId IN (select id from employeestatus where "
                        + "EmpID IN (select EmpID from employee where EmpName=" + filter.getEmpName() + ")) ";
            }

            switch (filter.getAggTime()) {
                case "YEAR":
                    sql += "GROUP by MONTH(EndTime)";
                    break;
                case "Monthly":
                    sql += "GROUP by MONTH(EndTime)";
                    break;
                case "Weekly":
                    sql += "GROUP by WEEK(EndTime)";
                    break;
                case "Daily":
                    sql += "GROUP by DATE(EndTime)";
                    break;
                default:
                    sql += "GROUP by HOUR(EndTime)";
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                double errorRate = rs.getDouble("errorPer");
                double procRate = rs.getDouble("processRate");
                Stats stat = new Stats(errorRate, procRate);
                statsList.add(stat);
            }
            return statsList;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
