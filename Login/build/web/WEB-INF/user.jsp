<%-- 
    Document   : driver
    Created on : 01-Nov-2015, 15:18:08
    Author     : me-aydin
--%>

<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="model.Jdbc"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Register</title>
    </head>
    <body>

        <%! int i = 0;
            String str = "Register";
            String url = "NewCustomer.do";
            String header = "";
        %>
        <%
            if (request.getAttribute("msg") != null) {
                List<String> values = (ArrayList) request.getAttribute("msg");

                if (values.get(0).equals("user") & values.get(1).equals("del")) {
                    str = "Delete User";
                    url = "Delete.do";
                    header = "Delete the User";


        %>

        <h1><%=header%></h1>
        <form method="POST" action="<%=url%>">     
            <table>
                <tr>
                    <th></th>
                    <th>Please provide your following details</th>
                </tr>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" name="user_name" required/></td>
                </tr>
                <tr>
                    <td>Address:</td>
                    <td><input type="text" name="user_address" required/></td>
                </tr>
                <tr>
                    <td>Admin Password:</td>
                    <td><input type="password" name="admin_password" required/></td>
                </tr>
                <tr> 
                    <td> <input type="hidden" name="ref" value="newuserbyadmin"/><input type="submit" value="<%=str%>"/></td>
                </tr>
            </table>
        </form>   
        <%

            if (i++ > 0 && request.getAttribute("message") != null) {
                out.println(request.getAttribute("message"));
                i--;
            }
        %>
        </br>
        <button type="button" name="back" onclick="history.back()">GO BACK</button>

        <%
            }
        %>


        <%-- BREAK HERE --%>


        <%
            if (values.get(0).equals("driver") & values.get(1).equals("del")) {
                str = "Delete Driver";
                url = "Delete.do";
                header = "Delete the Driver";
        %>


        <h1><%=header%></h1>
        <form method="POST" action="<%=url%>">     
            <table>
                <tr>
                    <th></th>
                    <th>Please provide your following details</th>
                </tr>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" name="user_name" required/></td>
                </tr>
                <tr>
                    <td>Registration</td>
                    <td><input type="text" name="registration" required/></td>
                </tr>

                <tr>
                    <td>Admin Password:</td>
                    <td><input type="password" name="admin_password" required/></td>
                </tr>

                <tr> 
                    <td> <input type="hidden" name="ref" value="newuserbyadmin"/><input type="submit" value="<%=str%>"/></td>
                </tr>
            </table>
        </form>   
        <%

            if (i++ > 0 && request.getAttribute("message") != null) {
                out.println(request.getAttribute("message"));
                i--;
            }
        %>
        </br>
        <button type="button" name="back" onclick="history.back()">GO BACK</button>

        <%
            }
        %>

        <%-- BREAK HERE AGAIN NO 2--%>       

        <%
            if (values.get(0).equals("user") & values.get(1).equals("add")) {
                str = "Register User";
                url = "NewCustomer.do";
                header = "Register New Customer";
        %>


        <h1><%=header%></h1>
        <form method="POST" action="<%=url%>">     
            <table>
                <tr>
                    <th></th>
                    <th>Please provide your following details</th>
                </tr>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" name="user_name" required/></td>
                </tr>
                <tr>
                    <td>Email address:</td>
                    <td><input type="text"  name="user_email" placeholder="email" required/></td>
                </tr>  
                <tr>
                    <td>Address:</td>
                    <td><input type="text" name="user_address" required/></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" name="user_password" required/></td>
                </tr>
                <tr>
                    <td>Admin Password:</td>
                    <td><input type="password" name="admin_password" required/></td>
                </tr>

                <tr> 
                    <td> <input type="hidden" name="ref" value="newuserbyadmin"/><input type="submit" value="<%=str%>"/></td>
                </tr>
            </table>
        </form>   
        <%

            if (i++ > 0 && request.getAttribute("message") != null) {
                out.println(request.getAttribute("message"));
                i--;
            }
        %>
        </br>
        <button type="button" name="back" onclick="history.back()">GO BACK</button>

        <%
            }
        %>

        <%-- BREAK HERE AGAIN NO 3--%>   

        <%
            if (values.get(0).equals("driver") & values.get(1).equals("add")) {
                str = "Register Driver";
                url = "NewDriver";
                header = "Register New Driver";

        %>

        <h1><%=header%></h1>
        <form method="POST" action="<%=url%>">     
            <table>
                <tr>
                    <th></th>
                    <th>Please provide your following details</th>
                </tr>
                <tr>
                    <td>Name:</td>
                    <td><input type="text" name="user_name" required/></td>
                </tr>
                <tr>
                    <td>Registration</td>
                    <td><input type="text" name="registration" required/></td>
                </tr>
                <tr>
                    <td>Password:</td>
                    <td><input type="password" name="user_password" required/></td>
                </tr>
                <tr>
                    <td>Admin Password:</td>
                    <td><input type="password" name="admin_password" required/></td>
                </tr>

                <tr> 
                    <td><input type="hidden" name="ref" value="newuserbyadmin"/> <input type="submit" value="<%=str%>"/></td>
                </tr>
            </table>
        </form>   
        <%
            if (i++ > 0 && request.getAttribute("message") != null) {
                out.println(request.getAttribute("message"));
                i--;
            }
        %>
        </br>
        <button type="button" name="back" onclick="history.back()">GO BACK</button>

        <%
                }
            }
        %>
    </body>
</html>
