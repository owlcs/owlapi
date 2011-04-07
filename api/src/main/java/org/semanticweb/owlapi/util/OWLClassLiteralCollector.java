/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.semanticweb.owlapi.util;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectComplementOf;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 23-Jul-2009
 */
public class OWLClassLiteralCollector extends OWLObjectWalker<OWLObject> {

	// XXX stateful visitor...
    private Set<OWLClass> pos = new HashSet<OWLClass>();

    private Set<OWLClass> neg = new HashSet<OWLClass>();

    private boolean processed = false;

    public OWLClassLiteralCollector(Set<OWLObject> objects) {
        super(objects);
    }

    public OWLClassLiteralCollector(Set<OWLObject> objects, boolean visitDuplicates) {
        super(objects, visitDuplicates);
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

        public OWLClassLiteralCollectorVisitor() {
			// TODO Auto-generated constructor stub
		}

		@Override
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
