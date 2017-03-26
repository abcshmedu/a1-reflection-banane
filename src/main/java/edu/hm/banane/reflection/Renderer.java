package edu.hm.banane.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Field;

public class Renderer {
    private Object o;

	public Renderer(Object object) {
        this.o = object;
    }

    public String render() {
        Class<?> c = o.getClass();
        Field[] fields = c.getDeclaredFields();
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
