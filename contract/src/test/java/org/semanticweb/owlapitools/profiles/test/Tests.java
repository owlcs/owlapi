package org.semanticweb.owlapitools.profiles.test;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/** Tests annotation
 * 
 * @author ignazio */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Tests {
    /** method name */
    String method();
}
