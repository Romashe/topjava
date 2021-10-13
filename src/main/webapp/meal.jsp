<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html lang="ru">
<head>
    <link rel="stylesheet" href="styles.css">
    <title>Meal</title>
</head>
<body>
<h3><a href="meals?action=listMeals">Back</a></h3>
<hr>
<h2>Meal</h2>
<div class="table">
    <form method="POST" action='meals' name="frmAddMeal">
        <div class="row">
            <div class="cell">Meal Id:</div>
            <div class="cell"><input type="text" readonly="readonly" name="mealId" value="<c:out value="${meal.id}" />"/></div>
        </div>
        <div class="row">
            <div class="cell">Description:</div>
            <div class="cell"><input required type="text" name="description" value="<c:out value="${meal.description}" />"/>
            </div>
        </div>
        <div class="row">
            <div class="cell">Calories:</div>
            <div class="cell"><input required type="number" min = "0" max = "10000" name="calories" value="<c:out value="${meal.calories}"/>"/>
            </div>
        </div>
        <div class="row">
            <div class="cell">Date/Time:</div>
            <div class="cell"><input required type="datetime-local" name="dateTime" value="<c:out value="${meal.dateTime}" />"/>
            </div>
        </div>
        <div class="row">
            <div class="cell"><input type="submit" value="Submit"/></div>
            <div class="cell"><input type="reset" value="Reset"/></div>
        </div>
    </form>
</div>
</body>
</html>
