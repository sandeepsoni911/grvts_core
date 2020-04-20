package com.owners.gravitas.enums;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

/**
 * Test for BuyerStageTest.
 *
 * @author shivamm
 */
public class BuyerStageTest {

    /**
     * Tests all the enums.
     */
    @Test( dataProvider = "getBuyerStage" )
    public void test( final BuyerStage buyerStage ) {
        assertNotNull( buyerStage );
    }

    /**
     * Tests get name.
     */
    @Test( dataProvider = "getBuyerStage" )
    public void testGetName( final BuyerStage buyerStage ) {
        assertNotNull( buyerStage.getStage() );
    }

    /**
     * Test get response key.
     */
    @Test( dataProvider = "getBuyerStage" )
    public void testGetResponseKey( final BuyerStage buyerStage ) {
        assertNotNull( buyerStage.getResponseKey() );
    }

    /**
     * Test get stage type should return stage when valid stage provided.
     *
     * @param buyerStage
     *            the buyer stage
     */
    @Test( dataProvider = "getBuyerStage" )
    public void testGetStageTypeShouldReturnStageWhenValidStageProvided( final BuyerStage buyerStage ) {
        final BuyerStage actualStageType = BuyerStage.getStageType( buyerStage.getStage() );
        assertEquals( actualStageType, buyerStage );
    }

    /**
     * Test get stage type should return unknown stage when invalid stage
     * provided.
     */
    @Test
    public void testGetStageTypeShouldReturnUnknownStageWhenInvalidStageProvided() {
        final BuyerStage actualStageType = BuyerStage.getStageType( "invalidStage" );
        assertEquals( actualStageType, BuyerStage.UNKNOWN );
    }

    /**
     * Test get display label should return label when valid response key is
     * provided.
     *
     * @param buyerStage
     *            the buyer stage
     */
    @Test( dataProvider = "getBuyerStage" )
    public void testGetDisplayLabelShouldReturnStageWhenValidResponseKeyIsProvided( final BuyerStage buyerStage ) {
        final BuyerStage actualStageType = BuyerStage.getDisplayLabel( buyerStage.getResponseKey() );
        assertEquals( actualStageType, buyerStage );
    }

    /**
     * Test get display label should return unknown stage when invalid response
     * key is provided.
     *
     * @param buyerStage
     *            the buyer stage
     */
    @Test
    public void testGetDisplayLabelShouldReturnUnknownStageWhenInvalidResponseKeyIsProvided() {
        final BuyerStage actualStageType = BuyerStage.getDisplayLabel( "invalidResponseKey" );
        assertEquals( actualStageType, BuyerStage.UNKNOWN );
    }

    /**
     * Gets the buyer stage.
     *
     * @return the buyer stage
     */
    @DataProvider( name = "getBuyerStage" )
    private Object[][] getBuyerStage() {
        final Object[][] objects = new Object[BuyerStage.values().length][];
        int i = 0;
        int j = 0;
        for ( final BuyerStage buyerStage : BuyerStage.values() ) {
            objects[i] = new Object[1];
            objects[i++][j] = buyerStage;
        }
        return objects;
    }
}
