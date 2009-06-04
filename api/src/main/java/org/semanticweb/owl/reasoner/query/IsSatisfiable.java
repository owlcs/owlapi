package org.semanticweb.owl.reasoner.query;

import org.semanticweb.owl.model.OWLClassExpression;
import org.semanticweb.owl.reasoner.UnsupportedQueryTypeException;
import org.semanticweb.owl.reasoner.query.StandardQuery;
import org.semanticweb.owl.reasoner.query.StandardQueryHandler;/*
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
 * Determines whether a given class expression is satisfiable with respect to the reasoner ontologies.
 * </p>
 */
public class IsSatisfiable implements StandardQuery<Boolean> {

    private OWLClassExpression expression;

    public IsSatisfiable(OWLClassExpression expression) {
        this.expression = expression;
    }

    public OWLClassExpression getExpression() {
        return expression;
    }

    public Boolean accept(StandardQueryHandler handler) throws UnsupportedQueryTypeException, InterruptedException {
        return handler.answer(this);
    }
}
