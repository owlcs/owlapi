package org.coode.obo.renderer.test;

import junit.framework.TestCase;
import org.coode.obo.parser.OBOOntologyFormat;
import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.io.OWLOntologyInputSource;
import org.semanticweb.owl.io.StreamInputSource;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.owl.model.OWLOntologyManager;

import java.io.File;
import java.io.InputStream;
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
 * Author: drummond<br>
 * http://www.cs.man.ac.uk/~drummond/<br><br>
 * <p/>
 * The University Of Manchester<br>
 * Bio Health Informatics Group<br>
 * Date: Dec 18, 2008<br><br>
 */
public class TestOBOFlatFileRenderer extends TestCase {

    public void testSavePizza(){
        OWLOntologyManager mngr = OWLManager.createOWLOntologyManager();
        try {
            InputStream is = getClass().getClassLoader().getResourceAsStream("pizza.owl");
            OWLOntologyInputSource ontInputSource = new StreamInputSource(is);
            OWLOntology ont = mngr.loadOntology(ontInputSource);

            mngr.saveOntology(ont, new OBOOntologyFormat(), new File("pizza.obo").toURI());
            System.out.println("done!");
        }
        catch (Exception e) {
            e.printStackTrace();
            fail();
        }
    }
}
