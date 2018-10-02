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
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author THILINA
 */
public class employeeStats extends HttpServlet {
    DAO dao = new DAO();
    Filter filter;
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet employeeStats</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet employeeStats at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
        //Stats avg = getAverages();
        List<EmployeePerformance> plist = checkBenchmark();
        request.setAttribute("li", plist);
        //request.setAttribute("avg",avg);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
        
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
        
        
        filter.setEndDate((LocalDate.now().minusMonths(1).toString()));
        filter.setStartDate(LocalDate.now().toString());
        
        
        List<Stats> averages = dao.getAverages(filter);
        
        double benchmarkProcess = (averages.get(0).getProcessAvg()/100)*70;
        double benchmarkError = (averages.get(0).getErrorAvg()/100)*20;
        
        
        List<String> allID = dao.allEmployees();
        List<EmployeePerformance> performancelist = new ArrayList<>();
        
        for(String empID : allID){
        
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
