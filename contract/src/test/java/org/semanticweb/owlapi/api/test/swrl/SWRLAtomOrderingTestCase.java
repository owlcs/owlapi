package org.semanticweb.owlapi.api.test.swrl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.util.CollectionFactory;

import uk.ac.manchester.cs.owl.owlapi.SWRLRuleImpl;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         04/04/2014
 */
@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class SWRLAtomOrderingTestCase {

    @Mock
    protected SWRLAtom atomA;
    @Mock
    protected SWRLAtom atomB;
    @Mock
    protected SWRLAtom atomC;
    @Mock
    protected SWRLAtom atomD;
    private SWRLRuleImpl rule;
    @Nonnull
    private final Set<SWRLAtom> body = new LinkedHashSet<>();

    @Before
    public void setUp() {
        body.add(atomC);
        body.add(atomB);
        body.add(atomA);
        Set<SWRLAtom> head = new LinkedHashSet<>();
        head.add(atomD);
        rule = new SWRLRuleImpl(body, head, CollectionFactory.<OWLAnnotation>emptySet());
    }

    @Test
    public void shouldPreserveBodyOrdering() {
        List<SWRLAtom> ruleImplBody = new ArrayList<>(rule.getBody());
        List<SWRLAtom> specifiedBody = new ArrayList<>(body);
        assertThat(ruleImplBody, is(equalTo(specifiedBody)));
    }
}
