<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html> <jsp:include page="header.jsp"/>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Login </title>
    </head>
    <body>
        <br>
        <h1>Taxi Booking System</h1>
        <h3>Log in: </h3>
        <form method="POST" action="LoginPage">
            <table cellspacing="3">
                <tr>
                    <td>Username:<input type="text"  name="user_name" placeholder="username" required/> </td>
                </tr>
                <tr>
                    <td>Password: <input type="password"  name="user_password" placeholder="password" required/></td>
                </tr>
                <tr>
                    <td>User type:  <select type="text" name ="user_type">
                            <option>Customer</option>
                            <option>Driver</option>
                            <option>Head Office</option>
                        </select></td>
                </tr>

                <tr>
                    <td>
                        <input type="submit"  value="Log in"/>
                    </td>

                </tr>
            </table>
        </form>
        <br/>
        <hr/>

        <%
            if (request.getAttribute("message") != null) {
                out.println(request.getAttribute("message"));
            }
        %>
        
        
        <form method="POST" action="UserService.do">
            <input type="hidden" name="tbl" value="Book"/>
            <input type="submit" value="Make a Booking" />
        </form>
        <hr/>
        <form method="POST" action="UserService.do">
            <input type="hidden" name="tbl" value="Signup"/>
            <input type="submit" value="Signup" />
        </form>
        
        
    </body>
</html>
