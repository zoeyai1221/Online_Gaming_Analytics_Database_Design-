<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Update Character</title>
</head>
<body>
    <h1>Update Character Information</h1>
	<form action="characterupdate" method="post">
			<p>
				<label for="currentFirstName">Current First Name</label>
				<input id="currentFirstName" name="currentFirstName" value="${fn:escapeXml(param.firstName)}" readonly>
			</p>
			<p>
				<label for="currentLastName">Current Last Name</label>
				<input id="currentLastName" name="currentLastName" value="${fn:escapeXml(param.lastName)}" readonly>
			</p>
			<p>
				<label for="newFirstName">New FirstName</label>
				<input id="newFirstName" name="newFirstName" value="">
			</p>
			<p>
				<label for="newLastName">New LastName</label>
				<input id="newLastName" name="newLastName" value="">
			</p>
			<p>
				<input type="submit">
			</p>
		</form>
		
		<br/><br/>
		<p>
			<span id="successMessage"><b>${messages.success}</b></span>
		</p>
</body>
</html>
