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
 * Copyright 2011, The University of Manchester
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

package org.coode.owlapi.examples.dlquery;

import org.semanticweb.owlapi.expression.ParserException;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.reasoner.OWLReasoner;
import org.semanticweb.owlapi.util.ShortFormProvider;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-May-2010
 */
public class DLQueryPrinter {

    private OWLReasoner reasoner;

    private DLQueryEngine dlQueryEngine;

    private ShortFormProvider shortFormProvider;

    public DLQueryPrinter(OWLReasoner reasoner, ShortFormProvider shortFormProvider) {
        this.reasoner = reasoner;
        this.shortFormProvider = shortFormProvider;
        dlQueryEngine = new DLQueryEngine(reasoner, shortFormProvider);
    }

    public void askQuery(String classExpression) {
        if (classExpression.length() == 0) {
            System.out.println("No class expression specified");
        }
        else {
            try {
                StringBuilder sb = new StringBuilder();
                sb.append("\n--------------------------------------------------------------------------------\n");
                sb.append("QUERY:   ");
                sb.append(classExpression);
                sb.append("\n");
                sb.append("--------------------------------------------------------------------------------\n\n");

                // Ask for the subclasses, superclasses etc. of the specified class expression.  Print out the results.
                Set<OWLClass> superClasses = dlQueryEngine.getSuperClasses(classExpression, true);
                printEntities("SuperClasses", superClasses, sb);
                Set<OWLClass> equivalentClasses = dlQueryEngine.getEquivalentClasses(classExpression);
                printEntities("EquivalentClasses", equivalentClasses, sb);
                Set<OWLClass> subClasses = dlQueryEngine.getSubClasses(classExpression, true);
                printEntities("SubClasses", subClasses, sb);
                Set<OWLNamedIndividual> individuals = dlQueryEngine.getInstances(classExpression, true);
                printEntities("Instances", individuals, sb);

                System.out.println(sb.toString());
            }
            catch (ParserException e) {
                System.out.println(e.getMessage());
            }
        }

    }

    private void printEntities(String name, Set<? extends OWLEntity> entities, StringBuilder sb) {
        sb.append(name);
        int length = 50 - name.length();
        for (int i = 0; i < length; i++) {
            sb.append(".");
        }
        sb.append("\n\n");
        if (!entities.isEmpty()) {
            for (OWLEntity entity : entities) {
                sb.append("\t");
                sb.append(shortFormProvider.getShortForm(entity));
                sb.append("\n");
            }
        }
        else {
            sb.append("\t[NONE]\n");
        }
        sb.append("\n");
    }


}
