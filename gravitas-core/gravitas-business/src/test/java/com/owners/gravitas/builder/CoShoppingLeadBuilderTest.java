package com.owners.gravitas.builder;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;

import java.math.BigDecimal;
import java.util.Date;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.CoShoppingLeadBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.dto.PropertyData;
import com.owners.gravitas.dto.request.CoShoppingLeadRequest;
import com.owners.gravitas.dto.request.LeadRequest;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.PropertyService;
import com.owners.gravitas.util.AddressFormatter;

public class CoShoppingLeadBuilderTest extends AbstractBaseMockitoTest {
    @InjectMocks
    private CoShoppingLeadBuilder testCoShoppingLeadBuilder;

    /** The contact entity service. */
    @Mock
    private ContactEntityService contactEntityService;

    /** The property service. */
    @Mock
    private PropertyService propertyService;

    /** The address formatter. */
    @Mock
    private AddressFormatter addressFormatter;

    @Test
    public void testConvertToWithSourceNull() {
        assertNotNull( testCoShoppingLeadBuilder.convertTo( null, new CoShoppingLeadRequest() ) );
    }

    @Test
    public void testConvertToWithDestinationNull() {
        assertNotNull( testCoShoppingLeadBuilder.convertTo( new LeadRequest(), null ) );
    }

    @Test
    public void testConvertToWithSourceAndDestinationNull() {
        assertNull( testCoShoppingLeadBuilder.convertTo( null, null ) );
    }

    @Test
    public void testConvertTo() {
        // assertNotNull( testCoShoppingLeadBuilder.convertTo( new
        // LeadRequest(), new CoShoppingLeadRequest() ) );
        final LeadRequest leadRequest = new LeadRequest();
        leadRequest.setFirstName( "Abc" );
        final CoShoppingLeadRequest coShoppingLeadRequest = new CoShoppingLeadRequest();
        final CoShoppingLeadRequest request = testCoShoppingLeadBuilder.convertTo( leadRequest, coShoppingLeadRequest );
        assertNotNull( request );
        assertEquals( request.getLeadModel().getFirstName(), leadRequest.getFirstName() );
    }

    /**
     * Test build.
     */
    //@Test
    public void testBuild() {
        final String listingId = "listingId";
        final String oppId = "oppId";
        final Date dueDtm = new Date();
        final PropertyData propertyData = new PropertyData();
        propertyData.setBathRooms( new BigDecimal( 0 ) );
        propertyData.setMlsID( "test" );
        final CoShoppingLeadRequest request = testCoShoppingLeadBuilder.build( dueDtm, new Contact(), propertyData, "EST" );
        assertNotNull( request );
        assertEquals( request.getLeadModel().getMlsId(), "test" );
    }
}
