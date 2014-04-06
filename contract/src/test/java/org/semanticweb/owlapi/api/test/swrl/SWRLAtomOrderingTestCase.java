package org.semanticweb.owlapi.api.test.swrl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.SWRLAtom;

import uk.ac.manchester.cs.owl.owlapi.SWRLRuleImpl;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group, Date: 04/04/2014
 */
@SuppressWarnings("javadoc")
@RunWith(MockitoJUnitRunner.class)
public class SWRLAtomOrderingTestCase {

    @Mock
    protected SWRLAtom atomA, atomB, atomC, atomD;
    private SWRLRuleImpl rule;
    private Set<SWRLAtom> body;

    @Before
    public void setUp() {
        when(atomA.compareTo(atomB)).thenReturn(-1);
        when(atomA.compareTo(atomC)).thenReturn(-1);
        when(atomB.compareTo(atomC)).thenReturn(-1);
        when(atomC.compareTo(atomB)).thenReturn(1);
        when(atomC.compareTo(atomA)).thenReturn(1);
        when(atomB.compareTo(atomA)).thenReturn(1);
        when(atomA.compareTo(atomA)).thenReturn(0);
        when(atomB.compareTo(atomB)).thenReturn(0);
        when(atomC.compareTo(atomC)).thenReturn(0);
        body = new LinkedHashSet<SWRLAtom>();
        body.add(atomC);
        body.add(atomB);
        body.add(atomA);
        Set<SWRLAtom> head = new LinkedHashSet<SWRLAtom>();
        head.add(atomD);
        rule = new SWRLRuleImpl(body, head,
                Collections.<OWLAnnotation> emptySet());
    }

    @Test
    public void shouldPreserveBodyOrdering() {
        List<SWRLAtom> ruleImplBody = new ArrayList<SWRLAtom>(rule.getBody());
        List<SWRLAtom> specifiedBody = new ArrayList<SWRLAtom>(body);
        assertThat(ruleImplBody, is(equalTo(specifiedBody)));
    }
}
