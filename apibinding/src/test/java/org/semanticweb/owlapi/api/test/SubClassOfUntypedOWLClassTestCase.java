package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25/02/2011
 */
public class SubClassOfUntypedOWLClassTestCase extends AbstractFileTestCase {

    public static final IRI SUBCLASS_IRI = IRI.create("http://www.semanticweb.org/owlapi/test#A");

    public static final IRI SUPERCLASS_IRI = IRI.create("http://www.semanticweb.org/owlapi/test#B");

    @Override
    protected OWLOntologyLoaderConfiguration getConfiguration() {
        return super.getConfiguration().setStrict(false);
    }

    @Override
    protected String getFileName() {
        return "SubClassOfUntypedOWLClass.rdf";
    }

    public void testParsedAxioms() {
        OWLOntology ontology = createOntology();
        Set<OWLSubClassOfAxiom> axioms = ontology.getAxioms(AxiomType.SUBCLASS_OF);
        assertTrue(axioms.size() == 1);
        OWLSubClassOfAxiom ax = axioms.iterator().next();
        OWLClass subCls = getFactory().getOWLClass(SUBCLASS_IRI);
        OWLClass supCls = getFactory().getOWLClass(SUPERCLASS_IRI);
        assertEquals(subCls, ax.getSubClass());
        assertEquals(supCls, ax.getSuperClass());
        
    }
}
