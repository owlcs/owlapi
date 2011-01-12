package org.semanticweb.owlapi.util;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLEquivalentClassesAxiom;
import org.semanticweb.owlapi.model.OWLObjectIntersectionOf;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLSubClassOfAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Dec-2006<br><br>
 * <p/>
 * A utility class that can be used to determine is a class is a syntactic direct
 * subclass of owl:Thing.  A class is considered NOT to be a syntactic direct subclass
 * of owl:Thing if ANY of the following conditions apply:
 * <p/>
 * <ol>
 * <li>It is equal to the left hand side of a subclass axiom, where the right hand side
 * is a named class other than owl:Thing</li>
 * <li>It is an operand in an equivalent class axiom where at least one of the other other
 * operands is an intersection class that has a named operand other than the class in question.
 * For example  <code>EquivalentClasses(A,  (B and prop some C))</code></li>
 * </ol>
 * <p/>
 * This functionality is provided because it is useful for displaying class hierarchies in
 * editors and browsers.  In these situations it is needed because not all "orphan" classes
 * are asserted to be subclasses of owl:Thing.  For example, if the only referencing axiom
 * of class A was ObjectDomain(propP A) then A is a syntactic subclass of owl:Thing
 */
public class SimpleRootClassChecker implements RootClassChecker {

    private Set<OWLOntology> ontologies;


    /**
     * Creates a root class checker, which examines axioms contained in ontologies from
     * the specified set in order to determine if a class is a syntactic subclass of
     * owl:Thing
     *
     * @param ontologies The ontologies whose axioms are to be taken into consideration
     *                   when determining if a class is a syntactic direct subclass of owl:Thing
     */
    public SimpleRootClassChecker(Set<OWLOntology> ontologies) {
        this.ontologies = ontologies;
    }

    //  Rules for determining if a class is a direct subclass of Thing

    // 1) It isn't referenced by ANY subclass axiom or equivalent class axioms
    // 2) It is reference only by subclass axioms, but doesn't appear on the LHS of these axioms
    // 3) It is on the LHS of a subclass axiom where the RHS is Thing
    // 4) It is referenced only by equivalent class axioms, where all other operands in these axioms are named
    // 5) It is not referenced by subclass axioms and is not referenced by any equivalent class axiom where there is
    //    at least one operand in the equivalent class axiom which is an intersection containing a named operand i.e.
    //    EquivalentClasses(A  (B and hasP some C)) would not be a subclass of Thing

    private RootClassCheckerHelper checker = new RootClassCheckerHelper();

    protected NamedSuperChecker superChecker = new NamedSuperChecker();


    /**
     * Determines if the specified class is a direct syntactic subclass of owl:Thing
     *
     * @param cls The class to be checked.
     * @return <code>true</code> if the class is a direct syntactic root class of
     *         owl:Thing, otherwise <code>false</code>.
     */
    public boolean isRootClass(OWLClass cls) {

        for (OWLOntology ont : ontologies) {
            for (OWLAxiom ax : ont.getReferencingAxioms(cls)) {
                checker.setOWLClass(cls);
                ax.accept(checker);
                if (!checker.isRoot()) {
                    return false;
                }
            }
        }
        return true;
    }

    private static class NamedSuperChecker extends OWLClassExpressionVisitorAdapter {

        private boolean namedSuper;

        public void reset() {
            namedSuper = false;
        }

        @Override
        @SuppressWarnings("unused")
		public void visit(OWLClass desc) {
            namedSuper = true;
        }


        @Override
		public void visit(OWLObjectIntersectionOf desc) {
            for (OWLClassExpression op : desc.getOperands()) {
                op.accept(this);
                if (namedSuper) {
                    break;
                }
            }
        }
    }


    /**
     * A utility class that checks if an axiom gives rise to
     * a class being a subclass of Thing.
     */
    private class RootClassCheckerHelper extends OWLAxiomVisitorAdapter {

        private boolean isRoot;

        private OWLClass cls;


        public void setOWLClass(OWLClass cls) {
            // Start off with the assumption that the class is
            // a root class.  This means if the class isn't referenced
            // by any equivalent class axioms or subclass axioms then
            // we correctly identify it as a root
            isRoot = true;
            this.cls = cls;
        }


        public boolean isRoot() {
            return isRoot;
        }


        @Override
		public void visit(OWLSubClassOfAxiom axiom) {
            if (axiom.getSubClass().equals(cls)) {
                superChecker.reset();
                axiom.getSuperClass().accept(superChecker);
                isRoot = !superChecker.namedSuper;
            }
        }


        @Override
		public void visit(OWLEquivalentClassesAxiom axiom) {
            Set<OWLClassExpression> descs = axiom.getClassExpressions();
            if (!descs.contains(cls)) {
                return;
            }
            for (OWLClassExpression desc : descs) {
                if (desc.equals(cls)) {
                    continue;
                }
                superChecker.reset();
                desc.accept(superChecker);
                if (superChecker.namedSuper) {
                    isRoot = false;
                    return;
                }
            }
            isRoot = !superChecker.namedSuper;
        }
    }
}
