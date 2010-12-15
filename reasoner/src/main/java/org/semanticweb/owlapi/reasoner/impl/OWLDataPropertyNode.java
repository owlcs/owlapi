package org.semanticweb.owlapi.reasoner.impl;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLDataProperty;
import org.semanticweb.owlapi.vocab.OWLDataFactoryVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Dec-2009
 */
public class OWLDataPropertyNode extends DefaultNode<OWLDataProperty> {
    
    
    private static final OWLDataProperty TOP_DATA_PROPERTY = OWLDataFactoryVocabulary.OWLTopDataProperty;
    
    private static final OWLDataPropertyNode TOP_NODE = new OWLDataPropertyNode(TOP_DATA_PROPERTY);

    private static final OWLDataProperty BOTTOM_DATA_PROPERTY = OWLDataFactoryVocabulary.OWLBottomDataProperty;
    
    private static final OWLDataPropertyNode BOTTOM_NODE = new OWLDataPropertyNode(BOTTOM_DATA_PROPERTY);

    public OWLDataPropertyNode() {
    }

    public OWLDataPropertyNode(OWLDataProperty entity) {
        super(entity);
    }

    public OWLDataPropertyNode(Set<OWLDataProperty> entities) {
        super(entities);
    }

    @Override
	protected OWLDataProperty getTopEntity() {
        return TOP_DATA_PROPERTY;
    }

    @Override
	protected OWLDataProperty getBottomEntity() {
        return BOTTOM_DATA_PROPERTY;
    }
    
    public static OWLDataPropertyNode getTopNode() {
        return TOP_NODE;
    }
    
    public static OWLDataPropertyNode getBottomNode() {
        return BOTTOM_NODE;
    }
}
