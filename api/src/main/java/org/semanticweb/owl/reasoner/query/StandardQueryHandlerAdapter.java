package org.semanticweb.owl.reasoner.query;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.reasoner.query.Hierarchy;
import org.semanticweb.owl.reasoner.query.HierarchyNode;
import org.semanticweb.owl.reasoner.query.IsSatisfiable;
import org.semanticweb.owl.reasoner.UnsupportedQueryTypeException;

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
 * Date: 18-Mar-2009
 */
public class StandardQueryHandlerAdapter implements StandardQueryHandler {

    public Hierarchy<OWLClass> answer(GetClassHierarchy query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Hierarchy<OWLDataPropertyExpression> answer(GetDataPropertyHierarchy query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Set<OWLNamedIndividual> answer(GetInstances query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Hierarchy<OWLObjectPropertyExpression> answer(GetObjectPropertyHierarchy query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Set<HierarchyNode<OWLClass>> answer(GetSubClasses query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Set<HierarchyNode<OWLDataProperty>> answer(GetSubDataProperties query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Set<HierarchyNode<OWLObjectPropertyExpression>> answer(GetSubObjectProperties query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Set<HierarchyNode<OWLClass>> answer(GetSuperClasses query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Set<HierarchyNode<OWLDataProperty>> answer(GetSuperDataProperties query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Set<HierarchyNode<OWLObjectPropertyExpression>> answer(GetSuperObjectProperties query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Set<HierarchyNode<OWLClass>> answer(GetTypes query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Boolean answer(IsConsistent query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Boolean answer(IsEntailed query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Boolean answer(IsSatisfiable query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }


    public Set<OWLPropertyAssertionAxiom> answer(GetPropertyAssertions query) throws UnsupportedQueryTypeException, InterruptedException {
        throw new UnsupportedQueryTypeException(query);
    }
}
