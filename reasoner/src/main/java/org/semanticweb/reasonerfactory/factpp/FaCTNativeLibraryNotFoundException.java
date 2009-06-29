package org.semanticweb.reasonerfactory.factpp;

import org.semanticweb.owlapi.model.OWLRuntimeException;
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
 * 08-Sep-2008<br><br>
 */
public class FaCTNativeLibraryNotFoundException extends OWLRuntimeException {


    public FaCTNativeLibraryNotFoundException() {
        super("The FaCT++ native library could not be found.  Please ensure that the library is in your java.library.path. (Add '-Djava.library.path=\"<Path_to_directory_containing_library>\"' to the Java Virtual Machine arguments)");
    }
}
