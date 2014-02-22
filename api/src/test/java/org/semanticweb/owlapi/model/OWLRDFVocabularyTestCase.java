package org.semanticweb.owlapi.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.vocab.OWLRDFVocabulary;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group Date: 18/02/2014
 */
@RunWith(Parameterized.class)
public class OWLRDFVocabularyTestCase {

    private OWLRDFVocabulary vocabulary;

    public OWLRDFVocabularyTestCase(OWLRDFVocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        List<Object[]> data = new ArrayList<Object[]>();
        for (OWLRDFVocabulary v : OWLRDFVocabulary.values()) {
            data.add(new Object[] { v });
        }
        return data;
    }

    @Test
    public void getPrefixedName_shouldStartWithOWLRDFPrefixName() {
        assertThat(vocabulary.getPrefixedName(), startsWith(vocabulary
                .getNamespace().getPrefixName()));
    }

    @Test
    public void getIRI_shouldReturnAnIRIThatStartsWithOWLRDFPrefix() {
        assertThat(vocabulary.getIRI().toString(), startsWith(vocabulary
                .getNamespace().getPrefixIRI()));
    }
}
