<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <title>Delete Player Account</title>
</head>
<body>
    <h1>Delete PlayerAccount</h1>
    <form action="DeletePlayerAccount" method="post">
        <label for="userName">User Name:</label>
        <input type="text" id="userName" name="userName" value="${userName}" readonly />
        <br />
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" value="${email}" readonly />
        <br />
        <input type="hidden" id="accountID" name="accountID" value="${accountID}" />
        <button type="submit">Submit</button>
    </form>


    <c:if test="${not empty message}">
        <p>${message}</p>
    </c:if>

    <a href="FindPlayerAccount">Back to Player Search</a>
</body>
</html>