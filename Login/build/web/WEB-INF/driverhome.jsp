<%-- 
    Document   : driverhome
    Created on : 18-Nov-2018, 14:41:58
    Author     : Monika Pusz
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><jsp:include page="header.jsp"/>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>DRIVER HOMEPAGE</title>
    </head>
    <body>
        <%

//            if (request.getSession().getAttribute("user_details") == null) {
//                request.setAttribute("login", true);
//                request.getRequestDispatcher("UserService.do").forward(request, response);
//            }
//
//            if (request.getSession().getAttribute("user_details") != null) {
//                List<String> values = (ArrayList) request.getSession().getAttribute("user_details");
//                String user_name = values.get(0).toString();
//                String user_type = values.get(1).toString();
//
//                //if(request.getSession().getAttribute(user_name) == "admin" & request.getSession().getAttribute(user_type) == "Admin"  )
//                if (values.get(0) != null && (values.get(1).equals("Driver") || (values.get(0).equals("admin") && values.get(1).equals("Head Office")))) {
//                    System.out.println("WE REACH HERE");
//                    System.out.println(request.getSession().getAttribute("user_details"));
//                    //System.out.println(values.get(0) + " HERE ");
        %>
        <h1>DRIVER HOME PAGE </h1>

        <form method="POST" action="UserService.do">

            <p />

            View a table <br />

            <input type="radio" name="tbl" value="DriverListJobs">Manage Jobs<br />
            <input type=submit value="Confirm"> <br />
            <br>

        </form> 
        <%
//                } else {
//                    System.out.println("YOU ARE NOT DRIVER OR ADMIN!");
//                    request.getRequestDispatcher("/WEB-INF/noaccess.jsp").forward(request, response);
//                }
//            }
        %>
        <form action="Logout">
            <input type="submit" value="Log out" />
        </form>
    </body>
</html>
