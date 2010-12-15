package org.coode.xml;


/**
 * Author: Matthew Horridge<br>
 * The University of Manchester<br>
 * Information Management Group<br>
 * Date: 17-Dec-2009
 * </p>
 * An exception which indicates that a caller tried to write an XML Element with a name that is not a QName.
 */
public class IllegalElementNameException extends RuntimeException {

    private String elementName;

    public IllegalElementNameException(String elementName) {
        super("Illegal Element Name (Element Is Not A QName): " + elementName);
        this.elementName = elementName;
    }

    public String getElementName() {
        return elementName;
    }

}
