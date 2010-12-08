package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLDataPropertyExpression;
import org.semanticweb.owlapi.model.OWLObjectPropertyExpression;

/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 09-Jun-2009
 */
public class BuiltInPropertyTestCase extends AbstractOWLAPITestCase {

    public void testTopObjectPropertyPositive() {
        OWLObjectPropertyExpression prop = getFactory().getOWLTopObjectProperty();
        assertTrue(prop.isOWLTopObjectProperty());
    }

    public void testBottomObjectPropertyPositive() {
        OWLObjectPropertyExpression prop = getFactory().getOWLBottomObjectProperty();
        assertTrue(prop.isOWLBottomObjectProperty());
    }


    public void testTopObjectPropertyNegative() {
        OWLObjectPropertyExpression prop = getOWLObjectProperty("prop");
        assertFalse(prop.isOWLTopObjectProperty());
    }

    public void testBottomObjectPropertyNegative() {
        OWLObjectPropertyExpression prop = getOWLObjectProperty("prop");
        assertFalse(prop.isOWLBottomObjectProperty());
    }

    public void testTopDataPropertyPositive() {
        OWLDataPropertyExpression prop = getFactory().getOWLTopDataProperty();
        assertTrue(prop.isOWLTopDataProperty());
    }

    public void testBottomDataPropertyPositive() {
        OWLDataPropertyExpression prop = getFactory().getOWLBottomDataProperty();
        assertTrue(prop.isOWLBottomDataProperty());
    }


    public void testTopDataPropertyNegative() {
        OWLDataPropertyExpression prop = getOWLDataProperty("prop");
        assertFalse(prop.isOWLTopDataProperty());
    }

    public void testBottomDataPropertyNegative() {
        OWLDataPropertyExpression prop = getOWLDataProperty("prop");
        assertFalse(prop.isOWLBottomDataProperty());
    }
}
