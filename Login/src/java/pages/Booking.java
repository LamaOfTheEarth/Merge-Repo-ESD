/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
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
public class Booking extends HttpServlet {

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
            HttpSession session = request.getSession(false);

            // Create array of strings
            String[] query = new String[9];
            // Request parameter and save it in array
            String username = null;
            String email = "";
            boolean isAllChecksAreOk = true;

            boolean isGuest = true;
            if (request.getSession().getAttribute("user_details") != null) {
                List<String> values2 = (ArrayList) request.getSession().getAttribute("user_details");
                username = values2.get(0);
                email = values2.get(2);
                isGuest = false;
            }

            if (isGuest) {
                query[0] = (String) request.getParameter("name");
            } else {
                query[0] = username;
            }
            query[1] = (String) request.getParameter("address");

            if (isGuest) {
                query[2] = (String) request.getParameter("email");
            } else {
                query[2] = email;
            }

            query[3] = (String) request.getParameter("phone");
            query[4] = (String) request.getParameter("destination");
            query[5] = (String) request.getParameter("date");
            query[6] = (String) request.getParameter("hour");
            query[7] = (String) request.getParameter("min");

            // Access Jdbc class and get create session  
            Jdbc jdbc = (Jdbc) session.getAttribute("dbbean");

            // Check if connection is null and display error
            if (jdbc == null) {
                request.getRequestDispatcher("/WEB-INF/conErr.jsp").forward(request, response);
            }

            if (query[0].equals("") && username == null) {
                isAllChecksAreOk = false;
                request.setAttribute("message", "Name cannot be emtpty");
            } else if (query[1].equals("") || query[4].equals("")) {
                isAllChecksAreOk = false;
                request.setAttribute("message", "Address and destination fields cannot be emtpty");
            } else if (query[2].equals("")  && username == null) {
                isAllChecksAreOk = false;
                request.setAttribute("message", "Email cannot be emtpty");
            }

            ArrayList availableDriverList = jdbc.getAnyAvailableDriver(request.getParameter("date"), 
                    Integer.parseInt(request.getParameter("hour")),
                    request.getParameter("min"));
            Distance distance = getDistance(query[1], query[4]);
            query[8] = distance.distanceInMeters+"";
            
            double miles = distance.distanceInMeters * 0.00062137119;
            double fare = 0;
            
            if(miles <= 5){
                fare = miles*3+miles*2*20/100;
            }
            else{
                fare = miles*6+miles*6*20/100;
            }
                
            
            request.setAttribute("fare", "Total Distance : " + distance.distanceInText + " Total Fare : " + 
                    fare + " GBP (including 20% Tax)");
            
            request.setAttribute("isReload", "N");
            if (availableDriverList.size() == 0) {
                request.setAttribute("message", " There is no available driver, please change your booking time!");
                request.setAttribute("isReload", "Y");
            } else if (isAllChecksAreOk) {
                jdbc.insertDemands(query);
                request.setAttribute("message", "Demand added to the system!");
            } else {
                request.setAttribute("isReload", "Y");
                request.setAttribute("message", " There has been a problem \n\nP\n"
                        + "                lease try again!");
            }

            request.setAttribute("bookingDetails", query);
            //AIzaSyAVNPjdVouiPOjbhAYo_GtOEqC2kYlCHTI
            request.getRequestDispatcher("/WEB-INF/booking.jsp").forward(request, response);
        }
    }

    public Distance getDistance(String from, String to) {

        Client client = ClientBuilder.newClient();
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?";
        WebTarget target = client.target(url).queryParam("units", "imperial").queryParam("origins", from)
                .queryParam("destinations", to)
                .queryParam("key", "AIzaSyAVNPjdVouiPOjbhAYo_GtOEqC2kYlCHTI");
        Response response = target.request(MediaType.APPLICATION_JSON).get();
        String jsonResponse = response.readEntity(String.class);
        JsonReader reader = Json.createReader(new StringReader(jsonResponse));
        JsonObject jsonObject = reader.readObject();

        JsonArray rows = jsonObject.getJsonArray("rows");
        JsonObject elements = rows.getJsonObject(0);
        JsonArray elementsArray = elements.getJsonArray("elements");
        JsonObject element0 = elementsArray.getJsonObject(0);
        JsonObject distanceObject = element0.getJsonObject("distance");

        Distance distance = new  Distance();
        distance.distanceInMeters = distanceObject.getInt("value");
        distance.distanceInText = distanceObject.getString("text");

        return distance;
    }
    public class Distance{
        int distanceInMeters;
        String distanceInText;
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
