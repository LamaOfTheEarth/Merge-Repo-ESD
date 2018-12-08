/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Jdbc;

/**
 *
 * @author me-aydin
 */
@WebServlet(name = "Delete", urlPatterns = {"/Delete.do"})
public class Delete extends HttpServlet {

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
        HttpSession session = request.getSession(false);
        List<String> opArray = new ArrayList<String>();
        // Create array of strings
        String[] query = new String[5];
        // Request parameter and save it in array
        query[0] = (String) request.getParameter("user_name");
        query[1] = (String) request.getParameter("user_password");
        query[2] = (String) request.getParameter("user_address");
        query[3] = (String) request.getParameter("registration");
        query[4] = (String) request.getParameter("admin_password");
        //String insert = "INSERT INTO `Users` (`username`, `password`) VALUES ('";

        // Access Jdbc class and get create session  
        Jdbc jdbc = (Jdbc) session.getAttribute("dbbean");

        // Check if connection is null and display error
        if (jdbc == null) {
            request.getRequestDispatcher("/WEB-INF/conErr.jsp").forward(request, response);
        }

        // Check if username field is empty and display error message
        if (query[0].equals("")) {
            request.setAttribute("message", "Username cannot be NULL");
        }

        // Check if user and password exists in database and delete him if successful
        if (jdbc.checkCustomerUsernamePassword(query[0], query[2])) {
            if (query[4].equals("admin")) {
                jdbc.deleteCustomer(query[0]);
                request.setAttribute("message", "Username " + query[0] + " has been deleted");
                opArray.add("user");
                opArray.add("del");
            }
        }
        if (query[3] != null && !query[3].equals("")) {
            if (jdbc.isDriverExists(query[0], query[3])) {
                if (query[4].equals("admin")) {
                    jdbc.deleteDriver(query[3]);
                    request.setAttribute("message", "Driver " + query[0] + " has been deleted");
                } else {
                    request.setAttribute("message", "Wrong " + query[0] + " password! Please try again!");
                }

                opArray.add("driver");
                opArray.add("del");
            } else {
                opArray.add("driver");
                opArray.add("del");
                request.setAttribute("message", "Driver does not exist ! " + query[3]);
            }
        }
        //  Check if password exists in database 

        request.setAttribute("msg", opArray);
        request.getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
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
            Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Delete.class.getName()).log(Level.SEVERE, null, ex);
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
