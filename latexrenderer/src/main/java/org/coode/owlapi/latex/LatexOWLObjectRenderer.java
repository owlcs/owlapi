package org.coode.owlapi.latex;

import org.semanticweb.owlapi.io.OWLObjectRenderer;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLObject;
import org.semanticweb.owlapi.util.ShortFormProvider;

import java.io.StringWriter;
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
 * Date: 25-Nov-2007<br><br>
 */
public class LatexOWLObjectRenderer implements OWLObjectRenderer {

    private OWLDataFactory dataFactory;

    private ShortFormProvider shortFormProvider;

    public LatexOWLObjectRenderer(OWLDataFactory dataFactory) {
        this.dataFactory = dataFactory;
    }


    public String render(OWLObject object) {
        StringWriter writer = new StringWriter();
        LatexWriter latexWriter = new LatexWriter(writer);
        LatexObjectVisitor visitor = new LatexObjectVisitor(latexWriter, dataFactory);

        object.accept(visitor);
        return writer.getBuffer().toString();
    }


    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
        this.shortFormProvider = shortFormProvider;
    }

}
