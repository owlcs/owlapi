package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNaryBooleanClassExpression;
import org.semanticweb.owlapi.model.OWLObject;

import java.util.*;
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
public abstract class OWLNaryBooleanClassExpressionImpl extends OWLAnonymousClassExpressionImpl implements OWLNaryBooleanClassExpression {

    private Set<OWLClassExpression> operands;


    public OWLNaryBooleanClassExpressionImpl(OWLDataFactory dataFactory, Set<? extends OWLClassExpression> operands) {
        super(dataFactory);
        this.operands = Collections.unmodifiableSet(new TreeSet<OWLClassExpression>(operands));
    }

    public List<OWLClassExpression> getOperandsAsList() {
        return new ArrayList<OWLClassExpression>(operands);
    }

    public Set<OWLClassExpression> getOperands() {
        return operands;
    }


    public boolean isClassExpressionLiteral() {
        return false;
    }


    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLNaryBooleanClassExpression)) {
                return false;
            }
            return ((OWLNaryBooleanClassExpression) obj).getOperands().equals(operands);
        }
        return false;
    }


    final protected int compareObjectOfSameType(OWLObject object) {
        return compareSets(operands, ((OWLNaryBooleanClassExpression) object).getOperands());
    }
}
