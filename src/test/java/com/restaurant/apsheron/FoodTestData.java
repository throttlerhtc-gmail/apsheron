package com.restaurant.apsheron;

import com.restaurant.apsheron.model.Food;
import com.restaurant.apsheron.to.FoodTo;

import java.time.Month;
import java.util.List;

import static java.time.LocalDateTime.of;
import static com.restaurant.apsheron.model.AbstractBaseEntity.START_SEQ;

public class FoodTestData {
    public static TestMatcher<Food> FOOD_MATCHER = TestMatcher.usingFieldsWithIgnoringComparator(Food.class, "user");
    public static TestMatcher<FoodTo> FOOD_TO_MATCHER = TestMatcher.usingEqualsComparator(FoodTo.class);

    public static final int NOT_FOUND = 10;
    public static final int FOOD1_ID = START_SEQ + 2;
    public static final int ADMIN_FOOD_ID = START_SEQ + 9;

    public static final Food FOOD1 = new Food(FOOD1_ID, of(2020, Month.JANUARY, 30, 10, 0), "Завтрак", 500);
    public static final Food FOOD2 = new Food(FOOD1_ID + 1, of(2020, Month.JANUARY, 30, 13, 0), "Обед", 1000);
    public static final Food FOOD3 = new Food(FOOD1_ID + 2, of(2020, Month.JANUARY, 30, 20, 0), "Ужин", 500);
    public static final Food FOOD4 = new Food(FOOD1_ID + 3, of(2020, Month.JANUARY, 31, 0, 0), "Еда на граничное значение", 100);
    public static final Food FOOD5 = new Food(FOOD1_ID + 4, of(2020, Month.JANUARY, 31, 10, 0), "Завтрак", 500);
    public static final Food FOOD6 = new Food(FOOD1_ID + 5, of(2020, Month.JANUARY, 31, 13, 0), "Обед", 1000);
    public static final Food FOOD7 = new Food(FOOD1_ID + 6, of(2020, Month.JANUARY, 31, 20, 0), "Ужин", 510);
    public static final Food ADMIN_FOOD1 = new Food(ADMIN_FOOD_ID, of(2020, Month.JANUARY, 31, 14, 0), "Админ ланч", 510);
    public static final Food ADMIN_FOOD2 = new Food(ADMIN_FOOD_ID + 1, of(2020, Month.JANUARY, 31, 21, 0), "Админ ужин", 1500);

    public static final List<Food> FOODS = List.of(FOOD7, FOOD6, FOOD5, FOOD4, FOOD3, FOOD2, FOOD1);

    public static Food getNew() {
        return new Food(null, of(2020, Month.FEBRUARY, 1, 18, 0), "Созданный ужин", 300);
    }

    public static Food getUpdated() {
        return new Food(FOOD1_ID, FOOD1.getDateTime(), "Обновленный завтрак", 200);
    }
}
