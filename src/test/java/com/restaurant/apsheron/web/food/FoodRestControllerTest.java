package com.restaurant.apsheron.web.food;


import com.restaurant.apsheron.web.AbstractControllerTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import com.restaurant.apsheron.model.Food;
import com.restaurant.apsheron.service.FoodService;
import com.restaurant.apsheron.util.exception.NotFoundException;
import com.restaurant.apsheron.web.json.JsonUtil;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static com.restaurant.apsheron.FoodTestData.*;
import static com.restaurant.apsheron.TestUtil.readFromJson;
import static com.restaurant.apsheron.ManagerTestData.USER;
import static com.restaurant.apsheron.ManagerTestData.USER_ID;
import static com.restaurant.apsheron.model.AbstractBaseEntity.START_SEQ;
import static com.restaurant.apsheron.util.FoodsUtil.createTo;
import static com.restaurant.apsheron.util.FoodsUtil.getTos;

class FoodRestControllerTest extends AbstractControllerTest {

    private static final String REST_URL = FoodRestController.REST_URL + '/';

    @Autowired
    private FoodService foodService;

    @Test
    void get() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + FOOD1_ID))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(FOOD_MATCHER.contentJson(FOOD1));
    }

    @Test
    void delete() throws Exception {
        perform(MockMvcRequestBuilders.delete(REST_URL + FOOD1_ID))
                .andExpect(status().isNoContent());
        assertThrows(NotFoundException.class, () -> foodService.get(FOOD1_ID, USER_ID));
    }

    @Test
    void update() throws Exception {
        Food updated = getUpdated();
        perform(MockMvcRequestBuilders.put(REST_URL + FOOD1_ID).contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(updated)))
                .andExpect(status().isNoContent());

        FOOD_MATCHER.assertMatch(foodService.get(FOOD1_ID, START_SEQ), updated);
    }

    @Test
    void createWithLocation() throws Exception {
        Food newFood = getNew();
        ResultActions action = perform(MockMvcRequestBuilders.post(REST_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(JsonUtil.writeValue(newFood)));

        Food created = readFromJson(action, Food.class);
        int newId = created.id();
        newFood.setId(newId);
        FOOD_MATCHER.assertMatch(created, newFood);
        FOOD_MATCHER.assertMatch(foodService.get(newId, USER_ID), newFood);
    }

    @Test
    void getAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL))
                .andExpect(status().isOk())
                .andDo(print())
                .andExpect(content().contentTypeCompatibleWith(MediaType.APPLICATION_JSON))
                .andExpect(FOOD_TO_MATCHER.contentJson(getTos(FOODS, USER.getCaloriesPerDay())));
    }

    @Test
    void filter() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter")
                .param("startDate", "2020-01-30").param("startTime", "07:00")
                .param("endDate", "2020-01-31").param("endTime", "11:00"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(FOOD_TO_MATCHER.contentJson(createTo(FOOD5, true), createTo(FOOD1, false)));
    }

    @Test
    void filterAll() throws Exception {
        perform(MockMvcRequestBuilders.get(REST_URL + "filter?startDate=&endTime="))
                .andExpect(status().isOk())
                .andExpect(FOOD_TO_MATCHER.contentJson(getTos(FOODS, USER.getCaloriesPerDay())));
    }
}