package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.AuthorizedUser;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.checkIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private static final Logger log = LoggerFactory.getLogger(MealRestController.class);
    @Autowired
    private MealService service;

    public List<Meal> getAll() {
        int userId = AuthorizedUser.id();
        log.info("getAll for User {}", userId);
        return service.getAll(userId);
    }

    public Meal get(int id) {
        log.info("get {}", id);
        int userId = AuthorizedUser.id();
        return service.get(id, userId);
    }

    public Meal create(Meal meal) {
        int userId = AuthorizedUser.id();
        log.info("create {} for User {}", meal, userId);
        checkNew(meal);
        return service.save(meal, userId);
    }

    public void update(Meal meal, int id) {
        int userId = AuthorizedUser.id();
        log.info("update {} with id={} for User {}", meal, id, userId);
        checkIdConsistent(meal, id);
        service.update(meal, userId);
    }

    public void delete(int id) {
        int userId = AuthorizedUser.id();
        log.info("delete {} for User {}", id, userId);
        service.delete(id, userId);
    }

    public List<MealWithExceed> getBetween(LocalDate startDate, LocalTime startTime, LocalDate endDate, LocalTime endTime) {
        int userId = AuthorizedUser.id();
        log.info("getBetween dates({} - {}) time({} - {}) for User {}", startDate, endDate, startTime, endTime, userId);
        return MealsUtil.getFilteredWithExceeded(
                service.getBetween(startDate != null ?
                        LocalDateTime.of(startDate, LocalTime.MIN) : LocalDateTime.of(LocalDate.MIN, LocalTime.MIN),
                        endDate != null ? LocalDateTime.of(endDate, LocalTime.MAX) : LocalDateTime.of(LocalDate.MAX, LocalTime.MAX), userId),
                startTime != null ? startTime : LocalTime.MIN,
                endTime != null ? endTime : LocalTime.MAX,
                AuthorizedUser.getCaloriesPerDay());
    }
}