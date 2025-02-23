<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Delete Character</title>
</head>
<body>
    <h1>${messages.title}</h1>

    <!-- Form for character deletion -->
    <form method="post" action="characterdelete">
        <p>
			<div <c:if test="${messages.disableSubmit}">style="display:none"</c:if>>
				<label for="characterID">Character ID: </label>
				<input id="characterID" name="characterID" value="${fn:escapeXml(param.characterID)}">
			</div>
		</p>
		<p>
			<span id="submitButton" <c:if test="${messages.disableSubmit}">style="display:none"</c:if>>
			<input type="submit">
			</span>
		</p>
    </form>
    <br/><br/>
</body>
</html>
