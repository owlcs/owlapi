package org.semanticweb.owlapi.model;
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
                Class cls = Class.forName(factoryName);
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
