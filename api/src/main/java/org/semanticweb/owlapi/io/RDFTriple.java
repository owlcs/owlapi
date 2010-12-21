package org.semanticweb.owlapi.io;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;


/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 21/12/2010
 * @since 3.2
 */
public class RDFTriple {

    private RDFResource subject;

    private RDFResource predicate;

    private RDFNode object;

    public RDFTriple(RDFResource subject, RDFResource predicate, RDFNode object) {
        this.subject = subject;
        this.predicate = predicate;
        this.object = object;
    }

    public RDFTriple(IRI subject, boolean subjectAnon, IRI predicate, boolean predicateAnon, IRI object, boolean objectAnon) {
        this.subject = new RDFResource(subject, subjectAnon);
        this.predicate = new RDFResource(predicate, predicateAnon);
        this.object = new RDFResource(object, objectAnon);
    }

    public RDFTriple(IRI subject, boolean subjectAnon, IRI predicate, boolean predicateAnon, OWLLiteral object) {
        this.subject = new RDFResource(subject, subjectAnon);
        this.predicate = new RDFResource(predicate, predicateAnon);
        this.object = new RDFLiteral(object);
    }

    public RDFResource getSubject() {
        return subject;
    }

    public RDFResource getPredicate() {
        return predicate;
    }

    public RDFNode getObject() {
        return object;
    }


    @Override
    public int hashCode() {
        return subject.hashCode() * 37 + predicate.hashCode() * 17 + object.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o == this) {
            return true;
        }

        if(!(o instanceof RDFTriple)) {
            return false;
        }
        RDFTriple other = (RDFTriple) o;
        return subject.equals(other.subject) && predicate.equals(other.predicate) && object.equals(other.object);
    }




}
