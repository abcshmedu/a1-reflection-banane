package edu.hm.banane.reflection;

import java.util.Date;

/**
 * Irgendeine Klasse zum Testen.
 */
public class SomeClass {

    private static final int SOME_NUMBS = 123456789;
    @RenderMe private int foo;
    @RenderMe(with = "edu.hm.banane.reflection.ArrayRenderer")private int[] array = {1, 2, 3};
    @RenderMe private Date date = new Date(SOME_NUMBS);

    /**
     * Erzeugt eine Klasse.
     * @param foo der Wert
     */
    public SomeClass(int foo) {
        this.foo = foo;
    } 
}
