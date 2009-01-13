package org.semanticweb.reasonerfactory.pellet;

import org.semanticweb.owl.inference.OWLReasoner;
import org.semanticweb.owl.inference.OWLReasonerFactory;
import org.semanticweb.owl.model.OWLOntologyManager;
import org.semanticweb.reasonerfactory.OWLReasonerSetupException;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * <p/>
 * A reasoner factory for the pellet reasoner.  Note that the appropriate pellet libraries must
 * be in the class path.  By default this class sets tracing enabled and turns off the various
 * pellet logging mechanisms.  There are various static methods to set these options though.
 */
public class PelletReasonerFactory implements OWLReasonerFactory {

    public static final String CLASS_NAME = "org.mindswap.pellet.owlapi.Reasoner";

    private Constructor reasonerConstructor;


    public PelletReasonerFactory() {
        try {
            Class pelletReasonerClass = Class.forName(CLASS_NAME);
            reasonerConstructor = pelletReasonerClass.getConstructor(OWLOntologyManager.class);
            setTracingEnabled(false);
            setUseConsoleProgressMonitor();
        }
        catch (ClassNotFoundException e) {
            throw new OWLReasonerSetupException(this, e);
        }
        catch (NoSuchMethodException e) {
            throw new OWLReasonerSetupException(this, e);
        }
    }


    /**
     * Specifies whether or not tracing should be used.
     *
     * @param b <code>true</code> if tracing should be used, otherwise
     *          <code>false</code>.
     */
    public void setTracingEnabled(boolean b) {
        try {
            Class pelletOptionsClass = Class.forName("org.mindswap.pellet.PelletOptions");
            Field tracingField = pelletOptionsClass.getField("USE_TRACING");
            tracingField.set(pelletOptionsClass, b);
        }
        catch (ClassNotFoundException e) {
            throw new OWLReasonerSetupException(this, e);
        }
        catch (NoSuchFieldException e) {
            throw new OWLReasonerSetupException(this, e);
        }
        catch (IllegalAccessException e) {
            throw new OWLReasonerSetupException(this, e);
        }
    }

    public void setUseConsoleProgressMonitor() {

        try {
            Class pelletOptionsClass = Class.forName("org.mindswap.pellet.PelletOptions");
            Field tracingField = pelletOptionsClass.getField("USE_CLASSIFICATION_MONITOR");
            Class monitorType = Class.forName(pelletOptionsClass.getName() + "$MonitorType");

            Field monitorTypeNoneField = monitorType.getField("NONE");
            tracingField.set(pelletOptionsClass, monitorTypeNoneField.get(monitorType));
        }
        catch (ClassNotFoundException e) {
            throw new OWLReasonerSetupException(this, e);
        }
        catch (NoSuchFieldException e) {
            throw new OWLReasonerSetupException(this, e);
        }
        catch (IllegalAccessException e) {
            throw new OWLReasonerSetupException(this, e);
        }
    }


    /**
     * Turns off pellet logging.  This turns off ABox logging, Taxonomy logging, KnowledgeBase logging,
     * RBox logging and PelletVisitor logging.
     */
    public void turnOffLogging() {
        setABoxLogging(Level.OFF);
        setTaxonomyLogging(Level.OFF);
        setKnowledgeBaseLogging(Level.OFF);
        setRBoxLogging(Level.OFF);
        setPelletVisitorLogging(Level.OFF);
    }


    public void setPelletVisitorLogging(Level level) {
        setLoggingLevel("org.mindswap.pellet.owlapi.PelletVisitor", level);
    }


    public void setRBoxLogging(Level level) {
        setLoggingLevel("org.mindswap.pellet.RBox", level);
    }


    public void setKnowledgeBaseLogging(Level level) {
        setLoggingLevel("org.mindswap.pellet.KnowledgeBase", level);
    }


    public void setTaxonomyLogging(Level level) {
        setLoggingLevel("org.mindswap.pellet.taxonomy.Taxonomy", level);
    }


    public void setABoxLogging(Level level) {
        setLoggingLevel("org.mindswap.pellet.ABox", level);
    }


    public void setLoggingLevel(String className, Level level) {
        try {
            Class cls = Class.forName(className);
            Class loggerCls = Class.forName(Logger.class.getName());
            Field logField = cls.getField("log");
            Logger logger = (Logger) loggerCls.cast(logField.get(cls));
            logger.setLevel(level);
        }
        catch (ClassNotFoundException e) {
            printLoggingErrorMessage(e);
        }
        catch (NoSuchFieldException e) {
            printLoggingErrorMessage(e);
        }
        catch (IllegalAccessException e) {
            printLoggingErrorMessage(e);
        }
        catch (ClassCastException e) {
            printLoggingErrorMessage(e);
        }
    }

    private static void printLoggingErrorMessage(Exception e) {
        System.err.println("Could not turn off logging: " + e.getMessage());
        System.err.println("Upgrade to Pellet 2.0.0 or higher");
    }

    ///////////////////////////////////////////////////////////////////////////////////
    //
    //  Implementation of interface
    //
    ///////////////////////////////////////////////////////////////////////////////////


    public String getReasonerName() {
        return "Pellet";
    }


    /**
     * Creates an instance of the pellet reasoner.
     *
     * @param manager The manager
     * @return An instance of OWLReasoner that corresponds to the Pellet reasoner.
     */
    public OWLReasoner createReasoner(OWLOntologyManager manager) {
        try {
            return (OWLReasoner) reasonerConstructor.newInstance(manager);
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
    }
}
