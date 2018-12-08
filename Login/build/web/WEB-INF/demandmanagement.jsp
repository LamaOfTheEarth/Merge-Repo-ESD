<%-- 
    Document   : results
    Created on : 30-Oct-2015, 10:02:53
    Author     : me-aydin
--%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Demand Management</title>
    </head>
    <body>
        <h1>Demands Which Not Assigned</h1>
        <%
            ArrayList demandsNotAssignedList = (ArrayList) request.getAttribute("demandDataList");
            ArrayList driverList = (ArrayList) request.getAttribute("driverList");
        %>
        <%
            StringBuilder b = new StringBuilder();
            String[] row;
            b.append("<table border=\"3\">");
            b.append("<tr style=\"background-color:#dfedf9\">");
             b.append("<td></td>");
            b.append("<td>Demand Id</td>");
            b.append("<td>Name</td>");
            b.append("<td>Address</td>");
            b.append("<td>Destination</td>");
            b.append("<td>Date</td>");
            b.append("<td>Time</td>");
            b.append("<td>Email</td>");
            b.append("<td>Phone</td>");
            b.append("<td>Distance</td>");

            b.append("</tr>");
            for (Object s : demandsNotAssignedList) {
                b.append("<tr>");

                row = (String[]) s;
                b.append("<form  method=\"POST\" action=\"Demand.do\">");
                b.append("<td>");
                b.append("<input type=\"hidden\" name=\"demandId\" value=\"" + row[0] + "\"> ");
                b.append("<input type=\"hidden\" name=\"type\" value=\"getdrivers\"> ");
                b.append("</td>");
                int i = 0;
                for (String row1 : row) {
                    b.append("<td>");
                    b.append(row1);
                    b.append("</td>");
                }
                
                b.append("<td>");
                b.append("<input type=\"submit\" value=\"Get Drivers\">");
                b.append("</td>");
                    
                b.append("</tr>\n");
                b.append("</form>");
            } // for
            b.append("</table>");
            out.print(b.toString());
        %>
        <%
            if(driverList != null && driverList.size()>0){
        %>
        <br>
        <hr>
        <br>
        
        <h1>Available Drivers</h1>
        <form  method="POST" action="Demand.do">
            <input type="hidden" name="type" value="assigndriver">
            <% 
                String selectedDemandId = (String) request.getAttribute("selectedDemandId");
            %>
            <input type="hidden" name="demandId" value="<%=selectedDemandId%>">
            <select name="selectedDriver">
                <option value="-">Select Driver</option>
                <%
                    String optionList="";
                    for(Object o : driverList){
                        String [] row1 = (String[]) o;
                        optionList += "<option value=\""+row1[0]+"\">"+row1[1]+"</option>";
                    }
                    out.print(optionList);
                %>
            </select>
            
            <input type="submit" value="Assign Driver">
        </form>
        <%
            }
        %>
        <% if(request.getAttribute("msg") != null){ %>
        <h1><%=(String) request.getAttribute("msg")%></h1>
        <%
        }
        %>
        <br>
        <hr>
        <button type="button" name="back" onclick="history.back()">GO BACK</button>
    </body>
</html>
