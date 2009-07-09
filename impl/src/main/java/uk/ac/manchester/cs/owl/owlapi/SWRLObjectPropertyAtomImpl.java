package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 15-Jan-2007<br><br>
 */
public class SWRLObjectPropertyAtomImpl extends SWRLBinaryAtomImpl<SWRLIArgument, SWRLIArgument> implements SWRLObjectPropertyAtom {

    public SWRLObjectPropertyAtomImpl(OWLDataFactory dataFactory, OWLObjectPropertyExpression predicate, SWRLIArgument arg0, SWRLIArgument arg1) {
        super(dataFactory, predicate, arg0, arg1);
    }

    public OWLObjectPropertyExpression getPredicate() {
        return (OWLObjectPropertyExpression) super.getPredicate();
    }

    /**
     * Gets a simplified form of this atom.  This basically creates and returns a new atom where the predicate is not
     * an inverse object property.  If the atom is of the form P(x, y) then P(x, y) is returned.  If the atom is of the
     * form inverseOf(P)(x, y) then P(y, x) is returned.
     * @return This atom in a simplified form
     */
    public SWRLObjectPropertyAtom getSimplified() {
        OWLObjectPropertyExpression prop = getPredicate().getSimplified();
        if (prop.equals(getPredicate())) {
            return this;
        }
        else if (prop.isAnonymous()) {
            // Flip
            return getOWLDataFactory().getSWRLObjectPropertyAtom(prop.getInverseProperty().getSimplified(), getSecondArgument(), getFirstArgument());
        }
        else {
            // No need to flip
            return getOWLDataFactory().getSWRLObjectPropertyAtom(prop, getFirstArgument(), getSecondArgument());
        }
    }

    public void accept(OWLObjectVisitor visitor) {
        visitor.visit(this);
    }


    public void accept(SWRLObjectVisitor visitor) {
        visitor.visit(this);
    }

    public <O> O accept(SWRLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }


    public <O> O accept(OWLObjectVisitorEx<O> visitor) {
        return visitor.visit(this);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SWRLObjectPropertyAtom)) {
            return false;
        }
        SWRLObjectPropertyAtom other = (SWRLObjectPropertyAtom) obj;
        return other.getPredicate().equals(getPredicate()) && other.getFirstArgument().equals(getFirstArgument()) && other.getSecondArgument().equals(getSecondArgument());
    }
}
