package org.semanticweb.owl;

import org.semanticweb.owl.model.*;
import org.semanticweb.owl.util.OWLObjectDuplicator;

import java.util.*;
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
 * Date: 13-Aug-2007<br><br>
 *
 * Coerces constants to have the same type as the range of a property in
 * axioms where the two are used.  For example, given, p value "xyz", the
 * "xyz" constant would be typed with the range of p.
 *
 */
public class CoerceConstantsIntoDataPropertyRange extends AbstractCompositeOntologyChange {

    private Map<OWLDataPropertyExpression, OWLDataType> map;

    private List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();

    public CoerceConstantsIntoDataPropertyRange(OWLDataFactory dataFactory, Set<OWLOntology> ontologies) {
        super(dataFactory);
        map = new HashMap<OWLDataPropertyExpression, OWLDataType>();
        for(OWLOntology ont : ontologies) {
            for(OWLDataPropertyRangeAxiom ax : ont.getAxioms(AxiomType.DATA_PROPERTY_RANGE)) {
                if (ax.getRange().isDataType()) {
                    map.put(ax.getProperty(), (OWLDataType) ax.getRange());
                }
            }
        }
        OWLConstantReplacer replacer = new OWLConstantReplacer(getDataFactory());
        for(OWLOntology ont : ontologies) {
            for(OWLAxiom ax : ont.getLogicalAxioms()) {
                OWLAxiom dupAx = replacer.duplicateObject(ax);
                if(!ax.equals(dupAx)) {
                    changes.add(new RemoveAxiom(ont, ax));
                    changes.add(new AddAxiom(ont, dupAx));
                }
            }
        }
    }


    public List<OWLOntologyChange> getChanges() {
        return changes;
    }


    private class OWLConstantReplacer extends OWLObjectDuplicator {

        public OWLConstantReplacer(OWLDataFactory dataFactory) {
            super(dataFactory);
        }

        private OWLDataOneOf process(OWLDataPropertyExpression prop, OWLDataOneOf oneOf) {
            Set<OWLConstant> vals = new HashSet<OWLConstant>();
            for(OWLConstant con : oneOf.getValues()) {
                vals.add(process(prop, con));
            }
            return getDataFactory().getOWLDataOneOf(vals);
        }

        private OWLConstant process(OWLDataPropertyExpression prop, OWLConstant con) {
            OWLDataType dt = map.get(prop);
            if(dt != null) {
                return getDataFactory().getOWLTypedConstant(con.getLiteral(), dt);
            }
            else {
                return con;
            }
        }

        public void visit(OWLDataValueRestriction desc) {
            super.visit(desc);
            setLastObject(getDataFactory().getOWLDataValueRestriction(desc.getProperty(), process(desc.getProperty(), desc.getValue())));
        }


        public void visit(OWLDataSomeRestriction desc) {
            super.visit(desc);
            if(desc instanceof OWLDataOneOf) {
                setLastObject(getDataFactory().getOWLDataSomeRestriction(desc.getProperty(), process(desc.getProperty(), (OWLDataOneOf) desc.getFiller())));
            }
        }


        public void visit(OWLDataMinCardinalityRestriction desc) {
            super.visit(desc);
            if(desc instanceof OWLDataOneOf) {
                setLastObject(getDataFactory().getOWLDataMinCardinalityRestriction(desc.getProperty(),
                                                                                   desc.getCardinality(),
                                                                                   process(desc.getProperty(), (OWLDataOneOf) desc.getFiller())));
            }

        }


        public void visit(OWLDataMaxCardinalityRestriction desc) {
            super.visit(desc);
            if(desc instanceof OWLDataOneOf) {
                setLastObject(getDataFactory().getOWLDataMaxCardinalityRestriction(desc.getProperty(),
                                                                                   desc.getCardinality(),
                                                                                   process(desc.getProperty(), (OWLDataOneOf) desc.getFiller())));
            }
        }


        public void visit(OWLDataExactCardinalityRestriction desc) {
            super.visit(desc);
            if(desc instanceof OWLDataOneOf) {
                setLastObject(getDataFactory().getOWLDataExactCardinalityRestriction(desc.getProperty(),
                                                                                   desc.getCardinality(),
                                                                                   process(desc.getProperty(), (OWLDataOneOf) desc.getFiller())));
            }
        }


        public void visit(OWLDataAllRestriction desc) {
            super.visit(desc);
            if(desc instanceof OWLDataOneOf) {
                setLastObject(getDataFactory().getOWLDataAllRestriction(desc.getProperty(), process(desc.getProperty(), (OWLDataOneOf) desc.getFiller())));
            }
        }


        public void visit(OWLDataPropertyAssertionAxiom axiom) {
            super.visit(axiom);
            setLastObject(getDataFactory().getOWLDataPropertyAssertionAxiom(axiom.getSubject(),
                                                                            axiom.getProperty(),
                                                                            process(axiom.getProperty(),
                                                                                    axiom.getObject())));
        }


        public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            super.visit(axiom);
            setLastObject(getDataFactory().getOWLNegativeDataPropertyAssertionAxiom(axiom.getSubject(),
                                                                            axiom.getProperty(),
                                                                            process(axiom.getProperty(),
                                                                                    axiom.getObject())));
        }
    }
}
