/**
 * Builder.java
 */
package com.owners.gravitas.service.builder;

/**
 * Service interface to convert DTO to domain object and vice versa.
 *
 * @author Harshad Shinde
 *
 * @param <A>
 *            is the entity class.
 * @param <B>
 *            is the dto/domain class.
 */
public interface Builder< A, B > {

    /**
     * This method converts dto object to domain object.
     *
     * @param sourceObject
     *            is the dto object.
     * @return domain object.
     */
    A convertFrom( B sourceObject );

    /**
     * This method converts domain object to dto object.
     *
     * @param sourceObject
     *            is the domain object.
     * @return dto object.
     */
    B convertTo( A sourceObject );

    /**
     * This method converts the <code>A</code> domain object to <code>B</code>
     * dto object.
     *
     * @param source
     *            is the domain object.
     * @param destination
     *            is the dto object.
     * @return dto object.
     */
    B convertTo( A source, B destination );

    /**
     * This method converts the <code>B</code> dto object to <code>A</code>
     * domain object.
     *
     * @param source
     *            is the dto object.
     * @param destination
     *            is the domain object.
     * @return domain object.
     */
    A convertFrom( B source, A destination );
}
