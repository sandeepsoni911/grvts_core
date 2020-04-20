package com.owners.gravitas.business.builder;

import static com.owners.gravitas.constants.Constants.NOTIFICATION_CLIENT_ID;
import static com.owners.gravitas.constants.NotificationParameters.ADDRESS;
import static com.owners.gravitas.constants.NotificationParameters.AGENT_EMAIL;
import static com.owners.gravitas.constants.NotificationParameters.AGENT_FULL_NAME;
import static com.owners.gravitas.constants.NotificationParameters.AGENT_NAME;
import static com.owners.gravitas.constants.NotificationParameters.AGENT_PHONE;
import static com.owners.gravitas.constants.NotificationParameters.BATH;
import static com.owners.gravitas.constants.NotificationParameters.BED;
import static com.owners.gravitas.constants.NotificationParameters.BUYER_EMAIL;
import static com.owners.gravitas.constants.NotificationParameters.BUYER_FIRST_NAME;
import static com.owners.gravitas.constants.NotificationParameters.EMAIL_SUBJECT;
import static com.owners.gravitas.constants.NotificationParameters.FALSE;
import static com.owners.gravitas.constants.NotificationParameters.HERO_IMAGE;
import static com.owners.gravitas.constants.NotificationParameters.HIDE_ADDRESS;
import static com.owners.gravitas.constants.NotificationParameters.HTTPS_PREFIX;
import static com.owners.gravitas.constants.NotificationParameters.IS_SIMILAR_PROPERTY_EXIST;
import static com.owners.gravitas.constants.NotificationParameters.MLS_ID;
import static com.owners.gravitas.constants.NotificationParameters.MLS_LOGO;
import static com.owners.gravitas.constants.NotificationParameters.PDP_URL;
import static com.owners.gravitas.constants.NotificationParameters.PRICE;
import static com.owners.gravitas.constants.NotificationParameters.PROPERTY_CITY;
import static com.owners.gravitas.constants.NotificationParameters.PROPERTY_DESCRIPTION;
import static com.owners.gravitas.constants.NotificationParameters.SCHEDULE_TOUR_CONFIRMATION_EMAIL;
import static com.owners.gravitas.constants.NotificationParameters.SIMILAR_PROPERTIES;
import static com.owners.gravitas.constants.NotificationParameters.SQUARE_FEET;
import static com.owners.gravitas.constants.NotificationParameters.STATE_CODE;
import static com.owners.gravitas.constants.NotificationParameters.STREET_NAME;
import static com.owners.gravitas.constants.NotificationParameters.STREET_NUM;
import static com.owners.gravitas.constants.NotificationParameters.TASK_ID;
import static com.owners.gravitas.constants.NotificationParameters.TOUR_TIME;
import static com.owners.gravitas.constants.NotificationParameters.TOUR_ZIP;
import static com.owners.gravitas.constants.NotificationParameters.TRUE;
import static com.owners.gravitas.constants.NotificationParameters.USER_ID;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.hubzu.notification.dto.client.digest.DigestData;
import com.hubzu.notification.dto.client.digest.DigestEmailNotification;
import com.hubzu.notification.dto.client.digest.DigestSection;
import com.hubzu.notification.dto.client.digest.DigestSectionItem;
import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailRecipients;
import com.owners.gravitas.config.tour.TourFeedbackJmxConfig;
import com.owners.gravitas.dto.PropertyData;
import com.owners.gravitas.dto.response.PropertyDetailsResponse;
import com.owners.gravitas.dto.response.SimilarProperty;
import com.owners.gravitas.dto.response.SimilarPropertyResponse;
import com.owners.gravitas.service.PropertyService;
import com.owners.gravitas.util.MathUtil;

@Component
public class ScheduleTourConfirmEmailBuilder {

    @Value( "${schedule_tour_confirmation_subject}" )
    private String subject;
    
    @Value( "${schedule_tour_bcc_email:}" )
    private String bccEmail;

    @Autowired
    private PropertyService propertyService;
    
    @Autowired
    private TourFeedbackJmxConfig tourFeedbackJmxConfig;

    public DigestEmailNotification prepareEmailNotification( PropertyData propertyData,
            Map< String, String > agentAndBuyerMap ) {
        DigestEmailNotification digestEmailNotification = new DigestEmailNotification();
        digestEmailNotification.setClientId( NOTIFICATION_CLIENT_ID );
        DigestData digestData = new DigestData();
        String applicationDigestId = UUID.randomUUID().toString();
        digestData.setApplicationDigestId( applicationDigestId );
        List< DigestSection > sections = new ArrayList< DigestSection >();
        DigestSection digestSection = new DigestSection();
        digestSection.setKey( SIMILAR_PROPERTIES );
        List< DigestSectionItem > digestSectionItems = prepareAndGetDigestSectionItems( propertyData );
        digestSection.setItems( digestSectionItems );
        sections.add( digestSection );
        digestData.setSections( sections );
        digestEmailNotification.setDigestData( digestData );
        digestEmailNotification.setCreatedOn( new Timestamp( System.currentTimeMillis() ) );
        Email email = new Email();
        email.setFromDisplayName( agentAndBuyerMap.get( AGENT_FULL_NAME ) );
        final String fromEmail = getValidAgentEmail( agentAndBuyerMap.get( AGENT_EMAIL ) );
        email.setFromEmail( fromEmail );
        Map< String, String > parameterMap = getCommonParameterMap( propertyData, agentAndBuyerMap );
        if (CollectionUtils.isEmpty( digestSectionItems )) {
            parameterMap.put( IS_SIMILAR_PROPERTY_EXIST, FALSE );
            digestSection.setItems( prepareAndGetDummyDigestSectionItems() );
        } else {
            parameterMap.put( IS_SIMILAR_PROPERTY_EXIST, TRUE );
        }
        email.setParameterMap( parameterMap );
        EmailRecipients recipients = getRecipients( agentAndBuyerMap.get( BUYER_EMAIL ), fromEmail );
        email.setRecipients( recipients );
        email.setSubject( subject.replace( "[First_Name]", agentAndBuyerMap.get( BUYER_FIRST_NAME ) ) );
        digestEmailNotification.setEmail( email );
        digestEmailNotification.setMessageTypeName( SCHEDULE_TOUR_CONFIRMATION_EMAIL );
        digestEmailNotification.setTriggerOn( System.currentTimeMillis() + 10000 );

        return digestEmailNotification;
    }

    private Map< String, String > getCommonParameterMap( PropertyData propertyData,
            Map< String, String > agentAndBuyerMap ) {
        Map< String, String > parameterMap = new HashMap< String, String >();
        parameterMap.put( EMAIL_SUBJECT, subject.replace( "[First_Name]", agentAndBuyerMap.get( BUYER_FIRST_NAME ) ) );
        parameterMap.put( TOUR_TIME, agentAndBuyerMap.get( TOUR_TIME ) );
        parameterMap.put( AGENT_NAME, agentAndBuyerMap.get( AGENT_FULL_NAME ) );
        parameterMap.put( AGENT_EMAIL, agentAndBuyerMap.get( AGENT_EMAIL ) );
        parameterMap.put( AGENT_PHONE, agentAndBuyerMap.get( AGENT_PHONE ) );
        parameterMap.put( HIDE_ADDRESS, propertyData.isHideAddress() + "");
        parameterMap.put( USER_ID, agentAndBuyerMap.get( USER_ID ));
        parameterMap.put( TASK_ID, agentAndBuyerMap.get( TASK_ID ));
        if (CollectionUtils.isNotEmpty( propertyData.getImages() )) {
            parameterMap.put( HERO_IMAGE, propertyData.getImages().get( 0 ).getImageURL() );
        }
        parameterMap.put( PROPERTY_DESCRIPTION, propertyData.getDescription() );
        parameterMap.put( PRICE,
                ( null != propertyData.getPrice() ? MathUtil.formatAmount( new Double(propertyData.getPrice()), Boolean.TRUE ) : null ) );
        parameterMap.put( BED, propertyData.getBedRooms() );
        parameterMap.put( BATH,
                ( null != propertyData.getBathRooms() ? propertyData.getBathRooms().toString() : null ) );
        if (null != propertyData.getMapParameters()
                && CollectionUtils.isNotEmpty( propertyData.getMapParameters().get( "sqft" ) )) {
            parameterMap.put( SQUARE_FEET, propertyData.getMapParameters().get( "sqft" ).get( 0 ) );
        }
        if (StringUtils.isNotBlank( propertyData.getMlsBoardImageURL() )) {
            parameterMap.put( MLS_LOGO, HTTPS_PREFIX + propertyData.getMlsBoardImageURL() );
        }
        parameterMap.put( MLS_ID, propertyData.getMlsID() );
        parameterMap.put( PDP_URL, getCompletePdpUrl( propertyData ) );
        parameterMap.put( ADDRESS, propertyData.getPropertyAddress().getAddressLine1() );
        parameterMap.put( PROPERTY_CITY, propertyData.getPropertyAddress().getCity() );
        parameterMap.put( STATE_CODE, propertyData.getPropertyAddress().getState() );
        parameterMap.put( TOUR_ZIP, propertyData.getPropertyAddress().getZip() );

        return parameterMap;
    }

    private EmailRecipients getRecipients( final String to, final String bcc ) {
        EmailRecipients recipients = new EmailRecipients();
        List< String > toList = new ArrayList< String >();
        toList.add( to );
        List< String > bccList = new ArrayList< String >();
        bccList.add( bcc );
        if (StringUtils.isNotBlank(bccEmail)) {
            bccList.add(bccEmail);
        }
        recipients.setToList( toList );
        recipients.setBccList( bccList );
        return recipients;
    }

    private List< DigestSectionItem > prepareAndGetDigestSectionItems( PropertyData propertyData ) {
        List< DigestSectionItem > digestSectionItems = null;
        SimilarPropertyResponse similarPropertyResponse = propertyService
                .getBuyerSimilarListingsSearchDetails( propertyData );
        if (null != similarPropertyResponse
                && CollectionUtils.isNotEmpty( similarPropertyResponse.getSimilarPropertyList() )) {
            digestSectionItems = new ArrayList< DigestSectionItem >();
            int sequence = 0;
            for ( SimilarProperty similarProperty : similarPropertyResponse.getSimilarPropertyList() ) {
                DigestSectionItem digestSectionItem = new DigestSectionItem();
                digestSectionItem.setId( UUID.randomUUID().toString() );

                Map< String, String > parameters = new HashMap< String, String >();
                parameters.put( BED,
                        ( null != similarProperty.getBedRooms() ? similarProperty.getBedRooms().toString() : null ) );
                parameters.put( BATH,
                        ( null != similarProperty.getBathRooms() ? similarProperty.getBathRooms().toString() : null ) );
                final String price = null != similarProperty.getListPrice()
                        ? MathUtil.formatAmount( similarProperty.getListPrice(), Boolean.TRUE ) : null;
                parameters.put( PRICE, price );
                parameters.put( SQUARE_FEET,
                        ( null != similarProperty.getSpace() ? similarProperty.getSpace().toString() : null ) );
                if (CollectionUtils.isNotEmpty( similarProperty.getImageUrlList() )) {
                    parameters.put( HERO_IMAGE, similarProperty.getImageUrlList().get( 0 ) );
                }
                parameters.put( PDP_URL, propertyService.getPdpUrl( similarProperty ) );
                if (StringUtils.isNotBlank( similarProperty.getMlsBoardImageURL() )) {
                    parameters.put( MLS_LOGO, HTTPS_PREFIX + similarProperty.getMlsBoardImageURL() );
                }
                parameters.put( STREET_NUM, similarProperty.getStreetNumber() );
                parameters.put( STREET_NAME, similarProperty.getStreetName() );
                parameters.put( PROPERTY_CITY, similarProperty.getCity() );
                parameters.put( STATE_CODE, similarProperty.getState() );
                parameters.put( TOUR_ZIP, similarProperty.getZip() );
                parameters.put( HIDE_ADDRESS, similarProperty.isHideAddress() + "" );

                digestSectionItem.setParameters( parameters );
                digestSectionItem.setSequence( sequence++ );
                digestSectionItems.add( digestSectionItem );
                //sent top 3 similar items
                if (sequence == 3) {
                    break;
                }
            }
        }
        return digestSectionItems;
    }
    
    private String getCompletePdpUrl( PropertyData propertyData ) {
        PropertyDetailsResponse propertyDetailsResponse = new PropertyDetailsResponse();
        propertyDetailsResponse.setData( propertyData );
        return propertyService.getPdpUrl( propertyDetailsResponse,
                propertyService.getValidPropertyId( propertyData.getPropertyID() ) );
    }

    // Adding this for temporary as there is a issue with NE. It is not
    // accepting empty digest section items
    // It will be removed once NE fixes from their side.
    private List< DigestSectionItem > prepareAndGetDummyDigestSectionItems() {
        List< DigestSectionItem > digestSectionItems = new ArrayList< DigestSectionItem >();
        DigestSectionItem digestSectionItem = new DigestSectionItem();
        digestSectionItem.setId( UUID.randomUUID().toString() ); 
        Map< String, String > parameters = new HashMap< String, String >();
        parameters.put( BED, "2" );
        parameters.put( BATH, "2" );
        digestSectionItem.setParameters( parameters );
        digestSectionItem.setSequence( 0 );
        digestSectionItems.add( digestSectionItem );
        return digestSectionItems;
    }
    
    // this code was added since we do not have the from domain
    // registered and hence for testing this can be used where we set the
    // from email as per our valid domain
    private String getValidAgentEmail( String agentEmail ) {
        if (tourFeedbackJmxConfig.isUseStaticFromEmail()
                && !StringUtils.isEmpty( tourFeedbackJmxConfig.getStaticFromEmail() )) {
            agentEmail = tourFeedbackJmxConfig.getStaticFromEmail();
        }
        return agentEmail;
    }
}
