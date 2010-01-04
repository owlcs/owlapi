package uk.ac.manchester.cs.owl.owlapi;

import org.semanticweb.owlapi.model.*;

import java.util.Set;
import java.util.TreeSet;
/*
 * Copyright (C) 2006, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 26-Oct-2006<br><br>
 */
public abstract class OWLPropertyExpressionImpl<P extends OWLPropertyExpression, R extends OWLPropertyRange> extends OWLObjectImpl implements OWLPropertyExpression<P, R> {

    public OWLPropertyExpressionImpl(OWLDataFactory dataFactory) {
        super(dataFactory);
    }


    public Set<R> getRanges(OWLOntology ontology) {
        Set<R> result = new TreeSet<R>();
        for(OWLPropertyRangeAxiom<P, R> axiom : getRangeAxioms(ontology)) {
            result.add(axiom.getRange());
        }
        return result;
    }


    public Set<R> getRanges(Set<OWLOntology> ontologies) {
        Set<R> result = new TreeSet<R>();
        for(OWLOntology ont : ontologies) {
            result.addAll(getRanges(ont));
        }
        return result;
    }


    public Set<OWLClassExpression> getDomains(OWLOntology ontology) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for(OWLPropertyDomainAxiom axiom : getDomainAxioms(ontology)) {
            result.add(axiom.getDomain());
        }
        return result;
    }


    public Set<OWLClassExpression> getDomains(Set<OWLOntology> ontologies) {
        Set<OWLClassExpression> result = new TreeSet<OWLClassExpression>();
        for(OWLOntology ont : ontologies) {
            result.addAll(getDomains(ont));
        }
        return result;
    }


    public Set<P> getSuperProperties(OWLOntology ontology) {
        Set<P> result = new TreeSet<P>();
        for(OWLSubPropertyAxiom<P> axiom : getSubPropertyAxioms(ontology)) {
            result.add(axiom.getSuperProperty());
        }
        return result;
    }


    public Set<P> getSuperProperties(Set<OWLOntology> ontologies) {
        Set<P> results = new TreeSet<P>();
        for(OWLOntology ont : ontologies) {
            results.addAll(getSuperProperties(ont));
        }
        return results;
    }


    public Set<P> getSubProperties(OWLOntology ontology) {
        Set<P> results = new TreeSet<P>();
        for(OWLSubPropertyAxiom<P> axiom : getSubPropertyAxiomsForRHS(ontology)) {
            results.add(axiom.getSubProperty());
        }
        return results;
    }


    public Set<P> getSubProperties(Set<OWLOntology> ontologies) {
        Set<P> result = new TreeSet<P>();
        for(OWLOntology ont : ontologies) {
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
        for(OWLOntology ont : ontologies) {
            result.addAll(getEquivalentProperties(ont));
        }
        return result;
    }


    public Set<P> getDisjointProperties(OWLOntology ontology) {
        return getProperties(getDisjointPropertiesAxioms(ontology));
    }


    public Set<P> getDisjointProperties(Set<OWLOntology> ontologies) {
        Set<P> result = new TreeSet<P>();
        for(OWLOntology ont : ontologies) {
            result.addAll(getDisjointProperties(ont));
        }
        return result;
    }

    
    protected abstract Set<? extends OWLPropertyDomainAxiom> getDomainAxioms(OWLOntology ontology);

    protected abstract Set<? extends OWLPropertyRangeAxiom<P, R>> getRangeAxioms(OWLOntology ontology);

    protected abstract Set<? extends OWLSubPropertyAxiom<P>> getSubPropertyAxioms(OWLOntology ontology);

    protected abstract Set<? extends OWLNaryPropertyAxiom<P>> getEquivalentPropertiesAxioms(OWLOntology ontology);

    protected abstract Set<? extends OWLNaryPropertyAxiom<P>> getDisjointPropertiesAxioms(OWLOntology ontology);

    private <P extends OWLPropertyExpression> Set<P> getProperties(Set<? extends OWLNaryPropertyAxiom<P>> axioms) {
        Set<P> result = new TreeSet<P>();
        for(OWLNaryPropertyAxiom<P> axiom : axioms) {
            result.addAll(axiom.getProperties());
        }
        result.remove(this);
        return result;
    }


    public boolean equals(Object obj) {
        if(super.equals(obj)) {
            return obj instanceof OWLPropertyExpression;
        }
        return false;
    }

}
