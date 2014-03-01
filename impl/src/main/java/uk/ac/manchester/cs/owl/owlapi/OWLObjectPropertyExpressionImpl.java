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
package uk.ac.manchester.cs.owl.owlapi;

import java.util.Set;
import java.util.TreeSet;

import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLInverseObjectPropertiesAxiom;
import org.semanticweb.owlapi.model.OWLNaryPropertyAxiom;
import org.semanticweb.owlapi.model.OWLObjectInverseOf;
import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyAxiom;
import org.semanticweb.owlapi.util.ObjectPropertySimplifier;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics
 *         Group, Date: 26-Oct-2006
 */
public abstract class OWLObjectPropertyExpressionImpl
        extends
        OWLPropertyExpressionImpl<OWLClassExpression, OWLObjectPropertyExpression>
        implements OWLObjectPropertyExpression {

    private static final long serialVersionUID = 30406L;
    private OWLObjectPropertyExpression simplestForm;
    private OWLObjectPropertyExpression inverse;

    @Override
    protected Set<? extends OWLPropertyDomainAxiom<?>> getDomainAxioms(
            OWLOntology ontology) {
        return ontology.getObjectPropertyDomainAxioms(this);
    }

    @Override
    public boolean isObjectPropertyExpression() {
        return true;
    }

    @Override
    public boolean isDataPropertyExpression() {
        return false;
    }

    @Override
    public boolean isFunctional(OWLOntology ontology) {
        return ontology.getFunctionalObjectPropertyAxioms(this).size() > 0;
    }

    @Override
    public boolean isFunctional(Set<OWLOntology> ontologies) {
        for (OWLOntology ont : ontologies) {
            if (isFunctional(ont)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isInverseFunctional(OWLOntology ontology) {
        return !ontology.getInverseFunctionalObjectPropertyAxioms(this)
                .isEmpty();
    }

    @Override
    public boolean isInverseFunctional(Set<OWLOntology> ontologies) {
        for (OWLOntology ont : ontologies) {
            if (isInverseFunctional(ont)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isSymmetric(OWLOntology ontology) {
        return !ontology.getSymmetricObjectPropertyAxioms(this).isEmpty();
    }

    @Override
    public boolean isSymmetric(Set<OWLOntology> ontologies) {
        for (OWLOntology ont : ontologies) {
            if (isSymmetric(ont)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isAsymmetric(OWLOntology ontology) {
        return !ontology.getAsymmetricObjectPropertyAxioms(this).isEmpty();
    }

    @Override
    public boolean isAsymmetric(Set<OWLOntology> ontologies) {
        for (OWLOntology ont : ontologies) {
            if (isAsymmetric(ont)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReflexive(OWLOntology ontology) {
        return !ontology.getReflexiveObjectPropertyAxioms(this).isEmpty();
    }

    @Override
    public boolean isReflexive(Set<OWLOntology> ontologies) {
        for (OWLOntology ont : ontologies) {
            if (isReflexive(ont)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isIrreflexive(OWLOntology ontology) {
        return !ontology.getIrreflexiveObjectPropertyAxioms(this).isEmpty();
    }

    @Override
    public boolean isIrreflexive(Set<OWLOntology> ontologies) {
        for (OWLOntology ont : ontologies) {
            if (isIrreflexive(ont)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isTransitive(OWLOntology ontology) {
        return !ontology.getTransitiveObjectPropertyAxioms(this).isEmpty();
    }

    @Override
    public boolean isTransitive(Set<OWLOntology> ontologies) {
        for (OWLOntology ont : ontologies) {
            if (isTransitive(ont)) {
                return true;
            }
        }
        return false;
    }

    @Override
    protected
            Set<? extends OWLPropertyRangeAxiom<OWLObjectPropertyExpression, OWLClassExpression>>
            getRangeAxioms(OWLOntology ontology) {
        return ontology.getObjectPropertyRangeAxioms(this);
    }

    @Override
    protected Set<? extends OWLSubPropertyAxiom<OWLObjectPropertyExpression>>
            getSubPropertyAxioms(OWLOntology ontology) {
        return ontology.getObjectSubPropertyAxiomsForSubProperty(this);
    }

    @Override
    protected Set<? extends OWLNaryPropertyAxiom<OWLObjectPropertyExpression>>
            getEquivalentPropertiesAxioms(OWLOntology ontology) {
        return ontology.getEquivalentObjectPropertiesAxioms(this);
    }

    @Override
    protected Set<? extends OWLNaryPropertyAxiom<OWLObjectPropertyExpression>>
            getDisjointPropertiesAxioms(OWLOntology ontology) {
        return ontology.getDisjointObjectPropertiesAxioms(this);
    }

    @Override
    public Set<OWLObjectPropertyExpression> getInverses(OWLOntology ontology) {
        Set<OWLObjectPropertyExpression> result = new TreeSet<OWLObjectPropertyExpression>();
        for (OWLInverseObjectPropertiesAxiom ax : ontology
                .getInverseObjectPropertyAxioms(this)) {
            if (ax.getFirstProperty().equals(this)) {
                result.add(ax.getSecondProperty());
            } else {
                result.add(ax.getFirstProperty());
            }
        }
        return result;
    }

    @Override
    public Set<OWLObjectPropertyExpression> getInverses(
            Set<OWLOntology> ontologies) {
        Set<OWLObjectPropertyExpression> result = new TreeSet<OWLObjectPropertyExpression>();
        for (OWLOntology ont : ontologies) {
            result.addAll(getInverses(ont));
        }
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj) && obj instanceof OWLObjectPropertyExpression;
    }

    @Override
    public OWLObjectPropertyExpression getSimplified() {
        if (simplestForm == null) {
            ObjectPropertySimplifier simplifier = new ObjectPropertySimplifier(
                    new OWLDataFactoryImpl());
            simplestForm = simplifier.getSimplified(this);
        }
        return simplestForm;
    }

    @Override
    public OWLObjectPropertyExpression getInverseProperty() {
        if (inverse == null) {
            inverse = new OWLObjectInverseOfImpl(this);
        }
        return inverse;
    }

    @Override
    public OWLObjectProperty getNamedProperty() {
        OWLObjectPropertyExpression simp = getSimplified();
        if (simp.isAnonymous()) {
            return ((OWLObjectInverseOf) simp).getInverse()
                    .asOWLObjectProperty();
        } else {
            return simp.asOWLObjectProperty();
        }
    }
}
