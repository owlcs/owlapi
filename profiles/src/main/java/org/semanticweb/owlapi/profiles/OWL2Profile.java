package org.semanticweb.owlapi.profiles;

import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDatatypeDefinitionAxiom;
import org.semanticweb.owlapi.model.OWLDatatypeRestriction;
import org.semanticweb.owlapi.model.OWLFacetRestriction;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyID;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.util.OWLOntologyWalker;
import org.semanticweb.owlapi.util.OWLOntologyWalkerVisitor;
import org.semanticweb.owlapi.vocab.OWL2Datatype;

/*
 * Copyright (C) 2008, University of Manchester
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
 * Information Management Group<br>
 * Date: 21-Jun-2008<br><br>
 * Checks to see if an ontology and its imports closure fall into the OWL 2 DL profile.
 * An ontology is OWL Full if any of the global structural restrictions are violated, if there is punning between
 * object and data properties
 */
public class OWL2Profile implements OWLProfile {

    public String getName() {
        return "OWL 2";
    }


    /**
     * Checks an ontology and its import closure to see if it is within
     * this profile.
     * @param ontology The ontology to be checked.
     * @param manager  A manager which can be used to obtain the imports closure
     *                 of the ontology
     * @return An <code>OWLProfileReport</code> that describes whether or not the
     *         ontology is within this profile.
     */
    public OWLProfileReport checkOntology(OWLOntology ontology) {
        OWLOntologyWalker walker = new OWLOntologyWalker(ontology.getImportsClosure());
        OWL2ProfileObjectWalker visitor = new OWL2ProfileObjectWalker(walker, ontology.getOWLOntologyManager());
        walker.walkStructure(visitor);
        Set<OWLProfileViolation> pv = visitor.getProfileViolations();
        return new OWLProfileReport(this, pv);
    }

    private static class OWL2ProfileObjectWalker extends OWLOntologyWalkerVisitor<Object> {

        private OWLOntologyManager man;

        private Set<OWLProfileViolation> profileViolations = new HashSet<OWLProfileViolation>();

        public OWL2ProfileObjectWalker(OWLOntologyWalker walker, OWLOntologyManager man) {
            super(walker);
            this.man = man;
        }

        public Set<OWLProfileViolation> getProfileViolations() {
            return new HashSet<OWLProfileViolation>(profileViolations);
        }

        @Override
		public Object visit(OWLOntology ont) {
            // The ontology IRI and version IRI must be absolute and must not be from the reserved vocab
            OWLOntologyID id = ont.getOntologyID();
            if (!id.isAnonymous()) {
                IRI ontologyIRI = id.getOntologyIRI();
                if (!ontologyIRI.isAbsolute()) {
                    profileViolations.add(new OntologyIRINotAbsolute(ont));
                }
                IRI versionIRI = id.getVersionIRI();
                if (versionIRI != null) {
                    if (!versionIRI.isAbsolute()) {
                        profileViolations.add(new OntologyVersionIRINotAbsolute(ont));
                    }
                }
            }
            return null;
        }

        @Override
		public Object visit(IRI iri) {
            if(!iri.isAbsolute()) {
                profileViolations.add(new UseOfNonAbsoluteIRI(getCurrentOntology(), getCurrentAxiom()));
            }
            return null;
        }

        @Override
		public Object visit(OWLLiteral node) {
            // Check that the lexical value of the literal is in the lexical space of the
            // literal datatype
            if (node.getDatatype().isBuiltIn()) {
                if (!node.getDatatype().getBuiltInDatatype().isInLexicalSpace(node.getLiteral())) {
                    profileViolations.add(new LexicalNotInLexicalSpace(getCurrentOntology(), getCurrentAxiom(), node));

                }
            }
            return null;
        }

        @Override
		public Object visit(OWLDatatypeRestriction node) {
            // The datatype should not be defined with a datatype definition axiom
            for(OWLOntology ont : man.getImportsClosure(getCurrentOntology())) {
                for(OWLDatatypeDefinitionAxiom ax : ont.getAxioms(AxiomType.DATATYPE_DEFINITION)) {
                    if(node.getDatatype().equals(ax.getDatatype())) {
                        profileViolations.add(new UseOfDefinedDatatypeInDatatypeRestriction(getCurrentOntology(), getCurrentAxiom(), node));
                    }
                }
            }

            // All facets must be allowed for the restricted datatype
            for (OWLFacetRestriction r : node.getFacetRestrictions()) {
                OWL2Datatype dt = node.getDatatype().getBuiltInDatatype();
                if (!dt.getFacets().contains(r.getFacet())) {
                    profileViolations.add(new UseOfIllegalFacetRestriction(getCurrentOntology(), getCurrentAxiom(), node, r.getFacet()));
                }
            }
            return null;
        }

        @Override
		public Object visit(OWLDatatypeDefinitionAxiom axiom) {
            // The datatype MUST be declared
            if (!getCurrentOntology().isDeclared(axiom.getDatatype(), true)) {
                profileViolations.add(new UseOfUndeclaredDatatype(getCurrentOntology(), axiom, axiom.getDatatype()));
            }
            return null;
        }

    }
}
