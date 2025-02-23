<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Create Character</title>
</head>
<body>
    <h1>Create a New Character</h1>


    <!-- Form for character creation -->
    <form method="post" action="charactercreate">
   		<p>
			<label for="playerid">Player ID:</label>
            <input id="playerid" name="playerid" value="${fn:escapeXml(param.playerid)}"/>
		</p>  
		<p>
			<label for="firstName">First Name:</label>
            <input id="firstName" name="firstName" value="" />
		</p>
        <p>
			<label for="lastName">Last Name:</label>
            <input id="lastName" name="lastName" value=""/>
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
