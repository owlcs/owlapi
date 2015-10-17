/* This file is part of the OWL API.
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * Copyright 2014, The University of Manchester
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software Foundation, either version 3 of the License, or (at your option) any later version.
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License for more details.
 * You should have received a copy of the GNU General Public License along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except in compliance with the License. You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License. */
package uk.ac.manchester.cs.owl.owlapi;

import static org.semanticweb.owlapi.util.OWLAPIPreconditions.checkNotNull;

import javax.annotation.Nullable;

import org.semanticweb.owlapi.model.OWLObjectProperty;
import org.semanticweb.owlapi.model.SWRLIArgument;
import org.semanticweb.owlapi.model.SWRLSameIndividualAtom;
import org.semanticweb.owlapi.util.OWLObjectTypeIndexProvider;

/**
 * @author Matthew Horridge, The University Of Manchester, Bio-Health
 *         Informatics Group
 * @since 2.0.0
 */
public class SWRLSameIndividualAtomImpl extends SWRLBinaryAtomImpl<SWRLIArgument, SWRLIArgument>
        implements SWRLSameIndividualAtom {

    @Override
    protected int index() {
        return OWLObjectTypeIndexProvider.RULE_OBJECT_TYPE_INDEX_BASE + 9;
    }

    /**
     * @param property
     *        property
     * @param arg0
     *        first individual
     * @param arg1
     *        second individual
     */
    public SWRLSameIndividualAtomImpl(OWLObjectProperty property, SWRLIArgument arg0, SWRLIArgument arg1) {
        super(checkNotNull(property, "property cannot be null"), checkNotNull(arg0, "arg0 cannot be null"),
                checkNotNull(arg1, "arg1 cannot be null"));
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof SWRLSameIndividualAtom)) {
            return false;
        }
        SWRLSameIndividualAtom other = (SWRLSameIndividualAtom) obj;
        return other.getAllArguments().equals(getAllArguments());
    }
}
