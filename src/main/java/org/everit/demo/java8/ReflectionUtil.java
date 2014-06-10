package org.everit.demo.java8;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ReflectionUtil {

    private static final Predicate<Field> isFinal = (field) -> (field.getModifiers() & Modifier.FINAL) == Modifier.FINAL;

    private static boolean isPublic(final Field field) {
        return (field.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC;
    }

    /**
     * Collects the getter {@link Class#getDeclaredMethods() methods declared} in {@code clazz} which' name starts with
     * {@code "get"}.
     *
     * @param clazz
     * @return
     */
    public List<Method> collectDeclaredGetters(final Class<?> clazz) {
        return Stream.of(clazz.getDeclaredMethods())
                .filter((method) -> method.getName().startsWith("get"))
                .collect(Collectors.toList());
    }

    /**
     * @param clazz
     * @param treshold
     * @return the methods in {@code clazz} which' methods have longer parameter list than {@code treshold}. The
     *         returned methods should be sorted by the method names.
     */
    public List<Method> collectMethodsWithTooLongParamList(final Class<?> clazz, final int treshold) {
        return Stream.of(clazz.getDeclaredMethods())
                .filter((method) -> (method.getParameterCount() > treshold))
                .sorted((method1, method2) -> method1.getName().compareTo(method2.getName()))
                .collect(Collectors.toList());
    }

    /**
     * @param clazz
     * @return a distinct list of the return types of the methods of {@code clazz}.
     */
    public List<Class<?>> collectReturnTypesOfMethods(final Class<?> clazz) {
        return Stream.of(clazz.getDeclaredMethods())
                .map(Method::getReturnType)
                .distinct()
                .collect(Collectors.toList());
    }

    public long countMethodsWithDefaultImplementation(final Class<?> intf) {
        return Stream.of(intf.getDeclaredMethods()).filter(Method::isDefault).count();
    }

    /**
     * @param classes
     * @return the average number of public methods in {@code classes}.
     */
    public double getAverageCountOfPublicMethods(final Collection<Class<?>> classes) {
        return classes.stream()
                .map(Class::getDeclaredMethods)
                .mapToLong(
                        (methodArr) -> Stream.of(methodArr)
                        .filter((method) -> ((method.getModifiers() & Modifier.PUBLIC) == Modifier.PUBLIC))
                        .count())
                .average()
                .orElse(0);
    }

    /**
     * @param clazz
     * @return the default (parameterless) constructor of {@code clazz}.
     * @throws IllegalArgumentException
     *             if {@code clazz} does not have a default constructor.
     */
    public Constructor<?> getDefaultConstructor(final Class<?> clazz) {
        return Stream.of(clazz.getConstructors())
                .filter((constr) -> constr.getParameterCount() == 0)
                .findAny()
                .orElseThrow(IllegalArgumentException::new);
    }

    /**
     * Returns the first 3 public static final fields of {@code clazz}, sorted by their name.
     *
     * @param clazz
     */
    public List<Field> getFirstThreeConstants(final Class<?> clazz) {
        return Stream.of(clazz.getFields())
                .filter((field) -> field.getDeclaringClass() == clazz)
                .filter(ReflectionUtil::isPublic)
                .filter(isFinal)
                .filter((field) -> (field.getModifiers() & Modifier.STATIC) == Modifier.STATIC)
                .sorted((field1, field2) -> field1.getName().compareTo(field2.getName()))
                .limit(3)
                .collect(Collectors.toList());
    }
}
