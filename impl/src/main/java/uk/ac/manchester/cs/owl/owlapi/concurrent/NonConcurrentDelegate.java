package uk.ac.manchester.cs.owl.owlapi.concurrent;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import javax.inject.Qualifier;

/**
 * Matthew Horridge Stanford Center for Biomedical Informatics Research 10/04/15
 */
@Qualifier
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NonConcurrentDelegate {
}
