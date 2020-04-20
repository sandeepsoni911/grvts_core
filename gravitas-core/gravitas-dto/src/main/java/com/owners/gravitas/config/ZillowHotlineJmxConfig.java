package com.owners.gravitas.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

@ManagedResource(objectName = "com.owners.gravitas:name=ZillowHotlineJmxConfig")
@Component
public class ZillowHotlineJmxConfig {

    private static final Logger LOGGER = LoggerFactory.getLogger(ZillowHotlineJmxConfig.class);

    @Value("${hotline.leads.inside_sales.inbound_calls_source_string:Owners.com Hotline}")
    private String inboundCallsInsideSalesLeadSourceStr;
    @Value("${hotline.leads.zillow_source_string:Zillow Hotline}")
    private String zillowLeadSourceStr;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    @ManagedAttribute
    public String getInboundCallsInsideSalesLeadSourceStr() {
        return inboundCallsInsideSalesLeadSourceStr;
    }

    @ManagedAttribute
    public void setInboundCallsInsideSalesLeadSourceStr(String inboundCallsInsideSalesLeadSourceStr) {
        this.inboundCallsInsideSalesLeadSourceStr = inboundCallsInsideSalesLeadSourceStr;
        propertyWriter.saveJmxProperty("hotline.leads.inside_sales.inbound_calls_source_string", inboundCallsInsideSalesLeadSourceStr);
        LOGGER.info("Changed the inboundCallsInsideSalesLeadSourceStr : {}", inboundCallsInsideSalesLeadSourceStr);
    }

    @ManagedAttribute
    public String getZillowLeadSourceStr() {
        return zillowLeadSourceStr;
    }

    @ManagedAttribute
    public void setZillowLeadSourceStr(String zillowLeadSourceStr) {
        this.zillowLeadSourceStr = zillowLeadSourceStr;
        propertyWriter.saveJmxProperty("hotline.leads.zillow_source_string", zillowLeadSourceStr);
        LOGGER.info("Changed the zillowLeadSourceStr : {}", zillowLeadSourceStr);
    }
}
