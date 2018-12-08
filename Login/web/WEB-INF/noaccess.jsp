<%-- 
    Document   : noaccess
    Created on : 18-Nov-2018, 16:27:17
    Author     : Monika Pusz
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html><jsp:include page="header.jsp"/>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>ERROR!</title>
    </head>
    <body>
        <h1>You have no access to this webpage! </h1>
        <br><br>
        <button type="button" name="back" onclick="history.back()">GO BACK</button>
        <jsp:include page="foot.jsp"/>
    </body>
</html>
