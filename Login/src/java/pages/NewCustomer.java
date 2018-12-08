/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pages;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.Jdbc;

/**
 *
 * @author me-aydin
 */
public class NewCustomer extends HttpServlet {

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

        HttpSession session = request.getSession(false);

        // Create array of strings
        String[] query = new String[6];
        // Request parameter and save it in array
        query[0] = (String) request.getParameter("user_name");
        query[1] = (String) request.getParameter("user_address");
        query[2] = (String) request.getParameter("user_id");
        query[3] = (String) request.getParameter("user_password");
        query[4] = (String) request.getParameter("admin_password");
        query[5] = (String) request.getParameter("user_email");
        String ref = (String) request.getParameter("ref");
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

        // Check if password field is empty and display error message
        if (query[3].equals("")) {
            request.setAttribute("message", "Password cannot be NULL");
        }
        // Check if user already exists in the system and display error message
        if (jdbc.isCustomerExists(query[0])) {
            request.setAttribute("message", query[0] + " already exists in the system. Please try again!");
        } // If no errors found, save new user and password in database
        else {
            if (ref.equals("signup") || query[4].equals("admin")) {
                jdbc.insertCustomer(query);
                request.setAttribute("message", "Account : " + query[0]+ " created successfully!");
            }
        }

        String name = "user";
        String action = "add";
        List<String> strg = new ArrayList<String>();
        strg.add(name);
        strg.add(action);
        request.setAttribute("msg", strg);

        if(!ref.equals("signup"))
            request.getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
        else
            request.getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
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
