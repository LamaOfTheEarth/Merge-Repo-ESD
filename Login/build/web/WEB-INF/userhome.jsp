<%-- 
    Document   : userhome
    Created on : 18-Nov-2018, 14:41:31
    Author     : Monika Pusz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><jsp:include page="header.jsp"/>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>USER HOME PAGE</title>
    </head>
    <body>
        <%

//            if (request.getSession().getAttribute("user_details") == null) {
//                request.setAttribute("login", true);
//                request.getRequestDispatcher("UserService.do").forward(request, response);
//            }
//
//          

        %>

        <h1>USER HOMEPAGE</h1>
        <form method="POST" action="UserService.do">
            <p />
            View a table <br />
            <input type="radio" name="tbl" value="Book">Create New Booking<br />
            <input type="radio" name="tbl" value="Update">Change Password <br />
            <input type="radio" name="tbl" value="History">Display Trip History<br />
            <br>
            <input type=submit value="Confirm"> <br />
        </form> 
        <br>
        <br>      
        <br>
        <form action="Logout">
            <input type="submit" value="Log out" />
        </form>


        <% //              }

        %>
    </body>
</html>
