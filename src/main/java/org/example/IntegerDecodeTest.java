package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Method;
import java.util.Arrays;

public class IntegerDecodeTest {
    private static Class<?> testingClass;

    @BeforeAll
    static void setTestingClass() {
        try {
            testingClass = Class.forName("java.lang.Integer");
        } catch (Exception e) {
            throw new AssertionError("Class not found");
        }
    }

    @Test
    public void testMethodExisting() {
        try {
            Method method = testingClass.getMethod("decode", String.class);
            if (method == null) {
                throw new AssertionError("Method doesn't exist");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMethodReturn() {
        try {
            Method method = Arrays.stream(testingClass.getDeclaredMethods())
                    .filter(x -> x.getName().equals("decode"))
                    .findFirst()
                    .get();
            if (!method.getReturnType().equals(Integer.class)) {
                throw new AssertionError("Method must return the Integer type");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testMethodParameters() {
        try {
            Method method = Arrays.stream(testingClass.getDeclaredMethods())
                    .filter(x -> x.getName().equals("decode"))
                    .findFirst()
                    .get();
            if (method.getParameters().length != 1) {
                throw new AssertionError("Method must take one parameter");
            }
            if (!method.getParameters()[0].getType().equals(String.class)) {
                throw new AssertionError("Method must take the String type");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void testEmpty() {
        Assertions.assertThrows(NumberFormatException.class, () -> Integer.decode(""));
    }

    @Test
    public void testNegativeValue() {
        Assertions.assertEquals(-1, Integer.decode("-1"));
        Assertions.assertEquals(-2, Integer.decode("-2"));
        Assertions.assertEquals(-3, Integer.decode("-3"));
    }

    @Test
    public void testPositiveValue() {
        Assertions.assertEquals(1, Integer.decode("+1"));
        Assertions.assertEquals(2, Integer.decode("+2"));
        Assertions.assertEquals(3, Integer.decode("+3"));
    }

    @Test
    public void testHexRadix1() {
        Assertions.assertEquals(10, Integer.decode("0xA"));
        Assertions.assertEquals(11, Integer.decode("0xB"));
        Assertions.assertEquals(12, Integer.decode("0xC"));
        Assertions.assertEquals(16, Integer.decode("0X10"));
    }

    @Test
    public void testHexRadix2() {
        Assertions.assertEquals(10, Integer.decode("#A"));
        Assertions.assertEquals(11, Integer.decode("#B"));
        Assertions.assertEquals(12, Integer.decode("#C"));
        Assertions.assertEquals(16, Integer.decode("#10"));
    }

    @Test
    public void testOctalRadix() {
        Assertions.assertEquals(5, Integer.decode("05"));
        Assertions.assertEquals(8, Integer.decode("010"));
        Assertions.assertEquals(9, Integer.decode("011"));
        Assertions.assertEquals(17, Integer.decode("021"));
        Assertions.assertEquals(45, Integer.decode("055"));
    }

    @Test
    public void testNumberFormat() {
        Assertions.assertThrows(NumberFormatException.class, () -> Integer.decode("2+2"));
        Assertions.assertThrows(NumberFormatException.class, () -> Integer.decode("3 2"));
        Assertions.assertThrows(NumberFormatException.class, () -> Integer.decode("++5"));
    }

    @Test
    public void testConstant() {
        Assertions.assertEquals(Integer.MIN_VALUE, Integer.decode(Integer.toString(Integer.MIN_VALUE)));
        Assertions.assertEquals(Integer.MAX_VALUE, Integer.decode(Integer.toString(Integer.MAX_VALUE)));
    }

    @Test
    public void testRandomString() {
        Assertions.assertThrows(NumberFormatException.class, () -> Integer.decode("Hello"));
        Assertions.assertThrows(NumberFormatException.class, () -> Integer.decode("      "));
    }

    @Test
    public void testNegativeHexValue() {
        Assertions.assertEquals(-10, Integer.decode("-#A"));
        Assertions.assertEquals(-16, Integer.decode("-0x10"));
    }

    @Test
    public void testNegativeOctalValue() {
        Assertions.assertEquals(-10, Integer.decode("-012"));
    }

    @Test
    public void testStartWithZero() {
        Assertions.assertNotEquals(12, Integer.decode("012"));
    }
}
