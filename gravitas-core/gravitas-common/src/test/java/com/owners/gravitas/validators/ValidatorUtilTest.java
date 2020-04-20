package com.owners.gravitas.validators;

import java.util.ArrayList;
import java.util.List;

import org.mockito.InjectMocks;
import org.testng.annotations.Test;

/**
 * The Class ValidatorUtilTest.
 *
 * @author pabhishek
 */
public class ValidatorUtilTest {
	
	/** The validator util. */
    @InjectMocks
    private ValidatorUtil validatorUtil;
    
    /**
     * Test validate decimal number.
     */
    @Test
    public void testValidateDecimalNumber(){
    	validatorUtil.validateDecimalNumber("value1",new ArrayList<String>(),"errorcode");
    	validatorUtil.validateDecimalNumber(null,new ArrayList<String>(),"errorcode");
    	validatorUtil.validateDecimalNumber("20",new ArrayList<String>(),"errorcode");
    }
    
    /**
     * Test validate decimal length.
     */
    @Test
    public void testValidateDecimalLength(){
    	validatorUtil.validateDecimalLength("value1",new ArrayList<String>(),"errorcode");
    	validatorUtil.validateDecimalLength(null,new ArrayList<String>(),"errorcode");
    	validatorUtil.validateDecimalLength("20.45789",new ArrayList<String>(),"errorcode");
    }
    
    /**
     * Test validate date.
     */
    @Test
    public void testValidateDate(){
    	validatorUtil.validateDate("value1",new ArrayList<String>(),"errorcode");
    	validatorUtil.validateDate(null,new ArrayList<String>(),"errorcode");
    	validatorUtil.validateDate("1990-11-20 10:20 5",new ArrayList<String>(),"errorcode");
    	
    }
    
    /**
     * Test check for length.
     */
    @Test
    public void testCheckForLength(){
    	validatorUtil.checkForLength("value1",2,4,new ArrayList<String>(),"errorcode");
    	validatorUtil.checkForLength(null,2,4,new ArrayList<String>(),"errorcode");
    	validatorUtil.checkForLength("a",2,4,new ArrayList<String>(),"errorcode");
    }
    
    /**
     * Test check for required.
     */
    @Test
    public void testCheckForRequired(){
    	validatorUtil.checkForRequired("value1",new ArrayList<String>(),"errorcode");
    	validatorUtil.checkForRequired("",new ArrayList<String>(),"errorcode");
    	
    	
    }

}
