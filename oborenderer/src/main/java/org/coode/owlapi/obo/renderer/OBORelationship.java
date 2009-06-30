package org.coode.owlapi.obo.renderer;

import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLEntity;
import org.semanticweb.owlapi.model.OWLNamedIndividual;
import org.semanticweb.owlapi.model.OWLObjectProperty;
/*
* Copyright (C) 2008, University of Manchester
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
 * Author: Nick Drummond<br>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Dec 18, 2008<br><br>
 */
public class OBORelationship {

    private OWLObjectProperty property;

    private int minCardinality = -1;
    private int maxCardinality = -1;
    private int cardinality = -1;

    private OWLEntity filler;

    public OBORelationship(OWLObjectProperty property, OWLNamedIndividual filler) {
        this.property = property;
        this.filler = filler;
    }

    public OBORelationship(OWLObjectProperty property, OWLClass filler) {
        this.property = property;
        this.filler = filler;
    }

    public OWLObjectProperty getProperty() {
        return property;
    }


    public int getMinCardinality() {
        return minCardinality;
    }


    public OWLEntity getFiller() {
        return filler;
    }


    public int getMaxCardinality() {
        return maxCardinality;
    }


    public void setMaxCardinality(int maxCardinality) {
        this.maxCardinality = maxCardinality;
    }


    public void setMinCardinality(int minCardinality) {
        this.minCardinality = minCardinality;
    }


    public int getCardinality() {
        return cardinality;
    }


    public void setCardinality(int cardinality) {
        this.cardinality = cardinality;
    }
}
