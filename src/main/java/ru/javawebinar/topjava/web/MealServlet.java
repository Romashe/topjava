package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.view.MealRepository;
import ru.javawebinar.topjava.view.InMemoryMealRepository;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Locale;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private static final String INSERT_OR_EDIT = "/meal.jsp";
    private static final String LIST_MEALS = "/meals.jsp";
    private MealRepository mealRepository;

    @Override
    public void init() {
        mealRepository = new InMemoryMealRepository();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String forward = "";
        String action = request.getParameter("action");
        String mealId = request.getParameter("mealId");
        switch (action == null ? "" : action.toLowerCase(Locale.ROOT)) {
            case "update":
                forward = INSERT_OR_EDIT;
                Meal meal = mealRepository.getById(Integer.parseInt(mealId));
                request.setAttribute("meal", meal);
                request.getRequestDispatcher(forward).forward(request, response);
                break;
            case "delete":
                mealRepository.delete(Integer.parseInt(mealId));
                response.sendRedirect("meals");
                break;
            case "insert":
                forward = INSERT_OR_EDIT;
                request.getRequestDispatcher(forward).forward(request, response);
                break;
            default:
                forward = LIST_MEALS;
                request.setAttribute("list", MealsUtil.filteredByStreams(mealRepository.getAll(), LocalTime.MIN, LocalTime.MAX, MealsUtil.CALORIES_PER_DAY));
                request.getRequestDispatcher(forward).forward(request, response);
        }
        log.debug("forward=" + forward + " action=" + action+" mealId="+mealId);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        LocalDateTime mealDateTime = LocalDateTime.parse(request.getParameter("dateTime"));
        String mealId = request.getParameter("mealId");
        String description = request.getParameter("description");
        int calories = Integer.parseInt(request.getParameter("calories"));
        log.debug("mealDateTime="+mealDateTime+" mealId="+mealId+" description="+description+" calories="+calories);
        if (mealId == null || mealId.isEmpty()) {
            mealRepository.add(new Meal(mealDateTime, description, calories));
            log.debug("meal added");
        } else {
            mealRepository.update(new Meal(Integer.parseInt(mealId), mealDateTime, description, calories));
            log.debug("meal updated");
        }
        log.debug("redirect=meals. mealId=" + mealId);
        response.sendRedirect("meals");
    }
}
