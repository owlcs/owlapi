package org.semanticweb.owlapi;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.AxiomType;
import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLDataAllValuesFrom;
import org.semanticweb.owlapi.model.OWLDataExactCardinality;
import org.semanticweb.owlapi.model.OWLDataFactory;
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
import org.semanticweb.owlapi.model.OWLNegativeDataPropertyAssertionAxiom;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyChange;
import org.semanticweb.owlapi.model.RemoveAxiom;
import org.semanticweb.owlapi.util.OWLObjectDuplicator;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 13-Aug-2007<br><br>
 * <p/>
 * Coerces constants to have the same type as the range of a property in
 * axioms where the two are used.  For example, given, p value "xyz", the
 * "xyz" constant would be typed with the range of p.
 */
public class CoerceConstantsIntoDataPropertyRange extends AbstractCompositeOntologyChange {

    private Map<OWLDataPropertyExpression, OWLDatatype> map;

    private List<OWLOntologyChange> changes = new ArrayList<OWLOntologyChange>();

    public CoerceConstantsIntoDataPropertyRange(OWLDataFactory dataFactory, Set<OWLOntology> ontologies) {
        super(dataFactory);
        map = new HashMap<OWLDataPropertyExpression, OWLDatatype>();
        for (OWLOntology ont : ontologies) {
            for (OWLDataPropertyRangeAxiom ax : ont.getAxioms(AxiomType.DATA_PROPERTY_RANGE)) {
                if (ax.getRange().isDatatype()) {
                    map.put(ax.getProperty(), (OWLDatatype) ax.getRange());
                }
            }
        }
        OWLConstantReplacer replacer = new OWLConstantReplacer(getDataFactory());
        for (OWLOntology ont : ontologies) {
            for (OWLAxiom ax : ont.getLogicalAxioms()) {
                OWLAxiom dupAx = replacer.duplicateObject(ax);
                if (!ax.equals(dupAx)) {
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
            Set<OWLLiteral> vals = new HashSet<OWLLiteral>();
            for (OWLLiteral con : oneOf.getValues()) {
                vals.add(process(prop, con));
            }
            return getDataFactory().getOWLDataOneOf(vals);
        }

        private OWLLiteral process(OWLDataPropertyExpression prop, OWLLiteral con) {
            OWLDatatype dt = map.get(prop);
            if (dt != null) {
                return getDataFactory().getOWLLiteral(con.getLiteral(), dt);
            }
            else {
                return con;
            }
        }

        @Override
		public void visit(OWLDataHasValue desc) {
            super.visit(desc);
            setLastObject(getDataFactory().getOWLDataHasValue(desc.getProperty(), process(desc.getProperty(), desc.getValue())));
        }


        @Override
		public void visit(OWLDataSomeValuesFrom desc) {
            super.visit(desc);
            if (desc instanceof OWLDataOneOf) {
                setLastObject(getDataFactory().getOWLDataSomeValuesFrom(desc.getProperty(), process(desc.getProperty(), (OWLDataOneOf) desc.getFiller())));
            }
        }


        @Override
		public void visit(OWLDataMinCardinality desc) {
            super.visit(desc);
            if (desc instanceof OWLDataOneOf) {
                setLastObject(getDataFactory().getOWLDataMinCardinality(desc.getCardinality(), desc.getProperty(), process(desc.getProperty(), (OWLDataOneOf) desc.getFiller())));
            }

        }


        @Override
		public void visit(OWLDataMaxCardinality desc) {
            super.visit(desc);
            if (desc instanceof OWLDataOneOf) {
                setLastObject(getDataFactory().getOWLDataMaxCardinality(desc.getCardinality(), desc.getProperty(), process(desc.getProperty(), (OWLDataOneOf) desc.getFiller())));
            }
        }


        @Override
		public void visit(OWLDataExactCardinality desc) {
            super.visit(desc);
            if (desc instanceof OWLDataOneOf) {
                setLastObject(getDataFactory().getOWLDataExactCardinality(desc.getCardinality(), desc.getProperty(), process(desc.getProperty(), (OWLDataOneOf) desc.getFiller())));
            }
        }


        @Override
		public void visit(OWLDataAllValuesFrom desc) {
            super.visit(desc);
            if (desc instanceof OWLDataOneOf) {
                setLastObject(getDataFactory().getOWLDataAllValuesFrom(desc.getProperty(), process(desc.getProperty(), (OWLDataOneOf) desc.getFiller())));
            }
        }


        @Override
		public void visit(OWLDataPropertyAssertionAxiom axiom) {
            super.visit(axiom);
            setLastObject(getDataFactory().getOWLDataPropertyAssertionAxiom(axiom.getProperty(), axiom.getSubject(), process(axiom.getProperty(), axiom.getObject())));
        }


        @Override
		public void visit(OWLNegativeDataPropertyAssertionAxiom axiom) {
            super.visit(axiom);
            setLastObject(getDataFactory().getOWLNegativeDataPropertyAssertionAxiom(axiom.getProperty(), axiom.getSubject(), process(axiom.getProperty(), axiom.getObject())));
        }
    }
}
