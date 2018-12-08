/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import com.UserServLet;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import model.Jdbc;

/**
 *
 * @author Mandoka
 */
public class Journey extends HttpServlet {

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
            throws ServletException, IOException, SQLException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            HttpSession session = request.getSession(false);
            Jdbc jdbc = (Jdbc) session.getAttribute("dbbean");
            if (jdbc == null) {
                request.getRequestDispatcher("/WEB-INF/conErr.jsp").forward(request, response);
            }
            // Create array of strings

            if (((String) request.getParameter("type")).equals("getinvoice")) {
                String journeyId = (String) request.getParameter("journeyId");

                List<String> values2 = (ArrayList) request.getSession().getAttribute("user_details");
                String driver = values2.get(0);

                String qry_jobs = "SELECT journey.jid,drivers.registration, drivers.name,"
                        + " journey.destination, journey.distance, journey.date,"
                        + " journey.time, fare"
                        + " FROM drivers,journey left join invoice on jid= journey_id "
                        + " WHERE drivers.registration=journey.registration and "
                        + " drivers.registration = '" + driver + "'"
                        + " order by journey.date desc";

                ArrayList rawDataList = null;
                rawDataList = jdbc.getRawDataList(qry_jobs);

                request.setAttribute("journeyDataList", rawDataList);

                request.setAttribute("journeyId", journeyId);
                ArrayList invoiceListItem = jdbc.getInvoiceByJourneyId(Integer.parseInt(journeyId));
                if (invoiceListItem.size() > 0) {
                    String[] invoiceFields = (String[]) invoiceListItem.get(0);
                    request.setAttribute("type", "invoice");
                    request.setAttribute("invoiceId", invoiceFields[0]);
                    request.setAttribute("fare", invoiceFields[2]);
                } else {
                    request.setAttribute("type", "invoice");
                    request.setAttribute("invoiceId", "-1");
                    request.setAttribute("fare", "-1");
                }

            } else if (((String) request.getParameter("type")).equals("printinvoice")) {
                String journeyId = (String) request.getParameter("journeyId");
                String invoiceId = (String) request.getParameter("invoiceId");
                String fare = (String) request.getParameter("fare");

                List<String> values2 = (ArrayList) request.getSession().getAttribute("user_details");
                String driver = values2.get(0);


                
                request.setAttribute("journeyId", journeyId);
                

                if (invoiceId.equals("-1")) {
                    int invoiceIdInt = jdbc.insertInvoice(Integer.parseInt(journeyId), Double.parseDouble(fare));
                    invoiceId = invoiceIdInt+"";
                } else {
                    jdbc.updateInvoice(Integer.parseInt(invoiceId), Double.parseDouble(fare));
                }

                jdbc.updateJourneyAsCompleted(Integer.parseInt(journeyId));
                String qry_jobs = "SELECT journey.jid,drivers.registration, drivers.name,"
                        + " journey.destination, journey.distance, journey.date,"
                        + " journey.time, fare"
                        + " FROM drivers,journey left join invoice on jid= journey_id "
                        + " WHERE drivers.registration=journey.registration and "
                        + " drivers.name = '" + driver + "'"
                        + " order by journey.date desc";

                ArrayList rawDataList = null;
                rawDataList = jdbc.getRawDataList(qry_jobs);
                request.setAttribute("journeyDataList", rawDataList);
                
                
                ArrayList invoiceListItem = jdbc.getInvoice(Integer.parseInt(invoiceId));
                if (invoiceListItem.size() > 0) {
                    String[] invoiceFields = (String[]) invoiceListItem.get(0);
                    request.setAttribute("type", "printinvoice");
                    request.setAttribute("invoiceId", invoiceFields[0]);
                    request.setAttribute("fare", invoiceFields[2]);
                }

            }

            request.getRequestDispatcher("/WEB-INF/driverjourneymanagement.jsp").forward(request, response);
        } catch (Exception e) {
            e.printStackTrace();
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
        try {

            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Demand.class.getName()).log(Level.SEVERE, null, ex);
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
        try {
            processRequest(request, response);
        } catch (SQLException ex) {
            Logger.getLogger(Demand.class.getName()).log(Level.SEVERE, null, ex);
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
