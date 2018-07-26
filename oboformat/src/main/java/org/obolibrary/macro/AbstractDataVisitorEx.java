package org.obolibrary.macro;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.List;

import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;

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
        return df.getOWLDataUnionOf(node.values().map(df::getOWLDataOneOf)).accept(this);
    }

    @Override
    public OWLDataRange visit(OWLDataIntersectionOf node) {
        List<OWLDataRange> ops = asList(node.operands().map(op -> op.accept(this)));
        return df.getOWLDataIntersectionOf(ops);
    }

    @Override
    public OWLDataRange visit(OWLDataUnionOf node) {
        List<OWLDataRange> ops = asList(node.operands().map(op -> op.accept(this)));
        return df.getOWLDataUnionOf(ops);
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
