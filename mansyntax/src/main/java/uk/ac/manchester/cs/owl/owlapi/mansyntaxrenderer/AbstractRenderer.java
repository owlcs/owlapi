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

package uk.ac.manchester.cs.owl.owlapi.mansyntaxrenderer;

import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.coode.owlapi.manchesterowlsyntax.ManchesterOWLSyntax;
import org.semanticweb.owlapi.io.OWLRendererException;
import org.semanticweb.owlapi.io.OWLRendererIOException;
import org.semanticweb.owlapi.util.ShortFormProvider;

/**
 * Author: Matthew Horridge<br>
 * The University Of Manchester<br>
 * Bio-Health Informatics Group<br>
 * Date: 25-Apr-2007<br><br>
 */
public class AbstractRenderer {

    private ShortFormProvider shortFormProvider;

    private int lastNewLinePos = -1;

    private int currentPos;

    private Writer writer;

    private List<Integer> tabs;

    private boolean useTabbing = true;

    private boolean useWrapping = true;
    
    public AbstractRenderer(Writer writer, ShortFormProvider shortFormProvider) {
        this.writer = writer;
        this.shortFormProvider = shortFormProvider;
        tabs = new ArrayList<Integer>();
        pushTab(0);
    }


    public void setUseTabbing(boolean useTabbing) {
        this.useTabbing = useTabbing;
    }


    public void setUseWrapping(boolean useWrapping) {
        this.useWrapping = useWrapping;
    }


    public boolean isUseWrapping() {
        return useWrapping;
    }


    public boolean isUseTabbing() {
        return useTabbing;
    }


//    public void setShortFormProvider(ShortFormProvider shortFormProvider) {
//        this.shortFormProvider = shortFormProvider;
//    }


    public void flush() throws OWLRendererException {
        try {
            writer.flush();
        }
        catch (IOException e) {
            throw new OWLRendererIOException(e);
        }
    }

    protected void pushTab(int size) {
        tabs.add(0, size);
    }

    protected void incrementTab(int increment) {
        int base = 0;
        if(!tabs.isEmpty()) {
            base = tabs.get(0);
        }
        tabs.add(0, base + increment);
    }

    protected void popTab() {
        tabs.remove(0);
    }

    protected void writeTab() {
        int tab = tabs.get(0);
        for(int i = 0; i < tab; i++) {
            write(" ");
        }
    }

    protected int getIndent() {
        return currentPos - lastNewLinePos - 2;
    }


    protected void write(String s) {
        if(s == null) {
            return;
        }
        int indexOfNewLine = s.indexOf('\n');
        if(indexOfNewLine != -1) {
            lastNewLinePos = currentPos + indexOfNewLine;
        }
        currentPos += s.length();
        try {
            writer.write(s);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void write(String s, int lineLen) {
        StringTokenizer tokenizer = new StringTokenizer(s, " \n\t-", true);
        int currentLineLength = 0;
        while(tokenizer.hasMoreTokens()) {
            String curToken = tokenizer.nextToken();
            write(curToken);
            if(curToken.equals("\n")) {
                writeTab();
            }
            currentLineLength += curToken.length();
            if(currentLineLength > lineLen && curToken.trim().length() != 0 && tokenizer.hasMoreTokens()) {
                writeNewLine();
                currentLineLength = 0;
            }
        }
    }

    protected void writeSpace() {
        write(" ");
    }

    protected void write(ManchesterOWLSyntax keyword) {
        write(" ", keyword, " ");
    }

    protected void writeFrameKeyword(ManchesterOWLSyntax keyword) {
        write("", keyword, ": ");
    }

    protected void writeSectionKeyword(ManchesterOWLSyntax keyword) {
        write(" ", keyword, ": ");
    }

    protected void writeNewLine() {
        write("\n");
        if (useTabbing) {
            writeTab();
        }
    }

    protected void write(String prefix, ManchesterOWLSyntax keyword, String suffix) {
        write(prefix);
        write(keyword.toString());
        write(suffix);
    }

    protected ShortFormProvider getShortFormProvider() {
        return shortFormProvider;
    }

}
