package ru.javawebinar.topjava;

import ru.javawebinar.topjava.matcher.BeanMatcher;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static ru.javawebinar.topjava.model.BaseEntity.START_SEQ;

public class MealTestData {
    public static final int USER_MEAL1_ID = START_SEQ + 2;
    public static final int ADMIN_MEAL1_ID = START_SEQ + 8;

    public static final Meal USER_MEAL1 = new Meal(USER_MEAL1_ID, LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL2 = new Meal(USER_MEAL1_ID + 1, LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL3 = new Meal(USER_MEAL1_ID + 2, LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500);
    public static final Meal USER_MEAL4 = new Meal(USER_MEAL1_ID + 3, LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 500);
    public static final Meal USER_MEAL5 = new Meal(USER_MEAL1_ID +4, LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 1000);
    public static final Meal USER_MEAL6 = new Meal(USER_MEAL1_ID +5, LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510);

    public static final Meal ADMIN_MEAL1 = new Meal(ADMIN_MEAL1_ID, LocalDateTime.of(2015, Month.JUNE, 1, 14, 0), "Админ ланч", 510);
    public static final Meal ADMIN_MEAL2 = new Meal(ADMIN_MEAL1_ID + 1, LocalDateTime.of(2015, Month.JUNE, 1, 21, 0), "Админ ужин", 1500);

    public static final List<Meal> USER_MEALS = Arrays.asList(USER_MEAL6, USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2, USER_MEAL1);

    public static Meal getCreated() {
        return new Meal(null, LocalDateTime.of(2015, Month.JUNE, 1, 18, 0), "Созданный ужин", 300);
    }

    public static Meal getUpdated() {
        return new Meal(USER_MEAL1_ID, USER_MEAL1.getDateTime(), "Обновленный завтрак", 200);
    }

    public static final BeanMatcher<Meal> MATCHER = new BeanMatcher<>();

}
