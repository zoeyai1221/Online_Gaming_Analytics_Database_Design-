<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Equipped Items</title>
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
    
    <h2>Equipped Weapon</h2>
    <table border="1">
        <tr>
            <th>Item Name</th>
            <th>Slot Name</th>
            <th>Vendor Price</th>
            <th>Sellable</th>
            <th>Item Level</th>
            <th>Required Level</th>
            <th>Damage</th>
            <th>Auto Attack</th>
            <th>Attack Delay</th>
            <th>Attribute Bonus</th>         
        </tr>
        <c:if test="${not empty equippedWeapon}">
            <tr>
            	<td><c:out value="${equippedWeapon.getItemName()}" /></td>
           	    <%-- Check if weaponSlot is not null and display the slot name --%>
	            <td>
	                <c:if test="${not empty weaponSlot}">
	                    <c:out value="${weaponSlot.getSlotName()}" />
	                </c:if>
	            </td>
                <td>
                    <c:choose>
                        <c:when test="${weapon.isSellable()}">
                            <c:out value="${weapon.getVendorPrice()}" />
                        </c:when>
                        <c:otherwise>
                            N/A
                        </c:otherwise>
                    </c:choose>
                </td>
                <td><c:out value="${equippedWeapon.isSellable() ? 'Yes' : 'No'}" /></td>
                <td><c:out value="${equippedWeapon.getItemLevel()}" /></td>
                <td><c:out value="${equippedWeapon.getRequiredLevel()}" /></td>
                <td><c:out value="${equippedWeapon.getDamage()}" /></td>
                <td><c:out value="${equippedWeapon.getAutoAttack()}" /></td>
                <td><c:out value="${equippedWeapon.getAttackDelay()}" /></td>
                <td>
                    <a href="attributeBonusDetails?itemID=${equippedWeapon.itemID}">View Bonuses</a>
                </td> 
            </tr>
		</c:if>
		<c:if test="${empty equippedWeapon}">
		    <p>No weapon equipped.</p>
		</c:if>
	</table>
	
    <h2>Equipped Gear</h2>
    <table border="1">
        <tr> 	
            <th>Item Name</th>
            <th>Slot Name</th>
            <th>Vendor Price</th>
            <th>Sellable</th>
            <th>Item Level</th>
            <th>Required Level</th>
            <th>Defense</th>
            <th>Magic Defense</th>
            <th>Attribute Bonus</th>
        </tr>
        <c:forEach items="${equippedGear}" var="gear">
            <tr>
                <td><c:out value="${gear.getItemName()}" /></td>
                <%-- Check if gearSlots for this gear is not null and display the slot name --%>
	            <td>
	                <c:if test="${not empty gearSlots[gear.itemID]}">
	                    <c:out value="${gearSlots[gear.itemID].getSlotName()}" />
	                </c:if>
	            </td>
                <td><fmt:formatNumber value="${gear.getVendorPrice()}" type="currency" /></td>
                <td><c:out value="${gear.isSellable() ? 'Yes' : 'No'}" /></td>
                <td><c:out value="${gear.getItemLevel()}" /></td>
                <td><c:out value="${gear.getRequiredLevel()}" /></td>
                <td><c:out value="${gear.getDefense()}" /></td>
                <td><c:out value="${gear.getMagicDefense()}" /></td>
                <td>
                    <a href="attributeBonusDetails?itemID=${gear.itemID}">View Bonuses</a>
                </td>  
            </tr>
        </c:forEach>
        <c:if test="${empty equippedGear}">
                <p>No gear equipped.</p>
        </c:if>
    </table>
    <a href="javascript:history.back()">Back</a>
</body>
</html>