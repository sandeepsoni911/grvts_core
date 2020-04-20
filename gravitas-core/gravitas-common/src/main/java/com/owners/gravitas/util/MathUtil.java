package com.owners.gravitas.util;

import java.text.NumberFormat;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;

/**
 * The Class MathUtil.
 *
 * @author raviz
 */
public class MathUtil {

    /**
     * Gets the median.
     *
     * @param items
     *            the items
     * @param comparator
     *            the comparator
     * @return the median
     */
    public static Long getMedian( final List< Long > items, Comparator< Long > comparator ) {
        Long median;
        if (comparator != null) {
            Collections.sort( items, comparator );
        } else {
            Collections.sort( items );
        }
        if (items.size() % 2 == 0) {
            median = ( items.get( items.size() / 2 ) + items.get( items.size() / 2 - 1 ) ) / 2;
        } else {
            median = items.get( items.size() / 2 );
        }
        return median;

    }

    /**
     * Gets the median.
     *
     * @param items
     *            the items
     * @return the median
     */
    public static Long getMedian( final List< Long > items ) {
        return getMedian( items, null );
    }
    
    /**
     * format amount with $ currency
     * 
     * @param amount
     * @param isDollarSignRequired
     * @return
     */
    public static String formatAmount( Double amount, boolean isDollarSignRequired ) {
        String formattedAmount = null;
        if (amount == 0 && isDollarSignRequired) {
            formattedAmount = "$0";
        }
        else if (amount == 0) {
            formattedAmount = "0";
        }
        else {
            NumberFormat numberFormat = NumberFormat.getCurrencyInstance( Locale.US );
            String currencyWithDollar = numberFormat.format( amount );
            formattedAmount = formatAmountString( currencyWithDollar, false, isDollarSignRequired );
        }
        return formattedAmount;
    }
    
    private static String formatAmountString( String currencyWithDollar, boolean isDecimal,
            boolean isDollarSignRequired ) {
        if (!isDecimal) {
            currencyWithDollar = currencyWithDollar.substring( 0, currencyWithDollar.length() - 3 );
        }
        return isDollarSignRequired ? currencyWithDollar
                : currencyWithDollar.substring( 1, currencyWithDollar.length() );
    }
}
