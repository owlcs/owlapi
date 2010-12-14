package uk.ac.manchester.cs.owl.owlapi.alternateimpls.test.performance;
import junit.framework.TestCase;
import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.io.ToStringRenderer;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.ShortFormProvider;

public class TestToStringRenderer extends TestCase {

   static final String TEST = "help";

   public void testToStringRenderer(){
       ToStringRenderer.getInstance().setRenderer(new OWLObjectRenderer(){

           public void setShortFormProvider(ShortFormProvider shortFormProvider) {
               // do nothing
           }

           public String render(OWLObject owlObject) {
               return TEST;
           }
       });

       OWLOntologyManager mngr = OWLManager.createOWLOntologyManager();

       OWLClass a = mngr.getOWLDataFactory().getOWLClass(IRI.create("http://example.com/a"));

       assertEquals(TEST, a.toString());
   }
}

