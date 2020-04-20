package com.owners.gravitas.service;

import com.hubzu.cms.client.dto.CmsResponse;

public interface GravitasCMSService {

	public CmsResponse fetchFromCMS(String path);
	
}
