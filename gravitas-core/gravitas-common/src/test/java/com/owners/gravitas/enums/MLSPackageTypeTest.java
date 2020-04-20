package com.owners.gravitas.enums;

import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * The Class MLSPackageTypeTest.
 *
 * @author vishwanathm
 */
public class MLSPackageTypeTest {
    @Test
    public final void test() {
    	for(MLSPackageType mlsPackageType:MLSPackageType.values()){
			Assert.assertNotNull(mlsPackageType);
			Assert.assertNotNull(mlsPackageType.getPackageType());
		}
        Assert.assertEquals( MLSPackageType.FREE.getPackageType(), "Free", "MLS Package Type not matching" );
    }
}
