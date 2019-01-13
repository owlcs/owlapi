package org.semanticweb.owlapi6.obolibrary.macro;

import org.semanticweb.owlapi6.model.OWLDataComplementOf;
import org.semanticweb.owlapi6.model.OWLDataFactory;
import org.semanticweb.owlapi6.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi6.model.OWLDataOneOf;
import org.semanticweb.owlapi6.model.OWLDataRange;
import org.semanticweb.owlapi6.model.OWLDataUnionOf;
import org.semanticweb.owlapi6.model.OWLDataVisitorEx;
import org.semanticweb.owlapi6.model.OWLDatatype;
import org.semanticweb.owlapi6.model.OWLDatatypeRestriction;

/**
 * Data visitor.
 */
public class AbstractDataVisitorEx implements OWLDataVisitorEx<OWLDataRange> {

    final OWLDataFactory df;

    protected AbstractDataVisitorEx(OWLDataFactory df) {
        this.df = df;
    }

    @Override
    public OWLDataRange visit(OWLDatatypeRestriction node) {
        return node;
    }

    @Override
    public OWLDataRange visit(OWLDataOneOf node) {
        // Encode as a data union of and return result
        return df.getOWLDataUnionOf(node.operands().map(df::getOWLDataOneOf)).accept(this);
    }

    @Override
    public OWLDataRange visit(OWLDataIntersectionOf node) {
        return df.getOWLDataIntersectionOf(node.operands().map(op -> op.accept(this)));
    }

    @Override
    public OWLDataRange visit(OWLDataUnionOf node) {
        return df.getOWLDataUnionOf(node.operands().map(op -> op.accept(this)));
    }

    @Override
    public OWLDataRange visit(OWLDatatype node) {
        return node;
    }

    @Override
    public OWLDataRange visit(OWLDataComplementOf node) {
        return node;
    }
}
