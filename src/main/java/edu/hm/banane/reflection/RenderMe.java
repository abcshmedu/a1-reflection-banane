package edu.hm.banane.reflection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;;

/**
 * Annotation fuer die Klasse Renderer.
 */
@Retention(RetentionPolicy.RUNTIME) 
@Target({ElementType.FIELD, ElementType.METHOD}) 
public @interface RenderMe {

    /**
     * Legt eine spezielle Renderer Klasse fest.
     * @return Der vollstaendige Name der Klasse
     */
    String with() default "";

}
