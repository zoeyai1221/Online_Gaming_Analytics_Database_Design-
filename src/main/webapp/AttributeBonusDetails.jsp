<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Attribute Bonuses</title>
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
        .section-title {
            margin-top: 20px;
            margin-bottom: 10px;
            font-weight: bold;
        }
        .attribute-link {
            color: blue;
            text-decoration: underline;
        }
    </style>
</head>
<body>
	<h1>${messages.title}</h1>
	
     <c:if test="${not empty flatBonuses}">
        <table border="1">
            <tr>
                <th>Attribute Name</th>
                <th>Base Value</th>
                <th>Bonus Value</th>
            </tr>
            <c:forEach var="bonus" items="${flatBonuses}">
                <tr>
                    <td><c:out value="${bonus.getAttribute().getAttributeName()}" /></td>
                    <td><c:out value="${bonus.getAttribute().getAttributeValue()}" /></td>
                    <td><c:out value="${bonus.getFlatBonusValue()}" /></td>
                </tr>
            </c:forEach>
        </table>
    </c:if>

    <c:if test="${empty flatBonuses}">
        <p>No Attribute Bonuses found for this item.</p>
    </c:if>

    <a href="javascript:history.back()">Back</a>
</body>
</html>
