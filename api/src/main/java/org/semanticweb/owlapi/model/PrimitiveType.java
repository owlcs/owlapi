package org.semanticweb.owlapi.model;

import java.io.Serializable;

/** @author Matthew Horridge, Stanford University, Bio-Medical Informatics
 *         Research Group
 * @since 3.5.0 */
public enum PrimitiveType implements Serializable, HasShortForm {
    //@formatter:off
    /** CLASS               */  CLASS               (EntityType.CLASS), 
    /** OBJECT_PROPERTY     */  OBJECT_PROPERTY     (EntityType.OBJECT_PROPERTY), 
    /** DATA_PROPERTY       */  DATA_PROPERTY       (EntityType.DATA_PROPERTY), 
    /** ANNOTATION_PROPERTY */  ANNOTATION_PROPERTY (EntityType.ANNOTATION_PROPERTY), 
    /** DATATYPE            */  DATATYPE            (EntityType.DATATYPE), 
    /** NAMED_INDIVIDUAL    */  NAMED_INDIVIDUAL    (EntityType.NAMED_INDIVIDUAL), 
    /** LITERAL             */  LITERAL             ("Literal", "Literal", "Literals"), 
    /** IRI                 */  IRI                 ("IRI", "IRI", "IRIs");
    //@formatter:on
    private final String shortForm;
    private final String printName;
    private final String pluralPrintName;

    PrimitiveType(EntityType<?> entityType) {
        this(entityType.getShortForm(), entityType.getPrintName(), entityType
                .getPluralPrintName());
    }

    PrimitiveType(String shortForm, String printName, String pluralPrintName) {
        this.shortForm = shortForm;
        this.printName = printName;
        this.pluralPrintName = pluralPrintName;
    }

    @Override
    public String getShortForm() {
        return shortForm;
    }

    /** @return print name for user consumption */
    public String getPrintName() {
        return printName;
    }

    /** @return plural of the print name */
    public String getPluralPrintName() {
        return pluralPrintName;
    }
}
