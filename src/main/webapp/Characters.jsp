<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Characters</title>
     <style>
        table {
            width: 100%;
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
	<a href="FindPlayerAccount" class="nav-link">Back to Player Search</a>
    <h1>${messages.title}</h1>
    <table border="1">
           <tr>
               <th>First Name</th>
               <th>Last Name</th>
               <th>Attribute</th>
               <th>Inventory</th>
               <th>Currency</th>
               <th>Job</th>
               <th>Equipped Item</th>
               <th>Delete Character</th>
               <th>Update Character</th>
               
           </tr>
           <c:forEach items="${characters}" var="character" >
               <tr>
                   <td><c:out value="${character.getFirstName()}" /></td>
                   <td><c:out value="${character.getLastName()}" /></td>
                   <td><a href="FindCharacterAttribute?characterId=<c:out value="${character.getCharacterID()}"/>">Attribute</a></td>
                   <td><a href="ItemInventorySlot?characterId=<c:out value="${character.getCharacterID()}"/>">Inventory</a></td>
                   <td><a href="findcurrency?characterId=<c:out value="${character.getCharacterID()}"/>">Currency</a></td>
                   <td><a href="findjob?characterId=<c:out value="${character.getCharacterID()}"/>">Job</a></td>
                   <td><a href="equippedItems?characterId=<c:out value="${character.getCharacterID()}"/>">Equipped Item</a></td>
                   <td><a href="characterdelete?characterID=<c:out value="${character.getCharacterID()}"/>">Delete</a></td>
                   <td><a href="characterupdate?firstName=<c:out value="${character.getFirstName()}"/>&lastName=<c:out value="${character.getLastName()}"/>">Update</a></td>
               </tr>
           </c:forEach>
    </table>
    <!-- A hyperlink directing users to the 'characterCreate' page (handled by a servlet or controller) -->
    <div id="charactercreate"><a href="charactercreate?playerid=<c:out value="${fn:escapeXml(param.playerid)}"/>">Create A New Character</a></div>
        
</body>
</html>
