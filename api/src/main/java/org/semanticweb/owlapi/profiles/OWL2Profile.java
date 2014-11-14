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
package org.semanticweb.owlapi.profiles;

import static org.semanticweb.owlapi.model.parameters.Imports.INCLUDED;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.profiles.violations.LexicalNotInLexicalSpace;
import org.semanticweb.owlapi.profiles.violations.OntologyIRINotAbsolute;
import org.semanticweb.owlapi.profiles.violations.OntologyVersionIRINotAbsolute;
import org.semanticweb.owlapi.profiles.violations.UseOfDefinedDatatypeInDatatypeRestriction;
import org.semanticweb.owlapi.profiles.violations.UseOfIllegalFacetRestriction;
import org.semanticweb.owlapi.profiles.violations.UseOfNonAbsoluteIRI;
import org.semanticweb.owlapi.profiles.violations.UseOfUndeclaredDatatype;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/**
 * Checks to see if an ontology and its imports closure fall into the OWL 2 DL
 * profile. An ontology is OWL Full if any of the global structural restrictions
 * are violated, if there is punning between object and data properties
 * 
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group
 */
public class OWL2Profile implements OWLProfile {

    @Override
    public String getName() {
        return "OWL 2";
    }

    @Nonnull
    @Override
    public IRI getIRI() {
        return Profiles.OWL2_FULL.getIRI();
    }

    /**
     * Checks an ontology and its import closure to see if it is within this
     * profile.
     * 
     * @param ontology
     *        The ontology to be checked.
     * @return An {@code OWLProfileReport} that describes whether or not the
     *         ontology is within this profile.
     */
    @Override
    public OWLProfileReport checkOntology(OWLOntology ontology) {
        OWLOntologyProfileWalker walker = new OWLOntologyProfileWalker(
                ontology.importsClosure());
        OWL2ProfileObjectWalker visitor = new OWL2ProfileObjectWalker(walker);
        walker.walkStructure(visitor);
        Set<OWLProfileViolation> pv = visitor.getProfileViolations();
        return new OWLProfileReport(this, pv);
    }

    private static class OWL2ProfileObjectWalker extends
            OWLOntologyWalkerVisitor {

        @Nonnull
        private final Set<OWLProfileViolation> profileViolations = new HashSet<>();

        OWL2ProfileObjectWalker(@Nonnull OWLOntologyWalker walker) {
            super(walker);
        }

        public Set<OWLProfileViolation> getProfileViolations() {
            return new HashSet<>(profileViolations);
        }

        @Override
        public void visit(OWLOntology ontology) {
            // The ontology IRI and version IRI must be absolute and must not be
            // from the reserved vocab
            OWLOntologyID id = ontology.getOntologyID();
            if (!id.isAnonymous()) {
                IRI ontologyIRI = id.getOntologyIRI().get();
                if (!ontologyIRI.isAbsolute()) {
                    profileViolations.add(new OntologyIRINotAbsolute(ontology));
                }
                Optional<IRI> versionIRI = id.getVersionIRI();
                if (versionIRI.isPresent()) {
                    if (!versionIRI.get().isAbsolute()) {
                        profileViolations
                                .add(new OntologyVersionIRINotAbsolute(ontology));
                    }
                }
            }
        }

        @Override
        public void visit(IRI iri) {
            if (!iri.isAbsolute()) {
                profileViolations.add(new UseOfNonAbsoluteIRI(
                        getCurrentOntology(), getCurrentAxiom(), iri));
            }
        }

        @Override
        public void visit(OWLLiteral node) {
            // Check that the lexical value of the literal is in the lexical
            // space of the literal datatype
            if (node.getDatatype().isBuiltIn()) {
                if (!node.getDatatype().getBuiltInDatatype()
                        .isInLexicalSpace(node.getLiteral())) {
                    profileViolations.add(new LexicalNotInLexicalSpace(
                            getCurrentOntology(), getCurrentAxiom(), node));
                }
            }
        }

        @Override
        public void visit(OWLDatatypeRestriction node) {
            // The datatype should not be defined with a datatype definition
            // axiom
            OWLDatatype datatype = node.getDatatype();
            getCurrentOntology()
                    .importsClosure()
                    .flatMap(o -> o.axioms(AxiomType.DATATYPE_DEFINITION))
                    .filter(ax -> datatype.equals(ax.getDatatype()))
                    .forEach(
                            ax -> profileViolations
                                    .add(new UseOfDefinedDatatypeInDatatypeRestriction(
                                            getCurrentOntology(),
                                            getCurrentAxiom(), node)));
            // All facets must be allowed for the restricted datatype
            node.facetRestrictions().forEach(
                    r -> {
                        OWL2Datatype dt = datatype.getBuiltInDatatype();
                        if (!dt.getFacets().contains(r.getFacet())) {
                            profileViolations
                                    .add(new UseOfIllegalFacetRestriction(
                                            getCurrentOntology(),
                                            getCurrentAxiom(), node, r
                                                    .getFacet()));
                        }
                    });
        }

        @Override
        public void visit(OWLDatatypeDefinitionAxiom axiom) {
            // The datatype MUST be declared
            if (!getCurrentOntology().isDeclared(axiom.getDatatype(), INCLUDED)) {
                profileViolations.add(new UseOfUndeclaredDatatype(
                        getCurrentOntology(), axiom, axiom.getDatatype()));
            }
        }
    }
}
