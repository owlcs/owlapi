package org.semanticweb.owlapi.reasoner.impl;

import java.util.Set;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;
import org.semanticweb.owlapi.vocab.OWLDataFactoryVocabulary;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 05-Dec-2009
 */
public class OWLObjectPropertyNode extends DefaultNode<OWLObjectPropertyExpression> {


    private static final OWLObjectProperty TOP_OBJECT_PROPERTY = OWLDataFactoryVocabulary.OWLTopObjectProperty;

    private static final OWLObjectPropertyNode TOP_NODE = new OWLObjectPropertyNode(TOP_OBJECT_PROPERTY);

    private static final OWLObjectProperty BOTTOM_OBJECT_PROPERTY = OWLDataFactoryVocabulary.OWLBottomObjectProperty;

    private static final OWLObjectPropertyNode BOTTOM_NODE = new OWLObjectPropertyNode(BOTTOM_OBJECT_PROPERTY);

    public OWLObjectPropertyNode() {
    }

    public OWLObjectPropertyNode(OWLObjectPropertyExpression entity) {
        super(entity);
    }

    public OWLObjectPropertyNode(Set<OWLObjectPropertyExpression> entities) {
        super(entities);
    }

    @Override
	protected OWLObjectProperty getTopEntity() {
        return TOP_OBJECT_PROPERTY;
    }

    @Override
	protected OWLObjectProperty getBottomEntity() {
        return BOTTOM_OBJECT_PROPERTY;
    }
    
    public static OWLObjectPropertyNode getTopNode() {
        return TOP_NODE;
    }
    
    public static OWLObjectPropertyNode getBottomNode() {
        return BOTTOM_NODE;
    }
}
