package uk.ac.manchester.cs.owl.owlapi.concurrent;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Matthew Horridge
 * Stanford Center for Biomedical Informatics Research
 * 10/04/15
 */
@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
public @interface NonConcurrentDelegate {}
