package org.coode.owl.krssparser;

/**
 * <p/><b>OntoTrack</b> <br/>
 * Copyright (c) 2008 by Olaf Noppens. All rights reserved.<br/>
 * Ulm University, Germany
 *
 * @author Olaf Noppens (ON)
 * @version 1.0
 * @since 09.10.2008
 */
public enum NameResolverStrategy {
    ADAPTIVE, //if the first n names are URIs(NAMEs) all names are assumed to be URIs (NAMEs), otherwise we have to CHECK
    IRI, //every found concept/role name in KRSS is an absolute IRI
    NAME, //concept/roles names in KRSS are all names, URI must be created (namespace of the ontology's URI)
    CHECK //for every concept/role name in KRSS we have to check whether it is an URI or NAME. This is very expensive
}
