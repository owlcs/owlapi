package org.semanticweb.owlapi.api.test;

import org.semanticweb.owlapi.model.OWLDataFactory;
import org.semanticweb.owlapi.model.OWLException;


/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 12-Dec-2006<br><br>
 */
public abstract class OWLDataFactoryFactory {

    private static OWLDataFactoryFactory factory;

    public static void setFactory(OWLDataFactoryFactory factory) {
        OWLDataFactoryFactory.factory = factory;
    }

    public static OWLDataFactoryFactory getInstance() throws OWLException {
        if(factory == null) {
            try {
                String factoryName = System.getProperty("DataFactoryFactory");
                if(factoryName == null) {
                    throw new RuntimeException("System property 'DataFactoryFactory' must be set in order to run the tests");
                }
                Class<?> cls = Class.forName(factoryName);
                factory = (OWLDataFactoryFactory) cls.newInstance();
            }
            catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
            catch (InstantiationException e) {
                e.printStackTrace();
            }
            catch (IllegalAccessException e) {
                e.printStackTrace();
            }
        }
        return factory;
    }

    public abstract OWLDataFactory createOWLDataFactory() throws OWLException;

}
