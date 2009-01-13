package org.semanticweb.owl.util;

import org.semanticweb.owl.model.*;

import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
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
 * Date: 10-Feb-2008<br><br>
 *
 * Provides the object that is the subject of an axiom.
 */
public class AxiomSubjectProvider implements OWLAxiomVisitor {

    private OWLObject subject;

    public OWLObject getSubject(OWLAxiom axiom) {
        axiom.accept(this);
        return subject;
    }


    public void visit(OWLSubClassAxiom axiom) {
        subject = axiom.getSubClass();
    }


    public void visit(OWLNegativeObjectPropertyAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }


    public void visit(OWLAntiSymmetricObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLReflexiveObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }

    private OWLDescription selectDescription(Set<OWLDescription> descs) {
        for(OWLDescription desc : descs) {
            if(!desc.isAnonymous()) {
               return desc;
            }
        }
        return descs.iterator().next();
    }


    public void visit(OWLDisjointClassesAxiom axiom) {
        subject = selectDescription(axiom.getDescriptions());
    }


    public void visit(OWLDataPropertyDomainAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLImportsDeclaration axiom) {
        subject = axiom.getSubject();
    }


    public void visit(OWLAxiomAnnotationAxiom axiom) {
        subject = axiom.getSubject();
    }


    public void visit(OWLObjectPropertyDomainAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLEquivalentObjectPropertiesAxiom axiom) {
        subject = axiom.getProperties().iterator().next();
    }


    public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }


    public void visit(OWLDifferentIndividualsAxiom axiom) {
        subject = axiom.getIndividuals().iterator().next();
    }


    public void visit(OWLDisjointDataPropertiesAxiom axiom) {
        subject = axiom.getProperties().iterator().next();
    }


    public void visit(OWLDisjointObjectPropertiesAxiom axiom) {
        subject = axiom.getProperties().iterator().next();
    }


    public void visit(OWLObjectPropertyRangeAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLObjectPropertyAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }


    public void visit(OWLFunctionalObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLObjectSubPropertyAxiom axiom) {
        subject = axiom.getSubProperty();
    }


    public void visit(OWLDisjointUnionAxiom axiom) {
        subject = axiom.getOWLClass();
    }


    public void visit(OWLDeclarationAxiom axiom) {
        subject = axiom.getEntity();
    }


    public void visit(OWLEntityAnnotationAxiom axiom) {
        subject = axiom.getSubject();
    }


    public void visit(OWLOntologyAnnotationAxiom axiom) {
        subject = axiom.getSubject();
    }


    public void visit(OWLSymmetricObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLDataPropertyRangeAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLFunctionalDataPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLEquivalentDataPropertiesAxiom axiom) {
        subject = axiom.getProperties().iterator().next();
    }


    public void visit(OWLClassAssertionAxiom axiom) {
        subject = axiom.getIndividual();
    }


    public void visit(OWLEquivalentClassesAxiom axiom) {
        subject = selectDescription(axiom.getDescriptions());
    }


    public void visit(OWLDataPropertyAssertionAxiom axiom) {
        subject = axiom.getSubject();
    }


    public void visit(OWLTransitiveObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLIrreflexiveObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLDataSubPropertyAxiom axiom) {
        subject = axiom.getSubProperty();
    }


    public void visit(OWLInverseFunctionalObjectPropertyAxiom axiom) {
        subject = axiom.getProperty();
    }


    public void visit(OWLSameIndividualsAxiom axiom) {
        subject = axiom.getIndividuals().iterator().next();
    }


    public void visit(OWLObjectPropertyChainSubPropertyAxiom axiom) {
        subject = axiom.getSuperProperty();
    }


    public void visit(OWLInverseObjectPropertiesAxiom axiom) {
        subject = axiom.getFirstProperty();
    }


    public void visit(SWRLRule rule) {
        subject = rule.getHead().iterator().next();
    }
}
