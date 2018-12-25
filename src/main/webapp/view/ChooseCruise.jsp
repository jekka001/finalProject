<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="author" content = "Zagrebelnyi">
    <title>CruiseCompany</title>
    <link rel="stylesheet" type="text/css" href="../css/ChooseCruise.css">

</head>
<body class = "fond">
<form method="Get" action = "" class= "form_menu">
    <input type="submit" name="UserProfile" value="My profile" class="field_menu">
    <input type="submit" name="UserCruise" value="My cruise" class="field_menu">
    <input type="submit" name="ChooseCruise" value="Choose a cruise" class="field_menu">
    <input type="submit" name="Bonus" value="My bonus" class="field_menu">
    <input type="submit" name="SignOut" value="Sign out" class="field_menu">
</form>
<div class = "main_name">CruiseCompany</div>
<form method="Get" action ="" class = "form_cruise">
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
            <th class="table_column">Ticket Class</th>
            <th class="table_column">Ticket Id</th>
            <th class="table_column">Action</th>
        </tr>
        <!--цикл на кількість круїзів -->
        <tr>
            <td class="table_column">Cruise</td>
            <td class="table_column">Kiev</td>
            <td class="table_column">12.12.12</td>
            <td class="table_column">5 days</td>
            <td class="table_column">Caterina</td>
            <td class="table_column">Lux</td>
            <td class="table_column">1</td>
            <td><input type="submit" name="Action" value="Buy" class="button_refuse"></td>
        </tr>
        <tr>
            <td class="table_column">Cruise</td>
            <td class="table_column">Kiev</td>
            <td class="table_column">12.12.12</td>
            <td class="table_column">5 days</td>
            <td class="table_column">Caterina</td>
            <td class="table_column">Lux</td>
            <td class="table_column">1</td>
            <td><input type="submit" name="Action" value="Buy" class="button_refuse"></td>
        </tr>
        <tr>
            <td class="table_column">Cruise</td>
            <td class="table_column">Kiev</td>
            <td class="table_column">12.12.12</td>
            <td class="table_column">5 days</td>
            <td class="table_column">Caterina</td>
            <td class="table_column">Lux</td>
            <td class="table_column">1</td>
            <td><input type="submit" name="Action" value="Buy" class="button_refuse"></td>
        </tr>
        <tr>
            <td class="table_column">Cruise</td>
            <td class="table_column">Kiev</td>
            <td class="table_column">12.12.12</td>
            <td class="table_column">5 days</td>
            <td class="table_column">Caterina</td>
            <td class="table_column">Lux</td>
            <td class="table_column">1</td>
            <td><input type="submit" name="Action" value="Buy" class="button_refuse"></td>
        </tr>
        <tr>
            <td class="table_column">Cruise</td>
            <td class="table_column">Kiev</td>
            <td class="table_column">12.12.12</td>
            <td class="table_column">5 days</td>
            <td class="table_column">Caterina</td>
            <td class="table_column">Lux</td>
            <td class="table_column">1</td>
            <td><input type="submit" name="Action" value="Buy" class="button_refuse"></td>
        </tr>
        <tr>
            <td class="table_column">Cruise</td>
            <td class="table_column">Kiev</td>
            <td class="table_column">12.12.12</td>
            <td class="table_column">5 days</td>
            <td class="table_column">Caterina</td>
            <td class="table_column">Lux</td>
            <td class="table_column">1</td>
            <td><input type="submit" name="Action" value="Buy" class="button_refuse"></td>
        </tr>
        <tr>
            <td class="table_column">Cruise</td>
            <td class="table_column">Kiev</td>
            <td class="table_column">12.12.12</td>
            <td class="table_column">5 days</td>
            <td class="table_column">Caterina</td>
            <td class="table_column">Lux</td>
            <td class="table_column">1</td>
            <td><input type="submit" name="Action" value="Buy" class="button_refuse"></td>
        </tr>
        <tr>
            <td class="table_column">Cruise</td>
            <td class="table_column">Kiev</td>
            <td class="table_column">12.12.12</td>
            <td class="table_column">5 days</td>
            <td class="table_column">Caterina</td>
            <td class="table_column">Lux</td>
            <td class="table_column">1</td>
            <td><input type="submit" name="Action" value="Buy" class="button_refuse"></td>
        </tr>
        <tr>
            <td class="table_column">Cruise</td>
            <td class="table_column">Kiev</td>
            <td class="table_column">12.12.12</td>
            <td class="table_column">5 days</td>
            <td class="table_column">Caterina</td>
            <td class="table_column">Lux</td>
            <td class="table_column">1</td>
            <td><input type="submit" name="Action" value="Buy" class="button_refuse"></td>
        </tr>
        <tr>
            <td class="table_column">Cruise</td>
            <td class="table_column">Kiev</td>
            <td class="table_column">12.12.12</td>
            <td class="table_column">5 days</td>
            <td class="table_column">Caterina</td>
            <td class="table_column">Lux</td>
            <td class="table_column">1</td>
            <td><input type="submit" name="Action" value="Buy" class="button_refuse"></td>
        </tr>
    </table>
    <input type="submit" name="Previous" value="Previous 10" class="button_previous">
    <input type="text" name="Find_Name" maxlength="20" placeholder="Name Cruise" class = "field_find">
    <input type="submit" name="Find" value="Find" class = "button_find">
    <input type="submit" name="Next" value="Next 10" class="button_next">
</form>
</body>
<html>