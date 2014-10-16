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
package org.semanticweb.owlapi.profiles;

import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

import javax.annotation.Nonnull;

import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.IRI;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLRuntimeException;
import org.semanticweb.owlapi.reasoner.OWLReasonerFactory;
import org.semanticweb.owlapi.util.CollectionFactory;

import com.google.inject.Provides;

/*
 * Not a pretty pattern but I didn't want to have long strings repeated across
 * constructors, and no static constants are allowed before members declaration
 * in an enum.
 */
interface KnownFactories {

    String FaCTPlusPlus = "uk.ac.manchester.cs.factplusplus.owlapiv3.FaCTPlusPlusReasonerFactory";
    String HermiT = "org.semanticweb.HermiT.Reasoner.ReasonerFactory";
    String JFact = "uk.ac.manchester.cs.jfact.JFactFactory";
    String TrOWL = "eu.trowl.owlapi3.rel.reasoner.dl.RELReasonerFactory";
    String Pellet = "com.clarkparsia.pellet.owlapiv3.PelletReasonerFactory";
    String MORe = "org.semanticweb.more.MOReRLrewReasonerFactory";
    String Elk = "org.semanticweb.elk.owlapi.ElkReasonerFactory";
    String Snorocket = "au.csiro.snorocket.owlapi.SnorocketReasonerFactory";
}

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
 * >patch set 2</a>.<br>
 * Notice that the OWLProfiles referred here are stateless, therefore only one
 * instance needs to be created and can be reused across threads.
 * 
 * @author ignazio
 */
public enum Profiles implements HasIRI, KnownFactories, OWLProfile {
    //@formatter:off
    /** http://www.w3.org/ns/owl-profile/DL. **/     OWL2_DL     ("DL",                   FaCTPlusPlus, HermiT, JFact, TrOWL, Pellet, MORe){ @Override public OWLProfile getOWLProfile() { return new OWL2DLProfile();} },
    /** http://www.w3.org/ns/owl-profile/QL. **/     OWL2_QL     ("QL",                   FaCTPlusPlus, HermiT, JFact, TrOWL, Pellet, MORe){ @Override public OWLProfile getOWLProfile() { return new OWL2QLProfile();} },
    /** http://www.w3.org/ns/owl-profile/EL. **/     OWL2_EL     ("EL",   Elk, Snorocket, FaCTPlusPlus, HermiT, JFact, TrOWL, Pellet, MORe){ @Override public OWLProfile getOWLProfile() { return new OWL2ELProfile();} },
    /** http://www.w3.org/ns/owl-profile/RL. **/     OWL2_RL     ("RL",                   FaCTPlusPlus, HermiT, JFact, TrOWL, Pellet, MORe){ @Override public OWLProfile getOWLProfile() { return new OWL2RLProfile();} },
    /** http://www.w3.org/ns/owl-profile/Full. **/   OWL2_FULL   ("Full",                 FaCTPlusPlus, HermiT, JFact, TrOWL, Pellet, MORe){ @Override public OWLProfile getOWLProfile() { return new OWL2DLProfile();} };
    //@formatter:on
    @Nonnull
    private final IRI iri;
    @Nonnull
    private final List<String> supportingFactories;

    Profiles(@Nonnull String name, @Nonnull String... supportingFactories) {
        iri = IRI.create("http://www.w3.org/ns/owl-profile/", name);
        this.supportingFactories = CollectionFactory.list(supportingFactories);
    }

    @Override
    public String getName() {
        return getOWLProfile().getName();
    }

    @Override
    public OWLProfileReport checkOntology(OWLOntology ontology) {
        return getOWLProfile().checkOntology(ontology);
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    /**
     * Factory method for OWLProfile checkers.
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
        return supportingFactories;
    }

    /**
     * @param factoryClassName
     *        class name to instantiate
     * @return an OWLReasonerFactory if the class name represents an
     *         OWLReasonerFactory implementation available on the classpath. Any
     *         exception raised by {@code Class.forName(factoryClassName)} is
     *         wrapped by an OWLRuntimeException.
     */
    public static OWLReasonerFactory
            instantiateFactory(String factoryClassName) {
        try {
            Class<?> c = Class.forName(factoryClassName);
            if (OWLReasonerFactory.class.isAssignableFrom(c)) {
                return (OWLReasonerFactory) c.newInstance();
            }
            throw new OWLRuntimeException(
                    "Reasoner factory cannot be instantiated: "
                            + factoryClassName);
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException e) {
            throw new OWLRuntimeException(
                    "Reasoner factory cannot be instantiated: "
                            + factoryClassName, e);
        }
    }

    /**
     * @param i
     *        IRI to match
     * @return Profiles with matching IRI, or null if none is found
     */
    public static Profiles valueForIRI(IRI i) {
        return Stream.of(values()).filter(p -> p.iri.equals(i)).findAny()
                .orElse(null);
    }
}
