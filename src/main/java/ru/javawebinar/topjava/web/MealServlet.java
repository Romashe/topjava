package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.view.MealView;
import ru.javawebinar.topjava.view.MealViewInMemory;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEALS = "/meals.jsp";
    private static MealView mealView;

    @Override
    public void init() throws ServletException {
        super.init();
        mealView = new MealViewInMemory();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward;
        String action = request.getParameter("action");
        if (action == null || action.equalsIgnoreCase("listMeals")) {
            forward = LIST_MEALS;
            request.setAttribute("list", MealsUtil.filteredByStreams(mealView.getAllMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("Update")) {
            forward = INSERT_OR_EDIT;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            Meal meal = mealView.getMealById(mealId);
            request.setAttribute("meal", meal);
        } else if (action.equalsIgnoreCase("Delete")) {
            forward = LIST_MEALS;
            int mealId = Integer.parseInt(request.getParameter("mealId"));
            mealView.delMeal(mealId);
            request.setAttribute("list", MealsUtil.filteredByStreams(mealView.getAllMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
        } else if (action.equalsIgnoreCase("Insert")) {
            forward = INSERT_OR_EDIT;
        } else {
            forward = LIST_MEALS;
            request.setAttribute("list", MealsUtil.filteredByStreams(mealView.getAllMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
        }
        log.debug("forward="+forward+" action="+action);
        RequestDispatcher view = request.getRequestDispatcher(forward);
        view.forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        LocalDateTime mealDateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String mealId = request.getParameter("mealId");
        if (mealId == null || mealId.isEmpty()) {
            mealView.addMeal(new Meal(mealDateTime, request.getParameter("description"), Integer.parseInt(request.getParameter("calories"))));
        } else {
            mealView.updateMeal(new Meal(mealDateTime, request.getParameter("description"), Integer.parseInt(request.getParameter("calories")), Integer.parseInt(mealId)));
        }
        log.debug("forward="+LIST_MEALS+" mealId="+mealId);
        RequestDispatcher view = request.getRequestDispatcher(LIST_MEALS);
        request.setAttribute("list", MealsUtil.filteredByStreams(mealView.getAllMeals(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
        view.forward(request, response);
    }
}
