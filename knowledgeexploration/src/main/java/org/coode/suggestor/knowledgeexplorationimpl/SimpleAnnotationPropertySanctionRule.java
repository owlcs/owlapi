/*
 * Date: Dec 17, 2007
 *
 * code made available under Mozilla Public License (http://www.mozilla.org/MPL/MPL-1.1.html)
 *
 * copyright 2007, The University of Manchester
 *
 * Author: Nick Drummond
 * http://www.cs.man.ac.uk/~drummond/
 * Bio Health Informatics Group
 * The University Of Manchester
 */
package org.coode.suggestor.knowledgeexplorationimpl;

import org.coode.suggestor.api.PropertySanctionRule;
import org.coode.suggestor.api.PropertySuggestor;
import org.coode.suggestor.util.ReasonerHelper;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.OWLReasoner;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Set;

/**
 * Checks the class to see if it has an annotation (specified by the constructor) matching the URI of the property.
 * If recursive is true, then all ancestors of the class are also checked.
 */
public class SimpleAnnotationPropertySanctionRule implements PropertySanctionRule {

    private OWLReasoner r;

    private final OWLAnnotationProperty annotationProperty;

    private final boolean recursive;

    public SimpleAnnotationPropertySanctionRule(OWLAnnotationProperty annotationProperty, boolean recursive){
        this.annotationProperty = annotationProperty;
        this.recursive = recursive;
    }

    public void setSuggestor(PropertySuggestor ps) {
        r = ps.getReasoner();
    }

    public boolean meetsSanction(OWLClassExpression c, OWLObjectPropertyExpression p) {
        return hasAnnotation(c, p);
    }

    public boolean meetsSanction(OWLClassExpression c, OWLDataProperty p) {
        return hasAnnotation(c, p);
    }

    private boolean hasAnnotation(OWLClassExpression c, OWLPropertyExpression p) {
        if (!p.isAnonymous()){

            if (!c.isAnonymous() && hasSanctionAnnotation(c.asOWLClass(), (OWLProperty)p)){
                return true;
            }

            if (recursive){
                // check the ancestors
                for (OWLClass superCls : r.getSuperClasses(c, true).getFlattened()){
                    if (hasAnnotation(superCls, p)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    private boolean hasSanctionAnnotation(OWLClass c, OWLProperty p) {

        IRIMatcher iriMatcher = new IRIMatcher(p.getIRI());

        for (OWLOntology ont : r.getRootOntology().getImportsClosure()){
            for (OWLAnnotation annot : c.getAnnotations(ont, annotationProperty)){
                if (annot.getValue().accept(iriMatcher)){
                    return true;
                }
            }
        }
        return false;
    }

    class IRIMatcher implements OWLAnnotationValueVisitorEx<Boolean> {

        private final IRI propertyIRI;

        IRIMatcher(IRI propertyIRI) {
            this.propertyIRI = propertyIRI;
        }

        public Boolean visit(IRI iri) {
            return iri.equals(propertyIRI);
        }

        public Boolean visit(OWLAnonymousIndividual owlAnonymousIndividual) {
            return false;
        }

        public Boolean visit(OWLLiteral owlLiteral) {
            try {
                IRI vIRI = IRI.create(new URI(owlLiteral.getLiteral()));
                if (vIRI.equals(propertyIRI)){
                    return true;
                }
            }
            catch (URISyntaxException e) {
                // do nothing - not a URI
            }
            return false;
        }
    }
}
