package org.semanticweb.owlapi.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLEntityVisitorEx;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;

/**
 * Convenience to create c1, c2, p1, p2... entities in replacement of input entities.
 */
public class OWLEntityFragmentProvider implements OWLEntityVisitorEx<String> {

    private static final AtomicInteger classCount = new AtomicInteger();
    private static final AtomicInteger objectPropertyCount = new AtomicInteger();
    private static final AtomicInteger dataPropertyCount = new AtomicInteger();
    private static final AtomicInteger individualCount = new AtomicInteger();
    private static final AtomicInteger annotationPropertyCount = new AtomicInteger();
    private static final AtomicInteger datatypeCount = new AtomicInteger();

    /**
     * @param entity entity to rename
     * @return new name
     */
    public String getName(OWLEntity entity) {
        if (entity.isBuiltIn()) {
            return entity.getIRI().toString();
        }
        return entity.accept(this);
    }

    @Override
    public String visit(OWLClass cls) {
        return "c" + classCount.incrementAndGet();
    }

    @Override
    public String visit(OWLDatatype datatype) {
        return "dt" + datatypeCount.incrementAndGet();
    }

    @Override
    public String visit(OWLNamedIndividual individual) {
        return "i" + individualCount.incrementAndGet();
    }

    @Override
    public String visit(OWLDataProperty property) {
        return "dp" + dataPropertyCount.incrementAndGet();
    }

    @Override
    public String visit(OWLObjectProperty property) {
        return "op" + objectPropertyCount.incrementAndGet();
    }

    @Override
    public String visit(OWLAnnotationProperty property) {
        return "ap" + annotationPropertyCount.incrementAndGet();
    }
}
