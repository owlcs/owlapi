package org.semanticweb.owlapi.reasoner.query;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObjectSomeValuesFrom;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.reasoner.UnsupportedQueryTypeException;

import java.util.Set;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 04-Jun-2009
 */
public class GetObjectPropertyDomains implements DerivableQuery<Set<HierarchyNode<OWLClass>>> {

    private OWLObjectPropertyExpression property;

    private GetSuperClasses internalQuery;

    public GetObjectPropertyDomains(OWLObjectPropertyExpression property, boolean direct, OWLDataFactory dataFactory) {
        this.property = property;
        OWLObjectSomeValuesFrom ce = dataFactory.getOWLObjectSomeValuesFrom(property, dataFactory.getOWLThing());
        internalQuery = new GetSuperClasses(ce, direct);
    }

    public OWLObjectPropertyExpression getProperty() {
        return property;
    }

    public boolean isDirect() {
        return internalQuery.isDirect();
    }

    public Set<HierarchyNode<OWLClass>> execute(OWLReasoner reasoner) throws UnsupportedQueryTypeException, InterruptedException {
        return reasoner.answerQuery(internalQuery);
    }


}
