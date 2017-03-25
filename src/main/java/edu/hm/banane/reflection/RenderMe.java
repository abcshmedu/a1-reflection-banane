package edu.hm.banane.reflection;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;;

@Retention(RetentionPolicy.RUNTIME) 
@Target({ElementType.TYPE_USE, ElementType.TYPE, ElementType.METHOD}) 
public @interface RenderMe {

	String with() default "";

}
