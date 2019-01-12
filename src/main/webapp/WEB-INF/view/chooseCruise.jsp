<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="author" content = "Zagrebelnyi">
    <title>CruiseCompany</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/chooseCruise.css">

</head>
<body class = "fond">
<form method="Get" action = "UserMenu" class= "form_menu">
    <input type="submit" name="UserMenu" value="My profile" class="field_menu">
    <input type="submit" name="UserMenu" value="My cruise" class="field_menu">
    <input type="submit" name="UserMenu" value="Choose a cruise" class="field_menu">
    <input type="submit" name="UserMenu" value="My bonus" class="field_menu">
    <input type="submit" name="UserMenu" value="Sign out" class="field_menu">
</form>
<div class = "main_name">CruiseCompany</div>
<form method="Get" action ="ChooseCruise" class = "form_cruise">
            <span class = "info_cruise">
                You feel this air, and the rays of the sun, you will soon be resting. Have you dream about it?
            </span>
    <table class="table_cruise">
        <tr>
            <th class="table_column">Name Cruise</th>
            <th class="table_column">City Departure</th>
            <th class="table_column">Start Cruise</th>
            <th class="table_column">Duration Cruise</th>
            <th class="table_column">Name Ship</th>
            <th class="table_column">Ticket Id</th>
            <th class="table_column">Ticket Class</th>
            <th class="table_column">Ticket Price</th>
            <th class="table_column">Action</th>
        </tr>
        <c:forEach items="${tickets}" var = "ticket" >
            <tr>
                <form method="Get" action ="ChooseCruise" class = "form_cruise">
                    <td class="table_column"><c:out value="${ticket.nameCruise}" /></td>
                    <td class="table_column"><c:out value="${ticket.cityDeparture}" /></td>
                    <td class="table_column"><c:out value="${ticket.startCruise}" /></td>
                    <td class="table_column"><c:out value="${ticket.durationCruise}" /></td>
                    <td class="table_column"><c:out value="${ticket.nameShip}" /></td>
                    <td class="table_column"><c:out value="${ticket.ticketId}" /></td>
                    <td class="table_column"><c:out value="${ticket.ticketClass}" /></td>
                    <td class="table_column"><c:out value="${ticket.ticketPrice}" /></td>
                    <input type="hidden" name="TicketId" value="<c:out value="${ticket.ticketId}" />">
                    <td><input type="submit" name="Operation" value="Buy" class="button_refuse"></td>
                </form>
            </tr>
        </c:forEach>
    </table>
    <input type="submit" name="Operation" value="Previous 10" class="button_previous">
    <input type="text" name="OperationText" maxlength="20" placeholder="Name cruise" class = "field_find">
    <input type="submit" name="Operation" value="Find" class = "button_find">
    <input type="submit" name="Operation" value="Next 10" class="button_next">
</form>
</body>
<html>