package org.semanticweb.owlapi.api.test.swrl;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;
import org.semanticweb.owlapi.api.test.baseclasses.TestBase;
import org.semanticweb.owlapi.model.OWLAnnotation;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.SWRLAtom;

import uk.ac.manchester.cs.owl.owlapi.SWRLRuleImpl;

/**
 * @author Matthew Horridge, Stanford University, Bio-Medical Informatics Research Group, Date:
 *         04/04/2014
 */
@SuppressWarnings({"javadoc", "null"})
@RunWith(MockitoJUnitRunner.class)
public class SWRLAtomOrderingTestCase extends TestBase {

    protected SWRLAtom atomA;
    protected SWRLAtom atomB;
    protected SWRLAtom atomC;
    protected SWRLAtom atomD;
    private SWRLRuleImpl rule;
    private final Set<SWRLAtom> body = new LinkedHashSet<>();

    @Before
    public void setUp() {
        OWLClass predicate = df.getOWLClass(iri("a"));
        atomA = df.getSWRLClassAtom(predicate,
                        df.getSWRLIndividualArgument(df.getOWLNamedIndividual(iri("i"))));
        atomB = df.getSWRLClassAtom(predicate,
                        df.getSWRLIndividualArgument(df.getOWLNamedIndividual(iri("j"))));
        atomC = df.getSWRLClassAtom(predicate,
                        df.getSWRLIndividualArgument(df.getOWLNamedIndividual(iri("k"))));
        atomD = df.getSWRLClassAtom(predicate,
                        df.getSWRLIndividualArgument(df.getOWLNamedIndividual(iri("l"))));
        body.add(atomC);
        body.add(atomB);
        body.add(atomA);
        Set<SWRLAtom> head = new LinkedHashSet<>();
        head.add(atomD);
        rule = new SWRLRuleImpl(body, head, Collections.<OWLAnnotation>emptySet());
    }

    @Test
    public void shouldPreserveBodyOrdering() {
        List<SWRLAtom> ruleImplBody = asList(rule.body());
        List<SWRLAtom> specifiedBody = new ArrayList<>(body);
        assertThat(ruleImplBody, is(equalTo(specifiedBody)));
    }
}
