/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controller;

import Model.DAO;
import Model.EmployeePerformance;
import Model.Filter;
import Model.Stats;
import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author THILINA
 */
public class employeeStats extends HttpServlet {
    
    
   
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<EmployeePerformance> plist = checkBenchmark();
        request.setAttribute("plist", plist);
        RequestDispatcher dispatcher = request.getRequestDispatcher("Tables.jsp");
        dispatcher.include(request, response);
    }

  
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
//          List<EmployeePerformance> plist = checkBenchmark();
//        request.setAttribute("li", plist);
//        RequestDispatcher dispatcher = request.getRequestDispatcher("/Tables.jsp");
//        dispatcher.include(request, response);
        
    }
    
     
    
//    public Stats getAverages(){
//        
//        filter.setEndDate((LocalDate.now().minusMonths(1).toString()));
//        filter.setStartDate(LocalDate.now().toString());
//        
//        
//        List<Stats> averages = dao.getAverages(filter);
//        
//        double benchmarkProcess = (averages.get(0).getProcessAvg()/100)*70;
//        double benchmarkError = (averages.get(0).getErrorAvg()/100)*20;
//        Stats avg = new Stats(benchmarkError, benchmarkProcess);
//        
//        
//       return avg;
//    }
    
    public List<EmployeePerformance> checkBenchmark(){
        DAO dao = new DAO();
        String endDate = LocalDate.now().minusMonths(1).toString();
        String startDate = LocalDate.now().toString();
        
        Filter filter = new Filter(endDate,startDate);
        
        
        List<Stats> averages = dao.getAverages(filter);
        System.out.println("averages : "+averages);
        double benchmarkProcess = (averages.get(0).getProcessAvg()/100)*70;
        double benchmarkError = (averages.get(0).getErrorAvg()/100)*20;
        
        
        List<String> allID = dao.allEmployees();
        List<EmployeePerformance> performancelist = new ArrayList<>();
        
        for(String empID : allID){
            System.out.println(empID);
            Double EA = dao.getEmployeeErrorAverage(empID, filter);
            Double PA = dao.getEmployeeProcessAverage(empID, filter);
            
            EmployeePerformance empP = new EmployeePerformance(empID, EA, PA, benchmarkError, benchmarkProcess);
            performancelist.add(empP);
        }
        
       return performancelist;
       
        
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
