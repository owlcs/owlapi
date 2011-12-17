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
package org.coode.suggestor.util;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.impl.OWLDatatypeNode;
import org.semanticweb.owlapi.reasoner.impl.OWLDatatypeNodeSet;

import java.util.*;

/**
 * Utility methods for common reasoner tasks.
 */
public class ReasonerHelper {

    private final OWLReasoner r;

    private final OWLDataFactory df;

    public ReasonerHelper(OWLReasoner r) {
        if (r == null){
            throw new IllegalArgumentException("Reasoner cannot be null");
        }
        this.r = r;
        this.df = r.getRootOntology().getOWLOntologyManager().getOWLDataFactory();
    }

    public Set<OWLObjectPropertyExpression> getReferencedObjectProperties() {
        final OWLOntology root = r.getRootOntology();
        Set<OWLObjectPropertyExpression> p = new HashSet<OWLObjectPropertyExpression>(root.getObjectPropertiesInSignature(true));
        p.add(df.getOWLTopObjectProperty());
        p.add(df.getOWLBottomObjectProperty());
        return p;
    }

    public Set<OWLDataProperty> getReferencedDataProperties() {
        final OWLOntology root = r.getRootOntology();
        Set<OWLDataProperty> p = new HashSet<OWLDataProperty>(root.getDataPropertiesInSignature(true));
        p.add(df.getOWLTopDataProperty());
        p.add(df.getOWLBottomDataProperty());
        return p;
    }

    public boolean isDescendantOf(OWLClassExpression cls1, OWLClassExpression cls2) {
        return isAncestorOf(cls2, cls1);
    }

    public boolean isAncestorOf(OWLClassExpression cls1, OWLClassExpression cls2) {

        if (!cls1.isAnonymous()){
            return r.getSuperClasses(cls2, false).containsEntity(cls1.asOWLClass());
        }

        return r.isEntailed(df.getOWLSubClassOfAxiom(cls2, cls1));
    }

    public Set<OWLClassExpression> filterClassExpressions(Set<OWLClassExpression> clses) {
        Set<OWLClassExpression> nonRedundantSet = new HashSet<OWLClassExpression>();
        List<OWLClassExpression> clsList = new ArrayList<OWLClassExpression>(clses);

        for (int i=0; i<clsList.size(); i++){
            final OWLClassExpression head = clsList.get(i);

            if (!containsSubclass(clsList.subList(i+1, clsList.size()), head) &&
                !containsSubclass(nonRedundantSet, head)){
                nonRedundantSet.add(head);
            }
        }

        return nonRedundantSet;
    }

    public boolean containsSubclass(Collection<OWLClassExpression> potentialSubs, OWLClassExpression cls) {
        for (OWLClassExpression potentialSub : potentialSubs){
            if (isDescendantOf(potentialSub, cls)){
                return true;
            }
        }
        return false;
    }

    public boolean isLocallyFunctional(OWLClassExpression c, OWLObjectPropertyExpression p) {
        return isDescendantOf(c, df.getOWLObjectMaxCardinality(1, p));
    }

    public boolean isLocallyFunctional(OWLClassExpression c, OWLDataProperty p) {
        return isDescendantOf(c, df.getOWLDataMaxCardinality(1, p));
    }

    /**
     * <p>Check the ontologies for range assertions on p and all ancestors of p.</p>
     *
     * @param p the object property for which a range is wanted
     * @return an intersection of the non-redundant ranges or Thing if no range assertions have been made
     */
    public OWLClassExpression getGlobalAssertedRange(OWLObjectPropertyExpression p) {

        OWLClassExpression range = df.getOWLThing();

        Set<OWLClassExpression> assertedRanges = new HashSet<OWLClassExpression>();
        Set<OWLObjectPropertyExpression> ancestors = new HashSet<OWLObjectPropertyExpression>(r.getSuperObjectProperties(p, false).getFlattened());
        ancestors.add(p);

        for (OWLOntology ont : r.getRootOntology().getImportsClosure()){
            for (OWLObjectPropertyExpression ancestor : ancestors){
                assertedRanges.addAll(ancestor.getRanges(ont));
            }
        }

        if (!assertedRanges.isEmpty()) {

            // filter to remove redundant ranges (supers of others) as getRanges() just returns all of them
            assertedRanges = filterClassExpressions(assertedRanges);

            if (assertedRanges.size() == 1) {
                range = assertedRanges.iterator().next();
            }
            else {
                range = df.getOWLObjectIntersectionOf(assertedRanges);
            }
        }

        return range;
    }

    /**
     * <p>Find the asserted ranges on this property and all of its ancestors.</p>
     * <p>If multiple ranges are found, they are pulled together into an intersection.</p>
     *
     * @param p the property we are querying
     * @return the range of this property or Top if none is found
     */
    public OWLDataRange getGlobalAssertedRange(OWLDataProperty p) {

        OWLDataRange range = df.getTopDatatype();

        Set<OWLDataRange> assertedRanges = new HashSet<OWLDataRange>();
        Set<OWLDataProperty> ancestors = new HashSet<OWLDataProperty>(r.getSuperDataProperties(p, false).getFlattened());
        ancestors.add(p);

        for (OWLOntology ont : r.getRootOntology().getImportsClosure()){
            for (OWLDataProperty ancestor : ancestors){
                assertedRanges.addAll(ancestor.getRanges(ont));
            }
        }

        if (!assertedRanges.isEmpty()) {
//
//            // filter to remove redundant ranges (supers of others) as getRanges() just returns all of them
//            assertedRanges = filterClassExpressions(assertedRanges);

            if (assertedRanges.size() == 1) {
                range = assertedRanges.iterator().next();
            }
            else {
                range = df.getOWLDataIntersectionOf(assertedRanges);
            }
        }

        return range;
    }

    /**
     * Subsumption  checking between dataranges/types.
     * This will only work if there is a suitable data property in the ontology.
     * This must satisfy the criteria in {@link #getCandidatePropForRangeSubsumptionCheck)}.
     *
     * @param subRange
     * @param superRange
     * @return true if subRange is subsumed by superRange
     * @throws RuntimeException if no suitable property can be found
     */
    public boolean isSubtype(OWLDataRange subRange, OWLDataRange superRange){
        OWLDataPropertyExpression p = getCandidatePropForRangeSubsumptionCheck(superRange);
        if (p == null){
            throw new RuntimeException("Cannot find a candidate property for datatype subsumption checking");
        }
        return r.isEntailed(df.getOWLSubClassOfAxiom(df.getOWLDataSomeValuesFrom(p, subRange),
                                                     df.getOWLDataSomeValuesFrom(p, superRange)));
    }

    /**
     * Subsumption between dataranges/types.
     * This will only work if there is a suitable data property in the ontology.
     * This must satisfy the criteria in {@link #getCandidatePropForRangeSubsumptionCheck)}.
     *
     * @param range The data range for which we will retrieve subtypes
     * @return a NodeSet containing named datatypes that are known to be subtypes of range
     * @throws RuntimeException if no suitable property can be found
     */
    public NodeSet<OWLDatatype> getSubtypes(OWLDataRange range) {

        OWLDataPropertyExpression p = getCandidatePropForRangeSubsumptionCheck(range);
        if (p == null){
            throw new RuntimeException("Cannot find a candidate property for datatype subsumption checking");
        }

        Set<Node<OWLDatatype>> subs = new HashSet<Node<OWLDatatype>>();

        OWLDataSomeValuesFrom pSomeRange = df.getOWLDataSomeValuesFrom(p, range);

        for (OWLDatatype dt : getDatatypesInSignature()){
            if (!dt.equals(range)){
                final OWLDataSomeValuesFrom pSomeDatatype = df.getOWLDataSomeValuesFrom(p, dt);

                if (!r.isSatisfiable(pSomeDatatype)){
                    // TODO can we protect against this?
                    System.err.println("Warning: unsatisfiable concept in subtype checking: " + pSomeDatatype);
                }
                else if (r.isEntailed(df.getOWLSubClassOfAxiom(pSomeDatatype, pSomeRange))){
                    subs.add(new OWLDatatypeNode(dt));
                }
            }
        }
        return new OWLDatatypeNodeSet(subs);
    }

    /**
     * Equivalence between dataranges/types.
     * This will only work if there is a suitable data property in the ontology.
     * This must satisfy the criteria in {@link #getCandidatePropForRangeSubsumptionCheck)}.
     *
     * @param range The data range for which we will retrieve equivalents
     * @return a NodeSet containing named datatypes that are known to be equivalent to range
     * @throws RuntimeException if no suitable property can be found
     */
    public Node<OWLDatatype> getEquivalentTypes(OWLDataRange range) {

        OWLDataPropertyExpression p = getCandidatePropForRangeSubsumptionCheck(range);
        if (p == null){
            throw new RuntimeException("Cannot find a candidate property for datatype subsumption checking");
        }

        Set<OWLDatatype> subs = new HashSet<OWLDatatype>();
                if (range.isDatatype()){
                    subs.add(range.asOWLDatatype());
                }

        OWLDataSomeValuesFrom pSomeRange = df.getOWLDataSomeValuesFrom(p, range);

        for (OWLDatatype dt : getDatatypesInSignature()){
            if (!dt.equals(range)){
                final OWLDataSomeValuesFrom pSomeDatatype = df.getOWLDataSomeValuesFrom(p, dt);

                if (!r.isSatisfiable(pSomeDatatype)){
                    // TODO can we protect against this?
                    System.err.println("Warning: unsatisfiable concept in equiv type checking: " + pSomeDatatype);
                }
                else if (r.isEntailed(df.getOWLEquivalentClassesAxiom(pSomeDatatype, pSomeRange))){
                    subs.add(dt);
                }
            }
        }
        return new OWLDatatypeNode(subs);
    }


    /**
     * <p>Find a candidate property for datatype subsumption checking.</p>
     * <p>If {O} is the set of ontologies loaded into the reasoner,
     * a candidate is ANY data property (APART FROM Top) in the signature of {O} that satifies the criteria
     * isSatisfiable(SomeValuesFrom(p, range).</p>
     *
     * @param range the data range from which will be used in the above test
     * @return a candidate property that fulfils the above criteria or null if none can be found
     */
    public OWLDataPropertyExpression getCandidatePropForRangeSubsumptionCheck(OWLDataRange range) {
        for (OWLOntology ont : r.getRootOntology().getImportsClosure()){
            for (OWLDataProperty p : ont.getDataPropertiesInSignature()){
                if (!p.isTopEntity() && r.isSatisfiable(df.getOWLDataSomeValuesFrom(p, range))){
                    return p;
                }
            }
        }
        return null;
    }

    public Set<OWLDatatype> getDatatypesInSignature() {
        Set<OWLDatatype> dts = new HashSet<OWLDatatype>();
        for (OWLOntology ont : r.getRootOntology().getImportsClosure()){
            dts.addAll(ont.getDatatypesInSignature());
        }
        return dts;
    }

    public boolean isInAssertedRange(OWLObjectPropertyExpression p, OWLClassExpression f) {
        return isDescendantOf(f, getGlobalAssertedRange(p));
    }

    public boolean isInAssertedRange(OWLDataProperty p, OWLDataRange f) {
        return isSubtype(f, getGlobalAssertedRange(p));
    }

//    /**
//     * A recursive method for finding all current object properties checking a root property and its descendants for efficiency.
//     * @param descr The class expression we wish to test
//     * @param direct If true then only the most specific properties are returned (and not their ancestors)
//     * @param p The root property we wish to check (start from Top if all properties are required)
//     * @return A set of properties where for each property p, desc -> p some Thing holds.
//     * If direct is true then the additional constraint is that there is no pair (p, p') in the set such that p -> p'
//     */
//    private Set<OWLObjectPropertyExpression> getCurrentObjectProperties(OWLClassExpression descr, boolean direct, OWLObjectPropertyExpression p) {
//        Set<OWLObjectPropertyExpression> props = new HashSet<OWLObjectPropertyExpression>();
//        if (ReasonerHelper.isDescendantOf(descr, getTopLevelMustHaveRestriction(p), r)) {
//            for (Node<OWLObjectPropertyExpression> node : r.getSubObjectProperties(p, true)){
//                OWLObjectPropertyExpression sub = node.getRepresentativeElement();
//                props.addAll(getCurrentObjectProperties(descr, direct, sub));
//            }
//            if (!direct || props.isEmpty()){
//                props.add(p);
//            }
//        }
//        return props;
//    }
//
//    /**
//     * A recursive method for finding all current data properties checking a root property and its descendants for efficiency.
//     * @param descr The class expression we wish to test
//     * @param direct If true then only the most specific properties are returned (and not their ancestors)
//     * @param p The root property we wish to check (start from Top if all properties are required)
//     * @return A set of properties where for each property p, desc -> p min 1 holds.
//     * If direct is true then the additional constraint is that there is no pair (p, p') in the set such that p -> p'
//     */
//    private Set<OWLDataProperty> getCurrentDataProperties(OWLClassExpression descr, boolean direct, OWLDataProperty p) {
//        Set<OWLDataProperty> props = new HashSet<OWLDataProperty>();
//        if (p.equals(df.getOWLTopDataProperty()) || isDescendantOf(descr, getTopLevelMustHaveRestriction(p))) {
//            for (Node<OWLDataProperty> node : r.getSubDataProperties(p, true)){
//                OWLDataProperty sub = node.getRepresentativeElement();
//                props.addAll(getCurrentDataProperties(descr, direct, sub));
//            }
//            if (!direct || props.isEmpty()){
//                props.add(p);
//            }
//        }
//        return props;
//    }
//
//    private Set<OWLObjectPropertyExpression> getImpossibleObjectProperties(OWLClassExpression descr, OWLObjectPropertyExpression p){
//        Set<OWLObjectPropertyExpression> props = new HashSet<OWLObjectPropertyExpression>();
//
//        if (isImpossible(p, descr)) {
//            props.add(p);
//            props.addAll(r.getSubObjectProperties(p, false).getFlattened());
//        }
//        else{
//            for (Node<OWLObjectPropertyExpression> node : r.getSubObjectProperties(p, true)){
//                OWLObjectPropertyExpression sub = node.getRepresentativeElement();
//                props.addAll(getImpossibleObjectProperties(descr, sub));
//            }
//        }
//        return props;
//    }
}
