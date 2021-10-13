package ru.javawebinar.topjava.view;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealView {
    void addMeal(Meal meal);

    void delMeal(int id);

    void updateMeal(Meal meal);

    List<Meal> getAllMeals();

    Meal getMealById(int id);
}
