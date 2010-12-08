package org.semanticweb.owlapi.api.test;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataIntersectionOf;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDataUnionOf;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-Jun-2009
 */
public class DataUnionOfTestCase2 extends AbstractAxiomsRoundTrippingTestCase {

    protected Set<? extends OWLAxiom> createAxioms() {
        OWLDataFactory factory = getFactory();
        OWLDatatype dt = factory.getOWLDatatype(IRI.create("file:/c/test.owlapi#SSN"));
        OWLFacetRestriction fr = factory.getOWLFacetRestriction(OWLFacet.PATTERN, factory.getOWLLiteral("[0-9]{3}-[0-9]{2}-[0-9]{4}"));
        OWLDataRange dr = factory.getOWLDatatypeRestriction(factory.getOWLDatatype(IRI.create("http://www.w3.org/2001/XMLSchema#string")), fr);
        OWLDataIntersectionOf disj1 = factory.getOWLDataIntersectionOf(factory.getOWLDataComplementOf(dr), dt); // here I negate dr
        OWLDataIntersectionOf disj2 = factory.getOWLDataIntersectionOf(factory.getOWLDataComplementOf(dt), dr); // here I negate dt
        OWLDataUnionOf union = factory.getOWLDataUnionOf(disj1, disj2);
        System.out.println(union.toString());
        OWLDataProperty prop = getOWLDataProperty("prop");
        OWLDataPropertyRangeAxiom ax = factory.getOWLDataPropertyRangeAxiom(prop, union);
        return Collections.singleton(ax);
    }
}
