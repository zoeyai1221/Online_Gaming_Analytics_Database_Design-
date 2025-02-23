<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
    <title>Create Player Account</title>
</head>
<body>
    <h1>Create PlayerAccount</h1>
    <form action="CreatePlayerAccount" method="post">
        <label for="userName">userName:</label>
        <input type="text" id="userName" name="userName" required />
        <br />
        <label for="email">email:</label>
        <input type="email" id="email" name="email" required />
        <br />
        <button type="submit">Submit</button>
    </form>

    <c:if test="${not empty message}">
        <p>${message}</p>
    </c:if>

    <a href="FindPlayerAccount">Back to Player Search</a>
</body>
</html>