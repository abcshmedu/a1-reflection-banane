package edu.hm.banane.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

/**
 * Die Render Klasse gibt mittels Reflection alle
 * Instanzvariablen eines Objektes aus, welche mit
 * der RenderMe-Annotation bezeichnet worden sind.
 */
public class Renderer {
    /**
     * Das Objekt, dessen Instanzvariablen ausgegeben werden sollen.
     */
    private Object o;

    /**
     * Erzeugt einen neuen Renderer.
     *
     * @param object Das Objekt, dessen Instanzvariablen ausgegeben werden sollen.
     */
    public Renderer(Object object) {
        this.o = object;
    }

    /**
     * Erzeugt einen String mit Name (Typ) Wert von allen Instanzvariblen eines Objektes,
     * welche mit RenderMe gekennzeichnet worden sind.
     *
     * @return String.
     */
    public String render() {
        final Class<?> c = o.getClass();
        final Field[] fields = c.getDeclaredFields();
        final StringBuilder strb = new StringBuilder(256);
        strb.append("Instance of ")
                .append(c.getName())
                .append(":\n");
        for (Field field : fields) {
            if (field.getAnnotation(RenderMe.class) != null) {
                strb.append(field.getName())
                        .append(" (Type ");
                if (field.getType().isArray()) {
                    strb.append(field.getType().getComponentType())
                            .append("[]");
                } else {
                    strb.append(field.getType().getName());
                }
                strb.append("): ");
                try {
                    field.setAccessible(true);
                    Object obj = field.get(o);
                    if (field.getType().isArray()) {
                        int length = Array.getLength(obj);
                        strb.append("[");
                        for (int i = 0; i < length; i++) {
                            strb.append(Array.get(obj, i))
                                    .append(", ");
                        }
                        strb.append("]");
                    } else {
                        strb.append(obj);
                    }
                    strb.append("\n");
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
        return strb.toString();
    }
}
