package Controller;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import Model.DAO;
import Model.DailyProcess;
import Model.Database;
import Model.Report;
import codefest.loadData;
import com.sun.corba.se.spi.presentation.rmi.StubAdapter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Frank
 */
public class ManagerHomeServlet extends HttpServlet {

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
    loadData load;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String command = request.getParameter("command");
        if (command == null) {
            command = "HomePage";
        }
        switch (command) {
            case "1HourRecords":
                store1HourRecord(request, response);

                break;
            case "View-Report":
                break;
            default:
                load = new loadData();
                request.getRequestDispatcher("Barchart.jsp").forward(request, response);
        }
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
        Database d = new Database();
        request.setAttribute("dataerror", d.getError());
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

    public void store1HourRecord(HttpServletRequest request, HttpServletResponse response) {
        DAO dao = new DAO();
        DailyProcess dailyProcess = load.getDailyProcess();
        int shiftNo = selectShift();
        List<Integer> shiftRefList = new ArrayList<>();
        System.out.println(shiftNo);
        if (shiftNo == 1) {
            shiftRefList = dailyProcess.getShiftRefIdList().subList(0, 4);
        } else if (shiftNo == 2) {
            shiftRefList = dailyProcess.getShiftRefIdList().subList(4, dailyProcess.getShiftRefIdList().size());
        }
        String startTime = request.getParameter("startTime");

        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(Long.parseLong(startTime));
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String sDate = sf.format(calendar.getTime());
        
        Date date = new Date();
        String eDate = sf.format(date);
        
        System.out.println(eDate);
        //ArrayList<Double> errorList = Double.parseDouble(Arrays.torequest.getParameter("errorList"));
        String[] errorList = request.getParameter("errorList").split(",");
        String[] processList = request.getParameter("procList").split(",");
        for(int count=0;count<shiftRefList.size();count++){
            Report report = new Report(shiftRefList.get(count).toString(), sDate, eDate,processList[count], errorList[count]);
            dao.addReport(report);
        }
        dao.close();
    }

    private int selectShift() {
        try {
            String afternoon = "20:00:00";
            Date time1 = new SimpleDateFormat("HH:mm:ss").parse(afternoon);
            Calendar calendar1 = Calendar.getInstance();
            calendar1.setTime(time1);

            String mid = "14:00:00";
            Date time2 = new SimpleDateFormat("HH:mm:ss").parse(mid);
            Calendar calendar2 = Calendar.getInstance();
            calendar2.setTime(time2);

            String morning = "06:00:00";
            Date time3 = new SimpleDateFormat("HH:mm:ss").parse(morning);
            Calendar calendar3 = Calendar.getInstance();
            calendar3.setTime(time3);

            //current date
            SimpleDateFormat sf = new SimpleDateFormat("HH:mm:ss");
            Calendar calendar4 = Calendar.getInstance();
            String curTime = sf.format(calendar4.getTime());
             curTime = "22:00:00";
            Date time4 = new SimpleDateFormat("HH:mm:ss").parse(curTime);
            calendar4 = Calendar.getInstance();
            calendar4.setTime(time4);
            
            if (calendar4.getTime().before(calendar1.getTime()) && calendar4.getTime().after(calendar2.getTime())) {
                //checkes whether the current time is between 14:49:00 and 20:11:13.
                return 2;
            } else if (calendar4.getTime().before(calendar2.getTime()) && calendar4.getTime().after(calendar3.getTime())) {
                return 1;
            }
        } catch (ParseException ex) {
            Logger.getLogger(ManagerHomeServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return -1;
    }
}
