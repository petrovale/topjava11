package ru.javawebinar.topjava.service;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.DbPopulator;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ContextConfiguration({
        "classpath:spring/spring-app.xml",
        "classpath:spring/spring-db.xml"
})
@RunWith(SpringRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Autowired
    private DbPopulator dbPopulator;

    @Before
    public void setUp() throws Exception {
        dbPopulator.execute();
    }

    @Test
    public void testGet() throws Exception {
        Meal meal = service.get(USER_MEAL1_ID, USER_ID);
        MATCHER.assertEquals(USER_MEAL1, meal);
    }

    @Test(expected = NotFoundException.class)
    public void testGetNotFound() throws Exception {
        service.get(1, USER_ID);
    }

    @Test
    public void testDelete() throws Exception {
        service.delete(USER_MEAL1_ID, USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL6, USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2), service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void testNotFoundDelete() throws Exception {
        service.delete(1, USER_ID);
    }

    @Test
    public void getBetweenDateTimes() throws Exception {
        List<Meal> mealsOfFiltered = service.getBetweenDates(LocalDate.of(2015, Month.MAY, 30), LocalDate.of(2015, Month.MAY, 30), USER_ID);
        MATCHER.assertCollectionEquals(Arrays.asList(USER_MEAL3, USER_MEAL2, USER_MEAL1), mealsOfFiltered);
    }

    @Test
    public void testGetAll() throws Exception {
        Collection<Meal> all = service.getAll(USER_ID);
        MATCHER.assertCollectionEquals(USER_MEALS, all);
    }

    @Test
    public void testUpdate() throws Exception {
        Meal updated = getUpdated();
        service.update(updated, USER_ID);
        MATCHER.assertEquals(updated, service.get(updated.getId(), USER_ID));
    }

    @Test
    public void testSave() throws Exception {
        Meal newMeal = getCreated();
        Meal created = service.save(newMeal, USER_ID);
        newMeal.setId(created.getId());
        MATCHER.assertCollectionEquals(Arrays.asList(created, USER_MEAL6, USER_MEAL5, USER_MEAL4, USER_MEAL3, USER_MEAL2, USER_MEAL1), service.getAll(USER_ID));
    }

    @Test(expected = DataAccessException.class)
    public void testDuplicateDateTImeSave() throws Exception {
        Meal newMeal = getCreated();
        newMeal.setDateTime(LocalDateTime.of(2015, Month.MAY, 30, 20, 0));
        service.save(newMeal, USER_ID);
    }

}