<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <link rel="stylesheet" href="styles.css">
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>
<h3><a href="meals?action=insert">Add meal</a></h3>
<div class="table">
    <div class="row header">
        <div class="cell">Description</div>
        <div class="cell">Calories</div>
        <div class="cell">Date/Time</div>
        <div class="cell">Action</div>
    </div>
    <c:forEach items="${list}" var="item">
        <div class="${item.excess ? 'row red' : 'row green'}">
            <div class="cell">${item.description}</div>
            <div class="cell">${item.calories}</div>
            <div class="cell">${item.dateTime.toLocalDate()} ${item.dateTime.toLocalTime()}</div>
            <div class="cell"><a href="meals?action=update&mealId=${item.id}">Update</a></div>
            <div class="cell"><a href="meals?action=delete&mealId=${item.id}">Delete</a></div>
        </div>
    </c:forEach>
    </div>
</body>
</html>