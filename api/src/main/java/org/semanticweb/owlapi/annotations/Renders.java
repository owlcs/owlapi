package org.semanticweb.owlapi.annotations;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import org.semanticweb.owlapi.model.OWLDocumentFormat;

/**
 * Annotates classes that can render a format. Primary use is annotation of OWLObjectRenderers for
 * use by ToStringRenderer, for the convenience OWLObject::toFooSyntax() methods
 */
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Renders {
    /** @return the format class that this renderer applies to */
    Class<? extends OWLDocumentFormat> value();
}
