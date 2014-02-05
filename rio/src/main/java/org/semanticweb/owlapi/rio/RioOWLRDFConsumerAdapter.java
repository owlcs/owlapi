/*
 * This file is part of the OWL API.
 * 
 * The contents of this file are subject to the LGPL License, Version 3.0.
 * 
 * Copyright (C) 2011, The University of Queensland
 * 
 * This program is free software: you can redistribute it and/or modify it under the terms of the
 * GNU General Public License as published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful, but WITHOUT ANY WARRANTY; without
 * even the implied warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License along with this program. If
 * not, see http://www.gnu.org/licenses/.
 * 
 * 
 * Alternatively, the contents of this file may be used under the terms of the Apache License,
 * Version 2.0 in which case, the provisions of the Apache License Version 2.0 are applicable
 * instead of those above.
 * 
 * Copyright 2011, The University of Queensland
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not use this file except
 * in compliance with the License. You may obtain a copy of the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software distributed under the License
 * is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express
 * or implied. See the License for the specific language governing permissions and limitations under
 * the License.
 */
package org.semanticweb.owlapi.rio;

import java.util.concurrent.atomic.AtomicInteger;

import org.coode.owlapi.rdfxml.parser.AnonymousNodeChecker;
import org.coode.owlapi.rdfxml.parser.OWLRDFConsumer;
import org.openrdf.model.BNode;
import org.openrdf.model.Literal;
import org.openrdf.model.Resource;
import org.openrdf.model.Statement;
import org.openrdf.rio.RDFHandler;
import org.openrdf.rio.RDFHandlerException;
import org.semanticweb.owlapi.model.OWLOntology;
import org.semanticweb.owlapi.model.OWLOntologyLoaderConfiguration;
import org.semanticweb.owlapi.model.OWLOntologyManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.xml.sax.SAXException;

/**
 * @author Peter Ansell p_ansell@yahoo.com
 * 
 */
public class RioOWLRDFConsumerAdapter extends OWLRDFConsumer implements RDFHandler
{
    private final Logger logger = LoggerFactory.getLogger(RioOWLRDFConsumerAdapter.class);
    
    private AtomicInteger statementCount = new AtomicInteger(0);
    
    public RioOWLRDFConsumerAdapter(final OWLOntology ontology, final AnonymousNodeChecker checker,
            final OWLOntologyLoaderConfiguration configuration)
    {
        super(ontology, checker, configuration);
    }
    
    public RioOWLRDFConsumerAdapter(final OWLOntologyManager owlOntologyManager, final OWLOntology ontology,
            final AnonymousNodeChecker checker, final OWLOntologyLoaderConfiguration configuration)
    {
        this(ontology, checker, configuration);
    }
    
    @Override
    public void endRDF() throws RDFHandlerException
    {
        this.logger.debug("Parsed {} statements", this.statementCount.toString());
        
        try
        {
            this.endModel();
        }
        catch(final SAXException e)
        {
            throw new RDFHandlerException(e);
        }
    }
    
    @Override
    public void handleComment(final String comment) throws RDFHandlerException
    {
        // TODO Auto-generated method stub
        
    }
    
    @Override
    public void handleNamespace(final String prefix, final String uri) throws RDFHandlerException
    {
        this.getOntologyFormat().setPrefix(prefix + ":", uri);
    }
    
    @Override
    public void handleStatement(final Statement st) throws RDFHandlerException
    {
        this.statementCount.incrementAndGet();
        
        this.logger.trace("st{}={}", this.statementCount.get(), st);
        
        String subjectString;
        String objectString;
        
        if(st.getSubject() instanceof BNode)
        {
            subjectString = st.getSubject().stringValue();
            
            // it is not mandatory for BNode.stringValue() to return a string prefixed with the
            // turtle blank node syntax, so we check here to make sure
            // if(!(subjectString.startsWith("_:genid")))
            // {
            subjectString = "#genid-nodeid-" + subjectString;
            // }
        }
        else
        {
            subjectString = st.getSubject().stringValue();
        }
        
        if(st.getObject() instanceof BNode)
        {
            objectString = st.getObject().stringValue();
            
            // it is not mandatory for BNode.stringValue() to return a string prefixed with the
            // turtle blank node syntax, so we check here to make sure
            // if(!(objectString.startsWith("_:genid")))
            // {
            objectString = "#genid-nodeid-" + objectString;
            // }
        }
        else
        {
            objectString = st.getObject().stringValue();
        }
        
        try
        {
            if(st.getObject() instanceof Resource)
            {
                this.logger.trace("statement with resource value");
                this.statementWithResourceValue(subjectString, st.getPredicate().stringValue(), objectString);
            }
            else
            {
                final Literal literalObject = (Literal)st.getObject();
                String literalDatatype = null;
                final String literalLanguage = literalObject.getLanguage();
                
                if(literalObject.getDatatype() != null)
                {
                    literalDatatype = literalObject.getDatatype().stringValue();
                }
                
                this.logger.trace("statement with literal value");
                this.statementWithLiteralValue(subjectString, st.getPredicate().stringValue(), objectString,
                        literalLanguage, literalDatatype);
            }
        }
        catch(final SAXException e)
        {
            throw new RDFHandlerException(e);
        }
    }
    
    @Override
    public void startRDF() throws RDFHandlerException
    {
        this.statementCount = new AtomicInteger(0);
        
        try
        {
            this.startModel("");
        }
        catch(final SAXException e)
        {
            throw new RDFHandlerException(e);
        }
    }
    
}
