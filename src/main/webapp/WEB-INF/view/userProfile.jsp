<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="author" content = "Zagrebelnyi">
    <title>CruiseCompany</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.servletContext.contextPath}/css/userProfile.css">
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
<form method="Post" action ="Change" class = "form_change">
    <span class = "info_change">
        Here you can feel yourself by anyone, even Angelina Jolie
    </span>
    <span class = "wrong_change">
        <c:out value="${Busy}"></c:out>
    </span>
    <input type="text" name="Name" placeholder="Name" maxlength="20" class = "field_change">
    <input type="text" name="Surname" placeholder="Surname" maxlength="20" class = "field_change">
    <input type="email" name="Email" placeholder="Email" maxlength="20" required class = "field_change">
    <input type="submit" name="Change" value="Change" class="change_user">
</form>
</body>
<html>
