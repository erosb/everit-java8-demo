package org.everit.demo.java8;

import java.util.Comparator;
import java.util.Objects;

public class User {

    public static final Comparator<User> AGE_COMPARATOR_JAVA7 = new Comparator<User>() {

        @Override
        public int compare(final User user1, final User user2) {
            return user1.age - user2.age;
        }
    };

    public static final Comparator<User> AGE_COMPARATOR_JAVA8_V1 = (final User user1, final User user2) -> {
        return user1.age - user2.age;
    };

    public static final Comparator<User> AGE_COMPARATOR_JAVA8_V2 =
            (final User user1, final User user2) -> user1.age - user2.age;

    public static final Comparator<User> AGE_COMPARATOR_JAVA8_V3 = (user1, user2) -> user1.age - user2.age;

    private final int age;

    private final String name;

    public User(final int age, final String name) {
        this.age = age;
        this.name = Objects.requireNonNull(name, "name cannot be null");
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

}
