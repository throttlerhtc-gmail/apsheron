package com.restaurant.apsheron;

import com.restaurant.apsheron.model.Auth;
import com.restaurant.apsheron.model.Manager;

import java.util.Collections;
import java.util.Date;

import static com.restaurant.apsheron.model.AbstractBaseEntity.START_SEQ;

public class ManagerTestData {
    public static TestMatcher<Manager> USER_MATCHER = TestMatcher.usingFieldsWithIgnoringComparator(Manager.class, "registered","meals");

    public static final int NOT_FOUND = 10;
    public static final int USER_ID = START_SEQ;
    public static final int ADMIN_ID = START_SEQ + 1;

    public static final Manager USER = new Manager(USER_ID, "Manager", "user@yandex.ru", "password", Auth.USER);
    public static final Manager ADMIN = new Manager(ADMIN_ID, "Admin", "admin@gmail.com", "admin", Auth.ADMIN, Auth.USER);

    public static Manager getNew() {
        return new Manager(null, "New", "new@gmail.com", "newPass", 1555, false, new Date(), Collections.singleton(Auth.USER));
    }

    public static Manager getUpdated() {
        Manager updated = new Manager(USER);
        updated.setName("UpdatedName");
        updated.setCaloriesPerDay(330);
        updated.setAuths(Collections.singletonList(Auth.ADMIN));
        return updated;
    }
}
