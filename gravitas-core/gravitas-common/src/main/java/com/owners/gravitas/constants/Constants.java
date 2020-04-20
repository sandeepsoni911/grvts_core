/*
 *
 */
package com.owners.gravitas.constants;

import static java.io.File.separator;
import static java.io.File.separatorChar;

/**
 * Constants for application.
 *
 * @author harshads
 *
 */
public class Constants {

    /** Gravitas configuration directory. **/
    public static final String GRAVITAS_CONFIG_DIR = System.getProperty( "gravitas.home" ) + separatorChar + "conf";

    /** The Constant GRAVITAS_ERROR_LOGGING_CONFIG_DIR. */
    public static final String GRAVITAS_ERROR_LOGGING_CONFIG_DIR = System.getProperty( "gravitas.home" ) + separatorChar
            + "logs";

    /** The Constant FIREBASE_ACCESS_FILE. */
    public static final String FIREBASE_ACCESS_FILE = GRAVITAS_CONFIG_DIR + separator + "firebase-access.json";

    /** The Constant BIGQUERY_ACCESS_FILE. */
    public static final String BIGQUERY_ACCESS_FILE = GRAVITAS_CONFIG_DIR + separator + "bigquery-access.json";

    /** The Constant GOOGLE_ACCESS_FILE. */
    public static final String GOOGLE_ACCESS_FILE = GRAVITAS_CONFIG_DIR + separator + "google-api-access.p12";

    /** The Constant LEAD_LAYOUT_FILE. */
    public static final String ALL_LEAD_LAYOUT_FILE = GRAVITAS_CONFIG_DIR + separator + "allleadDisplayLayout.json";

    /** The Constant LEAD_LAYOUT_FILE. */
    public static final String MY_LEAD_LAYOUT_FILE = GRAVITAS_CONFIG_DIR + separator + "myleadDisplayLayout.json";

    /** The Constant BEARER. */
    public static final String BEARER = "Bearer ";

    /** The Constant NOT_AVAILABLE. */
    public static final String NOT_AVAILABLE = "Not Available";

    /** The Constant BASIC. */
    public static final String BASIC = "Basic ";

    /** The Constant SF_AUTO_ASSIGN_HEADER. */
    public static final String SF_AUTO_ASSIGN_HEADER = "Sforce-Auto-Assign";

    /** The Constant NOTE_EMAIL_AFBA_SUBJECT. */
    public static final String NOTE_EMAIL_AFBA_SUBJECT = "AfbA sent to lead on ";

    /** The Constant NOTIFICATION_MESSAGE_TYPE_NAME. */
    public static final String NOTIFICATION_MESSAGE_TYPE_NAME = "POTENTIAL_BUYER_AFTER_HOURS";

    /** The Constant REFRRAL_EMAIL_NOTIFICATION_MESSAGE_TYPE_NAME. */
    public static final String REFRRAL_EMAIL_NOTIFICATION_MESSAGE_TYPE_NAME = "Referral_Exchange_Intro_Email";

    /** The Constant AFFILIATE_EMAIL_PARSING_NOTIFICATION_MESSAGE_TYPE_NAME. */
    public static final String AFFILIATE_EMAIL_PARSING_NOTIFICATION_MESSAGE_TYPE_NAME = "AFFILIATE_LEAD_PARSING_ERROR";

    /**
     * The Constant LEAD_TO_OPPORTUNITY_CONVERT_NOTIFICATION_MESSAGE_TYPE_NAME.
     */
    public static final String LEAD_TO_OPPORTUNITY_CONVERT_NOTIFICATION_MESSAGE_TYPE_NAME = "AFBA_LEAD_CONVERSION";

    /**
     * The Constant AFFILIATE_EMAIL_VALIDATION_NOTIFICATION_MESSAGE_TYPE_NAME.
     */
    public static final String AFFILIATE_EMAIL_VALIDATION_NOTIFICATION_MESSAGE_TYPE_NAME = "AFFILIATE_LEAD_VALIDATION_ERROR";

    /** The Constant NOTIFICATION_CLIENT_ID. */
    public static final String NOTIFICATION_CLIENT_ID = "GRAVITAS";

    /** Regular exp. to parse alpha numeric values. */
    public static final String REG_EXP_ALPANUMERICS = "^[a-zA-Z0-9,'.;\\-\\s]*";

    /** The Constant REG_EXP_NUMERICS. */
    public static final String REG_EXP_NUMERICS = "[\\d]+";

    /** The Constant REG_EXP_NON_NUMERICS. */
    public static final String REG_EXP_NON_NUMERICS = "[\\D]+";

    /** The Constant REG_EXP_NUMERIC_VERSION. */
    public static final String REG_EXP_NUMERIC_VERSION = "(([0-9]{1,2})\\.){2}[0-9]{1,2}";

    /** The Constant REG_EXP_BLANK_SPACES. */
    public static final String REG_EXP_BLANK_SPACES = "\\s+";

    /** The Constant REG_EXP_NON_BLANKSPACE_CHARS. */
    public static final String REG_EXP_NON_BLANKSPACE_CHARS = "\\S+";

    /** The Constant REG_EXP_HTML_TAGS. */
    public static final String REG_EXP_HTML_TAGS = "\\<.*?>";

    /** The Constant BLANK. */
    public static final String BLANK = "";

    /** The Constant BLANK_SPACE. */
    public static final String BLANK_SPACE = " ";

    /** The Constant UNDER_SCORE. */
    public static final String UNDER_SCORE = "_";

    /** The Constant COMMA. */
    public static final String COMMA = ",";

    public static final String NA_WITHOUT_DIVIDER = "NA";

    /** The Constant DOLLAR. */
    public static final String DOLLAR = "\\$";

    /** The Constant LEAD_SOURCE_URL. */
    public static final String LEAD_SOURCE_URL = "http://www.owners.com";

    /** The Constant COMMA. */
    public static final String COMMA_AND_SPACE = "\\s*,\\s*";

    /** The Constant OPPORTUNITY_PROBABILITY. */
    public static final Integer OPPORTUNITY_PROBABILITY = Integer.valueOf( 10 );

    /** The Constant QUERY_PARAM_ID. */
    public static final String QUERY_PARAM_ID = "Id";

    /** The Constant TITLE_CLOSING_COMPANY. */
    public static final String TITLE_CLOSING_COMPANY = "Title_Closing_Company__c";

    /** The Constant QUERY_PARAM_ACCOUNT_ID. */
    public static final String QUERY_PARAM_ACCOUNT_ID = "AccountId";

    /** The Constant QUERY_PARAM_PRIMARY_CONTACT. */
    public static final String QUERY_PARAM_PRIMARY_CONTACT = "Primary_Contact__c";

    /** The Constant REG_EXP_EMAIL. */
    public static final String REG_EXP_EMAIL = "^[A-Za-z0-9._%+-/!#$%&'*=?^_`{|}~]+@[A-Za-z0-9]+[A-Za-z0-9.-]*[.]+[A-Za-z0-9]+$";

    /** The Constant REGEX_CURRENCY. */
    public static final String REGEX_CURRENCY = "^(\\s*|\\d{0,16}(\\.\\d{1,2})|\\d{0,16})$";

    /** The Constant REGEX_NUMBER_VALIDATION_PATTERN. */
    public static final String REGEX_NUMBER_VALIDATION_PATTERN = "[0-9]*([.][0-9]+)?";

    /** The Constant REGEX_STATE. */
    public static final String REGEX_STATE = "\\s*|[a-zA-Z]{2}";

    /** The Constant REGEX_DECIMAL_LENGTH_VALIDATION_PATTERN. */
    public static final String REGEX_DECIMAL_LENGTH_VALIDATION_PATTERN = "\\w{0,16}+([.]\\w{1,2})?";

    /** The Constant REGEX_DOUBLE_QUOTE_START_END. */
    public static final String REGEX_DOUBLE_QUOTE_START_END = "^\"|\"$";

    /** The Constant EQUAL_TO. */
    public static final String EQUAL_TO = "=";

    /** The Constant OPENING_BRACE. */
    public static final String QUOTED_OPENING_BRACE = "'{";

    /** The Constant CLOSING_BRACE. */
    public static final String QUOTED_CLOSING_BRACE = "}'";

    /** The Constant OPENING_BRACKET. */
    public static final String OPENING_BRACKET = "(";

    /** The Constant CLOSING_BRACKET. */
    public static final String CLOSING_BRACKET = ")";

    /** The Constant OBJECTTYPE. */
    public static final String OBJECTTYPE = "objecttype";

    /** The Constant NAME. */
    public static final String NAME = "name";

    /** The Constant ID. */
    public static final String ID = "id";

    /** The Constant EMAIL. */
    public static final String EMAIL = "email";

    /** The Constant RECORD_TYPE_ID. */
    public static final String RECORD_TYPE_ID = "recordTypeId";

    /** The Constant RECORD_TYPE_ID. */
    public static final String RECORD_TYPE_NAME = "recordTypeName";

    /** The Constant PROPERTY_ORDER_TYPE. */
    public static final String PROPERTY_ORDER_TYPE = "TRIAL-LISTING";

    /** The Constant HYPHEN. */
    public static final String HYPHEN = "-";

    /** The Constant CRM_APPEND_SEPERATOR. */
    public static final String CRM_APPEND_SEPERATOR = "~~";

    /** The Constant GRAVITAS. */
    public static final String GRAVITAS = "GRAVITAS";

    /** The Constant SYSTEM_GENERATED. */
    public static final String SYSTEM_GENERATED = "SYSTEM_GENERATED";

    /** The Constant GRAVITAS. */
    public static final String GRAVITAS_CONTACT_ERR = "GRAVITAS_CONTACT_ERROR";

    /** The Constant AGENT_ID. */
    public static final String AGENT_ID = "agentId";

    /** The Constant CRM_OPPORTUNITY_ID. */
    public static final String CRM_OPPORTUNITY_ID = "crmOpportunityId";

    /** The Constant AGENT_EMAIL. */
    public static final String AGENT_EMAIL = "agentEmail";

    /** The Constant CONTACT_EMAIL. */
    public static final String CONTACT_EMAIL = "contactEmail";

    /** The Constant BUYER_NAME. */
    public static final String BUYER_NAME = "BUYER_NAME";

    /** The Constant TASK_TITLE. */
    public static final String TASK_TITLE = "TASK_TITLE";

    /** The Constant NOTIFICATION_TYPE. */
    public static final String NOTIFICATION_TYPE = "notificationType";

    /** The Constant OPPORTUNITY_ID. */
    public static final String OPPORTUNITY_ID = "opportunityId";

    /** The Constant TASK_ID. */
    public static final String TASK_ID = "taskId";

    /** The Constant CONTACT_ID. */
    public static final String CONTACT_ID = "contactId";

    /** The Constant CLIENT_ID. */
    public static final String OWNERS_ID = "ownersId";

    /** The Constant FROM_DTM. */
    public static final String FROM_DTM = "fromDtm";

    /** The Constant LEAD. */
    public static final String LEAD = "LEAD";

    /** The Constant MARKETING_BUYER_FIRST_NAME. */
    public static final String MARKETING_BUYER_FIRST_NAME = "BUYER_FIRST_NAME";

    /** The Constant MARKETING_LEAD_OWNER_FIRST_NAME. */
    public static final String MARKETING_LEAD_OWNER_FIRST_NAME = "LEAD_OWNER_FIRST_NAME";

    /** The Constant MARKETING_LEAD_OWNER_EMAIL. */
    public static final String MARKETING_LEAD_OWNER_EMAIL = "LEAD_OWNER_EMAIL";

    /** The Constant MARKETING_LEAD_OWNER_FULL_NAME. */
    public static final String MARKETING_LEAD_OWNER_FULL_NAME = "LEAD_OWNER_FULL_NAME";

    /** The Constant LOST_STATUS. */
    public static final String LOST_STATUS = "lostStatus";

    /** The Constant PIPE. */
    public static final String PIPE = "|";

    /** The Constant COLON. */
    public static final String COLON = ":";

    /** The Constant SEMI_COLON. */
    public static final String SEMI_COLON = ";";

    /** The Constant PERIOD. */
    public static final String PERIOD = ".";

    /** The Constant EXCLAMATORY_MARK. */
    public static final String EXCLAMATORY_MARK = "!";

    /** Note subject constant. */
    public static final String NOTE_EMAIL_SENT_SUBJECT = "Email Sent: Template Name - ";

    /** The Constant LOAN_NUMBER. */
    public static final String LOAN_NUMBER = "loanNumber";

    /** The Constant OCL_LOAN_PHASE_PRE_FIX. */
    public static final String OCL_LOAN_PHASE_PRE_FIX = "ocl.loan.phase.";

    /** The Constant OCL_LOAN_PHASE_STAGE_SUFFIX. */
    public static final String OCL_LOAN_PHASE_STAGE_SUFFIX = ".stage";

    /** The Constant OCL_LOAN_PHASE_DESCRIPTION_SUFFIX. */
    public static final String OCL_LOAN_PHASE_DESCRIPTION_SUFFIX = ".description";

    /** The Constant PERCENTAGE_SIGN. */
    public static final String PERCENTAGE_SIGN = "%";

    /** The Constant CAUSE_COLUMN_LENGTH. */
    public static final int CAUSE_COLUMN_LENGTH = 1000;

    /** The Constant OK_STATUS. */
    public static final String OK_STATUS = "Ok";

    /** The Constant HTTP_PREFIX. */
    public static final String HTTP_PREFIX = "http://";

    /** The Constant THOUSAND. */
    public static final String THOUSAND = "000";

    /** The Constant MILLION. */
    public static final String MILLION = "000000";

    /** The Constant ZERO. */
    public static final String ZERO = "0";

    /** The Constant THOUSAND_CONSTANT. */
    public static final String THOUSAND_CONSTANT = "K";

    /** The Constant MILLION_CONSTANT. */
    public static final String MILLION_CONSTANT = "M";

    /** The Constant MILLION. */
    public static final int MILLION_VAL = 1000000;

    /** The Constant THOUSAND. */
    public static final int THOUSAND_VAL = 1000;

    /** The Constant DIGIT_REGEX. */
    public static final String DIGIT_REGEX = "\\d+";

    /** The Constant CRM_AGENT_PHONE. */
    public static final String CRM_AGENT_PHONE = "Phone__c";

    /** The UTF-8 encoding format. */
    public static final String UTF_8 = "UTF-8";

    /** The Constant HASH_FUNCTION. */
    public static final String HASH_FUNCTION = "MD5";

    /** The Constant TASK. */
    public static final String TASK = "Task";

    /** The Constant ACTION_OBJ. */
    public static final String ACTION_OBJ = "actionObj";

    /** The Constant PLAT_FORM_VERSION. */
    public static final String PLAT_FORM_VERSION = "platFormVersion";

    /** The Constant PLAT_FORM. */
    public static final String PLAT_FORM = "platForm";

    /** The Constant ACTION_BY. */
    public static final String ACTION_BY = "actionBy";

    /** The Constant ENTITY_ID. */
    public static final String ENTITY_ID = "entityId";

    /** The versionInfo constant. */
    public static final String VERSION_INFO = "versionInfo";

    /** The Constant BEST_FIT_AGENT_DAY_THRESHOLD. */
    public static final String BEST_FIT_AGENT_DAY_THRESHOLD = "bestFitAgentDayThreshold";

    /** The Constant BEST_FIT_AGENT_OPPORTUNITY_THRESHOLD. */
    public static final String BEST_FIT_AGENT_OPPORTUNITY_THRESHOLD = "bestFitAgentOpportunityThreshold";

    /** The Constant OPPORTUNITY_RR_THRESHOLD. */
    public static final String OPPORTUNITY_RR_THRESHOLD = "opportunityRRThreshold";

    /** The Constant GOOGLE_USER_ID. */
    public static final String GMAIL_USER_ID = "me";

    /** The Constant SPACE. */
    public static final String SPACE = " ";

    /** The Constant PTS. */
    public static final String PTS = "PTS";

    /** The Constant BUYER_OFFER_TEXT. */
    public static final String BUYER_OFFER_TEXT = "offer made by";

    /** The Constant APPOINTMENT_TEXT. */
    public static final String APPOINTMENT_TEXT = "tour request from";

    /** The Constant INQUIRY_TEXT. */
    public static final String INQUIRY_TEXT = "question from";

    /** The Constant DYNAMIC_TEXT for push notification lead creation. */
    public static final String DYNAMIC_TEXT = "DYNAMIC_TEXT";

    /** The call key. */
    public static String CALL_KEY = "call";

    /** The sms key. */
    public static String SMS_KEY = "sms";

    /** The email key. */
    public static String EMAIL_KEY = "email";

    /** The total key. */
    public static String TOTAL_KEY = "total";

    /** The Constant NA. */
    public static final String NA = "N/A";

    /** The Constant AT_THE_RATE. */
    public static final String AT_THE_RATE = "@";

    /** The Constant TILDA. */
    public static final String TILDA = "~~";

    /** The Constant OPPORTUNITY. */
    public static final String OPPORTUNITY = "OPPORTUNITY";

    /**
     * The Constant SAVED_SEARCH_FIRST_FOLLOWUP_NOTIFICATION_MESSAGE_TYPE_NAME.
     */
    public static final String SAVED_SEARCH_FIRST_FOLLOWUP_NOTIFICATION_MESSAGE_TYPE_NAME = "Marathon_Followup_Email_1";

    /**
     * The Constant SAVED_SEARCH_SECOND_FOLLOWUP_NOTIFICATION_MESSAGE_TYPE_NAME.
     */
    public static final String SAVED_SEARCH_SECOND_FOLLOWUP_NOTIFICATION_MESSAGE_TYPE_NAME = "Marathon_Followup_Email_2";

    /** The Constant ACTIVITI_EXCEPTION. */
    public static final String ACTIVITI_EXCEPTION = "errorLog";

    /** The Constant NULL_STRING. */
    public static final String NULL_STRING = "NULL";

    /** The Constant PLUS_SIGN. */
    public static final String PLUS_SIGN = "+";

    /** The Constant GRAVITAS_MORTGAGE_LEAD_NOTIFICATION. */
    public static final String GRAVITAS_MORTGAGE_LEAD_NOTIFICATION = "GRAVITAS_MORTGAGE_LEAD_NOTIFICATION";

    /** The Constant YES. */
    public static final String YES = "Yes";

    /** The Constant NO. */
    public static final String NO = "No";

    /** The Constant SAVE_SEARCH. */
    public static final String SAVE_SEARCH = "SAVE_SEARCH";

    /** The Constant SAVE_SEARCH_CREATE_MSG. */
    public static final String SAVE_SEARCH_CREATE_MSG = "Saved Search Created";

    /** The Constant SAVE_SEARCH_FAILED_MSG. */
    public static final String SAVE_SEARCH_FAILED_MSG = "Saved Search Creation Failed";

    /** The Constant SAVE_SEARCH_EXISTS_MSG. */
    public static final String SAVE_SEARCH_EXISTS_MSG = "Saved Search Exists";

    /** The Constant CREATED. */
    public static final String CREATED = "Created";

    /** The Constant EXISTS. */
    public static final String EXISTS = "Exists";

    /** The Constant FAILED. */
    public static final String FAILED = "Failed";

    /** The Constant Lead Details Success Message. */
    public static final String LEAD_DETAILS_SUCCESS = "Lead Details are successfully returned";
    
    /** The Constant Opportunity Details Success Message. */
    public static final String OPPORTUNITY_DETAILS_SUCCESS = "Opportunity Details are successfully returned";

    /** The Constant Lead Details Failure Message. */
    public static final String LEAD_DETAILS_FAILURE = "Exception in retrieving Lead Details";
    
    /** The Constant Opportunity Details Failure Message. */
    public static final String OPPORTUNITY_DETAILS_FAILURE = "Exception in retrieving Opportunity Details";

    /** The Constant Lead Layout Get Exception Message. */
    public static final String LEAD_LAYOUT_GET_EXCEPTION = "Exception in retrieving Lead Layout";

    /** The Constant Lead Layout Save Exception Message. */
    public static final String LEAD_LAYOUT_SAVE_EXCEPTION = "Could not save the layout, please try later";

    /** The Constant Lead Layout Source Not Correct Message. */
    public static final String LEAD_LAYOUT_SOURCE_NOT_CORRECT = "Source of the lead layout request is incorrect";

    /** The Constant Lead Layout Get Success Message. */
    public static final String LEAD_LAYOUT_GET_SUCCESS = "Lead Layout is successfully returned";

    /** The Constant Lead Layout Save Success Message. */
    public static final String LEAD_LAYOUT_SAVE_SUCCESS = "Lead Layout is successfully saved";

    /** The Constant Role Config Success Message. */
    public static final String ROLE_CONFIG_GET_SUCCESS = "Role Config is successfully returned";

    /** The Constant Role Config Get Exception Message. */
    public static final String ROLE_CONFIG_GET_EXCEPTION = "Exception in retrieving Role Config";

    /** The Constant Role Config Failure Message, Role not correct. */
    public static final String ROLE_CONFIG_GET_FAILURE_ROLE_NOT_CORRECT = "Role is not correct";

    /** The Constant Role Config Failure Message. */
    public static final String ROLE_CONFIG_GET_FAILURE = "Failure in getting Role Config";

    /** The Constant Menu List Failure Message. */
    public static final String MENU_LIST_GET_FAILURE = "Failure in getting list of menus";

    /** The Constant Menu List Get Exception Message. */
    public static final String MENU_LIST_GET_EXCEPTION = "Exception in getting list of menus";

    /** The Constant Menu List Failure Message, Data is not present. */
    public static final String MENU_LIST_GET_FAILURE_DATA_NOT_PRESENT = "Data is not present";

    /** The Constant Menu List Success Message. */
    public static final String MENU_LIST_GET_SUCCESS = "Menu list is successfully returned";

    /** The Constant Role List Failure Message. */
    public static final String ROLE_LIST_GET_FAILURE = "Failure in getting list of roles";

    /** The Constant Menu List Get Exception Message. */
    public static final String ROLE_LIST_GET_EXCEPTION = "Exception in getting list of roles";

    /** The Constant Menu List Failure Message, Data is not present. */
    public static final String ROLE_LIST_GET_FAILURE_DATA_NOT_PRESENT = "Role for menu is not present";

    /** The Constant Menu List Success Message. */
    public static final String ROLE_LIST_GET_SUCCESS = "Role list is successfully returned";

    /** The Constant Claim Lead Success Message. */
    public static final String CLAIM_LEAD_SUCCESS = "Lead is successfully assigned";

    /** The Duplicate Lead. */
    public static final String LEAD_DUPLICATE_MESSAGE = "Duplicate Lead";

    /** The Constant Claim Lead Failure Message. */
    public static final String CLAIM_LEAD_FAILURE = "The lead is not available anymore as it has already been claimed by a different user. Please try claiming a different lead";

    /** The Constant Claim Lead not present Message. */
    public static final String CLAIM_LEAD_FAILURE_HEADER = "Lead Already Claimed";

    /** The Constant Claim Lead not present Message. */
    public static final String CLAIM_LEAD_SUCCESS_HEADER = "Success";

    /** The Constant REGISTERED_ON_OWNERS_COM. */
    public static final String REGISTERED_ON_OWNERS_COM = "REGISTERED_ON_OWNERS_COM";

    /** The Constant STAGE. */
    public static final String STAGE = "stage";

    /** The Constant REGISTERED_ON_GRAVITAS. */
    public static final String REGISTERED_ON_GRAVITAS = "Buyer registered on Gravitas at";

    /** The Constant STAGE. */
    public static final String ADDRESS = "Address";

    /** The Constant STAGE. */
    public static final String LISTING = "Listings";

    /** The Constant S_CURVE_AGENT. */
    public static final String S_CURVE_AGENT = "S-Curve Agent";

    public static final String BUYER_FARMING_DELAY = "buyerFarmingDelay";
    
    public static final String BUYER_REMINDER_DELAY = "buyerReminderDelay";

    /** The Constant DEFAULT_CBSA. */
    public static final String DEFAULT_CBSA = "default";

    /** The Constant INSIDE_SALES_ROLE_STR. */
    public static final String INSIDE_SALES_ROLE_STR = "INSIDE_SALES";

    /** The Constant NOTAVAILABLE. */
    public static final String NOTAVAILABLE = "NA";

    /** The Constant LEAD_SOURCE_URL. */
    public static final String ZILLOW_LEAD_SOURCE_URL = "http://www.zillow.com";

    /** The Constant Zillow hotline lead domain. */
    public static final String ZILLOW_HOTLEAD_DOMAIN = "@zillow.com";

    /** The Constant COMMON_EMAIL_PREFIX. */
    public static final String COMMON_LEAD_PREFIX = "lead_";

    public static final String COMMON_EMAIL_PREFIX = "mail_";

    /** The Constant Zillow hotline lead email prefix. */
    public static final String ZILLOW_HOTLINE_EMAIL_PREFIX = "zh_";

    /** The Constant Zillow hotline lead source. */
    public static final String ZILLOW_HOTLINE_LEAD_SOURCE = "Zillow Hotline";

    /** The Constant BUYER. */
    public static final String BUYER = "BUYER";

    /** The Constant FULL_NAME. */
    public static final String FULL_NAME = "Full Name";

    /** The Constant PHONE_NUMBER. */
    public static final String PHONE_NUMBER = "phoneNumber";

    /** The Constant KEY_FOR_PASSWORD_ENC. */
    public static final String KEY_FOR_PASSWORD_ENC = "password";

    public static final String INSTANT = "instant";

    public static final String DAILY = "daily";

    public static final String UNSUBSCRIBE = "unsubscribe";

    /** The Constant TAB. */
    public static final String TAB = "TAB";
	   
    /** The constant SKIP_SCHEDULE_MEETING. */
    public static final String SKIP_SCHEDULE_MEETING="SKIP_SCHEDULE_MEETING/TOUR";
     
    /** The constant SKIP_SCHEDULE_MEETING_DESC. */
    public static final String SKIP_SCHEDULE_MEETING_DESC = "skip schedule meeting/tour";
    
    public static final String DELETED_CC = "Deleted";
    
    public static final String PENDING_LC = "pending";
    
    public static final String COMPLETED_LC = "completed";
    
    public static final String CONFIRMED_LC = "confirmed";
    
    public static final String PAST_DUE_LC = "past due";
    
    public static final String ON_DUTY = "On Duty";
    
    public static final String OFF_DUTY = "Off Duty";
    
    /** The Constant FIRST NAME. */
    public static final String FIRST_NAME = "FIRST_NAME";
    
    /** The Subject  */
    public static final String GRAVITAS_BUYER_AGNT_OPP_ASGMNT_NTFC_SUBJECT = "your new home is just around the corner";
    
    /** The MessageTypeName  */
    public static final String GRAVITAS_BUYER_AGNT_OPP_ASGMNT_NTFC = "GRAVITAS_BUYER_AGNT_OPP_ASGMNT_NTFC";
    
    /** The Constant NEW. */
    public static final String NEW = "NEW";
    
    /** The Constant DEDUPED. */
    public static final String DEDUPED = "DEDUPED";
    
    /** The Constant LEAD. */
    public static final String lead = "lead";
    
    /** The Constant LEAD. */
    public static final String opportunity = "opportunity";
    public static final String SUCCESS = "Success";
    
    /** The PRICE_RANGE_IS_NOT_PRESENT  */
    public static final String PRICE_RANGE_IS_NOT_PRESENT = "Cannot provide Discount $ without Price Range in Opportunity";
    
    /** The STATE_IS_NOT_PRESENT  */
    public static final String STATE_IS_NOT_PRESENT = "Cannot provide Discount $ without State in Opportunity";

    /** The PRICE_RANGE_AND_STATE_IS_NOT_PRESENT  */
    public static final String PRICE_RANGE_AND_STATE_IS_NOT_PRESENT = "Cannot provide Discount $ without Price Range and State in Opportunity";
    
    /** The PRICE_RANGE_AND_STATE_IS_NOT_PRESENT  */
    public static final String NO_DISCOUNT_FOR_PRICE_RANGE = "We do not have a corresponding Discount for this particular price point";
    
    /** The PRICE_RANGE_AND_STATE_IS_NOT_PRESENT  */
    public static final String NO_DISCOUNT_FOR_STATE = "We do not have a corresponding Discount for this particular state";

    /** The Constant OCL_REFFERAL_STATUS. */
    public static final String OCL_REFFERAL_STATUS = "oclReferralStatus";

    /**
     * Instantiates a new constants.
     */
    private Constants() {
        // does nothing
    }
}
