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
        <title>Journey Management</title>
    </head>
    <body>
        <h1>Journeys</h1>
        <%
            ArrayList journeyList = (ArrayList) request.getAttribute("journeyDataList");
            String type = (String) request.getAttribute("type");
        %>
        <%

            StringBuilder b = new StringBuilder();
            String[] row;
            b.append("<table border=\"3\">");
            b.append("<tr style=\"background-color:#dfedf9\">");
            b.append("<td></td>");
            b.append("<td>Journey Id</td>");
            b.append("<td>Driver Registration No</td>");
            b.append("<td>Driver Name</td>");
            b.append("<td>Destination</td>");
            b.append("<td>Distance</td>");
            b.append("<td>Date</td>");
            b.append("<td>Time</td>");
            b.append("<td>Fare</td>");

            b.append("</tr>");
            
            for (Object s : journeyList) {
                b.append("<tr>");

                row = (String[]) s;
                b.append("<form  method=\"POST\" action=\"Journey.do\">");
                b.append("<td>");
                b.append("<input type=\"hidden\" name=\"journeyId\" value=\"" + row[0] + "\"> ");
                b.append("<input type=\"hidden\" name=\"type\" value=\"getinvoice\"> ");
                b.append("</td>");
                int i = 0;
                for (String row1 : row) {
                    b.append("<td>");
                    if(row1==null)
                        b.append("");
                    else
                        b.append(row1);
                    b.append("</td>");
                }
                
                b.append("<td>");
                b.append("<input type=\"submit\" value=\"Invoice\">");
                b.append("</td>");
                    
                b.append("</tr>\n");
                b.append("</form>");
            } // for
            b.append("</table>");
            out.print(b.toString());
        %>
       
        <%
            if(type.equals("invoice")){
        %>
        <br>
        <hr>
        <br>
        
        <h1>Invoice</h1>
        <form  method="POST" action="Journey.do">
            <input type="hidden" name="type" value="printinvoice">
            <% 
                String selectedJourneyId = (String) request.getAttribute("journeyId");
            %>
            <input type="hidden" name="journeyId" value="<%=selectedJourneyId%>">
            <%
                String invoiceId =(String) request.getAttribute("invoiceId");
                if(invoiceId.equals("-1"))
                    out.print("<input type=\"hidden\" name=\"invoiceId\" value=\"-1\">");
                else
                    out.print("<input type=\"hidden\" name=\"invoiceId\" value=\""+invoiceId+"\">");
            %>
            
            Fare :
            <%
                String fare =(String) request.getAttribute("fare");
                if(fare.equals("-1"))
                    out.print("<input type=\"text\" name=\"fare\" value=\"\">");
                else
                    out.print("<input type=\"text\" name=\"fare\" value=\""+fare+"\">" );
            %>
            
            
            <input type="submit" value="Print Invoice">
        </form>
        <%
            }
        %>
        <%
            if(request.getAttribute("msg") != null){
        %>
        <h1><%=(String) request.getAttribute("msg")%></h1>
        <%
            }
        %>
        <br>
        <hr>
        <button type="button" name="back" onclick="history.back()">GO BACK</button>
        <hr/>
        <form action="Logout">
            <input type="submit" value="Log out" />
        </form>
    </body>
</html>
