package ru.javawebinar.topjava.repository.inmemory;

import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.MealsUtil;

import java.util.Collection;
import java.util.Comparator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepository implements MealRepository {
    private final Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private final AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.meals.forEach(meal -> save(meal, null));
    }

    @Override
    public Meal save(Meal meal, Integer userId) {
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // handle case: update, but not present in storage
        return userId.equals(meal.getUserId()) ? repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal) : null;
    }

    @Override
    public boolean delete(int id, Integer userId) {
        return userId.equals(repository.get(id).getUserId()) && repository.remove(id) != null;
    }

    @Override
    public Meal get(int id, Integer userId) {
        return userId.equals(repository.get(id).getUserId()) ? repository.get(id) : null;
    }

    @Override
    public Collection<Meal> getAll(Integer userId) {
        return repository.values().stream().filter(meal -> meal.getUserId().equals(userId)).sorted(Comparator.comparing(Meal::getDate,Comparator.reverseOrder())).collect(Collectors.toList());
    }
}

