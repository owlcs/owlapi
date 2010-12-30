package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyExpressionVisitor;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 06-Jun-2008<br><br>
 *
 * This utility class can be used to obtain an object property expression in its simplest form.
 * Let P be an object property name and PE a property expression, then the simplification is
 * inductively defined as:
 *
 * simp(P) = P
 * simp(inv(P)) = inv(P)
 * simp(inv(inv(PE)) = simp(PE)
 */
public class ObjectPropertySimplifier {

    private OWLDataFactory dataFactory;

    private Simplifier simplifier;

    public ObjectPropertySimplifier(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
        this.simplifier = new Simplifier();

    }


    /**
     * Gets an object property expression in its simplest form.
     * @param prop The object property expression to be simplified.
     * @return The simplest form of the object property expression.
     */
    public OWLObjectPropertyExpression getSimplified(OWLObjectPropertyExpression prop) {
        simplifier.reset();
        prop.accept(simplifier);
        if(simplifier.isInverse()) {
            return dataFactory.getOWLObjectInverseOf(simplifier.getProperty());
        }
        else {
            return simplifier.getProperty();
        }
    }

    private static class Simplifier implements OWLPropertyExpressionVisitor {

        private OWLObjectProperty property;

        private int depth;

        public void reset() {
            depth = 0;
            property = null;
        }

        public OWLObjectProperty getProperty() {
            return property;
        }


//        public int getDepth() {
//            return depth;
//        }

        public boolean isInverse() {
            return depth % 2 != 0;
        }


        public void visit(OWLObjectProperty property) {
            this.property = property;
        }


        public void visit(OWLObjectInverseOf property) {
            depth++;
            property.getInverse().accept(this);
        }


        public void visit(OWLDataProperty property) {
        }
    }
}
