package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static org.junit.Assert.assertThrows;
import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
@Sql(scripts = "classpath:db/populateDB.sql", config = @SqlConfig(encoding = "UTF-8"))

public class MealServiceTest {
    static {
        // Only for postgres driver logging
        // It uses java.util.logging and logged via jul-to-slf4j bridge
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        Meal meal = service.get(USER_MEAL_ID, USER_ID);
        assertMatch(meal, MealTestData.meal);
    }

    @Test
    public void getNotFoundById() {
        assertThrows(NotFoundException.class, () -> service.get(NOT_FOUND, USER_ID));
    }

    @Test
    public void getNotFoundByUserId() {
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID, UserTestData.NOT_FOUND));
    }

    @Test
    public void getNotFoundByOtherUserId() {
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void getBetweenInclusive() {
        List<Meal> userMeals = service.getBetweenInclusive(LocalDate.of(2020, Month.JANUARY, 30),
                LocalDate.of(2020, Month.JANUARY, 30), USER_ID);
        assertMatch(userMeals, filteredUserMeals);
    }

    @Test
    public void getBetweenInclusiveEmptyList() {
        List<Meal> userMeals = service.getBetweenInclusive(LocalDate.of(3000, Month.JANUARY, 30),
                LocalDate.of(3020, Month.JANUARY, 31), USER_ID);
        assertMatch(userMeals, emptyMeals);
    }

    @Test
    public void getAllUser() {
        List<Meal> userMeals = service.getAll(USER_ID);
        assertMatch(userMeals, MealTestData.userMeals);
    }

    @Test
    public void getAllAdmin() {
        List<Meal> adminMeals = service.getAll(ADMIN_ID);
        assertMatch(adminMeals, MealTestData.adminMeals);
    }

    @Test
    public void delete() {
        service.delete(USER_MEAL_ID, USER_ID);
        assertThrows(NotFoundException.class, () -> service.get(USER_MEAL_ID, USER_ID));
    }

    @Test
    public void deletedNotFoundById() {
        assertThrows(NotFoundException.class, () -> service.delete(NOT_FOUND, USER_ID));
    }

    @Test
    public void deleteNotFoundByUserId() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_ID, NOT_FOUND));
    }

    @Test
    public void deleteNotFoundByOtherUserId() {
        assertThrows(NotFoundException.class, () -> service.delete(USER_MEAL_ID, ADMIN_ID));
    }

    @Test
    public void update() {
        Meal updated = getUpdated(USER_MEAL_ID);
        service.update(updated, USER_ID);
        assertMatch(service.get(USER_MEAL_ID, USER_ID), getUpdated(USER_MEAL_ID));
    }

    @Test
    public void updateNotFoundByUser() {
        Meal updated = getUpdated(USER_MEAL_ID);
        assertThrows(NotFoundException.class, () -> service.update(updated, ADMIN_ID));
    }

    @Test
    public void duplicateDateUserUpdate() {
        assertThrows(DuplicateKeyException.class, () -> service.update(getDuplicate(), USER_ID));
    }

    @Test
    public void create() {
        Meal created = service.create(getNewUniq(), USER_ID);
        Integer newId = created.getId();
        Meal newMeal = getNewUniq();
        newMeal.setId(newId);
        assertMatch(created, newMeal);
        assertMatch(service.get(newId, USER_ID), newMeal);
    }

    @Test
    public void duplicateDateTimeCreate() {
        assertThrows(DuplicateKeyException.class, () -> service.create(getDuplicate(), USER_ID));
    }
}