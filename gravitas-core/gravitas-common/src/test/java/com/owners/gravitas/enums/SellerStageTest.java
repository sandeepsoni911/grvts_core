package com.owners.gravitas.enums;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test for SellerStageTest.
 *
 * @author shivamm
 */
public class SellerStageTest {

    /**
     * Tests all the enums.
     */
    @Test( dataProvider = "getSellerStage" )
    public void test( final SellerStage sellerStage ) {
        assertNotNull( sellerStage );
    }

    /**
     * Tests get name.
     */
    @Test( dataProvider = "getSellerStage" )
    public void testGetName( final SellerStage sellerStage ) {
        assertNotNull( sellerStage.getStage() );
    }

    /**
     * Test get response key.
     */
    @Test( dataProvider = "getSellerStage" )
    public void testGetResponseKey( final SellerStage sellerStage ) {
        assertNotNull( sellerStage.getResponseKey() );
    }

    /**
     * Test get stage type should return stage when valid stage provided.
     *
     * @param sellerStage
     *            the buyer stage
     */
    @Test( dataProvider = "getSellerStage" )
    public void testGetStageTypeShouldReturnStageWhenValidStageProvided( final SellerStage sellerStage ) {
        final SellerStage actualStageType = SellerStage.getStageType( sellerStage.getStage() );
        assertEquals( actualStageType, sellerStage );
    }

    /**
     * Test get stage type should return unknown stage when invalid stage
     * provided.
     */
    @Test
    public void testGetStageTypeShouldReturnUnknownStageWhenInvalidStageProvided() {
        final SellerStage actualStageType = SellerStage.getStageType( "invalidStage" );
        assertEquals( actualStageType, SellerStage.UNKNOWN );
    }

    /**
     * Test get display label should return label when valid response key is
     * provided.
     *
     * @param sellerStage
     *            the buyer stage
     */
    @Test( dataProvider = "getSellerStage" )
    public void testGetDisplayLabelShouldReturnStageWhenValidResponseKeyIsProvided( final SellerStage sellerStage ) {
        final SellerStage actualStageType = SellerStage.getDisplayLabel( sellerStage.getResponseKey() );
        assertEquals( actualStageType, sellerStage );
    }

    /**
     * Test get display label should return unknown stage when invalid response
     * key is provided.
     *
     * @param sellerStage
     *            the buyer stage
     */
    @Test
    public void testGetDisplayLabelShouldReturnUnknownStageWhenInvalidResponseKeyIsProvided() {
        final SellerStage actualStageType = SellerStage.getDisplayLabel( "invalidResponseKey" );
        assertEquals( actualStageType, SellerStage.UNKNOWN );
    }

    /**
     * Gets the buyer stage.
     *
     * @return the buyer stage
     */
    @DataProvider( name = "getSellerStage" )
    private Object[][] getSellerStage() {
        final Object[][] objects = new Object[SellerStage.values().length][];
        int i = 0;
        int j = 0;
        for ( final SellerStage sellerStage : SellerStage.values() ) {
            objects[i] = new Object[1];
            objects[i++][j] = sellerStage;
        }
        return objects;
    }

}
