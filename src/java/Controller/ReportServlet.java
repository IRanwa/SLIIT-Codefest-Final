package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import Model.DAO;
import Model.Filter;
import Model.Stats;
import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Frank
 */
public class ReportServlet extends HttpServlet {

    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String command = request.getParameter("command");
        switch(command){
            case "Get-Employees":
                getEmployeeList(request, response);
                request.getRequestDispatcher("reports.jsp").include(request, response);
        }
    }

    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        generateReport(request, response);
    }

    
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    private void generateReport(HttpServletRequest request, HttpServletResponse response){
        DAO dao = new DAO();
        
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String Stepid = request.getParameter("Stepid");
        String timePeriod = request.getParameter("timePeriod");
        String EmpId = request.getParameter("EmpId");
        
        
        Filter filter = new Filter(startDate, endDate, Stepid, timePeriod, EmpId);
        List<Stats> records = dao.getReportDetails(filter);
        System.out.println("Size : "+records.size());
        
        dao.close();
    }
    
    private void getEmployeeList(HttpServletRequest request, HttpServletResponse response){
        DAO dao = new DAO();
        String startDate = request.getParameter("startDate");
        String endDate = request.getParameter("endDate");
        String Stepid = request.getParameter("Stepid");
        if(Stepid==null){
            Stepid = "All";
        }
        String timePeriod = request.getParameter("timePeriod");
        Filter filter = new Filter(startDate, endDate, Stepid, timePeriod, "All");
        System.out.println(filter.getStartDate());
        System.out.println(filter.getEndDate());
        System.out.println(filter.getStepId());
        System.out.println(filter.getAggTime());
        System.out.println(filter.getEmpName());
        List<String> records = dao.getEmpName(filter);
        request.setAttribute("empList", records);
        System.out.println("Size : "+records.size());
        dao.close();
    }
}
