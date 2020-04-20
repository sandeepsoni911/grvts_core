package com.owners.gravitas.controller;

import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;

import com.owners.gravitas.annotation.PerformanceLog;
import com.owners.gravitas.business.LeadBusinessService;

/**
 * The Class JmxController.
 * 
 * @author ankusht
 */
@RestController
public class JmxController {
	/** The Constant logger. */
	private static final Logger LOGGER = LoggerFactory.getLogger(JmxController.class);

	/** instance of {@link LeadBusinessService}. */
	@Autowired
	private LeadBusinessService buyerBusinessService;

	// /** The jmx user business service. */
	// @Autowired
	// private JmxUserBusinessService jmxUserBusinessService;
	//
	// /**
	// * Adds the jmx user.
	// *
	// * @param jmxUserDto
	// * the jmx user dto
	// */
	// @RequestMapping( value = "/jmxconsole/user", method = POST, consumes =
	// APPLICATION_JSON_VALUE )
	// public void addJmxUser( @RequestBody final JmxUserDto jmxUserDto ) {
	// jmxUserBusinessService.addJmxUser( jmxUserDto );
	// }
	//
	// /**
	// * Adds the jmx user.
	// *
	// * @param username
	// * the username
	// */
	// @RequestMapping( value = "/jmxconsole/user/{username}", method = DELETE )
	// public void deleteJmxUser( @PathVariable final String username ) {
	// jmxUserBusinessService.delete( username );
	// }
	//
	// /**
	// * Change password.
	// *
	// * @param jmxUserDto
	// * the jmx user dto
	// */
	// @RequestMapping( value = "/jmxconsole/user/{username}/password", method =
	// PUT, consumes = APPLICATION_JSON_VALUE )
	// public void changePassword( @PathVariable final String username, @RequestBody
	// final Map< String, String > map ) {
	// jmxUserBusinessService.changePassword( username, map.get(
	// KEY_FOR_PASSWORD_ENC ) );
	// }

	/**
	 * set seesion id and csompagin id.
	 *
	 * @param jsonData
	 *            the json data
	 * @return the void response
	 */
	@PerformanceLog
	@RequestMapping(value = "/lead/liveVox/chache", consumes = MediaType.APPLICATION_JSON_VALUE, method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_VALUE)
	public void enterValueIntoRedisCache(@RequestBody final Map<String, Object> request) {
		LOGGER.debug("Adding session id and campagin id to redis cache for  Live Vox!!");
		buyerBusinessService.enterValueIntoRedisCache(request);
	}
}
