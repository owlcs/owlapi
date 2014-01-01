/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2014, The University of Manchester
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see http://www.gnu.org/licenses/.
 *
 *
 * Alternatively, the contents of this file may be used under the terms of the Apache License, Version 2.0
 * in which case, the provisions of the Apache License Version 2.0 are applicable instead of those above.
 *
 * Copyright 2014, The University of Manchester
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
