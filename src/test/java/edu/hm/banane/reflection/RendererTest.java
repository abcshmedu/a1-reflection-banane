package edu.hm.banane.reflection;

import static org.junit.Assert.*;
import org.junit.*;


/**
 * JUnit Testklasse fuer Renderer.
 */
public class RendererTest {

    private static final int SOME_NUMB = 5;
    private SomeClass toRender;
    private Renderer renderer;

    /**
     * 
     */
    @Before      
    public void setUp() {
        toRender = new SomeClass(SOME_NUMB);
        renderer = new Renderer(toRender);
    }

    /**
     * @throws Exception wenn etwas schief geht
     */
    @Test      
    public void testRendering() throws Exception {
        assertEquals("Instance of edu.hm.banane.reflection.SomeClass:\n"
                + "foo (Type int): 5\n"
                + "array (Type int[]): [1, 2, ]\n"
                + "date (Type java.util.Date): Fri Jan 02 11:17:36 CET 1970\n",
                renderer.render());
    }
}
