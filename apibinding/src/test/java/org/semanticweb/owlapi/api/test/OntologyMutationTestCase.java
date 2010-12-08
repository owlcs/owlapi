package org.semanticweb.owlapi.api.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.OWLOntologyChangeListener;


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
