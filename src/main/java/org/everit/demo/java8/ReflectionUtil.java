package org.everit.demo.java8;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;

public class ReflectionUtil {

    /**
     * Collects the getter {@link Class#getDeclaredMethods() methods declared} in {@code clazz} which' name starts with
     * {@code "get"}.
     *
     * @param clazz
     * @return
     */
    public List<Method> collectDeclaredGetters(final Class<?> clazz) {
        // TODO
        return null;
    }

    /**
     * @param clazz
     * @param treshold
     * @return the methods in {@code clazz} which' methods have longer parameter list than {@code treshold}. The
     *         returned methods should be sorted by the method names.
     */
    public List<Method> collectMethodsWithTooLongParamList(final Class<?> clazz, final int treshold) {
        // TODO
        return null;
    }

    /**
     * @param clazz
     * @return a distinct list of the return types of the methods of {@code clazz}.
     */
    public List<Class<?>> collectReturnTypesOfMethods(final Class<?> clazz) {
        // TODO
        return null;
    }

    public long countMethodsWithDefaultImplementation(final Class<?> intf) {
        // TODO
        return 0;
    }

    /**
     * @param classes
     * @return the average number of public methods in {@code classes}.
     */
    public double getAverageCountOfPublicMethods(final Collection<Class<?>> classes) {
        // TODO
        return 0.0;
    }

    /**
     * @param clazz
     * @return the default (parameterless) constructor of {@code clazz}.
     * @throws IllegalArgumentException
     *             if {@code clazz} does not have a default constructor.
     */
    public Constructor<?> getDefaultConstructor(final Class<?> clazz) {
        // TODO
        return null;
    }

    /**
     * Returns the first 3 public static final fields of {@code clazz}, sorted by their name.
     *
     * @param clazz
     */
    public List<Field> getFirstThreeConstants(final Class<?> clazz) {
        // TODO
        return null;
    }
}
