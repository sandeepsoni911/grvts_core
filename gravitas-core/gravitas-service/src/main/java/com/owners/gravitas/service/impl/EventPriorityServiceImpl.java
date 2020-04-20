package com.owners.gravitas.service.impl;

import static com.owners.gravitas.constants.Constants.OPPORTUNITY;
import static com.owners.gravitas.constants.NotificationParameters.EMAIL_TEMPLATE;
import static com.owners.gravitas.constants.NotificationParameters.SUBJECT;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.owners.gravitas.amqp.BuyerWebActivitySource;
import com.owners.gravitas.config.BuyerFarmingConfig;
import com.owners.gravitas.domain.entity.Contact;
import com.owners.gravitas.enums.BuyerAction;
import com.owners.gravitas.service.ContactEntityService;
import com.owners.gravitas.service.EventPriorityService;
import com.owners.gravitas.service.UserService;
import com.owners.gravitas.service.chain.impl.FavoritedAction;

@Service
public class EventPriorityServiceImpl implements EventPriorityService {

    /** The Constant logger. */
    private static final Logger LOGGER = LoggerFactory.getLogger( EventPriorityServiceImpl.class );

    @Autowired
    private UserService userService;
    
    @Autowired
    private ContactEntityService contactEntityService;
    
    /** The buyer farming config. */
    @Autowired
    private BuyerFarmingConfig buyerFarmingConfig;
    
    @Autowired
    private FavoritedAction favoritedAction;

    /** The Constant FARMING_GROUP_ATTR. */
    private static final String FARMING_GROUP_ATTR = "farmingGroup";

    /** The Constant LONG_TERM. */
    private static final String LONG_TERM = "Long Term";

    /** The Constant Lost. */
    private static final String LOST = "Lost";

    /** Lost Opportunity User Action Flag. */
    @Value( "${lost.opp.user.action.mail.flag}" )
    private boolean lostOppUsrActFlag;
    
    /** The subject. */
    @Value( "${buyer.web.activity.repeat_pdp_viewed.subject}" )
    private String repeatPdpViewedSubject;
    
    @Value( "${buyer.web.activity.engaged_tos.subject}" )
    private String engagedTosSubject;
    
    @Value( "${buyer.web.activity.unengaged_30.subject}" )
    private String unengaged30Subject;
    
    @Value( "${buyer.web.activity.repeat_pdp_viewed.emailtemplate}" )
    private String repeatPdpViewedEmailtemplate;
    
    @Value( "${buyer.web.activity.engaged_tos.emailtemplate}" )
    private String engagedTosEmailtemplate;
    
    @Value( "${buyer.web.activity.unengaged_30.emailtemplate}" )
    private String unengaged30Emailtemplate;

    @Override
    public BuyerAction getHighestPriority( final BuyerWebActivitySource buyerWebActivitySource, final Contact contact ) {
        LOGGER.info( "Getting highest priority event name for the user {}", buyerWebActivitySource.getUserId() );
        String objectType = contact.getObjectType().getName().toLowerCase();
        String farmingGroup = contactEntityService.getContactAttribute( contact, FARMING_GROUP_ATTR, objectType );

        LOGGER.info("checking web Activity for buyer email id :{} , object type :{}, farming Group :{} and lostOppUsrActFlag :{} ",
                contact.getEmail(), objectType, farmingGroup, lostOppUsrActFlag );

        if ((farmingGroup.equals( LONG_TERM ) || ( lostOppUsrActFlag && farmingGroup.equals( LOST )
                && objectType.toLowerCase().equals( OPPORTUNITY.toLowerCase() ) ))
                && !userService.checkUserPreference( contact.getEmail() )) {
            final List< String > recievedActions = buyerWebActivitySource.getAlertDetails().stream()
                    .map( ald -> ald.getEventDisplayName() ).collect( Collectors.toList() );
            LOGGER.info( "Total received  activities {}", recievedActions.size() );
            try {
                return favoritedAction.getHighestPriorityAction( recievedActions, objectType );
            } catch ( Exception e ) {
                LOGGER.error( "No matching event action found for {} with exception {}", buyerWebActivitySource.getUserId(), e );
            }
        }
        return null;
    }

    @Override
    public boolean isEligibleForEmailNotification( BuyerAction webActivityAction ) {
        boolean flag = false;

        switch ( webActivityAction ) {
            case REPEAT_PDP_VIEWED:
                flag = buyerFarmingConfig.isBuyerWebActivityRepeatPdpViewedEnabled();
                break;
            case ENGAGED_TOS:
                flag = buyerFarmingConfig.isBuyerWebActivityEngagedTosEnabled();
                break;
            case UNENGAGED_30:
                flag = buyerFarmingConfig.isBuyerWebActivityUnengaged30Enabled();
                break;
            default :
                flag = true;
        }

        return flag;
    }
    
    @Override
    public Map<String, String> getSubjectAndEmailTemplate( BuyerAction priorityAction ) {
        String emailTemplate = null;
        String subject = null;
        Map<String, String> map = new HashMap<>();
        switch ( priorityAction ) {
            case REPEAT_PDP_VIEWED:
                emailTemplate = repeatPdpViewedEmailtemplate;
                subject = repeatPdpViewedSubject;
                break;
            case ENGAGED_TOS:
                emailTemplate = engagedTosEmailtemplate;
                subject = engagedTosSubject;
                break;
            case UNENGAGED_30:
                emailTemplate = unengaged30Emailtemplate;
                subject = unengaged30Subject;
                break;
            default:
                emailTemplate = null;
        }
        
        map.put( EMAIL_TEMPLATE, emailTemplate );
        map.put( SUBJECT, subject );

        return map;
    }

}
