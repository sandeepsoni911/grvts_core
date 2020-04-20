package com.owners.gravitas.config.tour;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jmx.export.annotation.ManagedAttribute;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

import com.owners.gravitas.util.PropertyWriter;

@ManagedResource(objectName = "com.owners.gravitas:name=TourFeedbackJmxConfig")
@Component
public class TourFeedbackJmxConfig {

    @Value("${gravitas.tour_feedback.send_email:false}")
    private boolean sendEmailFlag;
    @Value("${gravitas.tour_feedback.send_email_using_upload_events:false}")
    private boolean sendEmailUsingUploadEvents;
    @Value("${gravitas.tour_feedback.send_email_wait_time_min:5}")
    private int sendEmailWaitTimeMin;
    @Value("${gravitas.tour_feedback.send_email_subject}")
    private String sendEmailSubject;
    @Value("${gravitas.tour_feedback.from_display_name}")
    private String sendEmailFromDisplayName;

    @Value("${gravitas.tour_feedback.static_from_email:bhaaratsingh.rajput@owners.com}")
    private String staticFromEmail;
    @Value("${gravitas.tour_feedback.bcc_email:tour.report@owners.com}")
    private String bccEmail;
    @Value("${gravitas.tour_feedback.use_static_from_email:false}")
    private boolean useStaticFromEmail;

    /** The property writer. */
    @Autowired
    private PropertyWriter propertyWriter;

    @ManagedAttribute
    public boolean isSendEmailFlag() {
        return sendEmailFlag;
    }

    @ManagedAttribute
    public void setSendEmailFlag(boolean sendEmailFlag) {
        this.sendEmailFlag = sendEmailFlag;
        propertyWriter.saveJmxProperty("gravitas.tour_feedback.send_email", sendEmailFlag);
    }

    @ManagedAttribute
    public String getStaticFromEmail() {
        return staticFromEmail;
    }

    @ManagedAttribute
    public void setStaticFromEmail(String staticFromEmail) {
        this.staticFromEmail = staticFromEmail;
        propertyWriter.saveJmxProperty("gravitas.tour_feedback.static_from_emai", staticFromEmail);
    }

    @ManagedAttribute
    public boolean isUseStaticFromEmail() {
        return useStaticFromEmail;
    }

    @ManagedAttribute
    public void setUseStaticFromEmail(boolean useStaticFromEmail) {
        this.useStaticFromEmail = useStaticFromEmail;
        propertyWriter.saveJmxProperty("gravitas.tour_feedback.use_static_from_emai", useStaticFromEmail);        
    }
    
    @ManagedAttribute
    public String getBccEmail() {
        return bccEmail;
    }

    @ManagedAttribute
    public void setBccEmail(String bccEmail) {
        this.bccEmail = bccEmail;
        propertyWriter.saveJmxProperty("gravitas.tour_feedback.bcc_email", bccEmail);
    }

    @ManagedAttribute
    public boolean isSendEmailUsingUploadEvents() {
        return sendEmailUsingUploadEvents;
    }

    @ManagedAttribute
    public void setSendEmailUsingUploadEvents(boolean sendEmailUsingUploadEvents) {
        this.sendEmailUsingUploadEvents = sendEmailUsingUploadEvents;
        propertyWriter.saveJmxProperty("gravitas.tour_feedback.send_email_using_upload_events",
                sendEmailUsingUploadEvents);
    }

    @ManagedAttribute
    public int getSendEmailWaitTimeMin() {
        return sendEmailWaitTimeMin;
    }

    @ManagedAttribute
    public void setSendEmailWaitTimeMin(int sendEmailWaitTimeMin) {
        this.sendEmailWaitTimeMin = sendEmailWaitTimeMin;
        propertyWriter.saveJmxProperty("gravitas.tour_feedback.send_email_wait_time_min", sendEmailWaitTimeMin);
    }

    @ManagedAttribute
    public String getSendEmailSubject() {
        return sendEmailSubject;
    }

    @ManagedAttribute
    public void setSendEmailSubject(String sendEmailSubject) {
        this.sendEmailSubject = sendEmailSubject;
        propertyWriter.saveJmxProperty("gravitas.tour_feedback.send_email_subject", sendEmailSubject);
    }

    @ManagedAttribute
    public String getSendEmailFromDisplayName() {
        return sendEmailFromDisplayName;
    }

    @ManagedAttribute
    public void setSendEmailFromDisplayName(String sendEmailFromDisplayName) {
        this.sendEmailFromDisplayName = sendEmailFromDisplayName;
        propertyWriter.saveJmxProperty("gravitas.tour_feedback.from_display_name", sendEmailFromDisplayName);
    }
}
