package edu.hm.banane.reflection;

public class SomeTestClass {
    @RenderMe
    float f = 3.0f;
    @RenderMe
    double d = 3.0;
    @RenderMe
    int i = 1;
    @RenderMe
    boolean b = true;
    @RenderMe(with = "edu.hm.banane.reflection.ArrayRenderer")
    Integer[] intarray = {1, 5, 3, 432, 23, Integer.MAX_VALUE, Integer.MIN_VALUE};
    @RenderMe(with = "edu.hm.banane.reflection.ArrayRenderer")
    Boolean[] boolarray = {false, true, false, true};
    @RenderMe(with = "edu.hm.banane.reflection.ArrayRenderer")
    Float[] floatarray = {1.0f, 2.f, 4.f};
    @RenderMe(with = "edu.hm.banane.reflection.ArrayRenderer")
    Double[] doublearry = {1.0, 3.0, 4.0};

    public SomeTestClass() {
    }

    @RenderMe
    int testMe1() {
        return 0;
    }
}
