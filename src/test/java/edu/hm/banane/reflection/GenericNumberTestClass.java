package edu.hm.banane.reflection;

/**
 * Generic Class to see type erasure in action.
 *
 * @param <T>
 */
public class GenericNumberTestClass<T extends Number> {
    @RenderMe(with = "edu.hm.banane.reflection.ArrayRenderer")
    T[] array;
    @RenderMe
    T t;

    public GenericNumberTestClass(T[] array, T value) {
        this.array = array;
        t = value;
    }
}
