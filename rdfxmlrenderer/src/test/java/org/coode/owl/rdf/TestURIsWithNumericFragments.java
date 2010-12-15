package org.coode.owl.rdf;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLRuntimeException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Jul-2007<br><br>
 */
public class TestURIsWithNumericFragments extends AbstractRendererAndParserTestCase {


    @Override
	protected Set<OWLAxiom> getAxioms() {
        throw new OWLRuntimeException("TODO:");
//        OWLOntology ont = null;
//        try {
//            URL url = TestURIsWithNumericFragments.class.getResource("/numericfragments.rdf");
//            ont = getManager().loadOntologyFromOntologyDocument(url.toURI());
//            List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();
//            for(OWLOntologyAnnotationAxiom ax : ont.getOntologyAnnotationAxioms()) {
//                changes.add(new RemoveAxiom(ont, ax));
//            }
//            getManager().applyChanges(changes);
//            getManager().removeOntology(ont.getIRI());
//        }
//        catch(OWLOntologyChangeException e) {
//            throw new RuntimeException(e);
//        }
//        catch (URISyntaxException e) {
//            throw new RuntimeException(e);
//        }
//        catch (OWLOntologyCreationException e) {
//            throw new RuntimeException(e);
//        }
//        return ont.getAxioms();
    }


    @Override
	protected String getClassExpression() {
        return "URIs with purely numeric fragments";
    }
}
