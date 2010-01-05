package org.semanticweb.owlapi.model;
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
 * Date: 13-Nov-2006<br><br>
 * </p>
 * An interface to objects that can visit {@link org.semanticweb.owlapi.model.OWLClassExpression}s.
 * (See the <a href="http://en.wikipedia.org/wiki/Visitor_pattern">Visitor Patterns</a>)
 *
 */
public interface OWLClassExpressionVisitor {

    void visit(OWLClass ce);

    void visit(OWLObjectIntersectionOf ce);

    void visit(OWLObjectUnionOf ce);

    void visit(OWLObjectComplementOf ce);

    void visit(OWLObjectSomeValuesFrom ce);

    void visit(OWLObjectAllValuesFrom ce);

    void visit(OWLObjectHasValue ce);

    void visit(OWLObjectMinCardinality ce);

    void visit(OWLObjectExactCardinality ce);

    void visit(OWLObjectMaxCardinality ce);

    void visit(OWLObjectHasSelf ce);

    void visit(OWLObjectOneOf ce);

    void visit(OWLDataSomeValuesFrom ce);

    void visit(OWLDataAllValuesFrom ce);

    void visit(OWLDataHasValue ce);

    void visit(OWLDataMinCardinality ce);

    void visit(OWLDataExactCardinality ce);

    void visit(OWLDataMaxCardinality ce);


}
