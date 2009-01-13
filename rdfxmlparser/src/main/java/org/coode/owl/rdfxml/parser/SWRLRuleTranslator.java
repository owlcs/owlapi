package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.OWLException;
import org.semanticweb.owl.model.SWRLAtom;
import org.semanticweb.owl.model.SWRLRule;
import org.semanticweb.owl.vocab.SWRLVocabulary;

import java.net.URI;
import java.util.Collections;
import java.util.Set;
/*
 * Copyright (C) 2007, University of Manchester
 *
 * Modifications to the initial code base are copyright of their
 * respective authors, or their employers as appropriate.  Authorship
 * of the modifications may be determined from the ChangeLog placed at
 * the end of this file.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.

 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.

 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */


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


    public void translateRule(URI mainNode) throws OWLException {
        Set<SWRLAtom> consequent = Collections.emptySet();
        URI ruleHeadURI = consumer.getResourceObject(mainNode, SWRLVocabulary.HEAD.getURI(), true);
        if (ruleHeadURI != null) {
            consequent = listTranslator.translateToSet(ruleHeadURI);
        }

        Set<SWRLAtom> antecedent = Collections.emptySet();
        URI ruleBodyURI = consumer.getResourceObject(mainNode, SWRLVocabulary.BODY.getURI(), true);
        if (ruleBodyURI != null) {
            antecedent = listTranslator.translateToSet(ruleBodyURI);
        }
        SWRLRule rule = null;
        if (!consumer.isAnonymousNode(mainNode)) {
            rule = consumer.getDataFactory().getSWRLRule(mainNode, antecedent, consequent);
        }
        else {
            rule = consumer.getDataFactory().getSWRLRule(mainNode, true, antecedent, consequent);
        }
        consumer.addAxiom(rule);
    }
}
