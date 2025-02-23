<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

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
    </style>
</head>
<body>
	<a href="characters?playerid=${character.playerAccount.accountID}" class="back-link">Back to Characters</a>
	
    <h1>${messages.title}</h1>

    <div class="section-title">Weapon</div>
    <table>
        <thead>
            <tr>
                <th>Position</th>
                <th>Quantity</th>
                <th>Item Name</th>
                <th>Max Stack Size</th>
                <th>Is Sellable</th>
                <th>Vendor Price</th>
                <th>Item Level</th>
                <th>Required Level</th>
                <th>Damage</th>
                <th>Attack Speed</th>
                <th>Attack Delay</th>
                <th>Attributes</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${weapons}" var="slot">
                <tr>
                    <td>${slot.position}</td>
                    <td>${slot.quantity}</td>
                    <td>${slot.item.itemName}</td>
                    <td>${slot.item.maxStackSize}</td>
                    <td>${slot.item.sellable}</td>
                    <td>${slot.item.vendorPrice}</td>
                    <td>${slot.item.itemLevel}</td>
                    <td>${slot.item.requiredLevel}</td>
                    <td>${slot.item.damage}</td>
                    <td>${slot.item.autoAttack}</td>
                    <td>${slot.item.attackDelay}</td>
                    <td>
                        <a href="ItemAttributeBonus?itemId=${slot.item.itemID}&characterId=${character.characterID}" class="attribute-link">
                            View Attributes
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="section-title">Gear</div>
    <table>
        <thead>
            <tr>
                <th>Position</th>
                <th>Quantity</th>
                <th>Item Name</th>
                <th>Max Stack Size</th>
                <th>Is Sellable</th>
                <th>Vendor Price</th>
                <th>Item Level</th>
                <th>Required Level</th>
                <th>Defense</th>
                <th>Magic Defense</th>
                <th>Attributes</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${gear}" var="slot">
                <tr>
                    <td>${slot.position}</td>
                    <td>${slot.quantity}</td>
                    <td>${slot.item.itemName}</td>
                    <td>${slot.item.maxStackSize}</td>
                    <td>${slot.item.sellable}</td>
                    <td>${slot.item.vendorPrice}</td>
                    <td>${slot.item.itemLevel}</td>
                    <td>${slot.item.requiredLevel}</td>
                    <td>${slot.item.defense}</td>
                    <td>${slot.item.magicDefense}</td>
                    <td>
                        <a href="ItemAttributeBonus?itemId=${slot.item.itemID}&characterId=${character.characterID}" class="attribute-link">
                            View Attributes
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>

    <div class="section-title">Consumable</div>
    <table>
        <thead>
            <tr>
                <th>Position</th>
                <th>Quantity</th>
                <th>Item Name</th>
                <th>Max Stack Size</th>
                <th>Is Sellable</th>
                <th>Vendor Price</th>
                <th>Item Level</th>
                <th>Item Description</th>
                <th>Attributes</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach items="${consumables}" var="slot">
                <tr>
                    <td>${slot.position}</td>
                    <td>${slot.quantity}</td>
                    <td>${slot.item.itemName}</td>
                    <td>${slot.item.maxStackSize}</td>
                    <td>${slot.item.sellable}</td>
                    <td>${slot.item.vendorPrice}</td>
                    <td>${slot.item.itemLevel}</td>
                    <td>${slot.item.itemDescription}</td>
                    <td>
                        <a href="ItemAttributeBonus?itemId=${slot.item.itemID}&characterId=${character.characterID}" class="attribute-link">
                            View Attributes
                        </a>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</body>
</html>