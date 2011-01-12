package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLDataRange;

/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 08-Dec-2006<br><br>
 */
public abstract class AbstractDataQuantifiedRestrictionTranslator extends AbstractDataRestrictionTranslator {

    private IRI fillerPredicate;

    public AbstractDataQuantifiedRestrictionTranslator(OWLRDFConsumer consumer, IRI fillerPredicate) {
        super(consumer);
        this.fillerPredicate = fillerPredicate;
    }

    @Override
    public boolean matches(IRI mainNode) {
        if(!super.matches(mainNode)) {
            return false;
        }
        IRI filler = getConsumer().getResourceObject(mainNode, fillerPredicate, false);
        return filler != null && getConsumer().isDataRange(filler);
    }

    public OWLDataRange translateDataRange(IRI mainNode) {
        IRI fillerPredicateObject = getConsumer().getResourceObject(mainNode, fillerPredicate, true);
        return getConsumer().translateDataRange(fillerPredicateObject);
    }

    //    final protected OWLClassExpression translateRestriction(IRI mainNode) {
//        OWLDataPropertyExpression prop = translateOnProperty(mainNode);
//        if(prop == null) {
//            return getConsumer().getOWLClass(mainNode);
//        }
//        IRI fillerObject = getResourceObject(mainNode, fillerPredicate, true);
//        OWLDataRange dataRange;
//        if(fillerObject != null) {
//            dataRange = getConsumer().translateDataRange(fillerObject);
//        }
//        else {
//            dataRange = getConsumer().getDataFactory().getTopDatatype();
//        }
//        return createRestriction(prop, dataRange);
//    }

//    @Override
//    protected OWLClassExpression translateRestriction(IRI mainNode, IRI onPropertyObject, IRI fillerObject, OWLLiteral cardinalityObject) {
//        OWLDataPropertyExpression propertyExpression = getConsumer().translateDataPropertyExpression(iri)
//    }

//    protected abstract OWLQuantifiedDataRestriction createRestriction(OWLDataPropertyExpression prop, OWLDataRange filler);
}
