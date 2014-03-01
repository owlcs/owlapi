package org.semanticweb.owlapi.api.test.syntax;

import static org.junit.Assert.assertEquals;
import static org.semanticweb.owlapi.apibinding.OWLFunctionalSyntaxFactory.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.util.QNameShortFormProvider;

@SuppressWarnings("javadoc")
public class ShortFormProviderTestCase {

    @Test
    public void shouldFindShortForm() {
        OWLClass c = Class(IRI("http://www.ebi.ac.uk/fgpt/ontologies/test/TEST_00001> test:TEST_00001"));
        QNameShortFormProvider shortener = new QNameShortFormProvider();
        String shortform = shortener.getShortForm(c);
        assertEquals("test:TEST_00001", shortform);
    }

    @Test
    public void shouldFindShortFormForWoman() {
        OWLClass c = Class(IRI("http://www.example.org/#Woman"));
        QNameShortFormProvider shortener = new QNameShortFormProvider();
        String shortform = shortener.getShortForm(c);
        assertEquals("www:Woman", shortform);
    }

    @Test
    public void shouldFindShortFormForSetPRefix() {
        OWLClass c = Class(IRI("http://www.example.org/#Woman"));
        Map<String, String> prefixes = new HashMap<String, String>();
        prefixes.put("test", "http://www.example.org/#");
        QNameShortFormProvider shortener = new QNameShortFormProvider(prefixes);
        String shortform = shortener.getShortForm(c);
        assertEquals("test:Woman", shortform);
    }
}
