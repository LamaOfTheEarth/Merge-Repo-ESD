/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com;

import java.io.IOException;
//import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
public class UserServLet extends HttpServlet {

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
        HttpSession session = request.getSession();

        response.setContentType("text/html;charset=UTF-8");
        Jdbc dbBean = new Jdbc();
        dbBean.connect((Connection) request.getServletContext().getAttribute("connection"));
        session.setAttribute("dbbean", dbBean);

        String qry_customers = "select * from Taxi.Customer";

        String qry_drivers = "select * from Taxi.Drivers";

        String qry_bookings = "select * from Taxi.Journey";

        String qry_demands = "select * from Taxi.Demands d where d.id not in (select j.demand_id from taxi.journey j) order by d.date desc";

        String qry_journeys = "";
        if (request.getSession().getAttribute("user_details") != null) {

            List<String> values2 = (ArrayList) request.getSession().getAttribute("user_details");

            String username = values2.get(0);
            qry_journeys = "select j.DATE, j.TIME, j.PICKUP from_address, j.DESTINATION to_address, j.DISTANCE, i.FARE   "
                    + " from taxi.customer c, taxi.demands d,  taxi.journey j left join taxi.INVOICE i on i.JOURNEY_ID=j.JID "
                    + " where c.NAME='" + username + "' and c.NAME=d.name and d.id=j.demand_id order by j.DATE desc, j.TIME desc";

        }
        String qry_daily_turnover = "select j.DATE journey_date, sum(i.fare) total_fare "
                + "                    from taxi.JOURNEY j, taxi.DRIVERS d, TAXI.INVOICE i "
                + "                   where d.REGISTRATION=j.REGISTRATION"
                + " and i.JOURNEY_ID=j.JID "
                + " group by j.DATE"
                + " order by j.date desc"
                + "";

        String qry_jobs = "";

        if (request.getSession().getAttribute("user_details") != null) {

            List<String> values2 = (ArrayList) request.getSession().getAttribute("user_details");

            String driver = values2.get(0);

            if (driver.equals("admin")) {

                qry_jobs = "SELECT journey.jid, drivers.registration, drivers.name,"
                        + " journey.destination, journey.distance, journey.date,"
                        + " journey.time, fare"
                        + " FROM drivers,journey left join invoice on jid= journey_id "
                        + " WHERE drivers.registration=journey.registration and journey.date={journeyDate} "
                        + " order by journey.date desc";
            } else {
                qry_jobs = "SELECT journey.jid, drivers.registration, drivers.name,"
                        + " journey.destination, journey.distance, journey.date,"
                        + " journey.time, fare"
                        + " FROM drivers,journey left join invoice on jid= journey_id "
                        + " WHERE drivers.registration=journey.registration and "
                        + " drivers.registration = '" + driver + "' and journey.date={journeyDate}"
                        + " order by journey.date desc";
            }

        }

        if ((Connection) request.getServletContext().getAttribute("connection") == null) {
            request.getRequestDispatcher("/WEB-INF/conErr.jsp").forward(request, response);
        }
        if (request.getAttribute("login") != null) {
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("ListCustomers")) {
            String msg = "No users";
            String title = "Customers";
            try {
                msg = dbBean.retrieve(qry_customers, Arrays.asList("Name", "Address", "Id", "Password", "Email"));
            } catch (SQLException ex) {
                Logger.getLogger(UserServLet.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("query", msg);
            request.setAttribute("title", title);

            request.getRequestDispatcher("/WEB-INF/results.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("ListDrivers")) {
            String msg = "No users";
            String title = "Drivers";
            try {
                msg = dbBean.retrieve(qry_drivers, Arrays.asList("Registration", "Name", "Password"));
            } catch (SQLException ex) {
                Logger.getLogger(UserServLet.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("query", msg);
            request.setAttribute("title", title);
            request.getRequestDispatcher("/WEB-INF/results.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("ListBookings")) {
            String msg = "No users";
            String title = "Bookings";
            try {
                msg = dbBean.retrieve(qry_bookings, Arrays.asList("Journey Id", "Destination", "Distance",
                        "Driver Registration", "Pickup", "Date", "Time", "Demand Id", "Status"));
            } catch (SQLException ex) {
                Logger.getLogger(UserServLet.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("query", msg);
            request.setAttribute("title", title);
            request.getRequestDispatcher("/WEB-INF/results.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("ManageDemands")) {
            String msg = "No demands";
            String title = "Demands";
            ArrayList rawDataList = null;
            try {
                rawDataList = dbBean.getRawDataList(qry_demands);
                msg = "";
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(UserServLet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("demandDataList", rawDataList);
            request.setAttribute("query", msg);
            request.setAttribute("title", title);

            request.getRequestDispatcher("/WEB-INF/demandmanagement.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("DriverListJobs")) {
            String msg = "No Journeys";
            String title = "Journeys";
            ArrayList rawDataList = null;
            try {
                qry_jobs = qry_jobs.replace("{journeyDate}", "journey.date");
                rawDataList = dbBean.getRawDataList(qry_jobs);
                msg = "";
            } catch (SQLException ex) {
                ex.printStackTrace();
                Logger.getLogger(UserServLet.class.getName()).log(Level.SEVERE, null, ex);
            }

            request.setAttribute("type", "listjourneys");
            request.setAttribute("journeyDataList", rawDataList);
            request.setAttribute("query", msg);
            request.setAttribute("title", title);

            request.getRequestDispatcher("/WEB-INF/driverjourneymanagement.jsp").forward(request, response);

            //
        } else if (request.getParameter("tbl").equals("DailyReport")) {
            String msg = "No users";
            String title = "Daily Report";
            String dateOfReport = request.getParameter("date");
            try {
                if (dateOfReport != null && !dateOfReport.equals("")) {
                    qry_jobs = qry_jobs.replace("{journeyDate}", "\'"+dateOfReport+"\'");
                } else {
                    qry_jobs = qry_jobs.replace("{journeyDate}", "journey.date");
                }

                msg = dbBean.retrieve(qry_jobs, Arrays.asList("Journey Id", "Driver Registration",
                        "Driver Name", "Destination", "Distance", "Date", "Time", "Fare"));
            } catch (SQLException ex) {
                Logger.getLogger(UserServLet.class.getName()).log(Level.SEVERE, null, ex);
            }
            msg += "<br/><br/>";

            if (request.getSession().getAttribute("user_details") != null) {

                List<String> values2 = (ArrayList) request.getSession().getAttribute("user_details");

                String username = values2.get(0);

                if (username.equals("admin")) {
                    //j.DATE journey_date, sum(i.fare) total_fare
                    msg += "<h1>Daily Total Fare</h1>";
                    try {
                        msg += dbBean.retrieve(qry_daily_turnover, Arrays.asList("Journey Date", "Total Fare"));
                    } catch (SQLException ex) {
                        Logger.getLogger(UserServLet.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }

            request.setAttribute("title", title);
            request.setAttribute("query", msg);
            request.getRequestDispatcher("/WEB-INF/admindailyreport.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("NewUser")) {
            String name = "user";
            String action = "add";
            List<String> strg = new ArrayList<String>();
            strg.add(name);
            strg.add(action);
            request.setAttribute("msg", strg);
            request.setAttribute("tbl", request.getParameter("tbl"));
            request.getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("NewDriver")) {
            String name = "driver";
            String action = "add";
            List<String> strg = new ArrayList<String>();
            strg.add(name);
            strg.add(action);
            request.setAttribute("tbl", request.getParameter("tbl"));
            request.setAttribute("msg", strg);
            request.getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("Update")) {
            request.getRequestDispatcher("/WEB-INF/passwdChange.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("Login")) {
            request.getRequestDispatcher("/WEB-INF/login.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("Signup")) {
            request.getRequestDispatcher("/WEB-INF/signup.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("Book")) {
            request.getRequestDispatcher("/WEB-INF/booking.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("Admin")) {
            request.getRequestDispatcher("/WEB-INF/adminhome.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("Driver")) {
            request.getRequestDispatcher("/WEB-INF/driverhome.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("User")) {
            request.getRequestDispatcher("/WEB-INF/userhome.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("DeleteUser")) {
            String name = "user";
            String action = "del";
            List<String> strg = new ArrayList<String>();
            strg.add(name);
            strg.add(action);
            request.setAttribute("msg", strg);
            request.setAttribute("tbl", request.getParameter("tbl"));
            request.getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);
        } else if (request.getParameter("tbl").equals("DeleteDriver")) {
            String name = "driver";
            String action = "del";
            List<String> strg = new ArrayList<String>();
            strg.add(name);
            strg.add(action);
            request.setAttribute("msg", strg);
            request.setAttribute("tbl", request.getParameter("tbl"));
            request.getRequestDispatcher("/WEB-INF/user.jsp").forward(request, response);

        } else if (request.getParameter("tbl").equals("History")) {
            String msg = "No demands";
            String title = "History";

            List<String> values2 = (ArrayList) request.getSession().getAttribute("user_details");
            String username = values2.get(0);

            try {
                msg = dbBean.retrieve(qry_journeys, Arrays.asList("Date", "Time", "Pickup Address", "Destination Address", "Distance", "Fare"));
            } catch (SQLException ex) {
                Logger.getLogger(UserServLet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.setAttribute("tbl", request.getParameter("tbl"));
            request.setAttribute("query", msg);
            request.setAttribute("title", title);
            request.getRequestDispatcher("/WEB-INF/results.jsp").forward(request, response);
        } else {
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
