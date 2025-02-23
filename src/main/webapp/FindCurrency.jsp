<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Currency</title>
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
		<table border="1">
			<tr>
				<th>currencyName</th>
				<th>totalAmount</th>
				<th>totalCap</th>
				<th>weeklyEarned</th>
				<th>weeklyCap</th>
				<th>isDiscontinued</th>
				
			</tr>
			<c:forEach items="${characterCurrencies}" var="characterCurrency">
				<tr>
					<td><c:out value="${characterCurrency.getCurrency().getCurrencyName()}" /></td>
					<td><c:out value="${characterCurrency.getTotalAmount()}" /></td>
					<td><c:out value="${characterCurrency.getCurrency().getCap()}" /></td>
					<td><c:out value="${characterCurrency.getWeeklyEarned()}" /></td>	
					<td><c:out value="${characterCurrency.getCurrency().getWeeklyCap()}" /></td>
					<td><c:out value="${characterCurrency.getCurrency().isDiscontinued()}" /></td>			
				</tr>
			</c:forEach>
		</table>
</body>
</html>