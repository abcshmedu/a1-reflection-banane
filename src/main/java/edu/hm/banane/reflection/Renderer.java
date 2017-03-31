package edu.hm.banane.reflection;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Function;

/**
 * Die Render Klasse gibt mittels Reflection alle
 * Instanzvariablen eines Objektes aus, welche mit
 * der RenderMe-Annotation bezeichnet worden sind.
 */
public class Renderer {

    private static final String TITLE_STRF = "Instance of %s:\n";
    private static final String FIELD_STRF = "%s (Type %s): %s\n";
    private static final String METHOD_STRF = "%s (Return Type %s): %s\n";
    private static final String RENDERER_METHOD = "render";

    /**
     * Das Objekt, dessen Instanzvariablen ausgegeben werden sollen.
     */
    private Object obj;

    /**
     * Erzeugt einen neuen Renderer.
     *
     * @param object Das Objekt, dessen Instanzvariablen ausgegeben werden sollen.
     */
    public Renderer(Object object) {
        this.obj = object;
    }

    /**
     * Prueft ob eine Methode mit RenderMe deklariert worden ist und falls dies zutrifft
     * wird diese Methode aufgerufen. Wenn die Methode nicht mit RenderMe deklariert worden ist
     * wird null zurueckgegeben.
     * Nach dem Aufruf werden die gesamelten Informationen dann als String zurueckgegeben.
     * Diese Methode darf keine Parameterliste haben.
     *
     * @param method Die jeweilige Methode
     * @return "Name (Rueckgabe Typ) Wert \n" von der Methode als String oder null
     * @throws ReflectiveOperationException wenn die Methode eine Parameterliste hat
     */
    private String handleMethod(Method method) throws ReflectiveOperationException {
        String renderer = getRender(method);
        if (renderer == null) {
            return null;
        }
        String mName = method.getName();
        Class< ? > mRetType = method.getReturnType();
        method.setAccessible(true);
        Object mValue = method.invoke(obj);
        method.setAccessible(false);
        if (!renderer.isEmpty()) {
            mValue = execCustomRenderer(renderer, mValue);
        }
        return String.format(METHOD_STRF, mName, mRetType.getTypeName(), mValue);
    }

    /**
     * Prueft ob ein Feld mit RenderMe deklariert worden ist und falls dies zutrifft
     * werden gesamelten Informationen ueber das Feld dann als String zurueckgegeben.
     * Wenn das Feld nicht mit RenderMe deklariert worden ist
     * wird null zurueckgegeben.
     *
     * @param field Das jeweilige Feld
     * @return "Name (Typ) Wert \n" von dem Feld als String oder null
     * @throws ReflectiveOperationException wenn die ausgewaehlten Renderer Klasse nicht gefunden werden kann
     */
    private String handleField(Field field) throws ReflectiveOperationException {
        String renderer = getRender(field);
        if (renderer == null) {
            return null;
        }
        String fName = field.getName();
        Class< ? > fType = field.getType();
        field.setAccessible(true);
        Object fValue = field.get(obj);
        field.setAccessible(false);
        if (!renderer.isEmpty()) {
            fValue = execCustomRenderer(renderer, fValue);
        }
        return String.format(FIELD_STRF, fName, fType.getTypeName(), fValue);
    }

    /**
     * Versucht bei einem Element welches mit RenderMe ggf. deklariert ist den Renderer zu bestimen.
     * Ist das Element nicht deklariert wird null als Ergebnis geliefert.
     * Wurde kein spezieller Renderer mittels with Parameter bestimmt wird ein leerer String zurueckgegeben,
     * andernfalls wird der Name des spezieller Renderer zurueckgegeben.
     *
     * @param annotatedElement das zu pruefende Element
     * @return null, leerer String oder Name der Renderer Klasse
     */
    private static String getRender(AnnotatedElement annotatedElement) {
        RenderMe renderMe = annotatedElement.getAnnotation(RenderMe.class);
        return renderMe == null ? null : renderMe.with();
    }

    /**
     * Ruft dynamisch die Methode render() des definierten Renderer mit dem jeweiligen Parameter auf
     * und gibt dessen Rueckgabewert zurueck.
     *
     * @param customRender Der Name des spezieller Renderer
     * @param object Objekt welches render() uebergeben wird
     * @return Ruckgabewert der Methode render() der ausgewaehlten Renderer Klasse
     * @throws ReflectiveOperationException wenn keine passende Methode gefunden wurde
     */
    private static String execCustomRenderer(String customRender,  Object object) throws ReflectiveOperationException {
        Class< ? > renderer = Class.forName(customRender);
        Object objRenderer = renderer.newInstance();
        Method render = renderer.getMethod(RENDERER_METHOD, object.getClass());
        return (String) render.invoke(objRenderer, object);
    }

    /**
     * Erzeugt einen String mit "Name (Typ) Wert" von allen Instanzvariblen eines Objektes,
     * welche mit RenderMe gekennzeichnet worden sind.
     *
     * @return String.
     * @throws ReflectiveOperationException wenn was schief geht.
     */
    public String render() throws ReflectiveOperationException {
        final StringBuilder strb = new StringBuilder();
        final Class< ? > c = obj.getClass();
        strb.append(String.format(TITLE_STRF, c.getName()));
        ThrowingFunction<Field, String> fieldFunction = field -> handleField(field);
        ThrowingFunction<Method, String> methodFunction = method -> handleMethod(method);
        Arrays.stream(c.getDeclaredFields())
                .map(fieldFunction)
                .filter(Objects::nonNull)
                .forEach(strb::append);
        Arrays.stream(c.getDeclaredMethods())
                .map(methodFunction)
                .filter(Objects::nonNull)
                .forEach(strb::append);
        return strb.toString();
    }

    /**
     * Functional Interface, wandelt CheckedExceptions in RuntimeExceptions um.
     *
     * @param <T> der Parameter-Typ der Funktion
     * @param <R> der Rueckgabewert-Typ der Funktion
     */
    @FunctionalInterface
    public interface ThrowingFunction<T, R> extends Function<T, R> {
        /**
         * Ueberschreibt die apply Methode, wandelt CheckedException in RuntimeException um.
         *
         * @param t der Parameter der jeweilgen Funktion.
         * @return der Return-Wert der jeweiligen Funktion.
         */
        @Override
        default R apply(T t) {
            try {
                return applyThrows(t);
            } catch (final Exception e) {
                throw new RuntimeException(e);
            }
        }

        /**
         * Die Methode, welche eine Exception wirft.
         *
         * @param elem der Parameter der jeweiligen Funktion.
         * @return der Rueckgabewert der jeweiligen Funktion.
         * @throws Exception falls was schief laeuft.
         */
        R applyThrows(T elem) throws Exception;
    }

}
