<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="author" content = "Zagrebelnyi">
    <title>CruiseCompany</title>

    <link rel="stylesheet" type="text/css" href="<%=request.getContextPath()%>/css/register.css">

</head>
<body class = "fond">
<form method="Get" action = "MainMenu" class= "form_menu">
    <input type="submit" name="Menu" value="Sign in" class="field_menu">
    <input type="submit" name="Menu" value="Register" class="field_menu">
</form>
<div class = "main_name">CruiseCompany</div>
<form method="Post" action ="MainMenu" class = "form_register">
    <span class = "info_register">
        You register not on our site, but on the cruise of your dreams
    </span>
    <input type="text" name="NameRegist" placeholder="Name" maxlength="20" required class = "field_register">
    <input type="text" name="SurNameRegist" placeholder="SurName" maxlength="20" required class = "field_register">
    <input type="email" name="EmailRegist" placeholder="Email" maxlength="20" required class = "field_register">
    <input type="password" name="PasswordRegist" placeholder="Password" maxlength="20" required class = "field_register">
    <input type="password" name="RepeatPasswordRegist" placeholder="Repeat Password" maxlength="20" required class = "field_register">
    <input type="submit" name="Register" value="Register" class="register_user">
</form>
</body>
<html>
