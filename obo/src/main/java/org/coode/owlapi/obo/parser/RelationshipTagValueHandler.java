package org.coode.owlapi.obo.parser;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.semanticweb.owlapi.model.AddAxiom;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLClass;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.model.OWLObjectProperty;

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
 * Date: 06-Mar-2007<br><br>
 */
public class RelationshipTagValueHandler extends AbstractTagValueHandler {

    private Pattern tagValuePattern = Pattern.compile("([^\\s]*)\\s*([^\\s]*)\\s*(\\{([^\\}]*)\\})?");

    public RelationshipTagValueHandler(OBOConsumer consumer) {
        super(OBOVocabulary.RELATIONSHIP.getName(), consumer);
    }


    public void handle(String id, String value, String comment) {
        Matcher matcher = tagValuePattern.matcher(value);
        if(matcher.matches()) {
            IRI propIRI = getIdIRI(matcher.group(1));
            IRI fillerIRI = getIdIRI(matcher.group(2));
          //  String modifier = matcher.group(3);
            OWLObjectProperty prop = getDataFactory().getOWLObjectProperty(propIRI);
            OWLClass filler = getDataFactory().getOWLClass(fillerIRI);
            OWLClassExpression restriction = getDataFactory().getOWLObjectSomeValuesFrom(prop, filler);
            OWLClass subCls = getDataFactory().getOWLClass(getIdIRI(id));
            applyChange(new AddAxiom(getOntology(), getDataFactory().getOWLSubClassOfAxiom(subCls, restriction)));
            applyChange(new AddAxiom(getOntology(), getDataFactory().getOWLDeclarationAxiom(prop)));
        }

    }
}
