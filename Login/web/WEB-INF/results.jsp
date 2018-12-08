<%-- 
    Document   : results
    Created on : 30-Oct-2015, 10:02:53
    Author     : me-aydin
--%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title><%=(String)(request.getAttribute("title"))%></title>
    </head>
    <body>
        <h1><%=(String)(request.getAttribute("title"))%> </h1>
        <%=(String)(request.getAttribute("query"))%><br><br>
        
        
        
        
        <button type="button" name="back" onclick="history.back()">GO BACK</button>
    </body>
</html>
