/*
 *
 */
package com.owners.gravitas.constants;

import static com.owners.gravitas.constants.Constants.EMAIL;
import static com.owners.gravitas.constants.Constants.ID;
import static com.owners.gravitas.constants.Constants.LOAN_NUMBER;
import static com.owners.gravitas.constants.Constants.NAME;
import static com.owners.gravitas.constants.Constants.OBJECTTYPE;
import static com.owners.gravitas.constants.Constants.RECORD_TYPE_ID;
import static com.owners.gravitas.constants.Constants.RECORD_TYPE_NAME;

/**
 * The Interface CRMQuery.
 *
 * @author vishwanathm
 */
public final class CRMQuery {

    /** The Constant GET_MLS_RECORD_TYPE_ID. */
    public static final String GET_RECORD_TYPE_ID = "select id from RecordType where sobjecttype='{" + OBJECTTYPE
            + "}' and Name='{" + NAME + "}'";

    /** The Constant GET_RECORD_TYPE_NAME. */
    public static final String GET_RECORD_TYPE_NAME = "select Name from RecordType where id='{" + ID + "}'";

    /** The get cmr user id by email. */
    public static final String GET_CMR_USER_ID_BY_EMAIL = "SELECT u.id from User u where u.email='{" + EMAIL + "}'";

    /** The get opportunity. */
    public static final String GET_OPPORTUNITY = "SELECT o.name from Opportunity o where o.name='{" + NAME + "}'";

    /** The get opportunity for the provided loan number. */
    public static final String GET_OPPORTUNITY_ID_BY_RECORDTYPE_ID_AND_LOAN_NUMBER = "SELECT o.id from Opportunity o where o.RecordTypeId='{"
            + RECORD_TYPE_ID + "}'" + " and o.Loan_Number__c='{" + LOAN_NUMBER + "}'";

    /** The get opportunity for the provided email. */
    public static final String GET_OPPORTUNITY_ID_BY_RECORDTYPE_ID_AND_EMAIL = "SELECT o.id from Opportunity o where o.RecordTypeId='{"
            + RECORD_TYPE_ID + "}'"
            + " and o.id in (SELECT oc.OpportunityId FROM OpportunityContactRole oc where oc.Contact.Email='{" + EMAIL
            + "}')";

    /** The Constant GET_OPPORTUNITY_EMAIL_AND_RECORD_TYPE. */
    public static final String GET_OPPORTUNITY_EMAIL_AND_RECORD_TYPE = "SELECT o.RecordType.Name FROM Opportunity o WHERE o.RecordType.Name ='{"
            + RECORD_TYPE_NAME
            + "}' and o.id in (SELECT oc.OpportunityId FROM OpportunityContactRole oc where oc.Contact.Email='{" + EMAIL
            + "}')";

    /** The get account by seller id. */
    public static final String GET_ACCOUNT_BY_SELLER_ID = "SELECT a.id from Account a where a.name='{" + NAME + "}'";

    /** The Constant GET_ACCOUNT_NAME_BY_ID. */
    public static final String GET_ACCOUNT_NAME_BY_ID = "SELECT a.Name from Account a where a.id='{" + ID + "}'";

    /** The Constant GET_RECORD_TYPE_BY_ID. */
    public static final String GET_RECORD_TYPE_BY_ID = "SELECT  RecordType.Name, Owner.Name, Owner.Email from Opportunity where id= '{"
            + ID + "}'";

    /** The get account by seller id. */
    public static final String GET_CONTACT_BY_EMAIL_ID = "SELECT c.id from Contact c where c.email='{" + EMAIL + "}'";

    /** The Constant GET_CRM_USER_ID_USER_NAME. */
    public static final String GET_CRM_USER_ID_USER_NAME = "SELECT id, name from User u where u.name= '{" + NAME + "}'";

    /** The Constant GET_LEAD_ID_BY_PARAMETERS. */
    public static final String GET_LEAD_ID_BY_PARAMETERS = "SELECT  id, email, lastModifiedDate, recordTypeId,  leadSource, ConvertedOpportunityId  from Lead where email='{"
            + EMAIL + "}' ORDER BY lastModifiedDate DESC";

    /** The Constant GET_MORTGAGE_LEAD_ID_BY_PARAMETERS. */
    public static final String GET_MORTGAGE_LEAD_ID_BY_PARAMETERS = "SELECT  id, email, lastModifiedDate, recordTypeId,  leadSource, convertedOpportunityId  from Lead where email='{"
            + EMAIL + "}' and recordTypeId='{" + RECORD_TYPE_ID + "}' ORDER BY lastModifiedDate DESC";

    /** The Constant GET_LEAD_ID_BY_EMAIL_AND_RECORD_TYPE_ID. */
    public static final String GET_LEAD_ID_BY_EMAIL_AND_RECORD_TYPE_ID = "SELECT  id, email, lastModifiedDate, recordTypeId,  leadSource, convertedOpportunityId, Gravitas_Dedup_Count__c, Status  from Lead where email='{"
            + EMAIL + "}' and recordTypeId='{" + RECORD_TYPE_ID + "}' ORDER BY lastModifiedDate DESC";

    /** The Constant GET_OPPORTUNITY_BY_ID. */
    public static final String GET_OPPORTUNITY_BY_ID = "SELECT RecordType.Name, LeadSource, Owners_Agent__c, Lead_Request_Type__c, Opportunity_Notes__c, Pre_Approved_for_Mortgage__c, Working_with_External_Agent__c, Buyer_Readiness_Timeline__c, Listing_ID__c, Financing__c, Interested_Zip_Codes__c, Preferred_Contact_Time__c,Preferred_Contact_Method__c,Price_Range__c,Offer_Amount__c,Earnest_Money_Deposit__c,Purchase_Method__c,Down_Payment__c,Property_Tour_Information__c,Lead_Message__c,Median_Price__c,StageName,Reason_Lost__c,Commission_Post_Closing__c,Sales_Price_Post_Closing__c,CloseDate,Pre_approved_Amount__c,Commission_Base_Price__c,Title_Company_Non_PTS__c,Pending_Date__c,Acutal_Closing_Date__c,Expected_Agent_Revenue__c,Expected_Owners_com_Revenue__c, Gravitas_Record_History__c, Preferred_Language__c, Title_Selection_Reason__c,Price_Ranges__c, Property_Address__c, Listing_Side_Commission__c, List_Price__c, Listing_Date__c, Listing_Expiration_Date__c, Property_State__c, Record_Type_Name__c, Buyer_Lead_Quality__c, Gravitas_Dedup_Count__c, LastModifiedBy.FirstName, LastModifiedBy.LastName, Property_States_del__c, Property_Zip_del__c,Farming_Group__c  FROM Opportunity WHERE id='{"
            + ID + "}'";

    /** The Constant GET_CONTACT_BY_ID. */
    public static final String GET_CONTACT_BY_ID = "SELECT contactId, isPrimary, Contact.FirstName, Contact.LastName, Contact.Email, Contact.Phone, Contact.Preferred_Contact_Time__c, Contact.Preferred_Contact_Method__c, Opportunity.Owners_Agent__c, Contact.LastModifiedBy.FirstName, Contact.LastModifiedBy.LastName FROM OpportunityContactRole WHERE {findBy}='{"
            + ID + "}'";

    /** The Constant GET_CONTACT_DETAILS_BY_EMAIL_ID. */
    public static final String GET_CONTACT_DETAILS_BY_EMAIL_ID = "SELECT ID, FirstName, LastName, Email, Phone, Preferred_Contact_Time__c, Preferred_Contact_Method__c, RecordTypeId, AccountId FROM Contact WHERE email = '{"
            + EMAIL + "}'";

    /** The Constant GET_ACCOUNT_ID_BY_OPPORTUNITY_ID. */
    public static final String GET_ACCOUNT_ID_BY_OPPORTUNITY_ID = "select AccountId from Opportunity where id = '{" + ID
            + "}'";

    /** The Constant GET_ACCOUNT_ID_BY_EMAIL. */
    public static final String GET_ACCOUNT_ID_BY_EMAIL = "select AccountId from Contact where email = '{" + EMAIL
            + "}'";

    /** The Constant GET_CONTACT_ID_BY_OPPORTUNITY_ID. */
    public static final String GET_CONTACT_ID_BY_OPPORTUNITY_ID = "select Primary_Contact__c from Opportunity where id = '{"
            + ID + "}'";

    /** The Constant GET_OPPORTUNITIES_BY_AGENT_EMAIL. */
    public static final String GET_OPPORTUNITIES_BY_AGENT_EMAIL = "SELECT Opportunity.id, Opportunity.RecordType.Name, Opportunity.LeadSource, Opportunity.Owners_Agent__c, "
            + "Opportunity.Lead_Request_Type__c, Opportunity.Opportunity_Notes__c, Opportunity.Pre_Approved_for_Mortgage__c, Opportunity.Working_with_External_Agent__c, "
            + "Opportunity.Buyer_Readiness_Timeline__c, Opportunity.Listing_ID__c, Contact.Preferred_Contact_Time__c,Contact.Preferred_Contact_Method__c,"
            + "Opportunity.Price_Range__c,Opportunity.Offer_Amount__c,Opportunity.Earnest_Money_Deposit__c,Opportunity.Purchase_Method__c,Opportunity.Down_Payment__c,"
            + "Opportunity.Property_Tour_Information__c,Opportunity.Lead_Message__c,Opportunity.Median_Price__c,Opportunity.StageName,Opportunity.Reason_Lost__c,"
            + "Opportunity.Commission_Post_Closing__c,Opportunity.Sales_Price_Post_Closing__c,Opportunity.CloseDate,Opportunity.Pre_approved_Amount__c,"
            + "Opportunity.Commission_Base_Price__c,Opportunity.Title_Company_Non_PTS__c,Opportunity.Pending_Date__c,Opportunity.Acutal_Closing_Date__c,"
            + "Opportunity.Expected_Agent_Revenue__c,Opportunity.Expected_Owners_com_Revenue__c,Opportunity.Interested_Zip_Codes__c, Opportunity.Preferred_Language__c,"
            + " Opportunity.Gravitas_Record_History__c, Opportunity.Title_Selection_Reason__c,Opportunity.Price_Ranges__c,Opportunity.Property_Address__c,"
            + "Opportunity.Listing_Side_Commission__c,Opportunity.List_Price__c,Opportunity.Listing_Date__c,Opportunity.Listing_Expiration_Date__c,"
            + "LastModifiedBy.FirstName, LastModifiedBy.LastName,contactId, isPrimary, "
            + "Contact.FirstName, Contact.LastName, Contact.Email, Contact.Phone, Contact.LastModifiedBy.FirstName, Contact.LastModifiedBy.LastName FROM OpportunityContactRole WHERE isPrimary=true and Opportunity.Owners_Agent__c='{"
            + EMAIL + "}'";

    /** The Constant GET_LEAD_OWNER_INFO. */
    public static final String GET_LEAD_OWNER_INFO = "SELECT FirstName, Name, Email FROM User WHERE id = '{" + ID
            + "}'";

    /** The Constant GET_AGENT_ID_BY_EMAIL. */
    public static final String GET_AGENT_ID_BY_EMAIL = "SELECT id FROM Agent__c WHERE Email__c= '{" + EMAIL + "}'";

    /** The Constant GET_CRM_AGENT_DETAILS_BY_EMAIL. */
    public static final String GET_CRM_AGENT_DETAILS_BY_EMAIL = "SELECT License_Number__c, Agent_SMS_Email__c, Agent_Mobile_Carrier__c, Address1__c, Address2__c, SF_User_ID__c, Name, City__c, Field_Agent__c, Phone__c, Zip_Code__c, Id, Status__c, Agent_App_Starting_Date__c, State__c, Email__c, Active__c, Notes__c FROM Agent__c WHERE Email__c= '{"
            + EMAIL + "}'";

    /** The Constant GET_TITLE_CLOSING_COMPANY_BY_OPPORTUNITY_ID. */
    public static final String GET_TITLE_CLOSING_COMPANY_BY_OPPORTUNITY_ID = "SELECT Title_Closing_Company__c FROM Opportunity where id = '{"
            + ID + "}'";

    /**
     * Instantiates a new CRM query.
     */
    private CRMQuery() {
        // does noting
    }

}
