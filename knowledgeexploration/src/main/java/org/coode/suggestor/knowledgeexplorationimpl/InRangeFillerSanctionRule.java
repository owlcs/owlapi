/*
 * Date: Dec 17, 2007
 *
 * code made available under Mozilla Public License (http://www.mozilla.org/MPL/MPL-1.1.html)
 *
 * copyright 2007, The University of Manchester
 *
 * Author: Nick Drummond
 * http://www.cs.man.ac.uk/~drummond/
 * Bio Health Informatics Group
 * The University Of Manchester
 */
package org.coode.suggestor.knowledgeexplorationimpl;

import org.coode.suggestor.api.FillerSanctionRule;
import org.coode.suggestor.api.FillerSuggestor;
import org.coode.suggestor.util.ReasonerHelper;
import org.semanticweb.owlapi.model.*;

/**
 * Checks if the filler is in the asserted range.
 */
public class InRangeFillerSanctionRule implements FillerSanctionRule {

    private FillerSuggestor fs;

    public void setSuggestor(FillerSuggestor fs) {
        this.fs = fs;
    }

    public boolean meetsSanction(OWLClassExpression c, OWLObjectPropertyExpression p, OWLClassExpression f) {
        return new ReasonerHelper(fs.getReasoner()).isInAssertedRange(p, f);
    }

    public boolean meetsSanction(OWLClassExpression c, OWLDataProperty p, OWLDataRange f) {
        return new ReasonerHelper(fs.getReasoner()).isInAssertedRange(p, f);
    }
}
