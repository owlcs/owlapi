package org.semanticweb.owlapi.api.test;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntaxOntologyFormat;
import org.coode.owlapi.turtle.TurtleOntologyFormat;
import org.semanticweb.owlapi.io.OWLFunctionalSyntaxOntologyFormat;
import org.semanticweb.owlapi.io.OWLXMLOntologyFormat;
import org.semanticweb.owlapi.io.StringDocumentTarget;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyFormat;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-May-2008<br><br>
 */
public abstract class AbstractRoundTrippingTest extends AbstractOWLAPITestCase {

    private OWLOntology ont;

    protected abstract OWLOntology createOntology() throws Exception;

    protected OWLOntology getOnt() {
        return ont;
    }

    @Override
	protected void setUp() throws Exception {
    	super.setUp();
        ont = createOntology();
    }

    public void testRDFXML() throws Exception {
        roundTripOntology(ont);
    }

    public void testOWLXML() throws Exception {
        roundTripOntology(ont, new OWLXMLOntologyFormat());
    }

    public void testFunctionalSyntax() throws Exception {
        roundTripOntology(ont, new OWLFunctionalSyntaxOntologyFormat());
    }

    public void testTurtle() throws Exception {
        roundTripOntology(ont, new TurtleOntologyFormat());
    }

    public void testManchesterOWLSyntax() throws Exception {
        roundTripOntology(ont, new ManchesterOWLSyntaxOntologyFormat());
    }

    @Override
	protected void handleSaved(StringDocumentTarget target, OWLOntologyFormat format) {
        super.handleSaved(target, format);
    }

    @Override
    protected boolean isIgnoreDeclarationAxioms(OWLOntologyFormat format) {
        return format instanceof ManchesterOWLSyntaxOntologyFormat;
    }
}
