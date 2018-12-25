<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="author" content = "Zagrebelnyi">
    <title>CruiseCompany</title>

    <link rel="stylesheet" type="text/css" href="../css/userProfile.css">

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
<form method="Post" action ="" class = "form_change">
            <span class = "info_change">
                Here you can feel yourself by anyone, even Angelina Jolie
            </span>
    <input type="email" name="Name" placeholder="Name" maxlength="20" class = "field_change">
    <input type="email" name="SurName" placeholder="SurName" maxlength="20" class = "field_change">
    <input type="email" name="Email" placeholder="Email" maxlength="20" class = "field_change">
    <input type="password" name="Password" placeholder="Password" maxlength="20" class = "field_change">
    <input type="submit" name="Change" value="Change" class="change_user">
</form>
</body>
<html>
