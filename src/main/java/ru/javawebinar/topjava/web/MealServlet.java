package ru.javawebinar.topjava.web;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;
import java.time.LocalTime;
import org.slf4j.Logger;
import ru.javawebinar.topjava.util.MealsUtil;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        log.debug("forward to meals");
        request.setAttribute("list", MealsUtil.filteredByStreams(MealsUtil.mealList, LocalTime.MIN, LocalTime.MAX, MealsUtil.caloriesPerDay));
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

}
