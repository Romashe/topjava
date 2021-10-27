package ru.javawebinar.topjava;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {
    public static final int NOT_FOUND = 100;
    public static final int USER_MEAL_ID = START_SEQ + 2;

    public static final List<Meal> emptyMeals = new ArrayList<>();

    public static final Meal meal = new Meal(USER_MEAL_ID, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);

    public static final List<Meal> userMeals = Arrays.asList(
            new Meal(100008, LocalDateTime.of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 410),
            new Meal(100007, LocalDateTime.of(2020, Month.JANUARY, 31, 13, 0), "Обед", 500),
            new Meal(100006, LocalDateTime.of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 1000),
            new Meal(100005, LocalDateTime.of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100),
            new Meal(100004, LocalDateTime.of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500),
            new Meal(100003, LocalDateTime.of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000),
            new Meal(100002, LocalDateTime.of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500)
    );
    public static final List<Meal> adminMeals = Arrays.asList(
            new Meal(100010, LocalDateTime.of(2015, Month.AUGUST, 1, 21, 0), "Админ ужин", 1500),
            new Meal(100009, LocalDateTime.of(2015, Month.AUGUST, 1, 14, 0), "Админ ланч", 510)

    );

    public static final List<Meal> filteredUserMeals = userMeals.stream()
            .filter(m-> m.getDate().equals(meal.getDate()))
            .collect(Collectors.toList());

    public static Meal getDuplicate() {
        return new Meal(null, userMeals.get(0).getDateTime(), "Завтрак", 500);
    }

    public static Meal getNewUniq() {
        return new Meal(null, LocalDateTime.of(2050, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    }

    public static Meal getUpdated(int mealId) {
        Meal updated = new Meal(meal);
        updated.setCalories(999);
        updated.setDescription("Updated Meal");
        updated.setDateTime(LocalDateTime.of(2000, Month.JANUARY, 1, 10, 1, 0));
        return updated;
    }

    public static void assertMatch(Meal actual, Meal expected) {
        assertThat(actual).usingRecursiveComparison().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).isEqualTo(expected);
    }
}

