package org.semanticweb.owlapi.profiles;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.OWL2Datatype;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.DefaultPrefixManager;
import org.semanticweb.owlapi.util.SimpleRenderer;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.ToStringRenderer;

import java.util.HashSet;
import java.util.Set;
import java.net.URI;
/*
 * Copyright (C) 2009, University of Manchester
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
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 03-Aug-2009
 */
public class OWL2RLProfile implements OWLProfile {

    private Set<IRI> allowedDatatypes = new HashSet<IRI>();

    public OWL2RLProfile() {
        allowedDatatypes.add(OWLRDFVocabulary.RDF_PLAIN_LITERAL.getIRI());
        allowedDatatypes.add(OWLRDFVocabulary.RDF_XML_LITERAL.getIRI());
        allowedDatatypes.add(OWLRDFVocabulary.RDFS_LITERAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DECIMAL.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_INTEGER.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NON_NEGATIVE_INTEGER.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NON_POSITIVE_INTEGER.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_POSITIVE_INTEGER.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NEGATIVE_INTEGER.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_LONG.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_INT.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_SHORT.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_BYTE.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_UNSIGNED_LONG.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_UNSIGNED_BYTE.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_FLOAT.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DOUBLE.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_STRING.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NORMALIZED_STRING.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_TOKEN.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_LANGUAGE.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NAME.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NCNAME.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_NMTOKEN.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_BOOLEAN.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_HEX_BINARY.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_BASE_64_BINARY.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_ANY_URI.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DATE_TIME.getIRI());
        allowedDatatypes.add(OWL2Datatype.XSD_DATE_TIME_STAMP.getIRI());
    }

    /**
     * Gets the name of the profile.
     * @return A string that represents the name of the profile
     */
    public String getName() {
        return "OWL 2 RL";
    }

    /**
     * Checks an ontology and its import closure to see if it is within
     * this profile.
     * @param ontology The ontology to be checked.
     * @return An <code>OWLProfileReport</code> that describes whether or not the
     *         ontology is within this profile.
     */
    public OWLProfileReport checkOntology(OWLOntology ontology) {
        OWL2DLProfile profile = new OWL2DLProfile();
        OWLProfileReport report = profile.checkOntology(ontology);
        Set<OWLProfileViolation> violations = new HashSet<OWLProfileViolation>();
        violations.addAll(report.getViolations());

        OWLOntologyWalker walker = new OWLOntologyWalker(ontology.getImportsClosure());
        OWL2RLObjectVisitor visitor = new OWL2RLObjectVisitor(walker);
        walker.walkStructure(visitor);
        violations.addAll(visitor.getProfileViolations());
        return new OWLProfileReport(this, violations);
    }


    private class OWL2RLObjectVisitor extends OWLOntologyWalkerVisitor {

        private Set<OWLProfileViolation> profileViolations = new HashSet<OWLProfileViolation>();

        private OWL2RLObjectVisitor(OWLOntologyWalker walker) {
            super(walker);
        }

        public Set<OWLProfileViolation> getProfileViolations() {
            return new HashSet<OWLProfileViolation>(profileViolations);
        }

        public Object visit(OWLClassAssertionAxiom axiom) {
            if(!isOWL2RLSuperClassExpression(axiom.getClassExpression())) {
                profileViolations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom, axiom.getClassExpression()));
            }
            return null;
        }

        public Object visit(OWLDataPropertyDomainAxiom axiom) {
            if(!isOWL2RLSuperClassExpression(axiom.getDomain())) {
                profileViolations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom, axiom.getDomain()));
            }
            return null;
        }

        public Object visit(OWLDisjointClassesAxiom axiom) {
            for(OWLClassExpression ce : axiom.getClassExpressions()) {
                if(!isOWL2RLSubClassExpression(ce)) {
                    profileViolations.add(new UseOfNonSubClassExpression(getCurrentOntology(), axiom, ce));
                }
            }
            return null;
        }

        public Object visit(OWLDisjointDataPropertiesAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        public Object visit(OWLDisjointUnionAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        public Object visit(OWLEquivalentClassesAxiom axiom) {
            for(OWLClassExpression ce : axiom.getClassExpressions()) {
                if(!isOWL2RLEquivalentClassExpression(ce)) {
                    profileViolations.add(new UseOfNonEquivalentClassExpression(getCurrentOntology(), axiom, ce));
                }
            }
            return null;
        }

        public Object visit(OWLEquivalentDataPropertiesAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        public Object visit(OWLFunctionalDataPropertyAxiom axiom) {
            return null;
        }

        public Object visit(OWLHasKeyAxiom axiom) {
            if(!isOWL2RLSubClassExpression(axiom.getClassExpression())) {
                profileViolations.add(new UseOfNonSubClassExpression(getCurrentOntology(), axiom, axiom.getClassExpression()));
            }
            return null;
        }

        public Object visit(OWLObjectPropertyDomainAxiom axiom) {
            if(!isOWL2RLSuperClassExpression(axiom.getDomain())) {
                profileViolations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom, axiom.getDomain()));
            }
            return null;
        }

        public Object visit(OWLObjectPropertyRangeAxiom axiom) {
            if(!isOWL2RLSuperClassExpression(axiom.getRange())) {
                profileViolations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom, axiom.getRange()));
            }
            return null;
        }

        public Object visit(OWLSubClassOfAxiom axiom) {
            if(!isOWL2RLSubClassExpression(axiom.getSubClass())) {
                profileViolations.add(new UseOfNonSubClassExpression(getCurrentOntology(), axiom, axiom.getSubClass()));
            }
            if(!isOWL2RLSuperClassExpression(axiom.getSuperClass())) {
                profileViolations.add(new UseOfNonSuperClassExpression(getCurrentOntology(), axiom, axiom.getSuperClass()));
            }
            return null;
        }

        public Object visit(OWLSubDataPropertyOfAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), axiom));
            return null;
        }

        public Object visit(SWRLRule rule) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), rule));
            return super.visit(rule);
        }

        public Object visit(OWLDataComplementOf node) {
            return super.visit(node);
        }

        public Object visit(OWLDataIntersectionOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        public Object visit(OWLDataOneOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        public Object visit(OWLDatatype node) {
            if(!allowedDatatypes.contains(node.getIRI())) {
                profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            }
            return null;
        }

        public Object visit(OWLDatatypeRestriction node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        public Object visit(OWLDataUnionOf node) {
            profileViolations.add(new UseOfIllegalDataRange(getCurrentOntology(), getCurrentAxiom(), node));
            return null;
        }

        public Object visit(OWLDatatypeDefinitionAxiom axiom) {
            profileViolations.add(new UseOfIllegalAxiom(getCurrentOntology(), getCurrentAxiom()));
            return null;
        }
    }


    private class OWL2RLSubClassExpressionChecker implements OWLClassExpressionVisitorEx<Boolean> {

        public Boolean visit(OWLClass desc) {
            return !desc.isOWLThing();
        }

        public Boolean visit(OWLObjectIntersectionOf desc) {
            for (OWLClassExpression op : desc.getOperands()) {
                if (!isOWL2RLSubClassExpression(op)) {
                    return false;
                }
            }
            return true;
        }

        public Boolean visit(OWLObjectUnionOf desc) {
            for (OWLClassExpression op : desc.getOperands()) {
                if (!isOWL2RLSubClassExpression(op)) {
                    return false;
                }
            }
            return true;
        }

        public Boolean visit(OWLObjectComplementOf desc) {
            return false;
        }

        public Boolean visit(OWLObjectSomeValuesFrom desc) {
            return desc.getFiller().isOWLThing() || isOWL2RLSubClassExpression(desc.getFiller());
        }

        public Boolean visit(OWLObjectAllValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLObjectHasValue desc) {
            return true;
        }

        public Boolean visit(OWLObjectMinCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectMaxCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectHasSelf desc) {
            return false;
        }

        public Boolean visit(OWLObjectOneOf desc) {
            return true;
        }

        public Boolean visit(OWLDataSomeValuesFrom desc) {
            return true;
        }

        public Boolean visit(OWLDataAllValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLDataHasValue desc) {
            return true;
        }

        public Boolean visit(OWLDataMinCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataMaxCardinality desc) {
            return false;
        }
    }


    private OWL2RLSubClassExpressionChecker subClassExpressionChecker = new OWL2RLSubClassExpressionChecker();

    private boolean isOWL2RLSubClassExpression(OWLClassExpression ce) {
        return ce.accept(subClassExpressionChecker);
    }

    private class OWL2RLSuperClassExpressionChecker implements OWLClassExpressionVisitorEx<Boolean> {

        public Boolean visit(OWLClass desc) {
            return !desc.isOWLThing();
        }

        public Boolean visit(OWLObjectIntersectionOf desc) {
            for (OWLClassExpression ce : desc.getOperands()) {
                if (!ce.accept(this)) {
                    return false;
                }
            }
            return true;
        }

        public Boolean visit(OWLObjectUnionOf desc) {
            return false;
        }

        public Boolean visit(OWLObjectComplementOf desc) {
            return isOWL2RLSubClassExpression(desc.getOperand());
        }

        public Boolean visit(OWLObjectSomeValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLObjectAllValuesFrom desc) {
            return desc.getFiller().accept(this);
        }

        public Boolean visit(OWLObjectHasValue desc) {
            return true;
        }

        public Boolean visit(OWLObjectMinCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectMaxCardinality desc) {
            return (desc.getCardinality() == 0 || desc.getCardinality() == 1) && desc.getFiller().isOWLThing();
        }

        public Boolean visit(OWLObjectHasSelf desc) {
            return false;
        }

        public Boolean visit(OWLObjectOneOf desc) {
            return false;
        }

        public Boolean visit(OWLDataSomeValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLDataAllValuesFrom desc) {
            return true;
        }

        public Boolean visit(OWLDataHasValue desc) {
            return true;
        }

        public Boolean visit(OWLDataMinCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataMaxCardinality desc) {
            return (desc.getCardinality() == 0 || desc.getCardinality() == 1);
        }
    }

    private OWL2RLSuperClassExpressionChecker superClassExpressionChecker = new OWL2RLSuperClassExpressionChecker();

    public boolean isOWL2RLSuperClassExpression(OWLClassExpression ce) {
        return ce.accept(superClassExpressionChecker);
    }


    private class OWL2RLEquivalentClassExpressionChecker implements OWLClassExpressionVisitorEx<Boolean> {

        public Boolean visit(OWLClass desc) {
            return !desc.isOWLThing();
        }

        public Boolean visit(OWLObjectIntersectionOf desc) {
            for(OWLClassExpression ce : desc.getOperands()) {
                if(!ce.accept(this)) {
                    return false;
                }
            }
            return true;
        }

        public Boolean visit(OWLObjectUnionOf desc) {
            return false;
        }

        public Boolean visit(OWLObjectComplementOf desc) {
            return false;
        }

        public Boolean visit(OWLObjectSomeValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLObjectAllValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLObjectHasValue desc) {
            return true;
        }

        public Boolean visit(OWLObjectMinCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectMaxCardinality desc) {
            return false;
        }

        public Boolean visit(OWLObjectHasSelf desc) {
            return false;
        }

        public Boolean visit(OWLObjectOneOf desc) {
            return false;
        }

        public Boolean visit(OWLDataSomeValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLDataAllValuesFrom desc) {
            return false;
        }

        public Boolean visit(OWLDataHasValue desc) {
            return true;
        }

        public Boolean visit(OWLDataMinCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataExactCardinality desc) {
            return false;
        }

        public Boolean visit(OWLDataMaxCardinality desc) {
            return false;
        }
    }

    private OWL2RLEquivalentClassExpressionChecker equivalentClassExpressionChecker = new OWL2RLEquivalentClassExpressionChecker();

    public boolean isOWL2RLEquivalentClassExpression(OWLClassExpression ce) {
        return ce.accept(equivalentClassExpressionChecker);
    }


}
