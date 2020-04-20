package com.owners.gravitas.service.impl;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.owners.gravitas.constants.Constants;
import com.owners.gravitas.dao.LeadLayoutDao;
import com.owners.gravitas.service.LeadLayoutService;

@Service
public class LeadLayoutServiceImpl implements LeadLayoutService {

	/** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( LeadLayoutServiceImpl.class );
	
	/** The leadLayoutDao service. */
    @Autowired
    private LeadLayoutDao leadLayoutDao;
	
    private Object defaultAllLeadLayout; 
    
    private Object defaultMyLeadLayout; 
    
    @PostConstruct
	public void setDefaultLeadLayout() {
    	LOGGER.info( "Start of LeadLayoutServiceImpl setDefaultLeadLayout");
    	ObjectMapper objectMapper = new ObjectMapper();
    	
    	StringBuilder sb = new StringBuilder();
    	try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Constants.ALL_LEAD_LAYOUT_FILE)))) {
    		String line;
    		while ((line = br.readLine()) != null) {
    			sb.append(line);
    		}
        	this.defaultAllLeadLayout = objectMapper.readValue(sb.toString(), Object.class);
        } catch (FileNotFoundException e) {
        	LOGGER.info( "Exception in LeadLayoutServiceImpl setAllDefaultLeadLayoutEnd : {}", e );
		} catch (IOException e) {
			LOGGER.info( "Exception in LeadLayoutServiceImpl setAllDefaultLeadLayoutEnd : {}", e );
		}
    	
    	sb = new StringBuilder();
    	try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(Constants.MY_LEAD_LAYOUT_FILE)))) {
    		String line;
    		while ((line = br.readLine()) != null) {
    			sb.append(line);
    		}
        	this.defaultMyLeadLayout = objectMapper.readValue(sb.toString(), Object.class);
        } catch (FileNotFoundException e) {
        	LOGGER.info( "Exception in LeadLayoutServiceImpl setMyDefaultLeadLayoutEnd : {}", e );
		} catch (IOException e) {
			LOGGER.info( "Exception in LeadLayoutServiceImpl setMyDefaultLeadLayoutEnd : {}", e );
		}
    	LOGGER.info( "End of LeadLayoutServiceImpl setDefaultLeadLayout");
	}

	@Override
	public String getLeadLayout(String emailId, String type, String source) {
		return leadLayoutDao.getLeadLayout(emailId, type, source);
	}

	@Override
	public void saveOrUpdateLeadLayout(String emailId, String type, String source, String layout) {
		leadLayoutDao.saveOrUpdateLeadLayout(emailId, type, source, layout);
	}

	@Override
	public Object getDefaultAllLeadLayout() {
		return defaultAllLeadLayout;
	}

	@Override
	public Object getDefaultMyLeadLayout() {
		return defaultMyLeadLayout;
	}
}
