package org.obolibrary.macro;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.OWLDataComplementOf;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDataVisitorEx;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;

/** Data visitor. */
public class AbstractDataVisitorEx implements OWLDataVisitorEx<OWLDataRange> {

    final OWLDataFactory df;

    protected AbstractDataVisitorEx(@Nonnull OWLDataFactory df) {
        this.df = df;
    }

    @Override
    public OWLDataRange visit(OWLDatatypeRestriction node) {
        return node;
    }

    @Override
    public OWLDataRange visit(@Nonnull OWLDataOneOf node) {
        // Encode as a data union of and return result
        Set<OWLDataOneOf> oneOfs = new HashSet<>();
        node.values().forEach(lit -> oneOfs.add(df.getOWLDataOneOf(lit)));
        return df.getOWLDataUnionOf(oneOfs).accept(this);
    }

    @Override
    public OWLDataRange visit(@Nonnull OWLDataIntersectionOf node) {
        Set<OWLDataRange> ops = new HashSet<>();
        node.operands().forEach(op -> ops.add(op.accept(this)));
        return df.getOWLDataIntersectionOf(ops);
    }

    @Override
    public OWLDataRange visit(@Nonnull OWLDataUnionOf node) {
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
