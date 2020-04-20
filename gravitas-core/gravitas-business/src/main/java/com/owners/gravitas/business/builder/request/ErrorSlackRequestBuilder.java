package com.owners.gravitas.business.builder.request;

import static com.owners.gravitas.constants.Constants.GRAVITAS;
import static com.owners.gravitas.constants.Constants.GRAVITAS_ERROR_LOGGING_CONFIG_DIR;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.owners.gravitas.business.builder.AbstractBuilder;
import com.owners.gravitas.dto.SlackAttachment;
import com.owners.gravitas.dto.SlackAttachment.Field;
import com.owners.gravitas.dto.SlackError;
import com.owners.gravitas.dto.request.SlackRequest;
import com.owners.gravitas.error.log.CacheManager;
import com.owners.gravitas.error.log.CachedObject;
import com.owners.gravitas.util.DateUtil;
import com.owners.gravitas.util.JsonUtil;

/**
 * The Class ErrorSlackRequestBuilder.
 *
 * @author vishwanathm
 */
@Component
public class ErrorSlackRequestBuilder extends AbstractBuilder< SlackError, SlackRequest > {

    /** The Constant ERROR_CODE_FIELD_LABEL. */
    private static final String ERROR_CODE_FIELD_LABEL = "Error Code";

    /** The Constant DESCRIPTION_FIELD_LABEL. */
    private static final String DESCRIPTION_FIELD_LABEL = "Description";

    /** The Constant REQUEST_PARAMETERS_FIELD_LABEL. */
    private static final String REQUEST_PARAMETERS_FIELD_LABEL = "Request Parameters";

    /** The Constant USER_EMAIL_FIELD_LABEL. */
    private static final String USER_EMAIL_FIELD_LABEL = "User Email";

    /** The Constant URL_FIELD_LABEL. */
    private static final String URL_FIELD_LABEL = "API URL";

    /** The Constant ERROR_TYPE_FIELD_LABEL. */
    private static final String ERROR_TYPE_FIELD_LABEL = "Error Type";

    /** The Constant ERROR_MESSAGE_FIELD_LABEL. */
    private static final String ERROR_MESSAGE_FIELD_LABEL = "Error Message";

    /** The Constant ERROR_DATE_TIME_FIELD_LABEL. */
    private static final String ERROR_DATE_TIME_FIELD_LABEL = "Error Date Time";

    /** The Constant ERROR_ID_FIELD_LABEL. */
    private static final String ERROR_ID_FIELD_LABEL = "Error Id";

    /** The Constant GRAVITAS_AGENT_APP_ERROR. */
    private static final String GRAVITAS_AGENT_APP_ERROR = "Gravitas Agent App Error";

    /** The error log file path. */
    @Value( "${error.log.file.path}" )
    private String errorLogFilePath;

    /** The Constant LOGGER. */
    private static final Logger LOGGER = LoggerFactory.getLogger( ErrorSlackRequestBuilder.class );

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertTo(java.lang.
     * Object, java.lang.Object)
     */
    @Override
    public SlackRequest convertTo( final SlackError source, final SlackRequest destination ) {
        SlackRequest errorRequest = destination;
        if (source != null) {
            if (errorRequest == null) {
                errorRequest = new SlackRequest();
            }
            errorRequest.setUsername( GRAVITAS );
            errorRequest.addAttachments( populateSlackAttachment( source ) );
        }
        return errorRequest;
    }

    /**
     * Populate slack attachment.
     *
     * @param source
     *            the source
     * @return the slack attachment
     */
    private SlackAttachment populateSlackAttachment( final SlackError source ) {
        final SlackAttachment slackAttachment = new SlackAttachment();
        slackAttachment.setTitle( GRAVITAS_AGENT_APP_ERROR );
        slackAttachment.addField( createField( ERROR_ID_FIELD_LABEL, source.getErrorId(), slackAttachment ) );
        slackAttachment
                .addField( createField( ERROR_CODE_FIELD_LABEL, source.getErrorCode().getCode(), slackAttachment ) );
        slackAttachment.addField( createField( ERROR_DATE_TIME_FIELD_LABEL,
                DateUtil.toString( new DateTime(), DateUtil.DATE_TIME_PATTERN ), slackAttachment ) );
        slackAttachment.addField( createField( ERROR_MESSAGE_FIELD_LABEL, source.getErrorMessage(), slackAttachment ) );
        slackAttachment.addField(
                createField( ERROR_TYPE_FIELD_LABEL, source.getException().getClass().toString(), slackAttachment ) );
        slackAttachment.addField( createField( URL_FIELD_LABEL, source.getRequestUrl(), slackAttachment ) );
        slackAttachment.addField( createField( USER_EMAIL_FIELD_LABEL, source.getUser(), slackAttachment ) );
        slackAttachment.addField( createField( REQUEST_PARAMETERS_FIELD_LABEL,
                JsonUtil.toJson( source.getRequestParams() ), slackAttachment ) );
        slackAttachment.addField(
                createField( DESCRIPTION_FIELD_LABEL, getErrorStackTrace( source.getException() ), slackAttachment ) );

//        processForErrorLogAnalytics( source.getException().getClass().toString(), slackAttachment );
        return slackAttachment;
    }

    /**
     * Process for error log analytics.
     *
     * @param errorFieldLabel
     *            the error field label
     * @param slackAttachment
     *            the slack attachment
     */
    private void processForErrorLogAnalytics( final String errorFieldLabel, final SlackAttachment slackAttachment ) {
        LOGGER.debug( "Processing error log analytics for error " + errorFieldLabel );
        try {
            final Map< String, Integer > map = new HashMap< String, Integer >();
            final CachedObject existingCache = ( CachedObject ) CacheManager.getCache( new Long( 1234 ) );
            CachedObject newCache = new CachedObject( map, new Long( 1234 ), 0 );
            if (existingCache == null) {
                LOGGER.debug( "Cache does not exist making a new cache" );
                final int count = 1;
                map.put( errorFieldLabel, count );
                newCache = new CachedObject( map, new Long( 1234 ), 0 );
                CacheManager.putCache( newCache );
            } else {
                LOGGER.debug( "Cache already exist" + errorFieldLabel );
                final Map< String, Integer > existingCacheMap = ( Map ) existingCache.object;
                if (existingCacheMap.get( errorFieldLabel ) == null) {
                    final int count = 1;
                    existingCacheMap.put( errorFieldLabel, count );
                } else {
                    int count = ( Integer ) existingCacheMap.get( errorFieldLabel );
                    count++;
                    existingCacheMap.put( errorFieldLabel, count );
                }
            }
        } catch ( final Exception e ) {
            LOGGER.error( "Something went wrong in process for error log analytics " + e.getLocalizedMessage(), e );
        }
        writeError( errorFieldLabel, slackAttachment );
    }

    /**
     * Write error.
     *
     * @param errorFieldLabel
     *            the error field label
     * @param slackAttachment
     *            the slack attachment
     */
    private void writeError( final String errorFieldLabel, final SlackAttachment slackAttachment ) {
        String errorFilePath;
        try {
            errorFilePath = GRAVITAS_ERROR_LOGGING_CONFIG_DIR + errorLogFilePath
                    + com.owners.gravitas.constants.Constants.BLANK_SPACE
                    + DateUtil.toString( new DateTime(), DateUtil.DEFAULT_CRM_DATE_PATTERN ) + ".xls";
            LOGGER.debug( "Writing error in excel file for error " + errorFieldLabel );
            HSSFWorkbook workbook = null;
            FileOutputStream fileOut = null;
            // File file = new File( "D:/ErrorLogXcel.xls" );
            final File file = new File( errorFilePath );
            if (file.exists()) {
                LOGGER.debug( "Error log file already exist" );
                final FileInputStream fileIn = new FileInputStream( file );
                workbook = ( HSSFWorkbook ) WorkbookFactory.create( fileIn );
                final HSSFSheet existingWorksheet = workbook.getSheet( getWorksheetName( errorFieldLabel ) );
                if (existingWorksheet == null) {
                    LOGGER.debug( "Worksheet does not exist for error- " + errorFieldLabel );
                    createExcelAndAddData( errorFieldLabel, slackAttachment, workbook );
                } else {
                    LOGGER.debug( "Worksheet already exist for error " + errorFieldLabel );
                    int lastRowNum = existingWorksheet.getLastRowNum();
                    lastRowNum++;
                    final List< Field > fieldRowData = slackAttachment.getFields();
                    final HSSFRow rowData = existingWorksheet.createRow( lastRowNum );
                    LOGGER.debug( "Writing data in excel for error " + errorFieldLabel );
                    for ( int k = 0; k < fieldRowData.size(); k++ ) {
                        setData( rowData, k, fieldRowData.get( k ).getValue(), workbook, existingWorksheet );
                    }
                }
                fileOut = new FileOutputStream( errorFilePath );
                workbook.write( fileOut );
                fileOut.flush();
                fileOut.close();
            } else {
                LOGGER.debug( "Creating new file and logging error- " + errorFieldLabel );
                workbook = new HSSFWorkbook();
                fileOut = new FileOutputStream( errorFilePath );
                createExcelAndAddData( errorFieldLabel, slackAttachment, workbook );
                workbook.write( fileOut );
                fileOut.flush();
                fileOut.close();
            }

        } catch ( final Exception e ) {
            LOGGER.error( "Something went wrong in writing error " + e.getLocalizedMessage(), e );
        }
    }

    /**
     * Gets the worksheet name.
     *
     * @param errorFieldLabel
     *            the error field label
     * @return the worksheet name
     */
    private static String getWorksheetName( final String errorFieldLabel ) {
        final String exception = errorFieldLabel.substring( errorFieldLabel.lastIndexOf( '.' ) + 1,
                errorFieldLabel.length() );
        if (exception.length() > 30) {
            return exception.substring( 0, 30 );
        }
        return exception;
    }

    /**
     * Creates the excel and add data.
     *
     * @param errorFieldLabel
     *            the error field label
     * @param slackAttachment
     *            the slack attachment
     * @param workbook
     *            the workbook
     */
    private void createExcelAndAddData( final String errorFieldLabel, final SlackAttachment slackAttachment,
            final HSSFWorkbook workbook ) {

        LOGGER.debug( "Creating excel and adding data for " + errorFieldLabel );
        int rowNumErrorDetailsWorksheetHeader = 0;

        final HSSFSheet individualErrorWorksheet = workbook.createSheet( getWorksheetName( errorFieldLabel ) );
        final HSSFRow rowHeader = individualErrorWorksheet.createRow( ( short ) rowNumErrorDetailsWorksheetHeader );

        LOGGER.debug( "Adding headers for error " + errorFieldLabel );
        final List< Field > fieldHeader = slackAttachment.getFields();
        for ( int i = 0; i < fieldHeader.size(); i++ ) {
            setColumnHeaders( rowHeader, i, fieldHeader.get( i ).getTitle(), workbook, individualErrorWorksheet );
        }
        LOGGER.debug( "Adding error data for errror " + errorFieldLabel );
        rowNumErrorDetailsWorksheetHeader++;
        final List< Field > fieldRowData = slackAttachment.getFields();
        final HSSFRow rowData = individualErrorWorksheet.createRow( rowNumErrorDetailsWorksheetHeader );
        for ( int k = 0; k < fieldRowData.size(); k++ ) {
            setData( rowData, k, fieldRowData.get( k ).getValue(), workbook, individualErrorWorksheet );
        }
    }

    /**
     * Sets the data.
     *
     * @param row
     *            the row
     * @param coulumnNum
     *            the coulumn num
     * @param label
     *            the label
     * @param workbook
     *            the workbook
     * @param worksheet1
     *            the worksheet 1
     */
    private void setData( final HSSFRow row, final int coulumnNum, final String label, final HSSFWorkbook workbook, final HSSFSheet worksheet1 ) {
        final HSSFCell cell1 = row.createCell( ( short ) coulumnNum );
        cell1.setCellValue( label );
    }

    /**
     * Gets the column headers.
     *
     * @param row
     *            the row
     * @param coulumnNum
     *            the coulumn num
     * @param label
     *            the label
     * @param workbook
     *            the workbook
     * @param worksheet1
     *            the worksheet 1
     * @return the column headers
     */
    private void setColumnHeaders( final HSSFRow row, final int coulumnNum, final String label, final HSSFWorkbook workbook,
            final HSSFSheet worksheet1 ) {
        final HSSFCell cell1 = row.createCell( ( short ) coulumnNum );
        cell1.setCellValue( label );
        final HSSFCellStyle cellStylae = workbook.createCellStyle();
        cellStylae.setFillForegroundColor( HSSFColor.GOLD.index );
        cellStylae.setFillPattern( HSSFCellStyle.SOLID_FOREGROUND );
        cell1.setCellStyle( cellStylae );

    }

    /**
     * Gets the error stack trace.
     *
     * @param exception
     *            the exception
     * @return the error stack trace
     */
    private String getErrorStackTrace( final Throwable exception ) {
        final StringBuilder error = new StringBuilder();
        for ( final StackTraceElement stackTrace : exception.getStackTrace() ) {
            error.append( "\n" + stackTrace.toString() );
        }
        return error.toString();
    }

    /**
     * Creates the field.
     *
     * @param title
     *            the title
     * @param value
     *            the value
     * @param slackAttachment
     *            the slack attachment
     * @return the field
     */
    private Field createField( final String title, final String value, final SlackAttachment slackAttachment ) {
        final Field field = slackAttachment.new Field();
        field.setTitle( title );
        field.setValue( value );
        return field;
    }

    /*
     * (non-Javadoc)
     * @see
     * com.owners.gravitas.business.builder.AbstractBuilder#convertFrom(java.
     * lang.Object, java.lang.Object)
     */
    @Override
    public SlackError convertFrom( final SlackRequest source, final SlackError destination ) {
        throw new UnsupportedOperationException( "convertFrom is not supported" );
    }
}
