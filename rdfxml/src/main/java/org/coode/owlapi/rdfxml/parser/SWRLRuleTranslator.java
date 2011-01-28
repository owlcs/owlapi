package org.coode.owlapi.rdfxml.parser;

import java.util.Collections;
import java.util.Set;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.SWRLAtom;
import org.semanticweb.owlapi.model.SWRLRule;
import org.semanticweb.owlapi.vocab.SWRLVocabulary;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 18-Feb-2007<br><br>
 */
public class SWRLRuleTranslator {

    private OWLRDFConsumer consumer;

    private OptimisedListTranslator<SWRLAtom> listTranslator;


    public SWRLRuleTranslator(OWLRDFConsumer consumer) {
        this.consumer = consumer;
        listTranslator = new OptimisedListTranslator<SWRLAtom>(consumer, new SWRLAtomListItemTranslator(consumer));
    }


    public void translateRule(IRI mainNode) {
        Set<SWRLAtom> consequent = Collections.emptySet();
        IRI ruleHeadIRI = consumer.getResourceObject(mainNode, SWRLVocabulary.HEAD.getIRI(), true);
        if (ruleHeadIRI != null) {
            consequent = listTranslator.translateToSet(ruleHeadIRI);
        }

        Set<SWRLAtom> antecedent = Collections.emptySet();
        IRI ruleBodyIRI = consumer.getResourceObject(mainNode, SWRLVocabulary.BODY.getIRI(), true);
        if (ruleBodyIRI != null) {
            antecedent = listTranslator.translateToSet(ruleBodyIRI);
        }
        SWRLRule rule = null;
        if (!consumer.isAnonymousNode(mainNode)) {
            rule = consumer.getDataFactory().getSWRLRule(antecedent, consequent);
        }
        else {
            rule = consumer.getDataFactory().getSWRLRule(antecedent, consequent);
        }
        consumer.addAxiom(rule);
    }
}
