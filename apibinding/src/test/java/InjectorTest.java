import org.junit.Test;
import org.semanticweb.owlapi.model.OWLOntologyManagerFactory;
import org.semanticweb.owlapi.utilities.Injector;

public class InjectorTest {

    @Test
    public void test() {
        System.out.println("InjectorTest.test() "
            + new Injector().getImplementation(OWLOntologyManagerFactory.class));
    }

}
