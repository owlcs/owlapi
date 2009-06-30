package org.coode.owlapi.obo.renderer;

import org.semanticweb.owlapi.model.*;
import org.semanticweb.owlapi.util.OWLClassExpressionVisitorAdapter;

import java.util.HashSet;
import java.util.Set;
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
public class OBORelationshipGenerator extends OWLClassExpressionVisitorAdapter {

    private Set<OBORelationship> relationships = new HashSet<OBORelationship>();

    private OBOExceptionHandler eHandler;

    private OWLClass cls;


    public OBORelationshipGenerator(OBOExceptionHandler eHandler) {
        this.eHandler = eHandler;
    }


    public void setClass(OWLClass cls) {
        this.cls = cls;
        clear();
    }


    public void clear() {
        relationships.clear();
    }


    public Set<OBORelationship> getOBORelationships() {
        return new HashSet<OBORelationship>(relationships);
    }


    public void visit(OWLObjectSomeValuesFrom desc) {
        getRelationship(desc);
    }


    public void visit(OWLObjectMinCardinality desc) {
        OBORelationship rel = getRelationship(desc);
        if (rel != null) {
            rel.setMinCardinality(desc.getCardinality());
        }
    }


    public void visit(OWLObjectExactCardinality desc) {
        OBORelationship rel = getRelationship(desc);
        if (rel != null) {
            rel.setCardinality(desc.getCardinality());
        }
    }


    public void visit(OWLObjectMaxCardinality desc) {
        OBORelationship rel = getRelationship(desc);
        if (rel != null) {
            rel.setMaxCardinality(desc.getCardinality());
        }
    }

    // TODO error handling for un-translatable class expressions


    private OBORelationship getRelationship(OWLQuantifiedRestriction<OWLObjectPropertyExpression, OWLClassExpression> desc) {
        if (desc.isAnonymous() && !desc.getFiller().isAnonymous()) {
            final OWLObjectProperty p = desc.getProperty().asOWLObjectProperty();
            final OWLClass f = desc.getFiller().asOWLClass();

            for (OBORelationship rel : relationships) {
                if (rel.getProperty().equals(p) && rel.getFiller().equals(f)) {
                    return rel;
                }
            }
            final OBORelationship newRel = new OBORelationship(p, f);
            relationships.add(newRel);
            return newRel;
        }

        eHandler.addException(new OBOStorageException(cls, desc, "Anonymous filler of some restriction cannot be converted to OBO"));
        return null;
    }
}
