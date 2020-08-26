package com.restaurant.apsheron.web;

import com.restaurant.apsheron.model.AbstractBaseEntity;
import com.restaurant.apsheron.util.FoodsUtil;

public class SecurityUtil {

    private static int id = AbstractBaseEntity.START_SEQ;

    private SecurityUtil() {
    }

    public static int authManagerId() {
        return id;
    }

    public static void setAuthManagerId(int id) {
        SecurityUtil.id = id;
    }

    public static int authManagerCaloriesPerDay() {
        return FoodsUtil.DEFAULT_CALORIES_PER_DAY;
    }
}