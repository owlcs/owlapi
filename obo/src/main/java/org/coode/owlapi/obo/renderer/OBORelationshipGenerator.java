package org.coode.owlapi.obo.renderer;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectCardinalityRestriction;
import org.semanticweb.owlapi.model.OWLObjectExactCardinality;
import org.semanticweb.owlapi.model.OWLObjectMaxCardinality;
import org.semanticweb.owlapi.model.OWLObjectMinCardinality;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLQuantifiedObjectRestriction;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;

/**
 * Author: Nick Drummond<br>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Dec 18, 2008<br><br>
 */
public class OBORelationshipGenerator extends OWLClassExpressionVisitorAdapter {

    private Set<OBORelationship> relationships = new HashSet<OBORelationship>();

    private OBOExceptionHandler eHandler;

    private OWLClass cls;


    public OBORelationshipGenerator(OBOExceptionHandler eHandler) {
        this.eHandler = eHandler;
    }


    public void setClass(OWLClass cls) {
        this.cls = cls;
        clear();
    }


    public void clear() {
        relationships.clear();
    }


    public Set<OBORelationship> getOBORelationships() {
        return new HashSet<OBORelationship>(relationships);
    }


    @Override
	public void visit(OWLObjectSomeValuesFrom desc) {
        getRelationship(desc);
    }


    @Override
	public void visit(OWLObjectMinCardinality desc) {
        OBORelationship rel = getRelationship(desc);
        if (rel != null) {
            rel.setMinCardinality(desc.getCardinality());
        }
    }


    @Override
	public void visit(OWLObjectExactCardinality desc) {
        OBORelationship rel = getRelationship(desc);
        if (rel != null) {
            rel.setCardinality(desc.getCardinality());
        }
    }


    @Override
	public void visit(OWLObjectMaxCardinality desc) {
        OBORelationship rel = getRelationship(desc);
        if (rel != null) {
            rel.setMaxCardinality(desc.getCardinality());
        }
    }

    // TODO error handling for un-translatable class expressions

    private OBORelationship getRelationship(OWLObjectCardinalityRestriction desc) {
        if (desc.isAnonymous() && !desc.getFiller().isAnonymous()) {
            final OWLObjectProperty p = desc.getProperty().asOWLObjectProperty();
            final OWLClass f = desc.getFiller().asOWLClass();

            for (OBORelationship rel : relationships) {
                if (rel.getProperty().equals(p) && rel.getFiller().equals(f)) {
                    return rel;
                }
            }
            final OBORelationship newRel = new OBORelationship(p, f);
            relationships.add(newRel);
            return newRel;
        }

        eHandler.addException(new OBOStorageException(cls, desc, "Anonymous filler of some restriction cannot be converted to OBO"));
        return null;
    }

    private OBORelationship getRelationship(OWLQuantifiedObjectRestriction desc) {
        if (desc.isAnonymous() && !desc.getFiller().isAnonymous()) {
            final OWLObjectProperty p = desc.getProperty().asOWLObjectProperty();
            final OWLClass f = desc.getFiller().asOWLClass();

            for (OBORelationship rel : relationships) {
                if (rel.getProperty().equals(p) && rel.getFiller().equals(f)) {
                    return rel;
                }
            }
            final OBORelationship newRel = new OBORelationship(p, f);
            relationships.add(newRel);
            return newRel;
        }

        eHandler.addException(new OBOStorageException(cls, desc, "Anonymous filler of some restriction cannot be converted to OBO"));
        return null;
    }
}
