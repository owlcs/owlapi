package uk.ac.manchester.owl.tutorial;

import org.semanticweb.owl.model.OWLAnnotationVisitor;
import org.semanticweb.owl.model.OWLConstant;
import org.semanticweb.owl.model.OWLConstantAnnotation;
import org.semanticweb.owl.model.OWLObjectAnnotation;

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
 * <p>Simple visitor that grabs any labels on an entity.</p>
 * 
 * Author: Sean Bechhofer<br>
 * The University Of Manchester<br>
 * Information Management Group<br>
 * Date: 17-03-2007<br>
 * <br>
 */
public class LabelExtractor implements OWLAnnotationVisitor {

    String result;

    public LabelExtractor() {
        result = null;
    }

    public void visit(OWLConstantAnnotation annotation) {
        /*
         * If it's a label, grab it as the result. Note that if there are
         * multiple labels, the last one will be used.
         */
        if (annotation.isLabel()) {
            OWLConstant c = annotation.getAnnotationValue();
            result = c.getLiteral();
        }
    }

    public void visit(OWLObjectAnnotation annotation) {
    }

    public String getResult() {
        return result;
    }
}
