package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLOntologyManager;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 16-Mar-2007<br><br>
 */
public abstract class AbstractOWLOntologyManagerTestCase extends AbstractOWLDataFactoryTest {

    public static final String SYSTEM_PARAM_NAME = "OntologyManagerFactory";

    private OWLOntologyManager manager;

    @Override
	protected void setUp() throws Exception {
        super.setUp();
        String factoryName = System.getProperty(SYSTEM_PARAM_NAME);
        if (factoryName == null) {
            throw new RuntimeException("System property '" + SYSTEM_PARAM_NAME + " must be set in order to run the tests");
        }
        Class<?> cls = Class.forName(factoryName);
        OWLOntologyManagerFactory factory = (OWLOntologyManagerFactory) cls.newInstance();
        manager = factory.createOWLOntologyManager(getFactory());
    }


    public OWLOntologyManager getOWLOntologyManager() {
        return manager;
    }


    @Override
	public void testCreation() throws Exception {
        // Ignore
    }


    @Override
	public void testEqualsPositive() throws Exception {
        // Ignore
    }


    @Override
	public void testEqualsNegative() throws Exception {
        // Ignore
    }


    @Override
	public void testHashCode() throws Exception {
        // Ignore
    }
}
