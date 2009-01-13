package uk.ac.manchester.cs.owl.inference.dig11;

import java.util.Collection;
import java.util.List;
/*
 * Copyright (C) 2006, University of Manchester
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
 * Date: 21-Nov-2006<br><br>
 */
public class DIGErrorException extends Exception {

    private List errorList;


    public DIGErrorException(List errorList) {
        super(((DIGError) (errorList.get(0))).getMessage());
        this.errorList = errorList;
    }


    /**
     * Gets the exception code associated with this
     * DIGErrorException
     *
     * @return An integer exception code.
     */
    public int getErrorCode(int index) {
        return ((DIGError) errorList.get(index)).getErrorCode();
    }


    /**
     * Gets the exception message of the specified
     * exception.
     *
     * @param index The index of the exception
     * @return The exception message
     */
    public String getMessage(int index) {
        return ((DIGError) errorList.get(index)).getMessage();
    }


    /**
     * Gets the <code>DIGError</code> object
     * associated with this exception.
     */
    public DIGError getDIGError(int index) {
        return (DIGError) errorList.get(index);
    }


    /**
     * Gets the number of DIGErrors
     */
    public int getNumberOfErrors() {
        return errorList.size();
    }


    public Collection getErrors() {
        return errorList;
    }
}
