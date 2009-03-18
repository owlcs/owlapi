package org.semanticweb.owl.reasoner.query;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.reasoner.query.Hierarchy;
import org.semanticweb.owl.reasoner.query.HierarchyNode;
import org.semanticweb.owl.reasoner.query.IsSatisfiable;
import org.semanticweb.owl.reasoner.UnsupportedQueryTypeException;

import java.util.Set;

/*
 * Copyright (C) 2008, University of Manchester
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
 * Author: Matthew Horridge<br> The University of Manchester<br> Information Management Group<br>
 * Date: 21-Jan-2009
 *
 * <p>
 * The BuiltInQueryHandler is essentially part of an implementation of the visitor design patter that allows
 * reasoner to process built in queries without the need for lots of {@link instanceof} checks.
 * </p>
 */
public interface BuiltInQueryHandler {

    Boolean answer(IsConsistent query) throws UnsupportedQueryTypeException, InterruptedException;

    Boolean answer(IsSatisfiable query) throws UnsupportedQueryTypeException, InterruptedException;

    Boolean answer(IsEntailed query) throws UnsupportedQueryTypeException, InterruptedException;

    Hierarchy<OWLClass> answer(GetClassHierarchy query) throws UnsupportedQueryTypeException, InterruptedException;

    Hierarchy<OWLObjectPropertyExpression> answer(GetObjectPropertyHierarchy query) throws UnsupportedQueryTypeException, InterruptedException;

    Hierarchy<OWLDataPropertyExpression> answer(GetDataPropertyHierarchy query) throws UnsupportedQueryTypeException, InterruptedException;

    Set<HierarchyNode<OWLClass>> answer(GetSuperClasses query) throws UnsupportedQueryTypeException, InterruptedException;

    Set<HierarchyNode<OWLClass>> answer(GetSubClasses  query) throws UnsupportedQueryTypeException, InterruptedException;

    Set<HierarchyNode<OWLObjectPropertyExpression>> answer(GetSubObjectProperties query) throws UnsupportedQueryTypeException, InterruptedException;

    Set<HierarchyNode<OWLObjectPropertyExpression>> answer(GetSuperObjectProperties query) throws UnsupportedQueryTypeException, InterruptedException;

    Set<HierarchyNode<OWLDataProperty>> answer(GetSubDataProperties query) throws UnsupportedQueryTypeException, InterruptedException;

    Set<HierarchyNode<OWLDataProperty>> answer(GetSuperDataProperties query) throws UnsupportedQueryTypeException, InterruptedException;

    Set<HierarchyNode<OWLClass>> answer(GetTypes query) throws UnsupportedQueryTypeException, InterruptedException;

    Set<OWLNamedIndividual> answer(GetInstances query) throws UnsupportedQueryTypeException, InterruptedException;

    Set<OWLPropertyAssertionAxiom> answer(GetPropertyAssertions query) throws UnsupportedQueryTypeException, InterruptedException;

}
