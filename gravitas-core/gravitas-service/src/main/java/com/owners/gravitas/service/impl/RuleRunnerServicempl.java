package com.owners.gravitas.service.impl;

import java.net.InetAddress;
import java.net.UnknownHostException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.hubzu.notification.dto.client.email.EmailNotification;
import com.owners.gravitas.dto.response.NotificationPreferenceResponse;
import com.owners.gravitas.dto.response.NotificationResponse;
import com.owners.gravitas.service.MailService;
import com.owners.gravitas.service.RuleRunnerService;
import com.owners.gravitas.service.util.LiveVoxUtil;
import com.owners.gravitas.service.util.RuleRunnerUtil;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class RuleRunnerServicempl.
 *
 * @author kushwaja
 */
@Service
public class RuleRunnerServicempl implements RuleRunnerService {

    /** Logger instance. */
    private static final Logger LOGGER = LoggerFactory.getLogger( RuleRunnerServicempl.class );

    @Autowired
    private RuleRunnerUtil ruleRunnerUtil;

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.service.RuleRunnerService#executeRuleRunner(java.lang
     * .Object[], java.lang.String[])
     */
    @Override
    public void executeRuleRunner( String[] rules, Object[] facts ) {
        ruleRunnerUtil.runRules( rules, facts );
    }

}
