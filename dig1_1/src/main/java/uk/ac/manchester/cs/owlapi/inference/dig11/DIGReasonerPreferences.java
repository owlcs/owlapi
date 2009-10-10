package uk.ac.manchester.cs.owl.inference.dig11;

import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
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
 * Date: 21-Nov-2006<br><br>
 */
public class DIGReasonerPreferences {

    private static DIGReasonerPreferences instance;

    private URL url;

    private DIGReasonerPreferences() {
    }

    public static synchronized DIGReasonerPreferences getInstance() {
        if (instance == null) {
            instance = new DIGReasonerPreferences();
            instance.loadDefaults();
        }
        return instance;
    }

    public void loadDefaults() {
        try {
            url = new URL("http://localhost:3490");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
    }

    public URL getReasonerURL() {
        return url;
    }

    public void setReasonerURL(URL url) {
        if (url != null) {
            this.url = url;
        }
    }

    private boolean treatErrorsAsWarnings;

    private boolean logDIG = false;

    private OutputStream logOutputStream;

    /**
     * Determines if DIG Errors should be regarded
     * as warnings.
     */
    public boolean isTreatErrorsAsWarnings() {
        return treatErrorsAsWarnings;
    }


    /**
     * Specifies whether DIG Errors should be regarded as
     * warnings
     *
     * @param b <code>true</code> if errors should be regarded
     *          as warnings, otherwise <code>false</code>.
     */
    public void setTreatErrorsAsWarnings(boolean b) {
        this.treatErrorsAsWarnings = b;
    }


    /**
     * Determines if logging is on.  If logging is on
     * then the DIG communication with the DIG Reasoner
     * is logged to the specified log stream (or std out if
     * no log stream has been specified).
     */
    public boolean isLogDIG() {
        return logDIG;
    }


    /**
     * Specifies whether or not the DIG communication
     * with the reasoner should be logged.
     */
    public void setLogDIG(boolean b) {
        this.logDIG = b;
    }


    /**
     * Gets the output stream used for logging.
     */
    public OutputStream getLogOutputStream() {
        if (logOutputStream == null) {
            return System.out;
        } else {
            return logOutputStream;
        }
    }


    /**
     * Sets the output stream used for logging.
     */
    public void setLogOutputStream(OutputStream logOutputStream) {
        this.logOutputStream = logOutputStream;
    }
}
