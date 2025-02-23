<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Job</title>
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
				<th>jobName</th>
				<th>jobLevel</th>
				<th>experiencePoint</th>
			</tr>
			<c:forEach items="${jobRecords}" var="jobRecord">
				<tr>
					<td><c:out value="${jobRecord.getJob().getJobName()}" /></td>
					<td><c:out value="${jobRecord.getJobLevel()}" /></td>
					<td><c:out value="${jobRecord.getExperiencePoint()}" /></td>
										
				</tr>
			</c:forEach>
		</table>
</body>



</html>
