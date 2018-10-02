package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


import static com.sun.corba.se.spi.presentation.rmi.StubAdapter.request;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Frank
 */
public class HomeServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */

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
        
    }
    
    public void devicestatus(HttpServletRequest request, HttpServletResponse response){
        String d1 = (String) request.getAttribute("D1state");
        String d2 = (String) request.getAttribute("D2state");
        String d3 = (String) request.getAttribute("D3state");
        String d4 = (String) request.getAttribute("D4state");
        String d5 = (String) request.getAttribute("D5state");
        String msgbad="bad";
        String msgok="ok";
        if(d1 == null || d1 == ""){
            request.setAttribute("health", msgbad);
        }else{
            request.setAttribute("health", msgok);
        }
        if(d2 == null || d2 == ""){
        request.setAttribute("health", msgbad);
        }else{
            request.setAttribute("health", msgok);
        }
        if(d3 == null || d3 == ""){
        request.setAttribute("health", msgbad);
        }else{
            request.setAttribute("health", msgok);
        }
        if(d4 == null || d4 == ""){
        request.setAttribute("health", msgbad);
        }else{
            request.setAttribute("health", msgok);
        }
        if(d5 == null || d5 == ""){
        request.setAttribute("health", msgbad);
        }else{
            request.setAttribute("health", msgok);
        }
        
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
