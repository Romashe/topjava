MealRestController
===
getAllMeals
---
```
curl --location --request GET 'http://localhost:8080/topjava/rest/meals'
```

getMealsFiltered
---
```
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/filter?startDate=2020-01-30&startTime=10:00:00&endDate=2020-01-30&endTime=20:00:00'
```

getMealById
---
```
curl --location --request GET 'http://localhost:8080/topjava/rest/meals/100007'
```

createMeal
---
```curl --location --request POST 'http://localhost:8080/topjava/rest/meals' \
--header 'Content-Type: application/json' \
--data-raw '{
"dateTime": "2021-01-31T13:00:00",
"description": "Обед",
"calories": 1000
}'
```

updateMealById
---
```
curl --location --request PUT 'http://localhost:8080/topjava/rest/meals/100008' \
--header 'Content-Type: application/json' \
--data-raw '{
"dateTime": "2020-11-25T13:00:00",
"description": "Ужин555",
"calories": 777
}'
```

deleteMealById
---
```
curl --location --request DELETE 'http://localhost:8080/topjava/rest/meals/100007'
```