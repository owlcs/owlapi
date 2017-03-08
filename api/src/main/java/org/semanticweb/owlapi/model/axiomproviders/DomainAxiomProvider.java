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

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLAnnotationProperty;
import org.semanticweb.owlapi.model.OWLAnnotationPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * Annotation, datatype and object property domain provider.
 */
public interface DomainAxiomProvider {

    /**
     * @param property property
     * @param classExpression class Expression
     * @return an object property domain axiom
     */
    default OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(
        OWLObjectPropertyExpression property, OWLClassExpression classExpression) {
        return getOWLObjectPropertyDomainAxiom(property, classExpression, Collections.emptySet());
    }

    /**
     * @param property property
     * @param classExpression class Expression
     * @param annotations A set of annotations. Cannot be null or contain nulls.
     * @return an object property domain axiom with annotations
     */
    OWLObjectPropertyDomainAxiom getOWLObjectPropertyDomainAxiom(
        OWLObjectPropertyExpression property, OWLClassExpression classExpression,
        Collection<OWLAnnotation> annotations);

    /**
     * @param property property
     * @param domain domain
     * @return a data property domain axiom
     */
    default OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(
        OWLDataPropertyExpression property, OWLClassExpression domain) {
        return getOWLDataPropertyDomainAxiom(property, domain, Collections.emptySet());
    }

    /**
     * @param property property
     * @param domain domain
     * @param annotations A set of annotations. Cannot be null or contain nulls.
     * @return a data property domain axiom with annotations
     */
    OWLDataPropertyDomainAxiom getOWLDataPropertyDomainAxiom(OWLDataPropertyExpression property,
        OWLClassExpression domain, Collection<OWLAnnotation> annotations);

    /**
     * @param prop prop
     * @param domain domain
     * @return an annotation property domain assertion
     */
    default OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(
        OWLAnnotationProperty prop, IRI domain) {
        return getOWLAnnotationPropertyDomainAxiom(prop, domain, Collections.emptySet());
    }

    /**
     * @param prop prop
     * @param domain domain
     * @param annotations A set of annotations. Cannot be null or contain nulls.
     * @return an annotation property domain assertion with annotations
     */
    OWLAnnotationPropertyDomainAxiom getOWLAnnotationPropertyDomainAxiom(OWLAnnotationProperty prop,
        IRI domain, Collection<OWLAnnotation> annotations);
}
