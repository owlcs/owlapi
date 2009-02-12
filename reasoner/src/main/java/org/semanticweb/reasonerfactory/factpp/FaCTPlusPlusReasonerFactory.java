package org.semanticweb.reasonerfactory.factpp;

import org.semanticweb.owl.apibinding.OWLManager;
import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerFactory;
import org.semanticweb.owl.inference.OWLReasonerException;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.owl.model.OWLOntology;
import org.semanticweb.reasonerfactory.OWLReasonerSetupException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Set;

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
public class FaCTPlusPlusReasonerFactory implements OWLReasonerFactory {

    private Constructor factPPConstructor;

    private Method setSynchronisingMethod;


    public FaCTPlusPlusReasonerFactory() {
        try {
            Class faCTPPClass = Class.forName("uk.ac.manchester.cs.factplusplus.owlapi.Reasoner");
            factPPConstructor = faCTPPClass.getConstructor(OWLOntologyManager.class);
            setSynchronisingMethod = faCTPPClass.getMethod("setSychroniseOnlyOnClassify", Boolean.TYPE);
        }
        catch (ClassNotFoundException e) {
            throw new OWLReasonerSetupException(this, e);
        }
        catch (NoSuchMethodException e) {
            throw new OWLReasonerSetupException(this, e);
        }
    }


    public String getReasonerName() {
        return "FaCT++";
    }


    public OWLReasoner createReasoner(OWLOntologyManager manager, Set<OWLOntology> ontologies) throws OWLReasonerSetupException {
        try {
            OWLReasoner reasoner = (OWLReasoner) factPPConstructor.newInstance(manager);
            setSynchronisingMethod.invoke(reasoner, Boolean.FALSE);
            reasoner.loadOntologies(ontologies);
            return reasoner;
        }
        catch (InstantiationException e) {
            throw new OWLReasonerSetupException(this, e);
        }
        catch (IllegalAccessException e) {
            throw new OWLReasonerSetupException(this, e);
        }
        catch (InvocationTargetException e) {
            throw new OWLReasonerSetupException(this, e);
        }
        catch(UnsatisfiedLinkError e) {
            throw new FaCTNativeLibraryNotFoundException();
        } catch (OWLReasonerException e) {
            throw new OWLReasonerSetupException(this, e);
        }
    }

}
