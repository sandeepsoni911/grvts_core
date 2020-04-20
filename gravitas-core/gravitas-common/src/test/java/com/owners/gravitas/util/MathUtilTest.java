package com.owners.gravitas.util;

import static org.testng.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

/**
 * @author raviz
 *
 */
public class MathUtilTest extends AbstractBaseMockitoTest {

    /**
     * Test get median should return median when comparator is not passed and
     * size of list is odd number.
     */
    @Test
    public void testGetMedianShouldReturnMedianWhenComparatorIsNotPassedAndSizeOfListIsOddNumber() {
        final List< Long > items = new ArrayList< Long >();
        final Long expectedMedian = 200L;
        items.add( 100L );
        items.add( expectedMedian );
        items.add( 300L );
        final Long actualMedian = MathUtil.getMedian( items );
        assertEquals( actualMedian, expectedMedian );
    }

    /**
     * Test get median should return median when comparator is not passed and
     * size of list is even number.
     */
    @Test
    public void testGetMedianShouldReturnMedianWhenComparatorIsNotPassedAndSizeOfListIsEvenNumber() {
        final List< Long > items = new ArrayList< Long >();
        final Long expectedMedian = 150L;
        items.add( 100L );
        items.add( 200L );
        final Long actualMedian = MathUtil.getMedian( items );
        assertEquals( actualMedian, expectedMedian );
    }

    /**
     * Test get median should return median when custom comparator is passed and
     * size of list is odd number.
     */
    @Test
    public void testGetMedianShouldReturnMedianWhenCustomComparatorIsPassedAndSizeOfListIsOddNumber() {
        final List< Long > items = new ArrayList< Long >();
        final Long expectedMedian = 200L;
        items.add( 100L );
        items.add( expectedMedian );
        items.add( 300L );
        final Comparator< Long > comparator = new Comparator< Long >() {
            @Override
            public int compare( final Long o1, final Long o2 ) {
                return o2.compareTo( o1 );
            }
        };
        final Long actualMedian = MathUtil.getMedian( items, comparator );
        assertEquals( actualMedian, expectedMedian );
    }

    @Test
    public void testGetMedianShouldReturnMedianWhenCustomComparatorIsPassedAndSizeOfListIsEvenNumber() {
        final List< Long > items = new ArrayList< Long >();
        final Long expectedMedian = 150L;
        items.add( 100L );
        items.add( 200L );
        final Comparator< Long > comparator = new Comparator< Long >() {
            @Override
            public int compare( final Long o1, final Long o2 ) {
                return o2.compareTo( o1 );
            }
        };
        final Long actualMedian = MathUtil.getMedian( items, comparator );
        assertEquals( actualMedian, expectedMedian );
    }
}
