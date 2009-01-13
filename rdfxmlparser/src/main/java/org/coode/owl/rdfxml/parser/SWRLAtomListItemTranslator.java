package org.coode.owl.rdfxml.parser;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.vocab.SWRLBuiltInsVocabulary;
import static org.semanticweb.owl.vocab.SWRLVocabulary.*;

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


    public SWRLAtom translate(URI firstObject) throws OWLException {
        if (consumer.isSWRLBuiltInAtom(firstObject)) {
            URI builtInURI = consumer.getResourceObject(firstObject, BUILT_IN.getURI(), true);
            URI mainURI = consumer.getResourceObject(firstObject, ARGUMENTS.getURI(), true);
            OptimisedListTranslator<SWRLAtomDObject> listTranslator = new OptimisedListTranslator<SWRLAtomDObject>(
                    consumer,
                    new SWRLAtomDObjectListItemTranslator());
            List<SWRLAtomDObject> args = listTranslator.translateList(mainURI);
            return dataFactory.getSWRLBuiltInAtom(SWRLBuiltInsVocabulary.getBuiltIn(builtInURI), args);
        }
        else if (consumer.isSWRLClassAtom(firstObject)) {
            // C(?x) or C(ind)
            SWRLAtomIObject iObject = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getURI());
            URI classURI = consumer.getResourceObject(firstObject, CLASS_PREDICATE.getURI(), true);
            OWLDescription desc = consumer.translateDescription(classURI);
            return dataFactory.getSWRLClassAtom(desc, iObject);
        }
        else if (consumer.isSWRLDataRangeAtom(firstObject)) {
            // DR(?x) or DR(val)
            SWRLAtomDObject dObject = translateSWRLAtomDObject(firstObject, ARGUMENT_1.getURI());
            URI dataRangeURI = consumer.getResourceObject(firstObject, DATA_RANGE.getURI(), true);
            OWLDataRange dataRange = consumer.translateDataRange(dataRangeURI);
            return dataFactory.getSWRLDataRangeAtom(dataRange, dObject);
        }
        else if (consumer.isSWRLDataValuedPropertyAtom(firstObject)) {
            SWRLAtomIObject arg1 = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getURI());
            SWRLAtomDObject arg2 = translateSWRLAtomDObject(firstObject, ARGUMENT_2.getURI());
            URI dataPropertyURI = consumer.getResourceObject(firstObject, PROPERTY_PREDICATE.getURI(), true);
            OWLDataPropertyExpression prop = consumer.translateDataPropertyExpression(dataPropertyURI);
            return dataFactory.getSWRLDataValuedPropertyAtom(prop, arg1, arg2);
        }
        else if (consumer.isSWRLIndividualPropertyAtom(firstObject)) {
            SWRLAtomIObject arg1 = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getURI());
            SWRLAtomIObject arg2 = translateSWRLAtomIObject(firstObject, ARGUMENT_2.getURI());
            URI objectPropertyURI = consumer.getResourceObject(firstObject, PROPERTY_PREDICATE.getURI(), true);
            OWLObjectPropertyExpression prop = consumer.translateObjectPropertyExpression(objectPropertyURI);
            return dataFactory.getSWRLObjectPropertyAtom(prop, arg1, arg2);
        }
        else if (consumer.isSWRLSameAsAtom(firstObject)) {
            SWRLAtomIObject arg1 = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getURI());
            SWRLAtomIObject arg2 = translateSWRLAtomIObject(firstObject, ARGUMENT_2.getURI());
            return dataFactory.getSWRLSameAsAtom(arg1, arg2);
        }
        else if (consumer.isSWRLDifferentFromAtom(firstObject)) {
            SWRLAtomIObject arg1 = translateSWRLAtomIObject(firstObject, ARGUMENT_1.getURI());
            SWRLAtomIObject arg2 = translateSWRLAtomIObject(firstObject, ARGUMENT_2.getURI());
            return dataFactory.getSWRLDifferentFromAtom(arg1, arg2);
        }
        throw new OWLRDFXMLParserMalformedNodeException("Don't know how to translate SWRL Atom: " + firstObject);
    }


    public SWRLAtom translate(OWLConstant firstObject) throws OWLException {
        throw new OWLRDFXMLParserMalformedNodeException("Unexprected literal in atom list: " + firstObject);
    }


    private SWRLAtomIObject translateSWRLAtomIObject(URI mainURI, URI argPredicateURI) throws OWLException {
        URI argURI = consumer.getResourceObject(mainURI, argPredicateURI, true);
        if (argURI != null) {
            if (consumer.isSWRLVariable(argURI)) {
                return dataFactory.getSWRLAtomIVariable(argURI);
            }
            else {
                return dataFactory.getSWRLAtomIndividualObject(consumer.getOWLIndividual(argURI));
            }
        }
        else {
            throw new OWLRDFXMLParserMalformedNodeException("Cannot translate SWRL Atom I-Object for " + argPredicateURI + " Triple not found.");
        }
    }


    private SWRLAtomDObject translateSWRLAtomDObject(URI mainURI, URI argPredicateURI) throws OWLException {
        URI argURI = consumer.getResourceObject(mainURI, argPredicateURI, true);
        if (argURI != null) {
            // Must be a variable -- double check
            if (!consumer.isSWRLVariable(argURI)) {
                logger.info("Expected SWRL variable for SWRL Data Object: " + argURI + "(possibly untyped)");
            }
            return dataFactory.getSWRLAtomDVariable(argURI);
        }
        else {
            // Must be a literal
            OWLConstant con = consumer.getLiteralObject(mainURI, argPredicateURI, true);
            if (con != null) {
                return dataFactory.getSWRLAtomConstantObject(con);
            }
        }
        throw new OWLRDFXMLParserMalformedNodeException("Cannot translate SWRL Atom D-Object for " + argPredicateURI + ". Triple not found.");
    }


    private class SWRLAtomDObjectListItemTranslator implements ListItemTranslator<SWRLAtomDObject> {

        public SWRLAtomDObject translate(URI firstObject) throws OWLException {
            return dataFactory.getSWRLAtomDVariable(firstObject);
        }


        public SWRLAtomDObject translate(OWLConstant firstObject) throws OWLException {
            return dataFactory.getSWRLAtomConstantObject(firstObject);
        }
    }
}
