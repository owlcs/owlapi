/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi;

import org.semanticweb.owlapi.annotations.OwlapiModule;
import org.semanticweb.owlapi.functional.parser.OWLFunctionalSyntaxOWLParser;
import org.semanticweb.owlapi.functional.renderer.OWLFunctionalSyntaxOntologyStorer;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.krss2.parser.KRSS2OWLParser;
import org.semanticweb.owlapi.krss2.renderer.KRSS2OWLSyntaxOntologyStorer;
import org.semanticweb.owlapi.latex.renderer.LatexOntologyStorer;
import org.semanticweb.owlapi.mansyntax.parser.ManchesterOWLSyntaxOntologyParser;
import org.semanticweb.owlapi.mansyntax.renderer.ManchesterOWLSyntaxOntologyStorer;
import org.semanticweb.owlapi.model.OWLOntologyStorer;
import org.semanticweb.owlapi.owlxml.parser.OWLXMLParser;
import org.semanticweb.owlapi.owlxml.renderer.OWLXMLOntologyStorer;
import org.semanticweb.owlapi.rdf.rdfxml.parser.RDFXMLParser;
import org.semanticweb.owlapi.rdf.rdfxml.renderer.RDFXMLOntologyStorer;
import org.semanticweb.owlapi.rdf.turtle.parser.TurtleOntologyParser;
import org.semanticweb.owlapi.rdf.turtle.renderer.TurtleOntologyStorer;

import com.google.inject.AbstractModule;
import com.google.inject.multibindings.Multibinder;

/**
 * OWLAPI module. Bindings can be overridden by subclassing this class, to allow
 * to replace part of the configuration without having to rewrite all of it.
 */
@OwlapiModule
public class OWLAPIParsersModule extends AbstractModule {

    @Override
    protected void configure() {
        configureParsers();
        configureStorers();
    }

    @SuppressWarnings("unchecked")
    protected void configureStorers() {
        multibind(OWLOntologyStorer.class, RDFXMLOntologyStorer.class,
                OWLXMLOntologyStorer.class,
                OWLFunctionalSyntaxOntologyStorer.class,
                ManchesterOWLSyntaxOntologyStorer.class,
                KRSS2OWLSyntaxOntologyStorer.class, TurtleOntologyStorer.class,
                LatexOntologyStorer.class);
    }

    @SuppressWarnings("unchecked")
    protected void configureParsers() {
        multibind(OWLParser.class, ManchesterOWLSyntaxOntologyParser.class,
                KRSS2OWLParser.class, TurtleOntologyParser.class,
                OWLFunctionalSyntaxOWLParser.class, OWLXMLParser.class,
                RDFXMLParser.class);
    }

    private <T> Multibinder<T> multibind(Class<T> type,
            Class<? extends T>... implementations) {
        Multibinder<T> binder = Multibinder.newSetBinder(binder(), type);
        for (Class<? extends T> i : implementations) {
            binder.addBinding().to(i);
        }
        return binder;
    }
}
