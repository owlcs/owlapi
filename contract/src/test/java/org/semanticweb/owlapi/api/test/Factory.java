package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.apibinding.OWLManager;
import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.semanticweb.owlapi.model.OWLOntologyManagerFactory;

@SuppressWarnings("javadoc")
public class Factory {

    public static final String SYSTEM_PARAM_NAME = "OntologyManagerFactory";
    private static OWLOntologyManagerFactory factory;
    static {
        String factoryName = System.getProperty(SYSTEM_PARAM_NAME);
        if (factoryName == null) {
            System.out
                    .println("Factory: using default OWLManager. To change this, set the "
                            + SYSTEM_PARAM_NAME
                            + " to the class name for the alternate factory");
            factoryName = OWLManager.class.getName();
        }
        Class<?> cls;
        try {
            cls = Class.forName(factoryName);
            factory = (OWLOntologyManagerFactory) cls.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        if (factory == null) {
            factory = new OWLManager();
        }
    }

    public static void setFactory(OWLOntologyManagerFactory f) {
        factory = f;
    }

    public static void setFactory(String s) {
        Class<?> cls;
        try {
            cls = Class.forName(s);
            factory = (OWLOntologyManagerFactory) cls.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public static OWLOntologyManager getManager() {
        return factory.buildOWLOntologyManager();
    }

    public static OWLDataFactory getFactory() {
        return factory.getFactory();
    }
}
