package com.owners.gravitas.business.impl;

import static net.sf.dynamicreports.report.builder.DynamicReports.stl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.elasticsearch.common.lang3.StringUtils;
import org.joda.time.DateTime;
import org.json.simple.parser.ParseException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hubzu.notification.dto.client.email.Attachment;
import com.hubzu.notification.dto.client.email.AttachmentSource;
import com.hubzu.notification.dto.client.email.Email;
import com.hubzu.notification.dto.client.email.EmailNotification;
import com.hubzu.notification.dto.client.email.EmailRecipients;
import com.owners.gravitas.business.NotificationBusiness;
import com.owners.gravitas.business.RecordViewBusiness;
import com.owners.gravitas.business.builder.response.SavedFilterResponseBuilder;
import com.owners.gravitas.business.helper.Templates;
import com.owners.gravitas.domain.entity.RecordReportsNotficationEntity;
import com.owners.gravitas.domain.entity.User;
import com.owners.gravitas.dto.request.FilterCriteria;
import com.owners.gravitas.dto.request.RecordViewRequest;
import com.owners.gravitas.dto.response.RecordViewResponse;
import com.owners.gravitas.dto.response.SavedFilterResponse;
import com.owners.gravitas.service.NotificationService;
import com.owners.gravitas.service.RecordViewService;
import com.owners.gravitas.service.SavedFilterService;
import com.owners.gravitas.service.UserService;
import com.owners.gravitas.util.FileStorageUtil;
import com.owners.notification.dto.NotificationEngineResponse;

import net.sf.dynamicreports.jasper.builder.JasperReportBuilder;
import net.sf.dynamicreports.report.builder.DynamicReports;
import net.sf.dynamicreports.report.builder.column.Columns;
import net.sf.dynamicreports.report.builder.datatype.DataTypes;
import net.sf.dynamicreports.report.builder.style.StyleBuilder;
import net.sf.dynamicreports.report.exception.DRException;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

/**
 * Implementation class for NotificationBusiness
 * 
 * @author sandeepsoni
 *
 */
@Service
public class NotificationBusinessImpl implements NotificationBusiness {

    @Autowired
    RecordViewService recordViewService;

    @Autowired
    SavedFilterService savedFilterService;

    @Autowired
    SavedFilterResponseBuilder savedFilterResponseBuilder;

    @Autowired
    RecordViewBusiness recordViewBusiness;

    @Autowired
    NotificationService notificationService;

    @Autowired
    FileStorageUtil fileStorageUtil;

    @Value( "${file.temp.location}" )
    private String fileTempLocation;

    @Value( "${notification.upload.directory.location}" )
    private String gravitasS3Location;

    @Value( "${gravitas.notification.clientId}" )
    private String clientId;

    private static String MESSAGE_TYPE_NAME = "GRAVITAS_RECORDVIEW_REPORT_NTFC";

    @Autowired
    UserService userService;

    /**
     * To process notification
     * 
     * @param filePathUrl
     * @param userId
     * @param fileName
     * @return
     */
    public String processNotification( String filePathUrl, String userId, String fileName, Timestamp triggerTime ) {
        String status = "FAILED";

        EmailNotification emailNotification = new EmailNotification();

        emailNotification.setClientId( clientId );
        emailNotification.setMessageTypeName( MESSAGE_TYPE_NAME );
        emailNotification.setPriority( 0 );

        Email emailRequest = prepareEmailRequest( filePathUrl, userId, fileName, triggerTime );
        emailNotification.setEmail( emailRequest );
        NotificationEngineResponse response = notificationService.sendNotification( emailNotification );

        if (response != null) {
            status = response.getStatusCode();
        }
        return status;
    }

    /**
     * @param filePathUrl
     * @param userId
     * @param fileName
     * @param emailNotification
     */
    private Email prepareEmailRequest( String filePathUrl, String userId, String fileName, Timestamp triggerTime ) {
        Email emailRequest = new Email();
        EmailRecipients recipients = new EmailRecipients();
        List< String > toList = new ArrayList< String >();

        User user = getUserEmail( userId );
        toList.add( user.getEmail() );
        recipients.setToList( toList );
        emailRequest.setRecipients( recipients );

        emailRequest.setFromEmail( "QEtestadmin@owners.com" );

        Attachment attachments = new Attachment();
        attachments.setFileName( fileName );
        attachments.setFilePath( filePathUrl );
        attachments.setSource( AttachmentSource.S3 );

        List< Attachment > attachmentList = new ArrayList< Attachment >();
        attachmentList.add( attachments );

        emailRequest.setAttachments( attachmentList );
        Map< String, String > parameterMap = new HashMap< String, String >();
        parameterMap.put( "FIRST_NAME", "Guest" );
        parameterMap.put( "SAVED_SEARCH_NAME", fileName );
        parameterMap.put( "EMAIL_SUBJECT", "Report for " + fileName );
        parameterMap.put( "SCHEDULED_DATETIME", formatDateTime( triggerTime ) );
        emailRequest.setParameterMap( parameterMap );
        return emailRequest;
    }

    /**
     * To formate timestamp to date and time
     * 
     * @return
     */
    private String formatDateTime( Timestamp dateTime ) {
        String formattedDateTime = null;
        if (dateTime != null) {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat( "MM/dd/yyyy' 'HH:mm" );
            formattedDateTime = simpleDateFormat.format( dateTime );
        }
        return formattedDateTime;
    }

    /**
     * To get User email Id
     * 
     * @param userId
     * @return
     */
    private User getUserEmail( String userId ) {
       return userService.getUserDetailsByUserId(userId);
    }

    @Override
    public void processPendingNotifications() {

        List< RecordReportsNotficationEntity > pendingList = recordViewService.getPendingNotifcationList();
        for ( RecordReportsNotficationEntity pendingNotification : pendingList ) {
            SavedFilterResponse savedFilterResponse = recordViewBusiness
                    .getSavedFilterById( pendingNotification.getSavedFilterId() );
            if (savedFilterResponse != null && savedFilterResponse.getData() != null) {
                com.owners.gravitas.dto.response.SavedFilter savedFilter = savedFilterResponse.getData().get( 0 );
                RecordViewRequest recordViewRequest = createRecordViewRequest( savedFilter );
                RecordViewResponse recordViewResponse = recordViewBusiness.getRecord( recordViewRequest );
                if (recordViewResponse != null) {
                    generateReport( recordViewResponse.getData(), savedFilter.getFilterName(), pendingNotification,
                            savedFilter.getUserId() );
                }

            }
        }

    }

    /**
     * To generate report
     * 
     * @param object
     */
    private void generateReport( List< Map< String, Object > > data, String reportName,
            RecordReportsNotficationEntity pendingNotification, String userId ) {
        if (CollectionUtils.isNotEmpty( data )) {
            String fileType = pendingNotification.getFileType();
            Map< String, Object > map = data.get( 0 );
            JasperReportBuilder report = DynamicReports.report();
            StyleBuilder textStyle = stl.style( Templates.columnStyle ).setBorder( stl.pen1Point() );
            StyleBuilder dataTitle = stl.style( Templates.columnTitleStyle ).setBorder( stl.pen1Point() );

            for ( Map.Entry< String, Object > entry : map.entrySet() ) {
            	
            		if(entry != null && entry.getValue() != null) {
            			if (entry.getValue().getClass() == Integer.class) {
                            report.columns( Columns.column( entry.getKey().replaceAll( "\\s+", "" ), entry.getKey(),
                                    DataTypes.integerType() ) );
                        } else if (entry.getValue().getClass() == String.class) {
                            report.columns( Columns.column( entry.getKey().replaceAll( "\\s+", "" ), entry.getKey(),
                                    DataTypes.stringType() ) );
                        } else if (entry.getValue().getClass() == Float.class) {
                            report.columns( Columns.column( entry.getKey().replaceAll( "\\s+", "" ), entry.getKey(),
                                    DataTypes.floatType() ) );
                        } else if (entry.getValue().getClass() == BigDecimal.class) {
                            report.columns( Columns.column( entry.getKey().replaceAll( "\\s+", "" ), entry.getKey(),
                                    DataTypes.bigDecimalType() ) );
                        } else if (entry.getValue().getClass() == Double.class) {
                            report.columns( Columns.column( entry.getKey().replaceAll( "\\s+", "" ), entry.getKey(),
                                    DataTypes.doubleType() ) );
                        } else if (entry.getValue().getClass() == Byte.class) {
                            report.columns( Columns.column( entry.getKey().replaceAll( "\\s+", "" ), entry.getKey(),
                                    DataTypes.byteType() ) );
                        } else if (entry.getValue().getClass() == Boolean.class) {
                            report.columns( Columns.column( entry.getKey().replaceAll( "\\s+", "" ), entry.getKey(),
                                    DataTypes.booleanType() ) );
                        } else if (entry.getValue().getClass() == Long.class) {
                            report.columns( Columns.column( entry.getKey().replaceAll( "\\s+", "" ), entry.getKey(),
                                    DataTypes.longType() ) );
                        } else if (entry.getValue().getClass() == DateTime.class) {
                            report.columns( Columns.column( entry.getKey().replaceAll( "\\s+", "" ), entry.getKey(),
                                    DataTypes.dateYearToSecondType() ) );
                        }
            		}
            }

            report.setDataSource( createDataSource( data ) );
            report.setColumnStyle( textStyle );
            report.setColumnTitleStyle( dataTitle );
            try {
                reportName = reportName.replaceAll( "\\s+", "" ) + "." + fileType;
                if (fileType.equalsIgnoreCase( "xls" )) {
                    report.toXlsx( new FileOutputStream( fileTempLocation + reportName ) );
                } else if (fileType.equalsIgnoreCase( "pdf" )) {
                    report.toPdf( new FileOutputStream( fileTempLocation + reportName ) );
                } else if (fileType.equalsIgnoreCase( "doc" )) {
                    report.toDocx( new FileOutputStream( fileTempLocation + reportName ) );
                } else if (fileType.equalsIgnoreCase( "csv" )) {
                    report.toCsv( new FileOutputStream( fileTempLocation + reportName ) );
                }
                String filePathUrl = putOnAmazonS3( fileTempLocation, reportName );

                String notificationResponse = processNotification( filePathUrl, userId, reportName,
                        pendingNotification.getTriggerTime() );
                if (notificationResponse.equals( "SUCCESS" )) {
                    pendingNotification.setFilePath( filePathUrl );
                    pendingNotification.setStatus( "COMPLETED" );
                    updateFilePathForNotification( pendingNotification );
                }
            } catch ( FileNotFoundException | DRException e ) {
                e.printStackTrace();
            }
        }
    }

    private void updateFilePathForNotification( RecordReportsNotficationEntity pendingNotification ) {
        if (pendingNotification != null) {
            recordViewService.saveOrUpdateRecordReportNotification( pendingNotification );
        }
    }

    /**
     * To create record view request
     * 
     * @param savedFilter
     * @return
     * @throws ParseException
     */
    private RecordViewRequest createRecordViewRequest( com.owners.gravitas.dto.response.SavedFilter savedFilter ) {
        RecordViewRequest recordViewRequest = null;
        if (savedFilter != null) {
            recordViewRequest = new RecordViewRequest();
            recordViewRequest.setRecordViewConfigId( savedFilter.getRecordViewConfigId() );
            ObjectMapper ob = new ObjectMapper();
            FilterCriteria filterCriteria;
            try {
                filterCriteria = ob.readValue( savedFilter.getFilterCriteria(), FilterCriteria.class );
                if (filterCriteria != null) {
                    recordViewRequest.setRoleList( filterCriteria.getRoleList() );
                    recordViewRequest.setConditionGroup( filterCriteria.getConditionGroup() );
                    recordViewRequest.setPageNumber( filterCriteria.getPageNumber() );
                    recordViewRequest.setPerPage(
                            filterCriteria.getPerPage() != null ? filterCriteria.getPerPage().toString() : "100000" );
                }
            } catch ( IOException e ) {
                System.out.println( "error in createRecordViewRequest " + e.getMessage() );
            }

        }
        return recordViewRequest;
    }

    /**
     * Put file on amazon s3
     * 
     * @param reportName
     */
    private String putOnAmazonS3( String filePath, String reportName ) {
        File file = new File( filePath + reportName );
        String fileRelativePath = gravitasS3Location + getFilePath();
        fileStorageUtil.uploadFile( file, fileRelativePath + "/" + reportName );
        file.delete();
        return fileRelativePath + "/" + reportName;
    }

    private JRDataSource createDataSource( List< Map< String, Object > > data ) {
        List< Map< String, Object > > datasource = new ArrayList< Map< String, Object > >();
        datasource.addAll( data );
        return new JRBeanCollectionDataSource( datasource );
    }

    private String getFilePath() {
        String filePath = "";
        Date date = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat( "yyyy/MM/dd" );
        filePath = formatter.format( date );
        return filePath;
    }

}
