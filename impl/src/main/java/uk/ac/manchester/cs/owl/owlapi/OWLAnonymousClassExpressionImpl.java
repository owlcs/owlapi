package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.NNF;

import java.util.Collections;
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
public abstract class OWLAnonymousClassExpressionImpl extends OWLClassExpressionImpl implements OWLAnonymousClassExpression {

    public OWLAnonymousClassExpressionImpl(OWLDataFactory dataFactory) {
        super(dataFactory);
    }


    public boolean isAnonymous() {
        return true;
    }


    public boolean isOWLThing() {
        return false;
    }


    public boolean isOWLNothing() {
        return false;
    }


    public OWLClassExpression getNNF() {
        NNF nnf = new NNF(getOWLDataFactory());
        return accept(nnf);
    }

    public OWLClassExpression getComplementNNF() {
        NNF nnf = new NNF(getOWLDataFactory());
        return getOWLDataFactory().getOWLObjectComplementOf(this).accept(nnf);
    }

    /**
     * Gets the object complement of this class expression.
     * @return A class expression that is the complement of this class expression.
     */
    public OWLClassExpression getObjectComplementOf() {
        return getOWLDataFactory().getOWLObjectComplementOf(this);
    }

    public OWLClass asOWLClass() {
        throw new OWLRuntimeException("Not an OWLClass.  This method should only be called if the isAnonymous method returns false!");
    }


    public Set<OWLClassExpression> asConjunctSet() {
        return Collections.singleton((OWLClassExpression) this);
    }

    public boolean containsConjunct(OWLClassExpression ce) {
        return ce.equals(this);
    }

    public Set<OWLClassExpression> asDisjunctSet() {
        return Collections.singleton((OWLClassExpression) this);
    }
}
