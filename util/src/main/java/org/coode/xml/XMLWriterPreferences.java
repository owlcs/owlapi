package org.coode.xml;

/**
 * Copyright (C) 2006, Matthew Horridge, University of Manchester
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
 * Medical Informatics Group<br>
 * Date: 30-May-2006<br><br>
 * <p/>
 * matthew.horridge@cs.man.ac.uk<br>
 * www.cs.man.ac.uk/~horridgm<br><br>
 *
 * Developed as part of the CO-ODE project
 * http://www.co-ode.org
 */
public class XMLWriterPreferences {

    private static XMLWriterPreferences instance;

    private boolean useNamespaceEntities;

    private boolean indenting;

    private int indentSize;


    private XMLWriterPreferences() {
        useNamespaceEntities = false;
        indenting = true;
        indentSize = 4;
    }


    public static XMLWriterPreferences getInstance() {
        if (instance == null) {
            instance = new XMLWriterPreferences();
        }
        return instance;
    }


    public boolean isUseNamespaceEntities() {
        return useNamespaceEntities;
    }


    public void setUseNamespaceEntities(boolean useNamespaceEntities) {
        this.useNamespaceEntities = useNamespaceEntities;
    }


    public boolean isIndenting() {
        return indenting;
    }


    public void setIndenting(boolean indenting) {
        this.indenting = indenting;
    }


    public int getIndentSize() {
        return indentSize;
    }


    public void setIndentSize(int indentSize) {
        this.indentSize = indentSize;
    }
}
