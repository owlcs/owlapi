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
package org.semanticweb.owlapitools.profiles;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;

import com.google.inject.Provides;

/**
 * This enumeration includes all currently implemented profile checkers and
 * known information about available reasoners for those profiles. Note that
 * reasoner capabilities might be out of date, since they are independent
 * projects. Therefore, ther emight be reasoners not listed here and the
 * reasoners listed might have changed.<br>
 * The use case for this class was suggested by Peter Ansell, see <a href=
 * "https://github.com/ansell/owlapi/commit/fa88c8139fe3d59ea46d363a9c9a36f6ddf05119"
 * >patch set 1</a> and <a href=
 * "https://github.com/ansell/owlapi/commit/354885abd0f581942a1ac1d64ae8de4b85cbec7f"
 * >patch set 2</a>.
 * 
 * @author ignazio
 */
public enum Profiles implements HasIRI {
    //@formatter:off
    /** http://www.w3.org/ns/owl-profile/DL **/     OWL2_DL     (IRI.create("http://www.w3.org/ns/owl-profile/","DL"))   { @Override public OWLProfile getOWLProfile() { return new OWL2DLProfile(); }

    @Override
    public Collection<String> supportingReasoners() {
        return Arrays.asList("uk.ac.manchester.cs.factplusplus.owlapiv3.FaCTPlusPlusReasonerFactory", "org.semanticweb.HermiT.Reasoner.ReasonerFactory", "uk.ac.manchester.cs.jfact.JFactFactory",
                "eu.trowl.owlapi3.rel.reasoner.dl.RELReasonerFactory", "com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory", "org.semanticweb.more.MOReRLrewReasonerFactory");
    } },
    /** http://www.w3.org/ns/owl-profile/QL **/     OWL2_QL     (IRI.create("http://www.w3.org/ns/owl-profile/","QL"))   { @Override public OWLProfile getOWLProfile() { return new OWL2QLProfile(); } },
    /** http://www.w3.org/ns/owl-profile/EL **/     OWL2_EL     (IRI.create("http://www.w3.org/ns/owl-profile/","EL"))   { @Override public OWLProfile getOWLProfile() { return new OWL2ELProfile(); }
    @Override public Collection<String> supportingReasoners() {
        List<String> list=new ArrayList<String>(OWL2_DL.supportingReasoners());
        list.add(0, "org.semanticweb.elk.owlapi.ElkReasonerFactory");
        return list;
    } },
    /** http://www.w3.org/ns/owl-profile/RL **/     OWL2_RL     (IRI.create("http://www.w3.org/ns/owl-profile/","RL"))   { @Override public OWLProfile getOWLProfile() { return new OWL2RLProfile(); } },
    /** http://www.w3.org/ns/owl-profile/Full **/   OWL2_FULL   (IRI.create("http://www.w3.org/ns/owl-profile/","Full")) { @Override public OWLProfile getOWLProfile() { return new OWL2DLProfile(); } };
    //@formatter:on
    private IRI iri;

    private Profiles(IRI iri) {
        this.iri = iri;
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    /**
     * factory method for OWLProfile checkers
     * 
     * @return profile checker for this profile
     */
    @Provides
    public abstract OWLProfile getOWLProfile();

    /**
     * @return collection of OWLReasonerFactory class names known to support the
     *         expressivity of this profile. The factories can be instantiated
     *         through {@code instantiateFactory()} if the reasoner classes are
     *         on the classpath. Note that this list is provided for information
     *         only, and might be incorrect or incomplete due to changes in the
     *         reasoner implementations.<br>
     *         Should you know of a reasoner not mentioned here, or find an
     *         error in the reported supported profiles, please raise a bug
     *         about it.
     */
    public Collection<String> supportingReasoners() {
        return OWL2_DL.supportingReasoners();
    }

    /**
     * @param factoryClassName
     *        class name to instantiate
     * @return an OWLReasonerFactory if the class name represents an
     *         OWLReasonerFactory implementation available on the classpath. Any
     *         exception raised by {@code Class.forName(factoryClassName)} is
     *         wrapped by an OWLRuntimeException.
     */
    public OWLReasonerFactory instantiateFactory(String factoryClassName) {
        try {
            Class<?> c = Class.forName(factoryClassName);
            if (OWLReasonerFactory.class.isAssignableFrom(c)) {
                return (OWLReasonerFactory) c.newInstance();
            }
            throw new OWLRuntimeException(
                    "Reasoner factory cannot be instantiated: "
                            + factoryClassName);
        } catch (ClassNotFoundException e) {
            throw new OWLRuntimeException(
                    "Reasoner factory cannot be instantiated: "
                            + factoryClassName, e);
        } catch (InstantiationException e) {
            throw new OWLRuntimeException(
                    "Reasoner factory cannot be instantiated: "
                            + factoryClassName, e);
        } catch (IllegalAccessException e) {
            throw new OWLRuntimeException(
                    "Reasoner factory cannot be instantiated: "
                            + factoryClassName, e);
        }
    }
}
