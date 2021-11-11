package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

public interface CrudMealRepository extends JpaRepository<Meal, Integer> {
    Meal findByIdAndUserId(int id, int userId);

    @Query("select m from Meal m where m.user.id = ?1 order by m.dateTime DESC")
    List<Meal> getAll(int userId);

    @Query("select m from Meal m where m.user.id = ?1 and m.dateTime >= ?2 and m.dateTime < ?3 order by m.dateTime DESC")
    List<Meal> getBetweenHalfOpen(int userId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    int deleteByIdAndUserId(int id, int userId);
}
