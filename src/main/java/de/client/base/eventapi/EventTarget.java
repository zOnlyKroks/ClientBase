package de.client.base.eventapi;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * An indicator to treat a method as event handler
 */
@Documented @Target(ElementType.METHOD) @Retention(RetentionPolicy.RUNTIME) public @interface EventTarget {
}
