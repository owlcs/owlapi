package org.semanticweb.owl.api.test;

import org.semanticweb.owl.model.*;
import static org.semanticweb.owl.vocab.OWLRestrictedDataRangeFacetVocabulary.MIN_EXCLUSIVE;
/*
 * Copyright (C) 2008, University of Manchester
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
 * Date: 02-Jun-2008<br><br>
 */
public class RestrictedDataRangeTestCase extends AbstractRoundTrippingTest {


    protected OWLOntology createOntology() {
        try {
            OWLOntology ont = getOWLOntology("http://another.com/ont");
            OWLDatatype dt = getFactory().getIntegerDatatype();
            OWLDataRangeRestriction restriction = getFactory().getOWLDataRangeRestriction(dt,
                                                                                          MIN_EXCLUSIVE,
                                                                                          getFactory().getOWLTypedLiteral(33));
            OWLDataProperty prop = getOWLDataProperty("p");
            OWLDescription sup = getFactory().getOWLDataSomeRestriction(prop, restriction);
            OWLClass cls = getOWLClass("A");
            getManager().applyChange(new AddAxiom(ont, getFactory().getOWLSubClassAxiom(cls, sup)));
            return ont;
        }
        catch (OWLOntologyChangeException e) {
            fail(e.getMessage());
        }
        return null;
    }
}
