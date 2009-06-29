package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 28-Jul-2008<br><br>
 */
public class OntologyMutationTestCase extends AbstractOWLAPITestCase {

    public void testAddAxiom() throws Exception {
        OWLOntology ont = getOWLOntology("OntA");
        OWLAxiom ax = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getFactory().getOWLThing());
        final List<OWLOntologyChange> chgs = new ArrayList<OWLOntologyChange>();
        getManager().addOntologyChangeListener(new OWLOntologyChangeListener() {
            public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
                chgs.addAll(changes);
            }
        });
        getManager().addAxiom(ont, ax);
        assertTrue(chgs.size() == 1);
        assertTrue(chgs.contains(new AddAxiom(ont, ax)));
    }

    public void testAddAxioms() throws Exception {
        OWLOntology ont = getOWLOntology("OntB");
        OWLAxiom ax = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getFactory().getOWLThing());
        final List<OWLOntologyChange> chgs = new ArrayList<OWLOntologyChange>();
        getManager().addOntologyChangeListener(new OWLOntologyChangeListener() {
            public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
                chgs.addAll(changes);
            }
        });
        getManager().addAxioms(ont, Collections.singleton(ax));
        assertTrue(chgs.size() == 1);
        assertTrue(chgs.contains(new AddAxiom(ont, ax)));
    }

    public void testApplyChange() throws Exception {
        OWLOntology ont = getOWLOntology("OntC");
        OWLAxiom ax = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getFactory().getOWLThing());
        final List<OWLOntologyChange> chgs = new ArrayList<OWLOntologyChange>();
        getManager().addOntologyChangeListener(new OWLOntologyChangeListener() {
            public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
                chgs.addAll(changes);
            }
        });
        getManager().applyChange(new AddAxiom(ont, ax));
        assertTrue(chgs.size() == 1);
        assertTrue(chgs.contains(new AddAxiom(ont, ax)));
    }

    public void testApplyChanges() throws Exception {
        OWLOntology ont = getOWLOntology("OntD");
        OWLAxiom ax = getFactory().getOWLSubClassOfAxiom(getOWLClass("A"), getFactory().getOWLThing());
        final List<OWLOntologyChange> chgs = new ArrayList<OWLOntologyChange>();
        getManager().addOntologyChangeListener(new OWLOntologyChangeListener() {
            public void ontologiesChanged(List<? extends OWLOntologyChange> changes) throws OWLException {
                chgs.addAll(changes);
            }
        });
        getManager().applyChanges(Arrays.asList(new AddAxiom(ont, ax)));
        assertTrue(chgs.size() == 1);
        assertTrue(chgs.contains(new AddAxiom(ont, ax)));
    }


}
