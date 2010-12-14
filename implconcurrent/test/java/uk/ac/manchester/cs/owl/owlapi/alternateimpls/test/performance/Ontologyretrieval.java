package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.performance;
import java.util.Set;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyCreationException;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import static java.lang.System.out;

/**
 * Sample class for loading the IAO ontology and getting the no. of classes in it.
 * @author Chaitu
 */
public class Ontologyretrieval
{
    private static final String IAO_URL = "http://purl.obolibrary.org/obo/iao/dev/iao.owl";

    private static final String RO_URL = "http://www.obofoundry.org/ro/ro.owl";

    
    public static void main(String[] args) throws OWLOntologyCreationException
    {
        OWLOntologyManager manager = OWLManager.createOWLOntologyManager();
        //manager.loadOntologyFromOntologyDocument(IRI.create(IAO_URL));
        manager.loadOntologyFromOntologyDocument(IRI.create(RO_URL));
        Set<OWLOntology> ontologySet = manager.getOntologies();
        int i = 0;
        for (OWLOntology ontology: ontologySet) {
            out.println(ontology);
            Set<OWLClass> classes = ontology.getClassesInSignature();
            i += classes.size();
            out.println("");
        }  //for
        out.println("Class Count = "+i);
    } //main
}