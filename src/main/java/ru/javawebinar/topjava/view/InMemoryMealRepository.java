package ru.javawebinar.topjava.view;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> mealMap = new ConcurrentHashMap<>();
    private final AtomicInteger maxId = new AtomicInteger();

    public InMemoryMealRepository() {
        List<Meal> mealList = Arrays.asList(
                new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500),
                new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
                new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
                new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
                new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
                new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
                new Meal(getNextId(), LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410)
        );
        mealList.forEach(meal->add(meal));
    }

    @Override
    public Meal add(Meal meal) {
        meal.setId(getNextId());
        this.mealMap.put(meal.getId(), meal);
        return this.mealMap.get(meal.getId());
    }

    @Override
    public void delete(int id) {
        this.mealMap.remove(id);
    }

    @Override
    public Meal update(Meal meal) {
        this.mealMap.replace(meal.getId(), new Meal(meal.getId(), meal.getDateTime(), meal.getDescription(), meal.getCalories()));
        return this.mealMap.get(meal.getId());
    }

    @Override
    public List<Meal> getAll() {
        return new ArrayList<>(this.mealMap.values());
    }

    @Override
    public Meal getById(int id) {
        return this.mealMap.get(id);
    }

    private int getNextId() {
        return maxId.getAndIncrement();
    }
}
