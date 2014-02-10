/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2014, The University of Manchester
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
 * Copyright 2014, The University of Manchester
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
package org.semanticweb.owlapi;

import org.coode.owlapi.functionalparser.OWLFunctionalSyntaxOWLParser;
import org.coode.owlapi.functionalrenderer.OWLFunctionalSyntaxOntologyStorer;
import org.coode.owlapi.latex.LatexOntologyStorer;
import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyParser;
import org.coode.owlapi.oboformat.OBOFormatOWLAPIParser;
import org.coode.owlapi.oboformat.OBOFormatStorer;
import org.coode.owlapi.owlxml.renderer.OWLXMLOntologyStorer;
import org.coode.owlapi.owlxmlparser.OWLXMLParser;
import org.coode.owlapi.rdf.rdfxml.RDFXMLOntologyStorer;
import org.coode.owlapi.rdfxml.parser.RDFXMLParser;
import org.coode.owlapi.turtle.TurtleOntologyStorer;
import org.semanticweb.owlapi.io.OWLParser;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyFactory;
import org.semanticweb.owlapi.model.OWLOntologyIRIMapper;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyStorer;
import org.semanticweb.owlapi.util.NonMappingOntologyIRIMapper;

import uk.ac.manchester.cs.owl.owlapi.EmptyInMemOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;
import uk.ac.manchester.cs.owl.owlapi.OWLOntologyManagerImpl;
import uk.ac.manchester.cs.owl.owlapi.ParsableOWLOntologyFactory;
import uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer.ManchesterOWLSyntaxOntologyStorer;
import uk.ac.manchester.cs.owl.owlapi.turtle.parser.TurtleOntologyParser;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import com.google.inject.multibindings.Multibinder;

import de.uulm.ecs.ai.owlapi.krssparser.KRSS2OWLParser;
import de.uulm.ecs.ai.owlapi.krssrenderer.KRSS2OWLSyntaxOntologyStorer;

/** owlapi module */
public class OWLAPIModule extends AbstractModule {
    @Provides
    protected OWLDataFactory provideOWLDataFactory() {
        return new OWLDataFactoryImpl(true, false);
    }

    @Provides
    protected OWLOntologyManager provideOWLOntologyManager(OWLDataFactory df) {
        return new OWLOntologyManagerImpl(df);
    }

    @Override
    protected void configure() {
        multibind(OWLParser.class, OBOFormatOWLAPIParser.class,
                ManchesterOWLSyntaxOntologyParser.class, KRSS2OWLParser.class,
                TurtleOntologyParser.class, OWLFunctionalSyntaxOWLParser.class,
                OWLXMLParser.class, RDFXMLParser.class);
        multibind(OWLOntologyStorer.class, RDFXMLOntologyStorer.class,
                OWLXMLOntologyStorer.class,
                OWLFunctionalSyntaxOntologyStorer.class,
                ManchesterOWLSyntaxOntologyStorer.class,
                KRSS2OWLSyntaxOntologyStorer.class, TurtleOntologyStorer.class,
                LatexOntologyStorer.class, OBOFormatStorer.class);
        multibind(OWLOntologyFactory.class, EmptyInMemOWLOntologyFactory.class,
                ParsableOWLOntologyFactory.class);
        multibind(OWLOntologyIRIMapper.class, NonMappingOntologyIRIMapper.class);
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
