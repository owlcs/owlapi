package org.obolibrary.macro;

import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.List;

import org.semanticweb.owlapi.model.*;

/** Data visitor. */
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
        List<OWLDataOneOf> oneOfs = asList(node.values().map(lit -> df.getOWLDataOneOf(lit)));
        return df.getOWLDataUnionOf(oneOfs).accept(this);
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
