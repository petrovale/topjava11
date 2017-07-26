package ru.javawebinar.topjava;

import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.Role;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.web.meal.MealRestController;
import ru.javawebinar.topjava.web.user.AdminRestController;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class SpringMain {
    public static void main(String[] args) {
        // java 7 Automatic resource management
        try (ConfigurableApplicationContext appCtx = new ClassPathXmlApplicationContext("spring/spring-app.xml")) {
            System.out.println("Bean definition names: " + Arrays.toString(appCtx.getBeanDefinitionNames()));
            AdminRestController adminUserController = appCtx.getBean(AdminRestController.class);
            adminUserController.create(new User(null, "userName", "email", "password", Role.ROLE_ADMIN));

            MealRestController mealRestController = appCtx.getBean(MealRestController.class);
            List<MealWithExceed> mealsWithExceeds = mealRestController.getBetween(LocalDate.of(2015, Month.MAY, 30), LocalTime.of(10, 0),
                    LocalDate.of(2015, Month.MAY, 31), LocalTime.of(17, 0));
            mealsWithExceeds.forEach(System.out::println);

            Meal meal = mealRestController.get(3);
            System.out.println(meal);

            mealRestController.delete(3);

            List<Meal> all = mealRestController.getAll();
            all.forEach(System.out::println);

            mealRestController.create(new Meal(LocalDateTime.of(2015, Month.MAY, 30, 14, 12), "Новый Обед", 900));

            all = mealRestController.getAll();
            all.forEach(System.out::println);
        }
    }
}
