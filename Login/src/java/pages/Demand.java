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
public class Demand extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    String qry_demands = "select * from Taxi.Demands d where d.id not in (select j.demand_id from taxi.journey j ) order by d.date desc";

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

            if (((String) request.getParameter("type")).equals("getdrivers")) {
                String demandId = (String) request.getParameter("demandId");
                ArrayList demandListItem = jdbc.getDemand(Integer.parseInt(demandId));
                String[] demandFields = (String[]) demandListItem.get(0);

                ArrayList availableDriverList = jdbc.getAnyAvailableDriver(demandFields[4],
                        Integer.parseInt(demandFields[5].substring(0, 2)),
                        demandFields[5].substring(3, 5));

                System.out.println("##" + demandFields[5]);
                System.out.println("##" + demandFields[5].substring(0, 2));
                System.out.println("##" + demandFields[5].substring(3, 5));
                ArrayList rawDemandList = null;
                try {
                    rawDemandList = jdbc.getRawDataList(qry_demands);
                } catch (SQLException ex) {
                    Logger.getLogger(UserServLet.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("demandDataList", rawDemandList);
                request.setAttribute("selectedDemandId", demandId);
                request.setAttribute("driverList", availableDriverList);

            } else if (((String) request.getParameter("type")).equals("assigndriver")) {
                String demandId = (String) request.getParameter("demandId");
                String driverRegistrationNo = (String) request.getParameter("selectedDriver");
                System.out.println(driverRegistrationNo);

                ArrayList demandListItem = jdbc.getDemand(Integer.parseInt(demandId));
                String[] demandFields = (String[]) demandListItem.get(0);
                String[] inputs = new String[9];

                inputs[0] = demandFields[3];//dest
                inputs[1] = demandFields[8];//distance
                inputs[2] = driverRegistrationNo;//registration
                inputs[3] = demandFields[2];//pickup
                inputs[4] = demandFields[4];//date
                inputs[5] = demandFields[5];//time
                inputs[6] = demandId;//demandid
                jdbc.insertJourney(inputs);
                request.setAttribute("msg", "Driver Assigned! ");

                ArrayList rawDemandList = null;
                try {
                    rawDemandList = jdbc.getRawDataList(qry_demands);
                } catch (SQLException ex) {
                    Logger.getLogger(UserServLet.class.getName()).log(Level.SEVERE, null, ex);
                }
                request.setAttribute("demandDataList", rawDemandList);
            }

            request.getRequestDispatcher("/WEB-INF/demandmanagement.jsp").forward(request, response);
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
