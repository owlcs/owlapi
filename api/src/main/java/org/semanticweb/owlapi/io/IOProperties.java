package org.semanticweb.owlapi.io;
/*
 * Copyright (C) 2007, University of Manchester
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
