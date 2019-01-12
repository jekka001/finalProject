<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="author" content = "Zagrebelnyi">
    <title>CruiseCompany</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/bonus.css">

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
<form method="Get" action ="" class = "form_cruise">
            <span class = "info_cruise">
            </span>
    <table class="table_cruise">
        <tr>
            <th class="table_column">Ticket Id</th>
            <th class="table_column">Ticket Class</th>
            <th class="table_column">Bonus</th>
        </tr>
        <!--цикл на кількість круїзів -->
        <tr>
            <td class="table_column">1</td>
            <td class="table_column">Lux</td>
            <td class="table_column">Pool,cinema</td>
        </tr>
        <tr>
            <td class="table_column">1</td>
            <td class="table_column">Lux</td>
            <td class="table_column">Pool,cinema</td>
        </tr>
        <tr>
            <td class="table_column">1</td>
            <td class="table_column">Lux</td>
            <td class="table_column">Pool,cinema</td>
        </tr>
        <tr>
            <td class="table_column">1</td>
            <td class="table_column">Lux</td>
            <td class="table_column">Pool,cinema</td>
        </tr>
        <tr>
            <td class="table_column">1</td>
            <td class="table_column">Lux</td>
            <td class="table_column">Pool,cinema</td>
        </tr>
        <tr>
            <td class="table_column">1</td>
            <td class="table_column">Lux</td>
            <td class="table_column">Pool,cinema</td>
        </tr>
        <tr>
            <td class="table_column">1</td>
            <td class="table_column">Lux</td>
            <td class="table_column">Pool,cinema</td>
        </tr>
        <tr>
            <td class="table_column">1</td>
            <td class="table_column">Lux</td>
            <td class="table_column">Pool,cinema</td>
        </tr>
        <tr>
            <td class="table_column">1</td>
            <td class="table_column">Lux</td>
            <td class="table_column">Pool,cinema</td>
        </tr>
        <tr>
            <td class="table_column">1</td>
            <td class="table_column">Lux</td>
            <td class="table_column">Pool,cinema</td>
        </tr>
    </table>
    <input type="submit" name="Previous" value="Previous 10" class="button_previous">
    <input type="text" name="Find" maxlength="20" placeholder="Ticket Id" class = "field_find">
    <input type="submit" name="Find" value="Find" class = "button_find">
    <input type="submit" name="Next" value="Next 10" class="button_next">
</form>
</body>
<html>