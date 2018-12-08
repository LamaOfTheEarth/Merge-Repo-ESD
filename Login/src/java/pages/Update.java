/*

 */
package pages;

import java.io.IOException;
import java.io.PrintWriter;
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
public class Update extends HttpServlet {

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
        // Access Jdbc class and get create session  
        Jdbc jdbc = (Jdbc) session.getAttribute("dbbean");
        // Check if connection is null and redirect to conErr.jsp to display error
        if (jdbc == null) {
            request.getRequestDispatcher("/WEB-INF/conErr.jsp").forward(request, response);
        } else {
            // Create array of strings
            String[] query = new String[4];

            // Request parameter and save it in array\
            
            query[0] = (String) request.getParameter("user_name");
            query[1] = (String) request.getParameter("user_password");
            query[2] = (String) request.getParameter("new_password");
            query[3] = (String) request.getParameter("new_password2");

            //String queries = "UPDATE 'User_Login' SET 'user_name' = [query[0]], 'user_password' = [query[1]]";
            // Check if new passwords are matching and display error message if they are not
            if (!query[2].trim().equals(query[3].trim())) {
                request.setAttribute("msg", "Passwords are not matching. </br> Please try again!</br>");
                request.getRequestDispatcher("/WEB-INF/passwdChange.jsp").forward(request, response);
            }
            // Check if user and password are matching (exists in the database)
            if (jdbc.checkCustomerUsernamePassword(query[0], query[1])) {
                // Check if new passwords are matching and update the database with new values
                if (query[2].trim().equals(query[3].trim())) {
                    jdbc.updateCustomerPassword(query);
                    request.setAttribute("msg", query[0] + "'s password has been changed!</br>");
                    request.getRequestDispatcher("/WEB-INF/passwdChange.jsp").forward(request, response);
                }

            }
            // Check if passwords are matching and if they are not display error message
            if (!query[2].trim().equals(query[3].trim())) {
                request.setAttribute("msg", "New passwords are not matching. </br> Please try again!</br>");
                request.getRequestDispatcher("/WEB-INF/passwdChange.jsp").forward(request, response);
            } else {   // Display error mesaage to user
                request.setAttribute("msg", "Username or password is incorrect. </br> Please try again!</br>");

            }
            // Display passwdChange.jsp file to user after request
            request.getRequestDispatcher("/WEB-INF/passwdChange.jsp").forward(request, response);

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
