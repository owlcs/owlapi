package org.semanticweb.owlapi.model;

import static org.hamcrest.CoreMatchers.startsWith;
import static org.hamcrest.MatcherAssert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.semanticweb.owlapi.vocab.Namespaces;
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group Date: 18/02/2014
 */
@SuppressWarnings("javadoc")
@RunWith(Parameterized.class)
public class SWRLBuiltInsVocabularyTestCase {

    private SWRLBuiltInsVocabulary vocabulary;

    public SWRLBuiltInsVocabularyTestCase(SWRLBuiltInsVocabulary vocabulary) {
        this.vocabulary = vocabulary;
    }

    @Parameterized.Parameters
    public static Collection<Object[]> getData() {
        List<Object[]> data = new ArrayList<Object[]>();
        for (SWRLBuiltInsVocabulary v : SWRLBuiltInsVocabulary.values()) {
            data.add(new Object[] { v });
        }
        return data;
    }

    @Test
    public void getPrefixedName_shouldStartWithSWRLBuiltInsPrefixName() {
        assertThat(vocabulary.getPrefixedName(),
                startsWith(Namespaces.SWRLB.getPrefixName()));
    }

    @Test
    public void getIRI_shouldReturnAnIRIThatStartsWithSWRLBuiltInsPrefix() {
        assertThat(vocabulary.getIRI().toString(),
                startsWith(Namespaces.SWRLB.getPrefixIRI()));
    }
}
