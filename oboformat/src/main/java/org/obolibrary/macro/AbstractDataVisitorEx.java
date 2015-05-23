package org.obolibrary.macro;

import java.util.HashSet;
import java.util.Set;

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
        Set<OWLDataOneOf> oneOfs = new HashSet<>();
        node.values().forEach(lit -> oneOfs.add(df.getOWLDataOneOf(lit)));
        return df.getOWLDataUnionOf(oneOfs).accept(this);
    }

    @Override
    public OWLDataRange visit(OWLDataIntersectionOf node) {
        Set<OWLDataRange> ops = new HashSet<>();
        node.operands().forEach(op -> ops.add(op.accept(this)));
        return df.getOWLDataIntersectionOf(ops);
    }

    @Override
    public OWLDataRange visit(OWLDataUnionOf node) {
        Set<OWLDataRange> ops = new HashSet<>();
        node.operands().forEach(op -> ops.add(op.accept(this)));
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
