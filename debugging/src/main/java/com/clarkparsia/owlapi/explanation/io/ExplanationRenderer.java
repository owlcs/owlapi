package com.clarkparsia.owlapi.explanation.io;

import org.semanticweb.owlapi.model.OWLAxiom;
import org.semanticweb.owlapi.model.OWLException;

import java.io.IOException;
import java.io.Writer;
import java.util.Set;

/*
* Copyright (C) 2007, Clark & Parsia
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
 * <p>Copyright: Copyright (c) 2007</p>
 * <p>Company: Clark & Parsia, LLC. <http://www.clarkparsia.com></p>
 * @author Evren Sirin
 */
public interface ExplanationRenderer {

    public void startRendering(Writer writer) throws OWLException, IOException;


    public void render(OWLAxiom axiom, Set<Set<OWLAxiom>> explanations) throws OWLException, IOException;


    public void endRendering() throws OWLException, IOException;
}
