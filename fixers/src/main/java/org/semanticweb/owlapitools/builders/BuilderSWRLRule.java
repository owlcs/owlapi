package org.semanticweb.owlapitools.builders;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;

/** Builder class for SWRLRule */
public class BuilderSWRLRule extends BaseBuilder<SWRLRule, BuilderSWRLRule> {
    private Set<SWRLAtom> body = new HashSet<SWRLAtom>();
    private Set<SWRLAtom> head = new HashSet<SWRLAtom>();

    /** builder initialized from an existing object
     * 
     * @param expected
     *            the existing object */
    public BuilderSWRLRule(SWRLRule expected, OWLDataFactory df) {
        this(df);
        withBody(expected.getBody()).withHead(expected.getHead())
                .withAnnotations(expected.getAnnotations());
    }

    /** uninitialized builder */
    public BuilderSWRLRule(OWLDataFactory df) {
        super(df);
    }

    /** @param arg
     *            body atom
     * @return builder */
    public BuilderSWRLRule withBody(SWRLAtom arg) {
        body.add(arg);
        return this;
    }

    /** @param arg
     *            head atom
     * @return builder */
    public BuilderSWRLRule withHead(SWRLAtom arg) {
        head.add(arg);
        return this;
    }

    /** @param arg
     *            body atom
     * @return builder */
    public BuilderSWRLRule withBody(Collection<SWRLAtom> arg) {
        body.addAll(arg);
        return this;
    }

    /** @param arg
     *            head atom
     * @return builder */
    public BuilderSWRLRule withHead(Collection<SWRLAtom> arg) {
        head.addAll(arg);
        return this;
    }

    @Override
    public SWRLRule buildObject() {
        return df.getSWRLRule(body, head, annotations);
    }
}
