package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.AnnotationValueShortFormProvider;
import org.semanticweb.owlapi.util.DefaultPrefixManager;

import java.util.*;

import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 22/12/2010
 */
public class AnnotationShortFormProviderTestCase extends AbstractOWLAPITestCase {


    public void testLiteralWithoutLanguageValue() throws Exception {

        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        PrefixManager pm = new DefaultPrefixManager("http://org.semanticweb.owlapi/ont#");
        OWLAnnotationProperty prop = AnnotationProperty("prop", pm);
        OWLNamedIndividual root = NamedIndividual("ind", pm);
        String shortForm = "MyLabel";
        Ontology(
                man,
                AnnotationAssertion(prop, root.getIRI(), Literal(shortForm))
        );
        List<OWLAnnotationProperty> props = Arrays.asList(prop);
        Map<OWLAnnotationProperty, List<String>> langMap = Collections.emptyMap();
        AnnotationValueShortFormProvider sfp = new AnnotationValueShortFormProvider(props, langMap, man);
        assertEquals(sfp.getShortForm(root), shortForm);
    }

    public void testLiteralWithLanguageValue() throws Exception {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        PrefixManager pm = new DefaultPrefixManager("http://org.semanticweb.owlapi/ont#");
        OWLAnnotationProperty prop = AnnotationProperty("prop", pm);
        OWLNamedIndividual root = NamedIndividual("ind", pm);
        String label1 = "MyLabel";
        String label2 = "OtherLabel";
        Ontology(man, AnnotationAssertion(prop, root.getIRI(), Literal(label1, "ab")), AnnotationAssertion(prop, root.getIRI(), Literal(label2, "xy")));
        List<OWLAnnotationProperty> props = Arrays.asList(prop);
        Map<OWLAnnotationProperty, List<String>> langMap = new HashMap<OWLAnnotationProperty, List<String>>();
        langMap.put(prop, Arrays.asList("ab", "xy"));
        AnnotationValueShortFormProvider sfp = new AnnotationValueShortFormProvider(props, langMap, man);
        assertEquals(sfp.getShortForm(root), label1);

        Map<OWLAnnotationProperty, List<String>> langMap2 = new HashMap<OWLAnnotationProperty, List<String>>();
        langMap2.put(prop, Arrays.asList("xy", "ab"));
        AnnotationValueShortFormProvider sfp2 = new AnnotationValueShortFormProvider(props, langMap2, man);
        assertEquals(sfp2.getShortForm(root), label2);
    }

    public void testIRIValue() throws Exception {
        OWLOntologyManager man = OWLManager.createOWLOntologyManager();
        PrefixManager pm = new DefaultPrefixManager("http://org.semanticweb.owlapi/ont#");
        OWLAnnotationProperty prop = AnnotationProperty("prop", pm);
        OWLNamedIndividual root = NamedIndividual("ind", pm);
        Ontology(
                man,
                AnnotationAssertion(prop, root.getIRI(), IRI("http://org.semanticweb.owlapi/ont#myIRI"))
        );
        List<OWLAnnotationProperty> props = Arrays.asList(prop);
        Map<OWLAnnotationProperty, List<String>> langMap = Collections.emptyMap();
        AnnotationValueShortFormProvider sfp = new AnnotationValueShortFormProvider(props, langMap, man);
        assertEquals(sfp.getShortForm(root), "myIRI");
    }



}
