<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Find Player Account</title>
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
    <h1>Search for a player by userName</h1>
    <form action="FindPlayerAccount" method="get">
        <label for="userName">userName</label>
        <input type="text" id="userName" name="userName" value="${searchQuery}" />
        <button type="submit">Submit</button>
    </form>

    <c:if test="${not empty searchQuery}">
        <h2>Displaying results for ${searchQuery}</h2>
    </c:if>

    <br />
    <a href="CreatePlayerAccount.jsp">Create PlayerAccount</a>

    <c:if test="${not empty players}">
        <h3>Matching players</h3>
        <table border="1">
            <thead>
                <tr>
                    <th>userName</th>
                    <th>email</th>
                    <th>isActive</th>
                    <th>Character</th>
                    <th>deleteUser</th>
                    <th>updateUser</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="player" items="${players}">
                    <tr>
                        <td>${player.userName}</td>
                        <td>${player.email}</td>
                        <td>${player.isActive}</td>
                        <td><a href="characters?playerid=${player.accountID}">Character</a></td>
                        <td><a href="DeletePlayerAccount?userName=${player.userName}&accountID=${player.accountID}">Delete</a></td>
                        <td><a href="UpdatePlayerAccount?userName=${player.userName}&email=${player.email}&accountID=${player.accountID}">Update</a></td>
            
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
</body>
</html>