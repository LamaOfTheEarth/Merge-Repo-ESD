<%-- 
    Document   : home
    Created on : 17-Nov-2018, 17:41:54
    Author     : Monika Pusz
--%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Alphacab Taxi </title>

    </head>
    <body>
        <%  String user_name = "";
        
            String [] bookingValues = (String [])request.getAttribute("bookingDetails");
            String isReload = "N";
            if(request.getAttribute("isReload") != null)
               isReload = (String)request.getAttribute("isReload");
                
            if (request.getSession().getAttribute("user_details") != null) {
                List<String> values = (ArrayList) request.getSession().getAttribute("user_details");
                user_name = values.get(0).toString();
                String user_type = values.get(1).toString();
            }
        %>  
        <img src="img/booking.png" alt="HOME"> 

        <h1>Taxi Booking System</h1>
        <h1><%=user_name%></h1>


        <% if (user_name.equals("")) { %>
        You are not logged in! Please login!
        <form method="POST" action="UserService.do">
            <input type="hidden" name="tbl" value="Login"/>
            <input type="submit" value="Login" />
        </form>
        <% } %>
        <br/>
        <% if (user_name.equals("")) { %>
        Signup now! it is free!
        <form method="POST" action="UserService.do">
            <input type="hidden" name="tbl" value="Signup"/>
            <input type="submit" value="Signup" />
        </form>
        <% } %>

        <form method="POST" action="Booking">

            <% if (user_name.equals("")) { %>
            <input style="margin-top:25px;" type="radio" name="booking_type" value="continue_booking_as_guest" checked="true">Continue Booking as Guest
            <% } %>

            <hr/>
            <table>
                <tr>
                    <td></td>
                    <td></td>
                </tr>  

                <%
                    if (user_name.equals("")) {
                %>
                <tr>
                    <td>Name</td>
                    <td><input class="form-control" name="name" type="text" placeholder="Full name" value="<% if(isReload.equals("Y")) out.print(bookingValues[0]); %>"></td>
                </tr> 
                <tr>
                    <td>Email</td>
                    <td><input class="form-control" name="email" type="text" placeholder="Email"  value="<% if(isReload.equals("Y")) out.print(bookingValues[2]); %>"></td>
                </tr> 
                <%
                    }
                %>
                <tr>
                    <td>Address</td>
                    <td><input style="width:400px;" class="form-control" name ="address" type="text" placeholder="Address"  value="<% if(isReload.equals("Y")) out.print(bookingValues[1]); %>"></td>
                </tr> 
                <tr>
                    <td>Phone</td>
                    <td><input class="form-control" name ="phone" type="text" placeholder="Phone number"  value="<% if(isReload.equals("Y")) out.print(bookingValues[3]); %>"></td>
                </tr> 

                <tr>
                    <td>Destination Location</td>
                    <td><input  style="width:400px;"  class="form-control" name ="destination" type="text" placeholder="Destination Location"  value="<% if(isReload.equals("Y")) out.print(bookingValues[4]); %>">
                    </td>
                </tr>  
                <tr>
                    <td>Pickup Date</td>
                    <td><input class="form-control" name="date" type="date" required  value="<% if(isReload.equals("Y")) out.print(bookingValues[5]); %>"></td>
                </tr>  

                <tr>
                    <td>Hour</td>
                    <td>
                        <select class="form-control" name= "hour" type="text">
                             <%
                                for(int i = 0; i<=23; i=i+1){
                                    out.print("<option");
                                    if(isReload == "Y" &&Integer.parseInt(bookingValues[6])== i)
                                        out.print(" selected ");
                                    out.print(">");
                                    out.print(i);
                                    out.print("</option>");
                                }
                            %>
                        </select>
                    </td>
                </tr>
                <tr>
                    <td>Min</td>
                    <td>
                        <select class="form-control" name="min" type="text">
                            <%
                                for(int i = 0; i<=55; i=i+5){
                                    out.print("<option");
                                    if(isReload == "Y" && Integer.parseInt(bookingValues[7])== i)
                                        out.print(" selected ");
                                    out.print(">");
                                    out.print(i);
                                    out.print("</option>");
                                }
                            %>
                            
                        </select>

                    </td>
                </tr>  
                <tr>
                    <td></td>
                    <td><button class="submit-btn">Book Now</button></td>
                </tr>  
            </table>
        </form> 
        <br>
        <%
            if(request.getAttribute("fare") != null){
        %>
        <h1><%=request.getAttribute("fare")%></h1>
        <% } %>
        
        <%
            if(request.getAttribute("message") != null){
        %>
        <h1><%=request.getAttribute("message")%></h1>
        <% } %>
        <hr/>
        <button type="button" name="back" onclick="history.back()">GO BACK</button>

    </body>
</html>
