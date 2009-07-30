package org.semanticweb.owlapi.util;

import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;

import java.util.Set;
import java.util.HashSet;
import java.util.List;
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
 * Date: 23-Jul-2009
 */
public class OWLClassLiteralCollector extends OWLObjectWalker<OWLObject> {

    private Set<OWLClass> pos = new HashSet<OWLClass>();

    private Set<OWLClass> neg = new HashSet<OWLClass>();

    private boolean processed = false;

    public OWLClassLiteralCollector(Set<? extends OWLObject> objects) {
        super(new HashSet<OWLObject>(objects));
    }

    public OWLClassLiteralCollector(Set<? extends OWLObject> objects, boolean visitDuplicates) {
        super(new HashSet<OWLObject>(objects), visitDuplicates);
    }

    private void process() {
        if(!processed) {
            processed = true;
            walkStructure(new OWLClassLiteralCollectorVisitor());
        }
    }

    public Set<OWLClass> getPositiveLiterals() {
        process();
        return new HashSet<OWLClass>(pos);
    }

    public Set<OWLClass> getNegativeLiterals() {
        process();
        return new HashSet<OWLClass>(neg);
    }

    private class OWLClassLiteralCollectorVisitor extends OWLObjectVisitorExAdapter<Object> {

        public Object visit(OWLClass desc) {
            List<OWLClassExpression> path = getClassExpressionPath();
            if(path.size() > 1) {
                OWLClassExpression prev = path.get(path.size() - 2);
                if(prev instanceof OWLObjectComplementOf) {
                    neg.add(desc);
                }
                else {
                    pos.add(desc);
                }
            }
            else {
                pos.add(desc);
            }
            return null;
        }
    }
}
