package edu.hm.banane.reflection;

import static org.junit.Assert.*;

import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;


/**
 * JUnit Testklasse fuer Renderer.
 */
@RunWith(Parameterized.class)
public class RendererTest {
    private Renderer renderer;
    private Object input;
    private String expected;

    public RendererTest(Object input, String expected) {
        this.input = input;
        this.expected = expected;
    }

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(new Object[][]{
                {
                        new SomeClass(5),
                        "Instance of edu.hm.banane.reflection.SomeClass:\n"
                                + "foo (Type int): 5\n"
                                + "array (Type int[]): [1, 2, 3, ]\n"
                                + "date (Type java.util.Date): Fri Jan 02 11:17:36 CET 1970\n"
                },
                {
                        new GenericNumberTestClass<>(new Double[]{1.0, 2.0, 3.0}, 1.0),
                        "Instance of edu.hm.banane.reflection.GenericNumberTestClass:\n"
                                + "array (Type java.lang.Number[]): [1.0, 2.0, 3.0, ]\n"
                                + "t (Type java.lang.Number): 1.0\n"
                },
                {
                        new SomeTestClass(),
                        "Instance of edu.hm.banane.reflection.SomeTestClass:\n" +
                                "f (Type float): 3.0\n" +
                                "d (Type double): 3.0\n" +
                                "i (Type int): 1\n" +
                                "b (Type boolean): true\n" +
                                "intarray (Type java.lang.Integer[]): [1, 5, 3, 432, 23, 2147483647, -2147483648, ]\n" +
                                "boolarray (Type java.lang.Boolean[]): [false, true, false, true, ]\n" +
                                "floatarray (Type java.lang.Float[]): [1.0, 2.0, 4.0, ]\n" +
                                "doublearry (Type java.lang.Double[]): [1.0, 3.0, 4.0, ]\n" +
                                "testMe1 (Return Type int): 0\n"
                }
        });
    }

    /**
     *
     */
    @Before
    public void setUp() {
        renderer = new Renderer(input);
    }

    /**
     * @throws Exception wenn etwas schief geht
     */
    @Test
    public void testRendering() throws Exception {
        assertEquals(expected, renderer.render());
    }
}
