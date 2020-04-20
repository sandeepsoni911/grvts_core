package com.owners.gravitas.util;

import static com.owners.gravitas.util.DateUtil.CRM_DATE_TIME_PATTERN;
import static com.owners.gravitas.util.DateUtil.DEFAULT_CRM_DATE_PATTERN;
import static org.joda.time.DateTimeZone.UTC;
import static org.testng.Assert.assertEquals;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.joda.time.LocalDateTime;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.owners.gravitas.config.AbstractBaseMockitoTest;

// TODO: Auto-generated Javadoc
/**
 * The Class DateUtilTest.
 *
 * @author vishwanathm
 */
public class DateUtilTest extends AbstractBaseMockitoTest {

	/**
	 * Test local time to string.
	 */
	@Test
	public void testLocalTimeToString() {
		String datestr = DateUtil.toString(new LocalDateTime(), DEFAULT_CRM_DATE_PATTERN);
		Assert.assertNotNull(datestr);

		LocalDateTime nullVal = null;
		datestr = DateUtil.toString(nullVal, DEFAULT_CRM_DATE_PATTERN);
		Assert.assertNotNull(datestr);

	}

	/**
	 * Test date time to string.
	 */
	@Test
	public void testDateTimeToString() {
		String datestr = DateUtil.toString(new DateTime(), DEFAULT_CRM_DATE_PATTERN);
		Assert.assertNotNull(datestr);

		DateTime nullVal = null;
		datestr = DateUtil.toString(nullVal, DEFAULT_CRM_DATE_PATTERN);
		Assert.assertNotNull(datestr);

	}

	/**
	 * Test to date.
	 */
	@Test
	public void testToDateTime() {
		DateTime date = DateUtil.toDateTime("1980-10-10", DEFAULT_CRM_DATE_PATTERN);
		Assert.assertNotNull(date);
		date = DateUtil.toDateTime("", DEFAULT_CRM_DATE_PATTERN);
		Assert.assertNull(date);
	}

	/**
	 * Test to date time without pattern.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void testToDateTimeWithoutPattern() throws Exception {
		Assert.assertNotNull(DateUtil.toDateTime("2016-12-16", null), "isValidFormat should return not null!!");
	}

	/**
	 * Gets the long date test.
	 *
	 * @return the long date test
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getLongDateTestWithPattern() throws Exception {
		Assert.assertNotNull(DateUtil.getLongDate("2016-12-16", DEFAULT_CRM_DATE_PATTERN));
	}

	/**
	 * Gets the long date test.
	 *
	 * @return the long date test
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getLongDateTest() throws Exception {
		Assert.assertNotNull(DateUtil.getLongDate("2016-12-16"), "isValidFormat should return not null!!");
	}

	/**
	 * Gets the long date test null value.
	 *
	 * @return the long date test null value
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void getLongDateTestNullValue() throws Exception {
		Assert.assertNull(DateUtil.getLongDate(null), "isValidFormat should return null!!");
	}

	/**
	 * Checks if is valid format test.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void isValidFormatTest() throws Exception {
		Assert.assertTrue(DateUtil.isValidFormat("2016-12-16", DEFAULT_CRM_DATE_PATTERN),
				"isValidFormat should return true!!");
	}

	/**
	 * Checks if is valid format test invalid date.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void isValidFormatTestInvalidDate() throws Exception {
		Assert.assertFalse(DateUtil.isValidFormat("2016-12-16 23:00", DEFAULT_CRM_DATE_PATTERN),
				"isValidFormat should return true!!");
	}

	/**
	 * Checks if is valid format test null date.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void isValidFormatTestNullDate() throws Exception {
		Assert.assertFalse(DateUtil.isValidFormat(null, DEFAULT_CRM_DATE_PATTERN),
				"isValidFormat should return false!!");
	}

	/**
	 * Checks if is valid format test invalid format.
	 *
	 * @throws Exception
	 *             the exception
	 */
	@Test
	public void isValidFormatTestInvalidFormat() throws Exception {
		Assert.assertFalse(DateUtil.isValidFormat("2016-12-16 23:00", CRM_DATE_TIME_PATTERN),
				"isValidFormat should return false!!");
	}

	/**
	 * Test to date with date string.
	 */
	@Test
	public void testToDate_WithDateString() {
		final DateTime date = DateUtil.toDateTime("2016-12-16", DateUtil.DEFAULT_CRM_DATE_PATTERN);
		assertEquals(new Date(date.getMillis()), DateUtil.toDate("2016-12-16"));
	}

	/**
	 * Test to date with date string null.
	 */
	@Test
	public void testToDate_WithDateStringNull() {
		assertEquals(null, DateUtil.toDate(StringUtils.EMPTY));
	}

	/**
	 * Test to date with date string and pattern.
	 */
	@Test
	public void testToDate_WithDateStringAndPattern() {
		final DateTime date = DateUtil.toDateTime("2016-12-16", DateUtil.DEFAULT_CRM_DATE_PATTERN);
		assertEquals(new Date(date.getMillis()), DateUtil.toDate("2016-12-16", DateUtil.DEFAULT_CRM_DATE_PATTERN));
	}

	/**
	 * Test to date with date string and pattern.
	 */
	@Test
	public void testToDate_WithLondDate() {
		assertEquals(DateUtil.toDate(1121321321l), new DateTime(1121321321l, UTC));
	}

	/**
	 * Test to sql date.
	 */
	@Test
	public void testToSqlDate() {
		String datePattern = DateUtil.DEFAULT_CRM_DATE_PATTERN;
		Date date = null;
		final DateFormat dtFormatter = new SimpleDateFormat(datePattern);
		try {
			date = dtFormatter.parse("2016-12-16");
		} catch (ParseException e) {
		}
		assertEquals(new java.sql.Date(date.getTime()),
				DateUtil.toSqlDate("2016-12-16", DateUtil.DEFAULT_CRM_DATE_PATTERN));
	}

	/**
	 * Test get readable time should return readable date in descriptive format
	 * 1.
	 */
	@Test
	public void testGetReadableTimeShouldReturnReadableDateInDescriptiveFormat1() {
		final boolean isSeconds = true;
		final boolean isShortNotation = false;
		final String expectedReadableDate = "1 day 1 hour 1 min 1 sec";
		final String actualReadableDate = DateUtil.getReadableTime((60000 * 60 * 24) + (60000 * 60) + 60000 + 1000,
				isSeconds, isShortNotation);
		assertEquals(actualReadableDate, expectedReadableDate);
	}

	/**
	 * Test get readable time should return readable date in descriptive format
	 * 2.
	 */
	@Test
	public void testGetReadableTimeShouldReturnReadableDateInDescriptiveFormat2() {
		final boolean isSeconds = true;
		final boolean isShortNotation = false;
		final String expectedReadableDate = "2 days 2 hrs 2 mins 2 secs";
		final String actualReadableDate = DateUtil.getReadableTime(
				(60000 * 60 * 24 * 2) + (60000 * 60 * 2) + 60000 * 2 + 1000 * 2, isSeconds, isShortNotation);
		assertEquals(actualReadableDate, expectedReadableDate);
	}

	/**
	 * Test get readable time should return readable date in descriptive format
	 * without seconds.
	 */
	@Test
	public void testGetReadableTimeShouldReturnReadableDateInDescriptiveFormatWithoutSeconds() {
		final boolean isSeconds = false;
		final boolean isShortNotation = false;
		final String expectedReadableDate = "2 days 2 hrs 2 mins ";
		final String actualReadableDate = DateUtil.getReadableTime(
				(60000 * 60 * 24 * 2) + (60000 * 60 * 2) + 60000 * 2 + 1000 * 2, isSeconds, isShortNotation);
		assertEquals(actualReadableDate, expectedReadableDate);
	}

	/**
	 * Test get readable time should return readable date in short format1.
	 */
	@Test
	public void testGetReadableTimeShouldReturnReadableDateInShortFormat1() {
		final boolean isSeconds = true;
		final boolean isShortNotation = true;
		final String expectedReadableDate = "1d 1h 1m 1s";
		final String actualReadableDate = DateUtil.getReadableTime((60000 * 60 * 24) + (60000 * 60) + 60000 + 1000,
				isSeconds, isShortNotation);
		assertEquals(actualReadableDate, expectedReadableDate);
	}

	/**
	 * Test get readable time should return readable date in short format2.
	 */
	@Test
	public void testGetReadableTimeShouldReturnReadableDateInShortFormat2() {
		final boolean isSeconds = true;
		final boolean isShortNotation = true;
		final String expectedReadableDate = "2d 2h 2m 2s";
		final String actualReadableDate = DateUtil.getReadableTime(
				(60000 * 60 * 24 * 2) + (60000 * 60 * 2) + 60000 * 2 + 1000 * 2, isSeconds, isShortNotation);
		assertEquals(actualReadableDate, expectedReadableDate);
	}

	/**
	 * Test get readable time should return readable date in short format
	 * without seconds.
	 */
	@Test
	public void testGetReadableTimeShouldReturnReadableDateInShortFormatWithoutSeconds() {
		final boolean isSeconds = false;
		final boolean isShortNotation = true;
		final String expectedReadableDate = "2d 2h 2m ";
		final String actualReadableDate = DateUtil.getReadableTime(
				(60000 * 60 * 24 * 2) + (60000 * 60 * 2) + 60000 * 2 + 1000 * 2, isSeconds, isShortNotation);
		assertEquals(actualReadableDate, expectedReadableDate);
	}

	/**
	 * Test get readable time should return na 1.
	 */
	@Test
	public void testGetReadableTimeShouldReturnNA1() {
		final boolean isSeconds = true;
		final boolean isShortNotation = false;
		final String expectedReadableDate = "N/A";
		final String actualReadableDate = DateUtil.getReadableTime(100, isSeconds, isShortNotation);
		assertEquals(actualReadableDate, expectedReadableDate);
	}

	/**
	 * Test get readable time should return n a2.
	 */
	@Test
	public void testGetReadableTimeShouldReturnNA2() {
		final boolean isSeconds = true;
		final boolean isShortNotation = true;
		final String expectedReadableDate = "N/A";
		final String actualReadableDate = DateUtil.getReadableTime(100, isSeconds, isShortNotation);
		assertEquals(actualReadableDate, expectedReadableDate);
	}

	/**
	 * Test to string.
	 */
	@Test
	public void testToString() {
		Assert.assertNotNull(DateUtil.toString(new Date(0L), null), "0");
	}
}
