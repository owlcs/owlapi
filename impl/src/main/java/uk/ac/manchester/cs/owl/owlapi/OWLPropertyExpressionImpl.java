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
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLNaryPropertyAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLPropertyDomainAxiom;
import org.semanticweb.owlapi.model.OWLPropertyExpression;
import org.semanticweb.owlapi.model.OWLPropertyRange;
import org.semanticweb.owlapi.model.OWLPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLSubPropertyAxiom;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLPropertyExpressionImpl<R extends OWLPropertyRange, P extends OWLPropertyExpression<R, P>> extends OWLObjectImpl implements OWLPropertyExpression<R, P> {

    public OWLPropertyExpressionImpl(OWLDataFactory dataFactory) {
        super(dataFactory);
    }


    public Set<R> getRanges(OWLOntology ontology) {
        Set<R> result = new TreeSet<R>();
        for (OWLPropertyRangeAxiom<P, R> axiom : getRangeAxioms(ontology)) {
            result.add(axiom.getRange());
        }
        return result;
    }


    public Set<R> getRanges(Set<OWLOntology> ontologies) {
        Set<R> result = new TreeSet<R>();
        for (OWLOntology ont : ontologies) {
            result.addAll(getRanges(ont));
        }
        return result;
    }


    public Set<OWLClassExpression> getDomains(OWLOntology ontology) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLPropertyDomainAxiom<?> axiom : getDomainAxioms(ontology)) {
            result.add(axiom.getDomain());
        }
        return result;
    }


    public Set<OWLClassExpression> getDomains(Set<OWLOntology> ontologies) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for (OWLOntology ont : ontologies) {
            result.addAll(getDomains(ont));
        }
        return result;
    }


    public Set<P> getSuperProperties(OWLOntology ontology) {
        Set<P> result = new TreeSet<P>();
        for (OWLSubPropertyAxiom<P> axiom : getSubPropertyAxioms(ontology)) {
            result.add(axiom.getSuperProperty());
        }
        return result;
    }


    public Set<P> getSuperProperties(Set<OWLOntology> ontologies) {
        Set<P> results = new TreeSet<P>();
        for (OWLOntology ont : ontologies) {
            results.addAll(getSuperProperties(ont));
        }
        return results;
    }


    public Set<P> getSubProperties(OWLOntology ontology) {
        Set<P> results = new TreeSet<P>();
        for (OWLSubPropertyAxiom<P> axiom : getSubPropertyAxiomsForRHS(ontology)) {
            results.add(axiom.getSubProperty());
        }
        return results;
    }


    public Set<P> getSubProperties(Set<OWLOntology> ontologies) {
        Set<P> result = new TreeSet<P>();
        for (OWLOntology ont : ontologies) {
            result.addAll(getSubProperties(ont));
        }
        return result;
    }


    protected abstract Set<? extends OWLSubPropertyAxiom<P>> getSubPropertyAxiomsForRHS(OWLOntology ont);


    public Set<P> getEquivalentProperties(OWLOntology ontology) {
        return getProperties(getEquivalentPropertiesAxioms(ontology));
    }


    public Set<P> getEquivalentProperties(Set<OWLOntology> ontologies) {
        Set<P> result = new TreeSet<P>();
        for (OWLOntology ont : ontologies) {
            result.addAll(getEquivalentProperties(ont));
        }
        return result;
    }


    public Set<P> getDisjointProperties(OWLOntology ontology) {
        return getProperties(getDisjointPropertiesAxioms(ontology));
    }


    public Set<P> getDisjointProperties(Set<OWLOntology> ontologies) {
        Set<P> result = new TreeSet<P>();
        for (OWLOntology ont : ontologies) {
            result.addAll(getDisjointProperties(ont));
        }
        return result;
    }


    protected abstract Set<? extends OWLPropertyDomainAxiom<?>> getDomainAxioms(OWLOntology ontology);

    protected abstract Set<? extends OWLPropertyRangeAxiom<P, R>> getRangeAxioms(OWLOntology ontology);

    protected abstract Set<? extends OWLSubPropertyAxiom<P>> getSubPropertyAxioms(OWLOntology ontology);

    protected abstract Set<? extends OWLNaryPropertyAxiom<P>> getEquivalentPropertiesAxioms(OWLOntology ontology);

    protected abstract Set<? extends OWLNaryPropertyAxiom<P>> getDisjointPropertiesAxioms(OWLOntology ontology);

    private <P extends OWLPropertyExpression<?,?>> Set<P> getProperties(Set<? extends OWLNaryPropertyAxiom<P>> axioms) {
        Set<P> result = new TreeSet<P>();
        for (OWLNaryPropertyAxiom<P> axiom : axioms) {
            result.addAll(axiom.getProperties());
        }
        result.remove(this);
        return result;
    }


    @Override
	public boolean equals(Object obj) {
        if (super.equals(obj)) {
            return obj instanceof OWLPropertyExpression;
        }
        return false;
    }

}
