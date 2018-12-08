<%-- 
    Document   : signup
    Created on : 14-Nov-2018, 16:06:24
    Author     : Monika Pusz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Sign up </title>
    </head>
    <body>
        <h1>Registration</h1>
        <h3>Please enter following details to register: </h3>
        <form method="POST" action="NewCustomer.do">
            <table>
                <tr>
                    <td>Name:</td>
                    <td><input type="text"  name="user_name" placeholder="name" required/></td>
                </tr>  
                <tr>
                    <td>Address:</td>
                    <td><input type="text"  name="user_address" placeholder="address" required/></td>
                </tr> 
                <tr>
                    <td>Email address:</td>
                    <td><input type="text"  name="user_email" placeholder="email" required/></td>
                </tr>  
                <tr>
                    <td>Password: </td>
                    <td><input type="password"  name="user_password" placeholder="password" required/></td>
                </tr>  
                <tr>
                    <td></td>
                    <td><input type="hidden" name="ref" value="signup"/><input type="submit"  value="Signup"/></td>
                </tr>  
            </table>
        </form>
        <button type="button" name="back" onclick="history.back()">GO BACK</button>
        
        <%
            if(request.getAttribute("message") != null){
        %>
        <h1><%=request.getAttribute("message")%></h1>
        <%
            }
        %>
    </body>
</html>
