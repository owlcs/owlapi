package org.semanticweb.owlapi.vocab;

import static org.semanticweb.owlapi.model.EntityType.CLASS;
import static org.semanticweb.owlapi.model.EntityType.DATA_PROPERTY;
import static org.semanticweb.owlapi.model.EntityType.NAMED_INDIVIDUAL;
import static org.semanticweb.owlapi.model.EntityType.OBJECT_PROPERTY;
import static org.semanticweb.owlapi.vocab.Namespaces.TIME;

import org.semanticweb.owlapi.model.EntityType;
import org.semanticweb.owlapi.model.HasIRI;
import org.semanticweb.owlapi.model.HasPrefixedName;
import org.semanticweb.owlapi.model.HasShortForm;
import org.semanticweb.owlapi.model.IRI;

/**
 * @author Alex To, The University Of Sydney, Falcuty of Engineering & Information Technologies
 * @since 5.1.0
 */
public enum TIMEVocabulary implements HasShortForm, HasIRI, HasPrefixedName {

    // Time Vocab http://www.w3.org/2006/time#
    //@formatter:off
    /** http://www.w3.org/2006/time#DayOfWeek.                  */DAY_OF_WEEK_CLASS             (TIME, "DayOfWeek", CLASS),
    /** http://www.w3.org/2006/time#generalYear.                */GENERAL_YEAR                  (TIME, "generalYear", CLASS),
    /** http://www.w3.org/2006/time#TemporalDuration.           */TEMPORAL_DURATION             (TIME, "TemporalDuration", CLASS),
    /** http://www.w3.org/2006/time#GeneralDurationDescription. */GENERAL_DURATION_DESCRIPTION  (TIME, "GeneralDurationDescription", CLASS),
    /** http://www.w3.org/2006/time#DurationDescription.        */DURATION_DESCRIPTION          (TIME, "DurationDescription", CLASS),
    /** http://www.w3.org/2006/time#Year.                       */YEAR_CLASS                    (TIME, "Year", CLASS),
    /** http://www.w3.org/2006/time#TemporalUnit.               */TEMPORAL_UNIT                 (TIME, "TemporalUnit", CLASS),
    /** http://www.w3.org/2006/time#Duration.                   */DURATION                      (TIME, "Duration", CLASS),
    /** http://www.w3.org/2006/time#TemporalEntity.             */TEMPORAL_ENTITY               (TIME, "TemporalEntity", CLASS),
    /** http://www.w3.org/2006/time#Instant.                    */INSTANT                       (TIME, "Instant", CLASS),
    /** http://www.w3.org/2006/time#Interval.                   */INTERVAL                      (TIME, "Interval", CLASS),
    /** http://www.w3.org/2006/time#ProperInterval.             */PROPER_INTERVAL               (TIME, "ProperInterval", CLASS),
    /** http://www.w3.org/2006/time#DateTimeInterval.           */DATE_TIME_INTERVAL            (TIME, "DateTimeInterval", CLASS),
    /** http://www.w3.org/2006/time#TemporalPosition.           */TEMPORAL_POSITION             (TIME, "TemporalPosition", CLASS),
    /** http://www.w3.org/2006/time#GeneralDateTimeDescription. */GENERAL_DATE_TIME_DESCRIPTION (TIME, "GeneralDateTimeDescription", CLASS),
    /** http://www.w3.org/2006/time#DateTimeDescription.        */DATE_TIME_DESCRIPTION         (TIME, "DateTimeDescription", CLASS),
    /** http://www.w3.org/2006/time#MonthOfYear.                */MONTH_OF_YEAR_CLASS           (TIME, "MonthOfYear", CLASS),
    /** http://www.w3.org/2006/time#January.                    */JANUARY                       (TIME, "January", CLASS),
    /** http://www.w3.org/2006/time#TimePosition.               */TIME_POSITION                 (TIME, "TimePosition", CLASS),
    /** http://www.w3.org/2006/time#TRS.                        */TEMPORAL_REFERENCE_SYSTEM     (TIME, "TRS", CLASS),
    /** http://www.w3.org/2006/time#TimeZone.                   */TIME_ZONE_CLASS               (TIME, "TimeZone", CLASS),
    /** http://www.w3.org/2006/time#after.                      */AFTER                         (TIME, "after", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalAfter.              */INTERVAL_AFTER                (TIME, "intervalAfter", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalBefore.             */BEFORE                        (TIME, "intervalBefore", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#dayOfWeek.                  */DAY_OF_WEEK_PROPERTY          (TIME, "dayOfWeek", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#hasDateTimeDescription.     */HAS_DATE_TIME_DESCRIPTION     (TIME, "hasDateTimeDescription", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#hasTemporalDuration.        */HAS_TEMPORAL_DURATION         (TIME, "hasTemporalDuration", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#hasDuration.                */HAS_DURATION                  (TIME, "hasDuration", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#hasDurationDescription.     */HAS_DURATION_DESCRIPTION      (TIME, "hasDurationDescription", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#hasTime.                    */HAS_TIME                      (TIME, "hasTime", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#hasBeginning.               */HAS_BEGINNING                 (TIME, "hasBeginning", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#hasEnd.                     */HAS_END                       (TIME, "hasEnd", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#inside.                     */INSIDE                        (TIME, "inside", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#timeZone.                   */TIME_ZONE_PROPERTY            (TIME, "timeZone", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalContains.           */INTERVAL_CONTAINS             (TIME, "intervalContains", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalDisjoint.           */INTERVAL_DISJOINT             (TIME, "intervalDisjoint", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalEquals.             */INTERVAL_EQUALS               (TIME, "intervalEquals", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalFinishedBy.         */INTERVAL_FINISHED_BY          (TIME, "intervalFinishedBy", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalIn.                 */INTERVAL_IN                   (TIME, "intervalIn", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalDuring.             */INTERVAL_DURING               (TIME, "intervalDuring", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalFinishes.           */INTERVAL_FINISHES             (TIME, "intervalFinishes", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalStarts.             */INTERVAL_STARTS               (TIME, "intervalStarts", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalMeets.              */INTERVAL_MEETS                (TIME, "intervalMeets", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalMetBy.              */INTERVAL_MET_BY               (TIME, "intervalMetBy", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalOverlappedBy.       */INTERVAL_OVERLAPPED_BY        (TIME, "intervalOverlappedBy", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalOverlaps.           */INTERVAL_OVERLAP              (TIME, "intervalOverlaps", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#intervalStartedBy.          */INTERVAL_STARTED_BY           (TIME, "intervalStartedBy", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#monthOfYear.                */MONTH_OF_YEAR_PROPERTY        (TIME, "monthOfYear", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#inTemporalPosition.         */IN_TEMPORAL_POSITION          (TIME, "inTemporalPosition", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#inDateTime.                 */IN_DATE_TIME                  (TIME, "inDateTime", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#inTimePosition.             */IN_TIME_POSITION              (TIME, "inTimePosition", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#hasTRS.                     */HAS_TRS                       (TIME, "hasTRS", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#unitType.                   */UNIT_TYPE                     (TIME, "unitType", OBJECT_PROPERTY),
    /** http://www.w3.org/2006/time#day.                        */DAY                           (TIME, "day", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#dayOfYear.                  */DAY_OF_YEAR                   (TIME, "dayOfYear", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#days.                       */DAYS                          (TIME, "days", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#hasXSDDuration.             */HAS_XSD_DURATION              (TIME, "hasXSDDuration", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#hour.                       */HOUR                          (TIME, "hour", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#hours.                      */HOURS                         (TIME, "hours", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#inXSDDateTimeStamp.         */IN_XSD_DATE_TIME_STAMP        (TIME, "inXSDDateTimeStamp", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#inXSDgYear.                 */IN_XSD_G_YEAR                 (TIME, "inXSDgYear", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#inXSDgYearMonth.            */IN_XSD_G_YEAR_MONTH           (TIME, "inXSDgYearMonth", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#minute.                     */MINUTE                        (TIME, "minute", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#minutes.                    */MINUTES                       (TIME, "minutes", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#month.                      */MONTH                         (TIME, "month", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#months.                     */MONTHS                        (TIME, "months", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#nominalPosition.            */NOMINAL_POSITION              (TIME, "nominalPosition", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#numericDuration.            */NUMERIC_DURATION              (TIME, "numericDuration", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#numericPosition.            */NUMERIC_POSITION              (TIME, "numericPosition", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#second.                     */SECOND                        (TIME, "second", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#seconds.                    */SECONDS                       (TIME, "seconds", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#week.                       */WEEK                          (TIME, "week", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#weeks.                      */WEEKS                         (TIME, "weeks", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#year.                       */YEAR_PROPERTY                 (TIME, "year", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#years.                      */YEARS                         (TIME, "years", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#xsdDateTime.                */XSD_DATE_TIME                 (TIME, "xsdDateTime", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#inXSDDateTime.              */IN_XSD_DATE_TIME              (TIME, "inXSDDateTime", DATA_PROPERTY),
    /** http://www.w3.org/2006/time#unitDay.                    */UNIT_DAY                      (TIME, "unitDay", NAMED_INDIVIDUAL),
    /** http://www.w3.org/2006/time#Friday.                     */FRIDAY                        (TIME, "Friday", NAMED_INDIVIDUAL),
    /** http://www.w3.org/2006/time#unitHour.                   */UNIT_HOUR                     (TIME, "unitHour", NAMED_INDIVIDUAL),
    /** http://www.w3.org/2006/time#unitMinute.                 */UNIT_MINUTE                   (TIME, "unitMinute", NAMED_INDIVIDUAL),
    /** http://www.w3.org/2006/time#Monday.                     */MONDAY                        (TIME, "Monday", NAMED_INDIVIDUAL),
    /** http://www.w3.org/2006/time#unitMonth.                  */UNIT_MONTH                    (TIME, "unitMonth", NAMED_INDIVIDUAL),
    /** http://www.w3.org/2006/time#Saturday.                   */SATURDAY                      (TIME, "Saturday", NAMED_INDIVIDUAL),
    /** http://www.w3.org/2006/time#unitSecond.                 */UNIT_SECOND                   (TIME, "unitSecond", NAMED_INDIVIDUAL),
    /** http://www.w3.org/2006/time#Sunday.                     */SUNDAY                        (TIME, "Sunday", NAMED_INDIVIDUAL),
    /** http://www.w3.org/2006/time#Thursday.                   */THURSDAY                      (TIME, "Thursday", NAMED_INDIVIDUAL),
    /** http://www.w3.org/2006/time#Tuesday.                    */TUESDAY                       (TIME, "Tuesday", NAMED_INDIVIDUAL),
    /** http://www.w3.org/2006/time#Wednesday.                  */WEDNESDAY                     (TIME, "Wednesday", NAMED_INDIVIDUAL),
    /** http://www.w3.org/2006/time#unitWeek.                   */UNIT_WEEK                     (TIME, "unitWeek", NAMED_INDIVIDUAL),
    /** http://www.w3.org/2006/time#unitYear.                   */UNIT_YEAR                     (TIME, "unitYear", NAMED_INDIVIDUAL);
//@formatter:on
    private final IRI iri;
    private final Namespaces namespace;
    private final String shortName;
    private final String prefixedName;
    private final EntityType<?> entityType;

    TIMEVocabulary(Namespaces namespace, String shortName, EntityType<?> entityType) {
        this.namespace = namespace;
        this.shortName = shortName;
        prefixedName = namespace.getPrefixName() + ':' + shortName;
        iri = IRI.create(namespace.toString(), shortName);
        this.entityType = entityType;
    }

    @Override
    public IRI getIRI() {
        return iri;
    }

    @Override
    public String getPrefixedName() {
        return prefixedName;
    }

    @Override
    public String getShortForm() {
        return shortName;
    }

    /**
     * @return namespace
     */
    public Namespaces getNamespace() {
        return namespace;
    }

    /**
     * @return entity type
     */
    public EntityType<?> getEntityType() {
        return entityType;
    }
}
