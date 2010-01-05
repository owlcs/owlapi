package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.HashSet;
import java.util.Set;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public class OWLObjectIntersectionOfImpl extends OWLNaryBooleanClassExpressionImpl implements OWLObjectIntersectionOf {

    public OWLObjectIntersectionOfImpl(OWLDataFactory dataFactory, Set<? extends OWLClassExpression> operands) {
        super(dataFactory, operands);
    }

    /**
     * Gets the class expression type for this class expression
     * @return The class expression type
     */
    public ClassExpressionType getClassExpressionType() {
        return ClassExpressionType.OBJECT_INTERSETION_OF;
    }

    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLObjectIntersectionOf;
        }
        return false;
    }

    public void accept(OWLClassExpressionVisitor visitor) {
        visitor.visit(this);
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public <O> O accept(OWLClassExpressionVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public Set<OWLClassExpression> asConjunctSet() {
        Set<OWLClassExpression> conjuncts = new HashSet<OWLClassExpression>();
        for (OWLClassExpression op : getOperands()) {
            conjuncts.addAll(op.asConjunctSet());
        }
        return conjuncts;
    }


    @Override
    public boolean containsConjunct(OWLClassExpression ce) {
        if(ce.equals(this)) {
            return true;
        }
        for(OWLClassExpression op : getOperands()) {
            if(op.containsConjunct(ce)) {
                return true;
            }
        }
        return false;
    }
}
