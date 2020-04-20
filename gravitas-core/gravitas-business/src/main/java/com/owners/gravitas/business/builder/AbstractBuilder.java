/**
 * AbstractBuilder.java
 */
package com.owners.gravitas.business.builder;

/**
 * This abstract class is provide partial implementation of builder.
 *
 * @author Harshad Shinde
 *
 * @param <A>
 *            is the domain class.
 * @param <B>
 *            is the dto class.
 */
public abstract class AbstractBuilder< A, B > implements Builder< A, B > {

    /**
     * This method converts dto object to domain object.
     *
     * @param sourceObject
     *            is the dto object to be converted to domain.
     * @return A - destination object
     */
    @Override
    public A convertFrom( final B sourceObject ) {
        A destinationObject = null;

        if (sourceObject != null) {
            destinationObject = convertFrom( sourceObject, destinationObject );
        }

        return destinationObject;
    }

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
    public abstract B convertTo( final A source, final B destination );

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
    public abstract A convertFrom( final B source, final A destination );

    /**
     * This method converts domain object to dto object.
     *
     * @param sourceObject
     *            is the domain object to be converted to dto.
     * @return B - destination object
     */
    @Override
    public B convertTo( final A sourceObject ) {
        B destinationObject = null;
        if (sourceObject != null) {
            destinationObject = convertTo( sourceObject, destinationObject );
        }
        return destinationObject;
    }
}
