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

    public void close() {
        try {
            connection.close();
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean getEmployee(Employee employee) {
        try {
            PreparedStatement ps = connection.prepareStatement("select * from employee where EmpID=?");
            ps.setString(1, employee.getEmpId());
            ResultSet rs = ps.executeQuery();
            return rs.next();
        } catch (SQLException ex) {

            ex.printStackTrace();
        }
        return false;
    }

    public List<String> allEmployees() {

        List<String> empID = new ArrayList<>();
        try {
            PreparedStatement ps = connection.prepareStatement("select EmpID from employee");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                empID.add(rs.getString("EmpID"));
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return empID;
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

        long startTime = System.currentTimeMillis();
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
            long stopTime = System.currentTimeMillis();
            long elapsedTime = stopTime - startTime;
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
            PreparedStatement ps = connection.prepareStatement("Select * from employee");
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

    public Double getEmployeeProcessAverage(String empID, Filter filter) {

        Double avg = null;
        try {

            String sql = "select avg(reporttable.ProcessRate) "
                    + "from employeestats inner join reporttable "
                    + "on employeestats.id = reporttable.shiftRefId where EmpID=? and reporttable.EndTime between ? and ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, empID);
            ps.setString(2, filter.getStartDate());
            ps.setString(3, filter.getEndDate());
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return avg;

    }

    public Double getEmployeeErrorAverage(String empID, Filter filter) {
        Double avg = null;
        try {

            String sql = "select avg(reporttable.Error) "
                    + "from employeestats inner join reporttable "
                    + "on employeestats.id = reporttable.shiftRefId where EmpID=? and "
                    + "reporttable.EndTime between ? and ?";

            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, empID);
            ps.setString(2, filter.getStartDate());
            ps.setString(3, filter.getEndDate());

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getDouble(1);
            }

        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }

        return avg;
    }

    public List<Stats> getAverages(Filter filter) {
        List<Stats> avg = new ArrayList<>();

        try {
            String sql = "Select avg(Error) as errorPer, avg(ProcessRate) as processRate from reporttable "
                    + "where EndTime between " + filter.getStartDate() + " and " + filter.getEndDate();
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                double errorRate = rs.getDouble("errorPer");
                double procRate = rs.getDouble("processRate");
                Stats stat = new Stats(errorRate, procRate);
                avg.add(stat);
            }
            return avg;
        } catch (SQLException ex) {
            Logger.getLogger(DAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public List<Stats> getReportDetails(Filter filter) {
        List<Stats> statsList = new ArrayList<>();
        try {
            String sql = "select avg(Error) as errorPer, avg(ProcessRate) as processRate from reporttable where shiftRefId"
                    + "IN (select es.id from employeestats as es "
                    + "left join shiftreference as sr on sr.id=es.id where es.EmpID=? and sr.Step=?) "
                    + "and EndTime between ? and ? ";
            switch(filter.getAggTime()){
                case "Yearly":
                    sql += "Group by YEAR(EndTime)";
                    break;
                case "Monthly":
                    sql += "Group by MONTH(EndTime)";
                    break;
                case "Weekly":
                    sql += "Group by WEEK(EndTime)";
                    break;
                case "Daily":
                    sql += "Group by DATE(EndTime)";
                    break;
                default:
                    sql += "Group by HOUR(EndTime)";
                    break;
            }
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, filter.getEmpID());
            ps.setString(2, filter.getStepId());
            ps.setString(3, filter.getStartDate());
            ps.setString(4, filter.getEndDate());
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
        return statsList;
    }

    public List<String> getShiftList(Shiftlist shift) {
        List<String> StepID = new ArrayList<>();
        try {

            PreparedStatement ps = connection.prepareStatement("select id from shiftreference where Date=? and Shift=? ");
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
}
