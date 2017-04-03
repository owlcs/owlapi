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
package org.semanticweb.owlapi.util;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnonymousIndividual;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectVisitorEx;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.vocab.OWLFacet;

/**
 * Class to remove structure sharing from OWL objects (Axioms or expressions)
 *
 * @author Ignazio Palmisano
 * @since 4.2.8
 */
public class OWLObjectDesharer extends TransformerVisitorBase<Object>
    implements OWLObjectVisitorEx<OWLObject> {

    protected RemappingIndividualProvider anonProvider;

    /**
     * Creates an object duplicator that duplicates objects using the specified data factory and uri
     * replacement map.
     *
     * @param m The manager providing data factory and config to be used for the duplication.
     */
    public OWLObjectDesharer(OWLOntologyManager m) {
        super(x -> true, x -> x instanceof OWLFacet ? x : null, m.getOWLDataFactory(),
            Object.class);
        anonProvider = new RemappingIndividualProvider(m.getOntologyConfigurator(), df);
    }

    /**
     * @param object the object to duplicate
     * @param <O> return type
     * @return the duplicate
     */
    public <O extends OWLObject> O deshareObject(O object) {
        return t(checkNotNull(object, "object cannot be null"));
    }

    @Override
    public OWLLiteral visit(OWLLiteral node) {
        if (node.hasLang()) {
            return df.getOWLLiteral(node.getLiteral(), node.getLang());
        }
        return df.getOWLLiteral(node.getLiteral(), t(node.getDatatype()));
    }

    @Override
    public OWLObjectInverseOf visit(OWLObjectInverseOf property) {
        OWLObjectPropertyExpression inverse = property.getInverse();
        if (inverse.isAnonymous()) {
            return df.getOWLObjectInverseOf(t(property.getNamedProperty()));
        }
        return df.getOWLObjectInverseOf(t(inverse.asOWLObjectProperty()));
    }

    @Override
    public OWLOntology visit(OWLOntology ontology) {
        // Should we duplicate ontologies here? Probably not.
        return ontology;
    }

    @Override
    public OWLAnonymousIndividual visit(OWLAnonymousIndividual individual) {
        return anonProvider.getOWLAnonymousIndividual(individual.getID().getID());
    }

    @Override
    public IRI visit(IRI iri) {
        return iri;
    }
}
