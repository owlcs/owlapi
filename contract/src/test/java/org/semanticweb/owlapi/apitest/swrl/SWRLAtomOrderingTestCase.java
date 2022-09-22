package org.semanticweb.owlapi.apitest.swrl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.semanticweb.owlapi.apitest.baseclasses.TestBase;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         04/04/2014
 */
class SWRLAtomOrderingTestCase extends TestBase {

    private final Set<SWRLAtom> body = new LinkedHashSet<>();
    private SWRLRule rule;

    @BeforeEach
    void setUp() {
        body.add(SWRLClassAtom(CLASSES.A, SWRLIndividualArgument(INDIVIDUALS.k)));
        body.add(SWRLClassAtom(CLASSES.A, SWRLIndividualArgument(INDIVIDUALS.J)));
        body.add(SWRLClassAtom(CLASSES.A, SWRLIndividualArgument(INDIVIDUALS.I)));
        Set<SWRLAtom> head = new LinkedHashSet<>();
        head.add(SWRLClassAtom(CLASSES.A, SWRLIndividualArgument(INDIVIDUALS.l)));
        rule = SWRLRule(body, head);
    }

    @Test
    void shouldPreserveBodyOrdering() {
        List<SWRLAtom> ruleImplBody = rule.bodyList();
        List<SWRLAtom> specifiedBody = new ArrayList<>(body);
        assertThat(ruleImplBody, is(equalTo(specifiedBody)));
    }
}
