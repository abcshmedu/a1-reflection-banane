package edu.hm.banane.reflection;

import java.lang.reflect.AnnotatedElement;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

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
     * @throws ReflectiveOperationException 
     */
    public String render() throws ReflectiveOperationException {
        final StringBuilder strb = new StringBuilder();
        final Class< ? > c = o.getClass();
        strb.append(String.format(TITLE_STRF, c.getName()));
        for (Field field : c.getDeclaredFields()) {
            String str = handleField(field, o);
            if (str != null) {
                strb.append(str);
            }
        }
        for (Method method : c.getDeclaredMethods()) {
            String str = handleMethod(method, o);
            if (str != null) {
                strb.append(str);
            }
        }
        return strb.toString();
    }


    /**
     * Prueft ob eine Methode mit RenderMe deklariert worden ist und falls dies zutrifft
     * wird diese Methode aufgerufen. Wenn die Methode nicht mit RenderMe deklariert worden ist
     * wird null zurueckgegeben.
     * Nach dem Aufruf werden die gesamelten Informationen dann als String zurueckgegeben.
     * Diese Methode darf keine Parameterliste haben.
     * 
     * @param method Die Jeweilige Methode
     * @param object Objekt welches sich auf die Methode bezieht. Wird ignoriert bei einer static Methode
     * @return "Name (Rueckgabe Typ) Wert \n" von der Methode als String oder null
     * @throws ReflectiveOperationException wenn die Methode eine Parameterliste hat
     */
    private static String handleMethod(Method method, Object object) throws ReflectiveOperationException {
        String renderer = getRender(method);
        if (renderer == null) {
            return null;
        }
        String mName = method.getName();
        Class< ? > mRetType = method.getReturnType();
        method.setAccessible(true);
        Object mValue = method.invoke(object, new Object[0]);
        method.setAccessible(false);
        if (!renderer.isEmpty()) {
            mValue = execCustomRenderer(renderer, mRetType, mValue);
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
     * @param object Objekt welches sich auf das Feld bezieht. Wird ignoriert bei einer static Feld
     * @return "Name (Typ) Wert \n" von dem Feld als String oder null
     * @throws ReflectiveOperationException wenn die ausgewaehlten Renderer Klasse nicht gefunden werden kann
     */
    private static String handleField(Field field, Object object) throws ReflectiveOperationException {
        String renderer = getRender(field);
        if (renderer == null) {
            return null;
        }
        String fName = field.getName();
        Class< ? > fType = field.getType();
        field.setAccessible(true);
        Object fValue = field.get(object);
        field.setAccessible(false);
        if (!renderer.isEmpty()) {
            fValue = execCustomRenderer(renderer, fType, fValue);
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
     * @param type Typklasse des Parameters
     * @param value der Wert des Parameters
     * @return Ruckgabewert der Methode render() der ausgewaehlten Renderer Klasse
     * @throws ReflectiveOperationException wenn keine passende Methode gefunden wurde
     */
    private static String execCustomRenderer(String customRender, Class< ? > type, Object value) throws ReflectiveOperationException {
        Class< ? > renderer = Class.forName(customRender);
        Object objRenderer = renderer.newInstance();
        Method render = renderer.getMethod(RENDERER_METHOD, type);
        return (String) render.invoke(objRenderer, value);
    }

}
