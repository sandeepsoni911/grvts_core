package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.enums.CustomerType;

/**
 * This is test class for {@link CustomerType}.
 *
 * @author vishwanathm
 */
public class CustomerTypeTest {

    /**
     * Tests all the enums.
     */
    @Test
    public final void test() {
        for ( CustomerType customerTypes : CustomerType.values() ) {
        	Assert.assertNotNull(customerTypes);
        	Assert.assertNotNull( customerTypes.getType() );
        }
        Assert.assertEquals(CustomerType.NEW_CUSTOMER.getType(), "New Customer", "CustomerType Object name not matching" );
    }
}
