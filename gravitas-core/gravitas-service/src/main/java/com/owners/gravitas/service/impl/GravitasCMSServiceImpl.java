package com.owners.gravitas.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.hubzu.cms.client.dto.CmsRequest;
import com.hubzu.cms.client.dto.CmsResponse;
import com.hubzu.cms.client.exception.CmsClientException;
import com.hubzu.cms.client.service.CmsService;
import com.owners.gravitas.service.GravitasCMSService;

@Service
public class GravitasCMSServiceImpl implements GravitasCMSService{

	/** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( GravitasCMSServiceImpl.class );
	
	@Autowired
	private CmsService CmsService;
	
	/** The error log file path. */
    @Value( "${cms.content.gravitas.path}" )
    private String GRAVITAS_CMS_ROOT_PATH;
	
	@Override
	public CmsResponse fetchFromCMS(String path) {
		LOGGER.info("GravitasCMSServiceImpl - fetchFromCMS Start : {}",path);
		CmsResponse response = null;
		final CmsRequest request = new CmsRequest();
		if(path != null){
			path = path.trim();
			if(path.charAt(0) != '/'){
				path = "/" + path;
			}
		}else {
			path = "";
		}
		request.setResourcePath(GRAVITAS_CMS_ROOT_PATH + path);
		try {
			response = CmsService.fetchContent(request);
		} catch (CmsClientException e) {
			LOGGER.error("CmsClientException in GravitasCMSServiceImpl - fetchFromCMS method :{} ",e.getMessage());
		} catch (Exception e) {
			LOGGER.error("Exception in GravitasCMSServiceImpl - fetchFromCMS method :{} ",e.getMessage());
		} 
		LOGGER.info("GravitasCMSServiceImpl - fetchFromCMS Start : {}",response.getContent());
		return response;
	}

}
