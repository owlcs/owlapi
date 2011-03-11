package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.*;

import java.util.Set;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 11/03/2011
 */
public class SubClassOfUntypedSomeValuesFromTestCase extends AbstractFileTestCase {

    @Override
    protected String getFileName() {
        return "SubClassOfUntypedSomeValuesFrom.rdf";
    }

    public static final IRI SUBCLASS_IRI = IRI.create("http://www.semanticweb.org/owlapi/test#A");

    public static final IRI PROPERTY_IRI = IRI.create("http://www.semanticweb.org/owlapi/test#P");

    public static final IRI FILLER_IRI = IRI.create("http://www.semanticweb.org/owlapi/test#C");


    @Override
    protected OWLOntologyLoaderConfiguration getConfiguration() {
        return super.getConfiguration().setStrict(false);
    }

    public void testParsedAxioms() {
        OWLOntology ontology = createOntology();
        Set<OWLSubClassOfAxiom> axioms = ontology.getAxioms(AxiomType.SUBCLASS_OF);
        assertTrue(axioms.size() == 1);
        OWLSubClassOfAxiom ax = axioms.iterator().next();
        OWLClass subCls = getFactory().getOWLClass(SUBCLASS_IRI);
        assertEquals(subCls, ax.getSubClass());
        OWLClassExpression supCls = ax.getSuperClass();
        assertTrue(supCls instanceof OWLObjectSomeValuesFrom);
        OWLObjectSomeValuesFrom someValuesFrom = (OWLObjectSomeValuesFrom) supCls;
        OWLObjectProperty property = getFactory().getOWLObjectProperty(PROPERTY_IRI);
        OWLClass fillerCls = getFactory().getOWLClass(FILLER_IRI);
        assertEquals(property, someValuesFrom.getProperty());
        assertEquals(fillerCls, someValuesFrom.getFiller());

    }
}
