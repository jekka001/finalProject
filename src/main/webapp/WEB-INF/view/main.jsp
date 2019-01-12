<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="author" content = "Zagrebelnyi">
    <title>CruiseCompany</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/main.css">
</head>

<body class = "fond">
<form method="Get" action = "MainMenu" class= "form_menu">
    <input type="submit" name="Menu" value="Sign in" class="field_menu">
    <input type="submit" name="Menu" value="Register" class="field_menu">
</form>
<div class = "main_name">CruiseCompany</div>
<form method="Post" action ="Authorization" class = "form_sign_in">
    <span class = "info_sign_in">
        You have to hurry, the ship will depart soon
    </span>
    <span class="wrong_sign_in">
        <c:out value="${wrong}"></c:out>
    </span>
    <input type="email" name="Email" placeholder="Email" maxlength="20" required class = "field_sign_in">
    <input type="password" name="Password" placeholder="Password" maxlength="20" required class = "field_sign_in">
    <input type="submit" name="SignIn" value="Sign in" class="sign_in_user">
</form>
</body>
</html>