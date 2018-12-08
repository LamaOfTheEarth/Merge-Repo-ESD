<%-- 
    Document   : index
    Created on : 09-Mar-2016, 16:52:19
    Author     : me-aydin
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><jsp:include page="/WEB-INF/header.jsp"/>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Taxi Booking System</title>
    </head>
    <body>
        <%
            //System.out.println(request.getSession().getAttribute("user_details").toString());
           
//            if (request.getSession().getAttribute("user_details") == null) {
//                
//                System.out.println("hi2");
//                request.setAttribute("login", true);
//                request.getRequestDispatcher("UserService.do").forward(request, response);
//            } else {
//                List<String> values = (ArrayList) request.getSession().getAttribute("user_details");
//                String user_name = values.get(0).toString();
//                //System.out.println("USERNAME " + user_name);
//                String user_type = values.get(1).toString();
//                //System.out.println("USER TYPE " + user_type);
//                System.out.println("WE REACH HERE2222222");
//                System.out.println(request.getSession().getAttribute("user_details"));
//                System.out.println("WE REACH HERE");
//                //System.out.println(values.get(0) + " HERE ");
        %>
        <h1>Taxi Booking System</h1>
        <form method="POST" action="UserService.do">
            <p />
            View a table <br />
            <%
                //if (user_type.equals("Customer")) {
            %>
            <input type="radio" name="tbl" value="User">USER PAGE<br />
            <% //} %>
            <%
                //if (user_type.equals("Head Office")) {
            %>
            <input type="radio" name="tbl" value="Admin">ADMIN PAGE<br />
            <% //} %>
            <%
                //if (user_type.equals("Head Office")) {
            %>
            <input type="radio" name="tbl" value="Driver">DRIVER PAGE<br />
            <%//} %>
            <input type=submit value="Confirm"> <br />

            <div id="top" style="float: top;">
                Hello ${user_details[0]}
            </div>
        </form> 

        <br>
        <br>
        <form action="Logout">
            <input type="submit" value="Log out" />
        </form>

        <%
            //}

        %>
    </body>
</html>
