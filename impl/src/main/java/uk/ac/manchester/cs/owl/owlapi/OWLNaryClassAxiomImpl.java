package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

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
public abstract class OWLNaryClassAxiomImpl extends OWLClassAxiomImpl implements OWLNaryClassAxiom {

    private Set<OWLClassExpression> classExpressions;


    public OWLNaryClassAxiomImpl(OWLDataFactory dataFactory, Set<? extends OWLClassExpression> classExpressions, Collection<? extends OWLAnnotation> annotations) {
        super(dataFactory, annotations);
        this.classExpressions = Collections.unmodifiableSortedSet(new TreeSet<OWLClassExpression>(classExpressions));
    }


    public Set<OWLClassExpression> getClassExpressions() {
        return classExpressions;
    }

    public List<OWLClassExpression> getClassExpressionsAsList() {
        return new ArrayList<OWLClassExpression>(classExpressions);
    }

    public boolean contains(OWLClassExpression ce) {
        return classExpressions.contains(ce);
    }


    public Set<OWLClassExpression> getClassExpressionsMinus(OWLClassExpression... descs) {
        Set<OWLClassExpression> result = new HashSet<OWLClassExpression>(classExpressions);
        for (OWLClassExpression desc : descs) {
            result.remove(desc);
        }
        return result;
    }


    public boolean equals(Object obj) {
        if (super.equals(obj)) {
            if (!(obj instanceof OWLNaryClassAxiom)) {
                return false;
            }
            return ((OWLNaryClassAxiom) obj).getClassExpressions().equals(classExpressions);
        }
        return false;
    }


    protected int compareObjectOfSameType(OWLObject object) {
        return compareSets(classExpressions, ((OWLNaryClassAxiom) object).getClassExpressions());
    }
}
