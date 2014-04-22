/*
 * This file is part of the OWL API.
 * 
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * 
 * Copyright (C) 2011, The University of Queensland
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see http://www.gnu.org/licenses/.
 * 
 * 
 * Alternatively, the contents of this file may be used under the terms of the Apache License,
 * Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable
 * instead of those above.
 * 
 * Copyright 2011, The University of Queensland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.semanticweb.owlapi.rio.utils;

import java.util.Comparator;

import org.openrdf.OpenRDFUtil;
import org.openrdf.model.BNode;
import org.openrdf.model.Literal;
import org.openrdf.model.URI;
import org.openrdf.model.Value;

/**
 * Implements a Comparator for OpenRDF Value objects where:
 * <p>
 * the order for Values is: null &gt; URIs &gt; Literals &gt; BNodes
 * <p>
 * with null Values sorted before others
 * 
 * @author Peter Ansell p_ansell@yahoo.com
 */
public class OWLAPICompatibleValueComparator implements Comparator<Value> {

    private final static int BEFORE = -1;
    private final static int EQUALS = 0;
    private final static int AFTER = 1;

    /**
     * Sorts in the order nulls&gt;URIs&gt;Literals&gt;BNodes
     * <p>
     * This is due to the fact that nulls are only applicable to contexts, and
     * according to the OpenRDF documentation, the type of the null cannot be
     * sufficiently distinguished from any other Value to make an intelligent
     * comparison to other Values,
     * {@link OpenRDFUtil#verifyContextNotNull(org.openrdf.model.Resource...)}
     * <p>
     * BNodes are sorted according to the lexical compare of their identifiers,
     * which provides a way to sort statements with the same BNodes in the same
     * positions, near each other
     * <p>
     * BNode sorting is not specified across sessions
     * 
     * @param first
     *        The value to compare against the second.
     * @param second
     *        The value to compare against the first
     * @return {@link #BEFORE} if the first param is to be ordered before the
     *         second param, {@link #EQUALS} if the two params should be
     *         considered equal, and {@link #AFTER} if the first param should be
     *         ordered after the second param.
     */
    @Override
    public int compare(Value first, Value second) {
        if (first == null) {
            if (second == null) {
                return OWLAPICompatibleValueComparator.EQUALS;
            } else {
                return OWLAPICompatibleValueComparator.BEFORE;
            }
        } else if (second == null) {
            // always sort null Values before others, so if the second is null,
            // but the first
            // wasn't, sort the first after the second
            return OWLAPICompatibleValueComparator.AFTER;
        }
        if (first == second || first.equals(second)) {
            return OWLAPICompatibleValueComparator.EQUALS;
        }
        if (first instanceof URI) {
            if (second instanceof URI) {
                return ((URI) first).stringValue().compareTo(
                        ((URI) second).stringValue());
            } else {
                return OWLAPICompatibleValueComparator.BEFORE;
            }
        } else if (second instanceof URI) {
            // sort URIs before Literals and BNodes
            return OWLAPICompatibleValueComparator.AFTER;
        }
        // they must both be Literal's, so sort based on the lexical value of
        // the Literal
        else if (first instanceof Literal) {
            if (second instanceof Literal) {
                final int stringValueCompare = first.stringValue().compareTo(
                        second.stringValue());
                if (stringValueCompare == OWLAPICompatibleValueComparator.EQUALS) {
                    final URI firstType = ((Literal) first).getDatatype();
                    final URI secondType = ((Literal) second).getDatatype();
                    if (firstType == null) {
                        if (secondType == null) {
                            final String firstLang = ((Literal) first)
                                    .getLanguage();
                            final String secondLang = ((Literal) second)
                                    .getLanguage();
                            if (firstLang == null) {
                                if (null == secondLang) {
                                    return OWLAPICompatibleValueComparator.EQUALS;
                                } else {
                                    return OWLAPICompatibleValueComparator.BEFORE;
                                }
                            } else if (secondLang == null) {
                                return OWLAPICompatibleValueComparator.AFTER;
                            } else {
                                return firstLang.compareTo(secondLang);
                            }
                        } else {
                            return OWLAPICompatibleValueComparator.BEFORE;
                        }
                    } else {
                        if (null == secondType) {
                            return OWLAPICompatibleValueComparator.AFTER;
                        } else {
                            return firstType.stringValue().compareTo(
                                    secondType.stringValue());
                        }
                    }
                } else {
                    return stringValueCompare;
                }
            } else {
                // first is literal so sort it before
                return OWLAPICompatibleValueComparator.BEFORE;
            }
        } else if (second instanceof Literal) {
            // sort all literal before BNodes
            return OWLAPICompatibleValueComparator.BEFORE;
        } else
        // if(first instanceof BNode)
        {
            // if(second instanceof BNode)
            // {
            // if both are BNodes, sort based on the lexical value of the
            // internal ID
            // Although this sorting is not guaranteed to be consistent across
            // sessions,
            // it provides a consistent sorting of statements in every case
            // so that statements with the same BNode are sorted near each other
            return ((BNode) first).getID().compareTo(((BNode) second).getID());
            // }
            // else
            // {
            // return BEFORE;
            // }
        }
        // at this point second must be an instanceof BNode
        // else //if(second instanceof BNode)
        // {
        // // sort BNodes before other things, and first was not a BNode
        // return AFTER;
        // }
    }
}
