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

                }
        });
    }

    private static final int SOME_NUMB = 5;
    private Renderer renderer;

    private Object input;
    private String expected;

    public RendererTest(Object input, String expected) {
        this.input = input;
        this.expected = expected;
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
