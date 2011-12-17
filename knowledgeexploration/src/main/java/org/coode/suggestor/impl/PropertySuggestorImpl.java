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
package org.coode.suggestor.impl;

import org.coode.suggestor.api.PropertySanctionRule;
import org.coode.suggestor.api.PropertySuggestor;
import org.coode.suggestor.util.ReasonerHelper;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.reasoner.Node;
import org.semanticweb.owlapi.reasoner.NodeSet;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.impl.OWLDataPropertyNodeSet;
import org.semanticweb.owlapi.reasoner.impl.OWLObjectPropertyNodeSet;

import java.util.HashSet;
import java.util.Set;

/**
 * Default implementation of the PropertySuggestor.
 */
class PropertySuggestorImpl implements PropertySuggestor {

    private final OWLReasoner r;
    private final OWLDataFactory df;
    private final ReasonerHelper helper;

    private final Set<PropertySanctionRule> sanctionRules = new HashSet<PropertySanctionRule>();


    private final Matcher<OWLObjectPropertyExpression> currentOPMatcher = new AbstractOPMatcher(){
        public boolean isMatch(OWLClassExpression c, OWLObjectPropertyExpression p) {
            if (p.isTopEntity()){
                return true;
            }

            return helper.isDescendantOf(c, df.getOWLObjectSomeValuesFrom(p, df.getOWLThing()));
        }
    };

    private final Matcher<OWLDataProperty> currentDPMatcher = new AbstractDPMatcher(){
        public boolean isMatch(OWLClassExpression c, OWLDataProperty p) {
            if (p.isTopEntity()){
                return true;
            }

            return helper.isDescendantOf(c, df.getOWLDataSomeValuesFrom(p, df.getTopDatatype()));
        }
    };

    private Matcher<OWLObjectPropertyExpression> possibleOPMatcher = new AbstractOPMatcher(){
        public boolean isMatch(OWLClassExpression c, OWLObjectPropertyExpression p) {
            // Hermit and JFact are very slightly faster using the entailment check
//            return r.isSatisfiable(df.getOWLObjectIntersectionOf(c, df.getOWLObjectSomeValuesFrom(p, df.getOWLThing())));
        return !r.isEntailed(df.getOWLSubClassOfAxiom(c, df.getOWLObjectAllValuesFrom(p, df.getOWLNothing())));
        }
    };

    private Matcher<OWLDataProperty> possibleDPMatcher = new AbstractDPMatcher(){
        public boolean isMatch(OWLClassExpression c, OWLDataProperty p) {
            return r.isSatisfiable(df.getOWLObjectIntersectionOf(c, df.getOWLDataSomeValuesFrom(p, df.getTopDatatype())));
        }
    };



    public PropertySuggestorImpl(OWLReasoner r) {
        this.r = r;
        this.df = r.getRootOntology().getOWLOntologyManager().getOWLDataFactory();
        this.helper = new ReasonerHelper(r);
    }

    public void addSanctionRule(PropertySanctionRule rule) {
        sanctionRules.add(rule);
        rule.setSuggestor(this);
    }

    public void removeSanctionRule(PropertySanctionRule rule) {
        sanctionRules.remove(rule);
        rule.setSuggestor(null);
    }

    public OWLReasoner getReasoner() {
        return r;
    }

    // BOOLEAN TESTS

    public boolean isCurrent(OWLClassExpression c, OWLObjectPropertyExpression p) {
        return currentOPMatcher.isMatch(c, p);
    }

    public boolean isCurrent(OWLClassExpression c, OWLObjectPropertyExpression p, boolean direct) {
        return currentOPMatcher.isMatch(c, p, direct);
    }

    public boolean isCurrent(OWLClassExpression c, OWLDataProperty p) {
        return currentDPMatcher.isMatch(c, p);
    }

    public boolean isCurrent(OWLClassExpression c, OWLDataProperty p, boolean direct) {
        return currentDPMatcher.isMatch(c, p, direct);
    }

    public boolean isPossible(OWLClassExpression c, OWLObjectPropertyExpression p) {
        return possibleOPMatcher.isMatch(c, p);
    }

    public boolean isPossible(OWLClassExpression c, OWLDataProperty p) {
        return possibleDPMatcher.isMatch(c, p);
    }

    public boolean isSanctioned(OWLClassExpression c, OWLObjectPropertyExpression p) {
        return isPossible(c, p) && meetsOPSanctions(c, p);
    }

    public boolean isSanctioned(OWLClassExpression c, OWLDataProperty p) {
        return isPossible(c, p) && meetsDPSanctions(c, p);
    }


    // GETTERS

    public NodeSet<OWLObjectPropertyExpression> getCurrentObjectProperties(OWLClassExpression c, boolean direct) {
        return currentOPMatcher.getLeaves(c, r.getTopObjectPropertyNode(), direct);
    }

    public NodeSet<OWLDataProperty> getCurrentDataProperties(OWLClassExpression c, boolean direct) {
        return currentDPMatcher.getLeaves(c, r.getTopDataPropertyNode(), direct);
    }

    public NodeSet<OWLObjectPropertyExpression> getPossibleObjectProperties(OWLClassExpression c, OWLObjectPropertyExpression root, boolean direct) {
        Node<OWLObjectPropertyExpression> rootNode = (root == null) ? r.getTopObjectPropertyNode() : r.getEquivalentObjectProperties(root);
        return possibleOPMatcher.getRoots(c, rootNode, direct);
    }

    public NodeSet<OWLDataProperty> getPossibleDataProperties(OWLClassExpression c, OWLDataProperty root, boolean direct) {
        Node<OWLDataProperty> rootNode = (root == null) ? r.getTopDataPropertyNode() : r.getEquivalentDataProperties(root);
        return possibleDPMatcher.getRoots(c, rootNode, direct);
    }

    public Set<OWLObjectPropertyExpression> getSanctionedObjectProperties(OWLClassExpression c, OWLObjectPropertyExpression root, boolean direct) {
        Set<OWLObjectPropertyExpression> props = new HashSet<OWLObjectPropertyExpression>();
        for (OWLObjectPropertyExpression pNode : getPossibleObjectProperties(c, root, direct).getFlattened()) {
            if (meetsOPSanctions(c, pNode)) {
                props.add(pNode);
            }
        }
        return props;
    }

    public Set<OWLDataProperty> getSanctionedDataProperties(OWLClassExpression c, OWLDataProperty root, boolean direct) {
        Set<OWLDataProperty> props = new HashSet<OWLDataProperty>();
        for (OWLDataProperty pNode : getPossibleDataProperties(c, root, direct).getFlattened()) {
            if (meetsDPSanctions(c, pNode)) {
                props.add(pNode);
            }
        }
        return props;
    }



    // INTERNALS


    private boolean meetsOPSanctions(OWLClassExpression c, OWLObjectPropertyExpression p) {
        for (PropertySanctionRule rule : sanctionRules){
            if (rule.meetsSanction(c, p)){
                return true;
            }
        }
        return false;
    }

    private boolean meetsDPSanctions(OWLClassExpression c, OWLDataProperty p) {
        for (PropertySanctionRule rule : sanctionRules){
            if (rule.meetsSanction(c, p)){
                return true;
            }
        }
        return false;
    }



    // DELEGATES


    private interface Matcher<P extends OWLPropertyExpression>{

        boolean isMatch(OWLClassExpression c, P p);

        boolean isMatch(OWLClassExpression c, P p, boolean direct);

        /*
         * Perform a recursive search, adding nodes that match and if direct is true only if they have no subs that match
         */
        NodeSet<P> getLeaves(OWLClassExpression c, Node<P> root, boolean direct);

        /*
         * Perform a search on the direct subs of start, adding nodes that match. If not direct then recurse
         */
        NodeSet<P> getRoots(OWLClassExpression c, Node<P> start, boolean direct);
    }

    private abstract class AbstractMatcher<P extends OWLPropertyExpression> implements Matcher<P> {

        public final boolean isMatch(OWLClassExpression c, P p, boolean direct) {
            if (!direct){
                return isMatch(c, p);
            }

            if (!isMatch(c, p)) {
                return false;
            }

            for (Node<P> node : getDirectSubs(p)){ // check the direct subproperties
                if (isMatch(c, node.getRepresentativeElement())){
                    return false;
                }
            }
            return true;
        }

        public final NodeSet<P> getLeaves(OWLClassExpression c, Node<P> root, boolean direct) {

            Set<Node<P>> nodes = new HashSet<Node<P>>();

            final P p = root.getRepresentativeElement();

            if (isMatch(c, p)) {
                for (Node<P> sub : getDirectSubs(p)){
                    nodes.addAll(getLeaves(c, sub, direct).getNodes());
                }
                if (!direct || (nodes.isEmpty() && !root.isTopNode())){
                    nodes.add(root);
                }
            }
            return createNodeSet(nodes);
        }

        public final NodeSet<P> getRoots(OWLClassExpression c, Node<P> root, boolean direct) {

            Set<Node<P>> nodes = new HashSet<Node<P>>();

                for (Node<P> sub : getDirectSubs(root.getRepresentativeElement())){
                    if (isMatch(c, sub.getRepresentativeElement())){
                        nodes.add(sub);
                        if (!direct){
                            nodes.addAll(getRoots(c, sub, direct).getNodes());
                        }
                    }
                }
            return createNodeSet(nodes);
        }

        protected abstract NodeSet<P> getDirectSubs(P p);

        protected abstract NodeSet<P> createNodeSet(Set<Node<P>> nodes);
    }

    private abstract class AbstractOPMatcher extends AbstractMatcher<OWLObjectPropertyExpression> {

        @Override
        protected final NodeSet<OWLObjectPropertyExpression> getDirectSubs(OWLObjectPropertyExpression p) {
            return r.getSubObjectProperties(p, true);
        }

        @Override
        protected final NodeSet<OWLObjectPropertyExpression> createNodeSet(Set<Node<OWLObjectPropertyExpression>> nodes) {
            return new OWLObjectPropertyNodeSet(nodes);
        }
    }

    private abstract class AbstractDPMatcher extends AbstractMatcher<OWLDataProperty> {

        @Override
        protected final NodeSet<OWLDataProperty> getDirectSubs(OWLDataProperty p) {
            return r.getSubDataProperties(p, true);
        }

        @Override
        protected NodeSet<OWLDataProperty> createNodeSet(Set<Node<OWLDataProperty>> nodes) {
            return new OWLDataPropertyNodeSet(nodes);
        }
    }

}