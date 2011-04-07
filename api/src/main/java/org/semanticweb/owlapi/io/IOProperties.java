/*
 * This file is part of the OWL API.
 *
 * The contents of this file are subject to the LGPL License, Version 3.0.
 *
 * Copyright (C) 2011, The University of Manchester
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
 * Copyright 2011, University of Manchester
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

package org.semanticweb.owlapi.io;
/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 10-Apr-2008<br><br>
 */
public class IOProperties {

    private static IOProperties instance = new IOProperties();

    public static final int DEFAULT_CONNECTION_TIME_OUT = 20000;

    public static final String CONNECTION_TIME_OUT_PROPERTY_NAME = "owlapi.connectionTimeOut";

    private int connectionTimeout;


    public static final boolean DEFAULT_CONNECTION_ACCEPT_HTTP_COMPRESSION = true;

    public static final String CONNECTION_ACCEPT_HTTP_COMPRESSION_PROPERTY_NAME = "owlapi.connectionAcceptHTTPCompression";

    private boolean connectionAcceptHTTPCompression = DEFAULT_CONNECTION_ACCEPT_HTTP_COMPRESSION;



    private IOProperties() {
        connectionTimeout = DEFAULT_CONNECTION_TIME_OUT;
        connectionAcceptHTTPCompression = DEFAULT_CONNECTION_ACCEPT_HTTP_COMPRESSION;
    }


    public static IOProperties getInstance() {
        return instance;
    }


    /**
     * Gets the connection timeout that is used for sockets when loading
     * ontologies over HTTP etc.
     * @return The connection timeout in milliseconds
     */
    public int getConnectionTimeout() {
        return connectionTimeout;
    }


    /**
     * Sets the connection timeout that should be used when loading from
     * sockets.
     * @param connectionTimeout The connection timeout in milliseconds
     */
    public void setConnectionTimeout(int connectionTimeout) {
        this.connectionTimeout = connectionTimeout;
    }


    /**
     * Determines whether HTTP compression can be used
     * @return <code>true</code> if HTTP compression can be used, otherwise
     * false
     */
    public boolean isConnectionAcceptHTTPCompression() {
        return connectionAcceptHTTPCompression;
    }


    /**
     * Sets whether HTTP compression can be used.
     * @param connectionAcceptHTTPCompression <code>true</code> if HTTP compression can
     * be used, otherwise <code>false</code>
     */
    public void setConnectionAcceptHTTPCompression(boolean connectionAcceptHTTPCompression) {
        this.connectionAcceptHTTPCompression = connectionAcceptHTTPCompression;
    }
}
