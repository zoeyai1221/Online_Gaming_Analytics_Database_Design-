<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    
<!DOCTYPE html>
<html>
<head>
    <title>${messages.title}</title>
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
        .na-text {
            color: #999;
            font-style: italic;
        }
    </style>
</head>
<body>
    <h1>${messages.title}</h1>
   
    <c:if test="${not empty item}">
        <table border="1">
            <thead>
                <tr>
                    <th>Attribute Name</th>
                    <th>Base Value</th>
                    <th>Flat Bonus</th>
                    <th>Percentage Bonus</th>
                    <th>Bonus Cap</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${attributeBonuses}" var="bonus">
                    <tr>
                        <td>${bonus.attribute.attributeName}</td>
                        <td>${bonus.attribute.attributeValue}</td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty flatBonuses[bonus.attribute.attributeName]}">
                                    +${flatBonuses[bonus.attribute.attributeName].flatBonusValue}
                                </c:when>
                                <c:otherwise>
                                    <span class="na-text">N/A</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty percentageBonuses[bonus.attribute.attributeName]}">
                                    +${percentageBonuses[bonus.attribute.attributeName].percentageBonusValue * 100}%
                                </c:when>
                                <c:otherwise>
                                    <span class="na-text">N/A</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td>
                            <c:choose>
                                <c:when test="${not empty percentageBonuses[bonus.attribute.attributeName]}">
                                    ${percentageBonuses[bonus.attribute.attributeName].bonusCap}
                                </c:when>
                                <c:otherwise>
                                    <span class="na-text">N/A</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <a href="ItemInventorySlot?characterId=${param.characterId}" class="back-link">Back to Inventory</a>
</body>
</html>