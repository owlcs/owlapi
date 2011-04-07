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

import java.util.HashSet;
import java.util.Set;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 28-May-2009
 */
public abstract class AbstractAxiomsRoundTrippingTestCase extends AbstractRoundTrippingTest {

    private Set<OWLAxiom> axioms = new HashSet<OWLAxiom>();


    @Override
    final protected OWLOntology createOntology() {
        OWLOntology ont = getOWLOntology("Ont");
        axioms.clear();
        axioms.addAll(createAxioms());
        getManager().addAxioms(ont, axioms);
        for(OWLEntity entity : ont.getSignature()) {
            if (!entity.isBuiltIn()) {
                if (!ont.isDeclared(entity, true)) {
                    getManager().addAxiom(ont, getFactory().getOWLDeclarationAxiom(entity));
                }
            }
        }
        return ont;
    }

    @Override
    public void testRDFXML() throws Exception {
        super.testRDFXML();
    }

    @Override
    public void testOWLXML() throws Exception {
        super.testOWLXML();
    }

    @Override
    public void testFunctionalSyntax() throws Exception {
        super.testFunctionalSyntax();
    }

    @Override
    public void testTurtle() throws Exception {
        super.testTurtle();
    }

    @Override
    public void testManchesterOWLSyntax() throws Exception {
        super.testManchesterOWLSyntax();
    }

    protected abstract Set<? extends OWLAxiom> createAxioms();

    @Override
    protected boolean isIgnoreDeclarationAxioms(OWLOntologyFormat format) {
        return (format instanceof ManchesterOWLSyntaxOntologyFormat);
    }
}
