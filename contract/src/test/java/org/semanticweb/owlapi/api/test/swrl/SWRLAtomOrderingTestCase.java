package org.semanticweb.owlapi.api.test.swrl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         04/04/2014
 */
class SWRLAtomOrderingTestCase extends TestBase {

    @Nonnull
    private final Set<SWRLAtom> body = new LinkedHashSet<>();
    protected SWRLAtom atomA;
    protected SWRLAtom atomB;
    protected SWRLAtom atomC;
    protected SWRLAtom atomD;
    private SWRLRule rule;

    @BeforeEach
    void setUp() {
        atomA = df.getSWRLClassAtom(A, df.getSWRLIndividualArgument(I));
        atomB = df.getSWRLClassAtom(A, df.getSWRLIndividualArgument(J));
        atomC = df.getSWRLClassAtom(A, df.getSWRLIndividualArgument(k));
        atomD = df.getSWRLClassAtom(A, df.getSWRLIndividualArgument(l));
        body.add(atomC);
        body.add(atomB);
        body.add(atomA);
        Set<SWRLAtom> head = new LinkedHashSet<>();
        head.add(atomD);
        rule = df.getSWRLRule(body, head, Collections.<OWLAnnotation>emptySet());
    }

    @Test
    void shouldPreserveBodyOrdering() {
        List<SWRLAtom> ruleImplBody = new ArrayList<>(rule.getBody());
        List<SWRLAtom> specifiedBody = new ArrayList<>(body);
        assertThat(ruleImplBody, is(equalTo(specifiedBody)));
    }
}
