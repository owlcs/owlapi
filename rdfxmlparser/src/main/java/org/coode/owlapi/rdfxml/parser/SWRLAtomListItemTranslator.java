package org.coode.owlapi.rdfxml.parser;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.vocab.SWRLBuiltInsVocabulary;
import static org.semanticweb.owlapi.vocab.SWRLVocabulary.*;

import java.net.URI;
import java.util.List;
import java.util.logging.Logger;
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
public class SWRLAtomListItemTranslator implements ListItemTranslator<SWRLAtom> {

    private static final Logger logger = Logger.getLogger(SWRLAtomListItemTranslator.class.getName());


    private OWLRDFConsumer consumer;

    private OWLDataFactory dataFactory;


    public SWRLAtomListItemTranslator(OWLRDFConsumer consumer) {
        this.consumer = consumer;
        dataFactory = consumer.getDataFactory();
    }


    public SWRLAtom translate(URI firstObject) {
        if (consumer.isSWRLBuiltInAtom(firstObject)) {
            URI builtInURI = consumer.getResourceObject(firstObject, BUILT_IN.getURI(), true);
            URI mainURI = consumer.getResourceObject(firstObject, ARGUMENTS.getURI(), true);
            OptimisedListTranslator<SWRLDArgument> listTranslator = new OptimisedListTranslator<SWRLDArgument>(
                    consumer,
                    new SWRLAtomDObjectListItemTranslator());
            List<SWRLDArgument> args = listTranslator.translateList(mainURI);
            return dataFactory.getSWRLBuiltInAtom(SWRLBuiltInsVocabulary.getBuiltIn(builtInURI), args);
        }
        else if (consumer.isSWRLClassAtom(firstObject)) {
            // C(?x) or C(ind)
            SWRLIArgument iObject = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getURI());
            URI classURI = consumer.getResourceObject(firstObject, CLASS_PREDICATE.getURI(), true);
            OWLClassExpression desc = consumer.translateClassExpression(classURI);
            return dataFactory.getSWRLClassAtom(desc, iObject);
        }
        else if (consumer.isSWRLDataRangeAtom(firstObject)) {
            // DR(?x) or DR(val)
            SWRLDArgument dObject = translateSWRLAtomDObject(firstObject, ARGUMENT_1.getURI());
            URI dataRangeURI = consumer.getResourceObject(firstObject, DATA_RANGE.getURI(), true);
            OWLDataRange dataRange = consumer.translateDataRange(dataRangeURI);
            return dataFactory.getSWRLDataRangeAtom(dataRange, dObject);
        }
        else if (consumer.isSWRLDataValuedPropertyAtom(firstObject)) {
            SWRLIArgument arg1 = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getURI());
            SWRLDArgument arg2 = translateSWRLAtomDObject(firstObject, ARGUMENT_2.getURI());
            URI dataPropertyURI = consumer.getResourceObject(firstObject, PROPERTY_PREDICATE.getURI(), true);
            OWLDataPropertyExpression prop = consumer.translateDataPropertyExpression(dataPropertyURI);
            return dataFactory.getSWRLDataPropertyAtom(prop, arg1, arg2);
        }
        else if (consumer.isSWRLIndividualPropertyAtom(firstObject)) {
            SWRLIArgument arg1 = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getURI());
            SWRLIArgument arg2 = translateSWRLAtomIObject(firstObject, ARGUMENT_2.getURI());
            URI objectPropertyURI = consumer.getResourceObject(firstObject, PROPERTY_PREDICATE.getURI(), true);
            OWLObjectPropertyExpression prop = consumer.translateObjectPropertyExpression(objectPropertyURI);
            return dataFactory.getSWRLObjectPropertyAtom(prop, arg1, arg2);
        }
        else if (consumer.isSWRLSameAsAtom(firstObject)) {
            SWRLIArgument arg1 = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getURI());
            SWRLIArgument arg2 = translateSWRLAtomIObject(firstObject, ARGUMENT_2.getURI());
            return dataFactory.getSWRLSameAsAtom(arg1, arg2);
        }
        else if (consumer.isSWRLDifferentFromAtom(firstObject)) {
            SWRLIArgument arg1 = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getURI());
            SWRLIArgument arg2 = translateSWRLAtomIObject(firstObject, ARGUMENT_2.getURI());
            return dataFactory.getSWRLDifferentFromAtom(arg1, arg2);
        }
        throw new RuntimeException("Don't know how to translate SWRL Atom: " + firstObject);
    }


    public SWRLAtom translate(OWLLiteral firstObject) {
        throw new RuntimeException("Unexpected literal in atom list: " + firstObject);
    }


    private SWRLIArgument translateSWRLAtomIObject(URI mainURI, URI argPredicateURI) {
        URI argURI = consumer.getResourceObject(mainURI, argPredicateURI, true);
        if (argURI != null) {
            if (consumer.isSWRLVariable(argURI)) {
                return dataFactory.getSWRLIndividualVariable(IRI.create(argURI));
            }
            else {
                return dataFactory.getSWRLIndividualArgument(consumer.getOWLIndividual(argURI));
            }
        }
        else {
            throw new RuntimeException("Cannot translate SWRL Atom I-Object for " + argPredicateURI + " Triple not found.");
        }
    }


    private SWRLDArgument translateSWRLAtomDObject(URI mainURI, URI argPredicateURI) {
        URI argURI = consumer.getResourceObject(mainURI, argPredicateURI, true);
        if (argURI != null) {
            // Must be a variable -- double check
            if (!consumer.isSWRLVariable(argURI)) {
                logger.info("Expected SWRL variable for SWRL Data Object: " + argURI + "(possibly untyped)");
            }
            return dataFactory.getSWRLLiteralVariable(IRI.create(argURI));
        }
        else {
            // Must be a literal
            OWLLiteral con = consumer.getLiteralObject(mainURI, argPredicateURI, true);
            if (con != null) {
                return dataFactory.getSWRLLiteralArgument(con);
            }
        }
        throw new IllegalStateException("Could not translate SWRL Atom D-Object");
    }


    private class SWRLAtomDObjectListItemTranslator implements ListItemTranslator<SWRLDArgument> {

        public SWRLDArgument translate(URI firstObject) {
            return dataFactory.getSWRLLiteralVariable(IRI.create(firstObject));
        }


        public SWRLDArgument translate(OWLLiteral firstObject) {
            return dataFactory.getSWRLLiteralArgument(firstObject);
        }
    }
}
