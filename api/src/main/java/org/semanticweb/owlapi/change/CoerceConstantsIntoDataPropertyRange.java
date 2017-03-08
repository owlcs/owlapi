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
import static org.semanticweb.owlapi.util.OWLAPIStreamUtils.asMap;

import java.util.Collection;
import java.util.Map;
import java.util.stream.Stream;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataHasValue;
import org.semanticweb.owlapi.model.OWLDataMaxCardinality;
import org.semanticweb.owlapi.model.OWLDataMinCardinality;
import org.semanticweb.owlapi.model.OWLDataOneOf;
import org.semanticweb.owlapi.model.OWLDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLDataPropertyRangeAxiom;
import org.semanticweb.owlapi.model.OWLDataSomeValuesFrom;
import org.semanticweb.owlapi.model.OWLDatatype;
import org.semanticweb.owlapi.model.OWLLiteral;
import org.semanticweb.owlapi.model.OWLLogicalAxiom;
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.util.OWLObjectDuplicator;

/**
 * Coerces constants to have the same type as the range of a property in axioms where the two are
 * used. For example, given, p value "xyz", the "xyz" constant would be typed with the range of p.
 *
 * @author Matthew Horridge, The University Of Manchester, Bio-Health Informatics Group
 * @since 2.1.1
 */
public class CoerceConstantsIntoDataPropertyRange extends AbstractCompositeOntologyChange {

    /**
     * Instantiates a new coerce constants into data property range.
     *
     * @param m The manager providing data factory and config to be used for the duplication.
     * @param ontologies the ontologies to use
     */
    public CoerceConstantsIntoDataPropertyRange(OWLOntologyManager m,
        Collection<OWLOntology> ontologies) {
        super(m.getOWLDataFactory());
        checkNotNull(ontologies, "ontologies cannot be null");
        Map<OWLDataPropertyExpression, OWLDatatype> map = asMap(datatypes(ontologies),
            ax -> ax.getProperty(), ax -> ax.getRange().asOWLDatatype());
        OWLConstantReplacer replacer = new OWLConstantReplacer(m, map);
        ontologies.forEach(o -> o.logicalAxioms().forEach(ax -> duplicate(replacer, o, ax)));
    }

    /**
     * @param ontologies ontologies to inspect
     * @return datatypes declared in the ontologies (not including OWL 2 standard datatypes)
     */
    public Stream<OWLDataPropertyRangeAxiom> datatypes(Collection<OWLOntology> ontologies) {
        return ontologies.stream().flatMap(ont -> ont.axioms(AxiomType.DATA_PROPERTY_RANGE))
            .filter(ax -> ax.getRange().isOWLDatatype());
    }

    protected void duplicate(OWLConstantReplacer replacer, OWLOntology o, OWLLogicalAxiom ax) {
        OWLAxiom dupAx = replacer.duplicateObject(ax);
        if (!ax.equals(dupAx)) {
            addChange(new RemoveAxiom(o, ax));
            addChange(new AddAxiom(o, dupAx));
        }
    }

    private class OWLConstantReplacer extends OWLObjectDuplicator {

        private final Map<OWLDataPropertyExpression, OWLDatatype> map;

        /**
         * @param m The manager providing data factory and config to be used for the duplication.
         * @param map the map
         */
        OWLConstantReplacer(OWLOntologyManager m, Map<OWLDataPropertyExpression, OWLDatatype> map) {
            super(m);
            this.map = map;
        }

        private OWLDataOneOf process(OWLDataPropertyExpression prop, OWLDataOneOf oneOf) {
            return df.getOWLDataOneOf(oneOf.values().map(c -> process(prop, c)));
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
            return df.getOWLDataHasValue(ce.getProperty(),
                process(ce.getProperty(), ce.getFiller()));
        }

        @Override
        public OWLDataSomeValuesFrom visit(OWLDataSomeValuesFrom ce) {
            if (ce.getFiller() instanceof OWLDataOneOf) {
                return df.getOWLDataSomeValuesFrom(ce.getProperty(),
                    process(ce.getProperty(), (OWLDataOneOf) ce.getFiller()));
            }
            return (OWLDataSomeValuesFrom) super.visit(ce);
        }

        @Override
        public OWLDataMinCardinality visit(OWLDataMinCardinality ce) {
            if (ce.getFiller() instanceof OWLDataOneOf) {
                return df.getOWLDataMinCardinality(ce.getCardinality(), ce.getProperty(),
                    process(ce.getProperty(), (OWLDataOneOf) ce.getFiller()));
            }
            return (OWLDataMinCardinality) super.visit(ce);
        }

        @Override
        public OWLDataMaxCardinality visit(OWLDataMaxCardinality ce) {
            if (ce.getFiller() instanceof OWLDataOneOf) {
                return df.getOWLDataMaxCardinality(ce.getCardinality(), ce.getProperty(),
                    process(ce.getProperty(), (OWLDataOneOf) ce.getFiller()));
            }
            return (OWLDataMaxCardinality) super.visit(ce);
        }

        @Override
        public OWLDataExactCardinality visit(OWLDataExactCardinality ce) {
            if (ce.getFiller() instanceof OWLDataOneOf) {
                return df.getOWLDataExactCardinality(ce.getCardinality(), ce.getProperty(),
                    process(ce.getProperty(), (OWLDataOneOf) ce.getFiller()));
            }
            return (OWLDataExactCardinality) super.visit(ce);
        }

        @Override
        public OWLDataAllValuesFrom visit(OWLDataAllValuesFrom ce) {
            if (ce.getFiller() instanceof OWLDataOneOf) {
                return df.getOWLDataAllValuesFrom(ce.getProperty(),
                    process(ce.getProperty(), (OWLDataOneOf) ce.getFiller()));
            }
            return (OWLDataAllValuesFrom) super.visit(ce);
        }

        @Override
        public OWLDataPropertyAssertionAxiom visit(OWLDataPropertyAssertionAxiom axiom) {
            return df.getOWLDataPropertyAssertionAxiom(axiom.getProperty(), axiom.getSubject(),
                process(axiom.getProperty(), axiom.getObject()));
        }

        @Override
        public OWLNegativeDataPropertyAssertionAxiom visit(
            OWLNegativeDataPropertyAssertionAxiom axiom) {
            return df.getOWLNegativeDataPropertyAssertionAxiom(axiom.getProperty(),
                axiom.getSubject(), process(axiom.getProperty(), axiom.getObject()));
        }
    }
}
