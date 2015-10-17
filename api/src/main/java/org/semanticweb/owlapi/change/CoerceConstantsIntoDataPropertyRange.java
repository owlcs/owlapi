/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package org.semanticweb.owlapi.change;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asSet;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLObjectDuplicator;

/**
 * Coerces constants to have the same type as the range of a property in axioms
 * where the two are used. For example, given, p value "xyz", the "xyz" constant
 * would be typed with the range of p.
 * 
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.1.1
 */
public class CoerceConstantsIntoDataPropertyRange extends AbstractCompositeOntologyChange {

    /**
     * Instantiates a new coerce constants into data property range.
     * 
     * @param dataFactory
     *        the data factory
     * @param ontologies
     *        the ontologies to use
     */
    public CoerceConstantsIntoDataPropertyRange(OWLDataFactory dataFactory, Collection<OWLOntology> ontologies) {
        super(dataFactory);
        Map<OWLDataPropertyExpression, OWLDatatype> map = new HashMap<>();
        for (OWLOntology ont : checkNotNull(ontologies, "ontologies cannot be null")) {
            ont.axioms(AxiomType.DATA_PROPERTY_RANGE).filter(ax -> ax.getRange().isOWLDatatype())
                .forEach(ax -> map.put(ax.getProperty(), ax.getRange().asOWLDatatype()));
        }
        OWLConstantReplacer replacer = new OWLConstantReplacer(dataFactory, map);
        ontologies.forEach(o -> o.logicalAxioms().forEach(ax -> {
            OWLAxiom dupAx = replacer.duplicateObject(ax);
            if (!ax.equals(dupAx)) {
                addChange(new RemoveAxiom(o, ax));
                addChange(new AddAxiom(o, dupAx));
            }
        }));
    }

    /** The Class OWLConstantReplacer. */
    private class OWLConstantReplacer extends OWLObjectDuplicator {

        private final Map<OWLDataPropertyExpression, OWLDatatype> map;

        /**
         * @param dataFactory
         *        the data factory
         * @param m
         *        the m
         */
        OWLConstantReplacer(OWLDataFactory dataFactory, Map<OWLDataPropertyExpression, OWLDatatype> m) {
            super(dataFactory);
            map = m;
        }

        private OWLDataOneOf process(OWLDataPropertyExpression prop, OWLDataOneOf oneOf) {
            return df.getOWLDataOneOf(asSet(oneOf.values().map(c -> process(prop, c))));
        }

        private OWLLiteral process(OWLDataPropertyExpression prop, OWLLiteral con) {
            OWLDatatype dt = map.get(prop);
            if (dt == null) {
                return con;
            }
            return df.getOWLLiteral(con.getLiteral(), dt);
        }

        @Override
        public OWLDataHasValue visit(OWLDataHasValue ce) {
            return df.getOWLDataHasValue(ce.getProperty(), process(ce.getProperty(), ce.getFiller()));
        }

        @Override
        public OWLDataSomeValuesFrom visit(OWLDataSomeValuesFrom ce) {
            if (ce.getFiller() instanceof OWLDataOneOf) {
                return df.getOWLDataSomeValuesFrom(ce.getProperty(),
                    process(ce.getProperty(), (OWLDataOneOf) ce.getFiller()));
            }
            return super.visit(ce);
        }

        @Override
        public OWLDataMinCardinality visit(OWLDataMinCardinality ce) {
            if (ce.getFiller() instanceof OWLDataOneOf) {
                return df.getOWLDataMinCardinality(ce.getCardinality(), ce.getProperty(),
                    process(ce.getProperty(), (OWLDataOneOf) ce.getFiller()));
            }
            return super.visit(ce);
        }

        @Override
        public OWLDataMaxCardinality visit(OWLDataMaxCardinality ce) {
            if (ce.getFiller() instanceof OWLDataOneOf) {
                return df.getOWLDataMaxCardinality(ce.getCardinality(), ce.getProperty(),
                    process(ce.getProperty(), (OWLDataOneOf) ce.getFiller()));
            }
            return super.visit(ce);
        }

        @Override
        public OWLDataExactCardinality visit(OWLDataExactCardinality ce) {
            if (ce.getFiller() instanceof OWLDataOneOf) {
                return df.getOWLDataExactCardinality(ce.getCardinality(), ce.getProperty(),
                    process(ce.getProperty(), (OWLDataOneOf) ce.getFiller()));
            }
            return super.visit(ce);
        }

        @Override
        public OWLDataAllValuesFrom visit(OWLDataAllValuesFrom ce) {
            if (ce.getFiller() instanceof OWLDataOneOf) {
                return df.getOWLDataAllValuesFrom(ce.getProperty(),
                    process(ce.getProperty(), (OWLDataOneOf) ce.getFiller()));
            }
            return super.visit(ce);
        }

        @Override
        public OWLDataPropertyAssertionAxiom visit(OWLDataPropertyAssertionAxiom axiom) {
            return df.getOWLDataPropertyAssertionAxiom(axiom.getProperty(), axiom.getSubject(),
                process(axiom.getProperty(), axiom.getObject()));
        }

        @Override
        public OWLNegativeDataPropertyAssertionAxiom visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            return df.getOWLNegativeDataPropertyAssertionAxiom(axiom.getProperty(), axiom.getSubject(),
                process(axiom.getProperty(), axiom.getObject()));
        }
    }
}
