package edu.hm.banane.reflection;

/**
 * Klasse welche fuer das rendern von Integer Arrays dient.
 */
public class ArrayRenderer {

    /**
     * Rendern Integer Arrays.
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
