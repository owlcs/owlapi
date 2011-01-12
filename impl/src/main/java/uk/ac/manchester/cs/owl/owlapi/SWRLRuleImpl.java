package uk.ac.manchester.cs.owl.owlapi;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLAxiomVisitor;
import org.semanticweb.owlapi.model.OWLAxiomVisitorEx;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectVisitor;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLBuiltInAtom;
import org.semanticweb.owlapi.model.SWRLClassAtom;
import org.semanticweb.owlapi.model.SWRLDataPropertyAtom;
import org.semanticweb.owlapi.model.SWRLDataRangeAtom;
import org.semanticweb.owlapi.model.SWRLDifferentIndividualsAtom;
import org.semanticweb.owlapi.model.SWRLIndividualArgument;
import org.semanticweb.owlapi.model.SWRLLiteralArgument;
import org.semanticweb.owlapi.model.SWRLObject;
import org.semanticweb.owlapi.model.SWRLObjectPropertyAtom;
import org.semanticweb.owlapi.model.SWRLObjectVisitor;
import org.semanticweb.owlapi.model.SWRLObjectVisitorEx;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.model.SWRLVariable;
import org.semanticweb.owlapi.util.CollectionFactory;
import org.semanticweb.owlapi.util.SWRLVariableExtractor;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 15-Jan-2007<br><br>
 */
public class SWRLRuleImpl extends OWLLogicalAxiomImpl implements SWRLRule {

    private Set<SWRLAtom> head;

    private Set<SWRLAtom> body;

    private Set<SWRLVariable> variables;

//    private Set<SWRLVariable> dVariables;
//
//    private Set<SWRLVariable> iVariables;

    private Boolean containsAnonymousClassExpressions = null;

    private Set<OWLClassExpression> classAtomsPredicates;


    public SWRLRuleImpl(OWLDataFactory dataFactory, Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.head = new TreeSet<SWRLAtom>(head);
        this.body = new TreeSet<SWRLAtom>(body);
    }


    public SWRLRule getAxiomWithoutAnnotations() {
        if (!isAnnotated()) {
            return this;
        }
        return getOWLDataFactory().getSWRLRule(getBody(), getHead());
    }
    @SuppressWarnings("unused")
    public OWLAxiom getAnnotatedAxiom(Set<OWLAnnotation> annotations) {
        return getOWLDataFactory().getSWRLRule(getBody(), getHead());
    }

    public SWRLRuleImpl(OWLDataFactory dataFactory, Set<? extends SWRLAtom> body, Set<? extends SWRLAtom> head) {
        this(dataFactory, body, head, new ArrayList<OWLAnnotation>(0));
    }


    public Set<SWRLVariable> getVariables() {
        if (variables == null) {
            Set<SWRLVariable> vars = new HashSet<SWRLVariable>();
            SWRLVariableExtractor extractor = new SWRLVariableExtractor();
            accept(extractor);
            vars.addAll(extractor.getVariables());
            variables = new HashSet<SWRLVariable>(vars);
        }
        return variables;
    }

    public boolean containsAnonymousClassExpressions() {
        if (containsAnonymousClassExpressions == null) {
            for (SWRLAtom atom : head) {
                if (atom instanceof SWRLClassAtom) {
                    if (((SWRLClassAtom) atom).getPredicate().isAnonymous()) {
                        containsAnonymousClassExpressions = true;
                        break;
                    }
                }
            }
            if (containsAnonymousClassExpressions == null) {
                for (SWRLAtom atom : body) {
                    if (atom instanceof SWRLClassAtom) {
                        if (((SWRLClassAtom) atom).getPredicate().isAnonymous()) {
                            containsAnonymousClassExpressions = true;
                            break;
                        }
                    }
                }
            }
            if (containsAnonymousClassExpressions == null) {
                containsAnonymousClassExpressions = false;
            }
        }
        return containsAnonymousClassExpressions;
    }


    public Set<OWLClassExpression> getClassAtomPredicates() {
        if (classAtomsPredicates == null) {
            Set<OWLClassExpression> predicates = new HashSet<OWLClassExpression>();
            for (SWRLAtom atom : head) {
                if (atom instanceof SWRLClassAtom) {
                    predicates.add(((SWRLClassAtom) atom).getPredicate());
                }
            }
            for (SWRLAtom atom : body) {
                if (atom instanceof SWRLClassAtom) {
                    predicates.add(((SWRLClassAtom) atom).getPredicate());
                }
            }
            classAtomsPredicates = new HashSet<OWLClassExpression>(predicates);
        }
        return classAtomsPredicates;
    }


    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public void accept(SWRLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(SWRLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    /**
     * Gets the atoms in the antecedent
     * @return A set of <code>SWRLAtom</code>s, which represent the atoms
     *         in the antecedent of the rule.
     */
    public Set<SWRLAtom> getBody() {
        return CollectionFactory.getCopyOnRequestSet(body);
    }


    /**
     * Gets the atoms in the consequent.
     * @return A set of <code>SWRLAtom</code>s, which represent the atoms
     *         in the consequent of the rule
     */
    public Set<SWRLAtom> getHead() {
        return CollectionFactory.getCopyOnRequestSet(head);
    }


    public void accept(OWLAxiomVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLAxiomVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    /**
     * If this rule contains atoms that have predicates that are inverse object properties, then this method
     * creates and returns a rule where the arguments of these atoms are fliped over and the predicate is the
     * inverse (simplified) property
     * @return The rule such that any atoms of the form  inverseOf(p)(x, y) are transformed to p(x, y).
     */
    public SWRLRule getSimplified() {
        return (SWRLRule) this.accept(ATOM_SIMPLIFIER);
    }

    /**
     * Determines if this axiom is a logical axiom. Logical axioms are defined to be
     * axioms other than declaration axioms (including imports declarations) and annotation
     * axioms.
     * @return <code>true</code> if the axiom is a logical axiom, <code>false</code>
     *         if the axiom is not a logical axiom.
     */
    public boolean isLogicalAxiom() {
        return true;
    }

    @Override
	public boolean equals(Object obj) {
        if (!(obj instanceof SWRLRule)) {
            return false;
        }
        SWRLRule other = (SWRLRule) obj;
        return other.getBody().equals(body) && other.getHead().equals(head);
    }


    public AxiomType<?> getAxiomType() {
        return AxiomType.SWRL_RULE;
    }


    @Override
	protected int compareObjectOfSameType(OWLObject object) {
        SWRLRule other = (SWRLRule) object;

        int diff = compareSets(getBody(), other.getBody());
        if (diff == 0) {
            diff = compareSets(getHead(), other.getHead());
        }
        return diff;

    }

    protected AtomSimplifier ATOM_SIMPLIFIER = new AtomSimplifier();

    protected class AtomSimplifier implements SWRLObjectVisitorEx<SWRLObject> {

        public SWRLRule visit(SWRLRule node) {
            Set<SWRLAtom> body = new HashSet<SWRLAtom>();
            for (SWRLAtom atom : node.getBody()) {
                body.add((SWRLAtom) atom.accept(this));
            }
            Set<SWRLAtom> head = new HashSet<SWRLAtom>();
            for (SWRLAtom atom : node.getHead()) {
                head.add((SWRLAtom) atom.accept(this));
            }
            return getOWLDataFactory().getSWRLRule(body, head);
        }

        public SWRLClassAtom visit(SWRLClassAtom node) {
            return node;
        }

        public SWRLDataRangeAtom visit(SWRLDataRangeAtom node) {
            return node;
        }

        public SWRLObjectPropertyAtom visit(SWRLObjectPropertyAtom node) {
            return node.getSimplified();
        }

        public SWRLDataPropertyAtom visit(SWRLDataPropertyAtom node) {
            return node;
        }

        public SWRLBuiltInAtom visit(SWRLBuiltInAtom node) {
            return node;
        }

        public SWRLVariable visit(SWRLVariable node) {
            return node;
        }

        public SWRLIndividualArgument visit(SWRLIndividualArgument node) {
            return node;
        }

        public SWRLLiteralArgument visit(SWRLLiteralArgument node) {
            return node;
        }

        public SWRLSameIndividualAtom visit(SWRLSameIndividualAtom node) {
            return node;
        }

        public SWRLDifferentIndividualsAtom visit(SWRLDifferentIndividualsAtom node) {
            return node;
        }
    }
}
