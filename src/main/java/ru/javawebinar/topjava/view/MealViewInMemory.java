package ru.javawebinar.topjava.view;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MealViewInMemory implements MealView {
    private final Map<Integer, Meal> mealMap;
    private static final AtomicInteger maxId = new AtomicInteger();
    public static final List<Meal> mealList = Arrays.asList(
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500, getNextId()),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000, getNextId()),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500, getNextId()),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100, getNextId()),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000, getNextId()),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500, getNextId()),
            new Meal(LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410, getNextId())
    );

    public MealViewInMemory() {
        this.mealMap = mealList.stream().collect(Collectors.toConcurrentMap(Meal::getId, meal -> meal));
    }

    @Override
    public void addMeal(Meal meal) {
        meal.setId(getNextId());
        this.mealMap.put(meal.getId(), meal);
    }

    @Override
    public void delMeal(int id) {
        this.mealMap.remove(id);
    }

    @Override
    public void updateMeal(Meal meal) {
        this.mealMap.replace(meal.getId(), new Meal(meal.getDateTime(), meal.getDescription(), meal.getCalories(), meal.getId()));
    }

    @Override
    public List<Meal> getAllMeals() {
        return new ArrayList<>(this.mealMap.values());
    }

    @Override
    public Meal getMealById(int id) {
        return this.mealMap.get(id);
    }

    public static int getNextId() {
        return maxId.getAndIncrement();
    }
}
