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

package org.semanticweb.owlapi.api.test;

import java.util.Collections;

import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntology;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 05/01/2011
 */
public class AnnotationPropertyConvenienceMethodTestCase extends AbstractOWLAPITestCase {

    public void testGetSuperProperties() {
        OWLOntology ont = getOWLOntology("OntA");

        OWLDataFactory df = ont.getOWLOntologyManager().getOWLDataFactory();
        OWLAnnotationProperty propP = getOWLAnnotationProperty("propP");
        OWLAnnotationProperty propQ = getOWLAnnotationProperty("propQ");
        OWLAnnotationProperty propR = getOWLAnnotationProperty("propR");
        ont.getOWLOntologyManager().addAxiom(ont, df.getOWLSubAnnotationPropertyOfAxiom(propP, propQ));
        ont.getOWLOntologyManager().addAxiom(ont, df.getOWLSubAnnotationPropertyOfAxiom(propP, propR));

        assertTrue(propP.getSuperProperties(ont).contains(propQ));
        assertTrue(propP.getSuperProperties(Collections.singleton(ont)).contains(propQ));
        assertTrue(propP.getSuperProperties(ont, true).contains(propQ));
        assertTrue(propP.getSuperProperties(ont, false).contains(propQ));


        assertTrue(propP.getSuperProperties(ont).contains(propR));
        assertTrue(propP.getSuperProperties(Collections.singleton(ont)).contains(propR));
        assertTrue(propP.getSuperProperties(ont, true).contains(propR));
        assertTrue(propP.getSuperProperties(ont, false).contains(propR));
    }


    public void testGetSubProperties() {
        OWLOntology ont = getOWLOntology("OntA");

        OWLDataFactory df = ont.getOWLOntologyManager().getOWLDataFactory();
        OWLAnnotationProperty propP = getOWLAnnotationProperty("propP");
        OWLAnnotationProperty propQ = getOWLAnnotationProperty("propQ");
        OWLAnnotationProperty propR = getOWLAnnotationProperty("propR");
        ont.getOWLOntologyManager().addAxiom(ont, df.getOWLSubAnnotationPropertyOfAxiom(propP, propQ));
        ont.getOWLOntologyManager().addAxiom(ont, df.getOWLSubAnnotationPropertyOfAxiom(propP, propR));

        assertTrue(propQ.getSubProperties(ont).contains(propP));
        assertTrue(propQ.getSubProperties(Collections.singleton(ont)).contains(propP));
        assertTrue(propQ.getSubProperties(ont, true).contains(propP));
        assertTrue(propQ.getSubProperties(ont, false).contains(propP));

        assertTrue(propR.getSubProperties(ont).contains(propP));
        assertTrue(propR.getSubProperties(Collections.singleton(ont)).contains(propP));
        assertTrue(propR.getSubProperties(ont, true).contains(propP));
        assertTrue(propR.getSubProperties(ont, false).contains(propP));
    }


}
