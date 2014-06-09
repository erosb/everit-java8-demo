package org.everit.demo.java8;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.ImageCapabilities;
import java.awt.image.BufferedImage;
import java.awt.image.ImageProducer;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.ConsoleHandler;

import org.junit.Assert;
import org.junit.Test;

public class ReflectionUtilTest {

    private ReflectionUtil subject() {
        return new ReflectionUtil();
    }

    @Test
    public void testCollectDeclaredGetters() throws NoSuchMethodException, SecurityException {
        List<Method> actual = subject().collectDeclaredGetters(LinkedList.class);
        Assert.assertEquals(3, actual.size());
        Assert.assertTrue(actual.contains(LinkedList.class.getMethod("getFirst")));
        Assert.assertTrue(actual.contains(LinkedList.class.getMethod("getLast")));
        Assert.assertTrue(actual.contains(LinkedList.class.getMethod("get", int.class)));
    }

    @Test
    public void testCollectReturnTypesOfMethods() {
        List<Class<?>> actual = subject().collectReturnTypesOfMethods(Image.class);
        Assert.assertEquals(8, actual.size());
        Assert.assertEquals(new HashSet<Class<?>>(actual).size(), actual.size());
        Assert.assertTrue(actual.contains(void.class));
        Assert.assertTrue(actual.contains(float.class));
        Assert.assertTrue(actual.contains(ImageCapabilities.class));
        Assert.assertTrue(actual.contains(Graphics.class));
        Assert.assertTrue(actual.contains(int.class));
        Assert.assertTrue(actual.contains(Object.class));
        Assert.assertTrue(actual.contains(Image.class));
        Assert.assertTrue(actual.contains(ImageProducer.class));
    }

    @Test
    public void testCountMethodsWithDefaultImplementation() {
        long actual = subject().countMethodsWithDefaultImplementation(Comparator.class);
        Assert.assertEquals(7L, actual);
    }

    @Test
    public void testGetAverageCountOfPublicMethods() {
        double actual = subject().getAverageCountOfPublicMethods(Arrays.asList(ConsoleHandler.class));
        Assert.assertEquals(2.0, actual, 0.0);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetDefaultConstructor() throws NoSuchMethodException, SecurityException {
        Constructor<?> actual = subject().getDefaultConstructor(Object.class);
        Assert.assertEquals(actual, Object.class.getConstructor());
        subject().getDefaultConstructor(Integer.class);
    }

    @Test
    public void testGetFirstThreeConstants() throws NoSuchFieldException, SecurityException {
        List<Field> actual = subject().getFirstThreeConstants(BufferedImage.class);
        Assert.assertEquals(3, actual.size());
        Assert.assertEquals(BufferedImage.class.getField("TYPE_3BYTE_BGR"), actual.get(0));
        Assert.assertEquals(BufferedImage.class.getField("TYPE_4BYTE_ABGR"), actual.get(1));
        Assert.assertEquals(BufferedImage.class.getField("TYPE_4BYTE_ABGR_PRE"), actual.get(2));
    }

    @Test
    public void testGetLongParamList() throws NoSuchMethodException, SecurityException {
        List<Method> actual = subject().collectMethodsWithTooLongParamList(BufferedImage.class, 5);
        Assert.assertEquals(2, actual.size());
        Assert.assertEquals(BufferedImage.class.getMethod("getRGB", int.class, int.class, int.class, int.class,
                int[].class, int.class, int.class), actual.get(0));
        Assert.assertEquals(BufferedImage.class.getMethod("setRGB", int.class, int.class, int.class, int.class,
                int[].class, int.class, int.class), actual.get(1));
    }
}
