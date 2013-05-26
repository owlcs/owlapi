package org.semanticweb.owlapi.apibinding;

import org.semanticweb.owlapi.model.OWLDataFactory;

import uk.ac.manchester.cs.owl.owlapi.OWLDataFactoryImpl;

import com.google.inject.AbstractModule;

public class OWLAPIModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(OWLDataFactory.class).to(OWLDataFactoryImpl.class);
    }
}
