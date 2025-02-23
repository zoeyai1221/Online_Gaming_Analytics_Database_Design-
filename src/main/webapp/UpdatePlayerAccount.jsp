<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
    <title>Update Player Account</title>
</head>
<body>
    <h1>Update PlayerAccount</h1>
    <form action="UpdatePlayerAccount" method="post">
        <input type="hidden" id="accountID" name="accountID" value="${accountID}" />
        <label for="userName">Current userName:</label>
        <input type="text" id="userName" name="userName" value="${userName}" readonly />
        <br />
        <label for="newUserName">New userName:</label>
        <input type="text" id="newUserName" name="newUserName" />
        <br />
        <label for="newEmail">New email:</label>
        <input type="email" id="newEmail" name="newEmail" value="${email}" />
        <br />
        <button type="submit">Submit</button>
    </form>

    <c:if test="${not empty message}">
        <p>${message}</p>
    </c:if>

    <a href="FindPlayerAccount">Back to Player Search</a>
</body>
</html>