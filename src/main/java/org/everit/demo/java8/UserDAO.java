package org.everit.demo.java8;


import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class UserDAO {

    private final Collection<User> users;

    private final Supplier<Collection<User>> collSupplier;

    public UserDAO(final Collection<User> users, final Supplier<Collection<User>> collSupplier) {
        this.users = Objects.requireNonNull(users, "users cannot be null");
        this.collSupplier = Objects.requireNonNull(collSupplier, "collSupplier cannot be null");
    }
    
    public SortedMap<Integer, Collection<User>> groupUsersByAge() {
        SortedMap<Integer, Collection<User>> rval = new TreeMap<>();
        for (User users : users) {
            Collection<User> peopleByAge = rval.get(users.getAge());
            if (peopleByAge == null) {
                peopleByAge = collSupplier.get();
                rval.put(users.getAge(), peopleByAge);
            }
            peopleByAge.add(users);
        }
        return rval;
    }
    
    public User findUserByName(final String name) {
		return users.stream()
			.filter(user -> user.getName().equals(name))
			.findFirst()
			.orElseThrow(() -> new IllegalArgumentException("user [" + name + "] cannot be null"));
    }
    
    public User getYoungestUser() {
		return users.stream().min((user1, user2) -> user1.getAge() - user2.getAge()).orElse(null);
    }
    
    public int getAgeSum() {
		return users.stream().mapToInt(User::getAge).sum();
    }


}
