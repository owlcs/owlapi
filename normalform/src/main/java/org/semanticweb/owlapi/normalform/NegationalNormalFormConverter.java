package org.semanticweb.owlapi.normalform;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLClassExpression;
import org.semanticweb.owlapi.util.NNF;
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
 * Date: 24-Sep-2007<br><br>
 */
public class NegationalNormalFormConverter implements NormalFormRewriter {

    private NNF nnf;

    private OWLObjectComplementOfExtractor extractor;


    public NegationalNormalFormConverter(OWLDataFactory dataFactory) {
        nnf = new NNF(dataFactory);
        extractor = new OWLObjectComplementOfExtractor();
    }


    public boolean isInNormalForm(OWLClassExpression classExpression) {
        // The classExpression is in negational normal form if negations
        // only appear in front of named concepts
        extractor.getComplementedClassExpressions(classExpression);
        for (OWLClassExpression desc : extractor.getComplementedClassExpressions(classExpression)) {
            if (desc.isAnonymous()) {
                return false;
            }
        }
        return true;
    }


    public OWLClassExpression convertToNormalForm(OWLClassExpression classExpression) {
        nnf.reset();
        return classExpression.accept(nnf);
    }


}
