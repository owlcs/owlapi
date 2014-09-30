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
import org.semanticweb.owlapi.model.OWLLiteral;

/** Data visitor. */
public class AbstractDataVisitorEx implements OWLDataVisitorEx<OWLDataRange> {

    final OWLDataFactory dataFactory;

    protected AbstractDataVisitorEx(@Nonnull OWLDataFactory df) {
        dataFactory = df;
    }

    @Override
    public OWLDataRange visit(OWLDatatypeRestriction node) {
        return node;
    }

    @Override
    public OWLDataRange visit(@Nonnull OWLDataOneOf node) {
        // Encode as a data union of and return result
        Set<OWLDataOneOf> oneOfs = new HashSet<>();
        for (OWLLiteral lit : node.getValues()) {
            oneOfs.add(dataFactory.getOWLDataOneOf(lit));
        }
        return dataFactory.getOWLDataUnionOf(oneOfs).accept(this);
    }

    @Override
    public OWLDataRange visit(@Nonnull OWLDataIntersectionOf node) {
        Set<OWLDataRange> ops = new HashSet<>();
        for (OWLDataRange op : node.getOperands()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLDataIntersectionOf(ops);
    }

    @Override
    public OWLDataRange visit(@Nonnull OWLDataUnionOf node) {
        Set<OWLDataRange> ops = new HashSet<>();
        for (OWLDataRange op : node.getOperands()) {
            ops.add(op.accept(this));
        }
        return dataFactory.getOWLDataUnionOf(ops);
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
