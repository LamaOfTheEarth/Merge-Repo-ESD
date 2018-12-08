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
        <form method="POST" action="UserService.do">
            <input class="form-control" name="date" type="date" required>
            <input type="hidden" name="tbl" value="DailyReport">
            <input type=submit value="Get Report"> 
        </form> 
        
        
        <hr/>
        <%=(String)(request.getAttribute("query"))%><br><br>
        
        
        
        
        <button type="button" name="back" onclick="history.back()">GO BACK</button>
    </body>
</html>
