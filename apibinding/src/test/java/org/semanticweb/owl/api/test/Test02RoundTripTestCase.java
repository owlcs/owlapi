package org.semanticweb.owl.api.test;

import org.semanticweb.owl.io.StringOutputTarget;
import org.semanticweb.owl.model.OWLOntologyFormat;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
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
 * Author: Matthew Horridge<br> The University Of Manchester<br> Information Management Group<br> Date:
 * 23-Jul-2008<br><br>
 */
public class Test02RoundTripTestCase extends AbstractFileRoundTrippingTestCase {


    protected String getFileName() {
        return "Test02.rdf";
    }

    protected void handleSaved(StringOutputTarget target, OWLOntologyFormat format) {
            try {
                BufferedWriter fw = new BufferedWriter(new FileWriter("/tmp/out.txt"));
                fw.write(target.toString());
                fw.flush();
                fw.close();
            }
            catch (IOException e) {
                e.printStackTrace();
            }
    }

    
}
