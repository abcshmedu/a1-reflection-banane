package edu.hm.banane.reflection;

/**
 * Klasse welche fuer das rendern von Integer Arrays dient.
 */
public class ArrayRenderer {
    public static String render(Integer[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (Integer i : array) {
            stringBuilder.append(i + ", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static String render(Float[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (Float i : array) {
            stringBuilder.append(i + ", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static String render(Double[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (Double i : array) {
            stringBuilder.append(i + ", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static String render(Boolean[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (Boolean i : array) {
            stringBuilder.append(i + ", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    public static String render(Object[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (Object i : array) {
            stringBuilder.append(i + ", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * Render generic number array.
     *
     * @param array The array to be rendered.
     * @param <T>   The type of the array
     * @return Values of the Array in a string.
     */
    public static <T extends Number> String render(T[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (T i : array) {
            stringBuilder.append(i + ", ");
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }

    /**
     * Rendert Integer Arrays.
     * @param array Ein Integer Array
     * @return Wertvolle Informationen zu dessen Array
     */
    public String render(int[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("[");
        for (int i : array) {
            stringBuilder.append(String.format("%d, ", i));
        }
        stringBuilder.append("]");
        return stringBuilder.toString();
    }
}
