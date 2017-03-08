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
package org.semanticweb.owlapi.model.axiomproviders;

import java.util.Collection;
import java.util.Collections;

import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLDataRange;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDeclarationAxiom;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * Declaration provider interface.
 */
public interface DeclarationAxiomProvider {

    /**
     * Gets a declaration for an entity
     *
     * @param owlEntity The declared entity.
     * @return The declaration axiom for the specified entity.
     */
    default OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity) {
        return getOWLDeclarationAxiom(owlEntity, Collections.emptySet());
    }

    /**
     * Gets a declaration with zero or more annotations for an entity
     *
     * @param owlEntity The declared entity.
     * @param annotations A set of annotations. Cannot be null or contain nulls.
     * @return The declaration axiom for the specified entity which is annotated with the specified
     * annotations
     */
    OWLDeclarationAxiom getOWLDeclarationAxiom(OWLEntity owlEntity,
        Collection<OWLAnnotation> annotations);

    /**
     * @param datatype data type
     * @param dataRange data Range
     * @return a datatype definition axiom
     */
    default OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype,
        OWLDataRange dataRange) {
        return getOWLDatatypeDefinitionAxiom(datatype, dataRange, Collections.emptySet());
    }

    /**
     * @param datatype data type
     * @param dataRange data Range
     * @param annotations A set of annotations.
     * @return a datatype definition axiom with annotations
     */
    OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype,
        OWLDataRange dataRange, Collection<OWLAnnotation> annotations);

    /**
     * @param datatype data type
     * @param dataRange data Range
     * @return a datatype definition axiom
     */
    default OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype,
        OWL2Datatype dataRange) {
        return getOWLDatatypeDefinitionAxiom(datatype, dataRange, Collections.emptySet());
    }

    /**
     * @param datatype data type
     * @param dataRange data Range
     * @param annotations A set of annotations.
     * @return a datatype definition axiom with annotations
     */
    OWLDatatypeDefinitionAxiom getOWLDatatypeDefinitionAxiom(OWLDatatype datatype,
        OWL2Datatype dataRange, Collection<OWLAnnotation> annotations);
}
