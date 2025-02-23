<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>CharacterAttributes</title>
    <style>
        table {
            width: 50%;
            border-collapse: collapse;
            margin-bottom: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 8px;
            text-align: left;
        }
        th {
            background-color: #f2f2f2;
        }
        .back-link {
            margin-bottom: 20px;
            display: block;
            color: blue;
            text-decoration: underline;
        }
    </style>
</head>
<body>
    <h1>${messages.title}</h1>
    <table border="1">
        <tr>
            <th>Attribute Name</th>
            <th>Attribute Value</th>
        </tr>
        <c:forEach items="${characterAttributes}" var="characterAttribute">
            <tr>
                <td><c:out value="${characterAttribute.attribute.attributeName}" /></td>
                <td><c:out value="${characterAttribute.attribute.attributeValue}" /></td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>