/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2011, University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.coode.owlapi.rdfxml.parser;

import java.util.logging.Logger;

import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLPropertyExpression;

/**
 * @author Matthew Horridge, The University of Manchester, Information
 *         Management Group, Date: 02-Feb-2009
 */
public class HasKeyListItemTranslator implements
        ListItemTranslator<OWLPropertyExpression<?, ?>> {

    private static Logger logger = Logger
            .getLogger(HasKeyListItemTranslator.class.getName());
    private OWLRDFConsumer consumer;

    /**
     * @param consumer
     *        consumer
     */
    public HasKeyListItemTranslator(OWLRDFConsumer consumer) {
        this.consumer = consumer;
    }

    @Override
    public OWLPropertyExpression<?, ?> translate(OWLLiteral firstObject) {
        return null;
    }

    @Override
    public OWLPropertyExpression translate(IRI firstObject) {
        if (consumer.isObjectPropertyOnly(firstObject)) {
            return consumer.getDataFactory().getOWLObjectProperty(firstObject);
        }
        if (consumer.isDataPropertyOnly(firstObject)) {
            return consumer.getDataFactory().getOWLDataProperty(firstObject);
        }
        // If neither condition was true, the property has been illegally
        // punned, or is untyped
        // use the first translation available, since there is no way to
        // know which is correct
        OWLPropertyExpression property = null;
        if (consumer.isObjectProperty(firstObject)) {
            logger.warning("Property "
                    + firstObject
                    + " has been punned illegally: found declaration as OWLObjectProperty");
            property = consumer.getDataFactory().getOWLObjectProperty(
                    firstObject);
        }
        if (consumer.isDataProperty(firstObject)) {
            logger.warning("Property "
                    + firstObject
                    + " has been punned illegally: found declaration as OWLDataProperty");
            if (property == null) {
                property = consumer.getDataFactory().getOWLDataProperty(
                        firstObject);
            }
        }
        // if there is no declaration for the property at this point, warn
        // and consider it a datatype property.
        // This matches existing behaviour.
        if (property == null) {
            logger.warning("Property "
                    + firstObject
                    + " is undeclared at this point in parsing: typing as OWLDataProperty");
            property = consumer.getDataFactory()
                    .getOWLDataProperty(firstObject);
        }
        return property;
    }
}
