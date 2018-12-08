<%-- 
    Document   : passwdChange
    Created on : 11-Mar-2016, 01:02:16
    Author     : me-aydin
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Change Password</title>
    </head>
    <body>
        <h2>Password change</h2>
           <form method="POST" action="Update.do">     
            <table>
                <tr>
                    <th></th>
                    <th>Please provide your following details</th>
                </tr>
                <tr>
                    <td>Username:</td>
                    <td><input type="text" name="user_name" required=""/></td>
                </tr>
                <tr>
                    <td>Old Password:</td>
                    <td><input type="password" name="user_password" required=""/></td>
                </tr>
                <tr>
                    <td>New Password:</td>
                    <td><input type="password" name="new_password" required=""/></td>
                </tr>
                <tr>
                    <td>Confirm Password:</td>
                    <td><input type="password" name="new_password2" required=""/></td>
                </tr>
                <tr> 
                    <td> <input type="submit" value="Change"/></td>
                </tr>
            </table>
        </form>
        <%=((String)(request.getAttribute("msg"))!=null)?(String)(request.getAttribute("msg")):""%>
         <button type="button" name="back" onclick="history.back()">GO BACK</button>
    </body>
</html>
