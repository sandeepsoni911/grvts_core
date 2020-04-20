package com.owners.gravitas.builder;

import java.util.HashMap;
import java.util.Map;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.util.ReflectionTestUtils;
import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import com.owners.gravitas.business.builder.LeadDetailsBuilder;
import com.owners.gravitas.config.AbstractBaseMockitoTest;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.dto.request.LeadRequest;

// TODO: Auto-generated Javadoc
/**
 * The Class LeadDetailsBuilderTest.
 */
public class LeadDetailsBuilderTest extends AbstractBaseMockitoTest {

	/** The long term client email notification builder. */
	@InjectMocks
	private LeadDetailsBuilder leadDetailsBuilder;

	/** The config. */
	@Mock
	private BuyerFarmingConfig config;

	/**
	 * Before test.
	 */
	@BeforeMethod
	public void beforeTest() {
		Mockito.when(config.getLeadSourcefilter1Str()).thenReturn("email1,email2");
		Mockito.when(config.getLeadSourcefilter2Str()).thenReturn("email3,email4");
		ReflectionTestUtils.invokeMethod(leadDetailsBuilder, "initDataBuilder");
		ReflectionTestUtils.setField(leadDetailsBuilder, "ownersCom", "ownersCom");
		ReflectionTestUtils.setField(leadDetailsBuilder, "affiliateZillow", "affiliateZillow");
		ReflectionTestUtils.setField(leadDetailsBuilder, "ownersEleadNetwork", "ownersEleadNetwork");
		ReflectionTestUtils.setField(leadDetailsBuilder, "unbounceLandingPage", "unbounceLandingPage");
		ReflectionTestUtils.setField(leadDetailsBuilder, "ownersLendingTree", "ownersLendingTree");
		ReflectionTestUtils.setField(leadDetailsBuilder, "ownersCondo", "ownersCondo");
	}

	/**
	 * Test convert to_for owners com.
	 */
	@Test
	public void testConvertTo_forOwnersCom() {

		final LeadRequest source = new LeadRequest();
		source.setSource("ownersCom");
		source.setPropertyAddress("1700 AMELIA CT PLANO, TX 75075");
		final Map<String, String> destination = leadDetailsBuilder.convertTo(source);
		Assert.assertNotNull(destination);
		Assert.assertEquals(destination.get("state"), "TX");
	}

	/**
	 * Test convert to_foraffiliate zillow.
	 */
	@Test
	public void testConvertTo_foraffiliateZillow() {
		final LeadRequest source = new LeadRequest();
		source.setSource("affiliateZillow");
		source.setPropertyAddress("5625 Antoine Dr,Houston,TX,77091");
		final Map<String, String> destination = leadDetailsBuilder.convertTo(source);
		Assert.assertNotNull(destination);
		Assert.assertEquals(destination.get("state"), "TX");
	}

	/**
	 * Test convert to_foraffiliate zillow with address line2.
	 */
	@Test
	public void testConvertTo_foraffiliateZillowWithAddressLine2() {
		final LeadRequest source = new LeadRequest();
		source.setSource("affiliateZillow");
		source.setPropertyAddress("5625, Antoine Dr,Houston,TX,77091");
		final Map<String, String> destination = leadDetailsBuilder.convertTo(source);
		Assert.assertNotNull(destination);
		Assert.assertEquals(destination.get("state"), "TX");
	}

	/**
	 * Test convert to_foraffiliate zillow with tilda.
	 */
	@Test
	public void testConvertTo_foraffiliateZillowWithTilda() {
		final LeadRequest source = new LeadRequest();
		source.setSource("affiliateZillow");
		source.setPropertyAddress("8427 Glencrest St , Houston, TX 77061~~2326,Houston,TX,77061");
		final Map<String, String> destination = leadDetailsBuilder.convertTo(source);
		Assert.assertNotNull(destination);
		Assert.assertEquals(destination.get("state"), "TX");
	}

	/**
	 * Test convert to_foraffiliate zillow with hyphen.
	 */
	@Test
	public void testConvertTo_foraffiliateZillowWithHyphen() {
		final LeadRequest source = new LeadRequest();
		source.setSource("affiliateZillow");
		source.setPropertyAddress("2352 Camden Dr Apt D, Houston, TX 77021-1055,Houston,TX,77021");
		final Map<String, String> destination = leadDetailsBuilder.convertTo(source);
		Assert.assertNotNull(destination);
		Assert.assertEquals(destination.get("state"), "TX");
	}

	/**
	 * Test convert to_forowners elead network.
	 */
	@Test
	public void testConvertTo_forownersEleadNetwork() {
		final LeadRequest source = new LeadRequest();
		source.setSource("ownersEleadNetwork");
		source.setPropertyAddress("test");
		source.setState("TX");
		final Map<String, String> destination = leadDetailsBuilder.convertTo(source);
		Assert.assertNotNull(destination);
		Assert.assertEquals(destination.get("state"), "TX");
	}

	/**
	 * Test convert to_forunbounce landing page.
	 */
	@Test
	public void testConvertTo_forunbounceLandingPage() {
		final LeadRequest source = new LeadRequest();
		source.setSource("unbounceLandingPage");
		source.setPropertyAddress("test");
		source.setState("TX");
		final Map<String, String> destination = leadDetailsBuilder.convertTo(source);
		Assert.assertNotNull(destination);
		Assert.assertEquals(destination.get("state"), "TX");
	}

	/**
	 * Test convert to_forowners lending tree.
	 */
	@Test
	public void testConvertTo_forownersLendingTree() {
		final LeadRequest source = new LeadRequest();
		source.setSource("ownersLendingTree");
		source.setPropertyAddress("4434 Brookland's dr Hilliard, Ohio");
		source.setState("TX");
		final Map<String, String> destination = leadDetailsBuilder.convertTo(source);
		Assert.assertNotNull(destination);
		Assert.assertEquals(destination.get("state"), "TX");
	}

	/**
	 * Test convert to_for owners condo.
	 */
	@Test
	public void testConvertTo_forOwnersCondo() {
		final LeadRequest source = new LeadRequest();
		source.setSource("ownersCondo");
		source.setPropertyAddress("1700 AMELIA CT PLANO, TX 75075");
		final Map<String, String> destination = leadDetailsBuilder.convertTo(source);
		Assert.assertNotNull(destination);
		Assert.assertEquals(destination.get("state"), "TX");
	}

	/**
	 * Test convert to_different lead source.
	 */
	@Test
	public void testConvertTo_differentLeadSource() {
		final LeadRequest source = new LeadRequest();
		source.setSource("test");
		source.setPropertyAddress("test");
		source.setState("TX");
		final Map<String, String> destination = leadDetailsBuilder.convertTo(source);
		Assert.assertNotNull(destination);
		Assert.assertEquals(destination.get("state"), "TX");
	}

	/**
	 * Test convert to_no property address lead source fltr1.
	 */
	@Test
	public void testConvertTo_noPropertyAddressLeadSourceFltr1() {
		final LeadRequest source = new LeadRequest();
		source.setSource("email3");
		source.setState("TX");
		final Map<String, String> destination = leadDetailsBuilder.convertTo(source);
		Assert.assertNotNull(destination);
		Assert.assertEquals(destination.get("state"), "TX");
	}

	/**
	 * Test convert to_no property address lead source fltr2.
	 */
	@Test
	public void testConvertTo_noPropertyAddressLeadSourceFltr2() {
		final LeadRequest source = new LeadRequest();
		source.setSource("email1");
		source.setState("TX");
		final Map<String, String> destination = leadDetailsBuilder.convertTo(source);
		Assert.assertNotNull(destination);
		Assert.assertEquals(destination.get("state"), "TX");
	}

	/**
	 * Testconvert from.
	 */
	@Test(expectedExceptions = UnsupportedOperationException.class)
	public void testconvertFrom() {
		leadDetailsBuilder.convertFrom(new HashMap<String, String>());
	}
}
